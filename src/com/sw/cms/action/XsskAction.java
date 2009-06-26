package com.sw.cms.action;

import java.util.ArrayList;
import java.util.List;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.model.LoginInfo;
import com.sw.cms.model.Page;
import com.sw.cms.model.Xssk;
import com.sw.cms.service.ClientsService;
import com.sw.cms.service.UserService;
import com.sw.cms.service.XsskService;
import com.sw.cms.util.Constant;

public class XsskAction extends BaseAction {
	
	private XsskService xsskService;
	private UserService userService;
	private ClientsService clientsService;
	
	private Page pageXssk;
	private Xssk xssk = new Xssk();
	private List xsskDescs = new ArrayList();
	private List userList = new ArrayList();
	private List clientsList= new ArrayList();
	
	private String id;
	private String sk_date1 = "";
	private String sk_date2 = "";
	private String client_name = "";
	
	private String orderName ="";
	private String orderType ="";	
	private int curPage = 1;
	
	
	/**
	 * 取销售收款列表
	 * @return
	 */
	public String list(){
		int rowsPerPage = Constant.PAGE_SIZE2;
		
		String con = "";
		
		if(!sk_date1.equals("")){
			con += " and sk_date>='" + sk_date1 + "'";
		}
		if(!sk_date2.equals("")){
			con += " and sk_date<='" + sk_date2 + "'";
		}
		if(!client_name.equals("")){
			con += " and b.name like'%" + client_name + "%'";
		}
		if(orderName.equals("")){
			orderName = "id";
		}
		if(orderType.equals("")){
			orderType = "desc";
		}
		
		con += " order by " + orderName + " " + orderType;
		
		pageXssk = xsskService.getXsskList(con, curPage, rowsPerPage);
		
		return "success";
	}
	
	
	/**
	 * 打开添加销售收款页面
	 * @return
	 */
	public String add(){
		
		if(xssk.getClient_name()!=null && !xssk.getClient_name().equals("")){
			xsskDescs = xsskService.getYskByClientId(xssk.getClient_name());
		}
		
		if(xssk.getId()== null || xssk.getId().equals("")){
			xssk.setId(xsskService.updateXsskId());
		}
		
		userList = userService.getAllEmployeeList();
		clientsList=clientsService.getClientList("");
		return "success";
	}
	
	
	/**
	 * 保存销售收款信息
	 * @return
	 */
	public String save(){
		LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		String user_id = info.getUser_id();
		
		xssk.setCzr(user_id);
		
		xsskService.saveXssk(xssk, xsskDescs);
		return "success";
	}
	
	
	/**
	 * 编辑销售收款信息
	 * @return
	 */
	public String edit(){
		xssk = (Xssk)xsskService.getXssk(id);
		xsskDescs = xsskService.getXsskDescs(id);
		
		userList = userService.getAllEmployeeList();
		return "success";
	}
	
	
	/**
	 * 更新销售收款信息
	 * @return
	 */
	public String update(){
		LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		String user_id = info.getUser_id();
		
		xssk.setCzr(user_id);
		
		xsskService.updateXssk(xssk, xsskDescs);
		return "success";
	}
	
	
	/**
	 * 删除销售收款信息
	 * @return
	 */
	public String del(){
		xsskService.delXssk(id);
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
	public Page getPageXssk() {
		return pageXssk;
	}
	public void setPageXssk(Page pageXssk) {
		this.pageXssk = pageXssk;
	}
	public Xssk getXssk() {
		return xssk;
	}
	public void setXssk(Xssk xssk) {
		this.xssk = xssk;
	}
	public List getXsskDescs() {
		return xsskDescs;
	}
	public void setXsskDescs(List xsskDescs) {
		this.xsskDescs = xsskDescs;
	}
	public XsskService getXsskService() {
		return xsskService;
	}
	public void setXsskService(XsskService xsskService) {
		this.xsskService = xsskService;
	}
	public String getSk_date1() {
		return sk_date1;
	}
	public void setSk_date1(String sk_date1) {
		this.sk_date1 = sk_date1;
	}
	public String getSk_date2() {
		return sk_date2;
	}
	public void setSk_date2(String sk_date2) {
		this.sk_date2 = sk_date2;
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


	public String getClient_name() {
		return client_name;
	}


	public void setClient_name(String client_name) {
		this.client_name = client_name;
	}


	public ClientsService getClientsService() {
		return clientsService;
	}


	public void setClientsService(ClientsService clientsService) {
		this.clientsService = clientsService;
	}


	public List getClientsList() {
		return clientsList;
	}


	public void setClientsList(List clientsList) {
		this.clientsList = clientsList;
	}

}
