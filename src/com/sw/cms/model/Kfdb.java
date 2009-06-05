package com.sw.cms.model;

/**
 * ¿â·¿µ÷²¦
 * @author liyt
 *
 */

public class Kfdb {
	
	private String id;
	private String ck_date;
	private String jsr;
	private String ck_store_id;
	private String dbsq_id;
	private String rk_store_id;
	private String sqr;
	private String czr;
	private String state;
	private String remark;
	
	public String getCk_date() {
		return ck_date;
	}
	public void setCk_date(String ck_date) {
		this.ck_date = ck_date;
	}
	public String getCk_store_id() {
		return ck_store_id;
	}
	public void setCk_store_id(String ck_store_id) {
		this.ck_store_id = ck_store_id;
	}
	public String getCzr() {
		return czr;
	}
	public void setCzr(String czr) {
		this.czr = czr;
	}
	public String getDbsq_id() {
		return dbsq_id;
	}
	public void setDbsq_id(String dbsq_id) {
		this.dbsq_id = dbsq_id;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getRk_store_id() {
		return rk_store_id;
	}
	public void setRk_store_id(String rk_store_id) {
		this.rk_store_id = rk_store_id;
	}
	public String getSqr() {
		return sqr;
	}
	public void setSqr(String sqr) {
		this.sqr = sqr;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}

}
