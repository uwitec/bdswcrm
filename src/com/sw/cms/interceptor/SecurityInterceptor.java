package com.sw.cms.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.interceptor.Interceptor;
import com.sw.cms.model.LoginInfo;

public class SecurityInterceptor implements Interceptor {

	private static final long serialVersionUID = 3826133233201703797L;

	private Logger log = Logger.getLogger(this.getClass());
	public void destroy() {
		log.info("用户权限及安全性验证拦截器结束！");
	}

	public void init() {
		log.info("用户权限及安全性验证初始化！");
	}

	public String intercept(ActionInvocation actionInvocation) throws Exception {
		
		//当前请求的action名称
		String actionName = actionInvocation.getInvocationContext().getName();
		
		HttpServletRequest request = ServletActionContext.getRequest();   
        HttpSession session = request.getSession(); 
        
        
        //验证用户是否登录，只有在当前actionName不等于login时才判断session
        if(!actionName.equals("login") && !actionName.equals("toLogin")){
	        if(session.getAttribute("LOGINUSER") == null){
	        	log.info("用户尚未登录或登录已失效，请重新登录！");
	        	request.setAttribute("LOGINERROR", "用户尚未登录或登录已失效，请重新登录！");
	        	
	        	return "loginExpire";
	        }
	        LoginInfo info = (LoginInfo)session.getAttribute("LOGINUSER");
	        log.info("当前用户---" + info.getReal_name() +"---访问页面---" + actionName + ".html");
        }

		return actionInvocation.invoke();
	}

}
