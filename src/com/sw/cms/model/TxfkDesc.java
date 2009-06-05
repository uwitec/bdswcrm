package com.sw.cms.model;

/**
 * 摊销付款明细
 * @author liyt
 *
 */
public class TxfkDesc {

	private String id;         //付款明细编号
	private String txfk_id;    //对应摊销付款编号
	private String txrq;       //摊销日期
	private double je;         //金额
	private String remark;     //备注
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public double getJe() {
		return je;
	}
	public void setJe(double je) {
		this.je = je;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getTxfk_id() {
		return txfk_id;
	}
	public void setTxfk_id(String txfk_id) {
		this.txfk_id = txfk_id;
	}
	public String getTxrq() {
		return txrq;
	}
	public void setTxrq(String txrq) {
		this.txrq = txrq;
	}
	
}
