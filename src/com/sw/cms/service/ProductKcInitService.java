package com.sw.cms.service;

import java.util.List;

import com.sw.cms.dao.ProductKcInitDAO;
import com.sw.cms.model.ProductKcInit;

public class ProductKcInitService {
	
	private ProductKcInitDAO productKcInitDao;
	
	/**
	 * 取所有库存初始库房列表
	 * @return
	 */
	public List getProductKcInitList(){
		return productKcInitDao.getProductKcInitList();
	}
	
	
	/**
	 * 保存初始化信息
	 * @param productKcInit
	 */
	public void saveProductKcInit(ProductKcInit productKcInit){
		productKcInitDao.saveProductKcInit(productKcInit);
	}
	
	/**
	 * 取库存初始
	 * @param store_id
	 * @return
	 */
	public ProductKcInit getProductKcInit(String store_id){
		return productKcInitDao.getProductKcInit(store_id);
	}
	
	
	/**
	 * 判断库房初始是否存在
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
