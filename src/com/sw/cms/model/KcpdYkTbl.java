package com.sw.cms.model;

/**
 * �̵�ӯ����(����仯��)
 * @author liyt
 *
 */
public class KcpdYkTbl {
	
	private String id;          //ӯ�����
	private double je;          //ӯ�����
	private String kcpd_id;     //��Ӧ�̵���
	private String type;        //����(1����Ʒ�������룻2����Ʒ����֧��)
	private String remark;      //��ע
	private String cz_date;     //����ʱ��
	private String czr;         //������
	private String jsr;         //������
	private String fs_date;     //��������
	
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public double getJe() {
		return je;
	}
	public void setJe(double je) {
		this.je = je;
	}
	public String getKcpd_id() {
		return kcpd_id;
	}
	public void setKcpd_id(String kcpd_id) {
		this.kcpd_id = kcpd_id;
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
	public String getFs_date() {
		return fs_date;
	}
	public void setFs_date(String fs_date) {
		this.fs_date = fs_date;
	}
	public String getJsr() {
		return jsr;
	}
	public void setJsr(String jsr) {
		this.jsr = jsr;
	}

}
