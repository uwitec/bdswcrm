package com.sw.cms.model;

/**
 * 报修返还单商品
 * 
 * @author Administrator
 * 2010-02-04修改，有以下信息：流水号\报修返还单ID\产品ID\产品名称\产品型号\产品序列号\仓库编号\库存状态\数量\产品附件\单价\金额\产品备注
 *         id\bxfhd_id\product_id\product_name\product_xh\qz_serial_num\store_id\storestate\nums\cpfj\price\totalmoney\remark
 * 
 */
public class BxfhdProduct 
{
	private int id;// 流水号
	private String bxfhd_id; // 报修返还单ID
	private String product_id; // 产品ID
	private String product_name; // 产品名称
	private String product_xh; // 产品型号
	private String qz_serial_num;// 产品序列号
	private String remark;// 产品备注
	private String store_id; // 仓库编号
	private String storeState; // 仓库标识：仓库编号为坏件库编号时使用，1：在坏件库中  2：在外库中
	private int nums; // 产品数量
	private String cpfj;// 产品附件
	private double price; // 产品价格
	private double totalMoney; // 产品总金额
	 

	public String getStord_id() {
		return store_id;
	}

	public void setStord_id(String store_id) {
		this.store_id = store_id;
	}

	public String getStorestate() {
		return storeState;
	}

	public void setStorestate(String storeState) {
		this.storeState = storeState;
	}

	public String getBxfhd_id() {
		return bxfhd_id;
	}

	public void setBxfhd_id(String bxfhd_id) {
		this.bxfhd_id = bxfhd_id;
	}

	public String getCpfj() {
		return cpfj;
	}

	public void setCpfj(String cpfj) {
		this.cpfj = cpfj;
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

	public double getTotalmoney() {
		return totalMoney;
	}

	public void setTotalmoney(double totalMoney) {
		this.totalMoney = totalMoney;
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

	public String getProduct_xh() {
		return product_xh;
	}

	public void setProduct_xh(String product_xh) {
		this.product_xh = product_xh;
	}

	public String getQz_serial_num() {
		return qz_serial_num;
	}

	public void setQz_serial_num(String qz_serial_num) {
		this.qz_serial_num = qz_serial_num;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
