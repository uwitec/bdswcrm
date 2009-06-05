package com.sw.cms.service;

import java.util.List;

import com.sw.cms.dao.ProductKindDAO;
import com.sw.cms.model.ProductKind;

public class ProductKindService {
	
	private ProductKindDAO productKindDao;
	
	/**
	 * 取所有产品类别列表
	 * @return
	 */
	public List getAllProductKindList(){
		return productKindDao.getAllProductKindList();
	}
	
	/**
	 * 根据ID取产品类别
	 * @param id
	 * @return
	 */
	public Object getProductKindInfoById(String id){
		return productKindDao.getProductKindInfoById(id);
	}
	
	
	/**
	 * 添加产品信息
	 * @param productKind
	 */
	public void saveProductKind(ProductKind productKind){
		productKindDao.saveProductKind(productKind);
	}
	
	
	
	/**
	 * 更新产品信息
	 * @param productKind
	 */
	public void updateProductKind(ProductKind productKind){
		productKindDao.updateProductKind(productKind);
	}
	
	
	/**
	 * 返回子类别数量
	 * @param id
	 * @return
	 */
	public int getChildKindCount(String id){
		return productKindDao.getChildKindCount(id);
	}
	
	/**
	 * 返回类别下子产品的数量
	 * @param id
	 * @return
	 */
	public int getChildProductCount(String id){
		return productKindDao.getChildProductCount(id);
	}
	
	/**
	 * 删除产品分类信息
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
