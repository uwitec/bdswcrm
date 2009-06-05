package com.sw.cms.action;

import java.util.List;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.model.ClientWlInit;
import com.sw.cms.model.LoginInfo;
import com.sw.cms.model.Page;
import com.sw.cms.service.ClientWlInitService;
import com.sw.cms.util.Constant;

public class ClientWlInitAction extends BaseAction {
	
	private ClientWlInitService clientWlInitService;
	
	private Page clientWlInitPage;
	private ClientWlInit clientWlInit;
	
	private String client_name = "";
	private int curPage = 1;
	private String seq_id = "";
	
	public String list(){
		int rowsPerPage = Constant.PAGE_SIZE2;
		String con = "";
		
		if(!client_name.equals("")){
			con = con + " and b.name like '%" + client_name + "%'";
		}
		
		clientWlInitPage = clientWlInitService.getWlInitList(con, curPage, rowsPerPage);
		
		return "success";
	}
	
	public String add(){
		return "success";
	}
	
	public String save(){
		LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		String user_id = info.getUser_id();
		
		clientWlInit.setCzr(user_id);
		
		List chkList = clientWlInitService.getWlInitList(clientWlInit.getClient_name());
		
		if(chkList != null && chkList.size()>0){
			this.saveMessage("客户往来初始已存在，无法再次设置，请检查！");
			return "input";
		}
		
		clientWlInitService.insertWlInitInfo(clientWlInit);
		
		return "success";
	}
	
	
	public String del(){
		clientWlInitService.delClientWlInit(seq_id);
		return "success";
	}
	
	public ClientWlInit getClientWlInit() {
		return clientWlInit;
	}
	public void setClientWlInit(ClientWlInit clientWlInit) {
		this.clientWlInit = clientWlInit;
	}
	public Page getClientWlInitPage() {
		return clientWlInitPage;
	}
	public void setClientWlInitPage(Page clientWlInitPage) {
		this.clientWlInitPage = clientWlInitPage;
	}
	public ClientWlInitService getClientWlInitService() {
		return clientWlInitService;
	}
	public void setClientWlInitService(ClientWlInitService clientWlInitService) {
		this.clientWlInitService = clientWlInitService;
	}
	public String getClient_name() {
		return client_name;
	}
	public void setClient_name(String client_name) {
		this.client_name = client_name;
	}
	public int getCurPage() {
		return curPage;
	}
	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}

	public String getSeq_id() {
		return seq_id;
	}

	public void setSeq_id(String seq_id) {
		this.seq_id = seq_id;
	}

}
