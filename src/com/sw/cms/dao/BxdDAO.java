package com.sw.cms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.sw.cms.dao.base.JdbcBaseDAO;
import com.sw.cms.dao.base.SqlUtil;
import com.sw.cms.model.Bxd;
import com.sw.cms.model.BxdProduct;
import com.sw.cms.model.Page;
import com.sw.cms.util.DateComFunc;

public class BxdDAO extends JdbcBaseDAO {
	/**
	 * 返回报修单列表（带分页）
	 * 
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getBxdList(String con, int curPage, int rowsPerPage) {
		String sql = "select b.* from bxd b left join bxd_product p on b.id=p.bxd_id left join sys_user s on b.gcs=s.user_id  where 1=1  ";
		if (!con.equals("")) {
			sql = sql + con;
		}
		return this.getResultByPage(sql, curPage, rowsPerPage);
	}

	/**
	 * 根据报修单ID返回报修单
	 * 
	 * @param bxd_id
	 * @return
	 */
	public Object getBxd(String bxd_id) {
		String sql = "select *from bxd where id='" + bxd_id + "'";
		return this.getJdbcTemplate().queryForObject(sql, new BxdRowMapper());
	}

	/**
	 * 根据报修单ID返回保修单所修产品
	 * 
	 * @param bxd_id
	 * @return
	 */
	public Object getBxdProduct(String bxd_id) {
		String sql = "select *from bxd_product where bxd_id='" + bxd_id + "'";
		return this.getJdbcTemplate().queryForObject(sql,
				new BxdProductRowMapper());
	}

	/**
	 * 删除报修单
	 * 
	 * @param bxd_id
	 */
	public void delBxd(String bxd_id) {
		String sql = "delete from bxd where id='" + bxd_id + "'";

		this.getJdbcTemplate().update(sql);

		sql = "delete from bxd_product where bxd_id='" + bxd_id + "'";
		this.getJdbcTemplate().update(sql);
	}
	
	/**
	 * 删除报修单商品
	 * @param bxdProductId
	 */
	public void delBxdProductByBxdId(String bxd_id)
	{
		String sql="delete from bxd_product where bxd_id='"+bxd_id+"'";
		this.getJdbcTemplate().update(sql);
	}

	/**
	 * 返回报修单可用ID
	 * 
	 * @return
	 */
	public String getBxdId() {
		String sql = "select bxdid from cms_all_seq";

		// 取当前序列号
		String curId = this.getJdbcTemplate().queryForInt(sql) + "";

		String day = DateComFunc.getCurDay();

		// 将序列号加1
		sql = "update cms_all_seq set bxdid=bxdid+1";
		this.getJdbcTemplate().update(sql);

		for (int i = curId.length(); i < 3; i++) {
			curId = "0" + curId;
		}

		return "BX" + day + "-" + curId;

	}

	/**
	 * 根据序列号查询最近一次维修记录
	 * 
	 * @param num
	 * @return
	 */
	public Object getBxdRecord(String num) {
		String sql = " select p.product_id,p.product_name,p.product_xh,p.product_gg,p.product_serial_num,p.product_remark,p.gzfx,p.pcgc,b.client_name,b.lxr,b.lxdh,b.email,b.address,b.remark from bxd b left join bxd_product p on b.id=p.bxd_id  where  b.id=(select b.id from bxd b  left join bxd_product p on b.id=p.bxd_id  where p.product_serial_num='"
				+ num + "'  order by p.id desc limit 1 )";
		return this.getResultMap(sql);
	}

	/**
	 * 保存报修单信息
	 * 
	 * @param bxd
	 */
	public void saveBxd(Bxd bxd, BxdProduct bxdProduct) {
		String sql = "insert into bxd(id,jxdate,cjdate,jddate,jxr,cjr,gcs,state,client_name,lxr,lxdh,email,address,remark)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] param = new Object[14];
		param[0] = bxd.getId();
		param[1] = bxd.getJxdate();
		param[2] = DateComFunc.getToday();
		param[3] = bxd.getJddate();
		param[4] = bxd.getJxr();
		param[5] = bxd.getCjr();
		param[6] = bxd.getGcs();
		param[7] = bxd.getState();
		param[8] = bxd.getClient_name();
		param[9] = bxd.getLxr();
		param[10] = bxd.getLxdh();
		param[11] = bxd.getEmail();
		param[12] = bxd.getAddress();
		param[13] = bxd.getRemark();
		getJdbcTemplate().update(sql, param);
		saveBxdProduct(bxd.getId(), bxdProduct);
	}

