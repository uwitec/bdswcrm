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
	public List getFytjList(String start_date,String end_date,String dept,String fy_type){
		return fytjReportDao.getFytjList(start_date, end_date, dept, fy_type);
	}
	
	
	/**
	 * 费用统计分类汇总
	 */
	public List getFytjResult(String start_date,String end_date,String dept,int dj){
		return fytjReportDao.getFytjResult(start_date, end_date, dept, dj);
	}
	
	
	/**
	 * 部门费用汇总
	 * @param start_date  开始时间
	 * @param end_date    结束时间
	 * @param fy_type     费用类型
	 * @param dj          部门等级
	 * @return
	 */
	public List getDeptFytjResult(String start_date,String end_date,String fy_type,int dj){
		return fytjReportDao.getDeptFytjResult(start_date, end_date, fy_type, dj);
	}

	public FytjReportDAO getFytjReportDao() {
		return fytjReportDao;
	}

	public void setFytjReportDao(FytjReportDAO fytjReportDao) {
		this.fytjReportDao = fytjReportDao;
	}

}
