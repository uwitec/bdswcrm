package com.sw.cms.action;

import java.util.ArrayList;
import java.util.List;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.model.Chtj;
import com.sw.cms.model.LoginInfo;
import com.sw.cms.model.Page;
import com.sw.cms.service.ChtjService;
import com.sw.cms.service.ProductKcService;
import com.sw.cms.service.UserService;
import com.sw.cms.util.Constant;
import com.sw.cms.util.ParameterUtility;

public class ChtjAction extends BaseAction {
	
	private ChtjService chtjService;
	private UserService userService;
	
	private Page chtjPage;
	private Chtj chtj;
	
	private List chtjDesc = new ArrayList();
	private List userList = new ArrayList();
	
	private String id = "";
	private String tj_date1 = "";
	private String tj_date2 = "";
	
	private String orderName ="";
	private String orderType ="";
	
	private int curPage = 1;
	
	private Page productPage;
	private ProductKcService productKcService;
	
	
	/**
	 * 取存货调价列表
	 * @return
	 */
	public String list(){
		int rowsPerPage = Constant.PAGE_SIZE;

		String con = "";
		
		if(!tj_date1.equals("")){
			con += " and tj_date>='" + tj_date1 + "'";
		}
		if(!tj_date2.equals("")){
			con += " and tj_date<='" + tj_date2 + "'";
		}
		
		if(orderName.equals("")){
			orderName = "id";
		}
		if(orderType.equals("")){
			orderType = "desc";
		}
		
		con += " order by " + orderName + " " + orderType;
		
		chtjPage = chtjService.getChtjList(con, curPage, rowsPerPage);
		
		return "success";
	}
	
	
	/**
	 * 添加存货调价
	 * @return
	 */
	public String add(){
		id = chtjService.updateChtjID();
		
		userList = userService.getAllEmployeeList();
		return "success";
	}
	
	
	/**
	 * 保存存货调价
	 * @return
	 */
	public String save(){
		LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		String user_id = info.getUser_id();
		chtj.setCzr(user_id);
		
		chtjService.saveChtj(chtj,chtjDesc);
		return "success";
	}
	
	
	/**
	 * 编辑存货调价信息
	 * @return
	 */
	public String edit(){
		chtj = (Chtj)chtjService.getChtj(id);
		chtjDesc = chtjService.getChtjDesc(id);
		
		userList = userService.getAllEmployeeList();
		return "success";
	}
	
	
	/**
	 * 更新存货调价
	 * @return
	 */
	public String update(){
		LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		String user_id = info.getUser_id();
		chtj.setCzr(user_id);
		
		chtjService.updateChtj(chtj, chtjDesc);
		return "success";
	}
	
	
	/**
	 * 删除存货调价信息
	 * @return
	 */
	public String del(){
		chtjService.delChtj(id);
		return "success";
	}
	
	
	/**
	 * 取存货调价明细
	 * @return
	 */
	public String descChtj(){
		chtjDesc = chtjService.getChtjDesc(id);
		return "success";
	}
	
	
	/**
	 * 打开选择库存商品列表
	 * @return
	 */
	public String selProc(){
		int rowsPerPage = Constant.PAGE_SIZE;
		
		String product_xh = ParameterUtility.getStringParameter(getRequest(),"product_xh", "");
		String product_name = ParameterUtility.getStringParameter(getRequest(),"product_name", "");
		
		String con = " and state='正常'";
		if(!product_xh.equals("")){
			con += " and product_xh like '%" + product_xh + "%'";
		}
		if(!product_name.equals("")){
			con += " and product_name like '%" + product_name + "%'";
		}
		
		productPage = productKcService.getProductKcList(con, curPage, rowsPerPage);
		
		return "success";
	}
	
	

	public Chtj getChtj() {
		return chtj;
	}

	public void setChtj(Chtj chtj) {
		this.chtj = chtj;
	}

	public Page getChtjPage() {
		return chtjPage;
	}

	public void setChtjPage(Page chtjPage) {
		this.chtjPage = chtjPage;
	}

	public ChtjService getChtjService() {
		return chtjService;
	}

	public void setChtjService(ChtjService chtjService) {
		this.chtjService = chtjService;
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

	public String getTj_date1() {
		return tj_date1;
	}

	public void setTj_date1(String tj_date1) {
		this.tj_date1 = tj_date1;
	}

	public String getTj_date2() {
		return tj_date2;
	}

	public void setTj_date2(String tj_date2) {
		this.tj_date2 = tj_date2;
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


	public ProductKcService getProductKcService() {
		return productKcService;
	}


	public void setProductKcService(ProductKcService productKcService) {
		this.productKcService = productKcService;
	}


	public Page getProductPage() {
		return productPage;
	}


	public void setProductPage(Page productPage) {
		this.productPage = productPage;
	}

	public List getChtjDesc() {
		return chtjDesc;
	}


	public void setChtjDesc(List chtjDesc) {
		this.chtjDesc = chtjDesc;
	}

}