	/**
	 * 保存报修单商品信息
	 * 
	 * @param bxdProduct
	 */
	public void saveBxdProduct(String id, BxdProduct bxdProduct) {
		String sql = "insert into bxd_product(bxd_id,product_name,product_gg,product_id,product_serial_num,product_remark,bxaddress,bxstate,fj,qtfj,bj,gzfx,pcgc)values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object param[] = new Object[13];
		param[0] = id;
		param[1] = bxdProduct.getProduct_name();
		param[2] = bxdProduct.getProduct_gg();
		param[3] = bxdProduct.getProduct_id();
		param[4] = bxdProduct.getProduct_serial_num();
		param[5] = bxdProduct.getProduct_remark();
		param[6] = bxdProduct.getBxaddress();
		param[7] = bxdProduct.getBxstate();
		param[8] = bxdProduct.getFj();
		param[9] = bxdProduct.getQtfj();
		param[10] = bxdProduct.getBj();
		param[11] = bxdProduct.getGzfx();
		param[12] = bxdProduct.getPcgc();
		getJdbcTemplate().update(sql, param);

	}

	/**
	 * 更改报修单
	 * 
	 * @param id
	 * @param bxdProduct
	 */
	public void updateBxd(Bxd bxd, BxdProduct bxdProduct) {
		String sql = "update bxd set jddate=?,jxr=?,gcs=?,state=?,client_name=?,lxr=?,lxdh=?,email=?,address=?,remark=? where id=? ";
		Object param[] = new Object[11];
		param[0] = bxd.getJddate();
		param[1] = bxd.getJxr();
		param[2] = bxd.getGcs();
		param[3] = bxd.getState();
		param[4] = bxd.getClient_name();
		param[5] = bxd.getLxr();
		param[6] = bxd.getLxdh();
		param[7] = bxd.getEmail();
		param[8] = bxd.getAddress();
		param[9] = bxd.getRemark();
		param[10] = bxd.getId();
		this.getJdbcTemplate().update(sql, param);
		delBxdProductByBxdId(bxd.getId());
		updateBxdProduct(bxd.getId(), bxdProduct);
	}

	/**
	 * 更改报修单商品
	 * 
	 * @param id
	 * @param bxdProduct
	 */
	public void updateBxdProduct(String id, BxdProduct bxdProduct) {
		String sql = "update bxd_product set product_name=?,product_gg=?,product_id=?,product_serial_num=?,product_remark=?,bxaddress=?,bxstate=?,fj=?,qtfj=?,bj=?,gzfx=?,pcgc=? where bxd_id=?";
		Object param[] = new Object[13];
		param[0] = bxdProduct.getProduct_name();
		param[1] = bxdProduct.getProduct_gg();
		param[2] = bxdProduct.getProduct_id();
		param[3] = bxdProduct.getProduct_serial_num();
		param[4] = bxdProduct.getProduct_remark();
		param[5] = bxdProduct.getBxaddress();
		param[6] = bxdProduct.getBxstate();
		param[7] = bxdProduct.getFj();
		param[8] = bxdProduct.getQtfj();
		param[9] = bxdProduct.getBj();
		param[10] = bxdProduct.getGzfx();
		param[11] = bxdProduct.getPcgc();
		param[12] = id;
		this.getJdbcTemplate().update(sql, param);
	}

