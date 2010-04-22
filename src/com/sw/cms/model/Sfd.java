package com.sw.cms.model;

/**
 * 售后服务单
 * @author Administrator
 * 2010-3-23增加一个字段 flow
 */
public class Sfd 
{
	   private String  id;          //售后服务单号
	   private String  client_name ;//客户名称（如果是零售客户就不用填写，直接填写联系人）
	   private String  address    ;//地址
	   private String  mobile     ;//电话
	   private String  linkman    ;//联系人
	   private String  jxr;        //经手人
	   private String  cjr        ;//创建人
	   private String  jd_date    ;//结单时间
	   private String  jx_date    ;//接修时间
	   private String  cj_date    ;//创建时间
	   private String  qzfs;      //求助方式
	   private String  state      ;//售后服务单状态（保存，提交）	   
	   private String wx_state;     //维修状态(处理中，处理完)
	   private String  ms          ;//描述
	   private String  flow          ;//设置流程：咨询、投诉、维修
	   
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
	public String getJd_date() {
		return jd_date;
	}
	public void setJd_date(String jd_date) {
		this.jd_date = jd_date;
	}
	public String getJx_date() {
		return jx_date;
	}
	public void setJx_date(String jx_date) {
		this.jx_date = jx_date;
	}
	public String getJxr() {
		return jxr;
	}
	public void setJxr(String jxr) {
		this.jxr = jxr;
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
	public String getQzfs() {
		return qzfs;
	}
	public void setQzfs(String qzfs) {
		this.qzfs = qzfs;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getWx_state() {
		return wx_state;
	}
	public void setWx_state(String wx_state) {
		this.wx_state = wx_state;
	} 
	
	public String getFlow() {
		return flow;
	}
	public void setFlow(String flow) {
		this.flow = flow;
	}
}
