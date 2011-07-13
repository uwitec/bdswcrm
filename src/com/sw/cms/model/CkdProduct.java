package com.sw.cms.model;

public class CkdProduct {
	
	private int id;
	private String ckd_id;
	private String product_id;
	private String product_xh;
	private String product_name;
	private int nums = 0;
	private int ck_nums = 0;
	private String remark;
	private double price = 0;
	private double jgtz = 0;
	private double cbj = 0;
	private String qz_serial_num;
	private String qz_flag;            //«ø÷∆–Ú¡–∫≈
	
	public String getCkd_id() {
		return ckd_id;
	}
	public void setCkd_id(String ckd_id) {
		this.ckd_id = ckd_id;
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
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public double getCbj() {
		return cbj;
	}
	public void setCbj(double cbj) {
		this.cbj = cbj;
	}
	public double getJgtz() {
		return jgtz;
	}
	public void setJgtz(double jgtz) {
		this.jgtz = jgtz;
	}
	public String getQz_serial_num() {
		return qz_serial_num;
	}
	public void setQz_serial_num(String qz_serial_num) {
		this.qz_serial_num = qz_serial_num;
	}
	public int getCk_nums() {
		return ck_nums;
	}
	public void setCk_nums(int ck_nums) {
		this.ck_nums = ck_nums;
	}
	public String getQz_flag() {
		return qz_flag;
	}
	public void setQz_flag(String qz_flag) {
		this.qz_flag = qz_flag;
	}
}
