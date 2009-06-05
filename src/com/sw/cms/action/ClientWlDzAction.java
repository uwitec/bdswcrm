package com.sw.cms.action;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.service.ClientWlDzdService;

public class ClientWlDzAction extends BaseAction {
	
	private ClientWlDzdService clientWlDzdService;
	
	/**
	 * 打开条件列表页面
	 * @return
	 */
	public String showCondition(){
		return "success";
	}
	
	/**
	 * 汇总页面
	 * @return
	 */
	public String getResult(){
		return "success";
	}

	public ClientWlDzdService getClientWlDzdService() {
		return clientWlDzdService;
	}

	public void setClientWlDzdService(ClientWlDzdService clientWlDzdService) {
		this.clientWlDzdService = clientWlDzdService;
	}

}
