package com.sw.cms.action;

import java.util.ArrayList;
import java.util.List;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.model.Clients;
import com.sw.cms.model.Fxdd;
import com.sw.cms.model.LoginInfo;
import com.sw.cms.model.Page;
import com.sw.cms.model.Xsd;
import com.sw.cms.service.ClientsService;
import com.sw.cms.service.PosTypeService;
import com.sw.cms.service.ProductKcService;
import com.sw.cms.service.ProductService;
import com.sw.cms.service.SjzdService;
import com.sw.cms.service.StoreService;
import com.sw.cms.service.SysInitSetService;
import com.sw.cms.service.UserService;
import com.sw.cms.service.XsdService;
import com.sw.cms.source.SysSource;
import com.sw.cms.util.Constant;
import com.sw.cms.util.DateComFunc;
import com.sw.cms.util.ParameterUtility;

/**
 * 销售订单及分销采购单
 * @author liyt
 *
 */

public class XsdAction extends BaseAction {
	
	private XsdService xsdService;
	private UserService userService;
	private StoreService storeService;
	private ProductKcService productKcService;
	private SysInitSetService sysInitSetService;
	private SjzdService sjzdService;
	private ProductService productService;
	private ClientsService clientsService;
	private PosTypeService posTypeService;
	
	private SysSource sysSource;
	
	private Page pageXsd;
	private Page fxddProductPage;
	private Page productPage;
	private Page pageFxdd;
	private Xsd xsd = new Xsd();
	private Fxdd fxdd = new Fxdd();
	private Clients clients = new Clients();
	
	
	private List xsdProducts = new ArrayList();
	private List fxddProducts = new ArrayList();
	private List storeList = new ArrayList();
	private List posTypeList = new ArrayList();
	private List userList;
	private String[] ysfs;
	private String[] fkfs;
	
	private String id = "";
	private String fxdd_id = "";
	private String client_name = "";
	private String creatdate1 = DateComFunc.getToday();
	private String creatdate2 = DateComFunc.getToday();
	private String fzr = "";
	private String skxs = "";
	private String state = "";
	private String product_xh = "";
	private String product_name = "";
	private String wlzt = "";
	private String iscs_flag = "";  //系统是否初始完成标志
	private String sp_state = "";
	
	private String orderName ="";
	private String orderType ="";
	
	private int curPage = 1;
	
	/**
	 * flag="0" 修改销售单
	 * flag="1" 有超期应收款
	 * flag="2" 有超额应收款
	 */
	private String flag = "";
	private int rowsPerPage = Constant.PAGE_SIZE;
	
	
	/**
	 * 取销售订单列表
	 * @return
	 */
	public String list(){

		String con = "";
		if(!id.equals("")){
			con += " and id='" + id + "'";
		}
		if(!client_name.equals("")){
			con += " and b.name like'%" + client_name + "%'";
		}
		if(!creatdate1.equals("")){
			con += " and a.creatdate>='" + creatdate1 + "'";
		}
		if(!creatdate2.equals("")){
			con += " and a.creatdate<='" + (creatdate2+ " 23:59:59") + "'";
		}
		if(!fzr.equals("")){
			con += " and c.real_name like '%" + fzr + "%'";
		}
		if(!skxs.equals("")){
			con += " and a.skxs='" + skxs + "'";
		}
		if(!state.equals("")){
			con += " and a.state='" + state + "'";
		}
		
		if(orderName.equals("")){
			orderName = "id";
		}
		if(orderType.equals("")){
			orderType = "desc";
		}
		con += " order by " + orderName + " " + orderType;
		
		pageXsd = xsdService.getXsdList(con, curPage, rowsPerPage);
		
		return "success";
	}
	
	
	
