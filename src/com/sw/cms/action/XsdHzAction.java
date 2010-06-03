package com.sw.cms.action;

import java.util.ArrayList;
import java.util.List;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.service.ClientsService;
import com.sw.cms.service.ProductKindService;
import com.sw.cms.service.UserService;
import com.sw.cms.service.XsdHzService;

public class XsdHzAction extends BaseAction {
	
	private XsdHzService xsdHzService;
	private UserService userService;
	private ClientsService clientsService;
	private ProductKindService productKindService;
	private List userList = new ArrayList();
	private List clientsList= new ArrayList();
	
	private List productKindList = new ArrayList();
	
	
	public String showCondition(){
		userList = userService.getAllEmployeeList();
		clientsList=clientsService.getClientList("");
		productKindList = productKindService.getAllProductKindList();
		return "success";
	}
	
	public String getResult(){
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

	public XsdHzService getXsdHzService() {
		return xsdHzService;
	}

	public void setXsdHzService(XsdHzService xsdHzService) {
		this.xsdHzService = xsdHzService;
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
	
	public ProductKindService getProductKindService() {
		return productKindService;
	}
	public void setProductKindService(ProductKindService productKindService) {
		this.productKindService = productKindService;
	}
	
	
	public List getProductKindList() {
		return productKindList;
	}
	public void setProductKindList(List productKindList) {
		this.productKindList = productKindList;
	}
}
