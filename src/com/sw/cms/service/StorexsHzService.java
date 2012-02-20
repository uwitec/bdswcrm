package com.sw.cms.service;

import java.util.List;

import com.sw.cms.dao.StorexsHzDAO;

public class StorexsHzService {
	
	private StorexsHzDAO storexsHzDao;
	
	
	
	
	
	/**
	 * ����ͳ������ͳ�Ʋֿ����ۻ��ܱ�
	 * @param product_kind   ��Ʒ���
	 * @param product_name   ��Ʒ����
	 * @param product_xh     ��Ʒ���
	 * @param start_date     ��ʼʱ��
	 * @param end_date       ����ʱ��
	 * @param store_id    �ֿ���
	 * @return
	 */
	public List getStorexshzTjResult(String product_kind,String product_name,String product_xh,
			String start_date,String end_date,String store_id){
		return storexsHzDao.getStorexshzTjResult(product_kind, product_name, product_xh, start_date, end_date, store_id);
	}
	
	
	/**
	 * �ֿ����ۻ���--��ϸ��
	 * @param dept        ���ű��
	 * @param start_date  ��ʼʱ��
	 * @param end_date    ����ʱ��
	 * @param store_id �ֿ���
	 * @return
	 * @throws Exception
	 */
	public List getStoreResultMx(String store_id,String start_date,String end_date,String product_kind,String product_name,String product_xh) throws Exception{
		return storexsHzDao.getStoreResultMx(store_id, start_date, end_date, product_kind, product_name, product_xh);
	}

	/**
	 * �ֿ����ۻ���--������Ա������ϸ
	 * @param xsry
	 * @param start_date
	 * @param end_date
	 * @param store_id
	 * @return
	 * @throws Exception
	 */
	public List getStorexsProductMxResult(String xsry,String start_date,String end_date,String store_id,String product_kind,String product_name) throws Exception{
		return storexsHzDao.getStorexsProductMxResult(xsry, start_date, end_date, store_id, product_kind, product_name);
	}
	
	public StorexsHzDAO getStorexsHzDao() {
		return storexsHzDao;
	}

	public void setStorexsHzDao(StorexsHzDAO storexsHzDao) {
		this.storexsHzDao = storexsHzDao;
	}

}
