package com.sw.cms.model;

import java.sql.Timestamp;

public class Fysq {

	private String id;         //���
	private String creatdate;  //��������
	private String ywy_id;     //ҵ��Ա���
	private String sqr;        //������
	private String ywy_dept;   //ҵ��Ա���ڲ���
	private String xgkh;       //��ؿͻ�
	private String fy_type;    //��������
	private double je;         //���
	private String fklx;       //��������
	private String zfzh;       //֧���˻����
	private String remark;     //��ϸ˵��
	private String state;      //״̬ �����桢�ύ������ͨ����������ͨ������֧����
	private String czr;        //�����˱��
	private Timestamp cz_date;    //����ʱ��
	private String spr;        //������
	private Timestamp sp_date;    //����ʱ��
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCreatdate() {
		return creatdate;
	}
	public void setCreatdate(String creatdate) {
		this.creatdate = creatdate;
	}
	public String getYwy_id() {
		return ywy_id;
	}
	public void setYwy_id(String ywyId) {
		ywy_id = ywyId;
	}
	public String getSqr() {
		return sqr;
	}
	public void setSqr(String sqr) {
		this.sqr = sqr;
	}
	public String getYwy_dept() {
		return ywy_dept;
	}
	public void setYwy_dept(String ywyDept) {
		ywy_dept = ywyDept;
	}
	public String getXgkh() {
		return xgkh;
	}
	public void setXgkh(String xgkh) {
		this.xgkh = xgkh;
	}
	public String getFy_type() {
		return fy_type;
	}
	public void setFy_type(String fyType) {
		fy_type = fyType;
	}
	public double getJe() {
		return je;
	}
	public void setJe(double je) {
		this.je = je;
	}
	public String getFklx() {
		return fklx;
	}
	public void setFklx(String fklx) {
		this.fklx = fklx;
	}
	public String getZfzh() {
		return zfzh;
	}
	public void setZfzh(String zfzh) {
		this.zfzh = zfzh;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
	public Timestamp getCz_date() {
		return cz_date;
	}
	public void setCz_date(Timestamp czDate) {
		cz_date = czDate;
	}
	public String getSpr() {
		return spr;
	}
	public void setSpr(String spr) {
		this.spr = spr;
	}
	public Timestamp getSp_date() {
		return sp_date;
	}
	public void setSp_date(Timestamp spDate) {
		sp_date = spDate;
	}
	
}
