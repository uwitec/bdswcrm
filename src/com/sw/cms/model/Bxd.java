package com.sw.cms.model;

/*
 * ���޵�
 * 2010-01-29 �޸ģ���������Ϣ����š�����ʱ�䡢����ʱ�䡢�����ˡ����޳��̡������ˡ����޵�״̬����ע
 *                              id   bxdate  cjdate  jsr     bxcs    cjr    state    remark
 */
public class Bxd {
	private String id; // ���޵����

	private String bxdate; // ����ʱ��

	private String cjdate; // ����ʱ��

	private String jsr; // ������

	private String cjr; // ������

	private String state; // ���޵�״̬�������У������꣩

	private String bxcs; // ���޳���

	private String remark; // ��ע

	public String getCjdate() {
		return cjdate;
	}

	public void setCjdate(String cjdate) {
		this.cjdate = cjdate;
	}

	public String getCjr() {
		return cjr;
	}

	public void setCjr(String cjr) {
		this.cjr = cjr;
	}

	public String getBxcs() {
		return bxcs;
	}

	public void setBxcs(String bxcs) {
		this.bxcs = bxcs;
	}

	public String getJsr() {
		return jsr;
	}

	public void setJsr(String jsr) {
		this.jsr = jsr;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBxdate() {
		return bxdate;
	}

	public void setBxdate(String bxdate) {
		this.bxdate = bxdate;
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
