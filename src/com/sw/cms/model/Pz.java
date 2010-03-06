package com.sw.cms.model;

import java.util.Date;

public class Pz {
	
	private String id;
	private String pz_date;
	private String jsr;
	private String client_name;
	private double pzje;
	private String type;
	private String state;
	private String remark;
	private String czr;
	private String pzxm;
	private Date cz_date;
	private double jsje;
	
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
	public String getJsr() {
		return jsr;
	}
	public void setJsr(String jsr) {
		this.jsr = jsr;
	}
	public String getPz_date() {
		return pz_date;
	}
	public void setPz_date(String pz_date) {
		this.pz_date = pz_date;
	}
	public double getPzje() {
		return pzje;
	}
	public void setPzje(double pzje) {
		this.pzje = pzje;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCzr() {
		return czr;
	}
	public void setCzr(String czr) {
		this.czr = czr;
	}
	public String getPzxm() {
		return pzxm;
	}
	public void setPzxm(String pzxm) {
		this.pzxm = pzxm;
	}
	public Date getCz_date() {
		return cz_date;
	}
	public void setCz_date(Date czDate) {
		cz_date = czDate;
	}
	public double getJsje() {
		return jsje;
	}
	public void setJsje(double jsje) {
		this.jsje = jsje;
	}

}
