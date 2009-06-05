package com.sw.cms.model;

public class YufuToYingfuDesc {

	private int id;            //流水号
	private String yw_id;      //对应预收转应收编号
	private String jhd_id;     //进货单编号（结算进货单）
	private double yingfuje;   //进货单应付金额
	private double bcjs;       //本次结算金额
	private String remark;     //备注
	
	public double getBcjs() {
		return bcjs;
	}
	public void setBcjs(double bcjs) {
		this.bcjs = bcjs;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getJhd_id() {
		return jhd_id;
	}
	public void setJhd_id(String jhd_id) {
		this.jhd_id = jhd_id;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public double getYingfuje() {
		return yingfuje;
	}
	public void setYingfuje(double yingfuje) {
		this.yingfuje = yingfuje;
	}
	public String getYw_id() {
		return yw_id;
	}
	public void setYw_id(String yw_id) {
		this.yw_id = yw_id;
	}
	
}
