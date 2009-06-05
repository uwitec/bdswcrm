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
	public List getXsdList(String start_date,String end_date,String client_name,String xsry_id,String dj_id){
		return xsdHzDao.getXsdList(start_date, end_date, client_name, xsry_id, dj_id);
	}

	public XsdHzDAO getXsdHzDao() {
		return xsdHzDao;
	}

	public void setXsdHzDao(XsdHzDAO xsdHzDao) {
		this.xsdHzDao = xsdHzDao;
	}
	

}
