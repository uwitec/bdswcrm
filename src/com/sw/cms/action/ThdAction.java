package com.sw.cms.action;

import java.util.ArrayList;
import java.util.List;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.model.LoginInfo;
import com.sw.cms.model.Page;
import com.sw.cms.model.Thd;
import com.sw.cms.service.ClientsService;
import com.sw.cms.service.StoreService;
import com.sw.cms.service.ThdService;
import com.sw.cms.service.UserService;
import com.sw.cms.util.Constant;
import com.sw.cms.util.DateComFunc;

public class ThdAction extends BaseAction {

	private ThdService thdService;
	private StoreService storeService;
	private UserService userService;
	private ClientsService clientsService;

	private Page pageThd;

	private Thd thd = new Thd();

	private List thdProducts = new ArrayList();
	private List storeList = new ArrayList();
	private List userList = new ArrayList();
	private List clientsList= new ArrayList();

	private String client_name = "";
	private String th_date1 = DateComFunc.getToday();
	private String th_date2 = DateComFunc.getToday();
	private String orderName = "";
	private String orderType = "";
	private String thd_id = "";

	private int curPage = 1;

	/**
	 * 退货单列表（带分页）
	 * 
	 * @return
	 */
	public String list() {

		int rowsPerPage = Constant.PAGE_SIZE;

		String con = "";
		if (!client_name.equals("")) {
			con += " and client_name like'%" + client_name + "%'";
		}
		if (!th_date1.equals("")) {
			con += " and th_date>='" + th_date1 + "'";
		}
		if (!th_date2.equals("")) {
			con += " and th_date<='" + th_date2 + "'";
		}

		if (orderName.equals("")) {
			orderName = "thd_id";
		}
		if (orderType.equals("")) {
			orderType = "desc";
		}

		con += " order by " + orderName + " " + orderType;

		pageThd = thdService.getThdList(con, curPage, rowsPerPage);
		return "success";
	}

	/**
	 * 打开添加退货单页面
	 * 
	 * @return
	 */
	public String add() {
		thd_id = thdService.updateThdId();
		storeList = storeService.getAllStoreList();
		return "success";
	}

	/**
	 * 保存退货单信息
	 * 
	 * @return
	 */
	public String save() {
		LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		String user_id = info.getUser_id();
		thd.setCzr(user_id);
		
		thdService.saveThd(thd, thdProducts);
		return "success";
	}

	/**
	 * 编辑或查看退货单信息
	 * 
	 * @return
	 */
	public String edit() {
		thd = (Thd) thdService.getThd(thd_id);
		thdProducts = thdService.getThdProducts(thd_id);
		storeList = storeService.getAllStoreList();
		return "success";
	}
	
	
	/**
	 * 取退货单明细信息
	 * @return
	 */
	public String descThd(){
		thdProducts = thdService.getThdProducts(thd_id);
		return "success";
	}
	

	/**
	 * 更新退货单信息
	 * 
	 * @return
	 */
	public String update() {
		LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		String user_id = info.getUser_id();
		thd.setCzr(user_id);
		
		thdService.updateThd(thd, thdProducts);
		return "success";
	}

	/**
	 * 删除退货单信息
	 * 
	 * @return
	 */
	public String del() {
		thdService.delThd(thd_id);
		return "success";
	}

	public String getClient_name() {
		return client_name;
	}

	public void setClient_name(String client_name) {
		this.client_name = client_name;
	}

	public Page getPageThd() {
		return pageThd;
	}

	public void setPageThd(Page pageThd) {
		this.pageThd = pageThd;
	}

	public Thd getThd() {
		return thd;
	}

	public void setThd(Thd thd) {
		this.thd = thd;
	}

	public List getThdProducts() {
		return thdProducts;
	}

	public void setThdProducts(List thdProducts) {
		this.thdProducts = thdProducts;
	}

	public ThdService getThdService() {
		return thdService;
	}

	public void setThdService(ThdService thdService) {
		this.thdService = thdService;
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

	public StoreService getStoreService() {
		return storeService;
	}

	public void setStoreService(StoreService storeService) {
		this.storeService = storeService;
	}

	public List getStoreList() {
		return storeList;
	}

	public void setStoreList(List storeList) {
		this.storeList = storeList;
	}

	public String getThd_id() {
		return thd_id;
	}

	public void setThd_id(String thd_id) {
		this.thd_id = thd_id;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public List getUserList() {
		return userList;
	}

	public void setUserList(List userList) {
		this.userList = userList;
	}

	public List getClientsList() {
		return clientsList;
	}

	public void setClientsList(List clientsList) {
		this.clientsList = clientsList;
	}

	public ClientsService getClientsService() {
		return clientsService;
	}

	public void setClientsService(ClientsService clientsService) {
		this.clientsService = clientsService;
	}

	public String getTh_date1() {
		return th_date1;
	}

	public void setTh_date1(String thDate1) {
		th_date1 = thDate1;
	}

	public String getTh_date2() {
		return th_date2;
	}

	public void setTh_date2(String thDate2) {
		th_date2 = thDate2;
	}

}
