package com.sw.cms.service;

import java.util.List;

import com.sw.cms.dao.XsmxReportDAO;

public class XsmxReportService {
	
	private XsmxReportDAO xsmxReportDao;
	
	/**
	 * ȡ���۵��б�
	 * @param start_date ��ʼʱ��
	 * @param end_date  ��ֹʱ��
	 * @param client_id  �ͻ�����
	 * @return
	 */
	public List getXsdList(String start_date,String end_date,String client_name,String xsry_id,String dj_id,String dept_id){
		return xsmxReportDao.getXsdList(start_date, end_date, client_name,xsry_id,dj_id,dept_id);
	}
	
	
	/**
	 * �������۵����ȡ������ϸ��Ϣ
	 * @param xsd_id
	 * @return
	 */
	public List getXsdMxList(String xsd_id){
		return xsmxReportDao.getXsdMxList(xsd_id);
	}
	
	
	/**
	 * ���ݲ�ѯ����ȡ���۵��б�
	 * @param start_date ��ʼʱ��
	 * @param end_date  ��ֹʱ��
	 * @param client_id  �ͻ�����
	 * @return
	 */
	public List getLsdList(String start_date,String end_date,String client_name,String xsry_id,String dj_id,String dept_id){
		return xsmxReportDao.getLsdList(start_date, end_date, client_name,xsry_id,dj_id,dept_id);
	}
	
	
	/**
	 * �������۵����ȡ���۵�Ԫ������ϸ
	 * @param lsd_id
	 * @return
	 */
	public List getLsdMxList(String lsd_id){
		return xsmxReportDao.getLsdMxList(lsd_id);
	}
	
	
	/**
	 * ȡ�˻����б�
	 * @param start_date
	 * @param end_date
	 * @param client_name
	 * @return
	 */
	public List getThdList(String start_date,String end_date,String client_name,String xsry_id,String dj_id,String dept_id){
		return xsmxReportDao.getThdList(start_date, end_date, client_name,xsry_id,dj_id,dept_id);
	}
	
	
	/**
	 * �����˻������ȡ�˻�����ϸ
	 * @param thd_id
	 * @return
	 */
	public List getThdMxList(String thd_id){
		return xsmxReportDao.getThdMxList(thd_id);
	}
	
	
	/**
	 * ȡδ�յ����б�
	 * @param start_date ��ʼʱ��
	 * @param end_date ����ʱ��
	 * @param dept_id ���ű��
	 * @param xsry_id ������Ա
	 * @param khjl �ͻ�����
	 * @return
	 */
	public List getWsdjKhjlList(String start_date,String end_date,String dept_id,String xsry_id,String khjl,String client_name,String cq_flag){
		return xsmxReportDao.getWsdjKhjlList(start_date, end_date, dept_id, xsry_id,khjl,client_name,cq_flag);
	}

	/**
	 * ȡδ�յ����б�
	 * @param start_date ��ʼʱ��
	 * @param end_date ����ʱ��
	 * @param dept_id ���ű��
	 * @param xsry_id ������Ա
	 * 
	 * @return
	 */
	public List getWsdjList(String start_date,String end_date,String dept_id,String xsry_id,String client_name,String cq_flag){
		return xsmxReportDao.getWsdjList(start_date, end_date, dept_id, xsry_id,client_name,cq_flag);
	}
	
	public XsmxReportDAO getXsmxReportDao() {
		return xsmxReportDao;
	}

	public void setXsmxReportDao(XsmxReportDAO xsmxReportDao) {
		this.xsmxReportDao = xsmxReportDao;
	}

}
