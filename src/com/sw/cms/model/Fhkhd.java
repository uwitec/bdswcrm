package com.sw.cms.model;

/**
 * �����ͻ���
 * @author Administrator
 *
 */
public class Fhkhd 
{
	 private String  id  ;       //������ID
	 private String fh_date ;//����ʱ��       
	 private String cj_date ;//����ʱ��       	
	 private String fhr   ; //������  
	 private String cjr   ; //������
	 private String state  ;// ״̬  		 
	 private String client_name ;//�ͻ�����
	 private String lxr   ; //��ϵ��        
	 private String lxdh   ;	//��ϵ�绰 
	 private String address ;//��ַ  

	 private String fkfs       ;//���ʽ
	 private String pos_id     ;//POS��ID
     private double sfdje       ;   //�ϼƽ��
     private double skje       ;   //ʵ�ս��
     private String skzh       ;//�տ��˺�
     private String isfy;
      
     private String  ms ;//��ע

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

	public String getFhr() {
		return fhr;
	}

	public void setFhr(String fhr) {
		this.fhr = fhr;
	}

	public String getFkfs() {
		return fkfs;
	}

	public void setFkfs(String fkfs) {
		this.fkfs = fkfs;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getPos_id() {
		return pos_id;
	}

	public void setPos_id(String pos_id) {
		this.pos_id = pos_id;
	}

	public double getSfdje() {
		return sfdje;
	}

	public void setSfdje(double sfdje) {
		this.sfdje = sfdje;
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

	public String getIsfy() {
		return isfy;
	}

	public void setIsfy(String isfy) {
		this.isfy = isfy;
	}
}
