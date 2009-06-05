package com.sw.cms.model;

public class SerialNumMng {
	
	private int seq_id;          //流水号
	private String serial_num;   //序列号
	private String product_id;   //产品编号
	private String product_name; //产品名称
	private String product_xh;   //产品规格
	private String state;        //序列号状态，（在库、已售、已退货)
	private String store_id;     //库房编号
	private String store_name;   //库房名称(数据库中不保存，通过store_id在获取信息赋值)
	private String yj_flag;      //样机标记（0,否；1，是）
	
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
	public int getSeq_id() {
		return seq_id;
	}
	public void setSeq_id(int seq_id) {
		this.seq_id = seq_id;
	}
	public String getSerial_num() {
		return serial_num;
	}
	public void setSerial_num(String serial_num) {
		this.serial_num = serial_num;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getStore_id() {
		return store_id;
	}
	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}
	public String getYj_flag() {
		return yj_flag;
	}
	public void setYj_flag(String yj_flag) {
		this.yj_flag = yj_flag;
	}
	public String getStore_name() {
		return store_name;
	}
	public void setStore_name(String store_name) {
		this.store_name = store_name;
	}

}
