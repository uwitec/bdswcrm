package com.sw.cms.model;

/*
 * ��Ա������
 * 2010-06-12 ���ӣ���������Ϣ����š���Ա���š�״̬��    �Ƿ�ͣ�á���Ա���ơ���ϵ�ˡ�  ��ϵ�绰  �ֻ�       ��Ա���    ������ע
 *                           id   hykh    state   sfty     hymc   lxrname  lxdh    mobile   hybh     fkbz
 *                           ��ַ       mail  �����ˡ�����ʱ��  ���֤��  ���Ż���  ��������  ����������    �ƿ�����    �ƿ�������
 *                           address  mail   czr  cz_date   sfzh   ffjg     fkrq    fkjsr       zkrq      zkjsr
 */
public class Hykda {
	private String id; // ���

	private String hykh; // ��Ա����

	private String state; // ����״̬�������á�δʹ��

	private String sfty; // �Ƿ�ͣ�ã��ǡ���
	
	private String kymc; // ��Ա����

	private String lxrname; // ��ϵ��
	
	private String lxdh; // ��ϵ�绰

	private String mobile; // �ֻ�
	
	private String address; // ��ַ

	private String mail; // E-Mail

	private String czr; // ������

	private String cz_date; // ����ʱ��

	private String sfzh; //���֤��
	
	private String ffjg; //���Ż���
	
	private String fkrq; //��������
	
	private String fkjsr; //����������
	
    private String zkrq; //�ƿ�����
	
	private String zkjsr; //�ƿ�������
	
	private String hybh; //��Ա���
	
	private String fkbz; //������ע
	
	
	public String getFkbz() {
		return fkbz;
	}

	public void setFkbz(String fkbz) {
		this.fkbz = fkbz;
	}
	
	public String getHybh() {
		return hybh;
	}

	public void setHybh(String hybh) {
		this.hybh = hybh;
	}
	
	public String getZkjsr() {
		return zkjsr;
	}

	public void setZkjsr(String zkjsr) {
		this.zkjsr = zkjsr;
	}
	
	public String getFkjsr() {
		return fkjsr;
	}

	public void setFkjsr(String fkjsr) {
		this.fkjsr = fkjsr;
	}
	
	
	public String getZkrq() {
		return zkrq;
	}

	public void setZkrq(String zkrq) {
		this.zkrq = zkrq;
	}
	
	public String getFkrq() {
		return fkrq;
	}

	public void setFkrq(String fkrq) {
		this.fkrq = fkrq;
	}
	
	public String getFfjg() {
		return ffjg;
	}

	public void setFfjg(String ffjg) {
		this.ffjg = ffjg;
	}
	
	public String getSfzh() {
		return sfzh;
	}

	public void setSfzh(String sfzh) {
		this.sfzh = sfzh;
	}
	
	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public String getLxdh() {
		return lxdh;
	}

	public void setLxdh(String lxdh) {
		this.lxdh = lxdh;
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
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHykh() {
		return hykh;
	}

	public void setHykh(String hykh) {
		this.hykh = hykh;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getSfty() {
		return sfty;
	}

	public void setSfty(String sfty) {
		this.sfty = sfty;
	}

	public String getHymc() {
		return kymc;
	}

	public void setHymc(String hymc) {
		this.kymc = hymc;
	}

	public String getLxrname() {
		return lxrname;
	}

	public void setLxrname(String lxrname) {
		this.lxrname = lxrname;
	}
}
