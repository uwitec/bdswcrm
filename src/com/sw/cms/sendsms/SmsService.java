package com.sw.cms.sendsms;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * ���Ͷ���Ϣ����
 * @author liyt
 *
 */
public class SmsService {
	
	/**
	 * ���Ͷ���Ϣ
	 * @param userid  String �û����
	 * @param pass    String �û�����
	 * @param mobiles String �������ֻ��ţ���","�ָ�
	 * @param msg     String ��Ϣ����
	 * @param time    String yyyy-MM-dd HH:mm:ss ����ʱ�䣨��ʱ���� ���ռ�ʱ���ͣ�
	 * @return String ����һ����Ϣ���(16λ) ֻҪ���Ƿ��� �����������ǳɹ�
	 */
	public String sendSms(String userid, String pass, String mobiles,
			String msg, String time) {
		String result = "-12";
		try {
			Document doc;
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setNamespaceAware(true);
			DocumentBuilder db = dbf.newDocumentBuilder();
			InputStream is = getSoapInputStream(userid, pass, mobiles, msg,
					time);
			if (is != null) {
				doc = db.parse(is);
				NodeList nl = doc.getElementsByTagName("SendMessagesResult");
				Node n = nl.item(0);
				result = n.getFirstChild().getNodeValue();
				is.close();
			}
			return result;
		} catch (Exception e) {
			System.out.print("SmsSoap.sendSms error:" + e.getMessage());
			return "-12";
		}
	}
	
	
	/**
	 * ��ȡ�û��˻���Ϣ
	 * @param userid  String �û���
	 * @param pass    String ����
	 * @return String ���ؽ��ͣ�000/�û���/���   /����   /�����ַ���/ǩ��   /�������ŵ���/���浥��/���ŵ���/����������
	 */
	public String GetInfo(String userid, String pass) {
		String result = "-12";
		try {
			Document doc;
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setNamespaceAware(true);
			DocumentBuilder db = dbf.newDocumentBuilder();
			InputStream is = getUserInfoInputStream(userid, pass);
			if (is != null) {
				doc = db.parse(is);
				NodeList nl = doc.getElementsByTagName("GetUserInfoResult");
				Node n = nl.item(0);
				result = n.getFirstChild().getNodeValue();
				is.close();
			}
			return result;
		} catch (Exception e) {
			System.out.print("SmsSoap.sendSms error:" + e.getMessage());
			return "-12";
		}
	}