	public String listCjXsd(){
		
		String con = " and a.state<>'已保存'";
		if(!client_name.equals("")){
			con += " and b.name like'%" + client_name + "%'";
		}
		if(!creatdate1.equals("")){
			con += " and a.creatdate>='" + creatdate1 + "'";
		}
		if(!creatdate2.equals("")){
			con += " and a.creatdate<='" + (creatdate2+ " 23:59:59") + "'";
		}
		if(orderName.equals("")){
			orderName = "id";
		}
		
		if(orderType.equals("")){
			orderType = "desc";
		}
		con += " order by " + orderName + " " + orderType;
		
		pageXsd = xsdService.getXsdList(con, curPage, rowsPerPage);
		
		return "success";
	}
	
	
	/**
	 * 分销订单列表
	 * @return
	 */
	public String listFxdd(){
		LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		String client_name = info.getClient_name();
		String con = " and client_name='" + client_name + "'";
		if(!creatdate1.equals("")){
			con += " and creatdate>='" + creatdate1 + "'";
		}
		if(!creatdate2.equals("")){
			con += " and creatdate<='" + (creatdate2+ " 23:59:59") + "'";
		}
		if(!state.equals("")){
			con += " and state='" + state + "'";
		}
		if(!wlzt.equals("")){
			con += " and wlzt='" + wlzt + "'";
		}
		
		if(orderName.equals("")){
			orderName = "fxdd_id";
		}
		if(orderType.equals("")){
			orderType = "desc";
		}
		con += " order by " + orderName + " " + orderType;
		
		pageFxdd = xsdService.getFxddList(con, curPage, rowsPerPage);
		
		return "success";
	}
	
	
	/**
	 * 销售单名细信息
	 * @return
	 */
	public String descXsd(){
		xsdProducts = xsdService.getXsdProducts(id);
		
		return "success";
	}
	
	
	/**
	 * 打开添加销售单页面
	 * @return
	 */
	public String add(){
		userList = userService.getAllEmployeeList();
		storeList = storeService.getAllStoreList();
		xsd.setId(xsdService.updateXsdId()); //生成销售单ID
		iscs_flag = sysInitSetService.getQyFlag();
		ysfs = sjzdService.getSjzdXmxxByZdId("SJZD_YSFS");
		fkfs = sjzdService.getSjzdXmxxByZdId("SJZD_FKFS");
		posTypeList = posTypeService.getPosTypeList();
		return "success";
	}
	
