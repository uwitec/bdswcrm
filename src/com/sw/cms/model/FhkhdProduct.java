package com.sw.cms.model;

/**
 * 返还客户单商品
 * @author Administrator
 *2010-02-05修改，有以下信息：编号、返还客户单编号、产品编号、   产品名称、      产品规格、     产品附件
 *                         id   fhkhd_id    product_id  product_name  product_xh   cpfj
 *                      强制序列号、     备注、    单价、   金额、        仓库编号、    库存状态、     数量
 *                      qz_serial_num  remark   price   totalmoney   store_id    storestate   nums
 */
public class FhkhdProduct
{
	   private int id  ;//流水号
	   private String  fhkhd_id     ;//返还客户单ID
	   private String product_id  ;//返还客户单产品ID
	   private String product_name ;//返还客户单产品名称
	   private String product_xh   ;//返还客户单产品规格
	   private String qz_serial_num ;//返还客户单产品序列号
	   private String remark       ;//备注
	   
	   private double price;       //单价
	   private double totalmoney;   //金额
	   private String store_id;    //仓库编号
	   private String storestate;  //库存状态
	   private int nums;           //数量
	   private String cpfj;  //产品附件
	   
	public String getFhkhd_id() {
		return fhkhd_id;
	}
	public void setFhkhd_id(String fhkhd_id) {
		this.fhkhd_id = fhkhd_id;
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
	public String getCpfj() {
		return cpfj;
	}
	public void setCpfj(String cpfj) {
		this.cpfj = cpfj;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public double getTotalmoney() {
		return totalmoney;
	}
	public void setTotalmoney(double totalmoney) {
		this.totalmoney = totalmoney;
	}
	
	public int getNums() {
		return nums;
	}
	public void setNums(int nums) {
		this.nums = nums;
	}
}
