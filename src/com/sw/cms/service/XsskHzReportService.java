package com.sw.cms.service;

import java.util.List;

import com.sw.cms.dao.XsskHzReportDAO;

public class XsskHzReportService {

	private XsskHzReportDAO xsskHzReportDao;
	
	/**
	 * 取销售收款明细信息
	 * @param start_date
	 * @param end_date
	 * @param client_name
	 * @param jsr
	 * @param isYsk
	 * @param account_id
	 * @return
	 */
	public List getXsskMx(String start_date,String end_date,String client_name,String jsr,String isYsk,String account_id){
		return xsskHzReportDao.getXsskMx(start_date, end_date, client_name, jsr, isYsk, account_id);
	}

	public XsskHzReportDAO getXsskHzReportDao() {
		return xsskHzReportDao;
	}

	public void setXsskHzReportDao(XsskHzReportDAO xsskHzReportDao) {
		this.xsskHzReportDao = xsskHzReportDao;
	}
	
}
