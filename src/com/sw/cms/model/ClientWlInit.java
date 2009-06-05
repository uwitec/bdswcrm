package com.sw.cms.model;


/**
 * 客户往来对象
 * @author liyt
 *
 */

public class ClientWlInit {

	private int id;
	private String client_name;
	private double ysqc;
	private double yfqc;
	private String remark;
	private String czr;
	private String cz_date;
	
	public String getClient_name() {
		return client_name;
	}
	public void setClient_name(String client_name) {
		this.client_name = client_name;
	}
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
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public double getYfqc() {
		return yfqc;
	}
	public void setYfqc(double yfqc) {
		this.yfqc = yfqc;
	}
	public double getYsqc() {
		return ysqc;
	}
	public void setYsqc(double ysqc) {
		this.ysqc = ysqc;
	}	
	
}
