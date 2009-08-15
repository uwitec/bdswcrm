package com.sw.cms.model;

/*
 * 报修单
 */
public class Bxd {
	private String id; // ID

	private String jxdate; // 接修时间

	private String cjdate; // 创建时间

	private String jddate; // 结单时间

	private String jxr; // 接修人

	private String cjr; // 创建人

	private String gcs; // 工程师

	private String state; // 报修单状态（报修中，报修完）

	private String client_name; // 客户名称

	private String lxr; // 联系人

	private String lxdh; // 联系电话

	private String email; // 邮件

	private String address; // 地址

	private String cz; // 传真

	private String yb; // 邮编

	private String remark; // 备注

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCjdate() {
		return cjdate;
	}

	public void setCjdate(String cjdate) {
		this.cjdate = cjdate;
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

	public String getCz() {
		return cz;
	}

	public void setCz(String cz) {
		this.cz = cz;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getJddate() {
		return jddate;
	}

	public void setJddate(String jddate) {
		this.jddate = jddate;
	}

	public String getJxdate() {
		return jxdate;
	}

	public void setJxdate(String jxdate) {
		this.jxdate = jxdate;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getYb() {
		return yb;
	}

	public void setYb(String yb) {
		this.yb = yb;
	}

}
