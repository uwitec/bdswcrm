package com.sw.cms.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.service.YshzKhjlService;

/**
 * 客户经理应收汇总
 * @author liyt
 *
 */
public class YshzKhjlAction extends BaseAction {
	
	private YshzKhjlService yshzKhjlService;
	
	private List khjlList = new ArrayList();
	private Map qcMap = new HashMap();
	private Map fsMap = new HashMap();
	private List dzdList = new ArrayList();
	
	private String start_date = "";
	private String end_date = "";
	private String khjl = "";
	private String client_name = "";
	
	/**
	 * 打开查询条件页面
	 * @return
	 */
	public String showCondition(){
		try{
			return SUCCESS;
		}catch(Exception e){
			log.error("打开客户经理应收汇总条件页面错误," + e.getMessage());
			e.printStackTrace();
			return ERROR;
		}
	}
	
	
	/**
	 * 客户经理应收汇总结果
	 * @return
	 */
	public String getResult(){
		try{
			
			khjlList = yshzKhjlService.getKhjlList(client_name, khjl);
			qcMap = yshzKhjlService.getQc(start_date, client_name, khjl);
			fsMap = yshzKhjlService.getKhjlWlInfo(start_date, end_date, client_name, khjl);
			
			return SUCCESS;
		}catch(Exception e){
			log.error("客户经理应收汇总出错," + e.getMessage());
			e.printStackTrace();
			return ERROR;
		}
	}
	
	
	/**
	 * 客户经理应收汇总--明细
	 * @return
	 */
	public String getResultMx(){
		try{
			qcMap = yshzKhjlService.getQc(start_date, client_name, khjl);
			dzdList = yshzKhjlService.getKhjlYsDzd(start_date, end_date, client_name, khjl);
			return SUCCESS;
		}catch(Exception e){
			log.error("客户经理应收汇总出错," + e.getMessage());
			e.printStackTrace();
			return ERROR;
		}
	}
	
	public YshzKhjlService getYshzKhjlService() {
		return yshzKhjlService;
	}
	public void setYshzKhjlService(YshzKhjlService yshzKhjlService) {
		this.yshzKhjlService = yshzKhjlService;
	}
	public List getKhjlList() {
		return khjlList;
	}
	public void setKhjlList(List khjlList) {
		this.khjlList = khjlList;
	}
	public Map getQcMap() {
		return qcMap;
	}
	public void setQcMap(Map qcMap) {
		this.qcMap = qcMap;
	}
	public Map getFsMap() {
		return fsMap;
	}
	public void setFsMap(Map fsMap) {
		this.fsMap = fsMap;
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
	public String getKhjl() {
		return khjl;
	}
	public void setKhjl(String khjl) {
		this.khjl = khjl;
	}
	public String getClient_name() {
		return client_name;
	}
	public void setClient_name(String clientName) {
		client_name = clientName;
	}


	public List getDzdList() {
		return dzdList;
	}


	public void setDzdList(List dzdList) {
		this.dzdList = dzdList;
	}

}
