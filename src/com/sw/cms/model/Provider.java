package com.sw.cms.model;

import java.util.Date;

/**
 * 供应商类
 * @author liyt
 * create at 2008-03-17
 */
public class Provider {

	private String id;
	private String name;
	private String lxr;
	private String phone;
	private String fax;
	private String mobile;
	private String mail;
	private String web;
	private String address;
	private String pcode;
	private String ygs;
	private String gsxz;
	private Date creattime;
	private Date modtime;
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Date getCreattime() {
		return creattime;
	}
	public void setCreattime(Date creattime) {
		this.creattime = creattime;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getGsxz() {
		return gsxz;
	}
	public void setGsxz(String gsxz) {
		this.gsxz = gsxz;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLxr() {
		return lxr;
	}
	public void setLxr(String lxr) {
		this.lxr = lxr;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Date getModtime() {
		return modtime;
	}
	public void setModtime(Date modtime) {
		this.modtime = modtime;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPcode() {
		return pcode;
	}
	public void setPcode(String pcode) {
		this.pcode = pcode;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getWeb() {
		return web;
	}
	public void setWeb(String web) {
		this.web = web;
	}
	public String getYgs() {
		return ygs;
	}
	public void setYgs(String ygs) {
		this.ygs = ygs;
	}
	
}
