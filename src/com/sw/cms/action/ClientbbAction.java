package com.sw.cms.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.model.Clients;
import com.sw.cms.model.ClientsFollow;
import com.sw.cms.model.ClientsLinkman;
import com.sw.cms.model.Page;
import com.sw.cms.service.ClientsService;
import com.sw.cms.service.SjzdService;
import com.sw.cms.service.UserService;
import com.sw.cms.util.Constant;

public class ClientbbAction extends BaseAction {

	private ClientsService clientsService;
	private SjzdService sjzdService;
	private UserService userService;

	private Page clientsPage;
	private Page clientWlywPage;

	private Clients client = new Clients();
	
	private List userList = new ArrayList();

	private String name = "";
	private String type = "";
	private String lxr = "";
	private String khjl = "";
	
	private String[] wldwlx;
	private String[] lxrnld;
	private String orderName = "";
	private String orderType = "";

	private int curPage = 1;

	private String id = "";

	private List descClientLinkman = new ArrayList();
	private List clientsFollow = new ArrayList();
	private List clientsPayInfos = new ArrayList();

	private ClientsLinkman linkman = new ClientsLinkman();
	private ClientsFollow follow = new ClientsFollow();

	private String clients_id;
	private String client_con = "";
	private String clientsName = "";
	private String clientsTips = "";
	private String clientRegInfoText = "";

	private Map clientQcMap;
	private Map clientWlMap;


	/**
	 * 返回列表（带分页）
	 * 
	 * @return
	 */
	public String list() {

		int rowsPerPage = Constant.PAGE_SIZE2;

		String con = "";
      
		if (!name.equals("")) {
			con += " and a.name like'%" + name + "%'";
		}
		if (!lxr.equals("")) {
			con += " and a.lxr like'%" + lxr + "%'";
		}
		if (!khjl.equals("")) {
			con += " and b.real_name like '%" + khjl + "%'";
		}

		if (orderName.equals("")) {
			orderName = "id";
		}
		if (orderType.equals("")) {
			orderType = "desc";
		}

		con += " order by " + orderName + " " + orderType;

		clientsPage = clientsService.getClients(con, curPage, rowsPerPage);
		userList = userService.getAllEmployeeList();

		return "success";
	}

	
	/**
	 * 打开客户视图
	 * 
	 * @return
	 */
	public String listView() throws Exception {
		 
		try {
			client = (Clients) clientsService.getClient(id);// 客户实体
			clientWlywPage = clientsService.getClientWlyw(id);
			descClientLinkman = clientsService.getClientsLinkman(id);// 联系人
			clientsFollow = clientsService.getClientsFollow(id);// 跟进记录
			return "success";
		} catch (Exception e) {
			log.error("打开客户视图 错误原因" + e.getMessage());
			return "error";
		}

	}

		
	public Clients getClient() {
		return client;
	}

	public void setClient(Clients client) {
		this.client = client;
	}

	public Page getClientsPage() {
		return clientsPage;
	}

	public void setClientsPage(Page clientsPage) {
		this.clientsPage = clientsPage;
	}

	public void setClientsService(ClientsService clientsService) {
		this.clientsService = clientsService;
	}

	public int getCurPage() {
		return curPage;
	}

	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public ClientsService getClientsService() {
		return clientsService;
	}

	public SjzdService getSjzdService() {
		return sjzdService;
	}

	public void setSjzdService(SjzdService sjzdService) {
		this.sjzdService = sjzdService;
	}

	public String[] getWldwlx() {
		return wldwlx;
	}

	public void setWldwlx(String[] wldwlx) {
		this.wldwlx = wldwlx;
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

	public String getKhjl() {
		return khjl;
	}

	public void setKhjl(String khjl) {
		this.khjl = khjl;
	}

	public String getLxr() {
		return lxr;
	}

	public void setLxr(String lxr) {
		this.lxr = lxr;
	}

	public Page getClientWlywPage() {
		return clientWlywPage;
	}

	public void setClientWlywPage(Page clientWlywPage) {
		this.clientWlywPage = clientWlywPage;
	}

	public String getClientsName() {
		return clientsName;
	}

	public void setClientsName(String clientsName) {
		this.clientsName = clientsName;
	}

	public String getClientRegInfoText() {
		return clientRegInfoText;
	}

	public void setClientRegInfoText(String clientRegInfoText) {
		this.clientRegInfoText = clientRegInfoText;
	}
	
	public String getClientsTips() {
		return clientsTips;
	}

	public void setClientsTips(String clientsTips) {
		this.clientsTips = clientsTips;
	}

	public String getClient_con() {
		return client_con;
	}

	public void setClient_con(String client_con) {
		this.client_con = client_con;
	}

	public String getClients_id() {
		return clients_id;
	}

	public void setClients_id(String clients_id) {
		this.clients_id = clients_id;
	}

	public List getClientsFollow() {
		return clientsFollow;
	}

	public void setClientsFollow(List clientsFollow) {
		this.clientsFollow = clientsFollow;
	}

	public List getDescClientLinkman() {
		return descClientLinkman;
	}

	public void setDescClientLinkman(List descClientLinkman) {
		this.descClientLinkman = descClientLinkman;
	}

	public ClientsFollow getFollow() {
		return follow;
	}

	public void setFollow(ClientsFollow follow) {
		this.follow = follow;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ClientsLinkman getLinkman() {
		return linkman;
	}

	public void setLinkman(ClientsLinkman linkman) {
		this.linkman = linkman;
	}

	public List getClientsPayInfos() {
		return clientsPayInfos;
	}

	public void setClientsPayInfos(List clientsPayInfos) {
		this.clientsPayInfos = clientsPayInfos;
	}

	public Map getClientQcMap() {
		return clientQcMap;
	}

	public void setClientQcMap(Map clientQcMap) {
		this.clientQcMap = clientQcMap;
	}

	public Map getClientWlMap() {
		return clientWlMap;
	}

	public void setClientWlMap(Map clientWlMap) {
		this.clientWlMap = clientWlMap;
	}
	
	public String[] getLxrnld() {
		return lxrnld;
	}

	public void setLxrnld(String[] lxrnld) {
		this.lxrnld = lxrnld;
	}
}
