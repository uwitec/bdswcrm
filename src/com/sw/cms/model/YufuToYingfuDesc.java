package com.sw.cms.model;

public class YufuToYingfuDesc {

	private int id;            //��ˮ��
	private String yw_id;      //��ӦԤ��תӦ�ձ��
	private String jhd_id;     //��������ţ������������
	private double yingfuje;   //������Ӧ�����
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