	/**
	 * 获取销售单信息
	 * @return
	 */
	public String edit(){
		xsd = (Xsd)xsdService.getXsd(id);
		xsdProducts = xsdService.getXsdProducts(id);
		storeList = storeService.getAllStoreList();
		userList = userService.getAllEmployeeList();
		iscs_flag = sysInitSetService.getQyFlag();
		ysfs = sjzdService.getSjzdXmxxByZdId("SJZD_YSFS");
		posTypeList = posTypeService.getPosTypeList();
		fkfs = sjzdService.getSjzdXmxxByZdId("SJZD_FKFS");
		
		boolean hasLowFxxj = xsdService.checkFxxj(xsdProducts); //是否存在低于分销限价的
		boolean hasCeysk = xsdService.isCeysk(xsd.getClient_name(), xsd.getXsdje()-xsd.getSkje());
		
		if(hasLowFxxj || hasCeysk){
			flag = "2";
		}
		return "success";
	}
	
	
	/**
	 * 获取分销定单信息
	 * @return
	 */
	public String editFxdd(){
		LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		
		String client_id = info.getClient_name();
		clients = (Clients)clientsService.getClient(client_id);
		
		if(!fxdd_id.equals("")){
			fxdd = (Fxdd)xsdService.getFxdd(fxdd_id);
			fxddProducts = xsdService.getXsdProducts(fxdd_id);			
		}
		ysfs = sjzdService.getSjzdXmxxByZdId("SJZD_YSFS");
		return "success";
	}
	
	
	/**
	 * 更新销售单信息
	 * @return
	 */
	public String update(){
		//设置当前操作人
		LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		String user_id = info.getUser_id();
		xsd.setCzr(user_id);
		
		boolean hasLowFxxj = xsdService.checkFxxj(xsdProducts); //是否存在低于分销限价的
		boolean hasCeysk = xsdService.isCeysk(xsd.getClient_name(), xsd.getXsdje()-xsd.getSkje()); //是否存在超额应收款
		boolean hasCqysk = xsdService.isCqysk(xsd.getClient_name());  //是否存在超期应收款
		
		iscs_flag = sysInitSetService.getQyFlag();  //是否完成系统初始化
		
		boolean needSp = false; //单据是否需要审批
		
		//当销售订单审批状态不等于审批通过时，需要去判断是否符有待审批内容
		if(!xsd.getSp_state().equals("3")){
		
			//1：超期款审批；2：超额并且低于限价审批；3：超额审批；4：低于限价审批；	
			if(hasCqysk){
				//超期应收审批
				needSp = true;			
				xsd.setSp_type("1");
			}else if(hasCeysk && hasLowFxxj){
				//超额且低于限价
				needSp = true;
				xsd.setSp_type("2");
			}else if(hasCeysk){
				//超额
				needSp = true;
				xsd.setSp_type("3");
			}else if(hasLowFxxj){
				//低于分销限价
				needSp = true;
				xsd.setSp_type("4");
			}else{
				needSp = false;
				xsd.setSp_type("0");
			}
		}
		
		if(needSp){ //如果销售订单需要审批
			xsd.setSp_state("1");
			xsd.setState("已保存");
			
			userList = userService.getAllEmployeeList();
			ysfs = sjzdService.getSjzdXmxxByZdId("SJZD_YSFS");
			fkfs = sjzdService.getSjzdXmxxByZdId("SJZD_FKFS");
			storeList = storeService.getAllStoreList();
			
			xsdService.updateXsd(xsd, xsdProducts);
			return "input";
		}else{
			if(!xsd.getSp_state().equals("3")){
				xsd.setSp_state("0");
			}
		}
		
		
		//只有在完成初始工作后再做库存是否满足需求判断
		if(iscs_flag.equals("1")){
			//如果销售单状态为已出库则需要判断库存是否满足出库需求
			if(xsd.getState().equals("已出库")){
				String msg = xsdService.checkKc(xsd, xsdProducts);
				if(!msg.equals("")){
					this.saveMessage(msg);
					xsd.setState("已保存");
					xsdService.updateXsd(xsd, xsdProducts);
					
					userList = userService.getAllEmployeeList();
					ysfs = sjzdService.getSjzdXmxxByZdId("SJZD_YSFS");
					fkfs = sjzdService.getSjzdXmxxByZdId("SJZD_FKFS");
					storeList = storeService.getAllStoreList();
					
					return "input";
				}
			}
		}
		
		xsdService.updateXsd(xsd, xsdProducts);
		return "success";
	}
	
	
	/**
	 * 提交审批
	 * @return
	 */
	public String submitXsd(){		
		//设置当前操作人
		LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		String user_id = info.getUser_id();
		xsd.setCzr(user_id);
		
		xsd.setSp_state("2");
		xsd.setState("已保存");
		
		xsdService.updateXsd(xsd, xsdProducts);
		xsdService.saveMsg(xsd.getId(), user_id, xsd.getSp_type());
		
		return "success";
	}
	
