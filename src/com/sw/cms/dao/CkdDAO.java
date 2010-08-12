package com.sw.cms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.sw.cms.dao.base.JdbcBaseDAO;
import com.sw.cms.dao.base.SqlUtil;
import com.sw.cms.model.Ckd;
import com.sw.cms.model.CkdProduct;
import com.sw.cms.model.Page;
import com.sw.cms.util.DateComFunc;

public class CkdDAO extends JdbcBaseDAO {

	/**
	 * 取列表（包括分页）
	 * 
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getCkdList(String con, int curPage, int rowsPerPage) {

		String sql = "select * from ckd where 1=1";

		if (!con.equals("")) {
			sql = sql + con;
		}

		return this.getResultByPage(sql, curPage, rowsPerPage);
	}

	/**
	 * 保存出库单信息
	 * 
	 * @param ckd
	 * @param ckdProducts
	 */
	public void saveCkd(Ckd ckd, List ckdProducts) {
		String sql = "insert into ckd(fzr,xsd_id,creatdate,ck_date,state,ms,client_name,xsry,store_id,czr,cz_date,skzt,ckd_id,tel,ysfs,cx_tel,job_no,send_time,client_lxr,client_lxr_address,client_lxr_tel,yfzf_type) values(?,?,?,?,?,?,?,?,?,?,now(),?,?,?,?,?,?,?,?,?,?,?)";
		Object[] param = new Object[21];

		param[0] = ckd.getFzr();
		param[1] = ckd.getXsd_id();
		param[2] = ckd.getCreatdate();
		param[3] = ckd.getCk_date();
		param[4] = ckd.getState();
		param[5] = ckd.getMs();
		param[6] = ckd.getClient_name();
		param[7] = ckd.getXsry();
		param[8] = ckd.getStore_id();
		param[9] = ckd.getCzr();
		param[10] = ckd.getSkzt();
		param[11] = ckd.getCkd_id();
		param[12] = ckd.getTel();
		param[13] = ckd.getYsfs();
		param[14] = ckd.getCx_tel();
		param[15] = ckd.getJob_no();
		param[16] = ckd.getSend_time();
		param[17] = ckd.getClient_lxr();
		param[18] = ckd.getClient_lxr_address();
		param[19] = ckd.getClient_lxr_tel();
		param[20] = ckd.getYfzf_type();

		this.getJdbcTemplate().update(sql, param); // 更新出库单信息

		addCkdProducts(ckdProducts, ckd.getCkd_id());// 添加出库单相关商品
	}

	/**
	 * 更新出库单信息
	 * 
	 * @param ckd
	 * @param ckdProducts
	 */
	public void updateCkd(Ckd ckd, List ckdProducts) {
		String sql = "update ckd set fzr=?,xsd_id=?,creatdate=?,ck_date=?,state=?,ms=?,client_name=?,xsry=?,store_id=?,czr=?,cz_date=now(),skzt=?,tel=?,ysfs=?,cx_tel=?,job_no=?,send_time=?,client_lxr=?,client_lxr_address=?,client_lxr_tel=?,yfzf_type=? where ckd_id=?";

		Object[] param = new Object[21];

		param[0] = ckd.getFzr();
		param[1] = ckd.getXsd_id();
		param[2] = ckd.getCreatdate();
		param[3] = ckd.getCk_date();
		param[4] = ckd.getState();
		param[5] = ckd.getMs();
		param[6] = ckd.getClient_name();
		param[7] = ckd.getXsry();
		param[8] = ckd.getStore_id();
		param[9] = ckd.getCzr();
		param[10] = ckd.getSkzt();
		param[11] = ckd.getTel();
		param[12] = ckd.getYsfs();
		param[13] = ckd.getCx_tel();
		param[14] = ckd.getJob_no();
		param[15] = ckd.getSend_time();
		param[16] = ckd.getClient_lxr();
		param[17] = ckd.getClient_lxr_address();
		param[18] = ckd.getClient_lxr_tel();
		param[19] = ckd.getYfzf_type();
		param[20] = ckd.getCkd_id();

		this.getJdbcTemplate().update(sql, param); // 更新出库单信息

		this.delCkdProducts(ckd.getCkd_id()); // 删除关联商品

		this.addCkdProducts(ckdProducts, ckd.getCkd_id()); // 重新添加关联商品

	}

	/**
	 * 根据出库单ID获取商品列表
	 * 
	 * @param ckd_id
	 * @return
	 */
	public List getCkdProducts(String ckd_id) {
		String sql = "select a.*,b.qz_serial_num as qz_flag,b.dw from ckd_product a left join product b on b.product_id=a.product_id where a.ckd_id='"
				+ ckd_id + "'";

		return this.getResultList(sql);
	}

	/**
	 * 根据出库单ID获取出库单信息
	 * 
	 * @param ckd_id
	 * @return
	 */
	public Object getCkd(String ckd_id) {
		String sql = "select * from ckd where ckd_id='" + ckd_id + "'";

		return this.queryForObject(sql, new CkdRowMapper());
	}

