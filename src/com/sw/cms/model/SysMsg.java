package com.sw.cms.model;

public class SysMsg {
	
	private int msg_id;              //���
	private String msg_body;         //��Ϣ����
	private String msg_read_flag;    //�Ƿ��Ѷ���־��0:δ����1:�Ѷ���
	private String sender_id;        //�����˱��
	private String sender_name;      //����������
	private String send_del_flag;    //������ɾ����־(0:δɾ����1:��ɾ��)
	private String send_time;        //����ʱ��
	private String reciever_id;      //�����˱��
	private String reciever_name;    //����������
	private String read_del_flag;    //������ɾ����־(0:δɾ����1:��ɾ��)
	private String read_time;        //��ȡʱ��
	private String mobile_num;       //�ֻ�����
	private String mobile_send_flag; //���ŷ��ͱ�־
	
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
	public String getRead_time() {
		return read_time;
	}
	public void setRead_time(String read_time) {
		this.read_time = read_time;
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
	public String getSend_time() {
		return send_time;
	}
	public void setSend_time(String send_time) {
		this.send_time = send_time;
	}
	public String getSender_id() {
		return sender_id;
	}
	public void setSender_id(String sender_id) {
		this.sender_id = sender_id;
	}
	public String getReciever_name() {
		return reciever_name;
	}
	public void setReciever_name(String reciever_name) {
		this.reciever_name = reciever_name;
	}
	public String getSender_name() {
		return sender_name;
	}
	public void setSender_name(String sender_name) {
		this.sender_name = sender_name;
	}

}
