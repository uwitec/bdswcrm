package com.sw.cms.model;

public class ThdProduct {
	
	private int id;
	private String thd_id;
	private String product_id;
	private String product_xh;
	private String product_name;
	private double th_price;
	private int nums;
	private String remark;
	private double xj;
	private double cbj = 0;
	private double kh_cbj = 0;
	
	private String qz_serial_num;
	private String qz_flag;
	
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
	public String getThd_id() {
		return thd_id;
	}
	public void setThd_id(String thd_id) {
		this.thd_id = thd_id;
	}
	public double getXj() {
		return xj;
	}
	public void setXj(double xj) {
		this.xj = xj;
	}
	public double getCbj() {
		return cbj;
	}
	public void setCbj(double cbj) {
		this.cbj = cbj;
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
	public double getKh_cbj() {
		return kh_cbj;
	}
	public void setKh_cbj(double kh_cbj) {
		this.kh_cbj = kh_cbj;
	}

}
