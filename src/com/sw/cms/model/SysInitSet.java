package com.sw.cms.model;

/**
 * <p>ϵͳ��ʼ��</p>
 * <p>1.����ϵͳ��������</p>
 * <p>2.���ó�ʼ�������</p>
 * <p>2.���ó�ʼ��ɱ�־</p>
 * @author liyt
 *
 */
public class SysInitSet {
	
	private int id;             //���
	private String qyrq;         //ϵͳ��������
	private String cswcrq;       //��ʼ�������
	private String flag;         //�Ƿ���ɳ�ʼ��־ 0��δ��ɣ�1�������
	private String czr;          //������
	private String cz_date;      //����ʱ��
	
	public String getCswcrq() {
		return cswcrq;
	}
	public void setCswcrq(String cswcrq) {
		this.cswcrq = cswcrq;
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
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getQyrq() {
		return qyrq;
	}
	public void setQyrq(String qyrq) {
		this.qyrq = qyrq;
	}

}
