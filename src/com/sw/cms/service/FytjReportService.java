package com.sw.cms.service;

import java.util.List;

import com.sw.cms.dao.FytjReportDAO;

public class FytjReportService {
	
	private FytjReportDAO fytjReportDao;
	
	/**
	 * 取费用统计信息
	 * @param start_date 开始时间
	 * @param end_date   结束时间
	 * @param ywy        业务员
	 * @param dept       部门
	 * @param fy_type    费用类别
	 * @return
	 */
	public List getFytjList(String start_date,String end_date,String ywy,String dept,String fy_type){
		return fytjReportDao.getFytjList(start_date, end_date, ywy, dept, fy_type);
	}

	public FytjReportDAO getFytjReportDao() {
		return fytjReportDao;
	}

	public void setFytjReportDao(FytjReportDAO fytjReportDao) {
		this.fytjReportDao = fytjReportDao;
	}

}
