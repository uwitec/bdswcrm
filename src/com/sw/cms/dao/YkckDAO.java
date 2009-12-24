package com.sw.cms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.sw.cms.dao.KfdbDAO.KfdbRowMapper;
import com.sw.cms.dao.base.JdbcBaseDAO;
import com.sw.cms.dao.base.SqlUtil;
import com.sw.cms.model.Kfdb;
import com.sw.cms.model.KfdbProduct;
import com.sw.cms.model.Page;
import com.sw.cms.model.Ykck;
import com.sw.cms.model.YkckProduct;
import com.sw.cms.util.DateComFunc;

public class YkckDAO extends JdbcBaseDAO
{
	 
	/**
	 * 根据查询条件取移库出库列表信息
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getYkckList(String con,int curPage, int rowsPerPage){
		String sql = "select * from ykck where 1=1";
		
		if(!con.equals("")){
			sql = sql + con;
		}
		
		return this.getResultByPage(sql, curPage, rowsPerPage, new YkckRowMapper());
	}
	
	/**
	 * 保存移库出库相关信息
	 * @param kfdb
	 * @param kfdbProducts
	 */
	public void saveYkck(Ykck ykck,List ykckProducts)
	{
		String sql = "insert into ykck(ck_date,jsr,ck_store_id,czr,state,remark,cz_date,id) values(?,?,?,?,?,?,now(),?)";
		
		Object[] param = new Object[7];
		
		param[0] = ykck.getCk_date();
		param[1] = ykck.getJsr();
		param[2] = ykck.getCk_store_id();		 
		param[3] = ykck.getCzr();
		param[4] = ykck.getState();
		param[5] = ykck.getRemark();
		param[6] = ykck.getId();		
		this.getJdbcTemplate().update(sql,param);
		
		this.addYkckProducts(ykckProducts, ykck.getId());
	}
	
	public void updateYkck(Ykck ykck,List ykckProducts){
		String sql = "update ykck set ck_date=?,jsr=?,ck_store_id=?,czr=?,state=?,remark=?,cz_date=now() where id=?";
		
		Object[] param = new Object[7];
		
		param[0] = ykck.getCk_date();
		param[1] = ykck.getJsr();
		param[2] = ykck.getCk_store_id();
		 
		param[3] = ykck.getCzr();
		param[4] = ykck.getState();
		param[5] = ykck.getRemark();
		param[6] = ykck.getId();
		
		this.getJdbcTemplate().update(sql,param);
		
		this.delYkckProducts(ykck.getId());
		
		this.addYkckProducts(ykckProducts, ykck.getId());
	}
	
	private void delYkckProducts(String ykck_id){
		String sql = "delete from ykck_product where ykck_id='" + ykck_id + "'";
		
		this.getJdbcTemplate().update(sql);
	}
	
	public boolean isDbFinish(String id) {
		boolean is = false;
		String sql = "select count(*) from ykck where id='" + id + "' and state='已提交'";
		int counts = this.getJdbcTemplate().queryForInt(sql);
		if(counts > 0){
			is = true;
		}
		return is;
	}
	public void delYkck(String id){
		String sql = "delete from ykck where id='" + id + "'";
		this.getJdbcTemplate().update(sql);
		
		sql = "delete from ykck_product where ykck_id='" + id + "'";
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * 添加移库出库相关联产品
	 * @param lsdProducts
	 * @param lsd_id
	 */
	private void addYkckProducts(List YkckProducts,String ykck_id){
		String sql = "";
		Object[] param = new Object[7];
		
		if(YkckProducts != null && YkckProducts.size()>0){
			for(int i=0;i<YkckProducts.size();i++){
				YkckProduct ykckProduct = (YkckProduct)YkckProducts.get(i);
				if(ykckProduct != null){
					if(!ykckProduct.getProduct_id().equals("") && !ykckProduct.getProduct_name().equals("")){
						sql = "insert into ykck_product(ykck_id,product_id,product_xh,product_name,nums,product_remark,qz_serial_num) values(?,?,?,?,?,?,?)";
						
						param[0] = ykck_id;
						param[1] = ykckProduct.getProduct_id();
						param[2] = ykckProduct.getProduct_xh();
						param[3] = ykckProduct.getProduct_name();
						param[4] = new Integer(ykckProduct.getNums());
						param[5] = ykckProduct.getProduct_remark();
						param[6] = ykckProduct.getQz_serial_num();
						
						this.getJdbcTemplate().update(sql,param);
					}
				}
			}
		}
	}
	
	/**
	 * 取库房调拨信息
	 * @param id
	 * @return
	 */
	public Object getYkck(String id){
		String sql = "select * from ykck where id='" + id + "'";
		return this.getJdbcTemplate().queryForObject(sql, new YkckRowMapper());
	}
	
	public List getykckProducts(String id){
		String sql = "select a.*,b.qz_serial_num as qz_flag,b.dw from ykck_product a left join product b on b.product_id=a.product_id where a.ykck_id='" + id + "'";
		return this.getResultList(sql);
	}
	
	
	
	public String getYkckID() {
		String sql = "select ykckid from cms_all_seq";

		// 取当前序列号
		String curId = this.getJdbcTemplate().queryForInt(sql) + "";
		
		String day = DateComFunc.getCurDay();

		// 将序列号加1
		sql = "update cms_all_seq set ykckid=ykckid+1";
		this.getJdbcTemplate().update(sql);

		for (int i = curId.length(); i < 3; i++) {
			curId = "0" + curId;
		}

		return "YC" + day + "-" + curId;
	}
	
	class YkckRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			Ykck ykck = new Ykck();

			ykck.setId(rs.getString("id"));
			ykck.setCk_date(rs.getString("ck_date"));
			ykck.setJsr(rs.getString("jsr"));
			ykck.setCk_store_id(rs.getString("ck_store_id"));			 			 
			ykck.setCzr(rs.getString("czr"));
			ykck.setState(rs.getString("state"));
			ykck.setRemark(rs.getString("remark"));
			
			return ykck;
		}
	}	
	
	 
	class YkckProductRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			YkckProduct ykckProduct = new YkckProduct();

			if(SqlUtil.columnIsExist(rs,"ykck_id")) ykckProduct.setYkck_id(rs.getString("ykck_id"));
			if(SqlUtil.columnIsExist(rs,"product_id")) ykckProduct.setProduct_id(rs.getString("product_id"));
			if(SqlUtil.columnIsExist(rs,"product_name")) ykckProduct.setProduct_name(rs.getString("product_name"));
			if(SqlUtil.columnIsExist(rs,"product_xh")) ykckProduct.setProduct_xh(rs.getString("product_xh"));
			if(SqlUtil.columnIsExist(rs,"qz_serial_num")) ykckProduct.setQz_serial_num(rs.getString("qz_serial_num"));
			if(SqlUtil.columnIsExist(rs,"product_remark")) ykckProduct.setProduct_remark(rs.getString("product_remark"));
			if(SqlUtil.columnIsExist(rs, "nums"))ykckProduct.setNums(rs.getInt("nums"));
			return ykckProduct;
		}
	}	

}
