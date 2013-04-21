package com.sw.cms.xml.productkc;

/**
 * 商品库存期初信息
 * @author liyt
 *
 */
public class ProductKc {
	
	private String productId;
	private int nums;
	private Double price;
	
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public int getNums() {
		return nums;
	}
	public void setNums(int nums) {
		this.nums = nums;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}

	
}
