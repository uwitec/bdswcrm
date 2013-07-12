package com.sw.cms.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.model.LoginInfo;
import com.sw.cms.model.Lsd;
import com.sw.cms.model.Page;
import com.sw.cms.model.SysUser;
import com.sw.cms.service.LsdService;
import com.sw.cms.service.PosTypeService;
import com.sw.cms.service.ProductKcService;
import com.sw.cms.service.ProductKindService;
import com.sw.cms.service.SjzdService;
import com.sw.cms.service.StoreService;
import com.sw.cms.service.SysInitSetService;
import com.sw.cms.service.UserService;
import com.sw.cms.util.Constant;
import com.sw.cms.util.DateComFunc;
import com.sw.cms.util.JMath;
import com.sw.cms.util.MoneyUtil;
import com.sw.cms.util.ParameterUtility;
import com.sw.cms.util.StaticParamDo;
import com.sw.cms.util.StringUtils;

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
	private ProductKindService productKindService;
 
	
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
	private String isqzxlh_flag = "";  //系统是否要求强制序列号
	private int curPage = 1;
	
	private String id = "";
	private String sp_state = "";
	private String reportstyle="";//报表样式
	private String sd = "0";
	
	private Map tcblMap;
	private String basic_ratio = "0";
	private String out_ratio = "0";
	private String ds_ratio = "0";
	
	private String product_name = "";
	private String product_kind = "";
	private String prop = "";
	private List kindList = new ArrayList();
	
	
	//打印所需参数
	private String client_tel = "";
	private String dept_name = "";
	private String skfs = "";
	private String skzh_name = "";
	private String title_name = "";
	private String foot_name = "";
	private String address = "";
	private String remark = "";
	private String jexj_dx = "";

	
	/**
	 * 零售单列表
	 * @return
	 */
	public String list(){
		
		int rowsPerPage = Constant.PAGE_SIZE;
		reportstyle = userService.getReportStyle();
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
		isqzxlh_flag = userService.getQzxlh();
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
		
		//是否完成初始标志
		iscs_flag = sysInitSetService.getQyFlag();
		isqzxlh_flag = userService.getQzxlh();
		//只有在完成初始工作后再做库存是否满足需求判断
		if(iscs_flag.equals("1")){
			
			//如果零售单状态为已提交需要判断库存是否满足出库需求
			//如果不满足出库需求，则保存零售单，同时返回输入页面，提示用户
			if(lsd.getState().equals("已提交")){
				if(!(lsdService.checkKc(lsd, lsdProducts)).equals("")){
					this.setMsg(lsdService.checkKc(lsd, lsdProducts));
					lsd.setState("已保存");
					lsdService.saveLsd(lsd, lsdProducts);
					
					//页面初始数据
					storeList = storeService.getAllStoreList();
					ysfs = sjzdService.getSjzdXmxxByZdId("SJZD_FKFS");
					posTypeList = posTypeService.getPosTypeList();
					userList = userService.getAllEmployeeList();
					
					return INPUT;
				}
				
				if(isqzxlh_flag.equals("01")){
			 	   if(!(lsdService.checkXlh(lsd, lsdProducts)).equals("")){
						this.setMsg(lsdService.checkXlh(lsd, lsdProducts));
						lsd.setState("已保存");
						lsdService.saveLsd(lsd, lsdProducts);
						
						//页面初始数据
						storeList = storeService.getAllStoreList();
						ysfs = sjzdService.getSjzdXmxxByZdId("SJZD_FKFS");
						posTypeList = posTypeService.getPosTypeList();
						userList = userService.getAllEmployeeList();
						
						return INPUT;
				   }
				}
			}
			
		}
		
		//判断是否存在超低价商品，返回录入界面，提交用户提交审批，或关闭
		if(lsd.getState().equals("已提交") && lsdService.isExistLowLsxj(lsd,lsdProducts)){

			lsd.setState("已保存");
			lsd.setSp_state("1");  //需要审批
			lsdService.saveLsd(lsd, lsdProducts);
			
			//页面初始数据
			storeList = storeService.getAllStoreList();
			ysfs = sjzdService.getSjzdXmxxByZdId("SJZD_FKFS");
			posTypeList = posTypeService.getPosTypeList();
			userList = userService.getAllEmployeeList();
			
			//界面提示审批的标志
			flag = "1";
			
			return INPUT;
		}
		
		lsdService.saveLsd(lsd, lsdProducts);
		return SUCCESS;
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
		isqzxlh_flag = userService.getQzxlh();
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
		isqzxlh_flag = userService.getQzxlh();
		//只有在完成初始工作后再做库存是否满足需求判断
		if(iscs_flag.equals("1")){
			
			//如果零售单状态为已提交需要判断库存是否满足出库需求
			//如果不满足出库需求，则保存零售单，同时返回输入页面，提示用户
			if(lsd.getState().equals("已提交")){
				if(!(lsdService.checkKc(lsd, lsdProducts)).equals("")){
					this.setMsg(lsdService.checkKc(lsd, lsdProducts));
					lsd.setState("已保存");
					lsdService.updateLsd(lsd, lsdProducts);
					
					userList = userService.getAllEmployeeList();
					storeList = storeService.getAllStoreList();
					iscs_flag = sysInitSetService.getQyFlag();  //是否完成初始标志
					ysfs = sjzdService.getSjzdXmxxByZdId("SJZD_FKFS");
					posTypeList = posTypeService.getPosTypeList();
					
					return INPUT;
				}
				if(isqzxlh_flag.equals("01")){
				  if(!(lsdService.checkXlh(lsd, lsdProducts)).equals("")){
					this.setMsg(lsdService.checkXlh(lsd, lsdProducts));
					lsd.setState("已保存");
					lsdService.updateLsd(lsd, lsdProducts);
					
					//页面初始数据
					storeList = storeService.getAllStoreList();
					ysfs = sjzdService.getSjzdXmxxByZdId("SJZD_FKFS");
					posTypeList = posTypeService.getPosTypeList();
					userList = userService.getAllEmployeeList();
					
					return INPUT;
				  }
				}
			}
		}
		
		
		//判断是否存在超低价商品，如存在则审批状态修改为待审批
		if(lsd.getState().equals("已提交") && lsdService.isExistLowLsxj(lsd,lsdProducts)){
			if(flag.equals("1")){
				//提交审批
				lsd.setState("已保存");
				lsd.setSp_state("2");  //待审批
			}else{
				//如果不是提交审批，返回录入界面给用户提示
				lsd.setState("已保存");
				lsd.setSp_state("1");  //需要审批
				lsdService.updateLsd(lsd, lsdProducts);
				
				//页面初始数据
				storeList = storeService.getAllStoreList();
				ysfs = sjzdService.getSjzdXmxxByZdId("SJZD_FKFS");
				posTypeList = posTypeService.getPosTypeList();
				userList = userService.getAllEmployeeList();
				flag = "1";
				
				return INPUT;
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
		
		lsdProducts = lsdService.getLsdProducts(lsd.getId());
		isqzxlh_flag = userService.getQzxlh();
		String msg = lsdService.checkKc(lsd, lsdProducts);
		if(!msg.equals("")){
			this.saveMessage(msg);
			
			userList = userService.getAllEmployeeList();
			storeList = storeService.getAllStoreList();
			ysfs = sjzdService.getSjzdXmxxByZdId("SJZD_FKFS");
			posTypeList = posTypeService.getPosTypeList();
			
			return "input";
		}
		
		if(isqzxlh_flag.equals("01")){
			 msg = lsdService.checkXlh(lsd, lsdProducts);
			 if(!msg.equals(""))
			 {
				   this.saveMessage(msg);
					
					userList = userService.getAllEmployeeList();
					storeList = storeService.getAllStoreList();
					ysfs = sjzdService.getSjzdXmxxByZdId("SJZD_FKFS");
					posTypeList = posTypeService.getPosTypeList();
					
					return "input";
			   }
		}
		
		lsd.setCzr(user_id);
		
		lsd.setSp_state("2");  //修改零售单为待审批状态
		lsd.setState("已保存");      //修改零售单状态为已保存
		
		lsdService.updateLsd(lsd, lsdProducts);
		
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
	 * 打开选择库存商品列表
	 * @return
	 */
	public String selKcProc(){

		int rowsPerPage = 15;
		
		String con = " and a.state='正常'";
		if(!product_name.equals("")){
			product_name = ((product_name.replace("　", " ")).replace(",", "")).replace("，", " ");
			String[] arryCon = product_name.split(" ");
			String tempSql = "";
			if(arryCon != null && arryCon.length > 0){
				con +=" and(";
				for(int i=0;i<arryCon.length;i++){
					if(tempSql.equals("")){
						tempSql = "(a.product_id like '%" + arryCon[i] + "%' or a.product_name like '%" + arryCon[i] + "%' or a.product_xh like '%" + arryCon[i] + "%' or a.sp_txm like '%" + arryCon[i] + "%')";
					}else{
						tempSql = tempSql + " and (a.product_id like '%" + arryCon[i] + "%' or a.product_name like '%" + arryCon[i] + "%' or a.product_xh like '%" + arryCon[i] + "%' or a.sp_txm like '%" + arryCon[i] + "%')";
					}
				}
				con += tempSql + ")";
			}
		}
		if(!product_kind.equals("")){
			con += " and a.product_kind like '" + product_kind + "%'";
		}
		if(!prop.equals("")){
			con += " and prop='" + prop + "'";
		}
		
		productPage = productKcService.getProductKcList(con, curPage, rowsPerPage);
		
		storeList = storeService.getAllStoreList();
		kindList = productKindService.getAllProductKindList();
		
		sd = JMath.round(lsdService.getLssd());
		
		//提成比例
		Map tcblMap = lsdService.getTcbl();
		if(tcblMap != null){
			basic_ratio = JMath.round(tcblMap.get("basic_ratio")==null?0:((Double)tcblMap.get("basic_ratio")).doubleValue());
			out_ratio = JMath.round(tcblMap.get("out_ratio")==null?0:((Double)tcblMap.get("out_ratio")).doubleValue());
		}
		
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
		iscs_flag = sysInitSetService.getQyFlag();
		isqzxlh_flag = userService.getQzxlh();
		//只有在审批通过并且完成初始化后再判断库存是否满足
		if(sp_state.equals("3") && iscs_flag.equals("1")){	
			//审批通过,需要判断库存是否满足
			if(!(lsdService.checkKc(lsd, lsdProducts)).equals("")){
				this.setMsg(lsdService.checkKc(lsd, lsdProducts));
				return "input";
			}
			if(isqzxlh_flag.equals("01")){
				  if(!(lsdService.checkXlh(lsd, lsdProducts)).equals("")){
					  this.setMsg(lsdService.checkXlh(lsd, lsdProducts));
					  return "input";  
				  }
			}
		}
		
		//保存审批结果
		lsdService.saveSp(id, sp_state, user_id);
		
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
	
	
	/**
	 * 打开打印零售单页面 pdf
	 * @return
	 */
	public String printLsd(){
		try{
			lsd = (Lsd)lsdService.getLsd(id);
			
			client_name = StringUtils.nullToStr(lsd.getClient_name()) + "  " + StringUtils.nullToStr(lsd.getLxr());
			client_tel = StringUtils.nullToStr(lsd.getLxdh()) + "  " + StringUtils.nullToStr(lsd.getMobile()) + "  " + StringUtils.nullToStr(lsd.getMail());
			dept_name = StaticParamDo.getDeptNameById(((SysUser)userService.getUser(lsd.getXsry())).getDept());
			xsry_name = StaticParamDo.getRealNameById(lsd.getXsry());
			skfs = StringUtils.nullToStr(lsd.getFkfs());
			skzh_name = StaticParamDo.getAccountNameById(lsd.getSkzh());
			address = StringUtils.nullToStr(lsd.getAddress());
			remark = StringUtils.nullToStr(lsd.getMs());
			jexj_dx = MoneyUtil.toChinese(lsd.getLsdje()+"");
			creatdate = StringUtils.nullToStr(lsd.getCreatdate());
			
			Map map = sysInitSetService.getReportSet();
			
			if(map != null){
				title_name = StringUtils.nullToStr(map.get("title_name")) + "零售单";
				foot_name = StringUtils.nullToStr(map.get("foot_name"));	
			}

			
			lsdProducts = lsdService.getLsdProducts(id);
		}catch(Exception e){
			log.error("打印零售单出错，原因：" + e.getMessage());
			return ERROR;
		}
		return "success";
	}
	
	
	/**
	 * 编辑提成比例
	 * @return
	 */
	public String editTcbl(){
		try{
			tcblMap = lsdService.getTcbl();
		}catch(Exception e){
			log.error("编辑提成比例，原因：" + e.getMessage());
			e.printStackTrace();
			return ERROR;
		}
		return "success";
	}
	
	
	/**
	 * 保存提成比例
	 * @return
	 */
	public String saveTcbl(){
		try{
			lsdService.saveTcbl(basic_ratio, out_ratio, ds_ratio);
		}catch(Exception e){
			log.error("保存提成比例，原因：" + e.getMessage());
			e.printStackTrace();
			return ERROR;
		}
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
	public List getKindList() {
		return kindList;
	}
	public void setKindList(List kindList) {
		this.kindList = kindList;
	}
	public ProductKindService getProductKindService() {
		return productKindService;
	}
	public void setProductKindService(ProductKindService productKindService) {
		this.productKindService = productKindService;
	}
	public String getProp() {
		return prop;
	}
	public void setProp(String prop) {
		this.prop = prop;
	}
	public String getClient_tel() {
		return client_tel;
	}
	public void setClient_tel(String client_tel) {
		this.client_tel = client_tel;
	}
	public String getDept_name() {
		return dept_name;
	}
	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}
	public String getSkfs() {
		return skfs;
	}
	public void setSkfs(String skfs) {
		this.skfs = skfs;
	}
	public String getSkzh_name() {
		return skzh_name;
	}
	public void setSkzh_name(String skzh_name) {
		this.skzh_name = skzh_name;
	}
	public String getFoot_name() {
		return foot_name;
	}
	public void setFoot_name(String foot_name) {
		this.foot_name = foot_name;
	}
	public String getTitle_name() {
		return title_name;
	}
	public void setTitle_name(String title_name) {
		this.title_name = title_name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getJexj_dx() {
		return jexj_dx;
	}
	public void setJexj_dx(String jexj_dx) {
		this.jexj_dx = jexj_dx;
	}
	public String getBasic_ratio() {
		return basic_ratio;
	}
	public void setBasic_ratio(String basic_ratio) {
		this.basic_ratio = basic_ratio;
	}
	public String getOut_ratio() {
		return out_ratio;
	}
	public void setOut_ratio(String out_ratio) {
		this.out_ratio = out_ratio;
	}
	public Map getTcblMap() {
		return tcblMap;
	}
	public void setTcblMap(Map tcblMap) {
		this.tcblMap = tcblMap;
	}
	public String getDs_ratio() {
		return ds_ratio;
	}
	public void setDs_ratio(String ds_ratio) {
		this.ds_ratio = ds_ratio;
	}
	public String getIsqzxlh_flag() {
		return isqzxlh_flag;
	}


	public void setIsqzxlh_flag(String isqzxlh_flag) {
		this.isqzxlh_flag = isqzxlh_flag;
	}
	
	public String getReportstyle() {
		return reportstyle;
	}

	public void setReportstyle(String report_style) {
		this.reportstyle = report_style;
	}
}
