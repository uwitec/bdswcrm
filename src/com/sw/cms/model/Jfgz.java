package com.sw.cms.model;

/*
 * ���ֹ�������
 * 2010-06-03 ���ӣ���������Ϣ����š����ַ��������ѽ���Ӧ���֡������ˡ�����ʱ��
 *                           id   jfff  xfje  dyjf  czr    cz_date   
 */
public class Jfgz {
	private String id; // ���

	private String jfff; // ���ַ���

	private int xfje; // ���ѽ��

	private int dyjf; // ��Ӧ����

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

	public String getJfff() {
		return jfff;
	}

	public void setJfff(String jfff) {
		this.jfff = jfff;
	}

	public int getXfje() {
		return xfje;
	}

	public void setXfje(int xfje) {
		this.xfje = xfje;
	}

	public int getDyjf() {
		return dyjf;
	}

	public void setDyjf(int dyjf) {
		this.dyjf = dyjf;
	}

}
