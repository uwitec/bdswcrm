package com.sw.cms.model;

/*
 * ���ϵ�
 * 2010-02-22 ���ӣ���������Ϣ����š�����ʱ�䡢����ʱ�䡢�����ˡ������ˡ����ϵ�״̬����ע
 *                           id   bf_date  cj_date  jsr  cjr    state    remark
 */
public class Bfd {
	private String id; // ���ϵ����

	private String bf_date; // ����ʱ��

	private String cj_date; // ����ʱ��

	private String jsr; // ������

	private String cjr; // ������

	private String state; // ���ϵ�״̬���ѱ��桢���ύ��

	private String remark; // ��ע

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

	public String getBf_date() {
		return bf_date;
	}

	public void setBf_date(String bf_date) {
		this.bf_date = bf_date;
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
