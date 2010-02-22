package com.sw.cms.model;

/**
 * 返还客户单
 * @author Administrator
 * 2010-02-05修改，有以下信息：返还客户单编号、返还客户日期、创建日期、经手人、创建人、状态、客户编号、      收款金额、收款账户、备注、    联系人
 *                          id           fh_date    cj_date  jsr   cjr   state  client_id  skje    skzh    remark  lxr
 *
 */
public class Fhkhd 
{
	 private String  id  ;       //返还单ID
	 private String fh_date ;//返还时间       
	 private String cj_date ;//创建时间       	
	 private String jsr   ; //经手人  
	 private String cjr   ; //创建人
	 private String state  ;// 状态  		 
	 private String client_id ;//客户编号
	 private String lxr   ; //联系人   
	 private double skje       ;   //实收金额
     private String skzh       ;//收款账号
     private String  remark ;//备注	

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

	public String getClient_id() {
		return client_id;
	}

	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}

	public String getFh_date() {
		return fh_date;
	}

	public void setFh_date(String fh_date) {
		this.fh_date = fh_date;
	}

	public String getJsr() {
		return jsr;
	}

	public void setJsr(String jsr) {
		this.jsr = jsr;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public double getSkje() {
		return skje;
	}

	public void setSkje(double skje) {
		this.skje = skje;
	}

	public String getSkzh() {
		return skzh;
	}

	public void setSkzh(String skzh) {
		this.skzh = skzh;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
}
