package com.sw.cms.action;

import java.util.ArrayList;
import java.util.List;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.service.CgfptjService;
import com.sw.cms.service.ClientsService;

public class CgfptjAction extends BaseAction {
	
	private CgfptjService cgfptjService;
	private ClientsService clientsService;
	private List clientsList= new ArrayList();
	
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

	/**
	 * 打开条件列表页面
	 * @return
	 */
	public String showCondition(){
		clientsList=clientsService.getClientList("");
		return "success";
	}
	
	/**
	 * 汇总页面
	 * @return
	 */
	public String getResult(){
		return "success";
	}

	public CgfptjService getCgfptjService() {
		return cgfptjService;
	}

	public void setCgfptjService(CgfptjService cgfptjService) {
		this.cgfptjService = cgfptjService;
	}

}
