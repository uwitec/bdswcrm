package com.sw.cms.model;

/**
 * 预收转应收明细信息
 * @author liyt
 *
 */
public class YushouToYingshouDesc {

	private int id;           //流水号
	private String yw_id;     //对应预收转应收编号
	private String xsd_id;    //销售单编号（结算销售单）
	private double yingshouje; //销售单应收金额
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
	public double getYingshouje() {
		return yingshouje;
	}
	public void setYingshouje(double yingshouje) {
		this.yingshouje = yingshouje;
	}
	public String getYw_id() {
		return yw_id;
	}
	public void setYw_id(String yw_id) {
		this.yw_id = yw_id;
	}
	
}
