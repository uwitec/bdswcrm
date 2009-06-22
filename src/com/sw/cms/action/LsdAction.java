package com.sw.cms.action;

import java.util.ArrayList;
import java.util.List;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.model.LoginInfo;
import com.sw.cms.model.Lsd;
import com.sw.cms.model.Page;
import com.sw.cms.service.DeptService;
import com.sw.cms.service.EmployeeService;
import com.sw.cms.service.LsdService;
import com.sw.cms.service.PosTypeService;
import com.sw.cms.service.ProductKcService;
import com.sw.cms.service.SjzdService;
import com.sw.cms.service.StoreService;
import com.sw.cms.service.SysInitSetService;
import com.sw.cms.service.UserService;
import com.sw.cms.util.Constant;
import com.sw.cms.util.DateComFunc;
import com.sw.cms.util.ParameterUtility;

/**
 * 零售单处理
 * @author liyt
 *
 */
public class LsdAction extends BaseAction {
	
	private LsdService lsdService;	
	private UserService userService;
	private StoreService storeService;
	private ProductKcService productKcService;
	private SysInitSetService sysInitSetService;
	private SjzdService sjzdService;
	private PosTypeService posTypeService;
 
	
	private List userList = new ArrayList();
	private List storeList = new ArrayList();
	private List posTypeList = new ArrayList();
 
	private Page productPage;
 
	private String[] ysfs;
 
	
	private Page pageLsd;
	private Lsd lsd = new Lsd();
	
	private List lsdProducts = new ArrayList();
	
	private String client_name = "";
	private String creatdate = DateComFunc.getToday();
	private String creatdate2 = DateComFunc.getToday();
	private String state = "";
	private String orderName ="";
	private String orderType ="";
	private String iscs_flag = "";  //系统是否初始完成标志
	private String flag = "";
	private String xsry_name = "";
	
	private int curPage = 1;
	
	private String id = "";
	private String sp_state = "";
	
	private String sd = "0";
	
