package com.sw.cms.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.model.LoginInfo;
import com.sw.cms.model.Page;
import com.sw.cms.model.Hykzz;
import com.sw.cms.service.HykflService;
import com.sw.cms.service.HykzzService;
import com.sw.cms.util.Constant;
import com.sw.cms.util.ParameterUtility;

public class HykzzAction extends BaseAction {
	
	private HykzzService hykzzService;
	private HykflService hykflService;
	private Page hykzzPage;
	private Hykzz hykzz;
	private List hykflList = new ArrayList();
	String real_name= "";
	private String id = "";
	private int curPage = 1;
	private String hykh = "";
	private String orderName = "";
	private String orderType = "";
	/**
	 * 取会员卡列表
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
		hykzzPage = hykzzService.getHykzzList(curPage, rowsPerPage,con);
		return "success";
	}
	

	/**
	 * 打开添加页面
	 * @return
	 */
	public String add(){
		hykflList = hykzzService.getAllHykflList();
		return "success";
	}
	
	
	/**
	 * 保存会员卡
	 * @return
	 */
	public String save(){
		LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		String nums = ParameterUtility.getStringParameter(getRequest(), "num","");
		String zzfs = ParameterUtility.getStringParameter(getRequest(), "zzfs","");
		String user_id = info.getUser_id();
		hykzz.setCzr(user_id);
		hykzz.setId(hykzzService.updateHykzzId());		
		hykzzService.saveHykzz(hykzz,nums,zzfs);
		return "success";
	}
	
	
	/**
	 * 更新会员卡
	 * @return
	 */
	public String update(){
		LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		String user_id = info.getUser_id();
		hykzz.setCzr(user_id);
		
		hykzzService.updateHykzz(hykzz);
		return "success";
	}
	
	
	/**
	 * 编辑会员卡
	 * @return
	 */
	public String edit(){
		hykflList = hykzzService.getAllHykflList();
		hykzz = hykzzService.getHykzz(id);
		
		return "success";
	}
	
	
	/**
	 * 删除会员卡
	 * @return
	 */
	public String del(){
		hykzzService.delHykzz(hykh);
		return "success";
	}
	
	
	 
	 public String getReal_name() {
			return real_name;
		}

		public void setReal_name(String real_name) {
			this.real_name = real_name;
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

	public HykzzService getHykzzService() {
		return hykzzService;
	}

	public void setHykzzService(HykzzService hykzzService) {
		this.hykzzService = hykzzService;
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
	
	public HykflService getHykflService() {
		return hykflService;
	}

	public void setHykflService(HykflService hykflService) {
		this.hykflService = hykflService;
	}
	
	public List getHykflList() {
		return hykflList;
	}

	public void setHykflList(List hykflList) {
		this.hykflList = hykflList;
	}
}
