package com.sw.cms.action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.model.LoginInfo;
import com.sw.cms.model.Page;
import com.sw.cms.model.ProductKcInit;
import com.sw.cms.service.ProductKcInitService;
import com.sw.cms.service.ProductKcService;
import com.sw.cms.service.ProductKindService;
import com.sw.cms.service.StoreService;
import com.sw.cms.service.SysInitSetService;
import com.sw.cms.service.UserService;
import com.sw.cms.util.Constant;

public class ProductKcInitAction extends BaseAction {
	
	private ProductKcInitService productKcInitService;	
	private ProductKcService productKcService;
	private SysInitSetService sysInitSetService;
	private ProductKindService productKindService;
	private StoreService storeService;
	private UserService userService;
	
	private List productKcInitList;
	private ProductKcInit productKcInit = new ProductKcInit();
	private List storeList = new ArrayList();
	private List userList = new ArrayList();
	private Page productKcPage;
	private String store_id = "";
	private String product_xh = "";
	private String product_name = "";
	private String iscs_flag = "0";
	private int curPage = 1;
	private String strTree = "";
	private String product_kind = "";
	private String isExist = "0";
	
	
	/**
	 * 初始列表
	 * @return
	 */
	public String list(){
		productKcInitList = productKcInitService.getProductKcInitList();
		return "success";
	}
	
	/**
	 * 打开库存初始主页面
	 * @return
	 */
	public String initMain(){
		storeList = storeService.getAllStoreList();
		userList = userService.getAllEmployeeList();
		if(!store_id.equals("")){
			productKcInit = productKcInitService.getProductKcInit(store_id);
		}		
		return "success";
	}
	
	
	/**
	 * 保存初始化信息
	 * @return
	 */
	public String save(){
		LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		String user_id = info.getUser_id();
		
		productKcInit.setCzr(user_id);
		
		if(productKcInitService.isExist(productKcInit.getStore_id())){
			this.getSession().setAttribute("errorFlag", "1");
			return "cancel";
		}
		
		productKcInitService.saveProductKcInit(productKcInit);
		return "success";
	}
	
	/**
	 * 初始产品列表
	 * @return
	 */
	public String listProductKc(){
		int rowsPerPage = Constant.PAGE_SIZE;

		String con = "";
		if(!product_kind.equals("")){
			con += " and a.product_kind='" + product_kind + "'";
		}
		if (!product_xh.equals("")) {
			con += " and a.product_xh like '%" + product_xh + "%'";
		}
		if (!product_name.equals("")) {
			con += " and a.product_name like '%" + product_name + "%'";
		}

		productKcPage = productKcService.getProductInitList(con,store_id, curPage, rowsPerPage);
		iscs_flag = sysInitSetService.getQyFlag();

		return "success";
	}
	
	
	public String listKind(){
		List results = productKindService.getAllProductKindList();
		
		Iterator it = results.iterator();
		
		while(it.hasNext()){
			Map map = (Map)it.next();
			
			String id = (String)map.get("id");
			String name = (String)map.get("name");
			String parent_id = (String)map.get("parent_id");
			
			strTree += "add_item(\"" + id + "\",\"" + parent_id + "\",\"" + name + "\",\"\",\"\",\"listInitProductKc.html\",\"right\");\n";
		}
		
		return "success";
	}
	
	
	public ProductKcInit getProductKcInit() {
		return productKcInit;
	}
	public void setProductKcInit(ProductKcInit productKcInit) {
		this.productKcInit = productKcInit;
	}
	public List getProductKcInitList() {
		return productKcInitList;
	}
	public void setProductKcInitList(List productKcInitList) {
		this.productKcInitList = productKcInitList;
	}
	public ProductKcInitService getProductKcInitService() {
		return productKcInitService;
	}
	public void setProductKcInitService(ProductKcInitService productKcInitService) {
		this.productKcInitService = productKcInitService;
	}
	public String getStore_id() {
		return store_id;
	}
	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}

	public int getCurPage() {
		return curPage;
	}

	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}

	public String getIscs_flag() {
		return iscs_flag;
	}

	public void setIscs_flag(String iscs_flag) {
		this.iscs_flag = iscs_flag;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public String getProduct_xh() {
		return product_xh;
	}

	public void setProduct_xh(String product_xh) {
		this.product_xh = product_xh;
	}

	public Page getProductKcPage() {
		return productKcPage;
	}

	public void setProductKcPage(Page productKcPage) {
		this.productKcPage = productKcPage;
	}

	public ProductKcService getProductKcService() {
		return productKcService;
	}

	public void setProductKcService(ProductKcService productKcService) {
		this.productKcService = productKcService;
	}

	public SysInitSetService getSysInitSetService() {
		return sysInitSetService;
	}

	public void setSysInitSetService(SysInitSetService sysInitSetService) {
		this.sysInitSetService = sysInitSetService;
	}

	public ProductKindService getProductKindService() {
		return productKindService;
	}

	public void setProductKindService(ProductKindService productKindService) {
		this.productKindService = productKindService;
	}

	public String getStrTree() {
		return strTree;
	}

	public void setStrTree(String strTree) {
		this.strTree = strTree;
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

	public String getProduct_kind() {
		return product_kind;
	}

	public void setProduct_kind(String product_kind) {
		this.product_kind = product_kind;
	}

	public String getIsExist() {
		return isExist;
	}

	public void setIsExist(String isExist) {
		this.isExist = isExist;
	}

}
