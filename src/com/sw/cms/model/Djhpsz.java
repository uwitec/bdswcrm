package com.sw.cms.model;

/*
 * �ҽ���Ʒ���ñ�
 * 2010-06-30 ���ӣ���������Ϣ����Ʒ��š�     ��Ʒ���ơ�        ��Ʒ���      ��λ���֡������ˡ�����ʱ��
 *                           product_id   product_name   product_xh   dwjf    czr    cz_date   
 */
public class Djhpsz {
	private String product_id; // ��Ʒ���

	private String product_name; // ��Ʒ����

	private String product_xh; // ��Ʒ���
	
	private String dwjf; // ��λ����

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
	
	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	
	public String getProduct_xh() {
		return product_xh;
	}

	public void setProduct_xh(String product_xh) {
		this.product_xh = product_xh;
	}

	public String getDwjf() {
		return dwjf;
	}

	public void setDwjf(String dwjf) {
		this.dwjf = dwjf;
	}
}
