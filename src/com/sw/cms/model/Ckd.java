package com.sw.cms.model;

public class Ckd {
	
	private String ckd_id;
	private String fzr;
	private String xsd_id;
	private String creatdate;
	private String ck_date;
	private String state;  //物流状态
	private String ms;
	private String client_name; //客户编号
	private String tel;  //客户联系电话
	private String xsry;
	private String store_id;
	
	private String czr;
	private String skzt; //收款状态
	
	private String ysfs;       //运输方式
	private String cx_tel;     //查询电话
	private String job_no;     //货单号
	private String send_time;  //发货时间
	
	private String client_lxr;
	private String client_lxr_address;
	private String client_lxr_tel;

	
	public String getCk_date() {
		return ck_date;
	}
	public void setCk_date(String ck_date) {
		this.ck_date = ck_date;
	}
	public String getCkd_id() {
		return ckd_id;
	}
	public void setCkd_id(String ckd_id) {
		this.ckd_id = ckd_id;
	}
	public String getClient_name() {
		return client_name;
	}
	public void setClient_name(String client_name) {
		this.client_name = client_name;
	}
	public String getCreatdate() {
		return creatdate;
	}
	public void setCreatdate(String creatdate) {
		this.creatdate = creatdate;
	}
	public String getFzr() {
		return fzr;
	}
	public void setFzr(String fzr) {
		this.fzr = fzr;
	}
	public String getMs() {
		return ms;
	}
	public void setMs(String ms) {
		this.ms = ms;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getXsd_id() {
		return xsd_id;
	}
	public void setXsd_id(String xsd_id) {
		this.xsd_id = xsd_id;
	}
	public String getXsry() {
		return xsry;
	}
	public void setXsry(String xsry) {
		this.xsry = xsry;
	}
	public String getStore_id() {
		return store_id;
	}
	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}
	public String getCzr() {
		return czr;
	}
	public void setCzr(String czr) {
		this.czr = czr;
	}
	public String getSkzt() {
		return skzt;
	}
	public void setSkzt(String skzt) {
		this.skzt = skzt;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getCx_tel() {
		return cx_tel;
	}
	public void setCx_tel(String cx_tel) {
		this.cx_tel = cx_tel;
	}
	public String getJob_no() {
		return job_no;
	}
	public void setJob_no(String job_no) {
		this.job_no = job_no;
	}
	public String getSend_time() {
		return send_time;
	}
	public void setSend_time(String send_time) {
		this.send_time = send_time;
	}
	public String getYsfs() {
		return ysfs;
	}
	public void setYsfs(String ysfs) {
		this.ysfs = ysfs;
	}
	public String getClient_lxr() {
		return client_lxr;
	}
	public void setClient_lxr(String client_lxr) {
		this.client_lxr = client_lxr;
	}
	public String getClient_lxr_address() {
		return client_lxr_address;
	}
	public void setClient_lxr_address(String client_lxr_address) {
		this.client_lxr_address = client_lxr_address;
	}
	public String getClient_lxr_tel() {
		return client_lxr_tel;
	}
	public void setClient_lxr_tel(String client_lxr_tel) {
		this.client_lxr_tel = client_lxr_tel;
	}

}
