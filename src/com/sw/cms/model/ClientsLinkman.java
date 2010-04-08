package com.sw.cms.model;

/**
 * 往来单位联系人
 * 
 * @author Administrator
 * 
 */
public class ClientsLinkman {
	private String id;

	private String name; // 联系人姓名

	private String sex; // 联系人性别

	private String lx; // 是主联系人 || 联系人

	private String zw; // 职务

	private String dept;// 部门

	private String gzdh; // 工作电话

	private String yddh; // 移动电话

	private String mail; // 邮箱

	private String jtdh; // 家庭电话

	private String qtlx;// 其他联系方式 ：QQ MSN WW

	private String address; // 家庭地址

	private String sr; // 生日

	private String ah; // 爱好

	private String remark; // 备注

	private String clients_id;// 单位ID
	
	private String ch;// 称呼

	private String qq; // QQ

	private String msn; // MSN

	private String nld; // 年龄段
	
	public String getNld() {
		return nld;
	}

	public void setNld(String nld) {
		this.nld = nld;
	}
	
	public String getMsn() {
		return msn;
	}

	public void setMsn(String msn) {
		this.msn = msn;
	}
	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getCh() {
		return ch;
	}

	public void setCh(String ch) {
		this.ch = ch;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAh() {
		return ah;
	}

	public void setAh(String ah) {
		this.ah = ah;
	}

	public String getClients_id() {
		return clients_id;
	}

	public void setClients_id(String clients_id) {
		this.clients_id = clients_id;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getGzdh() {
		return gzdh;
	}

	public void setGzdh(String gzdh) {
		this.gzdh = gzdh;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getJtdh() {
		return jtdh;
	}

	public void setJtdh(String jtdh) {
		this.jtdh = jtdh;
	}

	public String getLx() {
		return lx;
	}

	public void setLx(String lx) {
		this.lx = lx;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getQtlx() {
		return qtlx;
	}

	public void setQtlx(String qtlx) {
		this.qtlx = qtlx;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getSr() {
		return sr;
	}

	public void setSr(String sr) {
		this.sr = sr;
	}

	public String getYddh() {
		return yddh;
	}

	public void setYddh(String yddh) {
		this.yddh = yddh;
	}

	public String getZw() {
		return zw;
	}

	public void setZw(String zw) {
		this.zw = zw;
	}
}
