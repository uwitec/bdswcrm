package com.sw.cms.action;

import java.util.ArrayList;
import java.util.List;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.model.Jhd;
import com.sw.cms.model.JhdProduct;
import com.sw.cms.model.LoginInfo;
import com.sw.cms.model.Page;
import com.sw.cms.service.ClientsService;
import com.sw.cms.service.JhdService;
import com.sw.cms.service.ProductKcService;
import com.sw.cms.service.ProductKindService;
import com.sw.cms.service.StoreService;
import com.sw.cms.service.UserService;
import com.sw.cms.util.Constant;
import com.sw.cms.util.DateComFunc;
import com.sw.cms.util.ParameterUtility;

public class JhdAction extends BaseAction {

	
	private JhdService jhdService;
	private UserService userService;
	private StoreService storeService;
	private ClientsService clientsService;
	private ProductKcService productKcService;
	private ProductKindService productKindService;

	private Page jhdPage;

	private Jhd jhd = new Jhd();

	private JhdProduct jhdProduct = new JhdProduct();

	private List jhdProducts = new ArrayList();
	private List providers = new ArrayList();	
	private List userList = new ArrayList();
	private List storeList = new ArrayList();
	private List clientsList= new ArrayList();
	private List kindList = new ArrayList();
	private Page productPage;
	
	
	private String gysbh ="";
	private String state ="";
	private String cg_date = DateComFunc.getToday();
	private String cg_date2 = DateComFunc.getToday();
	private String orderName ="";
	private String orderType ="";
	
	private int curPage = 1;
	
	private String id = "";
	
	private String product_name = "";
	private String product_kind = "";

	/**
	 * 取进货单列表
	 * 
	 * @return
	 */
	public String list() {
		// 查询条件
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
	 * 打开添加进货单页面
	 * 
	 * @return
	 */
	public String add() {
		userList = userService.getAllEmployeeList();
		storeList = storeService.getAllStoreList();
		id = jhdService.updateJhdID();
		clientsList=clientsService.getClientList("");
		return "success";
	}

	/**
	 * 保存进货单信息 包括相关产品信息
	 * 
	 * @return
	 */
	public String save() {
		LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		String user_id = info.getUser_id();
		jhd.setCzr(user_id); //添加操作人员
		jhdService.saveJhd(jhd, jhdProducts);
		return "success";
	}

	/**
	 * 查询进货单相关信息
	 * 
	 * @return
	 */
	public String edit() {
		String id = ParameterUtility.getStringParameter(getRequest(), "id", "");
		jhdProducts = jhdService.getJhdProducts(id);
		jhd = (Jhd) jhdService.getJhd(id);
		storeList = storeService.getAllStoreList();
		return "success";
	}

	/**
	 * 更新进货单信息 包括相关产品信息
	 * 
	 * @return
	 */
	public String update() {
		
		//防止重复提交进货单，如果进货单状态为提交，则提示用户
		if(jhdService.isJhdSubmit(jhd.getId())){
			this.saveMessage("采购订单已提交，不能重复提交，请检查！");
			storeList = storeService.getAllStoreList();
			return "input";
		}
		
		
		LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		String user_id = info.getUser_id();
		jhd.setCzr(user_id); //添加操作人员
		
		jhdService.updateJhd(jhd, jhdProducts);
		return "success";
	}

	/**
	 * 删除进货单信息 包括相关产品信息
	 * 
	 * @return
	 */
	public String del() {
		String id = ParameterUtility.getStringParameter(getRequest(), "id", "");
		jhdService.delJhd(id);
		return "success";
	}
	
	
	/**
	 * 取进货单明细
	 * @return
	 */
	public String getJhdDesc(){
		if(!id.equals("")){
			jhdProducts = jhdService.getJhdProducts(id);
		}		
		return "success";
	}
	
	
	
	public String selJhdProc(){
		
		int rowsPerPage = 15;
		
		String con = " and a.state='正常'";
		if(!product_name.equals("")){
			con += " and (a.product_name like '%" + product_name + "%' or a.product_xh like '%" + product_name + "%')";
		}
		if(!product_kind.equals("")){
			con += " and a.product_kind like '" + product_kind + "%'";
		}
		
		productPage = productKcService.getProductKcList(con, curPage, rowsPerPage);
		storeList = storeService.getAllStoreList();
		kindList = productKindService.getAllProductKindList();
		
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

	public List getKindList() {
		return kindList;
	}

	public void setKindList(List kindList) {
		this.kindList = kindList;
	}

	public String getProduct_kind() {
		return product_kind;
	}

	public void setProduct_kind(String product_kind) {
		this.product_kind = product_kind;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public ProductKcService getProductKcService() {
		return productKcService;
	}

	public void setProductKcService(ProductKcService productKcService) {
		this.productKcService = productKcService;
	}

	public ProductKindService getProductKindService() {
		return productKindService;
	}

	public void setProductKindService(ProductKindService productKindService) {
		this.productKindService = productKindService;
	}

	public Page getProductPage() {
		return productPage;
	}

	public void setProductPage(Page productPage) {
		this.productPage = productPage;
	}

}
