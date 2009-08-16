package com.sw.cms.service;

import java.util.List;
import java.util.Map;

import com.sw.cms.dao.ProductDAO;
import com.sw.cms.model.Page;
import com.sw.cms.model.Product;

public class ProductService {

	private ProductDAO productDao;
	
	/**
	 * 根据产品类别ID取产品 采用分页模型
	 * @param curId 类别ID
	 * @return
	 */
	public Page getProductListByID(String curId, int curPage, int rowsPerPage) {
		return productDao.getProductListByID(curId, curPage, rowsPerPage);
	}
	
	
	/**
	 * 根据查询条件取产品列表
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getProductList(String con, int curPage, int rowsPerPage) {
		return productDao.getProductList(con, curPage, rowsPerPage);
	}

	/**
	 * 第二种方式取分页对象 返回结果是包装的是Product对象
	 * 
	 * @param curId
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getProductsByID(String curId, int curPage, int rowsPerPage) {
		return productDao.getProductsByID(curId, curPage, rowsPerPage);
	}
	
	
	/**
	 * 取根据查询条取产品列表
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getProducts(String con,int curPage,int rowsPerPage){
		return productDao.getProducts(con, curPage, rowsPerPage);
	}

	/**
	 * 保存产品信息
	 * 
	 * @param product
	 */
	public void saveProductInfo(Product product) {
		productDao.saveProductInfo(product);
	}

	/**
	 * 修改产品信息
	 * 
	 * @param product
	 */
	public void updateProductInfo(Product product) {
		productDao.updateProductInfo(product);
	}

	/**
	 * 根据产品系统编号取产品详细信息
	 * 
	 * @param productId
	 * @return Map
	 */
	public Map getProductInfoById(String productId) {
		return productDao.getProductInfoById(productId);
	}

	/**
	 * 根据产品系统编号取产品信息
	 * 
	 * @param productId
	 * @return Product对象
	 */
	public Object getProductById(String productId) {
		return productDao.getProductById(productId);
	}
	
	
	/**
	 * 删除产品信息
	 * @param productId
	 */
	public void delProductById(String productId){
		productDao.delProductById(productId);
	}
	
	
	/**
	 * 根据产品编号取分销限价
	 * @param product_id
	 * @return
	 */
	public double getProductFxxj(String product_id){
		return productDao.getProductFxxj(product_id);
	}
	
	
	/**
	 * 根据产品编号取零售限价
	 * @param product_id
	 * @return
	 */
	public double getProductLsxj(String product_id){
		return productDao.getProductLsxj(product_id);
	}
	
	
	/**
	 * 根据条件查询所有产品列表
	 * @param product_kind  产品类别
	 * @param product_name  产品名称
	 * @param product_xh    产品规格
	 * @return
	 */
	public List getProductByCon(String product_kind,String product_name,String product_xh,String state){
		return productDao.getProductByCon(product_kind, product_name, product_xh, state);
	}
	
	
	/**
	 * 判断商品是否可以删除<BR>
	 * 发生业务数据的商品不能删除<BR>
	 * 业务数据包括：零售、销售、退货、采购、采购退货、调拨申请、调拨、调价<BR>
	 * 因为出库入库，不能添加，只能有相应单据生成，所以不在考虑范围内
	 * @param product_id  商品编号
	 * @return boolean true:可以；false:不可以
	 */
	public boolean isCanDel(String product_id){
		return productDao.isCanDel(product_id);
	}
	

	public ProductDAO getProductDao() {
		return productDao;
	}

	public void setProductDao(ProductDAO productDao) {
		this.productDao = productDao;
	}

	
}
