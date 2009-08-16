package com.sw.cms.service;

import java.util.List;
import java.util.Map;

import com.sw.cms.dao.ProductDAO;
import com.sw.cms.model.Page;
import com.sw.cms.model.Product;

public class ProductService {

	private ProductDAO productDao;
	
	/**
	 * ���ݲ�Ʒ���IDȡ��Ʒ ���÷�ҳģ��
	 * @param curId ���ID
	 * @return
	 */
	public Page getProductListByID(String curId, int curPage, int rowsPerPage) {
		return productDao.getProductListByID(curId, curPage, rowsPerPage);
	}
	
	
	/**
	 * ���ݲ�ѯ����ȡ��Ʒ�б�
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getProductList(String con, int curPage, int rowsPerPage) {
		return productDao.getProductList(con, curPage, rowsPerPage);
	}

	/**
	 * �ڶ��ַ�ʽȡ��ҳ���� ���ؽ���ǰ�װ����Product����
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
	 * ȡ���ݲ�ѯ��ȡ��Ʒ�б�
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getProducts(String con,int curPage,int rowsPerPage){
		return productDao.getProducts(con, curPage, rowsPerPage);
	}

	/**
	 * �����Ʒ��Ϣ
	 * 
	 * @param product
	 */
	public void saveProductInfo(Product product) {
		productDao.saveProductInfo(product);
	}

	/**
	 * �޸Ĳ�Ʒ��Ϣ
	 * 
	 * @param product
	 */
	public void updateProductInfo(Product product) {
		productDao.updateProductInfo(product);
	}

	/**
	 * ���ݲ�Ʒϵͳ���ȡ��Ʒ��ϸ��Ϣ
	 * 
	 * @param productId
	 * @return Map
	 */
	public Map getProductInfoById(String productId) {
		return productDao.getProductInfoById(productId);
	}

	/**
	 * ���ݲ�Ʒϵͳ���ȡ��Ʒ��Ϣ
	 * 
	 * @param productId
	 * @return Product����
	 */
	public Object getProductById(String productId) {
		return productDao.getProductById(productId);
	}
	
	
	/**
	 * ɾ����Ʒ��Ϣ
	 * @param productId
	 */
	public void delProductById(String productId){
		productDao.delProductById(productId);
	}
	
	
	/**
	 * ���ݲ�Ʒ���ȡ�����޼�
	 * @param product_id
	 * @return
	 */
	public double getProductFxxj(String product_id){
		return productDao.getProductFxxj(product_id);
	}
	
	
	/**
	 * ���ݲ�Ʒ���ȡ�����޼�
	 * @param product_id
	 * @return
	 */
	public double getProductLsxj(String product_id){
		return productDao.getProductLsxj(product_id);
	}
	
	
	/**
	 * ����������ѯ���в�Ʒ�б�
	 * @param product_kind  ��Ʒ���
	 * @param product_name  ��Ʒ����
	 * @param product_xh    ��Ʒ���
	 * @return
	 */
	public List getProductByCon(String product_kind,String product_name,String product_xh,String state){
		return productDao.getProductByCon(product_kind, product_name, product_xh, state);
	}
	
	
	/**
	 * �ж���Ʒ�Ƿ����ɾ��<BR>
	 * ����ҵ�����ݵ���Ʒ����ɾ��<BR>
	 * ҵ�����ݰ��������ۡ����ۡ��˻����ɹ����ɹ��˻����������롢����������<BR>
	 * ��Ϊ������⣬������ӣ�ֻ������Ӧ�������ɣ����Բ��ڿ��Ƿ�Χ��
	 * @param product_id  ��Ʒ���
	 * @return boolean true:���ԣ�false:������
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
