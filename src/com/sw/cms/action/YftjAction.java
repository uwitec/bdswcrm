package com.sw.cms.action;

import java.util.ArrayList;
import java.util.List;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.service.YftjService;

public class YftjAction extends BaseAction {

	private YftjService yftjService;
	
	private String yfrq1 = "";
	private String yfrq2 = "";
	private String gysbh = "";
	
	private List yfList = new ArrayList();
	private List yfMxList = new ArrayList();
	
	
	/**
	 * 应付统计列表
	 * @return
	 */
	public String listYftj(){
		yfList = yftjService.getYftjList(yfrq1, yfrq2);
		return "success";
	}
	
	
	/**
	 * 应付统计明细
	 * @return
	 */
	public String listYftjMx(){
		yfMxList = yftjService.getYftjMxList(gysbh);
		return "success";
	}
	
	public String getGysbh() {
		return gysbh;
	}
	public void setGysbh(String gysbh) {
		this.gysbh = gysbh;
	}
	public List getYfList() {
		return yfList;
	}
	public void setYfList(List yfList) {
		this.yfList = yfList;
	}
	public List getYfMxList() {
		return yfMxList;
	}
	public void setYfMxList(List yfMxList) {
		this.yfMxList = yfMxList;
	}
	public String getYfrq1() {
		return yfrq1;
	}
	public void setYfrq1(String yfrq1) {
		this.yfrq1 = yfrq1;
	}
	public String getYfrq2() {
		return yfrq2;
	}
	public void setYfrq2(String yfrq2) {
		this.yfrq2 = yfrq2;
	}
	public YftjService getYftjService() {
		return yftjService;
	}
	public void setYftjService(YftjService yftjService) {
		this.yftjService = yftjService;
	}
	
}