	/**
	 * 零售单列表
	 * @return
	 */
	public String list(){
		
		int rowsPerPage = Constant.PAGE_SIZE;

		String con = "";
		if(!creatdate.equals("")){
			con += " and a.creatdate>='" + creatdate + "'";
		}
		if(!creatdate2.equals("")){
			con += " and a.creatdate<='" + (creatdate2 + " 23:59:59") + "'";
		}
		if(!client_name.equals("")){
			con += " and a.client_name like'%" + client_name + "%'";
		}
		if(!state.equals("")){
			con += " and a.state='" + state + "'";
		}
		if(!xsry_name.equals("")){
			con += " and b.real_name like '%" + xsry_name + "%'";
		}
		
		if(orderName.equals("")){
			orderName = "id";
		}
		if(orderType.equals("")){
			orderType = "desc";
		}
		
		con += " order by " + orderName + " " + orderType;
		pageLsd = lsdService.getLsdList(con, curPage, rowsPerPage);
		
		return "success";
	}
	
	
	/**
	 * 已成交零售单列表（退货时选择）
	 * @return
	 */
	public String listCjLsd(){
		
		int rowsPerPage = Constant.PAGE_SIZE;

		String con = " and a.state='已提交'";
		
		if(!creatdate.equals("")){
			con += " and a.creatdate>='" + creatdate + "'";
		}
		if(!creatdate2.equals("")){
			con += " and a.creatdate<='" + (creatdate2 + " 23:59:59") + "'";
		}
		if(!client_name.equals("")){
			con += " and a.client_name like'%" + client_name + "%'";
		}
		
		if(orderName.equals("")){
			orderName = "id";
		}
		if(orderType.equals("")){
			orderType = "desc";
		}
		
		con += " order by " + orderName + " " + orderType;
		pageLsd = lsdService.getLsdList(con, curPage, rowsPerPage);
		
		return "success";
	}
	
	
	/**
	 * 打开添加零售单页面
	 * @return
	 */
	public String add(){
		userList = userService.getAllEmployeeList();
		
		//设置零售单编号
		lsd.setId(lsdService.updateLsdId());
		
		storeList = storeService.getAllStoreList();
		
		iscs_flag = sysInitSetService.getQyFlag();  //是否完成初始标志
		
		ysfs = sjzdService.getSjzdXmxxByZdId("SJZD_FKFS");
		
		posTypeList = posTypeService.getPosTypeList();
		
		return "success";
	}
	
	
	/**
	 * 保存零售单信息
	 * @return
	 */
	public String save(){
		LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		String user_id = info.getUser_id();
		lsd.setCzr(user_id);
		
		//判断是否存在超低价商品，如存在则需要审批
		if(lsd.getState().equals("已提交")){
			if(lsdService.isExistLowLsxj(lsd,lsdProducts)){
				
				//如果存在低于零售限价商品
				lsd.setState("已保存");
				lsd.setSp_state("1");  //需要审批
				lsdService.saveLsd(lsd, lsdProducts);
				
				//页面初始数据
				userList = userService.getAllEmployeeList();
				storeList = storeService.getAllStoreList();
				iscs_flag = sysInitSetService.getQyFlag();  //是否完成初始标志
				ysfs = sjzdService.getSjzdXmxxByZdId("SJZD_FKFS");
				posTypeList = posTypeService.getPosTypeList();
				
				return "input";
			}
		}
		
		//是否完成初始标志
		iscs_flag = sysInitSetService.getQyFlag();
		
		//只有在完成初始工作后再做库存是否满足需求判断
		if(iscs_flag.equals("1")){
			
			//如果零售单状态为已提交需要判断库存是否满足出库需求
			//如果不满足出库需求，则保存零售单，同时返回输入页面，提示用户
			if(lsd.getState().equals("已提交")){
				String msg = lsdService.checkKc(lsd, lsdProducts);
				if(!msg.equals("")){
					this.saveMessage(msg);
					lsd.setState("已保存");
					lsdService.saveLsd(lsd, lsdProducts);
					return "input";
				}
			}
			
		}
		
		lsdService.saveLsd(lsd, lsdProducts);
		return "success";
	}
	
	
	/**
	 * 打开修改零售单页面
	 * @return
	 */
	public String edit(){
		
		lsd = (Lsd)lsdService.getLsd(id);
		lsdProducts = lsdService.getLsdProducts(id);
		storeList = storeService.getAllStoreList();
		userList = userService.getAllEmployeeList();
		iscs_flag = sysInitSetService.getQyFlag();
		ysfs = sjzdService.getSjzdXmxxByZdId("SJZD_FKFS");
		posTypeList = posTypeService.getPosTypeList();
		return "success";
	}
	
	
	/**
	 * 更新零售单信息
	 */
	public String update(){
		LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		String user_id = info.getUser_id();
		lsd.setCzr(user_id);
		
		iscs_flag = sysInitSetService.getQyFlag();
		
		id = lsd.getId();
		userList = userService.getAllEmployeeList();
		storeList = storeService.getAllStoreList();
		ysfs = sjzdService.getSjzdXmxxByZdId("SJZD_FKFS");
		
		
		if(lsd.getState().equals("已提交")){
			
			if(!lsd.getSp_state().equals("3")){
				//如果零售单不是审批通过的，则需要判断是否存在超低价商品，如存在则需要审批		
				if(lsdService.isExistLowLsxj(lsd,lsdProducts)){
					
					//如果存在低于零售限价商品
					lsd.setState("已保存");
					lsd.setSp_state("1");  //需要审批
					lsdService.updateLsd(lsd, lsdProducts);
					
					//页面初始数据
					userList = userService.getAllEmployeeList();
					storeList = storeService.getAllStoreList();
					iscs_flag = sysInitSetService.getQyFlag();  //是否完成初始标志
					ysfs = sjzdService.getSjzdXmxxByZdId("SJZD_FKFS");
					posTypeList = posTypeService.getPosTypeList();
					
					return "input";
				}else{
					lsd.setSp_state("0");
				}
			}
		}
		
		//只有在完成初始工作后再做库存是否满足需求判断
		if(iscs_flag.equals("1")){
			
			//如果零售单状态为已提交需要判断库存是否满足出库需求
			//如果不满足出库需求，则保存零售单，同时返回输入页面，提示用户
			if(lsd.getState().equals("已提交")){
				String msg = lsdService.checkKc(lsd, lsdProducts);
				if(!msg.equals("")){
					this.saveMessage(msg);
					
					lsd.setState("已保存");
					lsdService.updateLsd(lsd, lsdProducts);
					return "input";
				}
			}
		}
		
		lsdService.updateLsd(lsd, lsdProducts);
		return "success";
	}
	
	
	/**
	 * 提交审批
	 * @return
	 */
	public String submitInfo(){		
		LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		String user_id = info.getUser_id();
		lsd.setCzr(user_id);
		
		lsd.setSp_state("2");  //修改零售单为待审批状态
		lsd.setState("已保存");      //修改零售单状态为已保存
		
		lsdService.updateLsd(lsd, lsdProducts);
		
		//发送系统消息提醒审批人
		lsdService.saveMsg(lsd.getId(), user_id);
		
		return "success";
		
	}
	
	
	/**
	 * 删除零售单
	 * @return
	 */
	public String del(){
		String id = ParameterUtility.getStringParameter(getRequest(),"id", "");
		lsdService.delLsd(id);
		return "success";
	}
	
	
	
