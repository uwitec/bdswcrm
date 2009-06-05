package com.sw.cms.action;

import java.util.ArrayList;
import java.util.List;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.model.Jhd;
import com.sw.cms.model.JhdProduct;
import com.sw.cms.model.LoginInfo;
import com.sw.cms.model.Page;
import com.sw.cms.service.JhdService;
import com.sw.cms.service.StoreService;
import com.sw.cms.service.UserService;
import com.sw.cms.util.Constant;
import com.sw.cms.util.DateComFunc;
import com.sw.cms.util.ParameterUtility;

public class JhdAction extends BaseAction {

	
	private JhdService jhdService;
	private UserService userService;
	private StoreService storeService;

	private Page jhdPage;

	private Jhd jhd = new Jhd();

	private JhdProduct jhdProduct = new JhdProduct();

	private List jhdProducts = new ArrayList();
	private List providers = new ArrayList();	
	private List userList = new ArrayList();
	private List storeList = new ArrayList();
	
	
	private String gysbh ="";
	private String state ="";
	private String cg_date = DateComFunc.getToday();
	private String cg_date2 = DateComFunc.getToday();
	private String orderName ="";
	private String orderType ="";
	
	private int curPage = 1;
	
	private String id = "";

	/**
	 * ȡ�������б�
	 * 
	 * @return
	 */
	public String list() {
		// ��ѯ����
		int rowsPerPage = Constant.PAGE_SIZE;

		String con = "";
		if (!gysbh.equals("")) {
			con += " and gysmc like'%" + gysbh + "%'";
		}
		if (!state.equals("")) {
			con += " and state='" + state + "'";
		}
		if (!cg_date.equals("")) {
			con += " and cg_date>='" + cg_date + "'";
		}
		if (!cg_date2.equals("")) {
			con += " and cg_date<='" + (cg_date2 + " 23:59:59") + "'";
		}		
		if(orderName.equals("")){
			orderName = "id";
		}
		if(orderType.equals("")){
			orderType = "desc";
		}
		
		con += " order by " + orderName + " " + orderType;

		jhdPage = jhdService.getJhdList(con, curPage, rowsPerPage);

		return "success";
	}

	/**
	 * ����ӽ�����ҳ��
	 * 
	 * @return
	 */
	public String add() {
		userList = userService.getAllEmployeeList();
		storeList = storeService.getAllStoreList();
		id = jhdService.updateJhdID();
		return "success";
	}

	/**
	 * �����������Ϣ ������ز�Ʒ��Ϣ
	 * 
	 * @return
	 */
	public String save() {
		LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		String user_id = info.getUser_id();
		jhd.setCzr(user_id); //��Ӳ�����Ա
		jhdService.saveJhd(jhd, jhdProducts);
		return "success";
	}

	/**
	 * ��ѯ�����������Ϣ
	 * 
	 * @return
	 */
	public String edit() {
		String id = ParameterUtility.getStringParameter(getRequest(), "id", "");
		jhdProducts = jhdService.getJhdProducts(id);
		jhd = (Jhd) jhdService.getJhd(id);
		userList = userService.getAllEmployeeList();
		storeList = storeService.getAllStoreList();
		return "success";
	}

	/**
	 * ���½�������Ϣ ������ز�Ʒ��Ϣ
	 * 
	 * @return
	 */
	public String update() {
		LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		String user_id = info.getUser_id();
		jhd.setCzr(user_id); //��Ӳ�����Ա
		
		jhdService.updateJhd(jhd, jhdProducts);
		return "success";
	}

	/**
	 * ɾ����������Ϣ ������ز�Ʒ��Ϣ
	 * 
	 * @return
	 */
	public String del() {
		String id = ParameterUtility.getStringParameter(getRequest(), "id", "");
		jhdService.delJhd(id);
		return "success";
	}
	
	
	/**
	 * ȡ��������ϸ
	 * @return
	 */
	public String getJhdDesc(){
		if(!id.equals("")){
			jhdProducts = jhdService.getJhdProducts(id);
		}		
		return "success";
	}


	public Jhd getJhd() {
		return jhd;
	}

	public void setJhd(Jhd jhd) {
		this.jhd = jhd;
	}

	public Page getJhdPage() {
		return jhdPage;
	}

	public void setJhdPage(Page jhdPage) {
		this.jhdPage = jhdPage;
	}

	public void setJhdProducts(List jhdProducts) {
		this.jhdProducts = jhdProducts;
	}

	public List getJhdProducts() {
		return jhdProducts;
	}

	public List getProviders() {
		return providers;
	}

	public JhdProduct getJhdProduct() {
		return jhdProduct;
	}

	public void setJhdProduct(JhdProduct jhdProduct) {
		this.jhdProduct = jhdProduct;
	}

	public void setJhdService(JhdService jhdService) {
		this.jhdService = jhdService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public List getUserList() {
		return userList;
	}

	public String getCg_date() {
		return cg_date;
	}

	public void setCg_date(String cg_date) {
		this.cg_date = cg_date;
	}

	public String getGysbh() {
		return gysbh;
	}

	public void setGysbh(String gysbh) {
		this.gysbh = gysbh;
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

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
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

	public JhdService getJhdService() {
		return jhdService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setProviders(List providers) {
		this.providers = providers;
	}

	public void setUserList(List userList) {
		this.userList = userList;
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

	public String getCg_date2() {
		return cg_date2;
	}

	public void setCg_date2(String cg_date2) {
		this.cg_date2 = cg_date2;
	}

}
