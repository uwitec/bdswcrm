package com.sw.cms.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LoginInfo {
	
	private String user_id;    //用户编号
	private String user_name;  //用户名
	private String real_name;  //真实姓名
	private String dept;       //部门
	private String position;   //职位
	private List userRoles = new ArrayList(); //用户角色
	private String sessionId;  //登录session
	private String last_login_ip;  //登陆IP
	private Date last_login_time;  //登陆时间
	private String is_dls;     //是否代理商
	private String client_name;//代理商编号
	
	
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
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
	public List getUserRoles() {
		return userRoles;
	}
	public void setUserRoles(List userRoles) {
		this.userRoles = userRoles;
	}
	public String getReal_name() {
		return real_name;
	}
	public void setReal_name(String real_name) {
		this.real_name = real_name;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getLast_login_ip() {
		return last_login_ip;
	}
	public void setLast_login_ip(String last_login_ip) {
		this.last_login_ip = last_login_ip;
	}
	public Date getLast_login_time() {
		return last_login_time;
	}
	public void setLast_login_time(Date last_login_time) {
		this.last_login_time = last_login_time;
	}
	public String getClient_name() {
		return client_name;
	}
	public void setClient_name(String client_name) {
		this.client_name = client_name;
	}
	public String getIs_dls() {
		return is_dls;
	}
	public void setIs_dls(String is_dls) {
		this.is_dls = is_dls;
	}

}
