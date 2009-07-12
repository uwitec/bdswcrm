package com.sw.cms.action;

import java.util.ArrayList;
import java.util.List;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.model.LoginInfo;
import com.sw.cms.model.Page;
import com.sw.cms.model.Rkd;
import com.sw.cms.model.RkdProduct;
import com.sw.cms.service.DeptService;
import com.sw.cms.service.EmployeeService;
import com.sw.cms.service.RkdService;
import com.sw.cms.service.SjzdService;
import com.sw.cms.service.UserService;
import com.sw.cms.util.Constant;
import com.sw.cms.util.DateComFunc;
import com.sw.cms.util.ParameterUtility;

public class RkdAction extends BaseAction {
	
	private RkdService rkdService;
	private UserService userService;
	private DeptService deptService;
	private SjzdService sjzdService;
	private EmployeeService employeeService;
	private Rkd rkd = new Rkd();
	private List depts = new ArrayList();
	private String[] positions;
	
	private Page rkdPage;
	private Page ywyEmployeePage;//修改
	private List storeList = new ArrayList();
	private List userList = new ArrayList();
	
	private List rkdProducts = new ArrayList();
	private RkdProduct rkdProduct = new RkdProduct();
	
	private String rkd_id = "";
	private String creatdate = DateComFunc.getToday();
	private String creatdate2 = DateComFunc.getToday();
	private String state = "";
	private String store_id = "";
	private int curPage = 1;
	
	private String orderName ="";
	private String orderType ="";
	
	
	
	/**
	 * 查询入库单列表
	 * @return
	 */
	public String list(){

		int rowsPerPage = Constant.PAGE_SIZE;
		
		//查询条件
		String con = "";
		if(!rkd_id.equals("")){
			con += " and rkd_id='" + rkd_id + "'";
		}
		if(!creatdate.equals("")){
			con += " and creatdate>='" + creatdate + "'";
		}
		if(!creatdate2.equals("")){
			con += " and creatdate<='" + (creatdate2 + " 23:59:59") + "'";
		}
		if(!state.equals("")){
			con += " and state='" + state + "'";
		}
		if(!store_id.equals("")){
			con += " and store_id='" + store_id + "'";
		}
		
		if(orderName.equals("")){
			orderName = "rkd_id";
		}
		if(orderType.equals("")){
			orderType = "desc";
		}
		
		con += " order by " + orderName + " " + orderType;
		
		rkdPage = rkdService.getRkdList(con, curPage, rowsPerPage);
		
		storeList = rkdService.getAllStoreList();
		return "success";
	}
	
	public String selCgfzr()
	{
		try
		{
			int curPage = ParameterUtility.getIntParameter(getRequest(), "curPage",1);
			String ywyname=ParameterUtility.getStringParameter(getRequest(), "name","");
			String ywydept=ParameterUtility.getStringParameter(getRequest(), "dept","");
			String ywyposition=ParameterUtility.getStringParameter(getRequest(),"position","");
			int rowsPerPage = 15;
			String con="";
			if(!ywyname.equals(""))
			{
				con+=" and a.real_name like '%"+ywyname+"%'";
			}
			if(!ywydept.equals(""))
			{
				con+=" and b.dept_id='"+ywydept+"'";
			}
			if(!ywyposition.equals(""))
			{
				con+=" and a.position='"+ywyposition+"'";
			}
			ywyEmployeePage=employeeService.getYwyEmployee(con, curPage, rowsPerPage);
			positions = sjzdService.getSjzdXmxxByZdId("SJZD_ZWXX");
			depts = deptService.getDepts();
			return "success";
		}
		catch(Exception e)
		{
			log.error("获取业务员列表  失败原因"+e.getMessage());
			return "error";
		}
		
	}
	
	
	/**
	 * 添加入库单页面
	 * @return
	 */
	public String add(){
		storeList = rkdService.getAllStoreList();
		userList = userService.getAllEmployeeList();
		rkd_id = rkdService.updateRkdId();
		return "success";
	}
	
	
	/**
	 * 保存入库单信息（包括关联产品）
	 * @return
	 */
	public String save(){
		LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		String user_id = info.getUser_id();
		rkd.setCzr(user_id); //添加操作人员
		
		rkdService.saveRkd(rkd, rkdProducts);
		return "success";	
	}
	
	
	/**
	 * 进货单信息，包括关联产品信息
	 * @return
	 */
	public String edit(){
		String rkd_id = ParameterUtility.getStringParameter(getRequest(), "rkd_id", "");
		rkdProducts = rkdService.getRkdProducts(rkd_id);
		
		rkd = (Rkd)rkdService.getRkd(rkd_id);
		
		storeList = rkdService.getAllStoreList();
		return "success";
	}
	
	
	/**
	 * 更新入库单信息
	 * @return
	 */
	public String update(){
		
		if(rkdService.isJhdSubmit(rkd.getRkd_id())){
			this.saveMessage("入库单已提交，不能重复提交，请检查！");
			rkdProducts = rkdService.getRkdProducts(rkd.getRkd_id());
			storeList = rkdService.getAllStoreList();
			return "input";
		}
		
		LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		String user_id = info.getUser_id();
		rkd.setCzr(user_id); //添加操作人员
		
		rkdService.updateRkd(rkd, rkdProducts);
		return "success";
	}

	
	
