package com.sw.cms.model;

public class DbsqProduct {
	
	private String dbsq_id;
	private String product_id;
	private String product_name;
	private String product_xh;
	private int nums;
	private String remark;
	
	public String getDbsq_id() {
		return dbsq_id;
	}
	public void setDbsq_id(String dbsq_id) {
		this.dbsq_id = dbsq_id;
	}
	public int getNums() {
		return nums;
	}
	public void setNums(int nums) {
		this.nums = nums;
	}
	public String getProduct_id() {
		return product_id;
	}
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public String getProduct_xh() {
		return product_xh;
	}
	public void setProduct_xh(String product_xh) {
		this.product_xh = product_xh;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

}
