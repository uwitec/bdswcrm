package com.sw.cms.model;

public class Cgfk {
	
	private String id;   //�ɹ�������
	private String fk_date; //��������
	private String gysbh;//��Ӧ�̱��
	private String fkzh; //�����˻�
	private String jsr;  //������
	private double fkje; //������
	private String remark; //��ע
	private String state;  //״̬	
	private String is_yfk; //�Ƿ�Ԥ����
	private double yfk_ye; //Ԥ�������
	private String czr;    //������
	
	//ɾ���ؼ���
	//�ڲɹ��˻������ύʱ������ֽ��˻�����Ҫ���ɹ��˻���IDд��
	//�ڷ����ɹ��˻��ֽ��˻�ʱ����Ҫ���ɹ��˻���IDд��
	private String delete_key = "";
	
	public String getFk_date() {
		return fk_date;
	}
	public void setFk_date(String fk_date) {
		this.fk_date = fk_date;
	}
	public double getFkje() {
		return fkje;
	}
	public void setFkje(double fkje) {
		this.fkje = fkje;
	}
	public String getFkzh() {
		return fkzh;
	}
	public void setFkzh(String fkzh) {
		this.fkzh = fkzh;
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
	public String getIs_yfk() {
		return is_yfk;
	}
	public void setIs_yfk(String is_yfk) {
		this.is_yfk = is_yfk;
	}
	public double getYfk_ye() {
		return yfk_ye;
	}
	public void setYfk_ye(double yfk_ye) {
		this.yfk_ye = yfk_ye;
	}
	public String getDelete_key() {
		return delete_key;
	}
	public void setDelete_key(String delete_key) {
		this.delete_key = delete_key;
	}

}
