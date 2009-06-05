package com.sw.cms.model;

public class Qtzc {
	
	private String id;       //编号
	private String zc_date;  //支出日期
	private String type;     //支出类型
	private double zcje;     //支出金额
	private String zczh;     //支出账户
	private String fklx;     //付款类型
	private String jsr;      //出纳
	private String ywy;      //业务员
	private String zcxm;     //支出项目
	private String remark;   //备注
	private String czr;      //操作人
	
	private String spr;     //审批人
	private String sp_date; //审批时间
	private String fysq_id; //对应费用申请单编号
	
	private String state;
	
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getZc_date() {
		return zc_date;
	}
	public void setZc_date(String zc_date) {
		this.zc_date = zc_date;
	}
	public double getZcje() {
		return zcje;
	}
	public void setZcje(double zcje) {
		this.zcje = zcje;
	}
	public String getZczh() {
		return zczh;
	}
	public void setZczh(String zczh) {
		this.zczh = zczh;
	}
	public String getCzr() {
		return czr;
	}
	public void setCzr(String czr) {
		this.czr = czr;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getYwy() {
		return ywy;
	}
	public void setYwy(String ywy) {
		this.ywy = ywy;
	}
	public String getZcxm() {
		return zcxm;
	}
	public void setZcxm(String zcxm) {
		this.zcxm = zcxm;
	}
	public String getSp_date() {
		return sp_date;
	}
	public void setSp_date(String sp_date) {
		this.sp_date = sp_date;
	}
	public String getSpr() {
		return spr;
	}
	public void setSpr(String spr) {
		this.spr = spr;
	}
	public String getFklx() {
		return fklx;
	}
	public void setFklx(String fklx) {
		this.fklx = fklx;
	}
	public String getFysq_id() {
		return fysq_id;
	}
	public void setFysq_id(String fysq_id) {
		this.fysq_id = fysq_id;
	}

}
