package com.sw.cms.model;

import java.util.Date;

public class Fxdd {
	
	private String fxdd_id;      //���
	private String creatdate;    //����ʱ��
	private String client_name;  //�ͻ����
	private String address;      //��ַ
	private String lxr;          //��ϵ��
	private String lxdh;         //��ϵ�绰
	private String ysfs;         //���䷽ʽ
	private double hjje;         //�ϼƽ��
	private String state;        //״̬
	private String remark;       //��ע
	private Date cz_date;        //����ʱ��
	private String wlzt;         //����״̬
	
	private String cx_tel;      //��ѯ�绰
	private String job_no;      //������
	private String send_time;   //����ʱ��
	
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
	public String getFxdd_id() {
		return fxdd_id;
	}
	public void setFxdd_id(String fxdd_id) {
		this.fxdd_id = fxdd_id;
	}
	public double getHjje() {
		return hjje;
	}
	public void setHjje(double hjje) {
		this.hjje = hjje;
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
	public String getYsfs() {
		return ysfs;
	}
	public void setYsfs(String ysfs) {
		this.ysfs = ysfs;
	}
	public Date getCz_date() {
		return cz_date;
	}
	public void setCz_date(Date cz_date) {
		this.cz_date = cz_date;
	}
	public String getWlzt() {
		return wlzt;
	}
	public void setWlzt(String wlzt) {
		this.wlzt = wlzt;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
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

}
