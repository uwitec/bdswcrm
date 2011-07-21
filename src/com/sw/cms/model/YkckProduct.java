package com.sw.cms.model;

public class YkckProduct
{
	 
	private String ykck_id;
	private String product_id;
	private String product_name;
	private String product_xh;
	private String product_remark;
	private String qz_serial_num;
	private String qz_flag;
	private int nums;
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
	 
	public String getProduct_remark() {
		return product_remark;
	}
	public void setProduct_remark(String product_remark) {
		this.product_remark = product_remark;
	}
	public String getYkck_id() {
		return ykck_id;
	}
	public void setYkck_id(String ykck_id) {
		this.ykck_id = ykck_id;
	}
	public String getQz_flag() {
		return qz_flag;
	}
	public void setQz_flag(String qz_flag) {
		this.qz_flag = qz_flag;
	}
}
