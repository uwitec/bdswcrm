package com.sw.cms.model;

import java.sql.Timestamp;

/**
 * ��Ա��������ˮ
 * @author liyt
 *
 */
public class HykJfFlow {
	
	private int seq_id;      //��ˮ��
	private String hyk_id;  //��Ա�����
	private String yw_id;   //ҵ����
	private double xfje;   //���ѽ��
	private double jf;      //����
	private Timestamp cz_date;  //����ʱ��
	private String czr;      //������
	private String jsr;       //������
	
	public String getJsr() {
		return jsr;
	}
	public void setJsr(String jsr) {
		this.jsr = jsr;
	}
	public int getSeq_id() {
		return seq_id;
	}
	public void setSeq_id(int seq_id) {
		this.seq_id = seq_id;
	}
	public String getHyk_id() {
		return hyk_id;
	}
	public void setHyk_id(String hyk_id) {
		this.hyk_id = hyk_id;
	}
	public String getYw_id() {
		return yw_id;
	}
	public void setYw_id(String yw_id) {
		this.yw_id = yw_id;
	}
	public double getXfje() {
		return xfje;
	}
	public void setXfje(double xfje) {
		this.xfje = xfje;
	}
	public double getJf() {
		return jf;
	}
	public void setJf(double jf) {
		this.jf = jf;
	}
	public String getCzr() {
		return czr;
	}
	public void setCzr(String czr) {
		this.czr = czr;
	}
	
	public Timestamp getCz_date() {
		return cz_date;
	}
	public void setCz_date(Timestamp cz_date) {
		this.cz_date = cz_date;
	}
}