	/**
	 * 保存审批结果
	 * @return
	 */
	public String doSp(){
		LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		String user_id = info.getUser_id();
		
		//保存审批结果
		xsdService.saveSp(id, sp_state, user_id);
		
		//发送系统消息
		Xsd curXsd = (Xsd)xsdService.getXsd(id);
		xsdService.saveMsg(curXsd.getCzr(), user_id, id,sp_state);
		
		return "success";
	}
	
	
	/**
	 * 删除销售单信息
	 * @return
	 */
	public String del(){
		xsdService.delXsd(id);
		return "success";
	}
	
	
	/**
	 * 删除分销订单
	 * @return
	 */
	public String delFxdd(){
		xsdService.delFxdd(fxdd_id);
		return "success";
	}
	
	
	/**
	 * 销售单选择库存产品表
	 * @return
	 */
	public String selXsdProc(){
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
	 * <P>更新分销订单信息</P>
	 * <P>如果记录不存在则插入新记录</P>
	 * <P>如果记录存在则更新现有记录</P>
	 * @return
	 */
	public String updateFxdd(){
		LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		String client_name = info.getClient_name();
		fxdd.setClient_name(client_name);
		xsdService.updateFxdd(fxdd, xsdProducts);
		return "success";
	}
	
	
	/**
	 * 分销订单，选择产品列表
	 * @return
	 */
	public String selFxddProc(){
		
		String con = " and a.state='正常'";
		if(!product_xh.equals("")){
			con += " and a.product_xh like '%" + product_xh + "%'";
		}
		if(!product_name.equals("")){
			con += " and a.product_name like '%" + product_name + "%'";
		}

		fxddProductPage = productKcService.getFxddProductKcList(con, curPage, rowsPerPage);
		return "success";
	}
	
	
	public Page getPageXsd() {
		return pageXsd;
	}
	public Xsd getXsd() {
		return xsd;
	}
	public void setXsd(Xsd xsd) {
		this.xsd = xsd;
	}
	public void setXsdService(XsdService xsdService) {
		this.xsdService = xsdService;
	}


	public List getXsdProducts() {
		return xsdProducts;
	}


	public void setXsdProducts(List xsdProducts) {
		this.xsdProducts = xsdProducts;
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

	public UserService getUserService() {
		return userService;
	}


	public XsdService getXsdService() {
		return xsdService;
	}


	public void setPageXsd(Page pageXsd) {
		this.pageXsd = pageXsd;
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


	public String getFlag() {
		return flag;
	}


	public void setFlag(String flag) {
		this.flag = flag;
	}


	public String getCreatdate1() {
		return creatdate1;
	}


	public void setCreatdate1(String creatdate1) {
		this.creatdate1 = creatdate1;
	}


	public String getCreatdate2() {
		return creatdate2;
	}


	public void setCreatdate2(String creatdate2) {
		this.creatdate2 = creatdate2;
	}


	public SysSource getSysSource() {
		return sysSource;
	}


	public void setSysSource(SysSource sysSource) {
		this.sysSource = sysSource;
	}


	public String getFzr() {
		return fzr;
	}


	public void setFzr(String fzr) {
		this.fzr = fzr;
	}


	public String getSkxs() {
		return skxs;
	}


	public void setSkxs(String skxs) {
		this.skxs = skxs;
	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}


	public Page getPageFxdd() {
		return pageFxdd;
	}


	public void setPageFxdd(Page pageFxdd) {
		this.pageFxdd = pageFxdd;
	}

	public Fxdd getFxdd() {
		return fxdd;
	}


	public void setFxdd(Fxdd fxdd) {
		this.fxdd = fxdd;
	}


	public int getRowsPerPage() {
		return rowsPerPage;
	}


	public void setRowsPerPage(int rowsPerPage) {
		this.rowsPerPage = rowsPerPage;
	}


	public String getFxdd_id() {
		return fxdd_id;
	}


	public void setFxdd_id(String fxdd_id) {
		this.fxdd_id = fxdd_id;
	}


	public List getFxddProducts() {
		return fxddProducts;
	}


	public void setFxddProducts(List fxddProducts) {
		this.fxddProducts = fxddProducts;
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


	public Page getFxddProductPage() {
		return fxddProductPage;
	}


	public void setFxddProductPage(Page fxddProductPage) {
		this.fxddProductPage = fxddProductPage;
	}


	public String getWlzt() {
		return wlzt;
	}


	public void setWlzt(String wlzt) {
		this.wlzt = wlzt;
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


	public ProductService getProductService() {
		return productService;
	}


	public void setProductService(ProductService productService) {
		this.productService = productService;
	}



	public String getSp_state() {
		return sp_state;
	}



	public void setSp_state(String sp_state) {
		this.sp_state = sp_state;
	}



	public ClientsService getClientsService() {
		return clientsService;
	}



	public void setClientsService(ClientsService clientsService) {
		this.clientsService = clientsService;
	}



	public Clients getClients() {
		return clients;
	}



	public void setClients(Clients clients) {
		this.clients = clients;
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



	public String[] getFkfs() {
		return fkfs;
	}



	public void setFkfs(String[] fkfs) {
		this.fkfs = fkfs;
	}
	
}
