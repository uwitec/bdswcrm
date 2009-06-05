package com.sw.cms.model;

/**
 * ÄÚ²¿×ªÕË
 * @author liyt
 *
 */

public class Nbzz {

	private String id;
	private String zz_date;
	private String zczh;
	private String zrzh;
	private double zzje = 0;
	private String jsr;
	private String state;
	private String czr;
	private String remark;
	
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
	public String getZczh() {
		return zczh;
	}
	public void setZczh(String zczh) {
		this.zczh = zczh;
	}
	public String getZrzh() {
		return zrzh;
	}
	public void setZrzh(String zrzh) {
		this.zrzh = zrzh;
	}
	public String getZz_date() {
		return zz_date;
	}
	public void setZz_date(String zz_date) {
		this.zz_date = zz_date;
	}
	public double getZzje() {
		return zzje;
	}
	public void setZzje(double zzje) {
		this.zzje = zzje;
	}
	
}
