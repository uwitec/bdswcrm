package com.sw.cms.xml.productkc;

import java.util.List;

public class ProductKcQc {
	
	private String cdate;     //�������
	private String storeId; //�ֿ���
	
	private List<ProductKc> products;  //�����Ʒ��ϸ

	public String getCdate() {
		return cdate;
	}

	public void setCdate(String cdate) {
		this.cdate = cdate;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public List<ProductKc> getProducts() {
		return products;
	}

	public void setProducts(List<ProductKc> products) {
		this.products = products;
	}

}
