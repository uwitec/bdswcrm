package com.sw.cms.model;

import java.util.Date;

public class ProductKc {

	private String kc_id;
	private String product_id;
	private String store_id;
	private String product_xh;
	private String product_name;    
	private int nums;              //库存数量
	private double price;          //成本价
	private String remark;	         //备注
	private double fxxj;           //分销限价
	private double lsxj;           //零售报价
	private double fxbj;           //分销报价
	private int gf;                //工分
	private Date gxrq;             //更新日期
	private String prop;           //商品属性
	private String dw;
	
	private String flag;
	

	public int getGf() {
		return gf;
	}
	public void setGf(int gf) {
		this.gf = gf;
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
	public String getKc_id() {
		return kc_id;
	}
	public void setKc_id(String kc_id) {
		this.kc_id = kc_id;
	}
	public String getStore_id() {
		return store_id;
	}
	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}
	public int getNums() {
		return nums;
	}
	public void setNums(int nums) {
		this.nums = nums;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public Date getGxrq() {
		return gxrq;
	}
	public void setGxrq(Date gxrq) {
		this.gxrq = gxrq;
	}
	public String getProp() {
		return prop;
	}
	public void setProp(String prop) {
		this.prop = prop;
	}
	public double getFxxj() {
		return fxxj;
	}
	public void setFxxj(double fxxj) {
		this.fxxj = fxxj;
	}
	public double getLsxj() {
		return lsxj;
	}
	public void setLsxj(double lsxj) {
		this.lsxj = lsxj;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public double getFxbj() {
		return fxbj;
	}
	public void setFxbj(double fxbj) {
		this.fxbj = fxbj;
	}
	public String getDw() {
		return dw;
	}
	public void setDw(String dw) {
		this.dw = dw;
	}
	
}
