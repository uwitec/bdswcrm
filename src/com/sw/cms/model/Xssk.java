package com.sw.cms.model;

public class Xssk {
	
	private String id;     //���
	private String sk_date;  //�տ�����
	private String client_name;  //�ͻ�����
	private String skzh;    //�տ��˻�
	private String jsr;     //������
	private double skje = 0; //�տ���
	private String state;    //״̬
	private String remark;	 //��ע
	private String is_ysk;//�Ƿ�Ԥ�տ�
	private double ysk_ye = 0; //Ԥ�տ����
	private String czr; //������
	
	//ɾ���ؼ���
	//�����۵������ύʱ����������տ��Ҫ�����۵�IDд��
	//�ڷ��������ֽ��˻�ʱ����Ҫ�������˻���IDд��
	private String delete_key = "";
	
	public String getClient_name() {
		return client_name;
	}
	public void setClient_name(String client_name) {
		this.client_name = client_name;
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
	public String getSk_date() {
		return sk_date;
	}
	public void setSk_date(String sk_date) {
		this.sk_date = sk_date;
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
	public String getIs_ysk() {
		return is_ysk;
	}
	public void setIs_ysk(String is_ysk) {
		this.is_ysk = is_ysk;
	}
	public double getYsk_ye() {
		return ysk_ye;
	}
	public void setYsk_ye(double ysk_ye) {
		this.ysk_ye = ysk_ye;
	}
	public String getCzr() {
		return czr;
	}
	public void setCzr(String czr) {
		this.czr = czr;
	}
	public String getDelete_key() {
		return delete_key;
	}
	public void setDelete_key(String delete_key) {
		this.delete_key = delete_key;
	}

}
