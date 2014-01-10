package com.sw.cms.action;

import java.util.ArrayList;
import java.util.List;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.service.ClientsService;
import com.sw.cms.service.DeptService;
import com.sw.cms.service.ProductKindService;
import com.sw.cms.service.ProductSerialNumCgHzService;
import com.sw.cms.service.UserService;

public class ProductSerialNumCgHzAction extends BaseAction {
	
	private ProductSerialNumCgHzService productSerialNumCgHzService = new ProductSerialNumCgHzService();
	private UserService userService;
	private DeptService deptService;
	private ProductKindService productKindService;
	private ClientsService clientsService;
	
	private String start_date = "";
	private String end_date = "";
	private String product_kind = "";
	private String product_name = "";
	private String dept_id = "";
	private String xsry_name = "";
	private String clientId = "";
	private List userList = new ArrayList();
	private List deptList = new ArrayList();
	private List productKindList = new ArrayList();
	
	private List results = new ArrayList();
	private List clientsList= new ArrayList();
	/**
	 * 打开查询条件列表页面
	 * @return
	 */
	public String showCondition(){
		userList = userService.getAllEmployeeList();
		deptList = deptService.getDepts();
		productKindList = productKindService.getAllProductKindList();
		clientsList=clientsService.getClientList("");
		return "success";
	}
	
	/**
	 * 取得查询结果
	 * @return
	 */
	public String listResults(){
		results = productSerialNumCgHzService.getSerialNumCgList(start_date, end_date, product_kind, product_name, dept_id, xsry_name,clientId);
		return "success";
	}

	public List getDeptList() {
		return deptList;
	}

	public void setDeptList(List deptList) {
		this.deptList = deptList;
	}

	public DeptService getDeptService() {
		return deptService;
	}

	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}

	public List getProductKindList() {
		return productKindList;
	}

	public void setProductKindList(List productKindList) {
		this.productKindList = productKindList;
	}

	public ProductKindService getProductKindService() {
		return productKindService;
	}

	public void setProductKindService(ProductKindService productKindService) {
		this.productKindService = productKindService;
	}

	public ProductSerialNumCgHzService getProductSerialNumCgHzService() {
		return productSerialNumCgHzService;
	}

	public void setProductSerialNumCgHzService(
			ProductSerialNumCgHzService productSerialNumCgHzService) {
		this.productSerialNumCgHzService = productSerialNumCgHzService;
	}

	public List getResults() {
		return results;
	}

	public void setResults(List results) {
		this.results = results;
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


	public String getDept_id() {
		return dept_id;
	}


	public void setDept_id(String dept_id) {
		this.dept_id = dept_id;
	}


	public String getEnd_date() {
		return end_date;
	}


	public void setEnd_date(String end_date) {
		this.end_date = end_date;
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


	public String getStart_date() {
		return start_date;
	}


	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}


	public String getXsry_name() {
		return xsry_name;
	}


	public void setXsry_name(String xsry_name) {
		this.xsry_name = xsry_name;
	}

	public String getClientId() {
		return clientId;
	}


	public void setClientId(String clientId) {
		this.clientId = clientId;
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
