package com.sw.cms.model;

/**
 * 预收款，包括销售退货时应收转预收的部分
 * @author liyt
 *
 */
public class Yushouk {
	
	private int id;    //编号
	private String client_name;  //客户编号
	private double hjje;   //合计金额
	private double jsje;   //结算金额
	private String js_date;  //结算状态
	private String url;      //url
	private String ywdj_id;  //业务单据编号
	private String ywdj_name; //业务单据名称
	private String remark;    //备注
	private String czr;       //操作人
	private String cz_date;   //操作时间
	
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
	public double getHjje() {
		return hjje;
	}
	public void setHjje(double hjje) {
		this.hjje = hjje;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getJs_date() {
		return js_date;
	}
	public void setJs_date(String js_date) {
		this.js_date = js_date;
	}
	public double getJsje() {
		return jsje;
	}
	public void setJsje(double jsje) {
		this.jsje = jsje;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getYwdj_id() {
		return ywdj_id;
	}
	public void setYwdj_id(String ywdj_id) {
		this.ywdj_id = ywdj_id;
	}
	public String getYwdj_name() {
		return ywdj_name;
	}
	public void setYwdj_name(String ywdj_name) {
		this.ywdj_name = ywdj_name;
	}

}
