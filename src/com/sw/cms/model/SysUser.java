package com.sw.cms.model;

public class SysUser {
	
	private String user_id;
	private String user_name;
	private String password;
	private String real_name;
	private String sex;  //性别
	private String csny;
	
	private String gs_phone;
	private String mobile;
	private String jt_phone;
	private String fax;
	private String mail;
	private String msn;
	private String qq;
	private String address;
	private String p_code;
	
	private String dept;
	private String position;
	private String szkf;
	private String state;
	
	private String last_login_time;  //末次登陆时间
	private String last_logout_time; //末次退出时间
	private String last_login_ip;  //末次登陆IP
	
	private String is_sys_user;  //是否系统用户
	private String is_ywy;   //是否业务员
	private String is_kgy;   //是否库管员
	
	private String gh;  //工号
	private int nl;    //年龄
	
	private int xh = 0;
	
	private String is_dls; //客户类别（0:业务员；1：代理商；2：供应商）
	private String client_name;  //与客户类别关联，如果是业务员则存放员工编号，如果是代理商则存放往来单位编号，如果是供应商则存放供应商品类别
	
	private String isOnline = "0"; //是否在线；0：不在线；1：在线
	
	private String china_py;   //汉语拼音缩写
	
	private String is_del;    //是否删除（0：未删除；1：已删除）
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCsny() {
		return csny;
	}
	public void setCsny(String csny) {
		this.csny = csny;
	}
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getGs_phone() {
		return gs_phone;
	}
	public void setGs_phone(String gs_phone) {
		this.gs_phone = gs_phone;
	}
	public String getJt_phone() {
		return jt_phone;
	}
	public void setJt_phone(String jt_phone) {
		this.jt_phone = jt_phone;
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
	public String getMsn() {
		return msn;
	}
	public void setMsn(String msn) {
		this.msn = msn;
	}
	public String getP_code() {
		return p_code;
	}
	public void setP_code(String p_code) {
		this.p_code = p_code;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getReal_name() {
		return real_name;
	}
	public void setReal_name(String real_name) {
		this.real_name = real_name;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
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
	public int getXh() {
		return xh;
	}
	public void setXh(int xh) {
		this.xh = xh;
	}
	public String getSzkf() {
		return szkf;
	}
	public void setSzkf(String szkf) {
		this.szkf = szkf;
	}
	public String getIs_kgy() {
		return is_kgy;
	}
	public void setIs_kgy(String is_kgy) {
		this.is_kgy = is_kgy;
	}
	public String getIs_sys_user() {
		return is_sys_user;
	}
	public void setIs_sys_user(String is_sys_user) {
		this.is_sys_user = is_sys_user;
	}
	public String getIs_ywy() {
		return is_ywy;
	}
	public void setIs_ywy(String is_ywy) {
		this.is_ywy = is_ywy;
	}
	public String getLast_login_ip() {
		return last_login_ip;
	}
	public void setLast_login_ip(String last_login_ip) {
		this.last_login_ip = last_login_ip;
	}
	public String getLast_login_time() {
		return last_login_time;
	}
	public void setLast_login_time(String last_login_time) {
		this.last_login_time = last_login_time;
	}
	public String getLast_logout_time() {
		return last_logout_time;
	}
	public void setLast_logout_time(String last_logout_time) {
		this.last_logout_time = last_logout_time;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getGh() {
		return gh;
	}
	public void setGh(String gh) {
		this.gh = gh;
	}
	public int getNl() {
		return nl;
	}
	public void setNl(int nl) {
		this.nl = nl;
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
	public String getIsOnline() {
		return isOnline;
	}
	public void setIsOnline(String isOnline) {
		this.isOnline = isOnline;
	}
	public String getChina_py() {
		return china_py;
	}
	public void setChina_py(String china_py) {
		this.china_py = china_py;
	}
	public String getIs_del() {
		return is_del;
	}
	public void setIs_del(String is_del) {
		this.is_del = is_del;
	}

}
