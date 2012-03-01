package com.sw.cms.model;

import java.util.Date;

/**
 * –Ú¡–∫≈≈Ãµ„º«¬º
 * @author liyt
 *
 */
public class SerialNumPd {
	
	private int id;
	private String cdate;
	private String jsr;
	private String store_id;
	private Date cz_date;
	private String pd_result;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCdate() {
		return cdate;
	}
	public void setCdate(String cdate) {
		this.cdate = cdate;
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
	public Date getCz_date() {
		return cz_date;
	}
	public void setCz_date(Date cz_date) {
		this.cz_date = cz_date;
	}
	public String getPd_result() {
		return pd_result;
	}
	public void setPd_result(String pd_result) {
		this.pd_result = pd_result;
	}

}
