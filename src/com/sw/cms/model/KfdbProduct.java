package com.sw.cms.model;

/**
 * 库房调拨产品明细
 * @author liyt
 *
 */

public class KfdbProduct {

	private String kfdb_id;
	private String product_id;
	private String product_name;
	private String product_xh;
	private String dw;
	private int nums;
	private String remark;
	private String qz_serial_num;


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

	public String getKfdb_id() {
		return kfdb_id;
	}

	public void setKfdb_id(String kfdb_id) {
		this.kfdb_id = kfdb_id;
	}

	public String getQz_serial_num() {
		return qz_serial_num;
	}

	public void setQz_serial_num(String qz_serial_num) {
		this.qz_serial_num = qz_serial_num;
	}

	public String getDw() {
		return dw;
	}

	public void setDw(String dw) {
		this.dw = dw;
	}
}
