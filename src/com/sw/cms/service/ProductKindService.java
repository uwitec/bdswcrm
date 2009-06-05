package com.sw.cms.service;

import java.util.List;

import com.sw.cms.dao.ProductKindDAO;
import com.sw.cms.model.ProductKind;

public class ProductKindService {
	
	private ProductKindDAO productKindDao;
	
	/**
	 * ȡ���в�Ʒ����б�
	 * @return
	 */
	public List getAllProductKindList(){
		return productKindDao.getAllProductKindList();
	}
	
	/**
	 * ����IDȡ��Ʒ���
	 * @param id
	 * @return
	 */
	public Object getProductKindInfoById(String id){
		return productKindDao.getProductKindInfoById(id);
	}
	
	
	/**
	 * ��Ӳ�Ʒ��Ϣ
	 * @param productKind
	 */
	public void saveProductKind(ProductKind productKind){
		productKindDao.saveProductKind(productKind);
	}
	
	
	
	/**
	 * ���²�Ʒ��Ϣ
	 * @param productKind
	 */
	public void updateProductKind(ProductKind productKind){
		productKindDao.updateProductKind(productKind);
	}
	
	
	/**
	 * �������������
	 * @param id
	 * @return
	 */
	public int getChildKindCount(String id){
		return productKindDao.getChildKindCount(id);
	}
	
	/**
	 * ����������Ӳ�Ʒ������
	 * @param id
	 * @return
	 */
	public int getChildProductCount(String id){
		return productKindDao.getChildProductCount(id);
	}
	
	/**
	 * ɾ����Ʒ������Ϣ
	 * @param id
	 */
	public void delProductKind(String id){
		productKindDao.delProductKind(id);
	}

	public ProductKindDAO getProductKindDao() {
		return productKindDao;
	}

	public void setProductKindDao(ProductKindDAO productKindDao) {
		this.productKindDao = productKindDao;
	}

}
