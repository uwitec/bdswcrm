package com.sw.cms.service;

import java.util.List;

import com.sw.cms.dao.ProductKcInitDAO;
import com.sw.cms.model.ProductKcInit;

public class ProductKcInitService {
	
	private ProductKcInitDAO productKcInitDao;
	
	/**
	 * ȡ���п���ʼ�ⷿ�б�
	 * @return
	 */
	public List getProductKcInitList(){
		return productKcInitDao.getProductKcInitList();
	}
	
	
	/**
	 * �����ʼ����Ϣ
	 * @param productKcInit
	 */
	public void saveProductKcInit(ProductKcInit productKcInit){
		productKcInitDao.saveProductKcInit(productKcInit);
	}
	
	/**
	 * ȡ����ʼ
	 * @param store_id
	 * @return
	 */
	public ProductKcInit getProductKcInit(String store_id){
		return productKcInitDao.getProductKcInit(store_id);
	}
	
	
	/**
	 * �жϿⷿ��ʼ�Ƿ����
	 * @param store_id
	 * @return
	 */
	public boolean isExist(String store_id){
		return productKcInitDao.isExist(store_id);
	}

	public ProductKcInitDAO getProductKcInitDao() {
		return productKcInitDao;
	}

	public void setProductKcInitDao(ProductKcInitDAO productKcInitDao) {
		this.productKcInitDao = productKcInitDao;
	}

}
