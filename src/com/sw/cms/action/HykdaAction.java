package com.sw.cms.action;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.model.Hykda;
import com.sw.cms.model.LoginInfo;
import com.sw.cms.model.Page;
import com.sw.cms.service.HykdaService;
import com.sw.cms.util.Constant;

public class HykdaAction extends BaseAction {

	private HykdaService hykdaService;
	private Page hykdaPage;
	private Hykda hykda;
	
	private String id = "";

	private String hykh = "";
	private String tel = "";
	private String sfzh = "";
	private String state = "正常";

	private int curPage = 1;
	private String orderName = "";
	private String orderType = "";

	/**
	 * 取会员卡档案列表
	 * 
	 * @return
	 */
	public String list() {
		LoginInfo info = (LoginInfo) getSession().getAttribute("LOGINUSER");
		String user_id = info.getUser_id();
		String con = "";

		if (!hykh.trim().equals("")) {
			con += " and hykh = '" + hykh + "'";
		}
		if (!tel.trim().equals("")) {
			con += " and (lxdh = '" + tel + "' or mobile = '" + tel + "')";
		}
		if (!sfzh.trim().equals("")) {
			con += " and sfzh = '" + sfzh + "'";
		}
		if (!state.trim().equals("")) {
			con += " and state = '" + state + "'";
		}

		if (orderName.equals("")) {
			orderName = "fkrq";
		}
		if (orderType.equals("")) {
			orderType = "desc";
		}

		con += " order by " + orderName + " " + orderType + "";
		int rowsPerPage = Constant.PAGE_SIZE2;
		hykdaPage = hykdaService.getHykdaList(curPage, rowsPerPage, con);
		return "success";
	}


	/**
	 * 更新会员卡档案
	 * 
	 * @return
	 */
	public String update() {
		LoginInfo info = (LoginInfo) getSession().getAttribute("LOGINUSER");
		String user_id = info.getUser_id();
		hykda.setCzr(user_id);

		hykdaService.updateHykda(hykda);
		return "success";
	}

	/**
	 * 编辑会员卡档案
	 * 
	 * @return
	 */
	public String edit() {
		hykda = hykdaService.getHykdaById(id);
		return "success";
	}
	
	
	/**
	 * 注销会员卡
	 * @return
	 */
	public String logout(){
		try{
			hykda = hykdaService.getHykdaById(id);
			hykda.setState("已注销");
			
			hykdaService.updateHykda(hykda);
			
			return SUCCESS;
		}catch(Exception e){
			log.error("注销会员卡出错" + e.getMessage());
			e.printStackTrace();
			return ERROR;
		}
	}


	public HykdaService getHykdaService() {
		return hykdaService;
	}


	public void setHykdaService(HykdaService hykdaService) {
		this.hykdaService = hykdaService;
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


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getHykh() {
		return hykh;
	}


	public void setHykh(String hykh) {
		this.hykh = hykh;
	}


	public String getTel() {
		return tel;
	}


	public void setTel(String tel) {
		this.tel = tel;
	}


	public String getSfzh() {
		return sfzh;
	}


	public void setSfzh(String sfzh) {
		this.sfzh = sfzh;
	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
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
