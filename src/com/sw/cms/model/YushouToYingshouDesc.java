package com.sw.cms.model;

/**
 * Ԥ��תӦ����ϸ��Ϣ
 * @author liyt
 *
 */
public class YushouToYingshouDesc {

	private int id;           //��ˮ��
	private String yw_id;     //��ӦԤ��תӦ�ձ��
	private String xsd_id;    //���۵���ţ��������۵���
	private double yingshouje; //���۵�Ӧ�ս��
	private double bcjs;       //���ν�����
	private String remark;     //��ע
	
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
