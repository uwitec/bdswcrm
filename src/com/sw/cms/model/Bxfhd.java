package com.sw.cms.model;

/**
 * ���޷�����
 * @author Administrator
 * 2010-01-29�޸ģ�ֻʣ��������Ϣ�����޷�����ID�����޷���ʱ�䡢����ʱ�䡢�����ˡ������ˡ�״̬�������˻���ʵ�ս����޳��̡���ע���ϼƽ��
 *                             id          fh_date    cj_date jsr    cjr   state fkzh   ssje    bxcs    remark hjje
 */
public class Bxfhd
{
	  private String id ;        //���޷�����ID
      private String fh_date;    //���޷���ʱ��       
      private String cj_date ;   //����ʱ��       	
      private String jsr    ;    //������  
      private String cjr    ;    //������     
      private String state  ;    //״̬ (�ѱ��棻���ύ)              
      private String remark ;     //��ע      
      private String fkzh;        //�����˻�
      private double ssje;        //ʵ�ս��
      private String bxcs;        //���޳���
      private double hjje;        //�ϼƽ��
      
  	public String getFkzh() {
		return fkzh;
	}
	public void setFkzh(String fkzh) {
		this.fkzh = fkzh;
	}
 
	public double getSsje() {
		return ssje;
	}
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             
	public void setSsje(double ssje) {
		this.ssje = ssje;
	}

	public double getHjje() {
		return hjje;
	}
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             
	public void setHjje(double hjje) {
		this.hjje = hjje;
	}
	
	public String getBxcs() {
		return bxcs;
	}
	public void setBxcs(String bxcs) {
		this.bxcs = bxcs;
	}
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

	public String getFh_date() {
		return fh_date;
	}
	public void setFh_date(String fh_date) {
		this.fh_date = fh_date;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getJsr() {
		return jsr;
	}
	public void setJsr(String jsr) {
		this.jsr = jsr;
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

}
