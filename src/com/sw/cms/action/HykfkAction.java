package com.sw.cms.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.model.LoginInfo;
import com.sw.cms.model.Page;
import com.sw.cms.model.Hykda;
import com.sw.cms.service.HykdaService;
import com.sw.cms.util.Constant;
import com.sw.cms.util.ParameterUtility;


public class HykfkAction extends BaseAction {
	
	private HykdaService hykdaService;

	private Page hykdaPage;
	private Hykda hykda;

	String real_name= "";
	private String hykh = "";
	private String state = "未使用";
	private int curPage = 1;
	
	private String orderName = "";
	private String orderType = "";
	/**
	 * 取会员卡发卡列表
	 * @return
	 */
	public String list(){
		LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		String user_id = info.getUser_id();
		String con = "";
		
		if (!hykh.trim().equals("")) {
			con += " and hykh like '" + hykh + "'";
		}
		
		if (!state.trim().equals("")) {
			con += " and state = '" + state + "'";
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
		return "success";
	}
	
	/**
	 * 保存会员卡发卡信息
	 * @return
	 */
	public String save(){
		LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		String nums = ParameterUtility.getStringParameter(getRequest(), "num","");
		String scfs = ParameterUtility.getStringParameter(getRequest(), "scfs","");
		String fklx = ParameterUtility.getStringParameter(getRequest(), "fklx","");
		String user_id = info.getUser_id();
		
		hykdaService.saveHykfk(hykda,nums,scfs,fklx);
		return "success";
	}
	
	/**
	 * 更新会员卡发卡记录
	 * @return
	 */
	public String update(){
		LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		String user_id = info.getUser_id();
		String fklx = ParameterUtility.getStringParameter(getRequest(), "fklx","");
		hykdaService.updateHykfk(hykda,fklx);
		return "success";
	}
	
	
	/**
	 * 编辑会员卡发卡记录
	 * @return
	 */
	public String edit(){
		hykda = hykdaService.getHykda(hykh);		
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

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
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
}
