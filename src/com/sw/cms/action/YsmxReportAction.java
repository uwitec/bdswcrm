package com.sw.cms.action;

import java.util.ArrayList;
import java.util.List;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.service.ClientWlDzdService;
import com.sw.cms.service.ClientWlStatService;
import com.sw.cms.service.ClientsService;
import com.sw.cms.service.UserService;
import com.sw.cms.service.YsmxReportService;

public class YsmxReportAction extends BaseAction {
	
	private YsmxReportService ysmxReportService;
	private UserService userService;
	private ClientsService clientsService;
	private ClientWlDzdService clientWlDzdService;
	private ClientWlStatService clientWlStatService;
	
	private List user_list = new ArrayList();
	private List ckdList = new ArrayList();
	private List clientList = new ArrayList();
	
	private String xsd_id = "";
	private String client_name = "";
	private String khjl = "";
	
	
	public String showCondition(){
		user_list = userService.getAllEmployeeList();
		clientList=clientsService.getClientList("");
		return "success";
	}
	
	public String getResult(){
		clientList = clientsService.getClietsById(client_name,khjl);
		return "success";
	}
	
	public String getMxResult(){
		return "success";
	}


	public YsmxReportService getYsmxReportService() {
		return ysmxReportService;
	}

	public void setYsmxReportService(YsmxReportService ysmxReportService) {
		this.ysmxReportService = ysmxReportService;
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

	public String getXsd_id() {
		return xsd_id;
	}

	public void setXsd_id(String xsd_id) {
		this.xsd_id = xsd_id;
	}

	public void setCkdList(List ckdList) {
		this.ckdList = ckdList;
	}

	public List getCkdList() {
		return ckdList;
	}

	public List getClientList() {
		return clientList;
	}

	public void setClientList(List clientList) {
		this.clientList = clientList;
	}

	public ClientsService getClientsService() {
		return clientsService;
	}

	public void setClientsService(ClientsService clientsService) {
		this.clientsService = clientsService;
	}

	public String getClient_name() {
		return client_name;
	}

	public void setClient_name(String client_name) {
		this.client_name = client_name;
	}

	public ClientWlDzdService getClientWlDzdService() {
		return clientWlDzdService;
	}

	public void setClientWlDzdService(ClientWlDzdService clientWlDzdService) {
		this.clientWlDzdService = clientWlDzdService;
	}

	public ClientWlStatService getClientWlStatService() {
		return clientWlStatService;
	}

	public void setClientWlStatService(ClientWlStatService clientWlStatService) {
		this.clientWlStatService = clientWlStatService;
	}

	public String getKhjl() {
		return khjl;
	}

	public void setKhjl(String khjl) {
		this.khjl = khjl;
	}

}
