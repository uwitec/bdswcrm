package com.sw.cms.model;

/**
 * �����ͻ���
 * @author Administrator
 * 2010-02-05�޸ģ���������Ϣ�������ͻ�����š������ͻ����ڡ��������ڡ������ˡ������ˡ�״̬���ͻ���š�      �տ���տ��˻�����ע��    ��ϵ��
 *                          id           fh_date    cj_date  jsr   cjr   state  client_id  skje    skzh    remark  lxr
 *
 */
public class Fhkhd 
{
	 private String  id  ;       //������ID
	 private String fh_date ;//����ʱ��       
	 private String cj_date ;//����ʱ��       	
	 private String jsr   ; //������  
	 private String cjr   ; //������
	 private String state  ;// ״̬  		 
	 private String client_id ;//�ͻ����
	 private String lxr   ; //��ϵ��   
	 private double skje       ;   //ʵ�ս��
     private String skzh       ;//�տ��˺�
     private String  remark ;//��ע	

	public String getCj_date() {
		return cj_date;
	}
	public void setCj_date(String cj_date) {
		this.cj_date = cj_date;
	}

	public String getCjr() {
		return cjr;
	}

	public void setCjr(String cjr) {
		this.cjr = cjr;
	}

	public String getClient_id() {
		return client_id;
	}

	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}

	public String getFh_date() {
		return fh_date;
	}

	public void setFh_date(String fh_date) {
		this.fh_date = fh_date;
	}

	public String getJsr() {
		return jsr;
	}

	public void setJsr(String jsr) {
		this.jsr = jsr;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLxr() {
		return lxr;
	}

	public void setLxr(String lxr) {
		this.lxr = lxr;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public double getSkje() {
		return skje;
	}

	public void setSkje(double skje) {
		this.skje = skje;
	}

	public String getSkzh() {
		return skzh;
	}

	public void setSkzh(String skzh) {
		this.skzh = skzh;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
}
