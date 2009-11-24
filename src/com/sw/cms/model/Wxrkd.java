package com.sw.cms.model;
/**
 * 维修入库单
 * @author Administrator
 *
 */
public class Wxrkd 
{
	 private String id  ;    //维修入库单ID
     private String wx_date ;//维修入库时间       
     private String cj_date ;//创建时间       	
     private String wxr    ; //维修人  
     private String cjr    ; //创建人
     private String state  ;// 状态  		 
     private String client_name ;//客户名称
     private String lxr  ;  //联系人        
     private String  mobile   ;	//联系电话 
     private String address ;//地址  	        
     private String  ms ;//备注
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCj_date() {
		return cj_date;
	}
	public void setCj_date(String cj_date) {
		this.cj_date = cj_date;
	}
	public String getCjr() {
		return cjr;
	}
	public void setCjr(String cjr) {
		this.cjr = cjr;
	}
	public String getClient_name() {
		return client_name;
	}
	public void setClient_name(String client_name) {
		this.client_name = client_name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLxr() {
		return lxr;
	}
	public void setLxr(String lxr) {
		this.lxr = lxr;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getMs() {
		return ms;
	}
	public void setMs(String ms) {
		this.ms = ms;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getWx_date() {
		return wx_date;
	}
	public void setWx_date(String wx_date) {
		this.wx_date = wx_date;
	}
	public String getWxr() {
		return wxr;
	}
	public void setWxr(String wxr) {
		this.wxr = wxr;
	}
}
