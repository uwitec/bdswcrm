package com.sw.cms.model;

/*
 * ��Ա�����ֱ�
 * 2010-06-22 ���ӣ���������Ϣ����Ա���š���Ա���ơ� ��Ա���   �ܻ���    ʵ�ʻ���
 *                            hykh   hymc     hybh     zjf      ssjf
 */
public class Hykjf {
	
	private String hykh; // ��Ա����
	
	private String kymc; // ��Ա����
	
	private String hybh; //��Ա���
	
	private int zjf; //�ܻ���
	
    private int ssjf; //ʵ�ʻ���
	
	
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
