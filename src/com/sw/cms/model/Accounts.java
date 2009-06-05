package com.sw.cms.model;

public class Accounts {
	
	private String id;
	private String name;
	private String type;
	private double qcje;
	private double dqje;
	private String bank;
	private String bank_count;
	private String remark;
	
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}
	public String getBank_count() {
		return bank_count;
	}
	public void setBank_count(String bank_count) {
		this.bank_count = bank_count;
	}
	public double getDqje() {
		return dqje;
	}
	public void setDqje(double dqje) {
		this.dqje = dqje;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getQcje() {
		return qcje;
	}
	public void setQcje(double qcje) {
		this.qcje = qcje;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

}
