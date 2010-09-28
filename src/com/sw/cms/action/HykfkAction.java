package com.sw.cms.action;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.model.Hykda;
import com.sw.cms.model.Hykzz;
import com.sw.cms.model.LoginInfo;
import com.sw.cms.model.Page;
import com.sw.cms.service.HykdaService;
import com.sw.cms.service.HykzzService;
import com.sw.cms.util.Constant;
import com.sw.cms.util.UUIDGenerator;

public class HykfkAction extends BaseAction {

	private HykdaService hykdaService;
	private HykzzService hykzzService;

	private Page hykzzPage;
	private Hykzz hykzz;

	private Hykda hykda;

	String real_name = "";
	private String hykh = "";
	private String state = "未使用";
	private int curPage = 1;
	private String id = "";

	private String orderName = "";
	private String orderType = "";

	/**
	 * 取会员卡发卡列表<BR>
	 * 取会员卡制作中的信息
	 * @return
	 */
	public String list() {

		String con = "";

		if (!hykh.trim().equals("")) {
			con += " and hykh = '" + hykh + "'";
		}

		if (!state.trim().equals("")) {
			con += " and state = '" + state + "'";
		}

		if (orderName.equals("")) {
			orderName = "hykh";
		}
		if (orderType.equals("")) {
			orderType = "desc";
		}

		con += " order by " + orderName + " " + orderType + "";

		int rowsPerPage = Constant.PAGE_SIZE2;
		hykzzPage = hykzzService.getHykzzList(curPage, rowsPerPage, con);

		return SUCCESS;
	}

	/**
	 * 打开发卡页面
	 * @return
	 */
	public String edit() {
		hykzz = hykzzService.getHykzz(id);
		return SUCCESS;
	}
	
	/**
	 * 执行发卡操作
	 * @return
	 */
	public String update() {
		LoginInfo info = (LoginInfo) getSession().getAttribute("LOGINUSER");
		String user_id = info.getUser_id();
		hykda.setCzr(user_id);
		hykda.setId(UUIDGenerator.getUUID());
		hykda.setState("正常");
		
		String returnVl = hykdaService.updateHykfk(hykda);
		
		if(!returnVl.equals("")){
			this.setMsg(returnVl);
			hykzz = hykzzService.getHykzz(hykda.getHykh());
			return INPUT;
		}
		
		return SUCCESS;
	}
	
	
	/**
	 * 执行退卡操作
	 * @return
	 */
	public String doTh(){
		try{
			hykzzService.updateHykzzDoth(id);
			return SUCCESS;
		}catch(Exception e){
			log.error("退货失败" + e.getMessage());
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

	public HykzzService getHykzzService() {
		return hykzzService;
	}

	public void setHykzzService(HykzzService hykzzService) {
		this.hykzzService = hykzzService;
	}

	public Page getHykzzPage() {
		return hykzzPage;
	}

	public void setHykzzPage(Page hykzzPage) {
		this.hykzzPage = hykzzPage;
	}

	public Hykzz getHykzz() {
		return hykzz;
	}

	public void setHykzz(Hykzz hykzz) {
		this.hykzz = hykzz;
	}

	public Hykda getHykda() {
		return hykda;
	}

	public void setHykda(Hykda hykda) {
		this.hykda = hykda;
	}

	public String getReal_name() {
		return real_name;
	}

	public void setReal_name(String realName) {
		real_name = realName;
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

	public int getCurPage() {
		return curPage;
	}

	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
