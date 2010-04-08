package com.sw.cms.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import com.sw.cms.util.DateComFunc;
import com.sw.cms.util.ParameterUtility;
import com.sw.cms.util.StringUtils;

public class ClientsAction extends BaseAction {

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
	 * �����б�����ҳ��
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
	 * �����ҳ��
	 * 
	 * @return
	 */
	public String add() {
		wldwlx = sjzdService.getSjzdXmxxByZdId("SJZD_WLDWLX");
		lxrnld= sjzdService.getSjzdXmxxByZdId("SJZD_LXRNLD");
		userList = userService.getAllEmployeeList();
		return "success";
	}

	/**
	 * ����ͻ���Ϣ
	 * 
	 * @return
	 */
	public String save() {
		
		if(clientsService.getClientsIsExist(client.getName())>0){
			getSession().setAttribute("MSG", "��λ�����Ѵ��ڣ���ѡ���������ƣ�");
			wldwlx = sjzdService.getSjzdXmxxByZdId("SJZD_WLDWLX");
			lxrnld= sjzdService.getSjzdXmxxByZdId("SJZD_LXRNLD");
			userList = userService.getAllEmployeeList();
			return "input";
		}
		clientsService.saveClient(client,linkman,clientsPayInfos);
		return "success";
	}

	/**
	 * �༭�ͻ���Ϣ
	 * 
	 * @return
	 */
	public String edit() {
		 
		String id = ParameterUtility.getStringParameter(getRequest(), "id", "");
		client = (Clients) clientsService.getClient(id);
		wldwlx = sjzdService.getSjzdXmxxByZdId("SJZD_WLDWLX");
		lxrnld= sjzdService.getSjzdXmxxByZdId("SJZD_LXRNLD");
		userList = userService.getAllEmployeeList();
		clientsPayInfos = clientsService.getClientsPayInfos(id);
		return "success";
	}

	/**
	 * ���¿ͻ���Ϣ
	 * 
	 * @return
	 */
	public String update(){
		
		Clients map=(Clients)clientsService.getClient(client.getId());
		if(!map.getName().equals(client.getName()))
		{
			if(clientsService.getClientsIsExist(client.getName())>0)
			{
			   String flogname=	client.getName();
				 getSession().setAttribute("MSG", "��λ�����Ѵ��ڣ���ѡ���������ƣ�");
				client = (Clients) clientsService.getClient(map.getId());
				client.setName(flogname);
				wldwlx = sjzdService.getSjzdXmxxByZdId("SJZD_WLDWLX");
				lxrnld= sjzdService.getSjzdXmxxByZdId("SJZD_LXRNLD");
				userList = userService.getAllEmployeeList();
				return "input";
			}
			
		}
		clientsService.updateClient(client,clientsPayInfos);
		return "success";
	}

	/**
	 * ɾ���ͻ���Ϣ
	 * 
	 * @return
	 */
	public String del() {
		String id = ParameterUtility.getStringParameter(getRequest(), "id", "");
		
		if(!clientsService.isCanDel(id)){
			this.setMsg("�ѷ�������ҵ�񣬿ͻ���Ϣ����ɾ����");
			return "notDel";
		}
		
		clientsService.delClient(id);
		return "success";
	}

	/**
	 * ȡ�ͻ���Ϣ��Ӧ�տ�
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
	 * �ͻ���ѯ,���涥��
	 * @return
	 * @throws Exception
	 */
	public String queryClients() throws Exception{
		try{
			int rowsPerPage = Constant.PAGE_SIZE2;
			String con = "";
			String con2 = "";
			if(!client_con.equals("")){
				con = " and (name like '%" + client_con + "%' or address like '%" + client_con + "%' or lxdh='" + client_con + "' or mobile='" + client_con + "' or lxr like '%" + client_con + "%')";
				con2 = " and (b.name like '%" + client_con + "%' or b.address like '%" + client_con + "%' or b.lxdh='" + client_con + "' or b.mobile='" + client_con + "' or b.lxr like '%" + client_con + "%')";
			}
			clientsPage = clientsService.getClientIncludYsk(con, curPage,rowsPerPage);
			clientQcMap = clientsService.getClientQc(con2, DateComFunc.getToday());
			clientWlMap = clientsService.getClientWlInfo(con2, DateComFunc.getToday());
			return "success";
		}catch(Exception e){
			log.error("�ͻ���ѯ����,����ԭ��:" + e.getMessage());
			return ERROR;
		}
	}
	