	/**
	 * 报修单封装对象
	 * 
	 * @author Administrator
	 * 
	 */
	class BxdRowMapper implements RowMapper 
	{
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			Bxd bxd = new Bxd();
			if (SqlUtil.columnIsExist(rs, "id"))
				bxd.setId(rs.getString("id"));
			if (SqlUtil.columnIsExist(rs, "jxdate"))
				bxd.setJxdate(rs.getString("jxdate"));
			if (SqlUtil.columnIsExist(rs, "cjdate"))
				bxd.setCjdate(rs.getString("cjdate"));
			if (SqlUtil.columnIsExist(rs, "jddate"))
				bxd.setJddate(rs.getString("jddate"));
			if (SqlUtil.columnIsExist(rs, "jxr"))
				bxd.setJxr(rs.getString("jxr"));
			if (SqlUtil.columnIsExist(rs, "cjr"))
				bxd.setCjr(rs.getString("cjr"));
			if (SqlUtil.columnIsExist(rs, "gcs"))
				bxd.setGcs(rs.getString("gcs"));
			if (SqlUtil.columnIsExist(rs, "state"))
				bxd.setState(rs.getString("state"));
			if (SqlUtil.columnIsExist(rs, "client_name"))
				bxd.setClient_name(rs.getString("client_name"));
			if (SqlUtil.columnIsExist(rs, "lxr"))
				bxd.setLxr(rs.getString("lxr"));
			if (SqlUtil.columnIsExist(rs, "lxdh"))
				bxd.setLxdh(rs.getString("lxdh"));
			if (SqlUtil.columnIsExist(rs, "email"))
				bxd.setEmail(rs.getString("email"));
			if (SqlUtil.columnIsExist(rs, "address"))
				bxd.setAddress(rs.getString("address"));
			if (SqlUtil.columnIsExist(rs, "cz"))
				bxd.setCz(rs.getString("cz"));
			if (SqlUtil.columnIsExist(rs, "yb"))
				bxd.setYb(rs.getString("yb"));
			if (SqlUtil.columnIsExist(rs, "remark"))
				bxd.setRemark(rs.getString("remark"));
			return bxd;
		}
	}

	/**
	 * 报修单商品封装对象
	 * 
	 * @author Administrator
	 * 
	 */
	class BxdProductRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			BxdProduct bxdProduct = new BxdProduct();
			if (SqlUtil.columnIsExist(rs, "id"))
				bxdProduct.setId(rs.getInt("id"));
			if (SqlUtil.columnIsExist(rs, "bxd_id"))
				bxdProduct.setBxd_id(rs.getString("bxd_id"));
			if (SqlUtil.columnIsExist(rs, "product_name"))
				bxdProduct.setProduct_name(rs.getString("product_name"));
			if (SqlUtil.columnIsExist(rs, "product_id"))
				bxdProduct.setProduct_id(rs.getString("product_id"));
			if (SqlUtil.columnIsExist(rs, "product_gg"))
				bxdProduct.setProduct_gg(rs.getString("product_gg"));
			if (SqlUtil.columnIsExist(rs, "product_xh"))
				bxdProduct.setProduct_xh(rs.getString("product_xh"));
			if (SqlUtil.columnIsExist(rs, "product_serial_num"))
				bxdProduct.setProduct_serial_num(rs
						.getString("product_serial_num"));
			if (SqlUtil.columnIsExist(rs, "product_remark"))
				bxdProduct.setProduct_remark(rs.getString("product_remark"));
			if (SqlUtil.columnIsExist(rs, "bxaddress"))
				bxdProduct.setBxaddress(rs.getString("bxaddress"));
			if (SqlUtil.columnIsExist(rs, "bxstate"))
				bxdProduct.setBxstate(rs.getString("bxstate"));
			if (SqlUtil.columnIsExist(rs, "has_fj"))
				bxdProduct.setHas_fj(rs.getString("has_fj"));
			if (SqlUtil.columnIsExist(rs, "fj"))
				bxdProduct.setFj(rs.getString("fj"));
			if (SqlUtil.columnIsExist(rs, "qtfj"))
				bxdProduct.setQtfj(rs.getString("qtfj"));
			if (SqlUtil.columnIsExist(rs, "bj"))
				bxdProduct.setBj(rs.getString("bj"));
			if (SqlUtil.columnIsExist(rs, "gzfx"))
				bxdProduct.setGzfx(rs.getString("gzfx"));
			if (SqlUtil.columnIsExist(rs, "pcgc"))
				bxdProduct.setPcgc(rs.getString("pcgc"));
			if (SqlUtil.columnIsExist(rs, "gf"))
				bxdProduct.setGf(rs.getDouble("gf"));
			return bxdProduct;
		}
	}

}
