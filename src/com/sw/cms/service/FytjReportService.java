package com.sw.cms.service;

import java.util.List;

import com.sw.cms.dao.FytjReportDAO;

public class FytjReportService {
	
	private FytjReportDAO fytjReportDao;
	
	/**
	 * ȡ����ͳ����Ϣ
	 * @param start_date ��ʼʱ��
	 * @param end_date   ����ʱ��
	 * @param ywy        ҵ��Ա
	 * @param dept       ����
	 * @param fy_type    �������
	 * @return
	 */
	public List getFytjList(String start_date,String end_date,String dept,String fy_type){
		return fytjReportDao.getFytjList(start_date, end_date, dept, fy_type);
	}
	
	
	/**
	 * ����ͳ�Ʒ������
	 */
	public List getFytjResult(String start_date,String end_date,String dept,int dj){
		return fytjReportDao.getFytjResult(start_date, end_date, dept, dj);
	}
	

	public FytjReportDAO getFytjReportDao() {
		return fytjReportDao;
	}

	public void setFytjReportDao(FytjReportDAO fytjReportDao) {
		this.fytjReportDao = fytjReportDao;
	}

}