	/**
	 * desc ��ϵ��
	 * 
	 * @return
	 */
	public String descClient() throws Exception {
		try {
			descClientLinkman = clientsService.getClientsLinkman(id);
			return "success";
		} catch (Exception e) {
			log.error("����ͻ���Ϣ��������ϵ����Ϣ������ԭ��" + e.getMessage());
			return "error";
		}

	}

	/**
	 * �򿪿ͻ���ͼ
	 * 
	 * @return
	 */
	public String listView() throws Exception {
		 
		try {
			client = (Clients) clientsService.getClient(id);// �ͻ�ʵ��
			clientWlywPage = clientsService.getClientWlyw(id);
			descClientLinkman = clientsService.getClientsLinkman(id);// ��ϵ��
			clientsFollow = clientsService.getClientsFollow(id);// ������¼
			return "success";
		} catch (Exception e) {
			log.error("�򿪿ͻ���ͼ ����ԭ��" + e.getMessage());
			return "error";
		}

	}

	/**
	 * �������ϵ��ҳ��
	 * 
	 * @return
	 * @throws Exception
	 */
	public String addLinkman() throws Exception {
		try {
			id = ParameterUtility.getStringParameter(getRequest(), "id", "");
			lxrnld= sjzdService.getSjzdXmxxByZdId("SJZD_LXRNLD");
			return "success";
		} catch (Exception e) {
			log.error("�������ϵ��ҳ�� ����ԭ��" + e.getMessage());
			return "error";
		}

	}

	/**
	 * �����ϵ��
	 * 
	 * @return
	 * @throws Exception
	 */
	public String saveLinkman() throws Exception {
		try {
			clientsService.insertLinkman(linkman);
			return "success";
		} catch (Exception e) {
			log.error("�����ϵ��  ����ԭ��" + e.getMessage());
			return "error";
		}

	}

	/**
	 * ���޸���ϵ��ҳ��
	 * 
	 * @return
	 * @throws Exception
	 */
	public String editLinkman() throws Exception {
		try {
			linkman = (ClientsLinkman) clientsService.getLinkmanById(id);
			lxrnld= sjzdService.getSjzdXmxxByZdId("SJZD_LXRNLD");
			return "success";
		} catch (Exception e) {
			log.error("���޸���ϵ��ҳ�� ����ԭ��" + e.getMessage());
			return "error";
		}

	}

	/**
	 * �����޸ĵ���ϵ��
	 * 
	 * @return
	 * @throws Exception
	 */
	public String updateLinkman() throws Exception {
		try {
			clientsService.updateLinkman(linkman);
			return "success";
		} catch (Exception e) {
			log.error("�����޸� ����ԭ��" + e.getMessage());
			return "error";
		}

	}

	/**
	 * ɾ����ϵ��
	 * 
	 * @return
	 * @throws Exception
	 */
	public String deleteLinkman() throws Exception {
		try {
			clients_id = clientsService.deleteLinkman(id);
			
			return "success";
		} catch (Exception e) {
			log.error("ɾ����ϵ�� ����ԭ��" + e.getMessage());
			return "error";
		}
	}

	/**
	 * ����Ӹ�����¼ҳ��
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
			log.error("����Ӹ�����¼ ����ԭ��" + e.getMessage());
			return "error";
		}
	}

	/**
	 * ������Ӹ�����¼
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
			log.error("������Ӹ�����¼ ����ԭ��" + e.getMessage());
			return "error";
		}
	}

	/**
	 * ���޸ĸ�����¼
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
			log.error("���޸ĸ�����¼  ����ԭ��" + e.getMessage());
			return "error";
		}
	}

	/**
	 * �����޸ĵĸ�������¼
	 * 
	 * @return
	 * @throws Exception
	 */
	public String updateFollow() throws Exception {
		try {
			clientsService.updateFollow(follow);
			return "success";
		} catch (Exception e) {
			log.error("�����޸ĵĸ�����¼ ����ԭ��" + e.getMessage());
			return "error";
		}
	}
	
	
	/**
	 * ������ʾ��������Ϣ��ѯ�ͻ��б���Ϣ
	 * @return
	 * @throws Exception
	 */
	public String getLikeClientInfo() throws Exception {
		try{
			if(clientsName.equals("")){
				return "success";
			}			
			List clients = clientsService.getClientListByAjaxParam(clientsName); //���ƿͻ���Ϣ�б�
			
			if(clients != null && clients.size() > 0){
				for(int i=0;i<clients.size();i++){
					Map clientMap = (Map)clients.get(i);
					if(clientsTips.equals("")){
						clientsTips = StringUtils.nullToStr(clientMap.get("id")) + "$" + StringUtils.nullToStr(clientMap.get("name"));
					}else{
						clientsTips += "%" + StringUtils.nullToStr(clientMap.get("id")) + "$" + StringUtils.nullToStr(clientMap.get("name"));
					}
				}
			}
			return "success";
		}catch(Exception e){
			log.error("���AJAX��ʾ����ѯ�ͻ���Ϣʧ��,ʧ��ԭ��" + e.getMessage());
			return "error";
		}
	}
	
