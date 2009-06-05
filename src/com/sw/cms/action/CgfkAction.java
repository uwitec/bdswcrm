package com.sw.cms.action;

import java.util.ArrayList;
import java.util.List;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.model.Cgfk;
import com.sw.cms.model.LoginInfo;
import com.sw.cms.model.Page;
import com.sw.cms.service.AccountsService;
import com.sw.cms.service.CgfkService;
import com.sw.cms.service.UserService;
import com.sw.cms.util.Constant;

public class CgfkAction extends BaseAction {

	private CgfkService cgfkService;
	private UserService userService;
	private AccountsService accountsService;
	
	private List userList;
	private List accountList;
	
	private Page cgfkPage;
	private Cgfk cgfk = new Cgfk();
	private List cgfkDescs = new ArrayList();
	private List providerList;
	
	private String gysmc = "";
	private String fk_date1 = "";
	private String fk_date2 = "";
	
	private String id;
	
	private String orderName ="";
	private String orderType ="";
	
	private int curPage = 1;
	
	
	/**
	 * 采购付款列表
	 * @return
	 */
	public String list(){
		
		int rowsPerPage = Constant.PAGE_SIZE2;
		
		String con = "";
		if(!fk_date1.equals("")){
			con += " and a.fk_date>='" + fk_date1 + "'";
		}
		if(!fk_date2.equals("")){
			con += " and a.fk_date<='" + fk_date2 + "'";
		}
		if(!gysmc.equals("")){
			con += " and b.name like'%" + gysmc + "%'";
		}
		
		if(orderName.equals("")){
			orderName = "id";
		}
		if(orderType.equals("")){
			orderType = "desc";
		}
		
		con += " order by " + orderName + " " + orderType;
		cgfkPage = cgfkService.getCgfks(con, curPage, rowsPerPage);
		
		return "success";
	}
	
	
	
	/**
	 * 转达到添加应付款页面
	 * @return
	 */
	public String add(){
		if(cgfk.getGysbh()!=null && !cgfk.getGysbh().equals("")){
			cgfkDescs = cgfkService.getDfkDesc(cgfk.getGysbh());
		}
		userList = userService.getAllEmployeeList();
		if(cgfk.getId()==null || cgfk.getId().equals("")){
			id = cgfkService.updateCgfkID();
			cgfk.setId(id);
		}
		
		return "success";
	}
	
	
	
	
	/**
	 * 保存应付款信息
	 * @return
	 */
	public String save(){		
		LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		String user_id = info.getUser_id();
		cgfk.setCzr(user_id);
		
		cgfkService.saveCgfk(cgfk, cgfkDescs);
		return "success";
	}
	
	
	/**
	 * 根据应付款ID取应付款信息
	 * @return
	 */
	public String edit(){
		cgfk = (Cgfk)cgfkService.getCgfk(id);
		cgfkDescs = cgfkService.getCgfkDesc(id);
		
		userList = userService.getAllEmployeeList();
		accountList = accountsService.getAccountList();
		
		return "success";
	}
	
	
	/**
	 * 更新应付款信息
	 * @return
	 */
	public String update(){
		LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		String user_id = info.getUser_id();
		cgfk.setCzr(user_id);
		
		cgfkService.updateCgfk(cgfk, cgfkDescs);
		return "success";
	}
	
	
	/**
	 * 根据应付款ID删除应付款信息
	 * @return
	 */
	public String del(){
		cgfkService.delCgfk(id);
		return "success";
	}



	public Cgfk getCgfk() {
		return cgfk;
	}



	public void setCgfk(Cgfk cgfk) {
		this.cgfk = cgfk;
	}



	public List getCgfkDescs() {
		return cgfkDescs;
	}



	public void setCgfkDescs(List cgfkDescs) {
		this.cgfkDescs = cgfkDescs;
	}



	public Page getCgfkPage() {
		return cgfkPage;
	}



	public void setCgfkPage(Page cgfkPage) {
		this.cgfkPage = cgfkPage;
	}



	public CgfkService getCgfkService() {
		return cgfkService;
	}



	public void setCgfkService(CgfkService cgfkService) {
		this.cgfkService = cgfkService;
	}



	public int getCurPage() {
		return curPage;
	}



	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}



	public String getFk_date1() {
		return fk_date1;
	}



	public void setFk_date1(String fk_date1) {
		this.fk_date1 = fk_date1;
	}



	public String getFk_date2() {
		return fk_date2;
	}



	public void setFk_date2(String fk_date2) {
		this.fk_date2 = fk_date2;
	}



	public String getGysmc() {
		return gysmc;
	}



	public void setGysmc(String gysmc) {
		this.gysmc = gysmc;
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



	public List getProviderList() {
		return providerList;
	}



	public void setProviderList(List providerList) {
		this.providerList = providerList;
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



	public AccountsService getAccountsService() {
		return accountsService;
	}



	public void setAccountsService(AccountsService accountsService) {
		this.accountsService = accountsService;
	}



	public List getAccountList() {
		return accountList;
	}



	public void setAccountList(List accountList) {
		this.accountList = accountList;
	}
	
	
}
