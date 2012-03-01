package com.sw.cms.servlet;

import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.sw.cms.service.SysMsgService;

/**
 * ��ѯ�û�δ����Ϣ�����������application��<BR>
 * ǰ̨��ѯ�û�δ����Ϣʱ��ֱ�Ӳ�ѯ���ݿ⣬�������ݿ�IO<BR>
 * �����࣬��������ʱ����ֱ�Ӳ��ù��캯����Ӧ����getInstace(ServletContext application)��������<BR>
 * ���þ�����<BR>
 * 		Thread th = new Thread(UserMsgLoadThread.getInstace(application));<BR>
 *		th.start();
 * @author liyt
 *
 */
public class UserMsgLoadThread implements Runnable {
	
	private ServletContext application = null;
	private static UserMsgLoadThread userMsgLoadThread = null;
	private SysMsgService sysMsgService = null;
	
	
	private UserMsgLoadThread(){
	}
	
	private UserMsgLoadThread(ServletContext application){
		this.application = application;
	}
	
	public static UserMsgLoadThread getInstace(ServletContext application){
		if(userMsgLoadThread == null){
			userMsgLoadThread = new UserMsgLoadThread(application);
		}
		return userMsgLoadThread;
	}

	public void run() {	
		
		if(sysMsgService == null){
			ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(application);
			sysMsgService = (SysMsgService)ctx.getBean("sysMsgService");	
		}
		
		do{		
			//ȥ�����û���δ����Ϣ��
			Map map = sysMsgService.getUserNoReadMsg();
			
			//��δ����Ϣ������application��
			application.setAttribute("userNoReadMsgMap", map);
            try {
            	//�߳���ͣʮ��
                Thread.sleep(10*1000);
            } catch(InterruptedException ex)  {
            	ex.printStackTrace();
            }
		}while(true);
	}

}
