package com.sw.cms.model;

/*
 * 会员卡分类表
 * 2010-06-03 增加，有以下信息：编号、名称、优惠方式、折扣率、积分方式、充值优惠率、操作人、操作时间
 *                           id   name  yhfs   zkl   jffs     czyhl    czr    cz_date   
 */
public class Hykfl {
	private String id; // 编号

	private String name; // 名称

	private double zkl; // 折扣率

	private String yhfs; // 优惠方式
	
	private double czyhl; // 充值优惠率

	private String jffs; // 积分方式

	private String czr; // 操作人

	private String cz_date; // 操作时间



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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getZkl() {
		return zkl;
	}

	public void setZkl(double zkl) {
		this.zkl = zkl;
	}

	public String getYhfs() {
		return yhfs;
	}

	public void setYhfs(String yhfs) {
		this.yhfs = yhfs;
	}

	public double getCzyhl() {
		return czyhl;
	}

	public void setCzyhl(double czyhl) {
		this.czyhl = czyhl;
	}

	public String getJffs() {
		return jffs;
	}

	public void setJffs(String jffs) {
		this.jffs = jffs;
	}
}
