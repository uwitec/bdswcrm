package com.sw.cms.model;

/*
 * 会员卡兑奖
 * 2010-06-22 增加，有以下信息：会员卡号、会员名称、 会员编号    实际积分    奖品名称   奖品数量   所剩积分   编号    操作人   操作日期
 *                            hykh   hymc     hybh      sjjf      jpmc      jpsl    ssjf     id     czr     cz_date
 */
public class Hykdj {
	
	private String hykh; // 会员卡号
	
	private String kymc; // 会员名称
	
	private String hybh; //会员编号
	
	private int sjjf; //实际积分
	
    private int ssjf; //所剩积分
    
    private String jpmc; // 奖品名称
	
	private String id; // 编号
	
	private int jpsl; //会员编号
	
	private String czr; // 操作人

	private String cz_date; // 操作时间
	
	
	public int getSjjf() {
		return sjjf;
	}

	public void setSjjf(int sjjf) {
		this.sjjf = sjjf;
	}
	
	public String getHybh() {
		return hybh;
	}

	public void setHybh(String hybh) {
		this.hybh = hybh;
	}
	
	public int getSsjf() {
		return ssjf;
	}

	public void setSsjf(int ssjf) {
		this.ssjf = ssjf;
	}
	
	public String getHykh() {
		return hykh;
	}

	public void setHykh(String hykh) {
		this.hykh = hykh;
	}
	
	public String getHymc() {
		return kymc;
	}

	public void setHymc(String hymc) {
		this.kymc = hymc;
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
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getJpmc() {
		return jpmc;
	}

	public void setJpmc(String jpmc) {
		this.jpmc = jpmc;
	}
	
	public int getJpsl() {
		return jpsl;
	}

	public void setJpsl(int jpsl) {
		this.jpsl = jpsl;
	}
}
