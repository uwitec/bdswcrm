package com.sw.cms.model;

import java.util.Date;

/**
 * ˢ��Pos���趨
 * @author liyt
 *
 */
public class PosType {
	
	private int id;           //���
	private String name;         //����
	private String type;         //���ͣ�1:�̶���2:�̶��ۿ��ʣ�3:�̶��ۿ���(�ⶥ)��
	private double mbfy;         //ÿ�ʷ���
	private double fl;           //����
	private double fdfy;         //�ⶥ����
	private String account_id;   //��Ӧ�˼Ǳ��
	private String remark;       //��ע
	private String czr;          //������
	private Date cz_date;        //����ʱ��
	
	public String getAccount_id() {
		return account_id;
	}
	public void setAccount_id(String account_id) {
		this.account_id = account_id;
	}
	public String getCzr() {
		return czr;
	}
	public void setCzr(String czr) {
		this.czr = czr;
	}
	public double getFdfy() {
		return fdfy;
	}
	public void setFdfy(double fdfy) {
		this.fdfy = fdfy;
	}
	public double getFl() {
		return fl;
	}
	public void setFl(double fl) {
		this.fl = fl;
	}
	public double getMbfy() {
		return mbfy;
	}
	public void setMbfy(double mbfy) {
		this.mbfy = mbfy;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Date getCz_date() {
		return cz_date;
	}
	public void setCz_date(Date cz_date) {
		this.cz_date = cz_date;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

}
