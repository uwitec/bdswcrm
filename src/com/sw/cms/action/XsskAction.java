package com.sw.cms.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.model.LoginInfo;
import com.sw.cms.model.Page;
import com.sw.cms.model.Xssk;
import com.sw.cms.service.ClientsService;
import com.sw.cms.service.PosTypeService;
import com.sw.cms.service.SjzdService;
import com.sw.cms.service.SysInitSetService;
import com.sw.cms.service.UserService;
import com.sw.cms.service.XsskService;
import com.sw.cms.util.Constant;
import com.sw.cms.util.DateComFunc;
import com.sw.cms.util.JMath;
import com.sw.cms.util.MoneyUtil;
import com.sw.cms.util.StaticParamDo;
import com.sw.cms.util.StringUtils;

public class XsskAction extends BaseAction {
	
	private XsskService xsskService;
	private UserService userService;
	private ClientsService clientsService;
	private SjzdService sjzdService;
	private PosTypeService posTypeService;
	private SysInitSetService sysInitSetService;
	
	private Page pageXssk;
	private Xssk xssk = new Xssk();
	private List xsskDescs = new ArrayList();
	private List userList = new ArrayList();
	private List clientsList= new ArrayList();
	
	private String id;
	private String sk_date1 = DateComFunc.getToday();;
	private String sk_date2 = DateComFunc.getToday();;
	private String client_name = "";
	
	private String orderName ="";
	private String orderType ="";	
	private int curPage = 1;
	
	private String[] ysfs;
	private List posTypeList = new ArrayList();
	
	
	//销售收款打印
	private String ys_date = "";
	private String skfs = "";
	private String skzh = "";
	private String skje = "";
	private String dxje = "";
	private String foot_name = "";
	private String title_name = "";
	private String jsr = "";
	
