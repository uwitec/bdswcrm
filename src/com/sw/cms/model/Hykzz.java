package com.sw.cms.model;

import java.util.Date;

/*
 * ��Ա��������
*/
public class Hykzz {
	private String id; // ���

	private String card_type; // ������

	private String dept; // ����

	private int csjf; // ��ʼ����

	private String csmm; // ��ʼ����

	private String yxrq; // ��Ч����

	private String sxrq; // ʧЧ����

	private String czr; // ������

	private Date cz_date; // ����ʱ��

	private String hykh; // ��Ա����

	private String ssfl; // ��������
	
	private String state; //״̬   δʹ��,��ʹ��

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

	public Date getCz_date() {
		return cz_date;
	}

	public void setCz_date(Date czDate) {
		cz_date = czDate;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
}
