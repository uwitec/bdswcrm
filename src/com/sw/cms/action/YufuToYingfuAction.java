package com.sw.cms.action;

import java.util.ArrayList;
import java.util.List;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.model.LoginInfo;
import com.sw.cms.model.Page;
import com.sw.cms.model.YufuToYingfu;
import com.sw.cms.service.UserService;
import com.sw.cms.service.YufuToYingfuService;
import com.sw.cms.util.Constant;

public class YufuToYingfuAction extends BaseAction {
	
	private YufuToYingfuService yufuToYingfuService;
	private UserService userService;
	
	private Page yufuToYingfuPage;
	private YufuToYingfu yufuToYingfu = new YufuToYingfu();
	private List yufuToYingfuDescs =  new ArrayList();
	private List userList = new ArrayList();
	private double clientHjYufuK = 0;   //¿Í»§ºÏ¼ÆÔ¤¸¶¿î
	
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
	 * ½áËãÇé¿öÁĞ±í
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
		
		yufuToYingfuPage = yufuToYingfuService.getYufuToYingfuList(con, curPage, rowsPerPage);
		userList = userService.getAllEmployeeList();
		
		return "success";
	}
	
	
	/**
	 * Ìí¼ÓÔ¤¸¶³åÓ¦¸¶
	 * @return
	 */
	public String add(){
		
		if(yufuToYingfu.getClient_name() != null && !yufuToYingfu.getClient_name().equals("")){
			yufuToYingfuDescs = yufuToYingfuService.getYingfukByClientId(yufuToYingfu.getClient_name());
			clientHjYufuK = yufuToYingfuService.getYufukjeByClientId(yufuToYingfu.getClient_name());
		}
		
		//Èç¹û±àºÅÎª¿Õ£¬ÔòÉèÖÃ±àºÅ
		if(yufuToYingfu.getId() == null || yufuToYingfu.getId().equals("")){
			yufuToYingfu.setId(yufuToYingfuService.updateID());
		}
		
		userList = userService.getAllEmployeeList();
		return "success";
	}
	
	
	/**
	 * ±£´æÔ¤¸¶³åÓ¦¸¶
	 * @return
	 */
	public String update(){
		LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		String user_id = info.getUser_id();
		yufuToYingfu.setCzr(user_id);
		
		//±£´æÔ¤¸¶³åÓ¦¸¶ĞÅÏ¢
		yufuToYingfuService.updateYufuToYingfu(yufuToYingfu,yufuToYingfuDescs);
		
		return "success";
	}
	
	
	/**
	 * ±à¼­Ô¤¸¶³åÓ¦¸¶ĞÅÏ¢
	 * @return
	 */
	public String edit(){
		yufuToYingfu = yufuToYingfuService.getYufuToYingfu(id);
		yufuToYingfuDescs = yufuToYingfuService.getYufuToYingfuDesc(id);
		
		clientHjYufuK = yufuToYingfuService.getYufukjeByClientId(yufuToYingfu.getClient_name());
		
		userList = userService.getAllEmployeeList();
		
		return "success";
	}
	
	
	/**
	 * ÏÔÊ¾½áËãÃ÷Ï¸
	 * @return
	 */
	public String desc(){
		yufuToYingfuDescs = yufuToYingfuService.getYufuToYingfuDesc(id);
		return "success";
	}
	
	
	/**
	 * É¾³ıÔ¤¸¶³åÓ¦¸¶
	 * @return
	 */
	public String del(){
		yufuToYingfuService.delYufuToYingfu(id);
		return "success";
	}
	
	
	public String getClient_name() {
		return client_name;
	}
	public void setClient_name(String client_name) {
		this.client_name = client_name;
	}
	public double getClientHjYufuK() {
		return clientHjYufuK;
	}
	public void setClientHjYufuK(double clientHjYufuK) {
		this.clientHjYufuK = clientHjYufuK;
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
	public YufuToYingfu getYufuToYingfu() {
		return yufuToYingfu;
	}
	public void setYufuToYingfu(YufuToYingfu yufuToYingfu) {
		this.yufuToYingfu = yufuToYingfu;
	}
	public List getYufuToYingfuDescs() {
		return yufuToYingfuDescs;
	}
	public void setYufuToYingfuDescs(List yufuToYingfuDescs) {
		this.yufuToYingfuDescs = yufuToYingfuDescs;
	}
	public Page getYufuToYingfuPage() {
		return yufuToYingfuPage;
	}
	public void setYufuToYingfuPage(Page yufuToYingfuPage) {
		this.yufuToYingfuPage = yufuToYingfuPage;
	}
	public YufuToYingfuService getYufuToYingfuService() {
		return yufuToYingfuService;
	}
	public void setYufuToYingfuService(YufuToYingfuService yufuToYingfuService) {
		this.yufuToYingfuService = yufuToYingfuService;
	}

}
