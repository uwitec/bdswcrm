package com.sw.cms.action;

import java.util.ArrayList;
import java.util.List;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.service.DzdService;

public class DzdAction extends BaseAction {
	
	private DzdService dzdService;
	private List dzdList = new ArrayList();
	
	private String client_id = "";
	private String s_date = "";
	private String e_date = "";
	
	/**
	 * 对账单列表页面
	 * @return
	 */
	public String list(){
		if(!client_id.equals("")){ //必须选择客户才能查看对账单
			dzdList = dzdService.getDzdList(client_id, s_date, e_date);
		}
		return "success";
	}
	
	
	public String getClient_id() {
		return client_id;
	}
	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}
	public List getDzdList() {
		return dzdList;
	}
	public void setDzdList(List dzdList) {
		this.dzdList = dzdList;
	}
	public DzdService getDzdService() {
		return dzdService;
	}
	public void setDzdService(DzdService dzdService) {
		this.dzdService = dzdService;
	}
	public String getE_date() {
		return e_date;
	}
	public void setE_date(String e_date) {
		this.e_date = e_date;
	}
	public String getS_date() {
		return s_date;
	}
	public void setS_date(String s_date) {
		this.s_date = s_date;
	}
	
}
