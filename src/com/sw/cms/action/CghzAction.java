package com.sw.cms.action;

import java.util.ArrayList;
import java.util.List;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.service.CghzService;
import com.sw.cms.service.ClientsService;
import com.sw.cms.service.ProductKindService;
import com.sw.cms.service.UserService;

public class CghzAction extends BaseAction {
	
	private CghzService cghzService;
	private UserService userService;
	private ProductKindService productKindService;
	private ClientsService clientsService;
	
	private List user_list = new ArrayList();
	private List productKindList = new ArrayList();
	private List clientsList= new ArrayList();
	
	public String showCondition(){
		user_list = userService.getAllEmployeeList();		
		productKindList = productKindService.getAllProductKindList();
		clientsList=clientsService.getClientList("");
		return "success";
	}
	
	public String getResult(){
		return "success";
	}
	
	public CghzService getCghzService() {
		return cghzService;
	}
	public void setCghzService(CghzService cghzService) {
		this.cghzService = cghzService;
	}
	public ProductKindService getProductKindService() {
		return productKindService;
	}
	public void setProductKindService(ProductKindService productKindService) {
		this.productKindService = productKindService;
	}
	public UserService getUserService() {
		return userService;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	public List getProductKindList() {
		return productKindList;
	}
	public void setProductKindList(List productKindList) {
		this.productKindList = productKindList;
	}
	public List getUser_list() {
		return user_list;
	}
	public void setUser_list(List user_list) {
		this.user_list = user_list;
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
	
}
