package com.sw.cms.model;

import java.util.Date;

/*
 * ���ֹ�������
 * 2010-06-03 ���ӣ���������Ϣ����š����ַ��������ѽ���Ӧ���֡������ˡ�����ʱ��
 *                           id   jfff  xfje  dyjf  czr    cz_date   
 */
public class Jfgz {
	private String id; // ���

	private String jfff; // ���ַ���

	private double xfje; // ���ѽ��

	private double dyjf; // ��Ӧ����

	private String czr; // ������

	private Date cz_date; // ����ʱ��

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
