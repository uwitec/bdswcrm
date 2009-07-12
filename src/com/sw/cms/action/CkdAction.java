package com.sw.cms.action;

/**
 * 出库单管理
 * author by liyt
 * 
 * 手动添加出库单必须输入销售单编号
 * 并且出库单中的产品在销售单中必须存在，否则不能添加
 */

import java.util.ArrayList;
import java.util.List;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.model.Ckd;
import com.sw.cms.model.CkdProduct;
import com.sw.cms.model.Clients;
import com.sw.cms.model.LoginInfo;
import com.sw.cms.model.Page;
import com.sw.cms.service.CkdService;
import com.sw.cms.service.ClientsService;
import com.sw.cms.service.ProductKcService;
import com.sw.cms.service.SjzdService;
import com.sw.cms.service.StoreService;
import com.sw.cms.service.SysInitSetService;
import com.sw.cms.service.UserService;
import com.sw.cms.service.XsdService;
import com.sw.cms.util.Constant;
import com.sw.cms.util.DateComFunc;
import com.sw.cms.util.ParameterUtility;

public class CkdAction extends BaseAction {
	
	private CkdService ckdService;
	private UserService userService;
	private ProductKcService productKcService;
	private StoreService storeService;
	private XsdService xsdService;
	private SysInitSetService sysInitSetService;
	private SjzdService sjzdService;
	private ClientsService clientsService;
	
	
	private Ckd ckd = new Ckd();
	private Page ckdPage;
	private List storeList = new ArrayList();
	private String[] ysfs;
	
	private List ckdProducts = new ArrayList();
	private List userList;
	private Clients client = new Clients();
	
	private Page productPage;
	
	private String msg = "";
	private String isHasAuth;
	
	private String ckd_id = "";
	private String creatdate = DateComFunc.getToday();
	private String creatdate2 = DateComFunc.getToday();
	private String state = "";
	private String orderName ="";
	private String orderType ="";
	
	private String iscs_flag = "";  //系统是否初始完成标志
	
	private int curPage = 1;
	
