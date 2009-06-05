package com.sw.cms.action;

import java.util.ArrayList;
import java.util.List;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.service.HpflxsHzService;
import com.sw.cms.service.UserService;

/**
 * 货品分类销售汇总
 * @author liyt
 *
 */
public class HpflxsHzAction extends BaseAction {
	
	private HpflxsHzService hpflxsHzService;
	private UserService userService;
	
	private List resultList = new ArrayList();
	private List user_list = new ArrayList();
	
	private String start_date = "";
	private String end_date = "";
	private int dj = 1;
	private String client_name = "";
	private String xsry = "";
	private String product_kind = "";
	
	public String getProduct_kind() {
		return product_kind;
	}


	public void setProduct_kind(String product_kind) {
		this.product_kind = product_kind;
	}


	public String getClient_name() {
		return client_name;
	}


	public void setClient_name(String client_name) {
		this.client_name = client_name;
	}


	public String getXsry() {
		return xsry;
	}


	public void setXsry(String xsry) {
		this.xsry = xsry;
	}


	public int getDj() {
		return dj;
	}


	public void setDj(int dj) {
		this.dj = dj;
	}


	public String getEnd_date() {
		return end_date;
	}


	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}


	public String getStart_date() {
		return start_date;
	}


	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}


	public List getResultList() {
		return resultList;
	}


	public void setResultList(List resultList) {
		this.resultList = resultList;
	}


	/**
	 * 显示条件
	 * @return
	 * @throws Exception
	 */
	public String showCondition()throws Exception{
		user_list = userService.getAllEmployeeList();
		return SUCCESS;
	}
	
	
	/**
	 * 统计结果
	 * @return
	 * @throws Exception
	 */
	public String getResults() throws Exception{
		try{
			resultList = hpflxsHzService.getHzResults(start_date,end_date,xsry,client_name,dj);
			return SUCCESS;
		}catch(Exception e){
			log.error("货品销售分类汇总出错，错误原因：" + e.getMessage());
			return ERROR;
		}
	}
	
	
	/**
	 * 明细信息
	 * @return
	 * @throws Exception
	 */
	public String getMxResults() throws Exception{
		try{
			resultList = hpflxsHzService.getMxResults(product_kind, start_date, end_date, client_name, xsry);
			return SUCCESS;
		}catch(Exception e){
			log.error("货品销售分类汇总明细信息出错，错误原因：" + e.getMessage());
			return ERROR;
		}
	}


	public HpflxsHzService getHpflxsHzService() {
		return hpflxsHzService;
	}


	public void setHpflxsHzService(HpflxsHzService hpflxsHzService) {
		this.hpflxsHzService = hpflxsHzService;
	}


	public List getUser_list() {
		return user_list;
	}


	public void setUser_list(List user_list) {
		this.user_list = user_list;
	}


	public UserService getUserService() {
		return userService;
	}


	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	

}
