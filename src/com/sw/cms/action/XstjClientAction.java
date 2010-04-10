package com.sw.cms.action;

import java.util.ArrayList;
import java.util.List;

import com.sw.cms.service.ClientsService;
import com.sw.cms.service.UserService;
import com.sw.cms.service.XstjClientService;

public class XstjClientAction {
	
	private XstjClientService xstjClientService;
	private UserService userService;
	private ClientsService clientsService;
	
	private List userList = new ArrayList();
	private List clientList = new ArrayList();
	private List statResult = new ArrayList();
	 
	
	private String client_name = "";
	private String start_date = "";
	private String end_date = "";
	private String xsry_id = "";
	private String dj_id = "";
	
	
	public String showCondition(){
		userList = userService.getAllEmployeeList();
		clientList=clientsService.getClientList("");
		return "success";
	}
	
	public String getResult(){
		statResult = xstjClientService.getXstjClientResult(start_date, end_date, client_name, xsry_id, dj_id);
		return "success";
	}
	
	public String getResultMx(){
		clientList = clientsService.getClientList(client_name);
		return "success";
	}	
	
	public String getResultLsMx(){
		return "success";
	}

	public List getUserList() {
		return userList;
	}

	public void setUserList(List userList) {
		this.userList = userList;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public XstjClientService getXstjClientService() {
		return xstjClientService;
	}

	public void setXstjClientService(XstjClientService xstjClientService) {
		this.xstjClientService = xstjClientService;
	}

	public ClientsService getClientsService() {
		return clientsService;
	}

	public void setClientsService(ClientsService clientsService) {
		this.clientsService = clientsService;
	}

	public List getClientList() {
		return clientList;
	}

	public void setClientList(List clientList) {
		this.clientList = clientList;
	}

	public String getClient_name() {
		return client_name;
	}

	public void setClient_name(String client_name) {
		this.client_name = client_name;
	}

	public List getStatResult() {
		return statResult;
	}

	public void setStatResult(List statResult) {
		this.statResult = statResult;
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

	public String getXsry_id() {
		return xsry_id;
	}

	public void setXsry_id(String xsryId) {
		xsry_id = xsryId;
	}

	public String getDj_id() {
		return dj_id;
	}

	public void setDj_id(String djId) {
		dj_id = djId;
	}

}
