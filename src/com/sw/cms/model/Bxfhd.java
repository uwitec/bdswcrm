package com.sw.cms.model;

/**
 * 报修返还单
 * @author Administrator
 *
 */
public class Bxfhd
{
	  private String id ;        //报修返还单ID
      private String fh_date;    //报修返还时间       
      private String cj_date ;   //创建时间       	
      private String jxr    ;    //经手人  
      private String cjr    ;    //创建人
      private String gcs    ;    //工程师
      private String state  ;    //状态 (待返还 已返还) 		 
      private String client_name ;//客户名称
      private String lxr   ;     //联系人        
      private String lxdh   ;	 //联系电话 
      private String address ;   //地址  	        
      private String ms ;        //备注
      private String bxd_id ;     //报修单ID
      private String bxr;         //报修人
      private String fkzh;        //付款账户
      private double ssje;        //实收金额
      private String isfy;        //是否产生费用
    
     
	public String getBxd_id() {
		return bxd_id;
	}
	public void setBxd_id(String bxd_id) {
		this.bxd_id = bxd_id;
	}
	public String getBxr() {
		return bxr;
	}
	public void setBxr(String bxr) {
		this.bxr = bxr;
	}
	public String getFkzh() {
		return fkzh;
	}
	public void setFkzh(String fkzh) {
		this.fkzh = fkzh;
	}
 
	 
	public double getSsje() {
		return ssje;
	}

	public void setSsje(double ssje) {
		this.ssje = ssje;
	}

	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
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
	public String getClient_name() {
		return client_name;
	}
	public void setClient_name(String client_name) {
		this.client_name = client_name;
	}
	public String getFh_date() {
		return fh_date;
	}
	public void setFh_date(String fh_date) {
		this.fh_date = fh_date;
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
	public String getMs() {
		return ms;
	}
	public void setMs(String ms) {
		this.ms = ms;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getIsfy() {
		return isfy;
	}
	public void setIsfy(String isfy) {
		this.isfy = isfy;
	}
}
