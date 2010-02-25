package com.sw.cms.model;

/*
 * 换件单
 * 2010-02-24 增加，有以下信息：编号、报修时间、创建时间、经手人、创建人、保修单状态、备注
 *                          id   hj_date  cj_date  jsr   cjr    state    remark
 */
public class Hjd {
	private String id; // 换件单编号

	private String hj_date; // 换件时间

	private String cj_date; // 创建时间

	private String jsr; // 经手人

	private String cjr; // 创建人

	private String state; // 换件单状态（已保存、已提交）
	
	private String remark; // 备注

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

	public String getHj_date() {
		return hj_date;
	}

	public void setHj_date(String hj_date) {
		this.hj_date = hj_date;
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
