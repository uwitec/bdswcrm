package com.sw.cms.model;

/**
 * ���޷�����
 * @author Administrator
 *
 */
public class Bxfhd
{
	  private String id ;        //���޷�����ID
      private String fh_date;    //���޷���ʱ��       
      private String cj_date ;   //����ʱ��       	
      private String jxr    ;    //������  
      private String cjr    ;    //������
      private String gcs    ;    //����ʦ
      private String state  ;    //״̬ (������ �ѷ���) 		 
      private String client_name ;//�ͻ�����
      private String lxr   ;     //��ϵ��        
      private String lxdh   ;	 //��ϵ�绰 
      private String address ;   //��ַ  	        
      private String ms ;        //��ע
      private String bxd_id ;     //���޵�ID
      private String bxr;         //������
      private String fkzh;        //�����˻�
      private double ssje;        //ʵ�ս��
      private String isfy;        //�Ƿ��������
    
     
	public String getBxd_id() {
		return bxd_id;
	}
	public void setBxd_id(String bxd_id) {
		this.bxd_id = bxd_id;
	}
	public String getBxr() {
		return bxr;
	}
	public void setBxr(String bxr) {
		this.bxr = bxr;
	}
	public String getFkzh() {
		return fkzh;
	}
	public void setFkzh(String fkzh) {
		this.fkzh = fkzh;
	}
 
	 
	public double getSsje() {
		return ssje;
	}

	public void setSsje(double ssje) {
		this.ssje = ssje;
	}

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
	public String getFh_date() {
		return fh_date;
	}
	public void setFh_date(String fh_date) {
		this.fh_date = fh_date;
	}
	public String getGcs() {
		return gcs;
	}
	public void setGcs(String gcs) {
		this.gcs = gcs;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getJxr() {
		return jxr;
	}
	public void setJxr(String jxr) {
		this.jxr = jxr;
	}
	public String getLxdh() {
		return lxdh;
	}
	public void setLxdh(String lxdh) {
		this.lxdh = lxdh;
	}
	public String getLxr() {
		return lxr;
	}
	public void setLxr(String lxr) {
		this.lxr = lxr;
	}
	public String getMs() {
		return ms;
	}
	public void setMs(String ms) {
		this.ms = ms;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getIsfy() {
		return isfy;
	}
	public void setIsfy(String isfy) {
		this.isfy = isfy;
	}
}
