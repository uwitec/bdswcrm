package com.sw.cms.service;

import java.util.List;

import com.sw.cms.dao.QtsrtjReportDAO;

public class QtsrtjReportService {
	
	private QtsrtjReportDAO qtsrtjReportDao;
	
	/**
	 * ȡ����ͳ����Ϣ
	 * @param start_date ��ʼʱ��
	 * @param end_date   ����ʱ��
	 
	 * @param sr_type    �������
	 * @return
	 */
	public List getQtsrtjList(String start_date,String end_date,String srlx){
		return qtsrtjReportDao.getQtsrtjList(start_date, end_date, srlx);
	}
	
	
	/**
	 * ����ͳ�Ʒ������
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
