package com.sw.cms.model;

public class Cgthd {
	
	private String id;
	private String th_date;
	private String provider_name;
	private String jsr;
	private String tkfs;
	private String state;
	private String zhmc;
	private double tkzje;
	private String remark;
	private String czr;
	private String cz_date;
	private String jhd_id;
	private String store_id;  //出库库房
	private String th_flag;     //退回标志（0：正常；1：库房退回
	
	public String getCz_date() {
		return cz_date;
	}
	public void setCz_date(String cz_date) {
		this.cz_date = cz_date;
	}
	public String getCzr() {
		return czr;
	}
	public void setCzr(String czr) {
		this.czr = czr;
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
	public String getProvider_name() {
		return provider_name;
	}
	public void setProvider_name(String provider_name) {
		this.provider_name = provider_name;
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
	public String getTh_date() {
		return th_date;
	}
	public void setTh_date(String th_date) {
		this.th_date = th_date;
	}
	public String getTkfs() {
		return tkfs;
	}
	public void setTkfs(String tkfs) {
		this.tkfs = tkfs;
	}
	public double getTkzje() {
		return tkzje;
	}
	public void setTkzje(double tkzje) {
		this.tkzje = tkzje;
	}
	public String getZhmc() {
		return zhmc;
	}
	public void setZhmc(String zhmc) {
		this.zhmc = zhmc;
	}
	public String getJhd_id() {
		return jhd_id;
	}
	public void setJhd_id(String jhd_id) {
		this.jhd_id = jhd_id;
	}
	public String getStore_id() {
		return store_id;
	}
	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}
	public String getTh_flag() {
		return th_flag;
	}
	public void setTh_flag(String th_flag) {
		this.th_flag = th_flag;
	}

}
