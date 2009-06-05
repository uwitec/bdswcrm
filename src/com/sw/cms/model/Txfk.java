package com.sw.cms.model;

/**
 * 摊销付款
 * @author liyt
 *
 */
public class Txfk {

	private String id;          //编号
	private String client_name; //相关客户
	private String fk_date;     //付款日期	
	private String jsr;         //经手人	
	private String account_id;  //付款账户	
	private String fklx;        //摊销付款类型	
	private double fkje;        //付款金额	
	private String remark;      //备注	
	private String czr;         //操作人编号	
	private String cz_date;     //操作时间
	private String state;       //状态
	
	public String getAccount_id() {
		return account_id;
	}
	public void setAccount_id(String account_id) {
		this.account_id = account_id;
	}
	public String getClient_name() {
		return client_name;
	}
	public void setClient_name(String client_name) {
		this.client_name = client_name;
	}
	public String getCz_date() {
		return cz_date;
	}
	public void setCz_date(String cz_date) {
		this.cz_date = cz_date;
	}
	public String getCzr() {
		return czr;
	}
	public void setCzr(String czr) {
		this.czr = czr;
	}
	public String getFk_date() {
		return fk_date;
	}
	public void setFk_date(String fk_date) {
		this.fk_date = fk_date;
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
	public double getFkje() {
		return fkje;
	}
	public void setFkje(double fkje) {
		this.fkje = fkje;
	}
	public String getFklx() {
		return fklx;
	}
	public void setFklx(String fklx) {
		this.fklx = fklx;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
}
