package com.sw.cms.model;

/**
 * ̯��������ϸ
 * @author liyt
 *
 */
public class TxfkDesc {

	private String id;         //������ϸ���
	private String txfk_id;    //��Ӧ̯��������
	private String txrq;       //̯������
	private double je;         //���
	private String remark;     //��ע
	
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
