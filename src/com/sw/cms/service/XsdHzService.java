package com.sw.cms.service;

import java.util.List;

import com.sw.cms.dao.XsdHzDAO;

public class XsdHzService {
	
	private XsdHzDAO xsdHzDao;
	
	/**
	 * 取销售订单列表
	 * @param start_date 开始时间
	 * @param end_date结束时间
	 * @param client_name客户名称
	 * @param xsry_id销售人员
	 * @param dj_id单据编号
	 * @return
	 */
	public List getXsdList(String start_date,String end_date,String client_name,String dj_id){
		return xsdHzDao.getXsdList(start_date, end_date, client_name,dj_id);
	}

	
	/**
	 * 取客户销售汇总列表信息（销售单）
	 * @param start_date
	 * @param end_date
	 * @param dj_id
	 * @param client_name
	 * @return
	 */
	public List getClientXsList(String start_date,String end_date,String dj_id,String client_name){
		return xsdHzDao.getClientXsList(start_date, end_date, dj_id, client_name);
	}
	
	/**
	 * 客户销售汇总单据列表(采购单)
	 * @param start_date
	 * @param end_date
	 * @param dj_id
	 * @param client_name
	 * @return
	 */
	public List getClientMxList(String start_date,String end_date,String client_name,String dj_id){
		return xsdHzDao.getClientMxList(start_date, end_date, client_name, dj_id);
	}
	
	/**
	 * 取单据商品明细
	 * @param dj_id
	 * @param xwtype
	 * @return
	 */
	public List getDjmxList(String dj_id){
		return xsdHzDao.getDjmxList(dj_id);
	}
	
	/**
	 * 取货品销售汇总
	 * @param product_id
	 * @param start_date
	 * @param end_date
	 * @param product_name
	 * @param product_kind
	 * @return
	 */
	public List getHpxsList(String product_kind,String start_date,String end_date,String product_name,String product_xh){
		return xsdHzDao.getHpxsList(product_kind, start_date, end_date,product_name, product_xh);
	}
	
	/**
	 * 取销售汇总单据明细（销售单）
	 * @param proudct_id
	 * @param start_date
	 * @param end_date
	 * 
	 * @return
	 */
	public List getHpDjmxList(String proudct_id,String start_date,String end_date){
		return xsdHzDao.getHpDjmxList(proudct_id, start_date, end_date);
	}
	
	public XsdHzDAO getXsdHzDao() {
		return xsdHzDao;
	}

	public void setXsdHzDao(XsdHzDAO xsdHzDao) {
		this.xsdHzDao = xsdHzDao;
	}
	

}
