package com.sw.cms.model;

/**
 * �ۺ��Ʒ���к���ת��¼
 * @author Administrator
 *
 */
public class ShSerialNumFlow 
{
	   private int    id;                                   //��ˮ��
	   private String qz_serial_num ;                     //���к�
	   private String ywtype ;                            //ҵ������(�Ӽ���ά����⣬���ޣ����޷����������ͻ�)
	   private String client_name ;                       //�ͻ�����
	   private String jsr;                                //������
	   private String yw_dj_id;                           //ҵ�񵥾�
	   private String fs_date;                           //��������
	   private String yw_url;                            //ҵ������
	   private String cj_date;                            //����ʱ��
	   private String kf;                                //Ŀ���
	   private String rk_date;                            //��Ŀ���ʱ��
	   private String linkman;                            //��ϵ��
	   
	public String getLinkman() {
		return linkman;
	}
	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}
	public String getCj_date() {
		return cj_date;
	}
	public void setCj_date(String cj_date) {
		this.cj_date = cj_date;
	}
	public String getClient_name() {
		return client_name;
	}
	public void setClient_name(String client_name) {
		this.client_name = client_name;
	}
	public String getFs_date() {
		return fs_date;
	}
	public void setFs_date(String fs_date) {
		this.fs_date = fs_date;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getJsr() {
		return jsr;
	}
	public void setJsr(String jsr) {
		this.jsr = jsr;
	}
	public String getKf() {
		return kf;
	}
	public void setKf(String kf) {
		this.kf = kf;
	}
	public String getQz_serial_num() {
		return qz_serial_num;
	}
	public void setQz_serial_num(String qz_serial_num) {
		this.qz_serial_num = qz_serial_num;
	}
	public String getRk_date() {
		return rk_date;
	}
	public void setRk_date(String rk_date) {
		this.rk_date = rk_date;
	}
	public String getYw_dj_id() {
		return yw_dj_id;
	}
	public void setYw_dj_id(String yw_dj_id) {
		this.yw_dj_id = yw_dj_id;
	}
	public String getYw_url() {
		return yw_url;
	}
	public void setYw_url(String yw_url) {
		this.yw_url = yw_url;
	}
	public String getYwtype() {
		return ywtype;
	}
	public void setYwtype(String ywtype) {
		this.ywtype = ywtype;
	}
	  
	    
}
