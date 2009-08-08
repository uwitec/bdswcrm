package com.sw.cms.action.base;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.xwork.ActionSupport;

public class BaseAction extends ActionSupport {
	
	protected transient final Log log = LogFactory.getLog(getClass());
	protected String msg = "";

    protected HttpServletRequest getRequest() {
        return ServletActionContext.getRequest();  
    }

    protected HttpServletResponse getResponse() {
        return ServletActionContext.getResponse();
    }
    
    protected HttpSession getSession() {
    	return getRequest().getSession();
    }
    
    protected ServletContext getApplication(){
    	return ServletActionContext.getServletContext();
    }
    
    protected void saveMessage(String msg) {
        List messages = (List) getRequest().getSession().getAttribute("messages");
        if (messages == null) {
            messages = new ArrayList();
        }
        messages.add(msg);
        getRequest().getSession().setAttribute("messages", messages);
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}  

}
