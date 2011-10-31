package com.sw.cms.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.model.LoginInfo;
import com.sw.cms.model.Page;
import com.sw.cms.model.Rkd;
import com.sw.cms.model.RkdProduct;
import com.sw.cms.service.DeptService;
import com.sw.cms.service.EmployeeService;
import com.sw.cms.service.RkdService;
import com.sw.cms.service.SjzdService;
import com.sw.cms.service.SysInitSetService;
import com.sw.cms.service.UserService;
import com.sw.cms.util.Constant;
import com.sw.cms.util.DateComFunc;
import com.sw.cms.util.ParameterUtility;
import com.sw.cms.util.StaticParamDo;
import com.sw.cms.util.StringUtils;

public class RkdAction extends BaseAction {
	
	private RkdService rkdService;
	private UserService userService;
	private DeptService deptService;
	private SjzdService sjzdService;
	private EmployeeService employeeService;
	private SysInitSetService sysInitSetService;
	
	private Rkd rkd = new Rkd();
	private List depts = new ArrayList();
	private String[] positions;
	
	private Page rkdPage;
	private Page ywyEmployeePage;
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
	
	//入库单打印
	private String rkdate;
	private String client_name;
	private String store_name;
	private String jsr;
	private String title_name;
	private String foot_name;
	private String remark;
	private String cgfzr;
	
	
	
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
	 * 保存入库单信息（包括关联商品）
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
	 * 进货单信息，包括关联商品信息
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
		
		if(!rkdService.isJhdSubmit(rkd.getRkd_id())){
			this.saveMessage("入库单状态已经发生变化，可能已入库或者已退回，不能重复操作，请检查！");
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
	 * 删除入库单及相关联商品
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
		
		if(!rkdService.isJhdSubmit(rkd.getRkd_id())){
			this.saveMessage("入库单状态已经发生变化，可能已入库或者已退回，不能重复操作，请检查！");
			rkdProducts = rkdService.getRkdProducts(rkd.getRkd_id());
			storeList = rkdService.getAllStoreList();
			return "input";
		}
		
		rkdService.doTh(rkd);
		return "success";
	}
	
	
	/**
	 * 打印入库单
	 * @return
	 */
	public String printRkd(){
		try{
			rkd = (Rkd)rkdService.getRkd(rkd_id);
			
			rkdate = StringUtils.nullToStr(rkd.getRk_date());
			client_name = StaticParamDo.getClientNameById(rkd.getClient_name());
			store_name = StaticParamDo.getStoreNameById(rkd.getStore_id());
			jsr = StaticParamDo.getRealNameById(rkd.getFzr());
			remark = StringUtils.nullToStr(rkd.getMs());
			cgfzr = StaticParamDo.getRealNameById(rkd.getCgfzr());
			
			Map map = sysInitSetService.getReportSet();			
			if(map != null){
				title_name = StringUtils.nullToStr(map.get("title_name")) + "入库单";
				foot_name = StringUtils.nullToStr(map.get("foot_name"));	
			}
			
			rkdProducts = rkdService.getRkdProducts(rkd_id);
			return SUCCESS;
		}catch(Exception e){
			e.printStackTrace();
			return ERROR;
		}
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

	public String getRkdate() {
		return rkdate;
	}

	public void setRkdate(String rkdate) {
		this.rkdate = rkdate;
	}

	public String getClient_name() {
		return client_name;
	}

	public void setClient_name(String clientName) {
		client_name = clientName;
	}

	public String getStore_name() {
		return store_name;
	}

	public void setStore_name(String storeName) {
		store_name = storeName;
	}

	public String getJsr() {
		return jsr;
	}

	public void setJsr(String jsr) {
		this.jsr = jsr;
	}

	public String getTitle_name() {
		return title_name;
	}

	public void setTitle_name(String titleName) {
		title_name = titleName;
	}

	public String getFoot_name() {
		return foot_name;
	}

	public void setFoot_name(String footName) {
		foot_name = footName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCgfzr() {
		return cgfzr;
	}

	public void setCgfzr(String cgfzr) {
		this.cgfzr = cgfzr;
	}

	public SysInitSetService getSysInitSetService() {
		return sysInitSetService;
	}

	public void setSysInitSetService(SysInitSetService sysInitSetService) {
		this.sysInitSetService = sysInitSetService;
	}

}
