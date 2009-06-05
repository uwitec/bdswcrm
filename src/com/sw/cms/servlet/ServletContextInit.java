package com.sw.cms.servlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.sw.cms.sendsms.SMSEngine;
import com.sw.cms.util.StaticParamDo;

public class ServletContextInit extends HttpServlet {

	private Logger log = Logger.getLogger(this.getClass());
	
	public void init() throws ServletException {
		ServletContext servletContext = getServletContext();
		ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
		StaticParamDo.setCtx(ctx);
		log.info("初始化spring context 成功！");
		
//		SMSEngine eng=SMSEngine.getSMSEngine();
//		eng.initService();
//		log.info("短信服务注册成功！");
	}
	
	public void destroy() {
	}

}
