package com.sw.cms.service;

import java.util.List;

import com.sw.cms.dao.CgfkHzReportDAO;

public class CgfkHzReportService {
	
	private CgfkHzReportDAO cgfkHzReportDao;
	
	/**
	 * 取采购付款明细列表
	 * @param start_date
	 * @param end_date
	 * @param client_name
	 * @param jsr
	 * @param isYfk
	 * @param account_id
	 * @return
	 */
	public List getCgfkMxList(String start_date,String end_date,String client_name,String jsr,String isYfk,String account_id){
		return cgfkHzReportDao.getCgfkMxList(start_date, end_date, client_name, jsr, isYfk, account_id);
	}

	public CgfkHzReportDAO getCgfkHzReportDao() {
		return cgfkHzReportDao;
	}

	public void setCgfkHzReportDao(CgfkHzReportDAO cgfkHzReportDao) {
		this.cgfkHzReportDao = cgfkHzReportDao;
	}

}
