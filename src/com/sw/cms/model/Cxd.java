package com.sw.cms.model;

import java.util.Date;

/**
 * ²ðÐ¶µ¥
 * @author jinyanni
 *
 */
public class Cxd {
	
	private String id;
	private String cdate;
	private String store_id;
	private String product_id;
	private String product_name;
	private String product_xh;
	private String product_dw;
	private int nums;
	private double price;
	private double hjje;
	private String jsr;
	private String remark;
	private String czr;
	private String state;
	private String serial_nums;
	private String qz_flag;
	private Date cz_date;
	
	public Date getCz_date() {
		return cz_date;
	}
	public void setCz_date(Date czDate) {
		cz_date = czDate;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCdate() {
		return cdate;
	}
	public void setCdate(String cdate) {
		this.cdate = cdate;
	}
	public String getStore_id() {
		return store_id;
	}
	public void setStore_id(String storeId) {
		store_id = storeId;
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
	public int getNums() {
		return nums;
	}
	public void setNums(int nums) {
		this.nums = nums;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public double getHjje() {
		return hjje;
	}
	public void setHjje(double hjje) {
		this.hjje = hjje;
	}
	public String getJsr() {
		return jsr;
	}
	public void setJsr(String jsr) {
		this.jsr = jsr;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCzr() {
		return czr;
	}
	public void setCzr(String czr) {
		this.czr = czr;
	}
	public String getSerial_nums() {
		return serial_nums;
	}
	public void setSerial_nums(String serialNums) {
		serial_nums = serialNums;
	}
	public String getQz_flag() {
		return qz_flag;
	}
	public void setQz_flag(String qzFlag) {
		qz_flag = qzFlag;
	}

	
}
