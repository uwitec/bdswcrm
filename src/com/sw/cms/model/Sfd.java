package com.sw.cms.model;

/**
 * �ۺ����
 * @author Administrator
 * 2010-3-23����һ���ֶ� flow
 */
public class Sfd 
{
	   private String  id;          //�ۺ���񵥺�
	   private String  client_name ;//�ͻ����ƣ���������ۿͻ��Ͳ�����д��ֱ����д��ϵ�ˣ�
	   private String  address    ;//��ַ
	   private String  mobile     ;//�绰
	   private String  linkman    ;//��ϵ��
	   private String  jxr;        //������
	   private String  cjr        ;//������
	   private String  jd_date    ;//�ᵥʱ��
	   private String  jx_date    ;//����ʱ��
	   private String  cj_date    ;//����ʱ��
	   private String  qzfs;      //������ʽ
	   private String  state      ;//�ۺ����״̬�����棬�ύ��	   
	   private String wx_state;     //ά��״̬(�����У�������)
	   private String  ms          ;//��Ʒ��Ϣ����
	   private String  flow          ;//�������̣���ѯ��Ͷ�ߡ�ά��
	   private String  bxyy          ;//����ԭ�򣺴������ֵ��л��
	   private String  bxyy_ms          ;//����ԭ��˵����������ԭ��Ϊ��������ʱ�����Խ��������Ϣ¼��
	   private String  qz_serial_num          ;//ǿ�����к�
	   
	   private String  khlx          ;//�ͻ�����
	   
	   private String  linkmanLs    ;//��ϵ�ˣ����ۣ�
	   
	   public String getKhlx() {
			return khlx;
		}
		public void setKhlx(String khlx) {
			this.khlx = khlx;
		}
	   
	   public String getQz_serial_num() {
			return qz_serial_num;
		}
		public void setQz_serial_num(String qz_serial_num) {
			this.qz_serial_num = qz_serial_num;
		}
	   public String getBxyy() {
			return bxyy;
		}
		public void setBxyy(String bxyy) {
			this.bxyy = bxyy;
		}
		public String getBxyy_ms() {
			return bxyy_ms;
		}
		public void setBxyy_ms(String bxyy_ms) {
			this.bxyy_ms = bxyy_ms;
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getJd_date() {
		return jd_date;
	}
	public void setJd_date(String jd_date) {
		this.jd_date = jd_date;
	}
	public String getJx_date() {
		return jx_date;
	}
	public void setJx_date(String jx_date) {
		this.jx_date = jx_date;
	}
	public String getJxr() {
		return jxr;
	}
	public void setJxr(String jxr) {
		this.jxr = jxr;
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
	public String getQzfs() {
		return qzfs;
	}
	public void setQzfs(String qzfs) {
		this.qzfs = qzfs;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getWx_state() {
		return wx_state;
	}
	public void setWx_state(String wx_state) {
		this.wx_state = wx_state;
	} 
	
	public String getFlow() {
		return flow;
	}
	public void setFlow(String flow) {
		this.flow = flow;
	}
	
	public String getLinkmanLs() {
		return linkmanLs;
	}
	public void setLinkmanLs(String linkmanLs) {
		this.linkmanLs = linkmanLs;
	}
}
