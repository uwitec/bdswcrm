package com.sw.cms.action;

import java.util.ArrayList;
import java.util.List;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.service.YstjService;

public class YstjAction extends BaseAction {
	
	private YstjService ystjService;
	
	private String ysrq1 = "";
	private String ysrq2 = "";
	private String client_name = "";
	
	private List ysList = new ArrayList();
	private List ysMxList = new ArrayList();
	
	
	/**
	 * 应收统计列表
	 * @return
	 */
	public String listYstj(){
		ysList = ystjService.getYsktj(ysrq1, ysrq2);
		return "success";
	}
	
	
	/**
	 * 应收统计明细
	 * @return
	 */
	public String listYstjMx(){
		ysMxList = ystjService.getYskMx(client_name);
		return "success";
	}
	

	public List getYsList() {
		return ysList;
	}

	public void setYsList(List ysList) {
		this.ysList = ysList;
	}

	public String getYsrq1() {
		return ysrq1;
	}

	public void setYsrq1(String ysrq1) {
		this.ysrq1 = ysrq1;
	}

	public String getYsrq2() {
		return ysrq2;
	}

	public void setYsrq2(String ysrq2) {
		this.ysrq2 = ysrq2;
	}

	public YstjService getYstjService() {
		return ystjService;
	}

	public void setYstjService(YstjService ystjService) {
		this.ystjService = ystjService;
	}


	public String getClient_name() {
		return client_name;
	}


	public void setClient_name(String client_name) {
		this.client_name = client_name;
	}


	public List getYsMxList() {
		return ysMxList;
	}


	public void setYsMxList(List ysMxList) {
		this.ysMxList = ysMxList;
	}

}
