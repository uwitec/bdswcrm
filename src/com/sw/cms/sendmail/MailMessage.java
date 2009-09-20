package com.sw.cms.sendmail;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.apache.log4j.Logger;

public class MailMessage {

	private MimeMessage mimeMsg; // MIME邮件对象

	private Session session; // 邮件会话对象

	private Properties props; // 系统属性

	private String username = ""; // smtp认证用户名和密码

	private String passWord = "";

	private Multipart mp; // Multipart对象,邮件内容,标题,附件等内容均添加到其中后再生成MimeMessage对象

	private Logger logger = Logger.getLogger(this.getClass());

	public MailMessage() {

	}

	public MailMessage(String smtp) {
		setSmtpHost(smtp);
		createMimeMessage();
	}

	/**
	 * 设置smtp主机
	 * 
	 * @param hostName
	 */
	public void setSmtpHost(String hostName) {

		logger.info("设置系统属性：mail.smtp.host = " + hostName);

		if (props == null)
			props = System.getProperties(); // 获得系统属性对象

		props.put("mail.smtp.host", hostName); // 设置SMTP主机

	}

	/**
	 * 获取邮件会话对象
	 * 
	 * @return
	 */
	public boolean createMimeMessage() {

		try {

			logger.info("准备获取邮件会话对象！");
			session = Session.getDefaultInstance(props, null); // 获得邮件会话对象

		} catch (Exception e) {

			logger.info("获取邮件会话对象时发生错误！" + e);
			return false;

		}

		logger.info("准备创建MIME邮件对象！");

		try {

			mimeMsg = new MimeMessage(session); // 创建MIME邮件对象
			mp = new MimeMultipart(); // mp 一个multipart对象
			return true;

		} catch (Exception e) {

			logger.info("创建MIME邮件对象失败！" + e);
			return false;

		}

	}

	/**
	 * 设置身份验证
	 * 
	 * @param need
	 */
	public void setNeedAuth(boolean need) {

		logger.info("设置smtp身份认证：mail.smtp.auth = " + need);

		if (props == null)
			props = System.getProperties();

		if (need) {

			props.put("mail.smtp.auth", "true");

		} else {

			props.put("mail.smtp.auth", "false");

		}

	}

	/**
	 * 设置用户名及密码
	 * 
	 * @param name
	 * @param pass
	 */
	public void setNamePass(String name, String pass) {
		username = name;
		passWord = pass;

	}

	/**
	 * 设置邮件主题
	 * 
	 * @param mailSubject
	 * @return
	 */
	public boolean setSubject(String mailSubject) {

		logger.info("设置邮件主题！");
		try {

			mimeMsg.setSubject(mailSubject);
			return true;

		} catch (Exception e) {

			logger.info("设置邮件主题发生错误！");
			return false;

		}

	}

	/**
	 * 设置邮件体
	 * 
	 * @param mailBody
	 * @return
	 */
	public boolean setBody(String mailBody) {

		try {

			logger.info("设置邮件体格式");

			BodyPart bp = new MimeBodyPart();

			bp.setContent(
					"<meta http-equiv=Content-Type content=text/html; charset=gb2312>"

					+ mailBody, "text/html;charset=GB2312");

			mp.addBodyPart(bp);

			return true;

		} catch (Exception e) {

			logger.info("设置邮件正文时发生错误！" + e);
			return false;

		}

	}

	/**
	 * 增加附件
	 * 
	 * @param filename
	 *            附件文件名包括路径
	 * @return
	 */
	public boolean addFileAffix(String filename) {

		logger.info("增加邮件附件：" + filename);
		try {

			BodyPart bp = new MimeBodyPart();
			FileDataSource fileds = new FileDataSource(filename);

			bp.setDataHandler(new DataHandler(fileds));
			bp.setFileName(MimeUtility.encodeText(fileds.getName()));
			mp.addBodyPart(bp);

			return true;

		} catch (Exception e) {

			logger.info("增加邮件附件：" + filename + "发生错误！" + e);
			return false;

		}

	}

	/**
	 * 设置发信人
	 * 
	 * @param from
	 * @return
	 */
	public boolean setFrom(String from) {

		logger.info("设置发信人！");

		try {

			mimeMsg.setFrom(new InternetAddress(from)); // 设置发信人

			return true;

		}

		catch (Exception e)

		{
			return false;
		}

	}

	/**
	 * 设置收信人
	 * 
	 * @param to
	 * @return
	 */
	public boolean setTo(String to) {

		logger.info("设置收信人");
		if (to == null)
			return false;

		try {

			mimeMsg.setRecipients(Message.RecipientType.TO, InternetAddress
					.parse(to));
			return true;

		} catch (Exception e) {

			return false;

		}

	}
	
	
	/**
	 * 设置邮件发送端口
	 * @param port
	 * @return
	 */
	public boolean setPort(String port){
		logger.info("设置系统属性：mail.smtp.port = " + port);

		try{
			if (props == null)
				props = System.getProperties(); // 获得系统属性对象

			props.put("mail.smtp.port", port); // 设置SMTP主机
			
			return true;
		}catch(Exception e){
			logger.error("设置系统属性mail.smtp.port出错，原因：" + e.getMessage());
			return false;
		}

	}
	
	
	/**
	 * 设置SSL验证
	 * @return
	 */
	public boolean setSsl(){
		logger.info("设置系统属性：mail.smtp.socketFactory.class = javax.net.ssl.SSLSocketFactory");

		try{
			if (props == null) props = System.getProperties(); // 获得系统属性对象

			props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); // 设置验收证
			props.setProperty("mail.smtp.socketFactory.fallback", "false");
			
			return true;
		}catch(Exception e){
			logger.error("设置系统属性mail.smtp.socketFactory.class出错，原因：" + e.getMessage());
			return false;
		}
	}
	


	/**
	 * 发送邮件
	 * 
	 * @return
	 */
	public boolean sendout() {
		try {

			mimeMsg.setContent(mp);
			mimeMsg.saveChanges();

			logger.info("正在发送邮件....");
			
			Session mailSession = Session.getInstance(props, null);
			Transport transport = mailSession.getTransport("smtp");
			transport.connect((String) props.get("mail.smtp.host"), username,passWord);
			transport.sendMessage(mimeMsg, mimeMsg.getRecipients(Message.RecipientType.TO));

			logger.info("发送邮件成功！");
			transport.close();
			return true;

		} catch (Exception e) {

			logger.info("邮件发送失败！" + e);
			return false;

		}
	}

}
