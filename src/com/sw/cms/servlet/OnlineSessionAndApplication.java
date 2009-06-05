package com.sw.cms.servlet;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;

import com.sw.cms.model.LoginInfo;
import com.sw.cms.service.UserService;
import com.sw.cms.util.StaticParamDo;

/**
 * 设置在线用户application<onlineUserList>
 * @author liyt
 *
 */

public class OnlineSessionAndApplication implements HttpSessionBindingListener {
	
	private UserService userService;

	private Log log = LogFactory.getLog(getClass());
	
	// 集合对象，保存所有的session 对象
	private static Hashtable<String,HttpSession> ht = new Hashtable<String,HttpSession>();
	
	private LoginInfo loginInfo;

	public OnlineSessionAndApplication(LoginInfo loginInfo) {
		this.loginInfo = loginInfo;
	}


	public synchronized void valueBound(HttpSessionBindingEvent e) {
		
		HttpSession session = e.getSession();
		ht.put(session.getId(), session);

		ServletContext application = session.getServletContext();

		List onlineUserList = (List) application.getAttribute("onlineUserList");
		// 第一次使用前，需要初始化
		if (onlineUserList == null) {
			onlineUserList = new ArrayList();
			application.setAttribute("onlineUserList", onlineUserList);
		}

		//将登陆用户放入applicateion中
		onlineUserList.add(this.loginInfo);
		
		log.info(loginInfo.getReal_name() + "登陆系统，系统当前用户数:" + onlineUserList.size());
	}

	public synchronized void valueUnbound(HttpSessionBindingEvent e) {
		
		HttpSession session = e.getSession();
		ht.remove(session.getId());
		
		ServletContext application = session.getServletContext();

		// 从在线列表中删除用户名
		List onlineUserList = (List) application.getAttribute("onlineUserList");
		onlineUserList.remove(this.loginInfo);
		
		ApplicationContext ctx = StaticParamDo.getCtx();
		UserService userService = (UserService)ctx.getBean("userService");
		userService.updateUserLastLogout(this.loginInfo.getUser_id(), new Date());
		
		log.info(loginInfo.getReal_name() + "注销登陆，系统当前用户数:" + onlineUserList.size());
	}
	
	
	// 依据session id返回指定的session对象
	public static HttpSession getSession(String sessionId) {
		return ht.get(sessionId);
	}


	public UserService getUserService() {
		return userService;
	}


	public void setUserService(UserService userService) {
		this.userService = userService;
	}
}
