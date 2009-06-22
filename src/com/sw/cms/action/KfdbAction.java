package com.sw.cms.action;

import java.util.ArrayList;
import java.util.List;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.model.Kfdb;
import com.sw.cms.model.LoginInfo;
import com.sw.cms.model.Page;
import com.sw.cms.service.DeptService;
import com.sw.cms.service.EmployeeService;
import com.sw.cms.service.KfdbService;
import com.sw.cms.service.SjzdService;
import com.sw.cms.service.StoreService;
import com.sw.cms.service.SysInitSetService;
import com.sw.cms.service.UserService;
import com.sw.cms.util.Constant;
import com.sw.cms.util.ParameterUtility;

public class KfdbAction extends BaseAction {
	
	private KfdbService kfdbService;
	private UserService userService;
	private StoreService storeService;
	private SysInitSetService sysInitSetService;
	
	private DeptService deptService;
	private SjzdService sjzdService;
	private EmployeeService employeeService;
	
	private String[] positions;
	
	private Page ywyEmployeePage;//修改
	
	private Page pageKfdb;
	private Kfdb kfdb = new Kfdb();
	private List kfdbProducts = new ArrayList();	
	private List storeList = new ArrayList();
	private List userList = new ArrayList();
	private List depts = new ArrayList();
	
	private String id = "";
	private String ck_date1 = "";
	private String ck_date2 = "";
	private String orderName ="";
	private String orderType ="";	
	private int curPage = 1;
	private String iscs_flag = "";  //系统是否初始完成标志
	
	private String msg = "";
	
	
	/**
	 * 库房调拨列表
	 * @return
	 */
	public String list(){
		int rowsPerPage = Constant.PAGE_SIZE;

		String con = "";
		
		//查询条件
		if(!ck_date1.equals("")){
			con += " and ck_date>='" + ck_date1 + "'";
		}
		if(!ck_date2.equals("")){
			con += " and ck_date<='" + ck_date2 + "'";
		}
		
		if(orderName.equals("")){
			orderName = "id";
		}
		if(orderType.equals("")){
			orderType = "desc";
		}
		
		con += " order by " + orderName + " " + orderType;
		
		pageKfdb = kfdbService.getKfdbList(con, curPage, rowsPerPage);
		return "success";
	}
	public String selSqr()
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
	 * 库房调拨添加页面
	 * @return
	 */
	public String add(){
		kfdb.setId(kfdbService.updateKfdbID());
		
		storeList = storeService.getAllStoreList();
		userList = userService.getAllEmployeeList();
		return "success";
	}
	
	
	/**
	 * 保存调拨申请
	 * @return
	 */
	public String save(){
		LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		String user_id = info.getUser_id();
		kfdb.setCzr(user_id);
		
		iscs_flag = sysInitSetService.getQyFlag();
		//只有在系统正式启用后才去判断库存是否满足需求
		if(iscs_flag.equals("1")){
			if(kfdb.getState().equals("已出库")){
				msg = kfdbService.checkKc(kfdb, kfdbProducts);
				if(!msg.equals("")){
					kfdb.setState("已保存");
					kfdbService.saveKfdb(kfdb, kfdbProducts);
					
					return "input";
				}
			}
		}
		
		
		kfdbService.saveKfdb(kfdb, kfdbProducts);
		return "success";
	}
	
	
	/**
	 * 编辑库房调拨
	 * @return
	 */
	public String edit(){
		storeList = storeService.getAllStoreList();
		userList = userService.getAllEmployeeList();
		
		kfdb = (Kfdb)kfdbService.getKfdb(id);
		kfdbProducts = kfdbService.getKfdbProducts(id);
		return "success";
	}
	
	
	/**
	 * 更新库房调拨
	 * @return
	 */
	public String update(){
		LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		String user_id = info.getUser_id();
		kfdb.setCzr(user_id);
		
		iscs_flag = sysInitSetService.getQyFlag();
		//只有在系统正式启用后才去判断库存是否满足需求
		if(iscs_flag.equals("1")){
			if(kfdb.getState().equals("已出库")){
				msg = kfdbService.checkKc(kfdb, kfdbProducts);
				if(!msg.equals("")){
					kfdb.setState("已保存");
					kfdbService.updateKfdb(kfdb, kfdbProducts);
					
					return "input";
				}
			}
		}
		
		kfdbService.updateKfdb(kfdb, kfdbProducts);
		return "success";
	}
	
	
	/**
	 * 删除库房调拨
	 * @return
	 */
	public String del(){
		kfdbService.delKfdb(id);
		return "success";
	}	
	
	
	/**
	 * 库房调拨产品明细
	 * @return
	 */
	public String descKfdb(){
		kfdbProducts = kfdbService.getKfdbProducts(id);
		return "success";
	}
	
	
	public String getCk_date1() {
		return ck_date1;
	}
	public void setCk_date1(String ck_date1) {
		this.ck_date1 = ck_date1;
	}
	public String getCk_date2() {
		return ck_date2;
	}
	public void setCk_date2(String ck_date2) {
		this.ck_date2 = ck_date2;
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
	public Kfdb getKfdb() {
		return kfdb;
	}
	public void setKfdb(Kfdb kfdb) {
		this.kfdb = kfdb;
	}
	public List getKfdbProducts() {
		return kfdbProducts;
	}
	public void setKfdbProducts(List kfdbProducts) {
		this.kfdbProducts = kfdbProducts;
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
	public Page getPageKfdb() {
		return pageKfdb;
	}
	public void setPageKfdb(Page pageKfdb) {
		this.pageKfdb = pageKfdb;
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
	public KfdbService getKfdbService() {
		return kfdbService;
	}
	public void setKfdbService(KfdbService kfdbService) {
		this.kfdbService = kfdbService;
	}


	public String getMsg() {
		return msg;
	}


	public void setMsg(String msg) {
		this.msg = msg;
	}


	public SysInitSetService getSysInitSetService() {
		return sysInitSetService;
	}


	public void setSysInitSetService(SysInitSetService sysInitSetService) {
		this.sysInitSetService = sysInitSetService;
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
	public String getIscs_flag() {
		return iscs_flag;
	}
	public void setIscs_flag(String iscs_flag) {
		this.iscs_flag = iscs_flag;
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
