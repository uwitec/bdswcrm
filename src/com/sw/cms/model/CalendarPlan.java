package com.sw.cms.model;

import java.sql.Timestamp;

/**
 * �ճ̰���
 * @author liyt
 *
 */
public class CalendarPlan {
	
	private String id;          //���
	private String cdate;       //����
	private String start_time;  //��ʼʱ��
	private String end_time;    //����ʱ��
	private String address;     //�ص�
	private String content;     //����
	private String is_remind = "1";   //�Ƿ����ѣ�0����1���ǣ�
	private int remind_time = 10;    //������ǰʱ�䣨���ӣ�
	private String grade = "1";       //�����̶ȣ�1��һ�㣻2��������
	private String czr;         //������
	private Timestamp cz_date;  //ʱ���
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCdate() {
		return cdate;
	}
	public void setCdate(String cdate) {
		this.cdate = cdate;
	}
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String startTime) {
		start_time = startTime;
	}
	public String getEnd_time() {
		return end_time;
	}
	public void setEnd_time(String endTime) {
		end_time = endTime;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getIs_remind() {
		return is_remind;
	}
	public void setIs_remind(String isRemind) {
		is_remind = isRemind;
	}
	public int getRemind_time() {
		return remind_time;
	}
	public void setRemind_time(int remindTime) {
		remind_time = remindTime;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getCzr() {
		return czr;
	}
	public void setCzr(String czr) {
		this.czr = czr;
	}
	public Timestamp getCz_date() {
		return cz_date;
	}
	public void setCz_date(Timestamp czDate) {
		cz_date = czDate;
	}

}
