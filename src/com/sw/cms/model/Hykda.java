package com.sw.cms.model;

/*
 * 会员卡档案
 */
public class Hykda {
	private String id;   //档案编号
	private String hykh; //会员卡号

	private String hymc;    //名称（个人会员存姓名，机构会员存单位编号）
	private String lxrname; // 联系人
	private String lxdh;    // 联系电话
	private String mobile;  // 手机
	private String address; // 地址
	private String mail;    // E-Mail
	private String sfzh;    //证件证号
    private String sex;     //性别
	private String gzdw;    //工作单位
	private String birth;    //出生日期
	
	private String fkrq;  //发卡日期
	private String fkjsr; //发卡经手人
	private String fkbz;  //备注
	
	private String state;   //会员状态：正常、已注销
	
	private String cz_date; //操作时间
	private String czr;    // 操作人
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getHykh() {
		return hykh;
	}
	public void setHykh(String hykh) {
		this.hykh = hykh;
	}
	public String getLxrname() {
		return lxrname;
	}
	public void setLxrname(String lxrname) {
		this.lxrname = lxrname;
	}
	public String getLxdh() {
		return lxdh;
	}
	public void setLxdh(String lxdh) {
		this.lxdh = lxdh;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getSfzh() {
		return sfzh;
	}
	public void setSfzh(String sfzh) {
		this.sfzh = sfzh;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getGzdw() {
		return gzdw;
	}
	public void setGzdw(String gzdw) {
		this.gzdw = gzdw;
	}
	public String getBirth() {
		return birth;
	}
	public void setBirth(String birth) {
		this.birth = birth;
	}
	public String getFkrq() {
		return fkrq;
	}
	public void setFkrq(String fkrq) {
		this.fkrq = fkrq;
	}
	public String getFkjsr() {
		return fkjsr;
	}
	public void setFkjsr(String fkjsr) {
		this.fkjsr = fkjsr;
	}
	public String getFkbz() {
		return fkbz;
	}
	public void setFkbz(String fkbz) {
		this.fkbz = fkbz;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCz_date() {
		return cz_date;
	}
	public void setCz_date(String czDate) {
		cz_date = czDate;
	}
	public String getCzr() {
		return czr;
	}
	public void setCzr(String czr) {
		this.czr = czr;
	}
	public String getHymc() {
		return hymc;
	}
	public void setHymc(String hymc) {
		this.hymc = hymc;
	}
}
