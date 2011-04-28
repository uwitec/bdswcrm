package com.sw.cms.service;

import java.util.List;

import com.sw.cms.dao.ProductSerialNumXsHzDAO;

public class ProductSerialNumXsHzService {
	
	private ProductSerialNumXsHzDAO productSerialNumXsHzDAO =  new ProductSerialNumXsHzDAO();
	
	/**
	 * 根据查询条件汇总商吕销售序列号
	 * @param start_date  开始时间
	 * @param end_date    结束时间
	 * @param product_kind 商品类别
	 * @param product_name 商品名称
	 * @param dept_id      部门编号
	 * @param xsry_id      销售人员编号
	 * @param clientId      客户名称
	 * @return
	 */
	public List getSerialNumXsList(String start_date,String end_date,String product_kind,
								   String product_name,String dept_id,String xsry_name,String clientId){
		return productSerialNumXsHzDAO.getSerialNumXsList(start_date, end_date, product_kind, product_name, dept_id, xsry_name,clientId);
	}

	public ProductSerialNumXsHzDAO getProductSerialNumXsHzDAO() {
		return productSerialNumXsHzDAO;
	}

	public void setProductSerialNumXsHzDAO(
			ProductSerialNumXsHzDAO productSerialNumXsHzDAO) {
		this.productSerialNumXsHzDAO = productSerialNumXsHzDAO;
	}

}
