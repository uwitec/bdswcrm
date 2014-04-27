package com.sw.cms.action.base;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.xwork.ActionSupport;
import com.sw.cms.dao.DataDisParseDAO;

/**
 * 所有Action类继承该类<BR>
 * 集成了日志处理,数据字典解析等<BR>
 * @author liyt
 *
 */
public class BaseAction extends ActionSupport {
	
	protected transient final Log log = LogFactory.getLog(getClass());
	protected String msg = "";
	
	private DataDisParseDAO dataDisParseDao;
	
	/**
	 * 根据用户ID返回用户姓名
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
	 * 根据用户ID返回用户姓名
	 * @param user_id
	 * @return
	 */
	public String gerDeptNameByUserId(String user_id){
		if(user_id == null || user_id.equals("")){
			return "";
		}
		return dataDisParseDao.gerDeptNameByUserId(user_id);
	}
	
	/**
	 * 根据客户ID取客户名称
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
	 * 根据仓库ID取仓库名称
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
	 * 根据商品类别编号取商品类别名称
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
	 * 根据部门编号取部门名称
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
	 * 根据账户编号取账户名称
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
	 * 根据费用类型编号取费用类型名称
	 * @param id
	 * @return
	 */
	public String getFyTypeName(String id){
		if(id == null || id.equals("")){
			return "";
		}
		return dataDisParseDao.getFyTypeNameById(id);
	}
	
	/**
	 * 消息处理
	 * @param msg
	 */
    protected void saveMessage(String msg) {
        List messages = (List) getRequest().getSession().getAttribute("messages");
        if (messages == null) {
            messages = new ArrayList();
        }
        messages.add(msg);
        getRequest().getSession().setAttribute("messages", messages);
	}	
    
    /**
     * JSONObject输出
     * @param jsonObj
     */
    public void writeJsonToResponse(JSONObject jsonObj){
    	PrintWriter out = null;
    	getResponse().setContentType("text/html;charset=utf-8");
    	try{
    		out = getResponse().getWriter();
    		out.print(jsonObj);
    		out.flush();
    	}catch(Exception e){
    		e.printStackTrace();
    	}finally{
    		out.close();
    	}
    }
    
    /**
     * JSONArray输出
     */
    public void writeJsonToResponse(JSONArray jsonArray){
    	PrintWriter out = null;
    	getResponse().setContentType("text/html;charset=utf-8");
    	try{
    		out = getResponse().getWriter();
    		out.print(jsonArray);
    		out.flush();
    	}catch(Exception e){
    		e.printStackTrace();
    	}finally{
    		out.close();
    	}
    }
    
    /**
     * 字符输出
     * @param vl
     */
    public void writeStringToResponse(String vl){
    	PrintWriter out = null;
    	getResponse().setContentType("text/html;charset=utf-8");
    	try{
    		out = getResponse().getWriter();
    		out.print(vl);
    		out.flush();
    	}catch(Exception e){
    		e.printStackTrace();
    	}finally{
    		out.close();
    	}
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
    
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}  

}
