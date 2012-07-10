package com.sw.cms.action;

import java.util.ArrayList;
import java.util.List;

import com.sw.cms.service.ClientsService;
import com.sw.cms.service.UserService;
import com.sw.cms.service.XstjClientService;
import com.sw.cms.service.SjzdService;

public class XstjClientAction {
	
	private XstjClientService xstjClientService;
	private UserService userService;
	private ClientsService clientsService;
	private SjzdService sjzdService;
	
	private List userList = new ArrayList();
	private List clientList = new ArrayList();
	private List statResult = new ArrayList();
	private List resultList = new ArrayList();
	private String[] wldwlx;
	
	private String client_name = "";
	private String start_date = "";
	private String end_date = "";
	private String xsry_id = "";
	private String dj_id = "";
	private String client_type = "";
	private String khjl = "";
	private String product_kind = "";
	private String product_name = "";
	
	public String getProduct_kind() {
		return product_kind;
	}

	public void setProduct_kind(String productKind) {
		product_kind = productKind;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_Name) {
		product_name = product_Name;
	}

	public String showCondition(){
		userList = userService.getAllEmployeeList();
		clientList=clientsService.getClientList("");
		wldwlx = sjzdService.getSjzdXmxxByZdId("SJZD_WLDWLX");
		return "success";
	}
	
	public String getResult(){
		statResult = xstjClientService.getXstjClientResult(start_date, end_date, client_name, xsry_id,client_type,khjl,dj_id, product_kind, product_name);
		return "success";
	}
	
	public String getResultMx(){
		clientList = clientsService.getClientList(client_name);
		return "success";
	}	
	
	public String getResultLsMx(){
		resultList = xstjClientService.getLsdList(start_date,end_date,xsry_id,dj_id,product_kind,product_name);
		return "success";
	}

	public List getUserList() {
		return userList;
	}

	public void setUserList(List userList) {
		this.userList = userList;
	}

	public List getResultList() {
		return resultList;
	}

	public void setResultList(List resultList) {
		this.resultList = resultList;
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

	public SjzdService getSjzdService() {
		return sjzdService;
	}

	public void setSjzdService(SjzdService sjzdService) {
		this.sjzdService = sjzdService;
	}

	public String[] getWldwlx() {
		return wldwlx;
	}

	public void setWldwlx(String[] wldwlx) {
		this.wldwlx = wldwlx;
	}
	
	public String getClient_type() {
		return client_type;
	}

	public void setClient_type(String client_type) {
		this.client_type = client_type;
	}
	
	public String getKhjl() {
		return khjl;
	}

	public void setKhjl(String khjl) {
		this.khjl = khjl;
	}
}
