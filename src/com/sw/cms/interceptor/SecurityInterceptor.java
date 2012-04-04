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
		log.info("�û�Ȩ�޼���ȫ����֤������������");
	}

	public void init() {
		log.info("�û�Ȩ�޼���ȫ����֤��ʼ����");
	}

	public String intercept(ActionInvocation actionInvocation) throws Exception {
		
		//��ǰ�����action����
		String actionName = actionInvocation.getInvocationContext().getName();
		
		HttpServletRequest request = ServletActionContext.getRequest();   
        HttpSession session = request.getSession(); 
        
        
        //��֤�û��Ƿ��¼��ֻ���ڵ�ǰactionName������loginʱ���ж�session
        if(!actionName.equals("login") && !actionName.equals("toLogin")){
	        if(session.getAttribute("LOGINUSER") == null){
	        	log.info("�û���δ��¼���¼��ʧЧ�������µ�¼��");
	        	request.setAttribute("LOGINERROR", "�û���δ��¼���¼��ʧЧ�������µ�¼��");
	        	
	        	return "loginExpire";
	        }
	        LoginInfo info = (LoginInfo)session.getAttribute("LOGINUSER");
	        log.info("��ǰ�û�---" + info.getReal_name() +"---����ҳ��---" + actionName + ".html");
        }

		return actionInvocation.invoke();
	}

}
