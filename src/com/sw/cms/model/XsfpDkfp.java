package com.sw.cms.model;

import java.sql.Timestamp;

/**
 * 销售发票管理-代开发票清单（包括销售订单及零售单）
* 销售订单出库后生成代开票信息
 * @author liyt
 *
 */
public class XsfpDkfp {
	
	private String id;          //编号（流水号）
	private String yw_type;  //对应业务(零售、销售)
	private String yw_id;    //对应业务编号
	private String khmc;    //客户名称
	private double ddje;   //订单金额
	private double ykpje; //已开票金额
	
	private String fplx;      //发票类型
	private String kpmc;   //开票名称
	private String kpdz;   //开票地址
	private String kpdh;   //开票电话
	private String khhzh;  //开户行账号
	private String sh;    //税号
	private String fpxxzy;   //发票信息摘要
	
	private String jy_jsr;     //交易经手人
	private String jy_date;  //交易日期

	private String state;    //状态（待开、部分已开、已开）
	
	private Timestamp cz_date;   //操作时间
	private String czr;    //操作人
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getKhmc() {
		return khmc;
	}
	public void setKhmc(String khmc) {
		this.khmc = khmc;
	}
	public double getDdje() {
		return ddje;
	}
	public void setDdje(double ddje) {
		this.ddje = ddje;
	}
	public double getYkpje() {
		return ykpje;
	}
	public void setYkpje(double ykpje) {
		this.ykpje = ykpje;
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
