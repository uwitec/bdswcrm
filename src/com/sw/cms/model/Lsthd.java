package com.sw.cms.model;

public class Lsthd {
	
	private String id;            //编号
	private String client_name;   //客户名称
	private String th_fzr;        //退货负责人
	private String store_id;      //库房编号
	private String state;         //状态
	private double thdje;         //退货单金额
	private String tkzh;          //退款账户
	private String lsd_id;        //对应零售单编号
	private String czr;           //操作人
	private String cz_date;       //操作日期
	private String th_date;       //退货日期
	private String remark;        //备注
	
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLsd_id() {
		return lsd_id;
	}
	public void setLsd_id(String lsd_id) {
		this.lsd_id = lsd_id;
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
	public String getStore_id() {
		return store_id;
	}
	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}
	public String getTh_date() {
		return th_date;
	}
	public void setTh_date(String th_date) {
		this.th_date = th_date;
	}
	public String getTh_fzr() {
		return th_fzr;
	}
	public void setTh_fzr(String th_fzr) {
		this.th_fzr = th_fzr;
	}
	public double getThdje() {
		return thdje;
	}
	public void setThdje(double thdje) {
		this.thdje = thdje;
	}
	public String getTkzh() {
		return tkzh;
	}
	public void setTkzh(String tkzh) {
		this.tkzh = tkzh;
	}

}
