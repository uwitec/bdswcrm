package com.sw.cms.action;

import java.util.ArrayList;
import java.util.List;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.model.LoginInfo;
import com.sw.cms.model.Page;
import com.sw.cms.model.Pz;
import com.sw.cms.service.ClientsService;
import com.sw.cms.service.PzService;
import com.sw.cms.service.SjzdService;
import com.sw.cms.service.UserService;
import com.sw.cms.util.Constant;

public class PzAction extends BaseAction {
	
	private PzService pzService;
	private UserService userService;
	private SjzdService sjzdService;
	private ClientsService clientsService;
	
	private Page pzPage;
	private Pz pz = new Pz();
	private List userList = new ArrayList();
	private List clientsList= new ArrayList();
	private String[] pzlx;
	
	private String pz_date1 = "";
	private String pz_date2 = "";
	private String type = "";
	
	private String id = "";
	
	private String orderName ="";
	private String orderType ="";
	
	private int curPage = 1;
	
	/**
	 * 列表
	 * @return
	 */
	public String list(){
		int rowsPerPage = Constant.PAGE_SIZE2;

		String con = "";
		
		if(!pz_date1.equals("")){
			con += " and pz_date>='" + pz_date1 + "'";
		}
		if(!pz_date2.equals("")){
			con += " and pz_date<='" + pz_date2 + "'";
		}
		if(!type.equals("")){
			con += " and type='" + type + "'";
		}
		
		if(orderName.equals("")){
			orderName = "id";
		}
		if(orderType.equals("")){
			orderType = "desc";
		}
		
		con += " order by " + orderName + " " + orderType;
		
		pzPage = pzService.getPzList(con, curPage, rowsPerPage);
		
		return "success";
	}
	
	
	/**
	 * 添加页面
	 * @return
	 */
	public String add(){
		userList = userService.getAllEmployeeList();
		id = pzService.updatePzID();
		pzlx = sjzdService.getSjzdXmxxByZdId("SJZD_PZLX");
		clientsList=clientsService.getClientList("");
		return "success";
	}
	
	
	/**
	 * 保存平账信息
	 * @return
	 */
	public String save(){
		LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		String user_id = info.getUser_id();
		pz.setCzr(user_id);
		
		pzService.savePz(pz);
		return "success";
	}
	
	/**
	 * 编辑平账信息
	 * @return
	 */
	public String edit(){
		pz = (Pz)pzService.getPz(id);
		userList = userService.getAllEmployeeList();
		pzlx = sjzdService.getSjzdXmxxByZdId("SJZD_PZLX");
		clientsList=clientsService.getClientList("");
		return "success";
	}
	
	
	/**
	 * 更新平账信息
	 * @return
	 */
	public String update(){
		LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		String user_id = info.getUser_id();
		pz.setCzr(user_id);
		
		pzService.updatePz(pz);
		return "success";
	}
	
	public String del(){
		pzService.delPz(id);
		return "success";
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

	public Pz getPz() {
		return pz;
	}

	public void setPz(Pz pz) {
		this.pz = pz;
	}

	public String getPz_date1() {
		return pz_date1;
	}

	public void setPz_date1(String pz_date1) {
		this.pz_date1 = pz_date1;
	}

	public String getPz_date2() {
		return pz_date2;
	}

	public void setPz_date2(String pz_date2) {
		this.pz_date2 = pz_date2;
	}

	public Page getPzPage() {
		return pzPage;
	}

	public void setPzPage(Page pzPage) {
		this.pzPage = pzPage;
	}

	public PzService getPzService() {
		return pzService;
	}

	public void setPzService(PzService pzService) {
		this.pzService = pzService;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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


	public String[] getPzlx() {
		return pzlx;
	}


	public void setPzlx(String[] pzlx) {
		this.pzlx = pzlx;
	}


	public SjzdService getSjzdService() {
		return sjzdService;
	}


	public void setSjzdService(SjzdService sjzdService) {
		this.sjzdService = sjzdService;
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

}
