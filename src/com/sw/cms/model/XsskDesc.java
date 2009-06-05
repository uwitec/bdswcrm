package com.sw.cms.model;

public class XsskDesc {
	
	private int id;
	private String xsd_id;
	private double bcsk;
	private String xssk_id;
	private String remark;
	private String fsrq;
	private double fsje;
	
	public double getBcsk() {
		return bcsk;
	}
	public void setBcsk(double bcsk) {
		this.bcsk = bcsk;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getXsd_id() {
		return xsd_id;
	}
	public void setXsd_id(String xsd_id) {
		this.xsd_id = xsd_id;
	}
	public String getXssk_id() {
		return xssk_id;
	}
	public void setXssk_id(String xssk_id) {
		this.xssk_id = xssk_id;
	}
	public double getFsje() {
		return fsje;
	}
	public void setFsje(double fsje) {
		this.fsje = fsje;
	}
	public String getFsrq() {
		return fsrq;
	}
	public void setFsrq(String fsrq) {
		this.fsrq = fsrq;
	}

}
