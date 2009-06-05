package com.sw.cms.model;

/**
 * 数据字典项目信息
 * @author liyt
 *
 */
public class SjzdXmxx {
	
	private int xm_id;
	private String zd_id;
	private String xm_name;
	private String xm_ms;
	private int xh;
	
	public int getXh() {
		return xh;
	}
	public void setXh(int xh) {
		this.xh = xh;
	}
	public int getXm_id() {
		return xm_id;
	}
	public void setXm_id(int xm_id) {
		this.xm_id = xm_id;
	}
	public String getXm_ms() {
		return xm_ms;
	}
	public void setXm_ms(String xm_ms) {
		this.xm_ms = xm_ms;
	}
	public String getXm_name() {
		return xm_name;
	}
	public void setXm_name(String xm_name) {
		this.xm_name = xm_name;
	}
	public String getZd_id() {
		return zd_id;
	}
	public void setZd_id(String zd_id) {
		this.zd_id = zd_id;
	}
	

}
