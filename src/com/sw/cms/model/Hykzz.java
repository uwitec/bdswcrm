package com.sw.cms.model;

/*
 * 会员卡制作表
 * 2010-06-07 增加，有以下信息：编号、卡类型、      机构、  初始积分、初始密码、初始金额、有效日期、失效日期、 可充值、 操作人   操作日期‘  会员卡号、 所属分类
 *                           id   card_type  dept   csjf    csmm     csje    yxrq    sxrq    sfcz    czr    cz_date   hykh     ssfl
 */
public class Hykzz {
	private String id; // 编号

	private String card_type; // 卡类型
	
	private String dept; // 机构
	
	private int csjf; //初始积分
	
	private String csmm; // 初始密码
	
	private double csje; // 初始金额

	private String  yxrq; // 有效日期	
	
	private String sxrq; // 失效日期
	
	private String sfcz; // 可充值

	private String czr; // 操作人

	private String cz_date; // 操作时间

	private String hykh; //会员卡号
	
	private String ssfl; //所属分类
	
	
	public String getSsfl() {
		return ssfl;
	}

	public void setSsfl(String ssfl) {
		this.ssfl = ssfl;
	}
	
	public String getHykh() {
		return hykh;
	}

	public void setHykh(String hykh) {
		this.hykh = hykh;
	}
	
	
	public int getCsjf() {
		return csjf;
	}

	public void setCsjf(int csjf) {
		this.csjf = csjf;
	}
	
	public String getSfcz() {
		return sfcz;
	}

	public void setSfcz(String sfcz) {
		this.sfcz = sfcz;
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

	public String getCard_type() {
		return card_type;
	}

	public void setCard_type(String card_type) {
		this.card_type = card_type;
	}

	public double getCsje() {
		return csje;
	}

	public void setCsje(double csje) {
		this.csje = csje;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getCsmm() {
		return csmm;
	}

	public void setCsmm(String csmm) {
		this.csmm = csmm;
	}

	public String getYxrq() {
		return yxrq;
	}

	public void setYxrq(String yxrq) {
		this.yxrq = yxrq;
	}
	
	public String getSxrq() {
		return sxrq;
	}

	public void setSxrq(String sxrq) {
		this.sxrq = sxrq;
	}
}
