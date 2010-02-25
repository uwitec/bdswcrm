package com.sw.cms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

import com.sw.cms.dao.LsdDAO.LsdProductRowMapper;
import com.sw.cms.dao.base.JdbcBaseDAO;
import com.sw.cms.dao.base.SqlUtil;
import com.sw.cms.model.Hjd;
import com.sw.cms.model.HjdProduct;
import com.sw.cms.model.LsdProduct;
import com.sw.cms.model.Page;
import com.sw.cms.util.DateComFunc;

public class HjdDAO extends JdbcBaseDAO {
	/**
	 * 返回换件单列表（带分页）
	 * 
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getHjdList(String con, int curPage, int rowsPerPage) {
		String sql = "select b.*,(select count(*)  from hjd_product p where b.id=p.hjd_id ) as productNums from hjd b  where 1=1  ";
		if (!con.equals("")) {
			sql = sql + con;
		}
		return this.getResultByPage(sql, curPage, rowsPerPage);
	}

	/**
	 * 根据换件单ID返回换件单
	 * 
	 * @param hjd_id
	 * @return
	 */
	public Object getHjd(String hjd_id) {
		String sql = "select *  from hjd where id='" + hjd_id + "'";
		return this.getJdbcTemplate().queryForObject(sql, new HjdRowMapper());
	}

	/**
	 * 根据换件单ID返回换件单所修产品
	 * 
	 * @param hjd_id
	 * @return
	 */
	public List getHjdProducts(String hjd_id) {
		String sql = "select * from hjd_product where hjd_id='" + hjd_id + "'";
		return this.getJdbcTemplate().query(sql, new HjdProductRowMapper());
	}

	/**
	 * 删除换件单
	 * 
	 * @param hjd_id
	 */
	public void delHjd(String hjd_id) {
		String sql = "delete from hjd where id='" + hjd_id + "'";

		this.getJdbcTemplate().update(sql);

		sql = "delete from hjd_product where hjd_id='" + hjd_id + "'";
		this.getJdbcTemplate().update(sql);
	}
	
	/**
	 * 删除换件单商品
	 * @param hjdProductId
	 */
	public void delHjdProductByHjdId(String hjd_id)
	{
		String sql="delete from hjd_product where hjd_id='"+hjd_id+"'";
		this.getJdbcTemplate().update(sql);
	}

	/**
	 * 返回换件单可用ID
	 * 
	 * @return
	 */
	public String getHjdId() {
		String sql = "select hjdid from cms_all_seq";

		// 取当前序列号
		String curId = this.getJdbcTemplate().queryForInt(sql) + "";

		String day = DateComFunc.getCurDay();

		// 将序列号加1
		sql = "update cms_all_seq set hjdid=hjdid+1";
		this.getJdbcTemplate().update(sql);

		for (int i = curId.length(); i < 3; i++) {
			curId = "0" + curId;
		}

		return "HJ" + day + "-" + curId;

	}

	/**
	 * 根据序列号查询最近一次换件记录
	 * 
	 * @param num
	 * @return
	 */
	public Object getHjdRecord(String num) {
		String sql = " select p.product_id,p.product_name,p.product_xh,p.product_gg,p.product_serial_num,p.product_remark,p.gzfx,p.pcgc,b.client_name,b.lxr,b.lxdh,b.email,b.address,b.remark from hjd b left join hjd_product p on b.id=p.hjd_id  where  b.id=(select b.id from hjd b  left join hjd_product p on b.id=p.hjd_id  where p.product_serial_num='"
				+ num + "'  order by p.id desc limit 1 )";
		return this.getResultMap(sql);
	}

	/**
	 * 保存换件单信息
	 * 
	 * @param hjd
	 */
	public void saveHjd(Hjd hjd, List hjdProducts) {
		Object[] param = new Object[7];
		String sql = "insert into hjd(id,hj_date,cj_date,jsr,cjr,state,remark) values(?,?,?,?,?,?,?)";
		
		param[0] = hjd.getId();
		param[1] = hjd.getHj_date();
		param[2] = DateComFunc.getToday();		
		param[3] = hjd.getJsr();
		param[4] = hjd.getCjr();		
		param[5] = hjd.getState();
		param[6] = hjd.getRemark();
		this.getJdbcTemplate().update(sql, param);		
		
		this.saveHjdProduct(hjd, hjdProducts);
	}

