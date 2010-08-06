package com.sw.cms.model;

/**
 * 接件单
 * @author Administrator
 *
 */
public class Jjd 
{	   
	   private String  id ;
	   private String  sfd_id;
	   private String  client_name;
	   private String  lxdh ;
	   private String   mobile;
	   private String  linkman;
	   private String  jjr;
	   private String  cjr;
	   private String  jj_date;
	   private String  cj_date;
	   private String  state;
	   private String  ms;
	   private String  mail;
	   private String  khlx;//客户类型：往来单位、零售客户
	   private String  address;
	   private String  linkmanLs;//零售客户联系人
	   
	   public String getAddress() {
			return address;
		}
		public void setAddress(String address) {
			this.address = address;
		}
	   
		public String getLinkmanLs() {
			return linkmanLs;
		}
		public void setLinkmanLs(String linkmanLs) {
			this.linkmanLs = linkmanLs;
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
	public String getLxdh() {
		return lxdh;
	}
	public void setLxdh(String lxdh) {
		this.lxdh = lxdh;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
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
	 
	public String getKhlx() {
		return khlx;
	}

	public void setKhlx(String khlx) {
		this.khlx = khlx;
	}
}
