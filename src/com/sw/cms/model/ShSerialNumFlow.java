package com.sw.cms.model;

/**
 * 售后产品序列号流转记录
 * @author Administrator
 *
 */
public class ShSerialNumFlow 
{
	   private int    id;                                   //流水号
	   private String qz_serial_num ;                     //序列号
	   private String ywtype ;                            //业务类型(接件，维修入库，报修，送修返还，返还客户)
	   private String client_name ;                       //客户名称
	   private String jsr;                                //经手人
	   private String yw_dj_id;                           //业务单据
	   private String fs_date;                           //发生日期
	   private String yw_url;                            //业务连接
	   private String cj_date;                            //创建时间
	   private String kf;                                //目标库
	   private String rk_date;                            //入目标库时间
	   private String linkman;                            //联系人
	   
	public String getLinkman() {
		return linkman;
	}
	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}
	public String getCj_date() {
		return cj_date;
	}
	public void setCj_date(String cj_date) {
		this.cj_date = cj_date;
	}
	public String getClient_name() {
		return client_name;
	}
	public void setClient_name(String client_name) {
		this.client_name = client_name;
	}
	public String getFs_date() {
		return fs_date;
	}
	public void setFs_date(String fs_date) {
		this.fs_date = fs_date;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getJsr() {
		return jsr;
	}
	public void setJsr(String jsr) {
		this.jsr = jsr;
	}
	public String getKf() {
		return kf;
	}
	public void setKf(String kf) {
		this.kf = kf;
	}
	public String getQz_serial_num() {
		return qz_serial_num;
	}
	public void setQz_serial_num(String qz_serial_num) {
		this.qz_serial_num = qz_serial_num;
	}
	public String getRk_date() {
		return rk_date;
	}
	public void setRk_date(String rk_date) {
		this.rk_date = rk_date;
	}
	public String getYw_dj_id() {
		return yw_dj_id;
	}
	public void setYw_dj_id(String yw_dj_id) {
		this.yw_dj_id = yw_dj_id;
	}
	public String getYw_url() {
		return yw_url;
	}
	public void setYw_url(String yw_url) {
		this.yw_url = yw_url;
	}
	public String getYwtype() {
		return ywtype;
	}
	public void setYwtype(String ywtype) {
		this.ywtype = ywtype;
	}
	  
	    
}
