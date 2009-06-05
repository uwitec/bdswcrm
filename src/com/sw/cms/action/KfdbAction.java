package com.sw.cms.action;

import java.util.ArrayList;
import java.util.List;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.model.Kfdb;
import com.sw.cms.model.LoginInfo;
import com.sw.cms.model.Page;
import com.sw.cms.service.KfdbService;
import com.sw.cms.service.StoreService;
import com.sw.cms.service.SysInitSetService;
import com.sw.cms.service.UserService;
import com.sw.cms.util.Constant;

public class KfdbAction extends BaseAction {
	
	private KfdbService kfdbService;
	private UserService userService;
	private StoreService storeService;
	private SysInitSetService sysInitSetService;
	
	private Page pageKfdb;
	private Kfdb kfdb = new Kfdb();
	private List kfdbProducts = new ArrayList();	
	private List storeList = new ArrayList();
	private List userList = new ArrayList();
	
	private String id = "";
	private String ck_date1 = "";
	private String ck_date2 = "";
	private String orderName ="";
	private String orderType ="";	
	private int curPage = 1;
	private String iscs_flag = "";  //ϵͳ�Ƿ��ʼ��ɱ�־
	
	private String msg = "";
	
	
	/**
	 * �ⷿ�����б�
	 * @return
	 */
	public String list(){
		int rowsPerPage = Constant.PAGE_SIZE;

		String con = "";
		
		//��ѯ����
		if(!ck_date1.equals("")){
			con += " and ck_date>='" + ck_date1 + "'";
		}
		if(!ck_date2.equals("")){
			con += " and ck_date<='" + ck_date2 + "'";
		}
		
		if(orderName.equals("")){
			orderName = "id";
		}
		if(orderType.equals("")){
			orderType = "desc";
		}
		
		con += " order by " + orderName + " " + orderType;
		
		pageKfdb = kfdbService.getKfdbList(con, curPage, rowsPerPage);
		return "success";
	}
	
	
	/**
	 * �ⷿ�������ҳ��
	 * @return
	 */
	public String add(){
		kfdb.setId(kfdbService.updateKfdbID());
		
		storeList = storeService.getAllStoreList();
		userList = userService.getAllEmployeeList();
		return "success";
	}
	
	
	/**
	 * �����������
	 * @return
	 */
	public String save(){
		LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		String user_id = info.getUser_id();
		kfdb.setCzr(user_id);
		
		iscs_flag = sysInitSetService.getQyFlag();
		//ֻ����ϵͳ��ʽ���ú��ȥ�жϿ���Ƿ���������
		if(iscs_flag.equals("1")){
			if(kfdb.getState().equals("�ѳ���")){
				msg = kfdbService.checkKc(kfdb, kfdbProducts);
				if(!msg.equals("")){
					kfdb.setState("�ѱ���");
					kfdbService.saveKfdb(kfdb, kfdbProducts);
					
					return "input";
				}
			}
		}
		
		
		kfdbService.saveKfdb(kfdb, kfdbProducts);
		return "success";
	}
	
	
	/**
	 * �༭�ⷿ����
	 * @return
	 */
	public String edit(){
		storeList = storeService.getAllStoreList();
		userList = userService.getAllEmployeeList();
		
		kfdb = (Kfdb)kfdbService.getKfdb(id);
		kfdbProducts = kfdbService.getKfdbProducts(id);
		return "success";
	}
	
	
	/**
	 * ���¿ⷿ����
	 * @return
	 */
	public String update(){
		LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		String user_id = info.getUser_id();
		kfdb.setCzr(user_id);
		
		iscs_flag = sysInitSetService.getQyFlag();
		//ֻ����ϵͳ��ʽ���ú��ȥ�жϿ���Ƿ���������
		if(iscs_flag.equals("1")){
			if(kfdb.getState().equals("�ѳ���")){
				msg = kfdbService.checkKc(kfdb, kfdbProducts);
				if(!msg.equals("")){
					kfdb.setState("�ѱ���");
					kfdbService.updateKfdb(kfdb, kfdbProducts);
					
					return "input";
				}
			}
		}
		
		kfdbService.updateKfdb(kfdb, kfdbProducts);
		return "success";
	}
	
	
	/**
	 * ɾ���ⷿ����
	 * @return
	 */
	public String del(){
		kfdbService.delKfdb(id);
		return "success";
	}	
	
	
	/**
	 * �ⷿ������Ʒ��ϸ
	 * @return
	 */
	public String descKfdb(){
		kfdbProducts = kfdbService.getKfdbProducts(id);
		return "success";
	}
	
	
	public String getCk_date1() {
		return ck_date1;
	}
	public void setCk_date1(String ck_date1) {
		this.ck_date1 = ck_date1;
	}
	public String getCk_date2() {
		return ck_date2;
	}
	public void setCk_date2(String ck_date2) {
		this.ck_date2 = ck_date2;
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
	public Kfdb getKfdb() {
		return kfdb;
	}
	public void setKfdb(Kfdb kfdb) {
		this.kfdb = kfdb;
	}
	public List getKfdbProducts() {
		return kfdbProducts;
	}
	public void setKfdbProducts(List kfdbProducts) {
		this.kfdbProducts = kfdbProducts;
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
	public Page getPageKfdb() {
		return pageKfdb;
	}
	public void setPageKfdb(Page pageKfdb) {
		this.pageKfdb = pageKfdb;
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
	public KfdbService getKfdbService() {
		return kfdbService;
	}
	public void setKfdbService(KfdbService kfdbService) {
		this.kfdbService = kfdbService;
	}


	public String getMsg() {
		return msg;
	}


	public void setMsg(String msg) {
		this.msg = msg;
	}


	public SysInitSetService getSysInitSetService() {
		return sysInitSetService;
	}


	public void setSysInitSetService(SysInitSetService sysInitSetService) {
		this.sysInitSetService = sysInitSetService;
	}

}
