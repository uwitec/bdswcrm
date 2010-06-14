package com.sw.cms.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.model.LoginInfo;
import com.sw.cms.model.Page;
import com.sw.cms.model.Hykzz;
import com.sw.cms.model.Hykda;
import com.sw.cms.service.HykflService;
import com.sw.cms.service.HykzzService;
import com.sw.cms.service.HykdaService;
import com.sw.cms.util.Constant;
import com.sw.cms.util.ParameterUtility;

public class HykdaAction extends BaseAction {
	
	private HykdaService hykdaService;
	private HykzzService hykzzService;
	private Page hykdaPage;
	private Hykda hykda;
	private Hykzz hykzz;
	private List hykflList = new ArrayList();
	String real_name= "";
	private String hykh = "";
	private int curPage = 1;
    private Map  hykdas;
	private String orderName = "";
	private String orderType = "";
	/**
	 * 取会员卡档案列表
	 * @return
	 */
	public String list(){
		LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		String user_id = info.getUser_id();
		String con = "";
		
		if (!hykh.trim().equals("")) {
			con += " and hykh like '" + hykh + "'";
		}
		
		if(orderName.equals("")){
			orderName = "hykh";
		}
		if(orderType.equals("")){
			orderType = "desc";
		}
		
		con += " order by " + orderName + " " + orderType + "";
		int rowsPerPage = Constant.PAGE_SIZE2;
		hykdaPage = hykdaService.getHykdaList(curPage, rowsPerPage,con);
		return "success";
	}
	

	/**
	 * 打开添加页面
	 * @return
	 */
	public String add(){
		hykflList = hykdaService.getAllHykflList();
		return "success";
	}
	
	
	
	/**
	 * 更新会员卡档案
	 * @return
	 */
	public String update(){
		LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		String user_id = info.getUser_id();
		hykda.setCzr(user_id);
		
		hykdaService.updateHykda(hykda);
		return "success";
	}
	
	
	/**
	 * 编辑会员卡档案
	 * @return
	 */
	public String edit(){		
		hykdas=(Map)hykdaService.getHykdaByHykh(hykh);
		
		return "success";
	}
	
	 
	 public String getReal_name() {
			return real_name;
		}

		public void setReal_name(String real_name) {
			this.real_name = real_name;
		}
 
	 
	public String getHykh() {
		return hykh;
	}

	public void setHykh(String hykh) {
		this.hykh = hykh;
	}

	public Page getHykdaPage() {
		return hykdaPage;
	}

	public void setHykdaPage(Page hykdaPage) {
		this.hykdaPage = hykdaPage;
	}

	public Hykda getHykda() {
		return hykda;
	}

	public void setHykda(Hykda hykda) {
		this.hykda = hykda;
	}

	public HykdaService getHykdaService() {
		return hykdaService;
	}

	public void setHykdaService(HykdaService hykdaService) {
		this.hykdaService = hykdaService;
	}

	public int getCurPage() {
		return curPage;
	}

	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}

	public String getOrderName() {
		return orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	
	public HykzzService getHykzzService() {
		return hykzzService;
	}

	public void setHykzzService(HykzzService hykzzService) {
		this.hykzzService = hykzzService;
	}
	
	public List getHykflList() {
		return hykflList;
	}

	public void setHykflList(List hykflList) {
		this.hykflList = hykflList;
	}
	
	public Map getHykdas() {
		return hykdas;
	}


	public void setHykdas(Map hykdas) {
		this.hykdas = hykdas;
	}
}
