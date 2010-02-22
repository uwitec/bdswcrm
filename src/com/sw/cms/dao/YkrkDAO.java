package com.sw.cms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.sw.cms.dao.YkckDAO.YkckRowMapper;
import com.sw.cms.dao.base.JdbcBaseDAO;
import com.sw.cms.dao.base.SqlUtil;
import com.sw.cms.model.Page;
import com.sw.cms.model.Ykck;
import com.sw.cms.model.YkckProduct;
import com.sw.cms.model.Ykrk;
import com.sw.cms.model.YkrkProduct;
import com.sw.cms.util.DateComFunc;

public class YkrkDAO extends JdbcBaseDAO
{
	/**
	 * 根据查询条件取移库出库列表信息
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getYkrkList(String con,int curPage, int rowsPerPage){
		String sql = "select * from ykrk where 1=1";
		
		if(!con.equals("")){
			sql = sql + con;
		}
		
		return this.getResultByPage(sql, curPage, rowsPerPage, new YkrkRowMapper());
	}
	
	/**
	 * 保存移库出库相关信息
	 * @param kfdb
	 * @param kfdbProducts
	 */
	public void saveYkrk(Ykrk ykrk,List ykrkProducts)
	{
		String sql = "insert into ykrk(rk_date,jsr,rk_store_id,czr,state,remark,cz_date,id) values(?,?,?,?,?,?,now(),?)";
		
		Object[] param = new Object[7];
		
		param[0] = ykrk.getRk_date();
		param[1] = ykrk.getJsr();
		param[2] = ykrk.getRk_store_id();		 
		param[3] = ykrk.getCzr();
		param[4] = ykrk.getState();
		param[5] = ykrk.getRemark();
		param[6] = ykrk.getId();		
		this.getJdbcTemplate().update(sql,param);
		
		this.addYkrkProducts(ykrkProducts, ykrk.getId());
	}
	
	public void updateYkrk(Ykrk ykrk,List ykrkProducts){
		String sql = "update ykrk set rk_date=?,jsr=?,rk_store_id=?,czr=?,state=?,remark=?,cz_date=now() where id=?";
		
		Object[] param = new Object[7];
		
		param[0] = ykrk.getRk_date();
		param[1] = ykrk.getJsr();
		param[2] = ykrk.getRk_store_id();
		 
		param[3] = ykrk.getCzr();
		param[4] = ykrk.getState();
		param[5] = ykrk.getRemark();
		param[6] = ykrk.getId();
		
		this.getJdbcTemplate().update(sql,param);
		
		this.delYkrkProducts(ykrk.getId());
		
		this.addYkrkProducts(ykrkProducts, ykrk.getId());
	}
	
	private void delYkrkProducts(String ykrk_id){
		String sql = "delete from ykrk_product where ykrk_id='" + ykrk_id + "'";
		
		this.getJdbcTemplate().update(sql);
	}
	
	public boolean isDbFinish(String id) {
		boolean is = false;
		String sql = "select count(*) from ykrk where id='" + id + "' and state='已提交'";
		int counts = this.getJdbcTemplate().queryForInt(sql);
		if(counts > 0){
			is = true;
		}
		return is;
	}
	public void delYkrk(String id){
		String sql = "delete from ykrk where id='" + id + "'";
		this.getJdbcTemplate().update(sql);
		
		sql = "delete from ykrk_product where ykrk_id='" + id + "'";
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * 添加移库出库相关联产品
	 * @param lsdProducts
	 * @param lsd_id
	 */
	private void addYkrkProducts(List YkrkProducts,String ykrk_id){
		String sql = "";
		Object[] param = new Object[7];
		
		if(YkrkProducts != null && YkrkProducts.size()>0){
			for(int i=0;i<YkrkProducts.size();i++){
				YkrkProduct ykrkProduct = (YkrkProduct)YkrkProducts.get(i);
				if(ykrkProduct != null){
					if( !ykrkProduct.getProduct_name().equals("")){
						sql = "insert into ykrk_product(ykrk_id,product_id,product_xh,product_name,product_remark,nums,qz_serial_num) values(?,?,?,?,?,?,?)";
						
						param[0] = ykrk_id;
						param[1] = ykrkProduct.getProduct_id();
						param[2] = ykrkProduct.getProduct_xh();
						param[3] = ykrkProduct.getProduct_name();
						param[4] = ykrkProduct.getProduct_remark();
						param[5] = ykrkProduct.getNums();						
						param[6] = ykrkProduct.getQz_serial_num();						
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
	public Object getYkrk(String id){
		String sql = "select * from ykrk where id='" + id + "'";
		return this.getJdbcTemplate().queryForObject(sql, new YkrkRowMapper());
	}
	
	public List getykrkProducts(String id){
		String sql = "select a.*,b.qz_serial_num as qz_flag,b.dw from ykrk_product a left join product b on b.product_id=a.product_id where a.ykrk_id='" + id + "'";
		return this.getResultList(sql);
	}
	
	
	
	public String getYkrkID() {
		String sql = "select ykrkid from cms_all_seq";

		// 取当前序列号
		String curId = this.getJdbcTemplate().queryForInt(sql) + "";
		
		String day = DateComFunc.getCurDay();

		// 将序列号加1
		sql = "update cms_all_seq set ykrkid=ykrkid+1";
		this.getJdbcTemplate().update(sql);

		for (int i = curId.length(); i < 3; i++) {
			curId = "0" + curId;
		}

		return "YR" + day + "-" + curId;
	}
	
	class YkrkRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			Ykrk ykrk = new Ykrk();

			ykrk.setId(rs.getString("id"));
			ykrk.setRk_date(rs.getString("rk_date"));
			ykrk.setJsr(rs.getString("jsr"));
			ykrk.setRk_store_id(rs.getString("rk_store_id"));			 			 
			ykrk.setCzr(rs.getString("czr"));
			ykrk.setState(rs.getString("state"));
			ykrk.setRemark(rs.getString("remark"));
			
			return ykrk;
		}
	}	
	
	 
	class YkrkProductRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			YkrkProduct ykrkProduct = new YkrkProduct();

			if(SqlUtil.columnIsExist(rs,"ykrk_id")) ykrkProduct.setYkrk_id(rs.getString("ykrk_id"));
			if(SqlUtil.columnIsExist(rs,"product_id")) ykrkProduct.setProduct_id(rs.getString("product_id"));
			if(SqlUtil.columnIsExist(rs,"product_name")) ykrkProduct.setProduct_name(rs.getString("product_name"));
			if(SqlUtil.columnIsExist(rs,"product_xh")) ykrkProduct.setProduct_xh(rs.getString("product_xh"));
			if(SqlUtil.columnIsExist(rs,"nums")) ykrkProduct.setNums(rs.getInt("nums"));
			if(SqlUtil.columnIsExist(rs,"product_remark")) ykrkProduct.setProduct_remark(rs.getString("product_remark"));
			if(SqlUtil.columnIsExist(rs,"qz_serial_num")) ykrkProduct.setQz_serial_num(rs.getString("qz_serial_num"));
			
			return ykrkProduct;
		}
	}	
}