	private List xsskList = new ArrayList();
	
	
	/**
	 * 取销售收款列表
	 * @return
	 */
	public String list(){
		int rowsPerPage = Constant.PAGE_SIZE2;
		
		String con = "";
		
		if(!sk_date1.equals("")){
			con += " and sk_date>='" + sk_date1 + "'";
		}
		if(!sk_date2.equals("")){
			con += " and sk_date<='" + sk_date2 + "'";
		}
		if(!client_name.equals("")){
			con += " and b.name like'%" + client_name + "%'";
		}
		if(orderName.equals("")){
			orderName = "id";
		}
		if(orderType.equals("")){
			orderType = "desc";
		}
		
		con += " order by " + orderName + " " + orderType;
		
		pageXssk = xsskService.getXsskList(con, curPage, rowsPerPage);
		
		return "success";
	}
	
	
	/**
	 * 打开添加销售收款页面
	 * @return
	 */
	public String add(){
		
		if(xssk.getClient_name()!=null && !xssk.getClient_name().equals("")){
			xsskDescs = xsskService.getYskByClientId(xssk.getClient_name());
		}
		
		if(xssk.getId()== null || xssk.getId().equals("")){
			xssk.setId(xsskService.updateXsskId());
		}
		ysfs = sjzdService.getSjzdXmxxByZdId("SJZD_FKFS");
		posTypeList = posTypeService.getPosTypeList();
		userList = userService.getAllEmployeeList();
		clientsList=clientsService.getClientList("");
		return "success";
	}
	
	
	/**
	 * 保存销售收款信息
	 * @return
	 */
	public String save(){
		LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		String user_id = info.getUser_id();
		
		xssk.setCzr(user_id);
		
		String tempMsg = xsskService.getExistXsskDesc(xssk, xsskDescs);
		if(!tempMsg.equals("")){
			this.setMsg(tempMsg);
			xsskDescs = xsskService.xsskDescObjToMap(xsskDescs);
			return INPUT;
		}
		
		xsskService.saveXssk(xssk, xsskDescs);
		return "success";
	}
	
	
	/**
	 * 编辑销售收款信息
	 * @return
	 */
	public String edit(){
		xssk = (Xssk)xsskService.getXssk(id);
		xsskDescs = xsskService.getXsskDescs(id);
		
		ysfs = sjzdService.getSjzdXmxxByZdId("SJZD_FKFS");
		posTypeList = posTypeService.getPosTypeList();
		userList = userService.getAllEmployeeList();
		return "success";
	}
	
	
	/**
	 * 更新销售收款信息
	 * @return
	 */
	public String update(){
		LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		String user_id = info.getUser_id();
		
		xssk.setCzr(user_id);
		
		String tempMsg = xsskService.getExistXsskDesc(xssk, xsskDescs);
		if(!tempMsg.equals("")){
			this.setMsg(tempMsg);
			xsskDescs = xsskService.xsskDescObjToMap(xsskDescs);
			return INPUT;
		}
		
		xsskService.updateXssk(xssk, xsskDescs);
		return "success";
	}
	
	
	/**
	 * 删除销售收款信息
	 * @return
	 */
	public String del(){
		xsskService.delXssk(id);
		return "success";
	}
	
	
	/**
	 * 打印销售收款
	 * @return
	 */
	public String printXssk(){
		try{
			
			xssk = (Xssk)xsskService.getXssk(id);
			
			xsskList.add(xssk);
			
			ys_date = StringUtils.nullToStr(xssk.getSk_date());
			skfs = StringUtils.nullToStr(xssk.getSkfs());
			skzh = StaticParamDo.getAccountNameById(xssk.getSkzh());
			skje = JMath.round(xssk.getSkje());
			dxje = MoneyUtil.toChinese(xssk.getSkje() + "");
			client_name = StaticParamDo.getClientNameById(xssk.getClient_name());
			jsr = StaticParamDo.getRealNameById(xssk.getJsr());
			
			Map map = sysInitSetService.getReportSet();			
			if(map != null){
				title_name = StringUtils.nullToStr(map.get("title_name")) + "收款单";
				foot_name = StringUtils.nullToStr(map.get("foot_name"));	
			}
			
			return SUCCESS;
		}catch(Exception e){
			log.error("打印收款单出错，原因：" + e.getMessage());
			e.printStackTrace();
			return ERROR;
		}
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
	public Page getPageXssk() {
		return pageXssk;
	}
	public void setPageXssk(Page pageXssk) {
		this.pageXssk = pageXssk;
	}
	public Xssk getXssk() {
		return xssk;
	}
	public void setXssk(Xssk xssk) {
		this.xssk = xssk;
	}
	public List getXsskDescs() {
		return xsskDescs;
	}
	public void setXsskDescs(List xsskDescs) {
		this.xsskDescs = xsskDescs;
	}
	public XsskService getXsskService() {
		return xsskService;
	}
	public void setXsskService(XsskService xsskService) {
		this.xsskService = xsskService;
	}
	public String getSk_date1() {
		return sk_date1;
	}
	public void setSk_date1(String sk_date1) {
		this.sk_date1 = sk_date1;
	}
	public String getSk_date2() {
		return sk_date2;
	}
	public void setSk_date2(String sk_date2) {
		this.sk_date2 = sk_date2;
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


	public String getClient_name() {
		return client_name;
	}


	public void setClient_name(String client_name) {
		this.client_name = client_name;
	}


	public ClientsService getClientsService() {
		return clientsService;
	}


	public void setClientsService(ClientsService clientsService) {
		this.clientsService = clientsService;
	}


	public List getClientsList() {
		return clientsList;
	}


	public void setClientsList(List clientsList) {
		this.clientsList = clientsList;
	}


	public String[] getYsfs() {
		return ysfs;
	}


	public void setYsfs(String[] ysfs) {
		this.ysfs = ysfs;
	}


	public List getPosTypeList() {
		return posTypeList;
	}


	public void setPosTypeList(List posTypeList) {
		this.posTypeList = posTypeList;
	}


	public SjzdService getSjzdService() {
		return sjzdService;
	}


	public void setSjzdService(SjzdService sjzdService) {
		this.sjzdService = sjzdService;
	}


	public PosTypeService getPosTypeService() {
		return posTypeService;
	}


	public void setPosTypeService(PosTypeService posTypeService) {
		this.posTypeService = posTypeService;
	}


	public String getYs_date() {
		return ys_date;
	}


	public void setYs_date(String ysDate) {
		ys_date = ysDate;
	}


	public String getSkfs() {
		return skfs;
	}


	public void setSkfs(String skfs) {
		this.skfs = skfs;
	}


	public String getSkzh() {
		return skzh;
	}


	public void setSkzh(String skzh) {
		this.skzh = skzh;
	}


	public String getSkje() {
		return skje;
	}


	public void setSkje(String skje) {
		this.skje = skje;
	}


	public String getDxje() {
		return dxje;
	}


	public void setDxje(String dxje) {
		this.dxje = dxje;
	}


	public String getFoot_name() {
		return foot_name;
	}


	public void setFoot_name(String footName) {
		foot_name = footName;
	}


	public String getTitle_name() {
		return title_name;
	}


	public void setTitle_name(String titleName) {
		title_name = titleName;
	}


	public String getJsr() {
		return jsr;
	}


	public void setJsr(String jsr) {
		this.jsr = jsr;
	}


	public SysInitSetService getSysInitSetService() {
		return sysInitSetService;
	}


	public void setSysInitSetService(SysInitSetService sysInitSetService) {
		this.sysInitSetService = sysInitSetService;
	}


	public List getXsskList() {
		return xsskList;
	}


	public void setXsskList(List xsskList) {
		this.xsskList = xsskList;
	}

}
