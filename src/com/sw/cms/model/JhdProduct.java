package com.sw.cms.model;


public class JhdProduct{
	
	private int id;
	private String jhd_id;
	private String product_id;
	private String product_xh;
	private String product_name;
	private double price = 0;
	private int nums = 0;
	private String remark;
	private String qz_serial_num;
	private String qz_flag;
	private int sjcj_nums;  //实际成交商品数量
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getJhd_id() {
		return jhd_id;
	}
	public void setJhd_id(String jhd_id) {
		this.jhd_id = jhd_id;
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
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
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
	public int getSjcj_nums() {
		return sjcj_nums;
	}
	public void setSjcj_nums(int sjcj_nums) {
		this.sjcj_nums = sjcj_nums;
	}

}
