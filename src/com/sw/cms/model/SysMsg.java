package com.sw.cms.model;

import java.sql.Timestamp;

public class SysMsg {
	
	private int msg_id;              //编号
	private String msg_body;         //消息内容
	private String msg_read_flag;    //是否已读标志（0:未读；1:已读）
	private String sender_id;        //发送人编号
	private String send_del_flag;    //发送人删除标志(0:未删除；1:已删除)
	private Timestamp send_time;        //发送时间
	private String reciever_id;      //接收人编号
	private String read_del_flag;    //接收人删除标志(0:未删除；1:已删除)
	private Timestamp  read_time;        //读取时间
	private String mobile_num;       //手机号码
	private String mobile_send_flag; //短信发送标志
	
	public String getMobile_num() {
		return mobile_num;
	}
	public void setMobile_num(String mobile_num) {
		this.mobile_num = mobile_num;
	}
	public String getMobile_send_flag() {
		return mobile_send_flag;
	}
	public void setMobile_send_flag(String mobile_send_flag) {
		this.mobile_send_flag = mobile_send_flag;
	}
	public String getMsg_body() {
		return msg_body;
	}
	public void setMsg_body(String msg_body) {
		this.msg_body = msg_body;
	}
	public int getMsg_id() {
		return msg_id;
	}
	public void setMsg_id(int msg_id) {
		this.msg_id = msg_id;
	}
	public String getMsg_read_flag() {
		return msg_read_flag;
	}
	public void setMsg_read_flag(String msg_read_flag) {
		this.msg_read_flag = msg_read_flag;
	}
	public String getRead_del_flag() {
		return read_del_flag;
	}
	public void setRead_del_flag(String read_del_flag) {
		this.read_del_flag = read_del_flag;
	}
	public String getReciever_id() {
		return reciever_id;
	}
	public void setReciever_id(String reciever_id) {
		this.reciever_id = reciever_id;
	}
	public String getSend_del_flag() {
		return send_del_flag;
	}
	public void setSend_del_flag(String send_del_flag) {
		this.send_del_flag = send_del_flag;
	}
	public String getSender_id() {
		return sender_id;
	}
	public void setSender_id(String sender_id) {
		this.sender_id = sender_id;
	}
	public Timestamp getSend_time() {
		return send_time;
	}
	public void setSend_time(Timestamp sendTime) {
		send_time = sendTime;
	}
	public Timestamp getRead_time() {
		return read_time;
	}
	public void setRead_time(Timestamp readTime) {
		read_time = readTime;
	}

}
