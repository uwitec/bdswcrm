package com.sw.cms.service;

import java.util.List;

import com.sw.cms.dao.XstjClientDAO;

public class XstjClientService {
	
	private XstjClientDAO xstjClientDao;
	
	/**
	 * ȡ���۵��б�
	 * @param start_date
	 * @param end_date
	 * @param xsry_id
	 * @return
	 */
	public List getXsdList(String start_date,String end_date,String xsry_id,String client_name,String dj_id){
		return xstjClientDao.getXsdList(start_date, end_date, xsry_id, client_name,dj_id);
	}
	
	
	/**
	 * �������۵����ȡ������ϸ��Ϣ
	 * @param xsd_id
	 * @return
	 */
	public List getXsdMxList(String xsd_id){
		return xstjClientDao.getXsdMxList(xsd_id);
	}
	
	
	/**
	 * ���ݲ�ѯ����ȡ���۵��б�
	 * @param start_date ��ʼʱ��
	 * @param end_date  ��ֹʱ��
	 * @param client_id  �ͻ�����
	 * @return
	 */
	public List getLsdList(String start_date,String end_date,String xsry_id,String dj_id){
		return xstjClientDao.getLsdList(start_date, end_date, xsry_id,dj_id);
	}
	
	
	/**
	 * �������۵����ȡ���۵�Ԫ������ϸ
	 * @param lsd_id
	 * @return
	 */
	public List getLsdMxList(String lsd_id){
		return xstjClientDao.getLsdMxList(lsd_id);
	}
	
	
	/**
	 * ȡ�˻����б�
	 * @param start_date
	 * @param end_date
	 * @param client_name
	 * @return
	 */
	public List getThdList(String start_date,String end_date,String xsry_id,String client_name,String dj_id){
		return xstjClientDao.getThdList(start_date, end_date, xsry_id, client_name,dj_id);
	}
	
	
	/**
	 * �����˻������ȡ�˻�����ϸ
	 * @param thd_id
	 * @return
	 */
	public List getThdMxList(String thd_id){
		return xstjClientDao.getThdMxList(thd_id);
	}	

	/**
	 * ȡ�ͻ����ۻ����ܽ��
	 * �������۶������˻���
	 * �ܽ��=���۶����ϼƳɽ����-�˻������
	 * @param start_date
	 * @param end_date
	 * @param xsry_id
	 * @param client_name
	 * @param dj_id
	 * @return
	 */
	public double getClientZje(String start_date,String end_date,String xsry_id,String client_name,String dj_id){
		return xstjClientDao.getClientZje(start_date, end_date, xsry_id, client_name, dj_id);
	}
	
	/**
	 * �����������۵������Ϊһ����¼
	 * @param start_date
	 * @param end_date
	 * @param xsry_id
	 * @param dj_id
	 * @return
	 */
	public double getLsdZje(String start_date,String end_date,String xsry_id,String dj_id){
		return xstjClientDao.getLsdZje(start_date, end_date, xsry_id, dj_id);
	}
	
	
	/**
	 * �ͻ����ۻ���
	 * 2010-04-10
	 * @param start_date
	 * @param end_date
	 * @param client_name
	 * @param xsry_id
	 * @param dj_id
	 * @return
	 */
	public List getXstjClientResult(String start_date, String end_date,String client_name, String xsry_id, String dj_id){
		return xstjClientDao.getXstjClientResult(start_date, end_date, client_name, xsry_id, dj_id);
	}
	
	public XstjClientDAO getXstjClientDao() {
		return xstjClientDao;
	}

	public void setXstjClientDao(XstjClientDAO xstjClientDao) {
		this.xstjClientDao = xstjClientDao;
	}
	
	

}
