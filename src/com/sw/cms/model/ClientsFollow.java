package com.sw.cms.model;

/**
 * ������λ������¼
 * 
 * @author Administrator
 * 
 */
public class ClientsFollow 
{
	private int id;             //��ˮ��

	private String zt;          //����

	private String clients_id; //�ͻ�

	private String linkman_id; //��ϵ��

	private String lxlx;      //��ϵ���� ������ �绰 �����Űݷã�

	private String lxdate;  //��ϵ����

	private String cjdate;  //��������

	private String cjr;    //������

	private String nextdate; //�´���ϵʱ��

	private String remark; //��ע

	public String getCjdate() {
		return cjdate;
	}

	public void setCjdate(String cjdate) {
		this.cjdate = cjdate;
	}

	public String getCjr() {
		return cjr;
	}

	public void setCjr(String cjr) {
		this.cjr = cjr;
	}

	public String getClients_id() {
		return clients_id;
	}

	public void setClients_id(String clients_id) {
		this.clients_id = clients_id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLinkman_id() {
		return linkman_id;
	}

	public void setLinkman_id(String linkman_id) {
		this.linkman_id = linkman_id;
	}

	public String getLxdate() {
		return lxdate;
	}

	public void setLxdate(String lxdate) {
		this.lxdate = lxdate;
	}

	public String getLxlx() {
		return lxlx;
	}

	public void setLxlx(String lxlx) {
		this.lxlx = lxlx;
	}

	public String getNextdate() {
		return nextdate;
	}

	public void setNextdate(String nextdate) {
		this.nextdate = nextdate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getZt() {
		return zt;
	}

	public void setZt(String zt) {
		this.zt = zt;
	}
}
