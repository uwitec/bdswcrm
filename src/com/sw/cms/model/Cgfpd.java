package com.sw.cms.model;

import java.util.Date;
/**
 * 
 * @author zhj
 * �ɹ���Ʊ��
 */
public class Cgfpd {
	
	private String id;   //�ɹ���Ʊ���
	private String cg_date; //�ɹ�����
	private String gysbh;//��Ӧ�̱��
	private String jhd_id; //���������
	private double total;  //���������
	private String state;  //״̬	����⡢δ���
	private String czr;    //������
	private Date cz_date;
	private String ms;    //������Ϣ	
	
	public String getCg_date() {
		return cg_date;
	}
	public void setCg_date(String cg_date) {
		this.cg_date = cg_date;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public String getJhd_id() {
		return jhd_id;
	}
	public void setJhd_id(String jhd_id) {
		this.jhd_id = jhd_id;
	}
	public String getGysbh() {
		return gysbh;
	}
	public void setGysbh(String gysbh) {
		this.gysbh = gysbh;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCzr() {
		return czr;
	}
	public void setCzr(String czr) {
		this.czr = czr;
	}
	
	public Date getCz_date() {
		return cz_date;
	}
	public void setCz_date(Date czDate) {
		cz_date = czDate;
	}
	public String getMs() {
		return ms;
	}
	public void setMs(String ms) {
		this.ms = ms;
	}
}
