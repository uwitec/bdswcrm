package com.sw.cms.model;
/**
 * 维修入库单
 * @author Administrator
 * 2010-03-01修改，有以下信息：id      wxrk_date  cj_date  jsr     cjr  state  remark
 *                     维修入库编号   维修入库日期  创建日期  经手人  创建人  状态  备注
 */
public class Wxrkd 
{
	 private String id  ;    //维修入库单ID
     private String wxrk_date ;//维修入库时间       
     private String cj_date ;//创建时间       	
     private String jsr    ; //经手人  
     private String cjr    ; //创建人
     private String state  ;// 状态  
     private String  remark ;//备注
	
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
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getWxrk_date() {
		return wxrk_date;
	}
	public void setWxrk_date(String wxrk_date) {
		this.wxrk_date = wxrk_date;
	}
	public String getJsr() {
		return jsr;
	}
	public void setJsr(String jsr) {
		this.jsr = jsr;
	}
}
