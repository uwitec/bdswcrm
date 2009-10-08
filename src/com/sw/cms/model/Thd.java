package com.sw.cms.model;

public class Thd {

	private String thd_id;
	private String client_name;
	private String th_fzr;
	private double thdje;
	private String remark;
	private String th_date;
	private String state;
	private String tkzh;
	private String czr;
	private String type;
	private String xsd_id;
	private String store_id;
	private String th_flag;
	
	//开票信息
	private String fplx;         //发票类型
	private String kp_mc;         //开票名称
	private String kp_address;   //开票地址
	private String kp_dh;        //开票电话
	private String khhzh;        //开户行账号
	private String sh;           //税号
	private String fpxx;         //发票信息
	
	public String getClient_name() {
		return client_name;
	}
	public void setClient_name(String client_name) {
		this.client_name = client_name;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getTh_fzr() {
		return th_fzr;
	}
	public void setTh_fzr(String th_fzr) {
		this.th_fzr = th_fzr;
	}
	public String getThd_id() {
		return thd_id;
	}
	public void setThd_id(String thd_id) {
		this.thd_id = thd_id;
	}
	public double getThdje() {
		return thdje;
	}
	public void setThdje(double thdje) {
		this.thdje = thdje;
	}
	public String getTh_date() {
		return th_date;
	}
	public void setTh_date(String th_date) {
		this.th_date = th_date;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getTkzh() {
		return tkzh;
	}
	public void setTkzh(String tkzh) {
		this.tkzh = tkzh;
	}
	public String getCzr() {
		return czr;
	}
	public void setCzr(String czr) {
		this.czr = czr;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getXsd_id() {
		return xsd_id;
	}
	public void setXsd_id(String xsd_id) {
		this.xsd_id = xsd_id;
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
	public String getFplx() {
		return fplx;
	}
	public void setFplx(String fplx) {
		this.fplx = fplx;
	}
	public String getFpxx() {
		return fpxx;
	}
	public void setFpxx(String fpxx) {
		this.fpxx = fpxx;
	}
	public String getKhhzh() {
		return khhzh;
	}
	public void setKhhzh(String khhzh) {
		this.khhzh = khhzh;
	}
	public String getKp_address() {
		return kp_address;
	}
	public void setKp_address(String kp_address) {
		this.kp_address = kp_address;
	}
	public String getKp_dh() {
		return kp_dh;
	}
	public void setKp_dh(String kp_dh) {
		this.kp_dh = kp_dh;
	}
	public String getKp_mc() {
		return kp_mc;
	}
	public void setKp_mc(String kp_mc) {
		this.kp_mc = kp_mc;
	}
	public String getSh() {
		return sh;
	}
	public void setSh(String sh) {
		this.sh = sh;
	}
	
}
