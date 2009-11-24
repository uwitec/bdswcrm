package com.sw.cms.model;

/**
 * 接件单
 * @author Administrator
 *
 */
public class Jjd 
{
	   private String  id       ;    //接件单ID
	   private String  sfd_id   ;//售后服务单ID
	   
	   private String  client_name ;//客户名称（如果是零售客户就不用填写，直接填写联系人）
	   private String  address     ;//地址
	   private String  mobile     ;//电话
	   private String  linkman    ;//联系人

	   private String  jjr        ;//接件人
	   private String  cjr         ;//创建人
	   private String  jj_date     ;//接件时间
	   private String  cj_date    ;//创建时间

	   private  String state     ;//接件单状态（已保存，已提交：坏件库）
	   private  String ms        ;//描述   
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
	public String getJj_date() {
		return jj_date;
	}
	public void setJj_date(String jj_date) {
		this.jj_date = jj_date;
	}
	public String getJjr() {
		return jjr;
	}
	public void setJjr(String jjr) {
		this.jjr = jjr;
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
	public String getMs() {
		return ms;
	}
	public void setMs(String ms) {
		this.ms = ms;
	}
	public String getSfd_id() {
		return sfd_id;
	}
	public void setSfd_id(String sfd_id) {
		this.sfd_id = sfd_id;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
}
