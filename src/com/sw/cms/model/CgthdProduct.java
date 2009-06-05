package com.sw.cms.model;

public class CgthdProduct {
	
	private int id;
	private String cgthd_id;
	private String product_id;
	private String product_name;
	private String product_xh;
	private double th_price;
	private int nums;
	private double xj;
	private String remark;
	private String qz_serial_num;
	private String qz_flag;
	
	public String getCgthd_id() {
		return cgthd_id;
	}
	public void setCgthd_id(String cgthd_id) {
		this.cgthd_id = cgthd_id;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public double getTh_price() {
		return th_price;
	}
	public void setTh_price(double th_price) {
		this.th_price = th_price;
	}
	public double getXj() {
		return xj;
	}
	public void setXj(double xj) {
		this.xj = xj;
	}
	public String getQz_serial_num() {
		return qz_serial_num;
	}
	public void setQz_serial_num(String qz_serial_num) {
		this.qz_serial_num = qz_serial_num;
	}
	public String getQz_flag() {
		return qz_flag;
	}
	public void setQz_flag(String qz_flag) {
		this.qz_flag = qz_flag;
	}

}
