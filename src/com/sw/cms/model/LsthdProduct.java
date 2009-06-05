package com.sw.cms.model;

public class LsthdProduct {

	private int id;
	private String lsthd_id;
	private String product_id;
	private String product_xh;
	private String product_name;
	private double th_price;
	private int nums;
	private double xj;
	private double cbj;
	private String qz_serial_num;
	
	public double getCbj() {
		return cbj;
	}
	public void setCbj(double cbj) {
		this.cbj = cbj;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLsthd_id() {
		return lsthd_id;
	}
	public void setLsthd_id(String lsthd_id) {
		this.lsthd_id = lsthd_id;
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
	public String getQz_serial_num() {
		return qz_serial_num;
	}
	public void setQz_serial_num(String qz_serial_num) {
		this.qz_serial_num = qz_serial_num;
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
	
}
