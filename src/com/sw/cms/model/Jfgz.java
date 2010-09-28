package com.sw.cms.model;

import java.util.Date;

/*
 * 积分规则设置
 * 2010-06-03 增加，有以下信息：编号、积分方法、消费金额、对应积分、操作人、操作时间
 *                           id   jfff  xfje  dyjf  czr    cz_date   
 */
public class Jfgz {
	private String id; // 编号

	private String jfff; // 积分方法

	private double xfje; // 消费金额

	private double dyjf; // 对应积分

	private String czr; // 操作人

	private Date cz_date; // 操作时间

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

	public String getJfff() {
		return jfff;
	}

	public void setJfff(String jfff) {
		this.jfff = jfff;
	}

	public double getXfje() {
		return xfje;
	}

	public void setXfje(double xfje) {
		this.xfje = xfje;
	}

	public double getDyjf() {
		return dyjf;
	}

	public void setDyjf(double dyjf) {
		this.dyjf = dyjf;
	}

	public Date getCz_date() {
		return cz_date;
	}

	public void setCz_date(Date czDate) {
		cz_date = czDate;
	}
}
