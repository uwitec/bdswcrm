package com.sw.cms.action;

import java.util.ArrayList;
import java.util.List;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.service.AccountsService;
import com.sw.cms.service.UserService;
import com.sw.cms.service.XsskHzReportService;

public class XsskHzReportAction extends BaseAction {
	
	private XsskHzReportService xsskHzReportService;
	private AccountsService accountsService;
	private UserService userService;
	
	private List userList = new ArrayList();
	private List accountList = new ArrayList();
	
	
	public String showCondition(){
		userList = userService.getAllEmployeeList();
		accountList = accountsService.getAccountList();
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
	public XsskHzReportService getXsskHzReportService() {
		return xsskHzReportService;
	}
	public void setXsskHzReportService(XsskHzReportService xsskHzReportService) {
		this.xsskHzReportService = xsskHzReportService;
	}

}
