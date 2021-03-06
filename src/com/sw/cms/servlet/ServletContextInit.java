package com.sw.cms.servlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.sw.cms.util.StaticParamDo;

public class ServletContextInit extends HttpServlet {

	private Logger log = Logger.getLogger(this.getClass());
	
	public void init() throws ServletException {
		try{
			ServletContext servletContext = getServletContext();
			ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
			StaticParamDo.setCtx(ctx);
			
			//用户未读消息轮转线程
			Thread thUserNoMsg = new Thread(UserMsgLoadThread.getInstace(servletContext));
			thUserNoMsg.start();
			 
			
			//生成期初调试
			//InitParamDAO dao = (InitParamDAO)ctx.getBean("initParamDao");			
			//生成客户往来期初
			//dao.genCleintWlqc("2009-12-20","2009-12-19"); 
			//生成账户期初
			//dao.genAccountQc("2009-12-16","2009-12-15"); 
			
			//批量生成客户期初值
			//dao.updateBatchGenClientWlQc("2011-03-03", "2011-03-03");
			
			//Thread.sleep(100*1000);
			
			//调整期初等的操作放到线程中做2010-01-05
//			Thread th = new Thread(new GenQcThread());
//			th.start();
			
		}catch(Exception e){
			log.error("初始化spring context失败，失败原因" + e.getMessage());
		}
		
//		try{
//			SMSEngine eng=SMSEngine.getSMSEngine();
//			eng.initService();
//		}catch(Exception e){
//			log.error("短信服务注册失败，失败原因：" + e.getMessage());
//		}
	}
	
	public void destroy() {
	}

}
