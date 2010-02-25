package com.sw.cms.model;

/*
 * 报废单
 * 2010-02-22 增加，有以下信息：编号、报废时间、创建时间、经手人、创建人、报废单状态、备注
 *                           id   bf_date  cj_date  jsr  cjr    state    remark
 */
public class Bfd {
	private String id; // 报废单编号

	private String bf_date; // 报废时间

	private String cj_date; // 创建时间

	private String jsr; // 经手人

	private String cjr; // 创建人

	private String state; // 报废单状态（已保存、已提交）

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

	public String getBf_date() {
		return bf_date;
	}

	public void setBf_date(String bf_date) {
		this.bf_date = bf_date;
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
