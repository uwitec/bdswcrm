package com.sw.cms.model;

/**
 * ������λ��ϵ��
 * 
 * @author Administrator
 * 
 */
public class ClientsLinkman
{
	private String id;  

	private String name; //��ϵ������

	private String sex;  //��ϵ���Ա�

	private String lx; //������ϵ�� || ��ϵ��

	private String zw; //ְ��

	private String dept;//����

	private String gzdh; //�����绰

	private String yddh; //�ƶ��绰

	private String mail; //����

	private String jtdh; //��ͥ�绰

	private String qtlx;//������ϵ��ʽ ��QQ MSN WW

	private String address; //��ͥ��ַ

	private String sr; //����

	private String ah; //����

	private String remark;  //��ע

	private String clients_id;//��λID

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAh() {
		return ah;
	}

	public void setAh(String ah) {
		this.ah = ah;
	}

	public String getClients_id() {
		return clients_id;
	}

	public void setClients_id(String clients_id) {
		this.clients_id = clients_id;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getGzdh() {
		return gzdh;
	}

	public void setGzdh(String gzdh) {
		this.gzdh = gzdh;
	}

 

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getJtdh() {
		return jtdh;
	}

	public void setJtdh(String jtdh) {
		this.jtdh = jtdh;
	}

	public String getLx() {
		return lx;
	}

	public void setLx(String lx) {
		this.lx = lx;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getQtlx() {
		return qtlx;
	}

	public void setQtlx(String qtlx) {
		this.qtlx = qtlx;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getSr() {
		return sr;
	}

	public void setSr(String sr) {
		this.sr = sr;
	}

	public String getYddh() {
		return yddh;
	}

	public void setYddh(String yddh) {
		this.yddh = yddh;
	}

	public String getZw() {
		return zw;
	}

	public void setZw(String zw) {
		this.zw = zw;
	}
}
