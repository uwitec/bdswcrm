package com.sw.cms.service;

import java.util.List;

import com.sw.cms.dao.HpxsMlHzDAO;

public class HpxsMlHzService {
	
	private HpxsMlHzDAO hpxsMlHzDao;
	
	/**
	 * ��Ʒ�б���Ϣ
	 * @param productKind ��Ʒ������
	 * @return   ��Ʒ�б�
	 */
	public List getProductList(String productKind){
		return hpxsMlHzDao.getProductList(productKind);
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
		return hpxsMlHzDao.getHpxsmlhzTjResult(product_kind, product_name, product_xh, start_date, end_date, client_name, xsry_id);
	}
	
	
	/**
	 * ��Ʒ����ë�������б�
	 * �������۵������۵����˻���
	 * @param product_id
	 * @param start_date
	 * @param end_date
	 * @param client_name
	 * @param xsry_id
	 * @return
	 */
	public List getHpxsList(String product_id,String start_date,String end_date,String client_name,String xsry_id){
		return hpxsMlHzDao.getHpxsList(product_id, start_date, end_date, client_name, xsry_id);
	}

	
	public HpxsMlHzDAO getHpxsMlHzDao() {
		return hpxsMlHzDao;
	}

	public void setHpxsMlHzDao(HpxsMlHzDAO hpxsMlHzDao) {
		this.hpxsMlHzDao = hpxsMlHzDao;
	}

}
