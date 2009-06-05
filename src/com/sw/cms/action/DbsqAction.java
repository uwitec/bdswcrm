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
	 * È¡µ÷²¦ÉêÇëÁĞ±í
	 * @return
	 */
	public String list(){
		int rowsPerPage = Constant.PAGE_SIZE;

		String con = "";
		
		//²éÑ¯Ìõ¼ş
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
	 * ´ò¿ªµ÷²¦ÉêÇëÌí¼ÓÒ³Ãæ
	 * @return
	 */
	public String add(){
		dbsq.setId(dbsqService.updateDbsqID());
		storeList = storeService.getAllStoreList();
		userList = userService.getAllEmployeeList();
		return "success";
	}
	
	
	/**
	 * ±£´æµ÷²¦ÉêÇë
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
	 * ±à¼­µ÷²¦ÉêÇë
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
	 * ¸üĞÂµ÷²¦ÉêÇë
	 * @return
	 */
	public String update(){
		LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		String user_id = info.getUser_id();
		dbsq.setCzr(user_id);
		
		dbsqService.updateDbsq(dbsq, dbsqProducts);
		return "success";
	}
	
	
	/**
	 * É¾³ıµ÷²¦ÉêÇë
	 * @return
	 */
	public String del(){
		dbsqService.delDbsq(id);
		return "success";
	}	
	
	
	/**
	 * µ÷²¦ÉêÇëÃ÷Ï¸
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