	/**
	 * 保存换件单商品信息
	 * 
	 * @param hjdProduct
	 */
	public void saveHjdProduct(Hjd hjd, List hjdProducts) 
	{
		 
		String sql = "";
		Object[] param = new Object[7];	
		String hjd_id = hjd.getId();
				
		if(hjdProducts != null && hjdProducts.size()>0)
		{
			for(int i=0;i<hjdProducts.size();i++)
			{
				HjdProduct hjdProduct = (HjdProduct)hjdProducts.get(i);
				if(hjdProduct != null){
				  if(!hjdProduct.getProduct_id().equals("") && !hjdProduct.getProduct_name().equals(""))
					{
		               sql = "insert into hjd_product(hjd_id,product_name,product_xh,product_id,oqz_serial_num,nqz_serial_num,product_remark) values(?,?,?,?,?,?,?)";
             		   param[0] = hjd_id;
		               param[1] = hjdProduct.getProduct_name();
		               param[2] = hjdProduct.getProduct_xh();
		               param[3] = hjdProduct.getProduct_id();
		               param[4] = hjdProduct.getOqz_serial_num();
		               param[5] = hjdProduct.getNqz_serial_num();
		               param[6] = hjdProduct.getProduct_remark();
		               this.getJdbcTemplate().update(sql,param);
					}
				}	
			}
		}
	}

	/**
	 * 更改换件单
	 * 
	 * @param id
	 * @param hjdProduct
	 */
	public void updateHjd(Hjd hjd, List hjdProducts) 
	{
		Object param[] = new Object[5];
				
		String sql = "update hjd set hj_date=?,jsr=?,state=?,remark=? where id=? ";
        param[0] = hjd.getHj_date();
		param[1] = hjd.getJsr();		
		param[2] = hjd.getState();				
		param[3] = hjd.getRemark();
		param[4] = hjd.getId();
		this.getJdbcTemplate().update(sql, param);
		delHjdProductByHjdId(hjd.getId());
		
		this.saveHjdProduct(hjd, hjdProducts);
	}

	
	/**
	 * 换件单封装对象
	 * 
	 * @author Administrator
	 * 
	 */
	class HjdRowMapper implements RowMapper 
	{
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			Hjd hjd = new Hjd();
			if (SqlUtil.columnIsExist(rs, "id"))
				hjd.setId(rs.getString("id"));
			if (SqlUtil.columnIsExist(rs, "hj_date"))
				hjd.setHj_date(rs.getString("hj_date"));
			if (SqlUtil.columnIsExist(rs, "cj_date"))
				hjd.setCj_date(rs.getString("cj_date"));
			if (SqlUtil.columnIsExist(rs, "jsr"))
				hjd.setJsr(rs.getString("jsr"));
			if (SqlUtil.columnIsExist(rs, "cjr"))
				hjd.setCjr(rs.getString("cjr"));			
			if (SqlUtil.columnIsExist(rs, "state"))
				hjd.setState(rs.getString("state"));					
			if (SqlUtil.columnIsExist(rs, "remark"))
				hjd.setRemark(rs.getString("remark"));
			return hjd;
		}
	}

	/**
	 * 换件单商品封装对象
	 * 
	 * @author Administrator
	 * 
	 */
	class HjdProductRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			HjdProduct hjdProduct = new HjdProduct();
			if (SqlUtil.columnIsExist(rs, "id"))
				hjdProduct.setId(rs.getInt("id"));
			if (SqlUtil.columnIsExist(rs, "hjd_id"))
				hjdProduct.setHjd_id(rs.getString("hjd_id"));
			if (SqlUtil.columnIsExist(rs, "product_name"))
				hjdProduct.setProduct_name(rs.getString("product_name"));
			if (SqlUtil.columnIsExist(rs, "product_id"))
				hjdProduct.setProduct_id(rs.getString("product_id"));
			if (SqlUtil.columnIsExist(rs, "product_xh"))
				hjdProduct.setProduct_xh(rs.getString("product_xh"));
			if (SqlUtil.columnIsExist(rs, "oqz_serial_num"))
				hjdProduct.setOqz_serial_num(rs
						.getString("oqz_serial_num"));
			if (SqlUtil.columnIsExist(rs, "nqz_serial_num"))
				hjdProduct.setNqz_serial_num(rs
						.getString("nqz_serial_num"));
			if (SqlUtil.columnIsExist(rs, "product_remark"))
				hjdProduct.setProduct_remark(rs.getString("product_remark"));
			
			return hjdProduct;
		}
	}

}
