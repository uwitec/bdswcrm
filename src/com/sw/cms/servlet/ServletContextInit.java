package com.sw.cms.servlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.sw.cms.dao.InitParamDAO;
import com.sw.cms.util.StaticParamDo;

public class ServletContextInit extends HttpServlet {

	private Logger log = Logger.getLogger(this.getClass());
	
	public void init() throws ServletException {
		try{
			ServletContext servletContext = getServletContext();
			ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
			StaticParamDo.setCtx(ctx);
			 
			
//			//�����ڳ�����
//			InitParamDAO dao = (InitParamDAO)ctx.getBean("initParamDao");			
//			//���ɿͻ������ڳ�
//			dao.genCleintWlqc("2009-09-12","2009-09-11"); 
//			//�����˻��ڳ�
//			dao.genAccountQc("2009-09-12","2009-09-11"); 
//			
//			System.out.println("ddddddddddddddddddd");
//			Thread.sleep(100*1000);
			
		}catch(Exception e){
			log.error("��ʼ��spring contextʧ�ܣ�ʧ��ԭ��" + e.getMessage());
		}
		
//		try{
//			SMSEngine eng=SMSEngine.getSMSEngine();
//			eng.initService();
//		}catch(Exception e){
//			log.error("���ŷ���ע��ʧ�ܣ�ʧ��ԭ��" + e.getMessage());
//		}
	}
	
	public void destroy() {
	}

}
