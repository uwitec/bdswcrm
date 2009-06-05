package com.sw.cms.action;

import java.util.ArrayList;
import java.util.List;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.model.LoginInfo;
import com.sw.cms.model.Nbzz;
import com.sw.cms.model.Page;
import com.sw.cms.service.AccountsService;
import com.sw.cms.service.NbzzService;
import com.sw.cms.service.UserService;
import com.sw.cms.util.Constant;

public class NbzzAction extends BaseAction {
	
	private NbzzService nbzzService;
	private UserService userService;
	private AccountsService accountsService;
	
	private Page pageNbzz;
	private Nbzz nbzz = new Nbzz();
	private List userList = new ArrayList();
	
	private String id = "";
	private String zz_date1 = "";
	private String zz_date2 = "";
	
	private String orderName ="";
	private String orderType ="";
	
	private int curPage = 1;
	
	
	/**
	 * 内部转账列表
	 * @return
	 */
	public String list(){
		int rowsPerPage = Constant.PAGE_SIZE2;

		String con = "";
		
		if(!zz_date1.equals("")){
			con += " and zz_date>='" + zz_date1 + "'";
		}
		if(!zz_date2.equals("")){
			con += " and zz_date<='" + zz_date2 + "'";
		}
		
		if(orderName.equals("")){
			orderName = "id";
		}
		if(orderType.equals("")){
			orderType = "desc";
		}
		
		con += " order by " + orderName + " " + orderType;
		
		pageNbzz = nbzzService.getNbzzList(con, curPage, rowsPerPage);
		
		return "success";
	}
	
	
	/**
	 * 添加内部转账
	 * @return
	 */
	public String add(){
		userList = userService.getAllEmployeeList();
		id = nbzzService.updateNbzzId();
		nbzz.setId(id);
		return "success";
	}
	
	
	/**
	 * 保存内部转账
	 * @return
	 */
	public String save(){
		userList = userService.getAllEmployeeList();
		if(accountsService.isZhjeXyZero(nbzz.getZczh(), nbzz.getZzje())){
			this.saveMessage("转出账户金额不足，请检查！");
			return "input";
		}
		
		LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		String user_id = info.getUser_id();
		nbzz.setCzr(user_id);
		
		nbzzService.saveNbzz(nbzz);
		return "success";
	}
	
	
	/**
	 * 编辑内部转账
	 * @return
	 */
	public String edit(){
		nbzz = (Nbzz)nbzzService.getNbzz(id);
		userList = userService.getAllEmployeeList();
		return "success";
	}
	
	
	/**
	 * 更新内部转账
	 * @return
	 */
	public String update(){
		userList = userService.getAllEmployeeList();
		if(accountsService.isZhjeXyZero(nbzz.getZczh(), nbzz.getZzje())){
			this.saveMessage("转出账户金额不足，请检查！");
			return "input";
		}
		
		LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		String user_id = info.getUser_id();
		nbzz.setCzr(user_id);
		
		nbzzService.updateNbzz(nbzz);
		return "success";
	}
	
	
	/**
	 * 删除内部转账
	 * @return
	 */
	public String del(){
		nbzzService.delNbzz(id);
		return "success";
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

	public Nbzz getNbzz() {
		return nbzz;
	}

	public void setNbzz(Nbzz nbzz) {
		this.nbzz = nbzz;
	}

	public NbzzService getNbzzService() {
		return nbzzService;
	}

	public void setNbzzService(NbzzService nbzzService) {
		this.nbzzService = nbzzService;
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

	public Page getPageNbzz() {
		return pageNbzz;
	}

	public void setPageNbzz(Page pageNbzz) {
		this.pageNbzz = pageNbzz;
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

	public String getZz_date1() {
		return zz_date1;
	}

	public void setZz_date1(String zz_date1) {
		this.zz_date1 = zz_date1;
	}

	public String getZz_date2() {
		return zz_date2;
	}

	public void setZz_date2(String zz_date2) {
		this.zz_date2 = zz_date2;
	}


	public AccountsService getAccountsService() {
		return accountsService;
	}


	public void setAccountsService(AccountsService accountsService) {
		this.accountsService = accountsService;
	}

}
