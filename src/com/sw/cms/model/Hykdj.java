package com.sw.cms.model;

/*
 * ��Ա���ҽ�
 * 2010-06-22 ���ӣ���������Ϣ����Ա���š���Ա���ơ� ��Ա���    ʵ�ʻ���    ��Ʒ����   ��Ʒ����   ��ʣ����   ���    ������   ��������
 *                            hykh   hymc     hybh      sjjf      jpmc      jpsl    ssjf     id     czr     cz_date
 */
public class Hykdj {
	
	private String hykh; // ��Ա����
	
	private String kymc; // ��Ա����
	
	private String hybh; //��Ա���
	
	private int sjjf; //ʵ�ʻ���
	
    private int ssjf; //��ʣ����
    
    private String jpmc; // ��Ʒ����
	
	private String id; // ���
	
	private int jpsl; //��Ա���
	
	private String czr; // ������

	private String cz_date; // ����ʱ��
	
	
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
