package com.sw.cms.service;

import java.util.List;

import com.sw.cms.dao.ProductSerialNumCgHzDAO;
import com.sw.cms.dao.RkdDAO;
import com.sw.cms.dao.UserDAO;

public class ProductSerialNumCgHzService {
	
	private ProductSerialNumCgHzDAO productSerialNumCgHzDAO =  new ProductSerialNumCgHzDAO();
	private UserDAO userDao;
	/**
	 * ���ݲ�ѯ����������Ʒ�ɹ����к�
	 * @param start_date  ��ʼʱ��
	 * @param end_date    ����ʱ��
	 * @param product_kind ��Ʒ���
	 * @param product_name ��Ʒ����
	 * @param dept_id      ���ű��
	 * @param xsry_id      ������Ա���
	 * @param clientId      �ͻ�����
	 * @return
	 */
	public List getSerialNumCgList(String start_date,String end_date,String product_kind,
								   String product_name,String dept_id,String xsry_name,String clientId){
		
		return productSerialNumCgHzDAO.getSerialNumCgList(start_date, end_date, product_kind, product_name, dept_id, userDao.getRealNameById(xsry_name),clientId);
	}

	public ProductSerialNumCgHzDAO getProductSerialNumCgHzDAO() {
		return productSerialNumCgHzDAO;
	}

	public void setProductSerialNumCgHzDAO(
			ProductSerialNumCgHzDAO productSerialNumCgHzDAO) {
		this.productSerialNumCgHzDAO = productSerialNumCgHzDAO;
	}

	public UserDAO getUserDao() {
		return userDao;
	}

	public void setUserDao(
			UserDAO userDao) {
		this.userDao = userDao;
	}
}
