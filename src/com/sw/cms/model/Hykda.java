package com.sw.cms.model;

/*
 * 会员卡档案
 * 2010-06-12 增加，有以下信息：编号、会员卡号、状态、    是否停用、会员名称、联系人、  联系电话  手机       会员编号    发卡备注
 *                           id   hykh    state   sfty     hymc   lxrname  lxdh    mobile   hybh     fkbz
 *                           地址       mail  操作人、操作时间  身份证号  发放机构  发卡日期  发卡经手人    制卡日期    制卡经手人
 *                           address  mail   czr  cz_date   sfzh   ffjg     fkrq    fkjsr       zkrq      zkjsr
 */
public class Hykda {
	private String id; // 编号

	private String hykh; // 会员卡号

	private String state; // 领用状态：已领用、未使用

	private String sfty; // 是否停用：是、否
	
	private String kymc; // 会员名称

	private String lxrname; // 联系人
	
	private String lxdh; // 联系电话

	private String mobile; // 手机
	
	private String address; // 地址

	private String mail; // E-Mail

	private String czr; // 操作人

	private String cz_date; // 操作时间

	private String sfzh; //身份证号
	
	private String ffjg; //发放机构
	
	private String fkrq; //发卡日期
	
	private String fkjsr; //发卡经手人
	
    private String zkrq; //制卡日期
	
	private String zkjsr; //制卡经手人
	
	private String hybh; //会员编号
	
	private String fkbz; //发卡备注
	
	
	public String getFkbz() {
		return fkbz;
	}

	public void setFkbz(String fkbz) {
		this.fkbz = fkbz;
	}
	
	public String getHybh() {
		return hybh;
	}

	public void setHybh(String hybh) {
		this.hybh = hybh;
	}
	
	public String getZkjsr() {
		return zkjsr;
	}

	public void setZkjsr(String zkjsr) {
		this.zkjsr = zkjsr;
	}
	
	public String getFkjsr() {
		return fkjsr;
	}

	public void setFkjsr(String fkjsr) {
		this.fkjsr = fkjsr;
	}
	
	
	public String getZkrq() {
		return zkrq;
	}

	public void setZkrq(String zkrq) {
		this.zkrq = zkrq;
	}
	
	public String getFkrq() {
		return fkrq;
	}

	public void setFkrq(String fkrq) {
		this.fkrq = fkrq;
	}
	
	public String getFfjg() {
		return ffjg;
	}

	public void setFfjg(String ffjg) {
		this.ffjg = ffjg;
	}
	
	public String getSfzh() {
		return sfzh;
	}

	public void setSfzh(String sfzh) {
		this.sfzh = sfzh;
	}
	
	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public String getLxdh() {
		return lxdh;
	}

	public void setLxdh(String lxdh) {
		this.lxdh = lxdh;
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

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getSfty() {
		return sfty;
	}

	public void setSfty(String sfty) {
		this.sfty = sfty;
	}

	public String getHymc() {
		return kymc;
	}

	public void setHymc(String hymc) {
		this.kymc = hymc;
	}

	public String getLxrname() {
		return lxrname;
	}

	public void setLxrname(String lxrname) {
		this.lxrname = lxrname;
	}
}
