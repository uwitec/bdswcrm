package com.sw.cms.model;
/**
 * ά����ⵥ
 * @author Administrator
 * 2010-03-01�޸ģ���������Ϣ��id      wxrk_date  cj_date  jsr     cjr  state  remark
 *                     ά�������   ά���������  ��������  ������  ������  ״̬  ��ע
 */
public class Wxrkd 
{
	 private String id  ;    //ά����ⵥID
     private String wxrk_date ;//ά�����ʱ��       
     private String cj_date ;//����ʱ��       	
     private String jsr    ; //������  
     private String cjr    ; //������
     private String state  ;// ״̬  
     private String  remark ;//��ע
	
	public String getCj_date() {
		return cj_date;
	}
	public void setCj_date(String cj_date) {
		this.cj_date = cj_date;
	}
	public String getCjr() {
		return cjr;
	}
	public void setCjr(String cjr) {
		this.cjr = cjr;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getWxrk_date() {
		return wxrk_date;
	}
	public void setWxrk_date(String wxrk_date) {
		this.wxrk_date = wxrk_date;
	}
	public String getJsr() {
		return jsr;
	}
	public void setJsr(String jsr) {
		this.jsr = jsr;
	}
}
