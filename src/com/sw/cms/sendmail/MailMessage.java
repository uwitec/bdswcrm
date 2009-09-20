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

	private MimeMessage mimeMsg; // MIME�ʼ�����

	private Session session; // �ʼ��Ự����

	private Properties props; // ϵͳ����

	private String username = ""; // smtp��֤�û���������

	private String passWord = "";

	private Multipart mp; // Multipart����,�ʼ�����,����,���������ݾ���ӵ����к�������MimeMessage����

	private Logger logger = Logger.getLogger(this.getClass());

	public MailMessage() {

	}

	public MailMessage(String smtp) {
		setSmtpHost(smtp);
		createMimeMessage();
	}

	/**
	 * ����smtp����
	 * 
	 * @param hostName
	 */
	public void setSmtpHost(String hostName) {

		logger.info("����ϵͳ���ԣ�mail.smtp.host = " + hostName);

		if (props == null)
			props = System.getProperties(); // ���ϵͳ���Զ���

		props.put("mail.smtp.host", hostName); // ����SMTP����

	}

	/**
	 * ��ȡ�ʼ��Ự����
	 * 
	 * @return
	 */
	public boolean createMimeMessage() {

		try {

			logger.info("׼����ȡ�ʼ��Ự����");
			session = Session.getDefaultInstance(props, null); // ����ʼ��Ự����

		} catch (Exception e) {

			logger.info("��ȡ�ʼ��Ự����ʱ��������" + e);
			return false;

		}

		logger.info("׼������MIME�ʼ�����");

		try {

			mimeMsg = new MimeMessage(session); // ����MIME�ʼ�����
			mp = new MimeMultipart(); // mp һ��multipart����
			return true;

		} catch (Exception e) {

			logger.info("����MIME�ʼ�����ʧ�ܣ�" + e);
			return false;

		}

	}

	/**
	 * ���������֤
	 * 
	 * @param need
	 */
	public void setNeedAuth(boolean need) {

		logger.info("����smtp�����֤��mail.smtp.auth = " + need);

		if (props == null)
			props = System.getProperties();

		if (need) {

			props.put("mail.smtp.auth", "true");

		} else {

			props.put("mail.smtp.auth", "false");

		}

	}

	/**
	 * �����û���������
	 * 
	 * @param name
	 * @param pass
	 */
	public void setNamePass(String name, String pass) {
		username = name;
		passWord = pass;

	}

	/**
	 * �����ʼ�����
	 * 
	 * @param mailSubject
	 * @return
	 */
	public boolean setSubject(String mailSubject) {

		logger.info("�����ʼ����⣡");
		try {

			mimeMsg.setSubject(mailSubject);
			return true;

		} catch (Exception e) {

			logger.info("�����ʼ����ⷢ������");
			return false;

		}

	}

	/**
	 * �����ʼ���
	 * 
	 * @param mailBody
	 * @return
	 */
	public boolean setBody(String mailBody) {

		try {

			logger.info("�����ʼ����ʽ");

			BodyPart bp = new MimeBodyPart();

			bp.setContent(
					"<meta http-equiv=Content-Type content=text/html; charset=gb2312>"

					+ mailBody, "text/html;charset=GB2312");

			mp.addBodyPart(bp);

			return true;

		} catch (Exception e) {

			logger.info("�����ʼ�����ʱ��������" + e);
			return false;

		}

	}

	/**
	 * ���Ӹ���
	 * 
	 * @param filename
	 *            �����ļ�������·��
	 * @return
	 */
	public boolean addFileAffix(String filename) {

		logger.info("�����ʼ�������" + filename);
		try {

			BodyPart bp = new MimeBodyPart();
			FileDataSource fileds = new FileDataSource(filename);

			bp.setDataHandler(new DataHandler(fileds));
			bp.setFileName(MimeUtility.encodeText(fileds.getName()));
			mp.addBodyPart(bp);

			return true;

		} catch (Exception e) {

			logger.info("�����ʼ�������" + filename + "��������" + e);
			return false;

		}

	}

	/**
	 * ���÷�����
	 * 
	 * @param from
	 * @return
	 */
	public boolean setFrom(String from) {

		logger.info("���÷����ˣ�");

		try {

			mimeMsg.setFrom(new InternetAddress(from)); // ���÷�����

			return true;

		}

		catch (Exception e)

		{
			return false;
		}

	}

	/**
	 * ����������
	 * 
	 * @param to
	 * @return
	 */
	public boolean setTo(String to) {

		logger.info("����������");
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
	 * �����ʼ����Ͷ˿�
	 * @param port
	 * @return
	 */
	public boolean setPort(String port){
		logger.info("����ϵͳ���ԣ�mail.smtp.port = " + port);

		try{
			if (props == null)
				props = System.getProperties(); // ���ϵͳ���Զ���

			props.put("mail.smtp.port", port); // ����SMTP����
			
			return true;
		}catch(Exception e){
			logger.error("����ϵͳ����mail.smtp.port����ԭ��" + e.getMessage());
			return false;
		}

	}
	
	
	/**
	 * ����SSL��֤
	 * @return
	 */
	public boolean setSsl(){
		logger.info("����ϵͳ���ԣ�mail.smtp.socketFactory.class = javax.net.ssl.SSLSocketFactory");

		try{
			if (props == null) props = System.getProperties(); // ���ϵͳ���Զ���

			props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); // ��������֤
			props.setProperty("mail.smtp.socketFactory.fallback", "false");
			
			return true;
		}catch(Exception e){
			logger.error("����ϵͳ����mail.smtp.socketFactory.class����ԭ��" + e.getMessage());
			return false;
		}
	}
	


	/**
	 * �����ʼ�
	 * 
	 * @return
	 */
	public boolean sendout() {
		try {

			mimeMsg.setContent(mp);
			mimeMsg.saveChanges();

			logger.info("���ڷ����ʼ�....");
			
			Session mailSession = Session.getInstance(props, null);
			Transport transport = mailSession.getTransport("smtp");
			transport.connect((String) props.get("mail.smtp.host"), username,passWord);
			transport.sendMessage(mimeMsg, mimeMsg.getRecipients(Message.RecipientType.TO));

			logger.info("�����ʼ��ɹ���");
			transport.close();
			return true;

		} catch (Exception e) {

			logger.info("�ʼ�����ʧ�ܣ�" + e);
			return false;

		}
	}

}
