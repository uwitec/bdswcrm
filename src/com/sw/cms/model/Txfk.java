package com.sw.cms.model;

/**
 * ̯������
 * @author liyt
 *
 */
public class Txfk {

	private String id;          //���
	private String client_name; //��ؿͻ�
	private String fk_date;     //��������	
	private String jsr;         //������	
	private String account_id;  //�����˻�	
	private String fklx;        //̯����������	
	private double fkje;        //������	
	private String remark;      //��ע	
	private String czr;         //�����˱��	
	private String cz_date;     //����ʱ��
	private String state;       //״̬
	
	public String getAccount_id() {
		return account_id;
	}
	public void setAccount_id(String account_id) {
		this.account_id = account_id;
	}
	public String getClient_name() {
		return client_name;
	}
	public void setClient_name(String client_name) {
		this.client_name = client_name;
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
	public String getFk_date() {
		return fk_date;
	}
	public void setFk_date(String fk_date) {
		this.fk_date = fk_date;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getJsr() {
		return jsr;
	}
	public void setJsr(String jsr) {
		this.jsr = jsr;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public double getFkje() {
		return fkje;
	}
	public void setFkje(double fkje) {
		this.fkje = fkje;
	}
	public String getFklx() {
		return fklx;
	}
	public void setFklx(String fklx) {
		this.fklx = fklx;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
}
