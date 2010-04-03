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
import com.sw.cms.dao.DataDisParseDAO;

public class BaseAction extends ActionSupport {
	
	protected transient final Log log = LogFactory.getLog(getClass());
	protected String msg = "";
	
	private DataDisParseDAO dataDisParseDao;
	
	/**
	 * �����û�ID�����û�����
	 * @param user_id
	 * @return
	 */
	public String getUserRealName(String user_id){
		if(user_id == null || user_id.equals("")){
			return "";
		}
		return dataDisParseDao.getRealNameById(user_id);
	}
	
	/**
	 * ���ݿͻ�IDȡ�ͻ�����
	 * @param client_id
	 * @return
	 */
	public String getClientName(String client_id){
		if(client_id == null || client_id.equals("")){
			return "";
		}
		return dataDisParseDao.getClientNameById(client_id);
	}
	
	
	/**
	 * ���ݲֿ�IDȡ�ֿ�����
	 * @param id
	 * @return
	 */
	public String getStoreName(String id){
		if(id == null || id.equals("")){
			return "";
		}
		return dataDisParseDao.getStoreNameById(id);
	}
	
	
	/**
	 * ������Ʒ�����ȡ��Ʒ�������
	 * @param kind_id
	 * @return
	 */
	public String getProductKindName(String kind_id){
		if(kind_id == null || kind_id.equals("")){
			return "";
		}
		return dataDisParseDao.getKindNameByKindId(kind_id);
	}
	
	
	/**
	 * ���ݲ��ű��ȡ��������
	 * @param dept_id
	 * @return
	 */
	public String getDeptName(String dept_id){
		if(dept_id == null || dept_id.equals("")){
			return "";
		}
		return dataDisParseDao.getDeptNameById(dept_id);
	}
	
	
	/**
	 * �����˻����ȡ�˻�����
	 * @param id
	 * @return
	 */
	public String getAccountName(String id){
		if(id == null || id.equals("")){
			return "";
		}
		return dataDisParseDao.getAccountNameById(id);
	}
	
	
	/**
	 * ���ݷ������ͱ��ȡ������������
	 * @param id
	 * @return
	 */
	public String getFyTypeName(String id){
		if(id == null || id.equals("")){
			return "";
		}
		return dataDisParseDao.getFyTypeNameById(id);
	}
	

	public DataDisParseDAO getDataDisParseDao() {
		return dataDisParseDao;
	}
	public void setDataDisParseDao(DataDisParseDAO dataDisParseDao) {
		this.dataDisParseDao = dataDisParseDao;
	}

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
