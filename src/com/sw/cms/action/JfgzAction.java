package com.sw.cms.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.model.LoginInfo;
import com.sw.cms.model.Page;
import com.sw.cms.model.Jfgz;
import com.sw.cms.service.JfgzService;
import com.sw.cms.util.Constant;

public class JfgzAction extends BaseAction {
	
	private JfgzService jfgzService;
	
	private Page jfgzPage;
	private Jfgz jfgz;
	
	String real_name= "";
	private String id = "";
	private int curPage = 1;
	
	private String orderName = "";
	private String orderType = "";
	/**
	 * 取积分规则列表
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
		jfgzPage = jfgzService.getJfgzList(curPage, rowsPerPage,con);
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
	 * 保存备忘录
	 * @return
	 */
	public String save(){
		LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		String user_id = info.getUser_id();
		jfgz.setCzr(user_id);
		jfgz.setId(jfgzService.updateJfgzId());		
		jfgzService.saveJfgz(jfgz);
		return "success";
	}
	
	
	/**
	 * 更新备忘录
	 * @return
	 */
	public String update(){
		LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		String user_id = info.getUser_id();
		jfgz.setCzr(user_id);
		
		jfgzService.updateJfgz(jfgz);
		return "success";
	}
	
	
	/**
	 * 编辑备忘录
	 * @return
	 */
	public String edit(){
		jfgz = jfgzService.getJfgz(id);
		return "success";
	}
	
	
	/**
	 * 删除备忘录
	 * @return
	 */
	public String del(){
		jfgzService.delJfgz(id);
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

	public Page getJfgzPage() {
		return jfgzPage;
	}

	public void setJfgzPage(Page jfgzPage) {
		this.jfgzPage = jfgzPage;
	}

	public Jfgz getJfgz() {
		return jfgz;
	}

	public void setJfgz(Jfgz jfgz) {
		this.jfgz = jfgz;
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
}
