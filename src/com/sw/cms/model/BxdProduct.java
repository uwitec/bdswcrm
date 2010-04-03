package com.sw.cms.model;

/**
 * 报修单商品信息
 * 2010-01-29 修改，只剩下以下信息：编号、报修单ID、商品ID、商品名称、商品型号、商品序列号、商品备注、仓库编号、库存状态、数量、商品附件、送修天数
 * id    bxd_id    product_id  product_name product_xh product_serial_num product_remark store_id storestate nums cpfj sxts
 */
public class BxdProduct {
	private int id; // 流水号

	private String bxd_id; // 报修单ID

	private String product_id; // 商品ID

	private String product_name; // 商品名称	

	private String product_xh; // 商品型号

	private String qz_serial_num; // 商品序列号

	private String product_remark; // 商品备注

	private String store_id; // 仓库编号

	private String storestate; // 仓库标识：1-坏件库中 2-在外库中，只对坏件库进行标识

	private String cpfj; // 商品附件

	private int nums; // 商品数量

	private int sxts; // 送修天数

	

	public int getSxts() {
		return sxts;
	}

	public void setSxts(int sxts) {
		this.sxts = sxts;
	}

	public int getNums() {
		return nums;
	}

	public void setNums(int nums) {
		this.nums = nums;
	}

	public String getBxd_id() {
		return bxd_id;
	}

	public void setBxd_id(String bxd_id) {
		this.bxd_id = bxd_id;
	}

	public String getCpfj() {
		return cpfj;
	}

	public void setCpfj(String cpfj) {
		this.cpfj = cpfj;
	}

	public String getStore_id() {
		return store_id;
	}

	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}

	public String getStorestate() {
		return storestate;
	}

	public void setStorestate(String storestate) {
		this.storestate = storestate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getProduct_remark() {
		return product_remark;
	}

	public void setProduct_remark(String product_remark) {
		this.product_remark = product_remark;
	}

	public String getQz_serial_num() {
		return qz_serial_num;
	}

	public void setQz_serial_num(String qz_serial_num) {
		this.qz_serial_num = qz_serial_num;
	}

	public String getProduct_xh() {
		return product_xh;
	}

	public void setProduct_xh(String product_xh) {
		this.product_xh = product_xh;
	}

}
