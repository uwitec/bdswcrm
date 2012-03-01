package com.sw.cms.servlet;

import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.sw.cms.service.SysMsgService;

/**
 * 轮询用户未读消息，将结果存入application中<BR>
 * 前台查询用户未读消息时不直接查询数据库，减少数据库IO<BR>
 * 单例类，创建对象时不能直接采用构造函数，应调用getInstace(ServletContext application)创建对象<BR>
 * 调用举例：<BR>
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
			//去所有用户的未读消息集
			Map map = sysMsgService.getUserNoReadMsg();
			
			//将未读消息集存入application中
			application.setAttribute("userNoReadMsgMap", map);
            try {
            	//线程暂停十秒
                Thread.sleep(10*1000);
            } catch(InterruptedException ex)  {
            	ex.printStackTrace();
            }
		}while(true);
	}

}
