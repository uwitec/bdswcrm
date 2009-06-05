package com.sw.cms.model;

public class Xssk {
	
	private String id;     //编号
	private String sk_date;  //收款日期
	private String client_name;  //客户名称
	private String skzh;    //收款账户
	private String jsr;     //经手人
	private double skje = 0; //收款金额
	private String state;    //状态
	private String remark;	 //备注
	private String is_ysk;//是否预收款
	private double ysk_ye = 0; //预收款余额
	private String czr; //操作人
	
	//删除关键字
	//在销售单发生提交时如果发生了收款，需要将销售单ID写入
	//在发生销售现金退货时，需要将销售退货单ID写入
	private String delete_key = "";
	
	public String getClient_name() {
		return client_name;
	}
	public void setClient_name(String client_name) {
		this.client_name = client_name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getJsr() {
		return jsr;
	}
	public void setJsr(String jsr) {
		this.jsr = jsr;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getSk_date() {
		return sk_date;
	}
	public void setSk_date(String sk_date) {
		this.sk_date = sk_date;
	}
	public double getSkje() {
		return skje;
	}
	public void setSkje(double skje) {
		this.skje = skje;
	}
	public String getSkzh() {
		return skzh;
	}
	public void setSkzh(String skzh) {
		this.skzh = skzh;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getIs_ysk() {
		return is_ysk;
	}
	public void setIs_ysk(String is_ysk) {
		this.is_ysk = is_ysk;
	}
	public double getYsk_ye() {
		return ysk_ye;
	}
	public void setYsk_ye(double ysk_ye) {
		this.ysk_ye = ysk_ye;
	}
	public String getCzr() {
		return czr;
	}
	public void setCzr(String czr) {
		this.czr = czr;
	}
	public String getDelete_key() {
		return delete_key;
	}
	public void setDelete_key(String delete_key) {
		this.delete_key = delete_key;
	}

}
