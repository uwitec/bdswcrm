package com.sw.cms.model;

public class ProductKcInit {
	
	private int id;    //编号
	private String store_id;  //库房编号
	private String store_name; //库房名称
	private String jsr;        //经手人
	private String jsr_name;
	private String create_date; //创建时间
	
	private String czr;
	private String cz_date;
	
	public String getCreate_date() {
		return create_date;
	}
	public void setCreate_date(String create_date) {
		this.create_date = create_date;
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
	public String getStore_id() {
		return store_id;
	}
	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}
	public String getStore_name() {
		return store_name;
	}
	public void setStore_name(String store_name) {
		this.store_name = store_name;
	}
	public String getJsr_name() {
		return jsr_name;
	}
	public void setJsr_name(String jsr_name) {
		this.jsr_name = jsr_name;
	}

}
