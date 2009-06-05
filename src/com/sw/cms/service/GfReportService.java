package com.sw.cms.service;

import java.util.List;

import com.sw.cms.dao.GfReportDAO;

public class GfReportService {
	
	private GfReportDAO gfReportDao;
	
	/**
	 * ����ͳ������ͳ��Ա������
	 * @param start_date
	 * @param end_date
	 * @param dept_id
	 * @param xsry_id
	 * @return
	 */
	public List getGfReportByCon(String start_date,String end_date,String dept_id,String xsry_id,String flag){
		return gfReportDao.getGfReportByCon(start_date, end_date, dept_id, xsry_id,flag);
	}
	
	/**
	 * ��ȡ����ͳ����ϸ
	 * @param start_date
	 * @param end_date
	 * @param xsry_id
	 * @return
	 */
	public List getMxList(String start_date,String end_date,String xsry_id){
		return gfReportDao.getMxList(start_date, end_date, xsry_id);
	}

	public GfReportDAO getGfReportDao() {
		return gfReportDao;
	}

	public void setGfReportDao(GfReportDAO gfReportDao) {
		this.gfReportDao = gfReportDao;
	}

}
