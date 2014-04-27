package com.sw.cms.model;

import java.sql.Timestamp;

/**
 * 销售发票管理-发票信息
 * @author liyt
 *
 */
public class XsfpFpxx {
	
	private String id;   //单号
	private String khmc;   //客户名称
	private String fplx;     //发票类型
	private String fph;     //发票号
	private double fpje;  //发票金额
	private String kpr;     //开票人
	private String remark;   //备注
	private String kprq;      //开票日期
	private String czr;          //操作人
	private String state;     //状态，(已保存、已提交)
	private Timestamp cz_date;   //操作时间 
	
	private double fpje_bdd;  //发票金额不对单
	private String remark_bdd;  //备注，不对单
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getKhmc() {
		return khmc;
	}
	public void setKhmc(String khmc) {
		this.khmc = khmc;
	}
	public String getFplx() {
		return fplx;
	}
	public void setFplx(String fplx) {
		this.fplx = fplx;
	}
	public String getFph() {
		return fph;
	}
	public void setFph(String fph) {
		this.fph = fph;
	}
	public double getFpje() {
		return fpje;
	}
	public void setFpje(double fpje) {
		this.fpje = fpje;
	}
	public String getKpr() {
		return kpr;
	}
	public void setKpr(String kpr) {
		this.kpr = kpr;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getKprq() {
		return kprq;
	}
	public void setKprq(String kprq) {
		this.kprq = kprq;
	}
	public String getCzr() {
		return czr;
	}
	public void setCzr(String czr) {
		this.czr = czr;
	}
	public Timestamp getCz_date() {
		return cz_date;
	}
	public void setCz_date(Timestamp cz_date) {
		this.cz_date = cz_date;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public double getFpje_bdd() {
		return fpje_bdd;
	}
	public void setFpje_bdd(double fpje_bdd) {
		this.fpje_bdd = fpje_bdd;
	}
	public String getRemark_bdd() {
		return remark_bdd;
	}
	public void setRemark_bdd(String remark_bdd) {
		this.remark_bdd = remark_bdd;
	}

}
