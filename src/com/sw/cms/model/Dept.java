package com.sw.cms.model;

/**
 * 部门信息
 * @author liyt
 *
 */
public class Dept {
	
	private String dept_id;
	private String dept_name;
	private String fzr;
	private String phone;
	private int xh;
	private String parent_id;
	
	public String getDept_id() {
		return dept_id;
	}
	public void setDept_id(String dept_id) {
		this.dept_id = dept_id;
	}
	public String getDept_name() {
		return dept_name;
	}
	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}
	public String getFzr() {
		return fzr;
	}
	public void setFzr(String fzr) {
		this.fzr = fzr;
	}
	public String getParent_id() {
		return parent_id;
	}
	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public int getXh() {
		return xh;
	}
	public void setXh(int xh) {
		this.xh = xh;
	}

}