	/**
	 * 删除出库单信息
	 * 
	 * @param ckd_id
	 */
	public void delCkd(String ckd_id) {
		String sql = "delete from ckd where ckd_id='" + ckd_id + "'";
		this.getJdbcTemplate().update(sql);

		sql = "delete from ckd_product where ckd_id='" + ckd_id + "'";
		this.getJdbcTemplate().update(sql);
	}

	/**
	 * 添加出库单相关联商品
	 * 
	 * @param ckdProducts
	 * @param ckd_id
	 */
	private void addCkdProducts(List ckdProducts, String ckd_id) {
		String sql = "";
		double zje = 0;
		Object[] param = new Object[11];
		if (ckdProducts != null && ckdProducts.size() > 0) {
			for (int i = 0; i < ckdProducts.size(); i++) {
				CkdProduct cdkProduct = (CkdProduct) ckdProducts.get(i);
				if (cdkProduct != null) {
					if (!cdkProduct.getProduct_id().equals("")) {
						sql = "insert into ckd_product(ckd_id,product_id,product_xh,product_name,nums,remark,price,jgtz,cbj,qz_serial_num,ck_nums) values(?,?,?,?,?,?,?,?,?,?,?)";

						param[0] = ckd_id;
						param[1] = cdkProduct.getProduct_id();
						param[2] = cdkProduct.getProduct_xh();
						param[3] = cdkProduct.getProduct_name();
						param[4] = new Integer(cdkProduct.getNums());
						param[5] = cdkProduct.getRemark();
						param[6] = cdkProduct.getPrice();
						param[7] = cdkProduct.getJgtz();
						param[8] = cdkProduct.getCbj();
						param[9] = cdkProduct.getQz_serial_num();
						param[10] = cdkProduct.getCk_nums();

						this.getJdbcTemplate().update(sql, param);

						zje += (cdkProduct.getPrice() + cdkProduct.getJgtz())
								* cdkProduct.getNums();
					}
				}
			}
		}

		Object[] param2 = new Object[2];
		sql = "update ckd set hjje=? where ckd_id=?";
		param2[0] = zje;
		param2[1] = ckd_id;

		this.getJdbcTemplate().update(sql, param2);
	}

	/**
	 * 根据对应销售单编号查看是否存在相应的出库单
	 * 
	 * @param xsd_id
	 * @return
	 */
	public boolean isCkdExist(String xsd_id) {
		boolean is = false;
		String sql = "select count(*) from ckd where xsd_id='" + xsd_id + "'";
		int counts = this.getJdbcTemplate().queryForInt(sql);
		if (counts > 0) {
			is = true;
		}
		return is;
	}

	/**
	 * 查看出库单是否已经提交
	 * 
	 * @param ckd_id
	 * @return
	 */
	public boolean isCkdSubmit(String ckd_id) {
		boolean is = false;
		String sql = "select count(*) from ckd where ckd_id='" + ckd_id
				+ "' and state='已出库'";
		int counts = this.getJdbcTemplate().queryForInt(sql);
		if (counts > 0) {
			is = true;
		}
		return is;
	}
	
	/**
	 * 出库单是否已经删除
	 * @param ckd_id
	 * @return
	 */
	public boolean isCkdDel(String ckd_id) {
		boolean is = false;
		String sql = "select count(*) from ckd where ckd_id='" + ckd_id + "'";
		int counts = this.getJdbcTemplate().queryForInt(sql);
		if (counts == 0) {
			is = true;
		}
		return is;
	}

	/**
	 * 删除出库单关联商品
	 * 
	 * @param ckd_id
	 */
	private void delCkdProducts(String ckd_id) {
		String sql = "delete from ckd_product where ckd_id='" + ckd_id + "'";
		this.getJdbcTemplate().update(sql);
	}

	/**
	 * 取当前可用的序列号
	 * 
	 * @return
	 */
	public String getCkdID() {
		String sql = "select ckdid from cms_all_seq";

		// 取当前序列号
		String curId = this.getJdbcTemplate().queryForInt(sql) + "";

		// 将序列号加1
		sql = "update cms_all_seq set ckdid=ckdid+1";
		this.getJdbcTemplate().update(sql);

		for (int i = curId.length(); i < 3; i++) {
			curId = "0" + curId;
		}

		String day = DateComFunc.getCurDay();

		return "CK" + day + "-" + curId;
	}

	
	public Object getCkdByXsdId(String xsdid) {
		String sql = "select ckd_id from ckd where xsd_id='" + xsdid + "'";
		List list = this.getResultList(sql);
		return list.get(0);
	}

	
	public Object getCkdByIdBySerailNum(String id, String num) {
		String sql = "select c.client_name,p.product_id,p.product_xh,p.product_name,p.qz_serial_num from ckd c left join ckd_product p on c.ckd_id=p.ckd_id where c.ckd_id='"
				+ id + "' and p.qz_serial_num like '%" + num + "%'";
		return this.getResultMap(sql);
	}

