package com.sw.cms.model;

/**
 * �Ӽ���
 * @author Administrator
 *
 */
public class Jjd 
{
	   private String  id       ;    //�Ӽ���ID
	   private String  sfd_id   ;//�ۺ����ID
	   
	   private String  client_name ;//�ͻ����ƣ���������ۿͻ��Ͳ�����д��ֱ����д��ϵ�ˣ�
	   private String  address     ;//��ַ
	   private String  mobile     ;//�绰
	   private String  linkman    ;//��ϵ��

	   private String  jjr        ;//�Ӽ���
	   private String  cjr         ;//������
	   private String  jj_date     ;//�Ӽ�ʱ��
	   private String  cj_date    ;//����ʱ��

	   private  String state     ;//�Ӽ���״̬���ѱ��棬���ύ�������⣩
	   private  String ms        ;//����   
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
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
	public String getJj_date() {
		return jj_date;
	}
	public void setJj_date(String jj_date) {
		this.jj_date = jj_date;
	}
	public String getJjr() {
		return jjr;
	}
	public void setJjr(String jjr) {
		this.jjr = jjr;
	}
	public String getLinkman() {
		return linkman;
	}
	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getMs() {
		return ms;
	}
	public void setMs(String ms) {
		this.ms = ms;
	}
	public String getSfd_id() {
		return sfd_id;
	}
	public void setSfd_id(String sfd_id) {
		this.sfd_id = sfd_id;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
}