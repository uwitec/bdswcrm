package com.sw.cms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

import com.sw.cms.dao.LsdDAO.LsdProductRowMapper;
import com.sw.cms.dao.base.JdbcBaseDAO;
import com.sw.cms.dao.base.SqlUtil;
import com.sw.cms.model.Bxd;
import com.sw.cms.model.BxdProduct;
import com.sw.cms.model.LsdProduct;
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
		String sql = "select b.*,c.name as bxcs_name,(select sum(p.nums) as totalNums  from bxd_product p where b.id=p.bxd_id ) as productNums from bxd b left join clients c  on c.id=b.bxcs   where 1=1  ";
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
		String sql = "select *  from bxd where id='" + bxd_id + "'";
		return this.getJdbcTemplate().queryForObject(sql, new BxdRowMapper());
	}

	/**
	 * 根据报修单ID返回保修单所修商品
	 * 
	 * @param bxd_id
	 * @return
	 */
	public List getBxdProducts(String bxd_id) {
		String sql = "select * from bxd_product where bxd_id='" + bxd_id + "'";
		return this.getJdbcTemplate().query(sql, new BxdProductRowMapper());
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
	public void saveBxd(Bxd bxd, List bxdProducts) {
		Object[] param = new Object[8];
		String sql = "insert into bxd(id,bxdate,cjdate,jsr,cjr,state,bxcs,remark) values(?,?,?,?,?,?,?,?)";
		
		param[0] = bxd.getId();
		param[1] = bxd.getBxdate();
		param[2] = DateComFunc.getToday();		
		param[3] = bxd.getJsr();
		param[4] = bxd.getCjr();		
		param[5] = bxd.getState();
		param[6] = bxd.getBxcs();	
		param[7] = bxd.getRemark();
		this.getJdbcTemplate().update(sql, param);		
		
		this.saveBxdProduct(bxd, bxdProducts);
	}

	/**
	 * 保存报修单商品信息
	 * 
	 * @param bxdProduct
	 */
	public void saveBxdProduct(Bxd bxd, List bxdProducts) 
	{
		 
		String sql = "";
		Object[] param = new Object[11];	
		String bxd_id = bxd.getId();
		String sqlStore=""; 
		sqlStore= "select id from storehouse where name='坏件库'";		
		Map map=getResultMap(sqlStore);		
		String storeId= (String)map.get("id") ; 
		
		if(bxdProducts != null && bxdProducts.size()>0)
		{
			for(int i=0;i<bxdProducts.size();i++)
			{
				BxdProduct bxdProduct = (BxdProduct)bxdProducts.get(i);
				if(bxdProduct != null){
				  if(!bxdProduct.getProduct_id().equals("") && !bxdProduct.getProduct_name().equals(""))
					{
		               sql = "insert into bxd_product(bxd_id,product_name,product_xh,product_id,qz_serial_num,product_remark,store_id,storestate,nums,cpfj,sxts) values(?,?,?,?,?,?,?,?,?,?,?)";
             		   param[0] = bxd_id;
		               param[1] = bxdProduct.getProduct_name();
		               param[2] = bxdProduct.getProduct_xh();
		               param[3] = bxdProduct.getProduct_id();
		               param[4] = bxdProduct.getQz_serial_num();
		               param[5] = bxdProduct.getProduct_remark();
		
		               param[6] = storeId;
		               param[7] = "2";
		               param[8] = bxdProduct.getNums();
		               param[9] = bxdProduct.getCpfj();
		               param[10] = bxdProduct.getSxts();
		               this.getJdbcTemplate().update(sql,param);
					}
				}	
			}
		}
	}

	/**
	 * 更改报修单
	 * 
	 * @param id
	 * @param bxdProduct
	 */
	public void updateBxd(Bxd bxd, List bxdProducts) 
	{
		Object param[] = new Object[6];
				
		String sql = "update bxd set bxdate=?,jsr=?,state=?,bxcs=?,remark=? where id=? ";
        param[0] = bxd.getBxdate();
		param[1] = bxd.getJsr();		
		param[2] = bxd.getState();
		param[3] = bxd.getBxcs();		
		param[4] = bxd.getRemark();
		param[5] = bxd.getId();
		this.getJdbcTemplate().update(sql, param);
		delBxdProductByBxdId(bxd.getId());
		
		this.saveBxdProduct(bxd, bxdProducts);
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
			if (SqlUtil.columnIsExist(rs, "bxdate"))
				bxd.setBxdate(rs.getString("bxdate"));
			if (SqlUtil.columnIsExist(rs, "cjdate"))
				bxd.setCjdate(rs.getString("cjdate"));
			if (SqlUtil.columnIsExist(rs, "jsr"))
				bxd.setJsr(rs.getString("jsr"));
			if (SqlUtil.columnIsExist(rs, "cjr"))
				bxd.setCjr(rs.getString("cjr"));			
			if (SqlUtil.columnIsExist(rs, "state"))
				bxd.setState(rs.getString("state"));
			if (SqlUtil.columnIsExist(rs, "bxcs"))
				bxd.setBxcs(rs.getString("bxcs"));			
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
			if (SqlUtil.columnIsExist(rs, "product_xh"))
				bxdProduct.setProduct_xh(rs.getString("product_xh"));
			if (SqlUtil.columnIsExist(rs, "qz_serial_num"))
				bxdProduct.setQz_serial_num(rs
						.getString("qz_serial_num"));
			if (SqlUtil.columnIsExist(rs, "product_remark"))
				bxdProduct.setProduct_remark(rs.getString("product_remark"));
			if (SqlUtil.columnIsExist(rs, "store_id"))
				bxdProduct.setStore_id(rs.getString("store_id"));
			if (SqlUtil.columnIsExist(rs, "storestate"))
				bxdProduct.setStorestate(rs.getString("storestate"));
			if (SqlUtil.columnIsExist(rs, "nums"))
				bxdProduct.setNums(rs.getInt("nums"));
			if (SqlUtil.columnIsExist(rs, "cpfj"))
				bxdProduct.setCpfj(rs.getString("cpfj"));
			if (SqlUtil.columnIsExist(rs, "sxts"))
				bxdProduct.setSxts(rs.getInt("sxts"));
			return bxdProduct;
		}
	}

}
