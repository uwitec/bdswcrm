package com.sw.cms.sendmail;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

 

 
 
public class SendMail {
	
	private String stmpHost;
	private String name;
	private String password;
	private String from;
	

	
	public void send(String subject,String body,String to){
		 
		MailMessage msg = new MailMessage();		
		msg.setSmtpHost(stmpHost);
		msg.createMimeMessage();
		msg.setNeedAuth(true);
		msg.setNamePass(name, password);
		msg.setSubject(subject);
		msg.setBody(body);
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
}
