package com.sw.cms.model;

public class KcpdDesc {
	
	private int id;
	private String pd_id;
	private String product_id;
	private String product_xh;
	private String product_name;
	private int kc_nums;
	private int sj_nums;
	private int yk;
	private String remark;
	
	private String qz_serial_num;
	private String qz_flag;            //«ø÷∆–Ú¡–∫≈
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getKc_nums() {
		return kc_nums;
	}
	public void setKc_nums(int kc_nums) {
		this.kc_nums = kc_nums;
	}
	public String getPd_id() {
		return pd_id;
	}
	public void setPd_id(String pd_id) {
		this.pd_id = pd_id;
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
	public int getSj_nums() {
		return sj_nums;
	}
	public void setSj_nums(int sj_nums) {
		this.sj_nums = sj_nums;
	}
	public int getYk() {
		return yk;
	}
	public void setYk(int yk) {
		this.yk = yk;
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
}
