package com.sw.cms.model;

/*
 * ������
 * 2010-02-24 ���ӣ���������Ϣ����š�����ʱ�䡢����ʱ�䡢�����ˡ������ˡ����޵�״̬����ע
 *                          id   hj_date  cj_date  jsr   cjr    state    remark
 */
public class Hjd {
	private String id; // ���������

	private String hj_date; // ����ʱ��

	private String cj_date; // ����ʱ��

	private String jsr; // ������

	private String cjr; // ������

	private String state; // ������״̬���ѱ��桢���ύ��
	
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

	public String getHj_date() {
		return hj_date;
	}

	public void setHj_date(String hj_date) {
		this.hj_date = hj_date;
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
