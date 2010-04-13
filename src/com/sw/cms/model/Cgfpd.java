package com.sw.cms.model;

import java.util.Date;
/**
 * 
 * @author zhj
 * 采购发票单
 */
public class Cgfpd {
	
	private String id;   //采购发票编号
	private String cg_date; //采购日期
	private String gysbh;//供应商编号
	private String jhd_id; //进货单编号
	private double total;  //进货单金额
	private String state;  //状态	已入库、未入库
	private String czr;    //操作人
	private Date cz_date;
	private String ms;    //描述信息	
	
	public String getCg_date() {
		return cg_date;
	}
	public void setCg_date(String cg_date) {
		this.cg_date = cg_date;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public String getJhd_id() {
		return jhd_id;
	}
	public void setJhd_id(String jhd_id) {
		this.jhd_id = jhd_id;
	}
	public String getGysbh() {
		return gysbh;
	}
	public void setGysbh(String gysbh) {
		this.gysbh = gysbh;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCzr() {
		return czr;
	}
	public void setCzr(String czr) {
		this.czr = czr;
	}
	
	public Date getCz_date() {
		return cz_date;
	}
	public void setCz_date(Date czDate) {
		cz_date = czDate;
	}
	public String getMs() {
		return ms;
	}
	public void setMs(String ms) {
		this.ms = ms;
	}
}
