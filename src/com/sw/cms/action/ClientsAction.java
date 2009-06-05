package com.sw.cms.action;

import java.util.ArrayList;
import java.util.List;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.model.Clients;
import com.sw.cms.model.ClientsFollow;
import com.sw.cms.model.ClientsLinkman;
import com.sw.cms.model.LoginInfo;
import com.sw.cms.model.Page;
import com.sw.cms.service.ClientsService;
import com.sw.cms.service.SjzdService;
import com.sw.cms.service.UserService;
import com.sw.cms.util.Constant;
import com.sw.cms.util.ParameterUtility;

public class ClientsAction extends BaseAction {

	private ClientsService clientsService;

	private SjzdService sjzdService;

	private UserService userService;

	private Page clientsPage;

	private Clients client = new Clients();

	private List userList = new ArrayList();

	private String name = "";

	private String type = "";

	private String lxr = "";

	private String khjl = "";

	private String[] wldwlx;

	private String orderName = "";

	private String orderType = "";

	private int curPage = 1;

	private String id = "";

	private List descClientLinkman = new ArrayList();

	private List clientsFollow = new ArrayList();

	private ClientsLinkman linkman = new ClientsLinkman();

	private ClientsFollow follow = new ClientsFollow();

	private String clients_id;

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

	/**
	 * 返回列表（带分页）
	 * 
	 * @return
	 */
	public String list() {

		int rowsPerPage = Constant.PAGE_SIZE2;

		String con = "";

		if (!name.equals("")) {
			con += " and name like'%" + name + "%'";
		}
		if (!lxr.equals("")) {
			con += " and lxr like'%" + lxr + "%'";
		}
		if (!khjl.equals("")) {
			con += " and khjl like '%" + khjl + "%'";
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
	 * 打开添加页面
	 * 
	 * @return
	 */
	public String add() {
		wldwlx = sjzdService.getSjzdXmxxByZdId("SJZD_WLDWLX");
		userList = userService.getAllEmployeeList();
		return "success";
	}

	/**
	 * 保存客户信息
	 * 
	 * @return
	 */
	public String save() {
		clientsService.saveClient(client);
		return "success";
	}

	/**
	 * 编辑客户信息
	 * 
	 * @return
	 */
	public String edit() {
		String id = ParameterUtility.getStringParameter(getRequest(), "id", "");
		client = (Clients) clientsService.getClient(id);
		wldwlx = sjzdService.getSjzdXmxxByZdId("SJZD_WLDWLX");
		userList = userService.getAllEmployeeList();
		return "success";
	}

	/**
	 * 更新客户信息
	 * 
	 * @return
	 */
	public String update() {
		clientsService.updateClient(client);
		return "success";
	}

	/**
	 * 删除客户信息
	 * 
	 * @return
	 */
	public String del() {
		String id = ParameterUtility.getStringParameter(getRequest(), "id", "");
		clientsService.delClient(id);
		return "success";
	}

	/**
	 * 取客户信息带应收款
	 * 
	 * @return
	 */
	public String listSel() {
		String name = ParameterUtility.getStringParameter(getRequest(), "name",
				"");

		int curPage = ParameterUtility.getIntParameter(getRequest(), "curPage",
				1);
		int rowsPerPage = Constant.PAGE_SIZE2;

		String con = "";

		if (!name.equals("")) {
			con += " and name like'%" + name + "%'";
		}

		clientsPage = clientsService.getClientIncludYsk(con, curPage,
				rowsPerPage);

		return "success";
	}

	/**
	 * desc 联系人
	 * 
	 * @return
	 */
	public String descClient() throws Exception {
		try {
			descClientLinkman = clientsService.getClientsLinkman(id);
			return "success";
		} catch (Exception e) {
			log.error("点击客户信息，返回联系人信息，错误原因" + e.getMessage());
			return "error";
		}

	}

	/**
	 * 打开客户视图
	 * 
	 * @return
	 */
	public String listView() throws Exception {
		try {
			String id = ParameterUtility.getStringParameter(getRequest(), "id",
					"");
			client = (Clients) clientsService.getClient(id);// 客户实体
			descClientLinkman = clientsService.getClientsLinkman(id);// 联系人
			clientsFollow = clientsService.getClientsFollow(id);// 跟进记录
			return "success";
		} catch (Exception e) {
			log.error("打开客户视图 错误原因" + e.getMessage());
			return "error";
		}

	}

	/**
	 * 打开添加联系人页面
	 * 
	 * @return
	 * @throws Exception
	 */
	public String addLinkman() throws Exception {
		try {
			id = ParameterUtility.getStringParameter(getRequest(), "id", "");
			return "success";
		} catch (Exception e) {
			log.error("打开添加联系人页面 错误原因" + e.getMessage());
			return "error";
		}

	}

	/**
	 * 添加联系人
	 * 
	 * @return
	 * @throws Exception
	 */
	public String saveLinkman() throws Exception {
		try {
			clientsService.insertLinkman(linkman);
			return "success";
		} catch (Exception e) {
			log.error("添加联系人  错误原因" + e.getMessage());
			return "error";
		}

	}

	/**
	 * 打开修改联系人页面
	 * 
	 * @return
	 * @throws Exception
	 */
	public String editLinkman() throws Exception {
		try {
			linkman = (ClientsLinkman) clientsService.getLinkmanById(id);
			return "success";
		} catch (Exception e) {
			log.error("打开修改联系人页面 错误原因" + e.getMessage());
			return "error";
		}

	}

	/**
	 * 保存修改的联系人
	 * 
	 * @return
	 * @throws Exception
	 */
	public String updateLinkman() throws Exception {
		try {
			clientsService.updateLinkman(linkman);
			return "success";
		} catch (Exception e) {
			log.error("保存修改 错误原因" + e.getMessage());
			return "error";
		}

	}

	/**
	 * 删除联系人
	 * 
	 * @return
	 * @throws Exception
	 */
	public String deleteLinkman() throws Exception {
		try {
			clients_id = clientsService.deleteLinkman(id);
			System.out.println(clients_id);
			return "success";
		} catch (Exception e) {
			log.error("删除联系人 错误原因" + e.getMessage());
			return "error";
		}
	}

	/**
	 * 打开添加跟进记录页面
	 * 
	 * @return
	 * @throws Exception
	 */
	public String addFollow() throws Exception {
		try {
			id = ParameterUtility.getStringParameter(getRequest(), "id", "");
			descClientLinkman = clientsService.getClientsLinkman(id);
			return "success";
		} catch (Exception e) {
			log.error("打开添加跟进记录 错误原因" + e.getMessage());
			return "error";
		}
	}

	/**
	 * 保存添加跟进记录
	 * 
	 * @return
	 * @throws Exception
	 */
	public String saveFollow() throws Exception {
		try {
			LoginInfo info = (LoginInfo) getSession().getAttribute("LOGINUSER");
			follow.setCjr(info.getUser_id());
			clientsService.insertFollow(follow);
			return "success";
		} catch (Exception e) {
			log.error("保存添加跟进记录 错误原因" + e.getMessage());
			return "error";
		}
	}

	/**
	 * 打开修改跟进记录
	 * 
	 * @return
	 * @throws Excpetion
	 */
	public String editFollow() throws Exception {
		try {
			clients_id = ParameterUtility.getStringParameter(getRequest(),
					"clients_id", "");
			descClientLinkman = clientsService.getClientsLinkman(clients_id);
			follow = (ClientsFollow) clientsService.getClientsFollowById(id);
			return "success";
		} catch (Exception e) {
			log.error("打开修改跟进记录  错误原因" + e.getMessage());
			return "error";
		}
	}

	/**
	 * 保存修改的跟进及记录
	 * 
	 * @return
	 * @throws Exception
	 */
	public String updateFollow() throws Exception {
		try {
			clientsService.updateFollow(follow);
			return "success";
		} catch (Exception e) {
			log.error("保存修改的跟进记录 错误原因" + e.getMessage());
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

}
