package com.sw.cms.model;

/**
 * ���۷�Ʊ����-��Ʊ��ϸ
 * @author liyt
 *
 */
public class XsfpFpmx {
	
	private int id;   //���
	private String fpxx_id;  //��Ӧ
	
	private String khmc;
	private String yw_id;
	private String cdate;
	private double kpje_ying;   //Ӧ��Ʊ���
	private double kpje_yi;    //�ѿ�Ʊ���
	private double kpje_bc;    //���ο�Ʊ���
	private String remark;      //��ע
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFpxx_id() {
		return fpxx_id;
	}
	public void setFpxx_id(String fpxx_id) {
		this.fpxx_id = fpxx_id;
	}
	public String getKhmc() {
		return khmc;
	}
	public void setKhmc(String khmc) {
		this.khmc = khmc;
	}
	public String getYw_id() {
		return yw_id;
	}
	public void setYw_id(String yw_id) {
		this.yw_id = yw_id;
	}
	public String getCdate() {
		return cdate;
	}
	public void setCdate(String cdate) {
		this.cdate = cdate;
	}
	public double getKpje_ying() {
		return kpje_ying;
	}
	public void setKpje_ying(double kpje_ying) {
		this.kpje_ying = kpje_ying;
	}
	public double getKpje_yi() {
		return kpje_yi;
	}
	public void setKpje_yi(double kpje_yi) {
		this.kpje_yi = kpje_yi;
	}
	public double getKpje_bc() {
		return kpje_bc;
	}
	public void setKpje_bc(double kpje_bc) {
		this.kpje_bc = kpje_bc;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
