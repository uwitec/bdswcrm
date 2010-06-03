package com.sw.cms.service;

import java.util.List;

import com.sw.cms.dao.XsdHzDAO;

public class XsdHzService {
	
	private XsdHzDAO xsdHzDao;
	
	/**
	 * ȡ���۶����б�
	 * @param start_date ��ʼʱ��
	 * @param end_date����ʱ��
	 * @param client_name�ͻ�����
	 * @param xsry_id������Ա
	 * @param dj_id���ݱ��
	 * @return
	 */
	public List getXsdList(String start_date,String end_date,String client_name,String dj_id){
		return xsdHzDao.getXsdList(start_date, end_date, client_name,dj_id);
	}

	
	/**
	 * ȡ�ͻ����ۻ����б���Ϣ�����۵���
	 * @param start_date
	 * @param end_date
	 * @param dj_id
	 * @param client_name
	 * @return
	 */
	public List getClientXsList(String start_date,String end_date,String dj_id,String client_name){
		return xsdHzDao.getClientXsList(start_date, end_date, dj_id, client_name);
	}
	
	/**
	 * �ͻ����ۻ��ܵ����б�(�ɹ���)
	 * @param start_date
	 * @param end_date
	 * @param dj_id
	 * @param client_name
	 * @return
	 */
	public List getClientMxList(String start_date,String end_date,String client_name,String dj_id){
		return xsdHzDao.getClientMxList(start_date, end_date, client_name, dj_id);
	}
	
	/**
	 * ȡ������Ʒ��ϸ
	 * @param dj_id
	 * @param xwtype
	 * @return
	 */
	public List getDjmxList(String dj_id){
		return xsdHzDao.getDjmxList(dj_id);
	}
	
	/**
	 * ȡ��Ʒ���ۻ���
	 * @param product_id
	 * @param start_date
	 * @param end_date
	 * @param product_name
	 * @param product_kind
	 * @return
	 */
	public List getHpxsList(String product_kind,String start_date,String end_date,String product_name,String product_xh){
		return xsdHzDao.getHpxsList(product_kind, start_date, end_date,product_name, product_xh);
	}
	
	/**
	 * ȡ���ۻ��ܵ�����ϸ�����۵���
	 * @param proudct_id
	 * @param start_date
	 * @param end_date
	 * 
	 * @return
	 */
	public List getHpDjmxList(String proudct_id,String start_date,String end_date){
		return xsdHzDao.getHpDjmxList(proudct_id, start_date, end_date);
	}
	
	public XsdHzDAO getXsdHzDao() {
		return xsdHzDao;
	}

	public void setXsdHzDao(XsdHzDAO xsdHzDao) {
		this.xsdHzDao = xsdHzDao;
	}
	

}
