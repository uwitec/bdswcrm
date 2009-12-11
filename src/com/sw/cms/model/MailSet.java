package com.sw.cms.model;

public class MailSet {
	
	private String user_id;
	private String smtp;
	private String user_name;
	private String password;
	private String from_user;
	private String is_ssl;
	private String port_num;
	private String remark;
	
	public String getFrom_user() {
		return from_user;
	}
	public void setFrom_user(String from_user) {
		this.from_user = from_user;
	}
	public String getIs_ssl() {
		return is_ssl;
	}
	public void setIs_ssl(String is_ssl) {
		this.is_ssl = is_ssl;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPort_num() {
		return port_num;
	}
	public void setPort_num(String port_num) {
		this.port_num = port_num;
	}
	public String getSmtp() {
		return smtp;
	}
	public void setSmtp(String smtp) {
		this.smtp = smtp;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	

}
