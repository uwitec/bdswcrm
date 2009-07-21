package com.sw.cms.service;

import java.util.List;

import com.sw.cms.dao.HpxsHzDAO;

public class HpxsHzService {
	
	private HpxsHzDAO hpxsHzDao;
	
	
	
	/**
	 * ����ͳ������ͳ�ƻ�Ʒ���ۻ��ܱ�
	 * @param product_kind   ��Ʒ���
	 * @param product_name   ��Ʒ����
	 * @param product_xh     ��Ʒ���
	 * @param start_date     ��ʼʱ��
	 * @param end_date       ����ʱ��
	 * @param client_name    �ͻ�����
	 * @param xsry_id        ������Ա
	 * @return
	 */
	public List getHpxshzTjResult(String product_kind,String product_name,String product_xh,
			String start_date,String end_date,String client_name,String xsry_id,String client_type){
		return hpxsHzDao.getHpxshzTjResult(product_kind, product_name, product_xh, start_date, end_date, client_name, xsry_id,client_type);
	}
	
	
	/**
	 * ����ͳ������ͳ�ƻ�Ʒ���ۻ��ܱ�(��Ӧ��)
	 * @param product_kind   ��Ʒ���
	 * @param product_name   ��Ʒ����
	 * @param product_xh     ��Ʒ���
	 * @param start_date     ��ʼʱ��
	 * @param end_date       ����ʱ��
	 * @param client_name    �ͻ�����
	 * @param xsry_id        ������Ա
	 * @return
	 */
	public List getGysHpxshzTjResult(String product_kind,String start_date,String end_date){
		return hpxsHzDao.getGysHpxshzTjResult(product_kind, start_date, end_date);
	}
	
	
	/**
	 * ����ͳ������ͳ�ƻ�Ʒë�����ۻ��ܱ�
	 * @param product_kind   ��Ʒ���
	 * @param product_name   ��Ʒ����
	 * @param product_xh     ��Ʒ���
	 * @param start_date     ��ʼʱ��
	 * @param end_date       ����ʱ��
	 * @param client_name    �ͻ�����
	 * @param xsry_id        ������Ա
	 * @return
	 */
	public List getHpxsmlhzTjResult(String product_kind,String product_name,String product_xh,
			String start_date,String end_date,String client_name,String xsry_id){
		return hpxsHzDao.getHpxsmlhzTjResult(product_kind, product_name, product_xh, start_date, end_date, client_name, xsry_id);
	}
	
	
	/**
	 * ȡ���۵��б�
	 * @param product_id
	 * @param start_date
	 * @param end_date
	 * @param client_name
	 * @param xsry_id
	 * @return
	 */
	public List getXsdList(String product_id,String start_date,String end_date,String client_name,String xsry_id,String client_type){
		return hpxsHzDao.getXsdList(product_id, start_date, end_date, client_name, xsry_id,client_type);
	}
	
	
	/**
	 * ȡ�˻����б�
	 * @param product_id
	 * @param start_date
	 * @param end_date
	 * @param client_name
	 * @param xsry_id
	 * @return
	 */
	public List getThdList(String product_id,String start_date,String end_date,String client_name,String xsry_id,String client_type){
		return hpxsHzDao.getThdList(product_id, start_date, end_date, client_name, xsry_id,client_type);
	}
	
	
	/**
	 * ȡ���۵��б�
	 * @param product_id
	 * @param start_date
	 * @param end_date
	 * @param client_name
	 * @param xsry_id
	 * @return
	 */
	public List getLsdList(String product_id,String start_date,String end_date,String client_name,String xsry_id){
		return hpxsHzDao.getLsdList(product_id, start_date, end_date, client_name, xsry_id);
	}


	public HpxsHzDAO getHpxsHzDao() {
		return hpxsHzDao;
	}

	public void setHpxsHzDao(HpxsHzDAO hpxsHzDao) {
		this.hpxsHzDao = hpxsHzDao;
	}

}
