package com.sw.cms.action;

import java.util.ArrayList;
import java.util.List;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.service.AccountsService;
import com.sw.cms.service.CgfkHzReportService;
import com.sw.cms.service.ClientsService;
import com.sw.cms.service.UserService;

public class CgfkHzReportAction extends BaseAction {
	
	private CgfkHzReportService cgfkHzReportService;
	private AccountsService accountsService;
	private UserService userService;
	private ClientsService clientsService;
	
	private List userList = new ArrayList();
	private List accountList = new ArrayList();
	private List clientList = new ArrayList();
	
	
	public String showCondition(){
		userList = userService.getAllEmployeeList();
		accountList = accountsService.getAccountList("1");
		clientList=clientsService.getClientList("");
		return "success";
	}
	
	public String getResult(){
		return "success";
	}

	public List getAccountList() {
		return accountList;
	}

	public void setAccountList(List accountList) {
		this.accountList = accountList;
	}

	public AccountsService getAccountsService() {
		return accountsService;
	}

	public void setAccountsService(AccountsService accountsService) {
		this.accountsService = accountsService;
	}

	public CgfkHzReportService getCgfkHzReportService() {
		return cgfkHzReportService;
	}

	public void setCgfkHzReportService(CgfkHzReportService cgfkHzReportService) {
		this.cgfkHzReportService = cgfkHzReportService;
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

}
