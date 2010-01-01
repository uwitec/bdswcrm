package com.sw.cms.model;

/**
 * 拆卸单相关新产品
 * @author jinyanni
 *
 */
public class CxdProduct {
	
	private int id;
	private String cxd_id;
	private String product_id;
	private String product_name;
	private String product_xh;
	private String product_dw;
	private double price;
	private int nums;
	private double hj;
	private String remark;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCxd_id() {
		return cxd_id;
	}
	public void setCxd_id(String cxdId) {
		cxd_id = cxdId;
	}
	public String getProduct_id() {
		return product_id;
	}
	public void setProduct_id(String productId) {
		product_id = productId;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String productName) {
		product_name = productName;
	}
	public String getProduct_xh() {
		return product_xh;
	}
	public void setProduct_xh(String productXh) {
		product_xh = productXh;
	}
	public String getProduct_dw() {
		return product_dw;
	}
	public void setProduct_dw(String productDw) {
		product_dw = productDw;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getNums() {
		return nums;
	}
	public void setNums(int nums) {
		this.nums = nums;
	}
	public double getHj() {
		return hj;
	}
	public void setHj(double hj) {
		this.hj = hj;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

}
