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
 * ���������û�application<onlineUserList>
 * @author liyt
 *
 */

public class OnlineSessionAndApplication implements HttpSessionBindingListener {
	
	private UserService userService;

	private Log log = LogFactory.getLog(getClass());
	
	// ���϶��󣬱������е�session ����
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
		// ��һ��ʹ��ǰ����Ҫ��ʼ��
		if (onlineUserList == null) {
			onlineUserList = new ArrayList();
			application.setAttribute("onlineUserList", onlineUserList);
		}

		//����½�û�����applicateion��
		onlineUserList.add(this.loginInfo);
		
		log.info(loginInfo.getReal_name() + "��½ϵͳ��ϵͳ��ǰ�û���:" + onlineUserList.size());
	}

	public synchronized void valueUnbound(HttpSessionBindingEvent e) {
		
		HttpSession session = e.getSession();
		ht.remove(session.getId());
		
		ServletContext application = session.getServletContext();

		// �������б���ɾ���û���
		List onlineUserList = (List) application.getAttribute("onlineUserList");
		onlineUserList.remove(this.loginInfo);
		
		ApplicationContext ctx = StaticParamDo.getCtx();
		UserService userService = (UserService)ctx.getBean("userService");
		userService.updateUserLastLogout(this.loginInfo.getUser_id(), new Date());
		
		log.info(loginInfo.getReal_name() + "ע����½��ϵͳ��ǰ�û���:" + onlineUserList.size());
	}
	
	
	// ����session id����ָ����session����
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
