package com.sw.cms.action;

import java.util.ArrayList;
import java.util.List;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.model.Jhd;
import com.sw.cms.model.Clients;
import com.sw.cms.model.Page;
import com.sw.cms.model.Cgfpd;
import com.sw.cms.service.AccountsService;
import com.sw.cms.service.CgfpService;
import com.sw.cms.service.ClientsService;
import com.sw.cms.service.UserService;
import com.sw.cms.util.Constant;
import com.sw.cms.util.DateComFunc;
import com.sw.cms.util.ParameterUtility;

/**
 * 采购发票
 * @author jinyanni
 *
 */
public class CgfpAction extends BaseAction {

	private CgfpService cgfpService;
	private UserService userService;
	private AccountsService accountsService;
	private ClientsService clientsService;
	
	private List userList;
	private List accountList;
	
	private Page cgfpPage;
	private Jhd jhd = new Jhd();
	private Cgfpd cgfpd = new Cgfpd();
	private List cgfpDescs = new ArrayList();
	private List providerList;
	private List clientsList= new ArrayList();
	
	private Clients clients;
	private List clientsPayInfos;
	private List clientsLinkMans;
	
	private String gysmc = "";
	private String cg_date1 = "";
	private String cg_date2 = "";
	private String fpstate = "";
	private String remark = "";
	private String gysbh = "";
	
	private String id;
	
	private String orderName ="";
	private String orderType ="";
	
	private int curPage = 1;
	
	
	/**
	 * 采购付款列表
	 * @return
	 */
	public String list(){
		
		int rowsPerPage = Constant.PAGE_SIZE2;
		
		String con = "";
		if(!cg_date1.equals("")){
			con += " and a.cg_date>='" + cg_date1 + "'";
		}
		if(!cg_date2.equals("")){
			con += " and a.cg_date<='" + cg_date2 + "'";
		}
		if(!gysmc.equals("")){
			con += " and b.name like'%" + gysmc + "%'";
		}
		if(fpstate.equals(" "))
		{
			con += "and (a.state='已入库' or a.state='未入库')";
		}
		else if(!fpstate.equals(""))
		{
			con += " and a.state='" + fpstate + "'";
		}
		else
		{
			con += " and (a.state='未入库')";
		}		
		cgfpPage = cgfpService.getCgfps(con, curPage, rowsPerPage);
		
		return "success";
	}
	
		
	/**
	 * 根据供应商编号取未入库的发票信息
	 * @return
	 */
	public String edit(){
		String id = ParameterUtility.getStringParameter(getRequest(), "id", "");
		
	    cgfpd=(Cgfpd)cgfpService.getCgfp(id);
		cgfpDescs = cgfpService.getCgfpDesc(id);
		
		return "success";
	}
	
	/**
	 * 根据供应商编号取采购发票的信息
	 * @return
	 */
	public String view(){
		String id = ParameterUtility.getStringParameter(getRequest(), "id", "");
		
	    cgfpd=(Cgfpd)cgfpService.getCgfp(id);	
	   	cgfpDescs = cgfpService.getCgfpDesc(id);
	   	
		return "success";
	}
	
	/**
	 * 根据供应商编号取采购发票的信息
	 * @return
	 */
	public String viewRk(){
		String id = ParameterUtility.getStringParameter(getRequest(), "id", "");
		
	    cgfpd=(Cgfpd)cgfpService.getCgfp(id);	
	   	cgfpDescs = cgfpService.getCgfpViewDesc(id);
	   	
		return "success";
	}
	
	/**
	 * 更新采购发票信息
	 * @return
	 */
	public String update(){	
		cgfpService.updateCgfp(cgfpd,cgfpDescs);
		return "success";
	}
	
	
	
	public Jhd getJhd() {
		return jhd;
	}

	public void setJhd(Jhd jhd) {
		this.jhd = jhd;
	}

	public Cgfpd getCgfpd() {
		return cgfpd;
	}

	public void setCgfpd(Cgfpd cgfpd) {
		this.cgfpd = cgfpd;
	}
	public List getCgfpDescs() {
		return cgfpDescs;
	}

	public void setCgfpDescs(List cgfpDescs) {
		this.cgfpDescs = cgfpDescs;
	}

	public Page getCgfpPage() {
		return cgfpPage;
	}

	public void setCgfpPage(Page cgfpPage) {
		this.cgfpPage = cgfpPage;
	}
	public CgfpService getCgfpService() {
		return cgfpService;
	}

	public void setCgfpService(CgfpService cgfpService) {
		this.cgfpService = cgfpService;
	}

	public int getCurPage() {
		return curPage;
	}

	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}

	public String getCg_date1() {
		return cg_date1;
	}

	public void setCg_date1(String cg_date1) {
		this.cg_date1 = cg_date1;
	}

	public String getCg_date2() {
		return cg_date2;
	}

	public void setCg_date2(String cg_date2) {
		this.cg_date2 = cg_date2;
	}

	public String getGysmc() {
		return gysmc;
	}

	public void setGysmc(String gysmc) {
		this.gysmc = gysmc;
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

	public List getProviderList() {
		return providerList;
	}

	public void setProviderList(List providerList) {
		this.providerList = providerList;
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

	public AccountsService getAccountsService() {
		return accountsService;
	}

	public void setAccountsService(AccountsService accountsService) {
		this.accountsService = accountsService;
	}
	public List getAccountList() {
		return accountList;
	}
	public void setAccountList(List accountList) {
		this.accountList = accountList;
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

	public String getFpstate() {
		return fpstate;
	}

	public void setFpstate(String fpstate) {
		this.fpstate = fpstate;
	}

	public Clients getClients() {
		return clients;
	}

	public void setClients(Clients clients) {
		this.clients = clients;
	}

	public List getClientsPayInfos() {
		return clientsPayInfos;
	}

	public void setClientsPayInfos(List clientsPayInfos) {
		this.clientsPayInfos = clientsPayInfos;
	}

	public List getClientsLinkMans() {
		return clientsLinkMans;
	}

	public void setClientsLinkMans(List clientsLinkMans) {
		this.clientsLinkMans = clientsLinkMans;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
