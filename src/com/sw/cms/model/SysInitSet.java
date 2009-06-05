package com.sw.cms.model;

/**
 * <p>系统初始类</p>
 * <p>1.设置系统启用日期</p>
 * <p>2.设置初始完成日期</p>
 * <p>2.设置初始完成标志</p>
 * @author liyt
 *
 */
public class SysInitSet {
	
	private int id;             //编号
	private String qyrq;         //系统启用日期
	private String cswcrq;       //初始完成日期
	private String flag;         //是否完成初始标志 0：未完成；1：已完成
	private String czr;          //操作人
	private String cz_date;      //操作时间
	
	public String getCswcrq() {
		return cswcrq;
	}
	public void setCswcrq(String cswcrq) {
		this.cswcrq = cswcrq;
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
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getQyrq() {
		return qyrq;
	}
	public void setQyrq(String qyrq) {
		this.qyrq = qyrq;
	}

}
