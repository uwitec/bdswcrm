package com.sw.cms.service;

import java.util.HashMap;
import java.util.List;

import com.sw.cms.dao.XstjXsryDAO;

public class XstjXsryService {
	
	private XstjXsryDAO xstjXsryDao;
	
	/**
	 * ȡ���۵��б�
	 * @param start_date
	 * @param end_date
	 * @param xsry_id
	 * @return
	 */
	public List getXsdList(String start_date,String end_date,String xsry_id,String client_name,String dj_id){
		return xstjXsryDao.getXsdList(start_date, end_date, xsry_id,client_name,dj_id);
	}
	
	
	/**
	 * �������۵����ȡ������ϸ��Ϣ
	 * @param xsd_id
	 * @return
	 */
	public List getXsdMxList(String xsd_id){
		return xstjXsryDao.getXsdMxList(xsd_id);
	}
	
	
	/**
	 * ���ݲ�ѯ����ȡ���۵��б�
	 * @param start_date ��ʼʱ��
	 * @param end_date  ��ֹʱ��
	 * @param client_id  �ͻ�����
	 * @return
	 */
	public List getLsdList(String start_date,String end_date,String xsry_id,String client_name,String dj_id){
		return xstjXsryDao.getLsdList(start_date, end_date, xsry_id,client_name,dj_id);
	}
	
	
	/**
	 * �������۵����ȡ���۵�Ԫ������ϸ
	 * @param lsd_id
	 * @return
	 */
	public List getLsdMxList(String lsd_id){
		return xstjXsryDao.getLsdMxList(lsd_id);
	}
	
	
	/**
	 * ȡ�˻����б�
	 * @param start_date
	 * @param end_date
	 * @param client_name
	 * @return
	 */
	public List getThdList(String start_date,String end_date,String xsry_id,String client_name,String dj_id){
		return xstjXsryDao.getThdList(start_date, end_date, xsry_id,client_name,dj_id);
	}
	
	
	/**
	 * �����˻������ȡ�˻�����ϸ
	 * @param thd_id
	 * @return
	 */
	public List getThdMxList(String thd_id){
		return xstjXsryDao.getThdMxList(thd_id);
	}	
	
	
	/**
	 * ҵ��Աë������(�������롢�ɱ������˳ɱ�)
	 * @param start_date
	 * @param end_date
	 * @return
	 */
	public HashMap<String,Double> getMlHz(String xsry_id,String start_date,String end_date){
		return xstjXsryDao.getMlHz(xsry_id, start_date, end_date);
	}
	
	
	/**
	 * ҵ��Ա����ë������
	 * @param start_date   ��ʼʱ��
	 * @param end_date     ����ʱ��
	 * @param dept_id      ���ű��
	 * @param user_id      ������Ա
	 * @return
	 */
	public List getYwymlHz(String start_date,String end_date,String dept_id,String user_id){
		return xstjXsryDao.getYwymlHz(start_date, end_date, dept_id, user_id);
	}
	
	
	/**
	 * ҵ��Աë��������ϸ
	 * @param start_date
	 * @param end_date
	 * @param user_id
	 * @return
	 */
	public List getYwymlMx(String start_date,String end_date,String user_id){
		return xstjXsryDao.getYwymlMx(start_date, end_date, user_id);
	}

	public XstjXsryDAO getXstjXsryDao() {
		return xstjXsryDao;
	}

	public void setXstjXsryDao(XstjXsryDAO xstjXsryDao) {
		this.xstjXsryDao = xstjXsryDao;
	}

}
