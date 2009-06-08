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
 * 短信发送接收逻辑类
 * 
 * @author sw
 * 
 */
public class SMSEngine {
	private Logger log = Logger.getLogger(this.getClass());
	
	private FsdxDAO fsdxDao;

	private static SMSEngine engine = null; // 采用单例模式设置

	private Service service = null; // 短信的服务类

	private SMSEngine() {

	}

	public static SMSEngine getSMSEngine() {
		if (engine == null) {
			engine = new SMSEngine();
		}
		return engine;
	}

	/**
	 * 初始化服务
	 * 
	 */
	public void initService() throws Exception{
		service = new Service();// NEW 一个服务
		// NEW 一个GSM调制解调器 ↓(网关) ↓(端口) ↓(波特率)
		
		//windows下处理
		//SerialModemGateway gateway = new SerialModemGateway("modem.com1","COM1", 9600, "Nokia", "6310i");
		
		//linux下处理
		SerialModemGateway gateway = new SerialModemGateway("SWSMS","/dev/ttyS0", 9600, "wavecom", "M1306B");
		
		gateway.setInbound(true); // 是否可以接受短信
		gateway.setOutbound(true);// 是否可以发送短信
		gateway.setSimPin("0000");// 设置SIM卡的个人识别号码

		service.addGateway(gateway);// 添加一个GSM调制解调器
		
		try{
			service.startService(); // 开始服务
		}catch(Exception e){
			log.error("短信猫启动失败,失败原因:" + e.getMessage());
			service.stopService();
		}
	}

	/**
	 * 发送信息
	 * 
	 * @param receptMobiles
	 *            手机号组
	 * @param msg
	 *            消息体
	 */
	public List sendMsg(String sendLinkman, String msg, String user_id) {

		List<SendMsgLog> sendMsgLogs = new ArrayList<SendMsgLog>(); // 日志列表
		List sendList = fsdxDao.getLinkmanById(sendLinkman);

		if (sendList != null && sendList.size() > 0) {

			for (int i = 0; i < sendList.size(); i++) {
				Map map = (Map) sendList.get(i);
				OutboundMessage obmsg = new OutboundMessage(map.get("yddh")
						.toString(), msg);
				obmsg.setEncoding(MessageEncodings.ENCUCS2);// 设置字符集
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
					sendMsgLog.setError_log("传送中超时");
				} catch (GatewayException gatewayException) {
					sendMsgLog.setError_log("网关连接错误");
				} catch (IOException ioException) {
					sendMsgLog.setError_log("传送错误");
				} catch (InterruptedException interruptedException) {
					sendMsgLog.setError_log("线程中断错误");
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
