package com.sw.cms.model;

public class SysUser {
	
	private String user_id;
	private String user_name;
	private String password;
	private String real_name;
	private String sex;  //�Ա�
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
	
	private String last_login_time;  //ĩ�ε�½ʱ��
	private String last_logout_time; //ĩ���˳�ʱ��
	private String last_login_ip;  //ĩ�ε�½IP
	
	private String is_sys_user;  //�Ƿ�ϵͳ�û�
	private String is_ywy;   //�Ƿ�ҵ��Ա
	private String is_kgy;   //�Ƿ���Ա
	
	private String gh;  //����
	private int nl;    //����
	
	private int xh = 0;
	
	private String is_dls; //�ͻ����0:ҵ��Ա��1�������̣�2����Ӧ�̣�
	private String client_name;  //��ͻ��������������ҵ��Ա����Ա����ţ�����Ǵ���������������λ��ţ�����ǹ�Ӧ�����Ź�Ӧ��Ʒ���
	
	private String isOnline = "0"; //�Ƿ����ߣ�0�������ߣ�1������
	
	private String china_py;   //����ƴ����д
	
	private String is_del;    //�Ƿ�ɾ����0��δɾ����1����ɾ����
	
	private String id_card;//���֤����	
	private String nation;//����
	private String lxr;//��ϵ��
	private String relation;//��ϵ
	private double jbgz;//��������
	private String rzrq;//��ְ����
	private int gl;//����
	private String byxx;//��ҵѧУ
	private String major;//רҵ
	private String xl;//ѧ��
	private String gzjl;//��������
	private String ldkh;//�쵼����
	private String remark;//��ע
	private String zzmm;//������ò
	
	private String sfjh;//���
	private String jtcy;//��ͥ��Ա
	
	public String getJtcy() {
		return jtcy;
	}
	public void setJtcy(String jtcy) {
		this.jtcy = jtcy;
	}	
	
	public String getSfjh() {
		return sfjh;
	}
	public void setSfjh(String sfjh) {
		this.sfjh = sfjh;
	}	
	public String getZzmm() {
		return zzmm;
	}
	public void setZzmm(String zzmm) {
		this.zzmm = zzmm;
	}
	public double getJbgz() {
		return jbgz;
	}
	public void setJbgz(double jbgz) {
		this.jbgz = jbgz;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getLdkh() {
		return ldkh;
	}
	public void setLdkh(String ldkh) {
		this.ldkh = ldkh;
	}
	public String getGzjl() {
		return gzjl;
	}
	public void setGzjl(String gzjl) {
		this.gzjl = gzjl;
	}
	public String getXl() {
		return xl;
	}
	public void setXl(String xl) {
		this.xl = xl;
	}
	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major = major;
	}
	public String getByxx() {
		return byxx;
	}
	public void setByxx(String byxx) {
		this.byxx = byxx;
	}
	public int getGl() {
		return gl;
	}
	public void setGl(int gl) {
		this.gl = gl;
	}
	public String getRzrq() {
		return rzrq;
	}
	public void setRzrq(String rzrq) {
		this.rzrq = rzrq;
	}
	public String getRelation() {
		return relation;
	}
	public void setRelation(String relation) {
		this.relation = relation;
	}
	public String getLxr() {
		return lxr;
	}
	public void setLxr(String lxr) {
		this.lxr = lxr;
	}
	public String getNation() {
		return nation;
	}
	public void setNation(String nation) {
		this.nation = nation;
	}	
	public String getId_card() {
		return id_card;
	}
	public void setId_card(String id_card) {
		this.id_card = id_card;
	}
	
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
