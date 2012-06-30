package com.sw.cms.model;

public class SerialNumFlow {

	private int seq_id;        //流水号
	private String serial_num; //序列号
	private String ywtype;     //业务类型
	private String client_name;  //客户名称
	private String tel;          //联系电话
	private String jsr;          //经手人（销售人员）
	private String yw_dj_id;     //业务单据编号
	private String kf_dj_id;     //库房单据编号
	private String fs_date;      //发生日期
	private String yw_url;       //业务URL
	private String kf_url;       //库房URL
	private String czr;          //操作人
	private String cz_date;      //操作时间
	private double xsdj;         //销售单价
	
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
	public String getFs_date() {
		return fs_date;
	}
	public void setFs_date(String fs_date) {
		this.fs_date = fs_date;
	}
	public String getJsr() {
		return jsr;
	}
	public void setJsr(String jsr) {
		this.jsr = jsr;
	}
	public int getSeq_id() {
		return seq_id;
	}
	public void setSeq_id(int seq_id) {
		this.seq_id = seq_id;
	}
	public String getSerial_num() {
		return serial_num.trim();
	}
	public void setSerial_num(String serial_num) {
		this.serial_num = serial_num;
	}
	public String getKf_dj_id() {
		return kf_dj_id;
	}
	public void setKf_dj_id(String kf_dj_id) {
		this.kf_dj_id = kf_dj_id;
	}
	public String getYw_dj_id() {
		return yw_dj_id;
	}
	public void setYw_dj_id(String yw_dj_id) {
		this.yw_dj_id = yw_dj_id;
	}
	public String getKf_url() {
		return kf_url;
	}
	public void setKf_url(String kf_url) {
		this.kf_url = kf_url;
	}
	public String getYw_url() {
		return yw_url;
	}
	public void setYw_url(String yw_url) {
		this.yw_url = yw_url;
	}
	public String getYwtype() {
		return ywtype;
	}
	public void setYwtype(String ywtype) {
		this.ywtype = ywtype;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public double getXsdj() {
		return xsdj;
	}
	public void setXsdj(double xsdj) {
		this.xsdj = xsdj;
	}
	
}
