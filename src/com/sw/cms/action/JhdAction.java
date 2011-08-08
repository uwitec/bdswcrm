package com.sw.cms.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.model.Jhd;
import com.sw.cms.model.JhdProduct;
import com.sw.cms.model.LoginInfo;
import com.sw.cms.model.Page;
import com.sw.cms.model.SysUser;
import com.sw.cms.model.Clients;
import com.sw.cms.model.StoreHouse;
import com.sw.cms.model.ClientsLinkman;
import com.sw.cms.service.ClientsService;
import com.sw.cms.service.JhdService;
import com.sw.cms.service.ProductKcService;
import com.sw.cms.service.ProductKindService;
import com.sw.cms.service.StoreService;
import com.sw.cms.service.SysInitSetService;
import com.sw.cms.service.UserService;
import com.sw.cms.util.Constant;
import com.sw.cms.util.DateComFunc;
import com.sw.cms.util.MoneyUtil;
import com.sw.cms.util.ParameterUtility;
import com.sw.cms.util.StaticParamDo;
import com.sw.cms.util.StringUtils;

public class JhdAction extends BaseAction {

	
	private JhdService jhdService;
	private UserService userService;
	private StoreService storeService;
	private ClientsService clientsService;
	private ProductKcService productKcService;
	private ProductKindService productKindService;
	private SysInitSetService sysInitSetService;
	

	private Page jhdPage;

	private Jhd jhd = new Jhd();

	private JhdProduct jhdProduct = new JhdProduct();
	private Clients clients = new Clients();
	private ClientsLinkman clientsLinkman= new ClientsLinkman();
	private StoreHouse storeHouse= new StoreHouse();
	
	private List jhdProducts = new ArrayList();
	private List providers = new ArrayList();	
	private List userList = new ArrayList();
	private List storeList = new ArrayList();
	private List clientsList= new ArrayList();
	private List kindList = new ArrayList();
	private Page productPage;
	
	
	private String gysbh ="";
	private String state ="";
	private String cg_date = DateComFunc.getToday();
	private String cg_date2 = DateComFunc.getToday();
	private String orderName ="";
	private String orderType ="";
	
	private int curPage = 1;
	
	private String id = "";
	
	private String product_name = "";
	private String product_kind = "";
	
	private String client_id = "";
	
	
	//打印所需参数
	private String creatdate = "";
	private String tel = "";
	private String dept_name = "";
	private String title_name = "";
	private String foot_name = "";
	private String jsr = "";
	private String address = "";
	private String remark = "";
	private String jexj_dx = "";
	private String provider = "";
	private String lxr_tel= "";//供应商的联系人及电话
	private String cz= "";  //供应商的传真号
	private String storedz= "";//收货地址
	private String storelxr_tel= "";//收货人电话
	private String fkrq= "";//付款日期
	/**
	 * 取进货单列表
	 * 
	 * @return
	 */
	public String list() {
		// 查询条件
		int rowsPerPage = Constant.PAGE_SIZE;

		String con = "";
		if (!gysbh.equals("")) {
			con += " and gysmc like'%" + gysbh + "%'";
		}
		if (!state.equals("")) {
			con += " and state='" + state + "'";
		}
		if (!cg_date.equals("")) {
			con += " and cg_date>='" + cg_date + "'";
		}
		if (!cg_date2.equals("")) {
			con += " and cg_date<='" + (cg_date2 + " 23:59:59") + "'";
		}		
		if(orderName.equals("")){
			orderName = "id";
		}
		if(orderType.equals("")){
			orderType = "desc";
		}
		
		con += " order by " + orderName + " " + orderType;

		jhdPage = jhdService.getJhdList(con, curPage, rowsPerPage);

		return "success";
	}

	/**
	 * 打开添加进货单页面
	 * 
	 * @return
	 */
	public String add() {
		userList = userService.getAllEmployeeList();
		storeList = storeService.getAllStoreList();
		id = jhdService.updateJhdID();
		clientsList=clientsService.getClientList("");
		return "success";
	}

	/**
	 * 保存进货单信息 包括相关商品信息
	 * 
	 * @return
	 */
	public String save() {
		LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		String user_id = info.getUser_id();
		jhd.setCzr(user_id); //添加操作人员
		jhdService.saveJhd(jhd, jhdProducts);
		return "success";
	}

	/**
	 * 查询进货单相关信息
	 * 
	 * @return
	 */
	public String edit() {
		String id = ParameterUtility.getStringParameter(getRequest(), "id", "");
		jhdProducts = jhdService.getJhdProducts(id);
		jhd = (Jhd) jhdService.getJhd(id);
		storeList = storeService.getAllStoreList();
		return "success";
	}

