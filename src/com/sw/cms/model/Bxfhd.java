package com.sw.cms.model;

/**
 * 报修返还单
 * @author Administrator
 * 2010-01-29修改，只剩下以下信息：报修返还单ID、报修返还时间、创建时间、经手人、创建人、状态、付款账户、实收金额、报修厂商、备注、合计金额
 *                             id          fh_date    cj_date jsr    cjr   state fkzh   ssje    bxcs    remark hjje
 */
public class Bxfhd
{
	  private String id ;        //报修返还单ID
      private String fh_date;    //报修返还时间       
      private String cj_date ;   //创建时间       	
      private String jsr    ;    //经手人  
      private String cjr    ;    //创建人     
      private String state  ;    //状态 (已保存；已提交)              
      private String remark ;     //备注      
      private String fkzh;        //付款账户
      private double ssje;        //实收金额
      private String bxcs;        //报修厂商
      private double hjje;        //合计金额
      
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

	public double getHjje() {
		return hjje;
	}
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             
	public void setHjje(double hjje) {
		this.hjje = hjje;
	}
	
	public String getBxcs() {
		return bxcs;
	}
	public void setBxcs(String bxcs) {
		this.bxcs = bxcs;
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

	public String getFh_date() {
		return fh_date;
	}
	public void setFh_date(String fh_date) {
		this.fh_date = fh_date;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getJsr() {
		return jsr;
	}
	public void setJsr(String jsr) {
		this.jsr = jsr;
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
