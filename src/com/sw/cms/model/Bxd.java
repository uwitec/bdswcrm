package com.sw.cms.model;

/*
 * 报修单
 * 2010-01-29 修改，有以下信息：编号、报修时间、创建时间、经手人、报修厂商、创建人、保修单状态、备注
 *                              id   bxdate  cjdate  jsr     bxcs    cjr    state    remark
 */
public class Bxd {
	private String id; // 报修单编号

	private String bxdate; // 报修时间

	private String cjdate; // 创建时间

	private String jsr; // 经手人

	private String cjr; // 创建人

	private String state; // 报修单状态（报修中，报修完）

	private String bxcs; // 报修厂商

	private String remark; // 备注

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

	public String getBxcs() {
		return bxcs;
	}

	public void setBxcs(String bxcs) {
		this.bxcs = bxcs;
	}

	public String getJsr() {
		return jsr;
	}

	public void setJsr(String jsr) {
		this.jsr = jsr;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBxdate() {
		return bxdate;
	}

	public void setBxdate(String bxdate) {
		this.bxdate = bxdate;
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

}
