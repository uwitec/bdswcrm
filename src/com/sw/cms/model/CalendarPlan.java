package com.sw.cms.model;

import java.sql.Timestamp;

/**
 * 日程安排
 * @author liyt
 *
 */
public class CalendarPlan {
	
	private String id;          //编号
	private String cdate;       //日期
	private String start_time;  //开始时间
	private String end_time;    //结束时间
	private String address;     //地点
	private String content;     //内容
	private String is_remind = "1";   //是否提醒（0：否；1：是）
	private int remind_time = 10;    //提醒提前时间（分钟）
	private String grade = "1";       //紧急程度（1：一般；2：紧急）
	private String czr;         //操作人
	private Timestamp cz_date;  //时间戳
	
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