	/**
	 * 更新进货单信息 包括相关商品信息
	 * 
	 * @return
	 */
	public String update() {
		
		//防止重复提交进货单，如果进货单状态为提交，则不做任何操作
		if(jhdService.isJhdSubmit(jhd.getId())){
			return "success";
		}
		
		
		LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		String user_id = info.getUser_id();
		jhd.setCzr(user_id); //添加操作人员
		
		jhdService.updateJhd(jhd, jhdProducts);
		return "success";
	}

	/**
	 * 删除进货单信息 包括相关商品信息
	 * 
	 * @return
	 */
	public String del() {
		String id = ParameterUtility.getStringParameter(getRequest(), "id", "");
		jhdService.delJhd(id);
		return "success";
	}
	
	
	/**
	 * 取进货单明细
	 * @return
	 */
	public String getJhdDesc(){
		if(!id.equals("")){
			jhdProducts = jhdService.getJhdProducts(id);
		}		
		return "success";
	}
	
	
	
	public String selJhdProc(){
		
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
		
		productPage = productKcService.getProductKcList(con, curPage, rowsPerPage);
		storeList = storeService.getAllStoreList();
		kindList = productKindService.getAllProductKindList();
		
		return "success";
	}
	
	
	/**
	 * 打印采购订单
	 * @return
	 */
	public String printJhd(){
		try{
			jhd = (Jhd)jhdService.getJhd(id);
			jhdProducts = jhdService.getJhdProducts(id);
			clients=(Clients)jhdService.getClient(id);			
			clientsLinkman=(ClientsLinkman)jhdService.getClientsLinkman(id);
			storeHouse=(StoreHouse)jhdService.getStoreHouse(id);
			
			creatdate = StringUtils.nullToStr(jhd.getCg_date());
			provider = StaticParamDo.getClientNameById(jhd.getGysbh());
			jexj_dx = MoneyUtil.toChinese(jhd.getTotal()+"");
			dept_name = StaticParamDo.getDeptNameById(((SysUser)userService.getUser(jhd.getFzr())).getDept());
			remark = StringUtils.nullToStr(jhd.getMs());
			//jsr = StaticParamDo.getRealNameById(jhd.getFzr());
			
			//经手人处显示电话、手机，标题为电话
			jsr=StaticParamDo.getRealNameById(jhd.getFzr())+" "+((SysUser)userService.getUser(jhd.getFzr())).getGs_phone()+" "+((SysUser)userService.getUser(jhd.getFzr())).getMobile();
			//增加供应商的联系人电话、付款日期、传真号
			lxr_tel= StringUtils.nullToStr(jhd.getKh_lxr()) + " " +StringUtils.nullToStr(jhd.getKh_lxdh());
			
			if(clients != null){
				cz=StringUtils.nullToStr(clients.getCz());
				fkrq=StringUtils.nullToStr(jhd.getYfrq());
			}
			
			//增加收货地址及负责人电话
			if(storeHouse != null){
				storedz=StringUtils.nullToStr(storeHouse.getAddress());
				storelxr_tel=StringUtils.nullToStr(storeHouse.getLxr())+"  "+StringUtils.nullToStr(storeHouse.getLxdh());
			}
			
			Map map = sysInitSetService.getReportSet();
			
			if(map != null){
				title_name = StringUtils.nullToStr(map.get("title_name")) + "采购订单";
				foot_name = StringUtils.nullToStr(map.get("foot_name"));	
			}
			
			return "success";
		}catch(Exception e){
			log.error("打印采购计单出错，原因：" + e);
			return "error";
		}
	}
	
	
	public String selSubmitJhd(){
		// 查询条件
		int rowsPerPage = Constant.PAGE_SIZE;

		String con = " and state='已入库'";
		if(!id.equals("")){
			con += " and id='" + id + "'";
		}
		if (!client_id.equals("")) {
			con += " and gysbh='" + client_id + "'";
		}
		if (!cg_date.equals("")) {
			con += " and cg_date>='" + cg_date + "'";
		}
		if (!cg_date2.equals("")) {
			con += " and cg_date<='" + (cg_date2 + " 23:59:59") + "'";
		}		
		if(orderName.equals("")){
			orderName = "id";
		}
		if(orderType.equals("")){
			orderType = "desc";
		}
		
		con += " order by " + orderName + " " + orderType;

		jhdPage = jhdService.getJhdList(con, curPage, rowsPerPage);

		return "success";
	}


