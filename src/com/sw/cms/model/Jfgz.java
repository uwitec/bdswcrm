package com.sw.cms.model;

/*
 * 积分规则设置
 * 2010-06-03 增加，有以下信息：编号、积分方法、消费金额、对应积分、操作人、操作时间
 *                           id   jfff  xfje  dyjf  czr    cz_date   
 */
public class Jfgz {
	private String id; // 编号

	private String jfff; // 积分方法

	private String xfje; // 消费金额

	private String dyjf; // 对应积分

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

	public String getJfff() {
		return jfff;
	}

	public void setJfff(String jfff) {
		this.jfff = jfff;
	}

	public String getXfje() {
		return xfje;
	}

	public void setXfje(String xfje) {
		this.xfje = xfje;
	}

	public String getDyjf() {
		return dyjf;
	}

	public void setDyjf(String dyjf) {
		this.dyjf = dyjf;
	}

}
