package com.sw.cms.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.service.UserService;
import com.sw.cms.service.YshzJsrService;

public class YshzJsrAction extends BaseAction {
	
	private YshzJsrService yshzJsrService;
	private UserService userService;
	
	private List userList = new ArrayList();
	private Map qcMap = new HashMap();
	private Map fsMap = new HashMap();
	private List xsdList = new ArrayList();
	
	private Map ysjeMap = new HashMap();
	private Map cqjeMap = new HashMap();
	private Map cqtsMap = new HashMap();
	
	private String start_date = "";
	private String end_date = "";
	private String client_name = "";
	private String jsr = "";
	
	/**
	 * 打开查询条件页面
	 * @return
	 */
	public String showCondition(){
		try{
			
			return SUCCESS;
		}catch(Exception e){
			log.error("打开业务员应收汇总条件页面错误," + e.getMessage());
			e.printStackTrace();
			return ERROR;
		}
	}
	
	
	/**
	 * 业务员应收汇总结果
	 * @return
	 */
	public String getResult(){
		try{
			String con = "";
			if(!jsr.equals("")){
				con = " and user_id='" + jsr + "'";
			}
			userList = userService.getYwryListByCon(con);
			qcMap = yshzJsrService.getYshzJsrQc(start_date, client_name, jsr);
			fsMap = yshzJsrService.getYshzFlow(start_date, end_date, client_name, jsr);
			
			return SUCCESS;
		}catch(Exception e){
			log.error("业务员应收汇总出错," + e.getMessage());
			e.printStackTrace();
			return ERROR;
		}
	}
	
	
	/**
	 * 业务员应收汇总--明细
	 * @return
	 */
	public String getResultMx(){
		try{
			String con = "";
			if(!jsr.equals("")){
				con = " and user_id='" + jsr + "'";
			}
			qcMap = yshzJsrService.getYshzJsrQc(start_date, client_name, jsr);
			xsdList = yshzJsrService.getXsdList(start_date, end_date, client_name, jsr);	
			return SUCCESS;
		}catch(Exception e){
			log.error("业务员应收汇总出错," + e.getMessage());
			e.printStackTrace();
			return ERROR;
		}
	}
	
	
	/**
	 * 业务员应收汇总表
	 * @return
	 */
	public String getHzResult(){
		try{
			userList = userService.getYwryListByCon("");
			
			ysjeMap = yshzJsrService.getYwyYsjeMap();
			cqjeMap = yshzJsrService.getYwyCqjeMap();
			cqtsMap = yshzJsrService.getYwyCqts();
			
			return SUCCESS;
		}catch(Exception e){
			log.error("业务员应收汇总出错," + e.getMessage());
			e.printStackTrace();
			return ERROR;
		}
	}

	public YshzJsrService getYshzJsrService() {
		return yshzJsrService;
	}

	public void setYshzJsrService(YshzJsrService yshzJsrService) {
		this.yshzJsrService = yshzJsrService;
	}


	public List getUserList() {
		return userList;
	}


	public void setUserList(List userList) {
		this.userList = userList;
	}


	public Map getQcMap() {
		return qcMap;
	}


	public void setQcMap(Map qcMap) {
		this.qcMap = qcMap;
	}


	public Map getFsMap() {
		return fsMap;
	}


	public void setFsMap(Map fsMap) {
		this.fsMap = fsMap;
	}


	public String getStart_date() {
		return start_date;
	}


	public void setStart_date(String startDate) {
		start_date = startDate;
	}


	public String getEnd_date() {
		return end_date;
	}


	public void setEnd_date(String endDate) {
		end_date = endDate;
	}


	public String getClient_name() {
		return client_name;
	}


	public void setClient_name(String clientName) {
		client_name = clientName;
	}


	public String getJsr() {
		return jsr;
	}


	public void setJsr(String jsr) {
		this.jsr = jsr;
	}


	public UserService getUserService() {
		return userService;
	}


	public void setUserService(UserService userService) {
		this.userService = userService;
	}


	public List getXsdList() {
		return xsdList;
	}


	public void setXsdList(List xsdList) {
		this.xsdList = xsdList;
	}


	public Map getYsjeMap() {
		return ysjeMap;
	}


	public void setYsjeMap(Map ysjeMap) {
		this.ysjeMap = ysjeMap;
	}


	public Map getCqjeMap() {
		return cqjeMap;
	}


	public void setCqjeMap(Map cqjeMap) {
		this.cqjeMap = cqjeMap;
	}


	public Map getCqtsMap() {
		return cqtsMap;
	}


	public void setCqtsMap(Map cqtsMap) {
		this.cqtsMap = cqtsMap;
	}

}