	public Jhd getJhd() {
		return jhd;
	}

	public void setJhd(Jhd jhd) {
		this.jhd = jhd;
	}

	public Page getJhdPage() {
		return jhdPage;
	}

	public void setJhdPage(Page jhdPage) {
		this.jhdPage = jhdPage;
	}

	public void setJhdProducts(List jhdProducts) {
		this.jhdProducts = jhdProducts;
	}

	public List getJhdProducts() {
		return jhdProducts;
	}

	public List getProviders() {
		return providers;
	}

	public JhdProduct getJhdProduct() {
		return jhdProduct;
	}

	public void setJhdProduct(JhdProduct jhdProduct) {
		this.jhdProduct = jhdProduct;
	}

	public Clients getClients() {
		return clients;
	}

	public void setClients(Clients clients) {
		this.clients = clients;
	}
	
	public ClientsLinkman getClientsLinkman() {
		return clientsLinkman;
	}

	public void setClientsLinkman(ClientsLinkman clientsLinkman) {
		this.clientsLinkman = clientsLinkman;
	}
	
	public void setJhdService(JhdService jhdService) {
		this.jhdService = jhdService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public List getUserList() {
		return userList;
	}

	public String getCg_date() {
		return cg_date;
	}

	public void setCg_date(String cg_date) {
		this.cg_date = cg_date;
	}

	public String getGysbh() {
		return gysbh;
	}

	public void setGysbh(String gysbh) {
		this.gysbh = gysbh;
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

	public JhdService getJhdService() {
		return jhdService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setProviders(List providers) {
		this.providers = providers;
	}

	public void setUserList(List userList) {
		this.userList = userList;
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

	public String getCg_date2() {
		return cg_date2;
	}

	public void setCg_date2(String cg_date2) {
		this.cg_date2 = cg_date2;
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

	public List getKindList() {
		return kindList;
	}

	public void setKindList(List kindList) {
		this.kindList = kindList;
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

	public ProductKcService getProductKcService() {
		return productKcService;
	}

	public void setProductKcService(ProductKcService productKcService) {
		this.productKcService = productKcService;
	}

	public ProductKindService getProductKindService() {
		return productKindService;
	}

	public void setProductKindService(ProductKindService productKindService) {
		this.productKindService = productKindService;
	}

	public Page getProductPage() {
		return productPage;
	}

	public void setProductPage(Page productPage) {
		this.productPage = productPage;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCreatdate() {
		return creatdate;
	}

	public void setCreatdate(String creatdate) {
		this.creatdate = creatdate;
	}

	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

	public String getFoot_name() {
		return foot_name;
	}

	public void setFoot_name(String foot_name) {
		this.foot_name = foot_name;
	}

	public String getJexj_dx() {
		return jexj_dx;
	}

	public void setJexj_dx(String jexj_dx) {
		this.jexj_dx = jexj_dx;
	}

	public String getJsr() {
		return jsr;
	}

	public void setJsr(String jsr) {
		this.jsr = jsr;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getTitle_name() {
		return title_name;
	}

	public void setTitle_name(String title_name) {
		this.title_name = title_name;
	}

	public SysInitSetService getSysInitSetService() {
		return sysInitSetService;
	}

	public void setSysInitSetService(SysInitSetService sysInitSetService) {
		this.sysInitSetService = sysInitSetService;
	}

	public String getLxr_tel() {
		return lxr_tel;
	}

	public void setLxr_tel(String lxr_tel) {
		this.lxr_tel = lxr_tel;
	}

	public String getCz() {
		return cz;
	}

	public void setCz(String cz) {
		this.cz = cz;
	}
	
	public String getStoredz() {
		return storedz;
	}

	public void setStoredz(String storedz) {
		this.storedz = storedz;
	}

	public String getStorelxr_tel() {
		return storelxr_tel;
	}

	public void setStorelxr_tel(String storelxr_tel) {
		this.storelxr_tel = storelxr_tel;
	}
	
	public StoreHouse getStoreHouse() {
		return storeHouse;
	}

	public void setStoreHouse(StoreHouse storeHouse) {
		this.storeHouse = storeHouse;
	}
	
	public String getFkrq() {
		return fkrq;
	}

	public void setFkrq(String fkrq) {
		this.fkrq = fkrq;
	}

	public String getClient_id() {
		return client_id;
	}

	public void setClient_id(String clientId) {
		client_id = clientId;
	}
}