	/**
	 * ��ʼҳ��ȡ���пͻ�������Ϣ
	 * @return
	 * @throws Exception
	 */
	public String getAllClientInfo() throws Exception {
		try{
			List clients = clientsService.getClientListByAjaxParam(clientsName); //���ƿͻ���Ϣ�б�
			
			if(clients != null && clients.size() > 0){
				for(int i=0;i<clients.size();i++){
					Map clientMap = (Map)clients.get(i);
					if(clientsTips.equals("")){
						clientsTips = StringUtils.nullToStr(clientMap.get("id")) + "$" + StringUtils.nullToStr(clientMap.get("name"));
					}else{
						clientsTips += "%" + StringUtils.nullToStr(clientMap.get("id")) + "$" + StringUtils.nullToStr(clientMap.get("name"));
					}
				}
			}
			return "success";
		}catch(Exception e){
			log.error("���AJAX��ʾ����ѯ�ͻ���Ϣʧ��,ʧ��ԭ��" + e.getMessage());
			return "error";
		}
	}	
	
	
	/**
	 * ���ݿͻ�IDȡ�ͻ������Ϣ(��ַ����ϵ���б���Ϣ)
	 * @return
	 * @throws Exception
	 */
	public String getClientsRegInfo() throws Exception {
		try{
			
			//�ͻ���ַ
			Clients client = (Clients)clientsService.getClient(clients_id);
			clientRegInfoText = StringUtils.nullToStr(client.getAddress()) + "#" + 
								StringUtils.nullToStr(client.getKp_name())+ "#" + 
								StringUtils.nullToStr(client.getKp_address())+ "#" + 
								StringUtils.nullToStr(client.getKp_tel())+ "#" + 
								StringUtils.nullToStr(client.getKp_khhzh())+ "#" + 
								StringUtils.nullToStr(client.getKp_sh()) + "#" + 
								StringUtils.nullToStr(client.getZq()) + 
								"%";
			
			//��ϵ����Ϣ
			List linkManList = clientsService.getClientsLinkman(clients_id);
			if(linkManList != null && linkManList.size() > 0){
				for(int i=0;i<linkManList.size();i++){
					ClientsLinkman clientslinkman = (ClientsLinkman)linkManList.get(i);
					
					String gzdh = StringUtils.nullToStr(clientslinkman.getGzdh());
					String mobile = StringUtils.nullToStr(clientslinkman.getYddh());
					
					if(gzdh.equals("")){
						gzdh = mobile;
					}else{
						gzdh += "/" + mobile;
					}
					
					int len=gzdh.length();
					
					if(gzdh.endsWith("/"))
					{
						gzdh=gzdh.substring(0,len-1);
					}
					
					if(i == 0){
						clientRegInfoText += StringUtils.nullToStr(clientslinkman.getId()) + "#" + StringUtils.nullToStr(clientslinkman.getName()) + "#" + gzdh;
					}else{
						clientRegInfoText += "$" + StringUtils.nullToStr(clientslinkman.getId()) + "#" + StringUtils.nullToStr(clientslinkman.getName()) + "#" + gzdh;
					}

				}
			}
			return "success";
		}catch(Exception e){
			log.error("���ݿͻ���Ų�ѯ�ͻ������Ϣ��������ԭ��" + e.getMessage());
			return "error";
		}
	}
	
	
	/**
	 * ѡ��ͻ���λȫ��
	 * @return
	 */
	public String selClientAllName(){
		try{
			clientsPayInfos = clientsService.getClientsPayInfos(id);
			return SUCCESS;
		}catch(Exception e){
			log.error("ѡ��ͻ���λȫ�Ƴ�������ԭ��" + e.getMessage());
			return ERROR;
		}
	}
	
	
	/**
	 * ѡ��ͻ���ϵ��
	 * @return
	 */
	public String selClientLxr(){
		try{
			descClientLinkman = clientsService.getClientsLinkman(id);
			return SUCCESS;
		}catch(Exception e){
			log.error("ѡ��ͻ���ϵ�˳�������ԭ��" + e.getMessage());
			return ERROR;
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
