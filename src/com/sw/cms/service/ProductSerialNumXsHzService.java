package com.sw.cms.service;

import java.util.List;

import com.sw.cms.dao.ProductSerialNumXsHzDAO;

public class ProductSerialNumXsHzService {
	
	private ProductSerialNumXsHzDAO productSerialNumXsHzDAO =  new ProductSerialNumXsHzDAO();
	
	/**
	 * ���ݲ�ѯ�������������������к�
	 * @param start_date  ��ʼʱ��
	 * @param end_date    ����ʱ��
	 * @param product_kind ��Ʒ���
	 * @param product_name ��Ʒ����
	 * @param dept_id      ���ű��
	 * @param xsry_id      ������Ա���
	 * @param clientId      �ͻ�����
	 * @return
	 */
	public List getSerialNumXsList(String start_date,String end_date,String product_kind,
								   String product_name,String dept_id,String xsry_name,String clientId){
		return productSerialNumXsHzDAO.getSerialNumXsList(start_date, end_date, product_kind, product_name, dept_id, xsry_name,clientId);
	}

	public ProductSerialNumXsHzDAO getProductSerialNumXsHzDAO() {
		return productSerialNumXsHzDAO;
	}

	public void setProductSerialNumXsHzDAO(
			ProductSerialNumXsHzDAO productSerialNumXsHzDAO) {
		this.productSerialNumXsHzDAO = productSerialNumXsHzDAO;
	}

}
