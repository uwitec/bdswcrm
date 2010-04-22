package com.sw.cms.model;

/**
 * 咨询工单
 * @author Administrator
 * 
 */
public class Zxgd 
{
	   private String  id;     //咨询工单编号
	   private String  sfd_id ;//售后服务单编号
	   private String  hfr;  //回复人
	   private String  czr;//操作人
	   private String  hf_date;//回复时间
	   private String  cz_date;//操作时间
	   private String  state;     //回复状态(处理中，已完成)
	   private String  content;//回复内容
	   private String  khyj;//客户意见
	   
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSfd_id() {
		return sfd_id;
	}
	public void setSfd_id(String sfd_id) {
		this.sfd_id = sfd_id;
	}
	public String getCzr() {
		return czr;
	}
	public void setCzr(String czr) {
		this.czr = czr;
	}
	public String getHfr() {
		return hfr;
	}
	public void setHfr(String hfr) {
		this.hfr = hfr;
	}
	
	public String getHf_date() {
		return hf_date;
	}
	public void setHf_date(String hf_date) {
		this.hf_date = hf_date;
	}
	
	public String getCz_date() {
		return cz_date;
	}
	public void setCz_date(String cz_date) {
		this.cz_date = cz_date;
	}
	public String getKhyj() {
		return khyj;
	}
	public void setKhyj(String khyj) {
		this.khyj = khyj;
	}
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	
}