	private String getSoapSmssend(String userid, String pass, String mobiles,
			String msg, String time) {
		try {
			String soap = "";
			soap = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
					+ "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
					+ "<soap:Body>"
					+ "<SendMessages xmlns=\"http://tempuri.org/\">" + "<uid>"
					+ userid + "</uid>" + "<pwd>" + pass + "</pwd>" + "<tos>"
					+ mobiles + "</tos>" + "<msg>" + msg + "</msg>" + "<otime>"
					+ time + "</otime>" + "</SendMessages>" + "</soap:Body>"
					+ "</soap:Envelope>";
			return soap;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	private InputStream getSoapInputStream(String userid, String pass,
			String mobiles, String msg, String time) throws Exception {
		URLConnection conn = null;
		InputStream is = null;
		try {
			String soap = getSoapSmssend(userid, pass, mobiles, msg, time);
			if (soap == null) {
				return null;
			}
			try {

				URL url = new URL("http://service2.winic.org:8003/Service.asmx");

				conn = url.openConnection();
				conn.setUseCaches(false);
				conn.setDoInput(true);
				conn.setDoOutput(true);
				conn.setRequestProperty("Content-Length", Integer.toString(soap
						.length()));
				conn.setRequestProperty("Content-Type",
						"text/xml; charset=utf-8");
				conn.setRequestProperty("HOST", "service2.winic.org");
				conn.setRequestProperty("SOAPAction",
						"\"http://tempuri.org/SendMessages\"");

				OutputStream os = conn.getOutputStream();
				OutputStreamWriter osw = new OutputStreamWriter(os, "utf-8");
				osw.write(soap);
				osw.flush();
			} catch (Exception ex) {
				System.out.print("SmsSoap.openUrl error:" + ex.getMessage());
			}
			try {
				is = conn.getInputStream();
			} catch (Exception ex1) {
				System.out.print("SmsSoap.getUrl error:" + ex1.getMessage());
			}

			return is;
		} catch (Exception e) {
			System.out.print("SmsSoap.InputStream error:" + e.getMessage());
			return null;
		}
	}


	private String getSoapUserInfo(String userid, String pass) {
		try {
			String soap = "";
			soap = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
					+ "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
					+ "<soap:Body>"
					+ "<GetUserInfo xmlns=\"http://tempuri.org/\">" + "<uid>"
					+ userid + "</uid>" + "<pwd>" + pass + "</pwd>"
					+ "</GetUserInfo>" + "</soap:Body>" + "</soap:Envelope>";
			return soap;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	private InputStream getUserInfoInputStream(String userid, String pass)
			throws Exception {
		URLConnection conn = null;
		InputStream is = null;
		try {
			String soap = getSoapUserInfo(userid, pass);
			if (soap == null) {
				return null;
			}
			try {

				URL url = new URL("http://service2.winic.org/Service.asmx");

				conn = url.openConnection();
				conn.setUseCaches(false);
				conn.setDoInput(true);
				conn.setDoOutput(true);
				conn.setRequestProperty("Content-Length", Integer.toString(soap
						.length()));
				conn.setRequestProperty("Content-Type",
						"text/xml; charset=utf-8");
				conn.setRequestProperty("HOST", "service2.winic.org");
				conn.setRequestProperty("SOAPAction",
						"\"http://tempuri.org/GetUserInfo\"");

				OutputStream os = conn.getOutputStream();
				OutputStreamWriter osw = new OutputStreamWriter(os, "utf-8");
				osw.write(soap);
				osw.flush();
			} catch (Exception ex) {
				System.out.print("SmsSoap.openUrl error:" + ex.getMessage());
			}
			try {
				is = conn.getInputStream();
			} catch (Exception ex1) {
				System.out.print("SmsSoap.getUrl error:" + ex1.getMessage());
			}

			return is;
		} catch (Exception e) {
			System.out.print("SmsSoap.InputStream error:" + e.getMessage());
			return null;
		}
	}



	private String getVoiceSend(String userid, String pwd, String txtPhone,
			String vtxt, String Svmode, byte[] buffer, String svrno,
			String str_time, String end_time) throws Exception {
		try {
			String soap = "";
			soap = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
					+ "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
					+ "<soap:Body>"
					+ "<SendVoice xmlns=\"http://tempuri.org/\">" + "<uid>"
					+ userid
					+ "</uid>"
					+ "<pwd>"
					+ pwd
					+ "</pwd>"
					+ "<vto>"
					+ txtPhone
					+ "</vto>"
					+ "<vtxt>"
					+ vtxt
					+ "</vtxt>"
					+ "<mode>"
					+ Svmode
					+ "</mode>"
					+ "<FileBytes>"
					+ buffer
					+ "</FileBytes>"
					+ "<svrno>"
					+ svrno
					+ "</svrno>"
					+ "<str_time>"
					+ str_time
					+ "</str_time>"
					+ "<end_time>"
					+ end_time
					+ "</end_time>"
					+ "</SendVoice>"
					+ "</soap:Body>" + "</soap:Envelope>";
			return soap;

		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	private InputStream getVoiceInputStream(String userid, String pwd,
			String txtPhone, String vtxt, String Svmode, String vfbtye,
			String svrno, String str_time, String end_time) throws Exception {
		URLConnection conn = null;
		InputStream is = null;
		try {

			byte[] buffer1 = null;

			if (Svmode == "3") {
				String vPath = vfbtye;
				if (vPath == "") {
					String xx = "��ѡ�������ļ�����ʽΪ.WAV ��С��Ҫ���� 2M";
					return null;
				} else {
					int i = vPath.lastIndexOf(".");
					// ȡ���ĵ���չ��
					String newext = vPath.substring(i + 1).toLowerCase();
					if (!newext.equals("wav")) {
						String xx = "�����ļ���ʽ����ȷ";
						return null;
					}

				}

			} else {
				buffer1 = new byte[0];
			}

			String soap = getVoiceSend(userid, pwd, txtPhone, vtxt, Svmode,
					buffer1, svrno, str_time, end_time);
			if (soap == null) {
				return null;
			}
			try {

				URL url = new URL("http://service2.winic.org/Service.asmx");

				conn = url.openConnection();
				conn.setUseCaches(false);
				conn.setDoInput(true);
				conn.setDoOutput(true);
				conn.setRequestProperty("Content-Length", Integer.toString(soap
						.length()));
				conn.setRequestProperty("Content-Type",
						"text/xml; charset=utf-8");
				conn.setRequestProperty("HOST", "service2.winic.org");
				conn.setRequestProperty("SOAPAction",
						"\"http://tempuri.org/SendVoice\"");

				OutputStream os = conn.getOutputStream();
				OutputStreamWriter osw = new OutputStreamWriter(os, "utf-8");
				osw.write(soap);
				osw.flush();
			} catch (Exception ex) {
				System.out.print("SmsSoap.openUrl error:" + ex.getMessage());
			}
			try {
				is = conn.getInputStream();
			} catch (Exception ex1) {
				System.out.print("SmsSoap.getUrl error:" + ex1.getMessage());
			}

			return is;
		} catch (Exception e) {
			System.out.print("SmsSoap.InputStream error:" + e.getMessage());
			return null;
		}
	}
	
	public static void main(String[] args) {
		SmsService sv = new SmsService();
//		sv.sendSms("jinyanlong", "long88", "13810528167", "���ŷ��Ͳ���", "");
		System.out.println(sv.GetInfo("jinyanlong", "long88"));
	}

}
