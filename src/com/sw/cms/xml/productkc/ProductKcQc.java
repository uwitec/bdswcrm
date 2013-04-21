package com.sw.cms.xml.productkc;

import java.util.List;

public class ProductKcQc {
	
	private String cdate;     //¿â´æÈÕÆÚ
	private String storeId; //²Ö¿â±àºÅ
	
	private List<ProductKc> products;  //¿â´æÉÌÆ·Ã÷Ï¸

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
