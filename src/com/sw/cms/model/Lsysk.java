package com.sw.cms.model;

/**
 * 零售预收款
 * @author liyt
 *
 */

public class Lsysk {
	
	private String id;
	private String client_name;
	private String ys_date;
	private String lxr;
	private String jsr;
	private String lxdh;
	private String mobile;
	private double ysje;
	private String skzh;
	private String state;
	private String fkfs;
	private String remark;
	private String czr;
	private String type;     
	private String pos_id;   //POS机编号
	
	public String getClient_name() {
		return client_name;
	}
	public void setClient_name(String client_name) {
		this.client_name = client_name;
	}
	public String getCzr() {
		return czr;
	}
	public void setCzr(String czr) {
		this.czr = czr;
	}
	public String getFkfs() {
		return fkfs;
	}
	public void setFkfs(String fkfs) {
		this.fkfs = fkfs;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLxdh() {
		return lxdh;
	}
	public void setLxdh(String lxdh) {
		this.lxdh = lxdh;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
	public double getYsje() {
		return ysje;
	}
	public void setYsje(double ysje) {
		this.ysje = ysje;
	}
	public String getJsr() {
		return jsr;
	}
	public void setJsr(String jsr) {
		this.jsr = jsr;
	}
	public String getYs_date() {
		return ys_date;
	}
	public void setYs_date(String ys_date) {
		this.ys_date = ys_date;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPos_id() {
		return pos_id;
	}
	public void setPos_id(String pos_id) {
		this.pos_id = pos_id;
	}

}
