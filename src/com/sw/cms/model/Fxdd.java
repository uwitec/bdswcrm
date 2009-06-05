package com.sw.cms.model;

import java.util.Date;

public class Fxdd {
	
	private String fxdd_id;      //编号
	private String creatdate;    //创建时间
	private String client_name;  //客户编号
	private String address;      //地址
	private String lxr;          //联系人
	private String lxdh;         //联系电话
	private String ysfs;         //运输方式
	private double hjje;         //合计金额
	private String state;        //状态
	private String remark;       //备注
	private Date cz_date;        //操作时间
	private String wlzt;         //物流状态
	
	private String cx_tel;      //查询电话
	private String job_no;      //货单号
	private String send_time;   //发货时间
	
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
