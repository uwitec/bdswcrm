package com.sw.cms.model;

import java.sql.Timestamp;

/**
 * ���۷�Ʊ����-������Ʊ�嵥���������۶��������۵���
* ���۶�����������ɴ���Ʊ��Ϣ
 * @author liyt
 *
 */
public class XsfpDkfp {
	
	private String id;          //��ţ���ˮ�ţ�
	private String yw_type;  //��Ӧҵ��(���ۡ�����)
	private String yw_id;    //��Ӧҵ����
	private String khmc;    //�ͻ�����
	private double ddje;   //�������
	private double ykpje; //�ѿ�Ʊ���
	
	private String fplx;      //��Ʊ����
	private String kpmc;   //��Ʊ����
	private String kpdz;   //��Ʊ��ַ
	private String kpdh;   //��Ʊ�绰
	private String khhzh;  //�������˺�
	private String sh;    //˰��
	private String fpxxzy;   //��Ʊ��ϢժҪ
	
	private String jy_jsr;     //���׾�����
	private String jy_date;  //��������

	private String state;    //״̬�������������ѿ����ѿ���
	
	private Timestamp cz_date;   //����ʱ��
	private String czr;    //������
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getYw_type() {
		return yw_type;
	}
	public void setYw_type(String yw_type) {
		this.yw_type = yw_type;
	}
	public String getYw_id() {
		return yw_id;
	}
	public void setYw_id(String yw_id) {
		this.yw_id = yw_id;
	}
	public String getKhmc() {
		return khmc;
	}
	public void setKhmc(String khmc) {
		this.khmc = khmc;
	}
	public double getDdje() {
		return ddje;
	}
	public void setDdje(double ddje) {
		this.ddje = ddje;
	}
	public double getYkpje() {
		return ykpje;
	}
	public void setYkpje(double ykpje) {
		this.ykpje = ykpje;
	}
	public String getFplx() {
		return fplx;
	}
	public void setFplx(String fplx) {
		this.fplx = fplx;
	}
	public String getKpmc() {
		return kpmc;
	}
	public void setKpmc(String kpmc) {
		this.kpmc = kpmc;
	}
	public String getKpdz() {
		return kpdz;
	}
	public void setKpdz(String kpdz) {
		this.kpdz = kpdz;
	}
	public String getKpdh() {
		return kpdh;
	}
	public void setKpdh(String kpdh) {
		this.kpdh = kpdh;
	}
	public String getKhhzh() {
		return khhzh;
	}
	public void setKhhzh(String khhzh) {
		this.khhzh = khhzh;
	}
	public String getSh() {
		return sh;
	}
	public void setSh(String sh) {
		this.sh = sh;
	}
	public String getFpxxzy() {
		return fpxxzy;
	}
	public void setFpxxzy(String fpxxzy) {
		this.fpxxzy = fpxxzy;
	}
	public String getJy_jsr() {
		return jy_jsr;
	}
	public void setJy_jsr(String jy_jsr) {
		this.jy_jsr = jy_jsr;
	}
	public String getJy_date() {
		return jy_date;
	}
	public void setJy_date(String jy_date) {
		this.jy_date = jy_date;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Timestamp getCz_date() {
		return cz_date;
	}
	public void setCz_date(Timestamp cz_date) {
		this.cz_date = cz_date;
	}
	public String getCzr() {
		return czr;
	}
	public void setCzr(String czr) {
		this.czr = czr;
	}
	
}
