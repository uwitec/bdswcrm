package com.sw.cms.model;

/*
 * 兑奖货品设置表
 * 2010-06-30 增加，有以下信息：产品编号、     产品名称、        产品规格、      单位积分、操作人、操作时间
 *                           product_id   product_name   product_xh   dwjf    czr    cz_date   
 */
public class Djhpsz {
	private String product_id; // 产品编号

	private String product_name; // 产品名称

	private String product_xh; // 产品规格
	
	private String dwjf; // 单位积分

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
	
	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	
	public String getProduct_xh() {
		return product_xh;
	}

	public void setProduct_xh(String product_xh) {
		this.product_xh = product_xh;
	}

	public String getDwjf() {
		return dwjf;
	}

	public void setDwjf(String dwjf) {
		this.dwjf = dwjf;
	}
}
