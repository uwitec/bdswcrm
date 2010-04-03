package com.sw.cms.model;

/**
 * 拆卸单相关新商品
 * @author jinyanni
 *
 */
public class CxdProduct {
	
	private int id;
	private String cxd_id;
	private String product_id;
	private String product_name;
	private String product_xh;
	private String product_dw;
	private double price;
	private int nums;
	private double hj;
	private String remark;
	private String qz_serial_num;
	private String qz_flag;
	
	public String getQz_serial_num() {
		return qz_serial_num;
	}
	public void setQz_serial_num(String qzSerialNum) {
		qz_serial_num = qzSerialNum;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCxd_id() {
		return cxd_id;
	}
	public void setCxd_id(String cxdId) {
		cxd_id = cxdId;
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
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getNums() {
		return nums;
	}
	public void setNums(int nums) {
		this.nums = nums;
	}
	public double getHj() {
		return hj;
	}
	public void setHj(double hj) {
		this.hj = hj;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getQz_flag() {
		return qz_flag;
	}
	public void setQz_flag(String qzFlag) {
		qz_flag = qzFlag;
	}

}
