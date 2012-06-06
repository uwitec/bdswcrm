package com.sw.cms.model;

public class RkdProduct {

	private int id;
	private String rkd_id;
	private String product_id;
	private String product_xh;
	private String product_name;
	private double price;
	private int nums;
	private String remark;
	private String qz_serial_num;
	private double sd;					//税点
	private double hsje;				//含税金额
	private double bhsje;			//不含税金额
	private double sje;				//税额
	
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
	public String getRkd_id() {
		return rkd_id;
	}
	public void setRkd_id(String rkd_id) {
		this.rkd_id = rkd_id;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public double getSd() {
		return sd;
	}
	public void setSd(double sd) {
		this.sd = sd;
	}
	public double getHsje() {
		return hsje;
	}
	public void setHsje(double hsje) {
		this.hsje = hsje;
	}
	public double getBhsje() {
		return bhsje;
	}
	public void setBhsje(double bhsje) {
		this.bhsje = bhsje;
	}
	public double getSje() {
		return sje;
	}
	public void setSje(double sje) {
		this.sje = sje;
	}
	
}
