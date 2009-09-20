package com.sw.cms.dao;

import java.util.List;
import java.util.Map;

import com.sw.cms.dao.base.JdbcBaseDAO;

public class CbtzDAO extends JdbcBaseDAO {
	
	
	/**
	 * 返回单据对应的商品列表
	 * @param dj_id
	 * @param dj_type
	 * @return
	 */
	public List getProductList(String dj_id,String dj_type){
		String sql = "";
		if(dj_type.equals("销售订单")){
			sql = "select * from xsd_product where xsd_id='" + dj_id + "'";
		}else{
			sql = "select * from lsd_product where lsd_id='" + dj_id + "'";
		}
		
		return this.getResultList(sql);
	}
	
	
	/**
	 * 编辑成本信息
	 * @param id
	 * @param dj_type
	 * @return
	 */
	public Map getProduct(String id,String dj_type){
		String sql = "";
		if(dj_type.equals("xs")){
			sql = "select * from xsd_product where id='" + id + "'";
		}else{
			sql = "select * from lsd_product where id='" + id + "'";
		}
		
		return this.getResultMap(sql);
	}
	
	
	/**
	 * 更新考核成本
	 * @param id
	 * @param dj_type
	 * @param kh_cbj
	 */
	public void updateProduct(String id,String dj_type,double kh_cbj){
		String sql = "";
		if(dj_type.equals("xs")){
			sql = "update xsd_product set kh_cbj=" + kh_cbj + " where id='" + id + "'";
		}else{
			sql = "update lsd_product set kh_cbj=" + kh_cbj + " where id='" + id + "'";
		}
		
		this.getJdbcTemplate().update(sql);
	}

}
