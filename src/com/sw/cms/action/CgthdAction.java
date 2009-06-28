package com.sw.cms.action;

import java.util.ArrayList;
import java.util.List;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.model.Cgthd;
import com.sw.cms.model.LoginInfo;
import com.sw.cms.model.Page;
import com.sw.cms.service.CgthdService;
import com.sw.cms.service.ClientsService;
import com.sw.cms.service.StoreService;
import com.sw.cms.service.SysInitSetService;
import com.sw.cms.service.UserService;
import com.sw.cms.util.Constant;

public class CgthdAction extends BaseAction {
	
	private CgthdService cgthdService;
	private UserService userService;
	private SysInitSetService sysInitSetService;
	private StoreService storeService;
	private ClientsService clientsService;
	
	private Page cgthdPage;
	private Cgthd cgthd = new Cgthd();
	private List cgthdProducts = new ArrayList();
	private List userList = new ArrayList();
	private List storeList = new ArrayList();
	private List clientsList= new ArrayList();
	
	private String id = "";
	private String th_date1 = "";
	private String th_date2 = "";
	private String provider_name = "";
	
	private int curPage = 1;
	private String orderName = "";
	private String orderType = "";
	private String iscs_flag = "";  //系统是否初始完成标志
	
	
	/**
	 * 采购退货单列表（带分页）
	 * 
	 * @return
	 */
	public String list() {

		int rowsPerPage = Constant.PAGE_SIZE;

		String con = "";
		if (!provider_name.equals("")) {
			con += " and b.name like'%" + provider_name + "%'";
		}
		if (!th_date1.equals("")) {
			con += " and a.th_date>='" + th_date1 + "'";
		}
		if (!th_date2.equals("")) {
			con += " and a.th_date<='" + th_date2 + "'";
		}

		if (orderName.equals("")) {
			orderName = "id";
		}
		if (orderType.equals("")) {
			orderType = "desc";
		}

		con += " order by " + orderName + " " + orderType;

		cgthdPage = cgthdService.getCgthdList(con, curPage, rowsPerPage);
		return "success";
	}

	/**
	 * 打开添加采购退货单页面
	 * 
	 * @return
	 */
	public String add() {
		id = cgthdService.updateCgthdID();
		userList = userService.getAllEmployeeList();
		storeList = storeService.getAllStoreList();
		iscs_flag = sysInitSetService.getQyFlag();
		clientsList=clientsService.getClientList("");
		return "success";
	}

	/**
	 * 保存采购退货单信息
	 * @return
	 */
	public String save() {
		LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		String user_id = info.getUser_id();
		cgthd.setCzr(user_id);
		
		cgthdService.saveCgthd(cgthd, cgthdProducts);
		return "success";
	}

	/**
	 * 编辑或查看采购退货单信息
	 * 
	 * @return
	 */
	public String edit() {
		cgthd = (Cgthd) cgthdService.getCgthd(id);
		cgthdProducts = cgthdService.getCgthdProducts(id);
		userList = userService.getAllEmployeeList();
		storeList = storeService.getAllStoreList();
		iscs_flag = sysInitSetService.getQyFlag();
		clientsList=clientsService.getClientList("");
		return "success";
	}
	
	
	/**
	 * 取退货单明细信息
	 * @return
	 */
	public String descCgthd(){
		cgthdProducts = cgthdService.getCgthdProducts(id);
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
		cgthd.setCzr(user_id);
		
		cgthdService.updateCgthd(cgthd, cgthdProducts);
		return "success";
	}

	/**
	 * 删除采购退货单信息
	 * 
	 * @return
	 */
	public String del() {
		cgthdService.delCgthd(id);
		return "success";
	}
	
	public Page getCgthdPage() {
		return cgthdPage;
	}
	public void setCgthdPage(Page cgthdPage) {
		this.cgthdPage = cgthdPage;
	}
	public List getCgthdProducts() {
		return cgthdProducts;
	}
	public void setCgthdProducts(List cgthdProducts) {
		this.cgthdProducts = cgthdProducts;
	}
	public CgthdService getCgthdService() {
		return cgthdService;
	}
	public void setCgthdService(CgthdService cgthdService) {
		this.cgthdService = cgthdService;
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
	public String getProvider_name() {
		return provider_name;
	}
	public void setProvider_name(String provider_name) {
		this.provider_name = provider_name;
	}
	public String getTh_date1() {
		return th_date1;
	}
	public void setTh_date1(String th_date1) {
		this.th_date1 = th_date1;
	}
	public String getTh_date2() {
		return th_date2;
	}
	public void setTh_date2(String th_date2) {
		this.th_date2 = th_date2;
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

	public Cgthd getCgthd() {
		return cgthd;
	}

	public void setCgthd(Cgthd cgthd) {
		this.cgthd = cgthd;
	}

	public SysInitSetService getSysInitSetService() {
		return sysInitSetService;
	}

	public void setSysInitSetService(SysInitSetService sysInitSetService) {
		this.sysInitSetService = sysInitSetService;
	}

	public String getIscs_flag() {
		return iscs_flag;
	}

	public void setIscs_flag(String iscs_flag) {
		this.iscs_flag = iscs_flag;
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

}