	/**
	 * 取出库单列表（带分页）
	 * @return
	 */
	public String list(){
		
		int rowsPerPage = Constant.PAGE_SIZE;

		String con = "";
		
		if(!ckd_id.equals("")){
			con += " and ckd_id='" + ckd_id + "'";
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
		
		if(orderName.equals("")){
			orderName = "ckd_id";
		}
		if(orderType.equals("")){
			orderType = "desc";
		}
		
		con += " order by " + orderName + " " + orderType;
		
		ckdPage = ckdService.getCkdList(con, curPage, rowsPerPage);
		storeList = ckdService.getStoreList();
		
		return "success";
	}
	
	
	/**
	 * 打开添加出库单页面
	 * @return
	 */
	public String add(){
		storeList = ckdService.getStoreList();
		userList = userService.getAllEmployeeList();
		iscs_flag = sysInitSetService.getQyFlag();
		ckd_id = ckdService.updateCkdId();
		return "success";
	}
	
	
	/**
	 * 保存出库单信息，包括关联产品信息
	 * @return
	 */
	public String save(){
		 
		LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		String user_id = info.getUser_id();
		ckd.setCzr(user_id);
		
		if(ckd.getXsd_id().indexOf("CGTH") == -1){ //不是采购退货出库时需要判断
			if(ckd.getXsd_id().equals("")){
				msg = "对应销售单不存在，或销售单编号为空，出库单无法保存！";
				return "input";
			}else{
				if(!xsdService.isHasXsdByID(ckd.getXsd_id())){  //用户添写的销售单不存在
					msg = "对应销售单不存在，出库单无法保存，请检查！";
					return "input";
				}else{  //如果存在则去判断产品是否存在
					if(ckdProducts != null && ckdProducts.size()>0){
						for(int i=0;i<ckdProducts.size();i++){
							CkdProduct ckdProduct = (CkdProduct)ckdProducts.get(i);
							if(ckdProduct.getProduct_id() != null && !ckdProduct.getProduct_id().equals("")){
								if(!xsdService.isHasXsdProduct(ckd.getXsd_id(), ckdProduct.getProduct_id())){
									msg = "出库单产品类别与对应销售单不同，出库单无法保存，请检查！";
									return "input";
								}
							}
						}
					}				
				}
			}
		}
		
		iscs_flag = sysInitSetService.getQyFlag();
		
		//只有在系统正式启用后才去判断库存是否满足需求
		if(iscs_flag.equals("1")){
			if(ckd.getState().equals("已出库")){
				msg = ckdService.checkKc(ckd, ckdProducts);
				if(!msg.equals("")){
					ckd.setState("待出库");
					ckdService.saveCkd(ckd,ckdProducts);
					return "input";
				}
			}
		}
		
		ckdService.saveCkd(ckd,ckdProducts);		
		return "success";
	}
	
	
	
	/**
	 * 编辑页面
	 * @return
	 */
	public String edit(){
		ckd = (Ckd)ckdService.getCkd(ckd_id);
		ckdProducts = ckdService.getCkdProducts(ckd_id);
		iscs_flag = sysInitSetService.getQyFlag();
		userList = userService.getAllEmployeeList();
		storeList = ckdService.getStoreList();
		ysfs = sjzdService.getSjzdXmxxByZdId("SJZD_YSFS");
		
		if(clientsService.getClient(ckd.getClient_name()) != null){
			client = (Clients)clientsService.getClient(ckd.getClient_name());
		}
		
		return "success";
	}
	
	
	/**
	 * 更新出库单信息
	 * @return
	 */
	public String update(){
		 
		LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		String user_id = info.getUser_id();
		ckd.setCzr(user_id);
		
		if(ckd.getXsd_id().indexOf("CGTH") == -1){ //不是采购退货出库时需要判断		
			if(ckd.getXsd_id().equals("")){
				msg = "对应销售单不存在，或销售单编号为空，出库单无法保存！";
				
				ckdProducts = ckdService.getCkdProducts(ckd.getCkd_id());
				iscs_flag = sysInitSetService.getQyFlag();
				userList = userService.getAllEmployeeList();
				storeList = ckdService.getStoreList();
				ysfs = sjzdService.getSjzdXmxxByZdId("SJZD_YSFS");
				
				if(clientsService.getClient(ckd.getClient_name()) != null){
					client = (Clients)clientsService.getClient(ckd.getClient_name());
				}
				
				this.saveMessage(msg);
				
				return "input";
			}else{
				if(!xsdService.isHasXsdByID(ckd.getXsd_id())){  //用户添写的销售单不存在
					msg = "对应销售单不存在，出库单无法保存，请检查！";
					
					ckdProducts = ckdService.getCkdProducts(ckd.getCkd_id());
					iscs_flag = sysInitSetService.getQyFlag();
					userList = userService.getAllEmployeeList();
					storeList = ckdService.getStoreList();
					ysfs = sjzdService.getSjzdXmxxByZdId("SJZD_YSFS");
					
					if(clientsService.getClient(ckd.getClient_name()) != null){
						client = (Clients)clientsService.getClient(ckd.getClient_name());
					}
					
					this.saveMessage(msg);
					
					return "input";
				}else{  //如果存在则去判断产品是否存在
					if(ckdProducts != null && ckdProducts.size()>0){
						for(int i=0;i<ckdProducts.size();i++){
							CkdProduct ckdProduct = (CkdProduct)ckdProducts.get(i);
							if(ckdProduct.getProduct_id() != null && !ckdProduct.getProduct_id().equals("")){
								if(!xsdService.isHasXsdProduct(ckd.getXsd_id(), ckdProduct.getProduct_id())){
									msg = "出库单产品类别与对应销售单不同，出库单无法保存，请检查！";
									
									ckdProducts = ckdService.getCkdProducts(ckd.getCkd_id());
									iscs_flag = sysInitSetService.getQyFlag();
									userList = userService.getAllEmployeeList();
									storeList = ckdService.getStoreList();
									ysfs = sjzdService.getSjzdXmxxByZdId("SJZD_YSFS");
									
									if(clientsService.getClient(ckd.getClient_name()) != null){
										client = (Clients)clientsService.getClient(ckd.getClient_name());
									}
									
									this.saveMessage(msg);
									return "input";
								}
							}
						}
					}				
				}
			}
		}
		
		iscs_flag = sysInitSetService.getQyFlag();  //系统是否完成标志
		//只有在系统正式启用后才去判断库存是否满足需求
		if(iscs_flag.equals("1")){
			 
			if(ckd.getState().equals("已出库")){
				 
				msg = ckdService.checkKc(ckd, ckdProducts);
				if(!msg.equals("")){
					ckd.setState("待出库");
					ckdService.updateCkd(ckd, ckdProducts);
					
					ckdProducts = ckdService.getCkdProducts(ckd.getCkd_id());
					iscs_flag = sysInitSetService.getQyFlag();
					userList = userService.getAllEmployeeList();
					storeList = ckdService.getStoreList();
					ysfs = sjzdService.getSjzdXmxxByZdId("SJZD_YSFS");
					
					if(clientsService.getClient(ckd.getClient_name()) != null){
						client = (Clients)clientsService.getClient(ckd.getClient_name());
					}
					
					this.saveMessage(msg);
					
					return "input";
				}
			}
		}
		
		//判断出库单是否已经提交
		if(ckdService.isCkdSubmit(ckd.getCkd_id())){
			this.saveMessage("出库单已经出库，不能重复出库，请检查！");
			
			ckdProducts = ckdService.getCkdProducts(ckd.getCkd_id());
			iscs_flag = sysInitSetService.getQyFlag();
			userList = userService.getAllEmployeeList();
			storeList = ckdService.getStoreList();
			ysfs = sjzdService.getSjzdXmxxByZdId("SJZD_YSFS");
			
			if(clientsService.getClient(ckd.getClient_name()) != null){
				client = (Clients)clientsService.getClient(ckd.getClient_name());
			}
			
			return "input";
		}
		
		ckdService.updateCkd(ckd, ckdProducts);		
		return "success";
	}
	
	
	/**
	 * 删除出库单信息
	 * @return
	 */
	public String del(){
		String ckd_id = ParameterUtility.getStringParameter(getRequest(),"ckd_id", "");
		ckdService.delCkd(ckd_id);
		return "success";
	}
	
	
	/**
	 * 退回订单
	 * @return
	 */
	public String doTh(){
		//判断出库单是否已经提交
		if(ckdService.isCkdSubmit(ckd.getCkd_id())){
			this.saveMessage("出库单已经出库，不能退回，请检查！");
			
			ckdProducts = ckdService.getCkdProducts(ckd.getCkd_id());
			iscs_flag = sysInitSetService.getQyFlag();
			userList = userService.getAllEmployeeList();
			storeList = ckdService.getStoreList();
			ysfs = sjzdService.getSjzdXmxxByZdId("SJZD_YSFS");
			
			if(clientsService.getClient(ckd.getClient_name()) != null){
				client = (Clients)clientsService.getClient(ckd.getClient_name());
			}
			
			return "input";
		}
		ckdService.doTh(ckd);
		return "success";
	}
	
	
	/**
	 * 选择库存产品
	 * @return
	 */
	public String selCkdProc(){
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
	 * 出库单相关产品明细
	 * @return
	 */
	public String descCkd(){
		ckdProducts = ckdService.getCkdProducts(ckd_id);
		return "success";
	}
	
	
	public Ckd getCkd() {
		return ckd;
	}
	public void setCkd(Ckd ckd) {
		this.ckd = ckd;
	}
	public Page getCkdPage() {
		return ckdPage;
	}
	public void setCkdService(CkdService ckdService) {
		this.ckdService = ckdService;
	}
	public List getStoreList() {
		return storeList;
	}


	public List getCkdProducts() {
		return ckdProducts;
	}


	public void setCkdProducts(List ckdProducts) {
		this.ckdProducts = ckdProducts;
	}


	public String getMsg() {
		return msg;
	}


	public void setMsg(String msg) {
		this.msg = msg;
	}


	public String getIsHasAuth() {
		return isHasAuth;
	}


	public void setIsHasAuth(String isHasAuth) {
		this.isHasAuth = isHasAuth;
	}


	public void setUserService(UserService userService) {
		this.userService = userService;
	}


	public List getUserList() {
		return userList;
	}

	public String getCkd_id() {
		return ckd_id;
	}


	public void setCkd_id(String ckd_id) {
		this.ckd_id = ckd_id;
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

	public CkdService getCkdService() {
		return ckdService;
	}


	public UserService getUserService() {
		return userService;
	}


	public void setCkdPage(Page ckdPage) {
		this.ckdPage = ckdPage;
	}


	public void setStoreList(List storeList) {
		this.storeList = storeList;
	}


	public void setUserList(List userList) {
		this.userList = userList;
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


	public StoreService getStoreService() {
		return storeService;
	}


	public void setStoreService(StoreService storeService) {
		this.storeService = storeService;
	}


	public XsdService getXsdService() {
		return xsdService;
	}


	public void setXsdService(XsdService xsdService) {
		this.xsdService = xsdService;
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


	public String getCreatdate() {
		return creatdate;
	}


	public void setCreatdate(String creatdate) {
		this.creatdate = creatdate;
	}


	public String getCreatdate2() {
		return creatdate2;
	}


	public void setCreatdate2(String creatdate2) {
		this.creatdate2 = creatdate2;
	}


	public String[] getYsfs() {
		return ysfs;
	}


	public void setYsfs(String[] ysfs) {
		this.ysfs = ysfs;
	}


	public SjzdService getSjzdService() {
		return sjzdService;
	}


	public void setSjzdService(SjzdService sjzdService) {
		this.sjzdService = sjzdService;
	}


	public ClientsService getClientsService() {
		return clientsService;
	}


	public void setClientsService(ClientsService clientsService) {
		this.clientsService = clientsService;
	}


	public Clients getClient() {
		return client;
	}


	public void setClient(Clients client) {
		this.client = client;
	}

}
