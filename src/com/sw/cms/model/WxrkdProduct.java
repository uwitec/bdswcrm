package com.sw.cms.model;
/**
 * 维修入库单_产品
 * @author Administrator
 * 2010-03-01修改，有以下信息：id  流水号
 * wxrkd_id        维修入库单编号
 * product_id      产品编号
 * product_name    产品名称
 * product_xh      产品规格
 * qz_serial_num   强制序列号
 * remark          备注
 * nums            数量
 * store_id       目标库——好件库
 * storestate     目标库状态——3
 */
public class WxrkdProduct 
{
	 private int id  ;//流水号
     private String wxrkd_id ;//维修入库单ID
     private String product_id; //产品ID 
     private String  product_name  ;      //产品名称
      
     private String product_xh   ;   //产品型号 
     private String qz_serial_num  ;//产品序列号
     private String remark ;//产品备注    
     private int nums       ;//数量
     private String store_id         ; //目标库
     private String storestate          ;//目标库状态
          
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
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
	public String getStorestate() {
		return storestate;
	}
	public void setStorestate(String storestate) {
		this.storestate =storestate;
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
	public String getWxrkd_id() {
		return wxrkd_id;
	}
	public void setWxrkd_id(String wxrkd_id) {
		this.wxrkd_id = wxrkd_id;
	}
	
	public String getStore_id() {
		return store_id;
	}
	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}
}
