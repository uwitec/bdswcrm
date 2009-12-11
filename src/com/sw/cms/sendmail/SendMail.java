package com.sw.cms.sendmail;

import com.sw.cms.dao.MailDAO;
import com.sw.cms.model.MailSet;

public class SendMail {
	
	private MailDAO mailDao;

	private String stmpHost;

	private String name;

	private String password;

	private String from;
	
	private String ssl;
	
	private String port;
	
	private String remark;



	public void send(String user_id,String subject, String body, String[] files, String to) {
		MailSet mailSet = mailDao.getMailSet(user_id);
		if(mailSet != null){
			stmpHost = mailSet.getSmtp();
			name = mailSet.getUser_name();
			password = mailSet.getPassword();
			from = mailSet.getFrom_user();
			ssl = mailSet.getIs_ssl();
			port = mailSet.getPort_num();
			remark=mailSet.getRemark();
		}

		MailMessage msg = new MailMessage();
		msg.setSmtpHost(stmpHost);
		msg.createMimeMessage();
		msg.setNeedAuth(true);
		msg.setNamePass(name, password);
		msg.setSubject(subject);
		msg.setFrom(from);
		msg.setPort(port);
		
		if(ssl.equals("true")){
			msg.setSsl();
		}
		if(!remark.equals(""))
		{
			body+=body+"<br>"+"-----<br>"+remark;
		}
		
		msg.setBody(body);
		
		if(files != null && files.length > 0){
			for(int i=0;i<files.length;i++){
				msg.addFileAffix(files[i]);
			}
		}
		
		msg.setTo(to);
		msg.sendout();
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getStmpHost() {
		return stmpHost;
	}

	public void setStmpHost(String stmpHost) {
		this.stmpHost = stmpHost;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getSsl() {
		return ssl;
	}

	public void setSsl(String ssl) {
		this.ssl = ssl;
	}

	public MailDAO getMailDao() {
		return mailDao;
	}

	public void setMailDao(MailDAO mailDao) {
		this.mailDao = mailDao;
	}
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
