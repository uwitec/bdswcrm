package com.sw.cms.service;

import java.util.List;

import com.sw.cms.dao.QtsrtjReportDAO;

public class QtsrtjReportService {
	
	private QtsrtjReportDAO qtsrtjReportDao;
	
	/**
	 * 取收入统计信息
	 * @param start_date 开始时间
	 * @param end_date   结束时间
	 
	 * @param sr_type    收入类别
	 * @return
	 */
	public List getQtsrtjList(String start_date,String end_date,String srlx){
		return qtsrtjReportDao.getQtsrtjList(start_date, end_date, srlx);
	}
	
	
	/**
	 * 收入统计分类汇总
	 */
	public List getQtsrtjResult(String start_date,String end_date,String srlx){
		return qtsrtjReportDao.getQtsrtjResult(start_date, end_date,srlx);
	}
	
	
	public QtsrtjReportDAO getQtsrtjReportDao() {
		return qtsrtjReportDao;
	}

	public void setQtsrtjReportDao(QtsrtjReportDAO qtsrtjReportDao) {
		this.qtsrtjReportDao = qtsrtjReportDao;
	}

}
