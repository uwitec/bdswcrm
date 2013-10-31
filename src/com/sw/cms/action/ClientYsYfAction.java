package com.sw.cms.action;

import java.util.ArrayList;
import java.util.List;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.service.ClientsService;
import com.sw.cms.service.SjzdService;
import com.sw.cms.service.UserService;
import com.sw.cms.service.YfHzMxService;

public class ClientYsYfAction extends BaseAction {
	
	private YfHzMxService yfHzMxService;
	private UserService userService;
	private ClientsService clientsService;
	private SjzdService sjzdService;
	private List user_list = new ArrayList();
	private List clientList = new ArrayList();
	
	private String client_name = "";
	private String khjl = "";
	private String client_type ="";
	private String[] wldwlx;
	
	/**
	 * 打开条件列表页面
	 * @return
	 */
	public String showCondition(){
		user_list = userService.getAllEmployeeList();
		clientList=clientsService.getClientList("");
		wldwlx = sjzdService.getSjzdXmxxByZdId("SJZD_WLDWLX");
		return "success";
	}
	
	/**
	 * 应收应付汇总页面
	 * @return
	 */
	public String getResult(){
		clientList = clientsService.getClietsYsyfById(client_name,khjl,client_type);
		return "success";
	}
	
	/**
	 * 应收应付对账单页面
	 * @return
	 */
	public String getMxResult(){
		return "success";
	}
	

	public String getClient_name() {
		return client_name;
	}

	public void setClient_name(String client_name) {
		this.client_name = client_name;
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

	public YfHzMxService getYfHzMxService() {
		return yfHzMxService;
	}

	public void setYfHzMxService(YfHzMxService yfHzMxService) {
		this.yfHzMxService = yfHzMxService;
	}

	
	public String getKhjl() {
		return khjl;
	}

	public void setKhjl(String khjl) {
		this.khjl = khjl;
	}
	
	public String getClient_type() {
		return client_type;
	}

	public void setClient_type(String client_type) {
		this.client_type = client_type;
	}
	
	public String[] getWldwlx() {
		return wldwlx;
	}

	public void setWldwlx(String[] wldwlx) {
		this.wldwlx = wldwlx;
	}
	public SjzdService getSjzdService() {
		return sjzdService;
	}

	public void setSjzdService(SjzdService sjzdService) {
		this.sjzdService = sjzdService;
	}
}
