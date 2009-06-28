package com.sw.cms.action;

import java.util.ArrayList;
import java.util.List;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.model.LoginInfo;
import com.sw.cms.service.ClientsService;
import com.sw.cms.service.HpxsHzService;
import com.sw.cms.service.UserService;

/**
 * 货品销售汇总，货品销售毛利汇总
 * 
 * @author liyt
 * 
 */
public class HpxsHzAction extends BaseAction {

	private UserService userService;
	private HpxsHzService hpxsHzService;
	private ClientsService clientsService;
	
	private List user_list = new ArrayList();
	private List productKindList = new ArrayList();
	private List resultList = new ArrayList();
	private List xsdList = new ArrayList();
	private List thdList = new ArrayList();
	private List lsdList = new ArrayList();
	private List clientsList= new ArrayList();

	private String product_kind = "";
	private String product_name = "";
	private String product_xh = "";
	private String start_date = "";
	private String end_date = "";
	private String clientName = "";
	private String xsry_id = "";
	private String product_id = "";

	/**
	 * 显示货品销售汇总条件
	 * 
	 * @return
	 */
	public String showCondition() {
		clientsList=clientsService.getClientList("");
		user_list = userService.getAllEmployeeList();
		return "success";
	}

	/**
	 * 货品销售统计结果
	 * 
	 * @return
	 */
	public String getHpxshzTjResult() {
		resultList = hpxsHzService.getHpxshzTjResult(product_kind,
				product_name, product_xh, start_date, end_date, clientName,
				xsry_id);
		return "success";
	}
	
	
	/**
	 * 供应商销售汇总条件页面
	 * @return
	 */
	public String showGysCondition(){
		return "success";
	}
	
	
	/**
	 * 供应商销售汇总
	 * @return
	 */
	public String getGysXshzResult(){
		LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		String productKind = info.getClient_name();  //分配给供应商的商品类别
		
		resultList = hpxsHzService.getGysHpxshzTjResult(productKind,start_date, end_date);
		
		return "success";
	}
	

	/**
	 * 货品销售毛利汇总结果
	 * 
	 * @return
	 */
	public String getHpxsMlHzResult() {
		resultList = hpxsHzService.getHpxsmlhzTjResult(product_kind,
				product_name, product_xh, start_date, end_date, clientName,
				xsry_id);
		return "success";
	}

	/**
	 * 明细列表
	 * 
	 * @return
	 */
	public String getResultMx() {
		xsdList = hpxsHzService.getXsdList(product_id, start_date, end_date,
				clientName, xsry_id);
		thdList = hpxsHzService.getThdList(product_id, start_date, end_date,
				clientName, xsry_id);
		lsdList = hpxsHzService.getLsdList(product_id, start_date, end_date,
				clientName, xsry_id);
		return "success";
	}

	public List getUser_list() {
		return user_list;
	}

	public void setUser_list(List user_list) {
		this.user_list = user_list;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public List getProductKindList() {
		return productKindList;
	}

	public void setProductKindList(List productKindList) {
		this.productKindList = productKindList;
	}

	public HpxsHzService getHpxsHzService() {
		return hpxsHzService;
	}

	public void setHpxsHzService(HpxsHzService hpxsHzService) {
		this.hpxsHzService = hpxsHzService;
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

	public String getProduct_xh() {
		return product_xh;
	}

	public void setProduct_xh(String product_xh) {
		this.product_xh = product_xh;
	}

	public String getStart_date() {
		return start_date;
	}

	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}

	public String getXsry_id() {
		return xsry_id;
	}

	public void setXsry_id(String xsry_id) {
		this.xsry_id = xsry_id;
	}

	public List getResultList() {
		return resultList;
	}

	public void setResultList(List resultList) {
		this.resultList = resultList;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public List getLsdList() {
		return lsdList;
	}

	public void setLsdList(List lsdList) {
		this.lsdList = lsdList;
	}

	public List getThdList() {
		return thdList;
	}

	public void setThdList(List thdList) {
		this.thdList = thdList;
	}

	public List getXsdList() {
		return xsdList;
	}

	public void setXsdList(List xsdList) {
		this.xsdList = xsdList;
	}

	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
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
