package com.sw.cms.model;

public class SendMsgLog {
	
	private String seq_id;      //流水号
	private String user_name;   //接收人姓名
	private String mobile_num;  //手机号码
	private String send_time;   //发送时间
	private String flag;        //发送标志
	private String error_log;   //错误原因
	private String czr;         //当前操作人
	private String cz_date;     //操作时间
	
	
	public String getCz_date() {
		return cz_date;
	}
	public void setCz_date(String cz_date) {
		this.cz_date = cz_date;
	}
	public String getCzr() {
		return czr;
	}
	public void setCzr(String czr) {
		this.czr = czr;
	}
	public String getError_log() {
		return error_log;
	}
	public void setError_log(String error_log) {
		this.error_log = error_log;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getMobile_num() {
		return mobile_num;
	}
	public void setMobile_num(String mobile_num) {
		this.mobile_num = mobile_num;
	}
	public String getSend_time() {
		return send_time;
	}
	public void setSend_time(String send_time) {
		this.send_time = send_time;
	}
	public String getSeq_id() {
		return seq_id;
	}
	public void setSeq_id(String seq_id) {
		this.seq_id = seq_id;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

}