	/**
	 * 包装对象(出库单)
	 * 
	 * @author liyt
	 * 
	 */
	class CkdRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			Ckd ckd = new Ckd();

			if (SqlUtil.columnIsExist(rs, "ckd_id"))
				ckd.setCkd_id(rs.getString("ckd_id"));
			if (SqlUtil.columnIsExist(rs, "xsd_id"))
				ckd.setXsd_id(rs.getString("xsd_id"));
			if (SqlUtil.columnIsExist(rs, "fzr"))
				ckd.setFzr(rs.getString("fzr"));
			if (SqlUtil.columnIsExist(rs, "creatdate"))
				ckd.setCreatdate(rs.getString("creatdate"));
			if (SqlUtil.columnIsExist(rs, "ck_date"))
				ckd.setCk_date(rs.getString("ck_date"));
			if (SqlUtil.columnIsExist(rs, "state"))
				ckd.setState(rs.getString("state")); // 物流状态
			if (SqlUtil.columnIsExist(rs, "ms"))
				ckd.setMs(rs.getString("ms"));
			if (SqlUtil.columnIsExist(rs, "client_name"))
				ckd.setClient_name(rs.getString("client_name"));
			if (SqlUtil.columnIsExist(rs, "xsry"))
				ckd.setXsry(rs.getString("xsry")); // 销售人员
			if (SqlUtil.columnIsExist(rs, "store_id"))
				ckd.setStore_id(rs.getString("store_id"));
			if (SqlUtil.columnIsExist(rs, "czr"))
				ckd.setCzr(rs.getString("czr"));
			if (SqlUtil.columnIsExist(rs, "skzt"))
				ckd.setSkzt(rs.getString("skzt"));// 收款状态
			if (SqlUtil.columnIsExist(rs, "tel"))
				ckd.setTel(rs.getString("tel")); // 客户联系电话

			if (SqlUtil.columnIsExist(rs, "ysfs"))
				ckd.setYsfs(rs.getString("ysfs")); // 运输方式
			if (SqlUtil.columnIsExist(rs, "cx_tel"))
				ckd.setCx_tel(rs.getString("cx_tel")); // 但询电话
			if (SqlUtil.columnIsExist(rs, "send_time"))
				ckd.setSend_time(rs.getString("send_time")); // 发货时间
			if (SqlUtil.columnIsExist(rs, "job_no"))
				ckd.setJob_no(rs.getString("job_no")); // 货单号

			if (SqlUtil.columnIsExist(rs, "client_lxr"))
				ckd.setClient_lxr(rs.getString("client_lxr"));
			if (SqlUtil.columnIsExist(rs, "client_lxr_address"))
				ckd.setClient_lxr_address(rs.getString("client_lxr_address"));
			if (SqlUtil.columnIsExist(rs, "client_lxr_tel"))
				ckd.setClient_lxr_tel(rs.getString("client_lxr_tel"));
			if (SqlUtil.columnIsExist(rs, "yfzf_type"))
				ckd.setYfzf_type(rs.getString("yfzf_type"));

			return ckd;
		}
	}

	/**
	 * 包装对象(出库单商品)
	 * 
	 * @author liyt
	 * 
	 */
	class CkdProductRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			CkdProduct ckdProduct = new CkdProduct();

			if (SqlUtil.columnIsExist(rs, "id"))
				ckdProduct.setId(rs.getInt("id"));
			if (SqlUtil.columnIsExist(rs, "ckd_id"))
				ckdProduct.setCkd_id(rs.getString("ckd_id"));
			if (SqlUtil.columnIsExist(rs, "product_id"))
				ckdProduct.setProduct_id(rs.getString("product_id"));
			if (SqlUtil.columnIsExist(rs, "product_xh"))
				ckdProduct.setProduct_xh(rs.getString("product_xh"));
			if (SqlUtil.columnIsExist(rs, "product_name"))
				ckdProduct.setProduct_name(rs.getString("product_name"));
			if (SqlUtil.columnIsExist(rs, "nums"))
				ckdProduct.setNums(rs.getInt("nums"));
			if (SqlUtil.columnIsExist(rs, "remark"))
				ckdProduct.setRemark(rs.getString("remark"));
			if (SqlUtil.columnIsExist(rs, "price"))
				ckdProduct.setPrice(rs.getDouble("price"));
			if (SqlUtil.columnIsExist(rs, "cbj"))
				ckdProduct.setCbj(rs.getDouble("cbj"));
			if (SqlUtil.columnIsExist(rs, "jgtz"))
				ckdProduct.setJgtz(rs.getDouble("jgtz"));
			if (SqlUtil.columnIsExist(rs, "qz_serial_num"))
				ckdProduct.setQz_serial_num(rs.getString("qz_serial_num"));
			if (SqlUtil.columnIsExist(rs, "ck_nums"))
				ckdProduct.setCk_nums(rs.getInt("ck_nums"));

			return ckdProduct;
		}
	}

}