	/**
	 * 打开选择库存产品列表
	 * @return
	 */
	public String selKcProc(){
		String product_xh = ParameterUtility.getStringParameter(getRequest(),"product_xh", "");
		String product_name = ParameterUtility.getStringParameter(getRequest(),"product_name", "");
		String prop = ParameterUtility.getStringParameter(getRequest(),"prop", "");	
		
		int rowsPerPage = 15;
		
		String con = " and state='正常'";
		if(!product_xh.equals("")){
			con += " and product_xh like '%" + product_xh + "%'";
		}
		if(!product_name.equals("")){
			con += " and product_name like '%" + product_name + "%'";
		}
		if(!prop.equals("")){
			con += " and prop='" + prop + "'";
		}
		
		productPage = productKcService.getProductKcList(con, curPage, rowsPerPage);
		storeList = storeService.getAllStoreList();
		
		return "success";
	}
	
	
	
	/**
	 * 打开输入序列号窗口
	 * @return
	 */
	public String importSerial(){
		return "success";
	}
	
	
	/**
	 * 零售单名细
	 * @return
	 */
	public String descLsd(){
		lsdProducts = lsdService.getLsdProducts(id);
		return "success";
	}
	
	
	/**
	 * 零售单审批
	 * @return
	 */
	public String doSp(){
		LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		String user_id = info.getUser_id();
		
		lsd = (Lsd)lsdService.getLsd(id); //零售单
		lsdProducts = lsdService.getLsdProducts(id);
		
		//只有在审批通过并且完成初始化后再判断库存是否满足
		if(sp_state.equals("3") && iscs_flag.equals("1")){	
			//审批通过,需要判断库存是否满足			
			String msg = lsdService.checkKc(lsd, lsdProducts);
			if(!msg.equals("")){
				this.saveMessage(msg);
				return "input";
			}
		}
		
		//保存审批结果
		lsdService.saveSp(id, sp_state, user_id);
		
		//发送系统消息
		Lsd curLsd = (Lsd)lsdService.getLsd(id);
		lsdService.saveMsg(curLsd.getCzr(), user_id, id,sp_state);
		
		return "success";
	}
	
	/**
	 * 编辑零售税点
	 * @return
	 */
	public String editLssd(){
		sd = lsdService.getLssd() + "";
		return "success";
	}
	
	/**
	 * 保存零售税点
	 * @return
	 */
	public String saveLssd(){
		lsdService.saveLssd(sd);
		return "success";
	}
	
	
	public Lsd getLsd() {
		return lsd;
	}
	public void setLsd(Lsd lsd) {
		this.lsd = lsd;
	}
	public LsdService getLsdService() {
		return lsdService;
	}
	public void setLsdService(LsdService lsdService) {
		this.lsdService = lsdService;
	}
	public Page getPageLsd() {
		return pageLsd;
	}
	public void setPageLsd(Page pageLsd) {
		this.pageLsd = pageLsd;
	}
	public List getLsdProducts() {
		return lsdProducts;
	}
	public void setLsdProducts(List lsdProducts) {
		this.lsdProducts = lsdProducts;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	public List getUserList() {
		return userList;
	}
	public String getClient_name() {
		return client_name;
	}
	public void setClient_name(String client_name) {
		this.client_name = client_name;
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
	public UserService getUserService() {
		return userService;
	}
	public void setUserList(List userList) {
		this.userList = userList;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public SjzdService getSjzdService() {
		return sjzdService;
	}
	public void setSjzdService(SjzdService sjzdService) {
		this.sjzdService = sjzdService;
	}
	public String[] getYsfs() {
		return ysfs;
	}
	public void setYsfs(String[] ysfs) {
		this.ysfs = ysfs;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}


	public String getCreatdate2() {
		return creatdate2;
	}


	public void setCreatdate2(String creatdate2) {
		this.creatdate2 = creatdate2;
	}


	public String getXsry_name() {
		return xsry_name;
	}


	public void setXsry_name(String xsry_name) {
		this.xsry_name = xsry_name;
	}


	public String getSp_state() {
		return sp_state;
	}


	public void setSp_state(String sp_state) {
		this.sp_state = sp_state;
	}


	public String getSd() {
		return sd;
	}


	public void setSd(String sd) {
		this.sd = sd;
	}


	public List getPosTypeList() {
		return posTypeList;
	}


	public void setPosTypeList(List posTypeList) {
		this.posTypeList = posTypeList;
	}


	public PosTypeService getPosTypeService() {
		return posTypeService;
	}


	public void setPosTypeService(PosTypeService posTypeService) {
		this.posTypeService = posTypeService;
	}



	
}
