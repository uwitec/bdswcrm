package com.sw.cms.action;

import java.util.ArrayList;
import java.util.List;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.service.ClientsService;
import com.sw.cms.service.DeptXshzService;

/**
 * 部门销售汇总
 * @author liyt
 *
 */
public class DeptXshzAction extends BaseAction {
	
	private DeptXshzService deptXshzService;
	private ClientsService clientsService;
	
	private List resultList = new ArrayList();
	private List clientsList= new ArrayList();
	
	private String start_date = "";
	private String end_date = "";
	private String client_name = "";
	private String dept = "";
	private int dj = 1;
	private String xsry = "";
	String product_kind = "";
	String product_name = "";


	/**
	 * 统计条件
	 * @return
	 */
	public String showCondition(){
		clientsList=clientsService.getClientList("");
		return SUCCESS;
	}
	
	
	/**
	 * 统计结果
	 * @return
	 */
	public String getResults(){
		try{
			resultList = deptXshzService.getResults(start_date, end_date, client_name, dj, product_kind, product_name);
			return SUCCESS;
		}catch(Exception e){
			log.error("部门销售汇总出错，错误原因：" + e.getMessage());
			return ERROR;
		}
	}
	
	
	/**
	 * 统计结果(销售人员汇总)
	 * @return
	 */
	public String getMxResults(){
		try{
			resultList = deptXshzService.getMxResults(dept, start_date, end_date, client_name, product_kind, product_name);
			return SUCCESS;
		}catch(Exception e){
			log.error("部门销售汇总--销售人员汇总出错，错误原因：" + e.getMessage());
			return ERROR;
		}
	}
	
	
	/**
	 * 统计结果（产品汇总）
	 * @return
	 */
	public String getProductMxResults(){
		try{
			resultList = deptXshzService.getProductMxResults(xsry, start_date, end_date, client_name, product_kind, product_name);
			return SUCCESS;
		}catch(Exception e){
			log.error("部门销售汇总--产品汇总出错，错误原因：" + e.getMessage());
			return ERROR;
		}
	}
	
	
	public String getClient_name() {
		return client_name;
	}
	public void setClient_name(String client_name) {
		this.client_name = client_name;
	}
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	public DeptXshzService getDeptXshzService() {
		return deptXshzService;
	}
	public void setDeptXshzService(DeptXshzService deptXshzService) {
		this.deptXshzService = deptXshzService;
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
	public List getResultList() {
		return resultList;
	}
	public void setResultList(List resultList) {
		this.resultList = resultList;
	}
	public String getStart_date() {
		return start_date;
	}
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}
	public String getXsry() {
		return xsry;
	}
	public void setXsry(String xsry) {
		this.xsry = xsry;
	}


	public List getClientsList() {
		return clientsList;
	}


	public void setClientsList(List clientsList) {
		this.clientsList = clientsList;
	}


	public ClientsService getClientsService() {
		return clientsService;
	}


	public void setClientsService(ClientsService clientsService) {
		this.clientsService = clientsService;
	}


	public String getProduct_kind() {
		return product_kind;
	}


	public void setProduct_kind(String product_kind) {
		this.product_kind = product_kind;
	}


	public String getProduct_name() {
		return product_name;
	}


	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

}
