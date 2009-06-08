package com.sw.cms.sendsms;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.smslib.GatewayException;
import org.smslib.OutboundMessage;
import org.smslib.Service;
import org.smslib.TimeoutException;
import org.smslib.Message.MessageEncodings;
import org.smslib.modem.SerialModemGateway;

import com.sw.cms.dao.FsdxDAO;
import com.sw.cms.model.SendMsgLog;
import com.sw.cms.util.DateComFunc;

/**
 * ���ŷ��ͽ����߼���
 * 
 * @author sw
 * 
 */
public class SMSEngine {
	private Logger log = Logger.getLogger(this.getClass());
	
	private FsdxDAO fsdxDao;

	private static SMSEngine engine = null; // ���õ���ģʽ����

	private Service service = null; // ���ŵķ�����

	private SMSEngine() {

	}

	public static SMSEngine getSMSEngine() {
		if (engine == null) {
			engine = new SMSEngine();
		}
		return engine;
	}

	/**
	 * ��ʼ������
	 * 
	 */
	public void initService() throws Exception{
		service = new Service();// NEW һ������
		// NEW һ��GSM���ƽ���� ��(����) ��(�˿�) ��(������)
		
		//windows�´���
		//SerialModemGateway gateway = new SerialModemGateway("modem.com1","COM1", 9600, "Nokia", "6310i");
		
		//linux�´���
		SerialModemGateway gateway = new SerialModemGateway("SWSMS","/dev/ttyS0", 9600, "wavecom", "M1306B");
		
		gateway.setInbound(true); // �Ƿ���Խ��ܶ���
		gateway.setOutbound(true);// �Ƿ���Է��Ͷ���
		gateway.setSimPin("0000");// ����SIM���ĸ���ʶ�����

		service.addGateway(gateway);// ���һ��GSM���ƽ����
		
		try{
			service.startService(); // ��ʼ����
		}catch(Exception e){
			log.error("����è����ʧ��,ʧ��ԭ��:" + e.getMessage());
			service.stopService();
		}
	}

	/**
	 * ������Ϣ
	 * 
	 * @param receptMobiles
	 *            �ֻ�����
	 * @param msg
	 *            ��Ϣ��
	 */
	public List sendMsg(String sendLinkman, String msg, String user_id) {

		List<SendMsgLog> sendMsgLogs = new ArrayList<SendMsgLog>(); // ��־�б�
		List sendList = fsdxDao.getLinkmanById(sendLinkman);

		if (sendList != null && sendList.size() > 0) {

			for (int i = 0; i < sendList.size(); i++) {
				Map map = (Map) sendList.get(i);
				OutboundMessage obmsg = new OutboundMessage(map.get("yddh")
						.toString(), msg);
				obmsg.setEncoding(MessageEncodings.ENCUCS2);// �����ַ���
				SendMsgLog sendMsgLog = new SendMsgLog();
				sendMsgLog.setUser_name(map.get("name").toString());

				sendMsgLog.setMobile_num(map.get("yddh").toString());

				sendMsgLog.setCz_date(DateComFunc.formatDate(new Date(),
						"yyyy-MM-dd HH:mm:ss"));

				sendMsgLog.setCzr(user_id);

				sendMsgLog.setSend_time(DateComFunc.formatDate(new Date(),
						"yyyy-MM-dd HH:mm:ss"));

				try {
					if (service.sendMessage(obmsg) == true) {
						sendMsgLog.setFlag("1");

					} else {
						sendMsgLog.setFlag("0");

					}

				} catch (TimeoutException timeoutException) {
					sendMsgLog.setError_log("�����г�ʱ");
				} catch (GatewayException gatewayException) {
					sendMsgLog.setError_log("�������Ӵ���");
				} catch (IOException ioException) {
					sendMsgLog.setError_log("���ʹ���");
				} catch (InterruptedException interruptedException) {
					sendMsgLog.setError_log("�߳��жϴ���");
				}
				sendMsgLogs.add(sendMsgLog);
			}
			fsdxDao.insertSendMsgLog(sendMsgLogs);
		}

		return sendMsgLogs;
	}

	public FsdxDAO getFsdxDao() {
		return fsdxDao;
	}

	public void setFsdxDao(FsdxDAO fsdxDao) {
		this.fsdxDao = fsdxDao;
	}

}
