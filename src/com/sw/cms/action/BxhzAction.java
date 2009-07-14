package com.sw.cms.action;

import java.util.ArrayList;
import java.util.List;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.service.BxhzService;
import com.sw.cms.service.ClientsService;
import com.sw.cms.service.UserService;

public class BxhzAction extends BaseAction 
{
	private UserService userService;
	private ClientsService clientsService;
	private  BxhzService bxhzService;
	
	private List user_list = new ArrayList();
	private List clientsList= new ArrayList();
	
	
   public String showCondition()
   {
	   try 
	   {
		  
		   user_list = userService.getAllEmployeeList();	
		   clientsList=clientsService.getClientList("");
		   return "success";
       }
	   catch (Exception e)
	   {
	   log.error("打开货品报修汇总  失败原因"+e.getMessage());
	   return "error";
	   }
   }
   
   public String getResult()
   {
	   try
	   {
		   return "success";  
	   }
	   catch(Exception e)
	   {
		   log.error("货品报修汇总返回结果 失败原因"+e.getMessage());
		   return "error";
	   }
		
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

public BxhzService getBxhzService() {
	return bxhzService;
}

public void setBxhzService(BxhzService bxhzService) {
	this.bxhzService = bxhzService;
}
}
