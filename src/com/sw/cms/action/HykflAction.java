package com.sw.cms.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.model.LoginInfo;
import com.sw.cms.model.Page;
import com.sw.cms.model.Hykfl;
import com.sw.cms.service.HykflService;
import com.sw.cms.service.JfgzService;
import com.sw.cms.util.Constant;

public class HykflAction extends BaseAction {
	
	private HykflService hykflService;
	private JfgzService jfgzService;
	private Page hykflPage;
	private Hykfl hykfl;
	
	String real_name= "";
	private String id = "";
	private int curPage = 1;
	private List jfgzList = new ArrayList();
	private String orderName = "";
	private String orderType = "";
	/**
	 * 取会员卡分类列表
	 * @return
	 */
	public String list(){
		LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		String user_id = info.getUser_id();
		String con = "";
		if(orderName.equals("")){
			orderName = "id";
		}
		if(orderType.equals("")){
			orderType = "desc";
		}
		
		con += " order by " + orderName + " " + orderType + "";
		int rowsPerPage = Constant.PAGE_SIZE2;
		hykflPage = hykflService.getHykflList(curPage, rowsPerPage,con);
		return "success";
	}
	

	/**
	 * 打开添加页面
	 * @return
	 */
	public String add(){	
		id = hykflService.updateHykflId();
		jfgzList = jfgzService.getAllJfgzList();
		return "success";
	}
	
	
	/**
	 * 保存会员卡分类
	 * @return
	 */
	public String save(){
		LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		String user_id = info.getUser_id();
		hykfl.setCzr(user_id);
			
		hykflService.saveHykfl(hykfl);
		return "success";
	}
	
	
	/**
	 * 更新会员卡分类
	 * @return
	 */
	public String update(){
		LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		String user_id = info.getUser_id();
		hykfl.setCzr(user_id);
		
		hykflService.updateHykfl(hykfl);
		return "success";
	}
	
	
	/**
	 * 编辑会员卡分类
	 * @return
	 */
	public String edit(){
		jfgzList = jfgzService.getAllJfgzList();
		hykfl = hykflService.getHykfl(id);		
		return "success";
	}
	
	
	/**
	 * 删除会员卡分类
	 * @return
	 */
	public String del(){
		hykflService.delHykfl(id);
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

	public Page getHykflPage() {
		return hykflPage;
	}

	public void setHykflPage(Page hykflPage) {
		this.hykflPage = hykflPage;
	}

	public Hykfl getHykfl() {
		return hykfl;
	}

	public void setHykfl(Hykfl hykfl) {
		this.hykfl = hykfl;
	}

	public HykflService getHykflService() {
		return hykflService;
	}

	public void setHykflService(HykflService hykflService) {
		this.hykflService = hykflService;
	}

	public JfgzService getJfgzService() {
		return jfgzService;
	}

	public void setJfgzService(JfgzService jfgzService) {
		this.jfgzService = jfgzService;
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
	
	public List getJfgzList() {
		return jfgzList;
	}

	public void setJfgzList(List jfgzList) {
		this.jfgzList = jfgzList;
	}

}
