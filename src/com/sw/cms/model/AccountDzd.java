package com.sw.cms.model;

public class AccountDzd {
	
	private int id;
	private String account_id;
	private double jyje;  //交易金额
	private double zhye;  //账户余额(不再使用，账户余额，根据期初值及交易金额计算)
	private String remark; //说明
	private String jy_date;//交易日期
	private String jsr;//经手人
	private String czr;//操作人
	private String action_url;//打开单据页面所需URL，例如："viewJhd.htm?id=JH00000001"
	
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
	public String getJsr() {
		return jsr;
	}
	public void setJsr(String jsr) {
		this.jsr = jsr;
	}
	public String getJy_date() {
		return jy_date;
	}
	public void setJy_date(String jy_date) {
		this.jy_date = jy_date;
	}
	public double getJyje() {
		return jyje;
	}
	public void setJyje(double jyje) {
		this.jyje = jyje;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public double getZhye() {
		return zhye;
	}
	public void setZhye(double zhye) {
		this.zhye = zhye;
	}
	public String getAccount_id() {
		return account_id;
	}
	public void setAccount_id(String account_id) {
		this.account_id = account_id;
	}
	public String getAction_url() {
		return action_url;
	}
	public void setAction_url(String action_url) {
		this.action_url = action_url;
	}

}
