package com.sw.cms.model;

/*
 * ��Ա��������
 * 2010-06-07 ���ӣ���������Ϣ����š������͡�      ������  ��ʼ���֡���ʼ���롢��ʼ����Ч���ڡ�ʧЧ���ڡ� �ɳ�ֵ�� ������   �������ڡ�  ��Ա���š� ��������
 *                           id   card_type  dept   csjf    csmm     csje    yxrq    sxrq    sfcz    czr    cz_date   hykh     ssfl
 */
public class Hykzz {
	private String id; // ���

	private String card_type; // ������
	
	private String dept; // ����
	
	private int csjf; //��ʼ����
	
	private String csmm; // ��ʼ����
	
	private double csje; // ��ʼ���

	private String  yxrq; // ��Ч����	
	
	private String sxrq; // ʧЧ����
	
	private String sfcz; // �ɳ�ֵ

	private String czr; // ������

	private String cz_date; // ����ʱ��

	private String hykh; //��Ա����
	
	private String ssfl; //��������
	
	
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
