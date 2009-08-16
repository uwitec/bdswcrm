package com.sw.cms.service;

import java.util.List;

import com.sw.cms.dao.SysInitSetDAO;
import com.sw.cms.dao.XsmxReportDAO;
import com.sw.cms.dao.YsmxReportDAO;

public class YsmxReportService {
	
	private YsmxReportDAO ysmxReprotDao;
	private SysInitSetDAO sysInitSetDao;
	private XsmxReportDAO xsmxReportDao;
	
	
	/**
	 * ���ݿͻ���ż�����ȡ�ڳ���
	 * @param client_name
	 * @param cdate
	 * @return  �ڳ�Ӧ�ն���޷���0
	 */
	public double getQc(String client_name,String cdate){
		String xtqyrq = sysInitSetDao.getSysInitSet().getQyrq(); //ϵͳ��������
		if(xtqyrq == null){
			xtqyrq = "";
		}
		
		//����û�ѡ������С��ϵͳ��������
		if(cdate.compareTo(xtqyrq)<0){
			cdate = xtqyrq;
		}
		return ysmxReprotDao.getQc(client_name, cdate);
	}
	
	
	/**
	 * ���ݿͻ���ż���ʼ����ȡ���ڷ�����
	 * @param client_name
	 * @param start_date
	 * @param end_date
	 * @return
	 */
	public double getBqfs(String client_name,String start_date,String end_date){
		return ysmxReprotDao.getBqfs(client_name, start_date, end_date);
	}
	
	
	/**
	 * ���ݿͻ���ż���ʼ����ȡ��������
	 * @param client_name
	 * @param start_date
	 * @param end_date
	 * @return
	 */
	public double getBqyishou(String client_name,String start_date,String end_date){
		return ysmxReprotDao.getBqyishou(client_name, start_date, end_date);
	}
	
	/**
	 * ��������ȡ������ϸ�б�
	 * @param client_name
	 * @param start_date
	 * @param end_date
	 * @return
	 */
	public List getYsMxList(String client_name,String start_date,String end_date){
		return ysmxReprotDao.getYsMxList(client_name, start_date, end_date);
	}
	
	
	/**
	 * ȡδ�յ����б�
	 * @param start_date ��ʼʱ��
	 * @param end_date ����ʱ��
	 * @param dept_id ���ű��
	 * @param xsry_id ������Ա
	 * @return
	 */
	public List getWsdjList(String start_date,String end_date,String dept_id,String xsry_id,String client_name){
		return xsmxReportDao.getWsdjList(start_date, end_date, dept_id, xsry_id,client_name,"");
	}
	
	
	/**
	 * ȡ�ͻ�Ԥ�տ�ϼ�ֵ
	 * @param client_name
	 * @param start_date
	 * @param end_date
	 * @return
	 */
	public double getClientYushoukHj(String client_name,String start_date,String end_date){
		return ysmxReprotDao.getClientYushoukHj(client_name, start_date, end_date);
	}
	
	public YsmxReportDAO getYsmxReprotDao() {
		return ysmxReprotDao;
	}

	public void setYsmxReprotDao(YsmxReportDAO ysmxReprotDao) {
		this.ysmxReprotDao = ysmxReprotDao;
	}


	public SysInitSetDAO getSysInitSetDao() {
		return sysInitSetDao;
	}


	public void setSysInitSetDao(SysInitSetDAO sysInitSetDao) {
		this.sysInitSetDao = sysInitSetDao;
	}


	public XsmxReportDAO getXsmxReportDao() {
		return xsmxReportDao;
	}


	public void setXsmxReportDao(XsmxReportDAO xsmxReportDao) {
		this.xsmxReportDao = xsmxReportDao;
	}

}
