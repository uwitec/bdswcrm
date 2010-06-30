package com.sw.cms.model;

/*
 * 会员卡积分表
 * 2010-06-22 增加，有以下信息：会员卡号、会员名称、 会员编号   总积分    实际积分
 *                            hykh   hymc     hybh     zjf      ssjf
 */
public class Hykjf {
	
	private String hykh; // 会员卡号
	
	private String kymc; // 会员名称
	
	private String hybh; //会员编号
	
	private int zjf; //总积分
	
    private int ssjf; //实际积分
	
	
	public int getZjf() {
		return zjf;
	}

	public void setZjf(int zjf) {
		this.zjf = zjf;
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
}
