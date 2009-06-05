package com.sw.cms.model;

/**
 * Êý¾Ý×Öµä¾ß±¾ÐÅÏ¢
 * @author liyt
 *
 */
public class SjzdJbxx {

	private String zd_id;   //×Öµä±àºÅ
	private String zd_name; //×ÖµäÃû³Æ
	private String zdms;    //×ÖµäÃèÊö
	private int xh;         //ÐòºÅ
	
	public String getZdms() {
		return zdms;
	}
	public void setZdms(String zdms) {
		this.zdms = zdms;
	}
	public int getXh() {
		return xh;
	}
	public void setXh(int xh) {
		this.xh = xh;
	}
	public String getZd_id() {
		return zd_id;
	}
	public void setZd_id(String zd_id) {
		this.zd_id = zd_id;
	}
	public String getZd_name() {
		return zd_name;
	}
	public void setZd_name(String zd_name) {
		this.zd_name = zd_name;
	}
	
}