	/**
	 * 删除入库单及相关联产品
	 * @return
	 */
	public String del(){
		String rkd_id = ParameterUtility.getStringParameter(getRequest(), "rkd_id", "");
		
		rkdService.delRkd(rkd_id);
		return "success";
	}
	
	
	/**
	 * 入库单明细
	 * @return
	 */
	public String descRkd(){
		rkdProducts = rkdService.getRkdProducts(rkd_id);
		return "success";
	}
	
	/**
	 * 打开序列号输入
	 * @return
	 */
	public String importSerial(){
		return "success";
	}
	
	/**
	 * 退回订单
	 * @return
	 */
	public String doTh(){
		
		if(rkdService.isJhdSubmit(rkd.getRkd_id())){
			this.saveMessage("入库单已入库，不能退回，请检查！");
			rkdProducts = rkdService.getRkdProducts(rkd.getRkd_id());
			storeList = rkdService.getAllStoreList();
			return "input";
		}
		
		rkdService.doTh(rkd);
		return "success";
	}
	
	
	public Rkd getRkd() {
		return rkd;
	}
	public void setRkd(Rkd rkd) {
		this.rkd = rkd;
	}
	public void setRkdService(RkdService rkdService) {
		this.rkdService = rkdService;
	}
	public Page getRkdPage() {
		return rkdPage;
	}


	public List getStoreList() {
		return storeList;
	}


	public List getRkdProducts() {
		return rkdProducts;
	}


	public void setRkdProducts(List rkdProducts) {
		this.rkdProducts = rkdProducts;
	}


	public RkdProduct getRkdProduct() {
		return rkdProduct;
	}


	public void setRkdProduct(RkdProduct rkdProduct) {
		this.rkdProduct = rkdProduct;
	}


	public List getUserList() {
		return userList;
	}


	public void setUserService(UserService userService) {
		this.userService = userService;
	}


	public String getCreatdate() {
		return creatdate;
	}


	public void setCreatdate(String creatdate) {
		this.creatdate = creatdate;
	}


	public int getCurPage() {
		return curPage;
	}


	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}


	public String getRkd_id() {
		return rkd_id;
	}


	public void setRkd_id(String rkd_id) {
		this.rkd_id = rkd_id;
	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}


	public String getStore_id() {
		return store_id;
	}


	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}


	public RkdService getRkdService() {
		return rkdService;
	}


	public UserService getUserService() {
		return userService;
	}


	public void setRkdPage(Page rkdPage) {
		this.rkdPage = rkdPage;
	}


	public void setStoreList(List storeList) {
		this.storeList = storeList;
	}


	public void setUserList(List userList) {
		this.userList = userList;
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


	public String getCreatdate2() {
		return creatdate2;
	}


	public void setCreatdate2(String creatdate2) {
		this.creatdate2 = creatdate2;
	}

	public List getDepts() {
		return depts;
	}

	public void setDepts(List depts) {
		this.depts = depts;
	}

	public DeptService getDeptService() {
		return deptService;
	}

	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}

	public EmployeeService getEmployeeService() {
		return employeeService;
	}

	public void setEmployeeService(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	public String[] getPositions() {
		return positions;
	}

	public void setPositions(String[] positions) {
		this.positions = positions;
	}

	public SjzdService getSjzdService() {
		return sjzdService;
	}

	public void setSjzdService(SjzdService sjzdService) {
		this.sjzdService = sjzdService;
	}

	public Page getYwyEmployeePage() {
		return ywyEmployeePage;
	}

	public void setYwyEmployeePage(Page ywyEmployeePage) {
		this.ywyEmployeePage = ywyEmployeePage;
	}

}
