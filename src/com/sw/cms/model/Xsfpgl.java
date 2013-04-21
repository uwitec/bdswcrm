package com.sw.cms.model;

import java.sql.Timestamp;

/**
 * 销售开票管理
 * @author liyt
 *
 */
public class Xsfpgl {
	
	private String id;          //编号
	private String fplx;      //发票类型
	private String kpmc;   //开票名称
	private double fpje;  //发票金额
	private String kpdz;   //开票地址
	private String kpdh;   //开票电话
	private String khhzh;  //开户行账号
	private String sh;    //税号
	private String fpxxzy;   //发票信息摘要
	private String jy_jsr;     //交易经手人
	private String jy_date;  //交易日期
	private String kp_jsr;    //开票人
	private String kp_date;   //开票日期
	private String yw_type;  //对应业务(零售、销售、其他)
	private String yw_id;    //对应业务编号
	private String state;    //状态（待开、已开）
	private Timestamp cz_date;   //操作时间
	private String czr;    //操作人
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFplx() {
		return fplx;
	}
	public void setFplx(String fplx) {
		this.fplx = fplx;
	}
	public String getKpmc() {
		return kpmc;
	}
	public void setKpmc(String kpmc) {
		this.kpmc = kpmc;
	}
	public double getFpje() {
		return fpje;
	}
	public void setFpje(double fpje) {
		this.fpje = fpje;
	}
	public String getKpdz() {
		return kpdz;
	}
	public void setKpdz(String kpdz) {
		this.kpdz = kpdz;
	}
	public String getKpdh() {
		return kpdh;
	}
	public void setKpdh(String kpdh) {
		this.kpdh = kpdh;
	}
	public String getKhhzh() {
		return khhzh;
	}
	public void setKhhzh(String khhzh) {
		this.khhzh = khhzh;
	}
	public String getSh() {
		return sh;
	}
	public void setSh(String sh) {
		this.sh = sh;
	}
	public String getFpxxzy() {
		return fpxxzy;
	}
	public void setFpxxzy(String fpxxzy) {
		this.fpxxzy = fpxxzy;
	}
	public String getJy_jsr() {
		return jy_jsr;
	}
	public void setJy_jsr(String jy_jsr) {
		this.jy_jsr = jy_jsr;
	}
	public String getJy_date() {
		return jy_date;
	}
	public void setJy_date(String jy_date) {
		this.jy_date = jy_date;
	}
	public String getKp_jsr() {
		return kp_jsr;
	}
	public void setKp_jsr(String kp_jsr) {
		this.kp_jsr = kp_jsr;
	}
	public String getKp_date() {
		return kp_date;
	}
	public void setKp_date(String kp_date) {
		this.kp_date = kp_date;
	}
	public String getYw_type() {
		return yw_type;
	}
	public void setYw_type(String yw_type) {
		this.yw_type = yw_type;
	}
	public String getYw_id() {
		return yw_id;
	}
	public void setYw_id(String yw_id) {
		this.yw_id = yw_id;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Timestamp getCz_date() {
		return cz_date;
	}
	public void setCz_date(Timestamp cz_date) {
		this.cz_date = cz_date;
	}
	public String getCzr() {
		return czr;
	}
	public void setCzr(String czr) {
		this.czr = czr;
	}
	
}
