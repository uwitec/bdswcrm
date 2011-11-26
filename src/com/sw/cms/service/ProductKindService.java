package com.sw.cms.service;

import java.util.List;

import com.sw.cms.dao.ProductDAO;
import com.sw.cms.dao.ProductKindDAO;
import com.sw.cms.model.ProductKind;

public class ProductKindService {
	
	private ProductKindDAO productKindDao;
	private ProductDAO productDao;
	
	/**
	 * ȡ������Ʒ����б�
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
	 * �����Ʒ��Ϣ
	 * @param productKind
	 */
	public void saveProductKind(ProductKind productKind){
		//����µ���Ʒ���
		String product_kind = productKindDao.saveProductKind(productKind);
		
		//��������Ӧ����Ʒ
		String parent_id = productKind.getParent_id();
		if(parent_id.equals("")){
			parent_id = "0";
		}
		productDao.updateProductKind(product_kind,parent_id);
	}
	
	
	
	/**
	 * ������Ʒ��Ϣ
	 * @param productKind
	 */
	public void updateProductKind(ProductKind productKind,String old_parent_id){
		if(productKind.getParent_id().equals(old_parent_id)){
			//����µ�parent_id��ɵ�parent_idһ��
			productKindDao.updateProductKind(productKind);
		}else{
			//����µ�parent_id��ɵ�parent_id��һ��
			//1��ɾ���ɵ���Ʒ���
			productKindDao.delProductKind(productKind.getId());
			//2������µ���Ʒ���
			String new_id = productKindDao.saveProductKind(productKind);
			//3���޸���Ƶ���������Ʒ�����
			productDao.updateProductKind(new_id, productKind.getId());
		}
		
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
	 * �������������Ʒ������
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

	public ProductDAO getProductDao() {
		return productDao;
	}

	public void setProductDao(ProductDAO productDao) {
		this.productDao = productDao;
	}

}
