package com.sw.cms.model;

/*
 * ��Ա�������
 * 2010-06-03 ���ӣ���������Ϣ����š����ơ��Żݷ�ʽ���ۿ��ʡ����ַ�ʽ����ֵ�Ż��ʡ������ˡ�����ʱ��
 *                           id   name  yhfs   zkl   jffs     czyhl    czr    cz_date   
 */
public class Hykfl {
	private String id; // ���

	private String name; // ����

	private double zkl; // �ۿ���

	private String yhfs; // �Żݷ�ʽ
	
	private double czyhl; // ��ֵ�Ż���

	private String jffs; // ���ַ�ʽ

	private String czr; // ������

	private String cz_date; // ����ʱ��



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
