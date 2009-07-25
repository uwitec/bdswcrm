package com.sw.cms.action;

import java.util.ArrayList;
import java.util.List;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.model.Dbsq;
import com.sw.cms.model.LoginInfo;
import com.sw.cms.model.Page;
import com.sw.cms.service.DbsqService;
import com.sw.cms.service.StoreService;
import com.sw.cms.service.UserService;
import com.sw.cms.util.Constant;

public class DbsqAction extends BaseAction {
	
	private DbsqService dbsqService;
	private UserService userService;
	private StoreService storeService;
	
	private Page pageDbsq;
	private Dbsq dbsq = new Dbsq();
	private List dbsqProducts = new ArrayList();
	private List storeList = new ArrayList();
	private List userList = new ArrayList();
	
	private String id = "";
	private String creatdate1 = "";
	private String creatdate2 = "";
	
	private String orderName ="";
	private String orderType ="";	
	private int curPage = 1;
	
	
	/**
	 * 取调拨申请列表
	 * @return
	 */
	public String list(){
		int rowsPerPage = Constant.PAGE_SIZE;

		String con = "";
		
		//查询条件
		if(!creatdate1.equals("")){
			con += " and creatdate>='" + creatdate1 + "'";
		}
		if(!creatdate2.equals("")){
			con += " and creatdate<='" + creatdate2 + "'";
		}
		
		if(orderName.equals("")){
			orderName = "id";
		}
		if(orderType.equals("")){
			orderType = "desc";
		}
		
		con += " order by " + orderName + " " + orderType;
		
		pageDbsq = dbsqService.getDbsqList(con, curPage, rowsPerPage);
		return "success";
	}
	
	
	/**
	 * 打开调拨申请添加页面
	 * @return
	 */
	public String add(){
		dbsq.setId(dbsqService.updateDbsqID());
		storeList = storeService.getAllStoreList();
		userList = userService.getAllEmployeeList();
		return "success";
	}
	
	
	/**
	 * 保存调拨申请
	 * @return
	 */
	public String save(){
		LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		String user_id = info.getUser_id();
		dbsq.setCzr(user_id);
		
		dbsqService.saveDbsq(dbsq, dbsqProducts);
		return "success";
	}
	
	
	/**
	 * 编辑调拨申请
	 * @return
	 */
	public String edit(){
		storeList = storeService.getAllStoreList();
		userList = userService.getAllEmployeeList();
		
		dbsq = (Dbsq)dbsqService.getDbsq(id);
		dbsqProducts = dbsqService.getDbsqProducts(id);
		return "success";
	}
	
	
	/**
	 * 更新调拨申请
	 * @return
	 */
	public String update(){
		LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		String user_id = info.getUser_id();
		dbsq.setCzr(user_id);
		
		//判断调拨申请是否已经提交
		if(dbsqService.isDbsqFinish(dbsq.getId())){
			this.saveMessage("调拨申请已经提交，不能重复提交，请检查！");
			storeList = storeService.getAllStoreList();
			return "input";
		}
		
		dbsqService.updateDbsq(dbsq, dbsqProducts);
		return "success";
	}
	
	
	/**
	 * 删除调拨申请
	 * @return
	 */
	public String del(){
		dbsqService.delDbsq(id);
		return "success";
	}	
	
	
	/**
	 * 调拨申请明细
	 * @return
	 */
	public String descDbsq(){
		dbsqProducts = dbsqService.getDbsqProducts(id);
		return "success";
	}
	
	
	public String getCreatdate1() {
		return creatdate1;
	}
	public void setCreatdate1(String creatdate1) {
		this.creatdate1 = creatdate1;
	}
	public String getCreatdate2() {
		return creatdate2;
	}
	public void setCreatdate2(String creatdate2) {
		this.creatdate2 = creatdate2;
	}
	public int getCurPage() {
		return curPage;
	}
	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}
	public Dbsq getDbsq() {
		return dbsq;
	}
	public void setDbsq(Dbsq dbsq) {
		this.dbsq = dbsq;
	}
	public List getDbsqProducts() {
		return dbsqProducts;
	}
	public void setDbsqProducts(List dbsqProducts) {
		this.dbsqProducts = dbsqProducts;
	}
	public DbsqService getDbsqService() {
		return dbsqService;
	}
	public void setDbsqService(DbsqService dbsqService) {
		this.dbsqService = dbsqService;
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
	public List getStoreList() {
		return storeList;
	}
	public void setStoreList(List storeList) {
		this.storeList = storeList;
	}
	public StoreService getStoreService() {
		return storeService;
	}
	public void setStoreService(StoreService storeService) {
		this.storeService = storeService;
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
	public Page getPageDbsq() {
		return pageDbsq;
	}
	public void setPageDbsq(Page pageDbsq) {
		this.pageDbsq = pageDbsq;
	}

}
