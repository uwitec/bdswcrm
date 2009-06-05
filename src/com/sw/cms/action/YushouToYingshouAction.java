package com.sw.cms.action;

import java.util.ArrayList;
import java.util.List;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.model.LoginInfo;
import com.sw.cms.model.Page;
import com.sw.cms.model.YushouToYingshou;
import com.sw.cms.service.UserService;
import com.sw.cms.service.YushouToYingshouService;
import com.sw.cms.util.Constant;

public class YushouToYingshouAction extends BaseAction {
	
	private YushouToYingshouService yushouToYingshouService;
	private UserService userService;
	
	private Page yushouToYingshouPage;
	private YushouToYingshou yushouToYingshou = new YushouToYingshou();
	private List yushouToYingshouDescs = new ArrayList();
	private List userList = new ArrayList();
	private double clientHjYushouK = 0;   //客户合计预收款
	
	private String id;
	private String client_name = "";
	private String create_date1 = "";
	private String create_date2 = "";
	private String jsr = "";
	
	private String orderName ="";
	private String orderType ="";
	private int curPage = 1;
	private int rowsPerPage = Constant.PAGE_SIZE;
	
	/**
	 * 结算情况列表
	 * @return
	 */
	public String list(){
		
		String con = "";
		if(!client_name.equals("")){
			con += " and b.name like'%" + client_name + "%'";
		}
		if(!create_date1.equals("")){
			con += " and a.create_date>='" + create_date1 + "'";
		}
		if(!create_date2.equals("")){
			con += " and a.create_date<='" + (create_date2 + " 23:59:59") + "'";
		}
		if(!jsr.equals("")){
			con += " and a.jsr='" + jsr + "'";
		}
		if(orderName.equals("")){
			orderName = "a.id";
		}
		if(orderType.equals("")){
			orderType = "desc";
		}
		
		con += " order by " + orderName + " " + orderType;
		
		yushouToYingshouPage = yushouToYingshouService.getYushouToYingshouList(con, curPage, rowsPerPage);
		userList = userService.getAllEmployeeList();
		
		return "success";
	}
	
	
	/**
	 * 添加预收转应收
	 * @return
	 */
	public String add(){
		
		if(yushouToYingshou.getClient_name() != null && !yushouToYingshou.getClient_name().equals("")){
			yushouToYingshouDescs = yushouToYingshouService.getYingshoukByClientId(yushouToYingshou.getClient_name());
			clientHjYushouK = yushouToYingshouService.getYishoukjeByClientId(yushouToYingshou.getClient_name());
		}
		
		//如果编号为空，则设置编号
		if(yushouToYingshou.getId() == null || yushouToYingshou.getId().equals("")){
			yushouToYingshou.setId(yushouToYingshouService.updateID());
		}
		
		userList = userService.getAllEmployeeList();
		
		return "success";
	}
	
	
	/**
	 * 保存预收转应收
	 * @return
	 */
	public String update(){
		LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		String user_id = info.getUser_id();
		yushouToYingshou.setCzr(user_id);
		
		//保存预收转应收信息
		yushouToYingshouService.updateYushouToYingshou(yushouToYingshou,yushouToYingshouDescs);
		
		return "success";
	}
	
	
	/**
	 * 编辑预收转应收信息
	 * @return
	 */
	public String edit(){
		yushouToYingshou = yushouToYingshouService.getYushouToYingshou(id);
		yushouToYingshouDescs = yushouToYingshouService.getYushouToYingshouDesc(id);
		
		clientHjYushouK = yushouToYingshouService.getYishoukjeByClientId(yushouToYingshou.getClient_name());
		
		userList = userService.getAllEmployeeList();
		
		return "success";
	}
	
	
	/**
	 * 显示结算明细
	 * @return
	 */
	public String desc(){
		yushouToYingshouDescs = yushouToYingshouService.getYushouToYingshouDesc(id);
		return "success";
	}
	
	
	/**
	 * 删除预收转应收
	 * @return
	 */
	public String del(){
		yushouToYingshouService.delYushouToYingshou(id);
		return "success";
	}

	
	public String getClient_name() {
		return client_name;
	}
	public void setClient_name(String client_name) {
		this.client_name = client_name;
	}
	public String getCreate_date1() {
		return create_date1;
	}
	public void setCreate_date1(String create_date1) {
		this.create_date1 = create_date1;
	}
	public String getCreate_date2() {
		return create_date2;
	}
	public void setCreate_date2(String create_date2) {
		this.create_date2 = create_date2;
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
	public String getJsr() {
		return jsr;
	}
	public void setJsr(String jsr) {
		this.jsr = jsr;
	}
	public YushouToYingshou getYushouToYingshou() {
		return yushouToYingshou;
	}
	public void setYushouToYingshou(YushouToYingshou yushouToYingshou) {
		this.yushouToYingshou = yushouToYingshou;
	}
	public List getYushouToYingshouDescs() {
		return yushouToYingshouDescs;
	}
	public void setYushouToYingshouDescs(List yushouToYingshouDescs) {
		this.yushouToYingshouDescs = yushouToYingshouDescs;
	}
	public Page getYushouToYingshouPage() {
		return yushouToYingshouPage;
	}
	public void setYushouToYingshouPage(Page yushouToYingshouPage) {
		this.yushouToYingshouPage = yushouToYingshouPage;
	}
	public YushouToYingshouService getYushouToYingshouService() {
		return yushouToYingshouService;
	}
	public void setYushouToYingshouService(YushouToYingshouService yushouToYingshouService) {
		this.yushouToYingshouService = yushouToYingshouService;
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
	public List getUserList() {
		return userList;
	}
	public void setUserList(List userList) {
		this.userList = userList;
	}
	public UserService getUserService() {
		return userService;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}


	public double getClientHjYushouK() {
		return clientHjYushouK;
	}


	public void setClientHjYushouK(double clientHjYushouK) {
		this.clientHjYushouK = clientHjYushouK;
	}

}
