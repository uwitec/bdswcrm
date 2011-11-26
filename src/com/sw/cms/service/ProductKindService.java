package com.sw.cms.service;

import java.util.List;

import com.sw.cms.dao.ProductDAO;
import com.sw.cms.dao.ProductKindDAO;
import com.sw.cms.model.ProductKind;

public class ProductKindService {
	
	private ProductKindDAO productKindDao;
	private ProductDAO productDao;
	
	/**
	 * 取所有商品类别列表
	 * @return
	 */
	public List getAllProductKindList(){
		return productKindDao.getAllProductKindList();
	}
	
	/**
	 * 根据ID取商品类别
	 * @param id
	 * @return
	 */
	public Object getProductKindInfoById(String id){
		return productKindDao.getProductKindInfoById(id);
	}
	
	
	/**
	 * 添加商品信息
	 * @param productKind
	 */
	public void saveProductKind(ProductKind productKind){
		//添加新的商品类别
		String product_kind = productKindDao.saveProductKind(productKind);
		
		//处理类别对应的商品
		String parent_id = productKind.getParent_id();
		if(parent_id.equals("")){
			parent_id = "0";
		}
		productDao.updateProductKind(product_kind,parent_id);
	}
	
	
	
	/**
	 * 更新商品信息
	 * @param productKind
	 */
	public void updateProductKind(ProductKind productKind,String old_parent_id){
		if(productKind.getParent_id().equals(old_parent_id)){
			//如果新的parent_id与旧的parent_id一致
			productKindDao.updateProductKind(productKind);
		}else{
			//如果新的parent_id与旧的parent_id不一致
			//1、删除旧的商品类别
			productKindDao.delProductKind(productKind.getId());
			//2、添加新的商品类别
			String new_id = productKindDao.saveProductKind(productKind);
			//3、修改设计道德所有商品的类别
			productDao.updateProductKind(new_id, productKind.getId());
		}
		
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
	 * 返回类别下子商品的数量
	 * @param id
	 * @return
	 */
	public int getChildProductCount(String id){
		return productKindDao.getChildProductCount(id);
	}
	
	/**
	 * 删除商品分类信息
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
