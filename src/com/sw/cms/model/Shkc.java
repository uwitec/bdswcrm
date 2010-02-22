package com.sw.cms.model;

/**
 * 库存信息
 * @author Administrator
 * 2010-01-29 增加仓库编号、仓库标识
 */
public class Shkc 
{
	  private  int    id;                          //流水号
	  private  String product_id;                  //产品ID
	  private  String product_xh;                  //产品型号
	  private  String product_name;                //产品名称
	  private  String qz_serial_num;               //序列号    
	  private  String state;                       //库存状态(1:坏 2：外)
	  private  String day_num;                     //已送修天数
	  private  String remark;                      //备注
	  private  String client_name ;                //所属客户
	  private  String linkman ;                    //联系人
	  private  String mobile ;                     //手机
	  private  String lxdh ;                     //联系电话
	  private  String address ;                    //地址
	  private   double  wxf;                       //维修费
	  
	  
	  private  String store_id ;                  //仓库编号
	  
	  
	public double getWxf() {
		return wxf;
	}
	public void setWxf(double wxf) {
		this.wxf = wxf;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getDay_num() {
		return day_num;
	}
	public void setDay_num(String day_num) {
		this.day_num = day_num;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getClient_name() {
		return client_name;
	}
	public void setClient_name(String client_name) {
		this.client_name = client_name;
	}
	public String getLinkman() {
		return linkman;
	}
	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	//add 2010-01-29
	
	public String getStore_id() {
		return store_id;
	}
	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}
	
	public String getLxdh() {
		return lxdh;
	}
	public void setLxdh(String lxdh) {
		this.lxdh = lxdh;
	}
	//add end
}
