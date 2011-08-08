package com.sw.cms.action;

import java.util.ArrayList;
import java.util.List;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.model.Kcpd;
import com.sw.cms.model.LoginInfo;
import com.sw.cms.model.Page;
import com.sw.cms.service.KcpdService;
import com.sw.cms.service.StoreService;
import com.sw.cms.service.UserService;
import com.sw.cms.util.Constant;
import com.sw.cms.util.ParameterUtility;

public class KcpdAction extends BaseAction {
	
	private KcpdService kcpdService;
	private UserService userService;
	private StoreService storeService;
	
	
	private Kcpd kcpd = new Kcpd();
	
	private Page kcpdPage;
	private List kcpdDesc = new ArrayList();
	private Page productPage;
	private List storeList;
	private List userList = new ArrayList();
	
	
	private String pdrq1 = "";
	private String pdrq2 = "";
	private String store_id = "";
	private String state = "";
	private String id = "";
	
	private String orderName ="";
	private String orderType ="";
	
	private int curPage = 1;
	
	private String isqzxlh_flag = "";  //ϵͳ�Ƿ�Ҫ��ǿ�����к�
	/**
	 * �����б�
	 * @return
	 */
	public String list(){
		//��ѯ����
		int rowsPerPage = Constant.PAGE_SIZE;
		
		String con = "";
		

		if(!pdrq1.equals("")){
			con += " and pdrq>='" + pdrq1 + "'";
		}
		if(!pdrq2.equals("")){
			con += " and pdrq<='" + pdrq2 + "'";
		}
		if(!store_id.equals("")){
			con += " and store_id='" + store_id + "'";
		}

		if(orderName.equals("")){
			orderName = "id";
		}
		if(orderType.equals("")){
			orderType = "desc";
		}
		
		con += " order by " + orderName + " " + orderType;
		
		kcpdPage = kcpdService.getKcpds(con, curPage, rowsPerPage);
		storeList = storeService.getAllStoreList();
		return "success";
	}
	
	
	/**
	 * �����ҳ��
	 * @return
	 */
	public String add(){
		storeList = storeService.getAllStoreList();
		userList = userService.getAllEmployeeList();
		id = kcpdService.updateKcpdId();
		isqzxlh_flag = userService.getQzxlh();
		return "success";
	}
	
	
	
	/**
	 * �������̵���Ϣ
	 * @return
	 */
	public String save(){
		LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		String user_id = info.getUser_id();
		kcpd.setCzr(user_id);
		isqzxlh_flag = userService.getQzxlh();
		
		if(isqzxlh_flag.equals("01")){
			   msg = kcpdService.checkXlh(kcpd, kcpdDesc);
			   if(!msg.equals(""))
			   {
				  kcpd.setState("�ѱ���");
				  kcpdService.saveKcpd(kcpd, kcpdDesc);
				  return "input";
			   }
			}
		kcpdService.saveKcpd(kcpd, kcpdDesc);
		return "success";
	}
	
	
	/**
	 * �����̵�ID�鿴�̵���Ϣ
	 * @return
	 */
	public String edit(){
		kcpd = (Kcpd)kcpdService.getKcpd(id);
		kcpdDesc = kcpdService.getKcpdDescs(id);
		isqzxlh_flag = userService.getQzxlh();
		storeList = storeService.getAllStoreList();
		userList = userService.getAllEmployeeList();
		return "success";
	}
	
	
	/**
	 * ���¿���̵���Ϣ
	 * @return
	 */
	public String update(){
//		�жϳ��ⵥ�Ƿ��Ѿ��ύ������Ѿ��ύ�����κβ���
		if(kcpdService.isKcpdSubmit(kcpd.getId())){
			return SUCCESS;
		}
		LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		String user_id = info.getUser_id();
		kcpd.setCzr(user_id);
		isqzxlh_flag = userService.getQzxlh();
		if(isqzxlh_flag.equals("01")){
			   msg = kcpdService.checkXlh(kcpd, kcpdDesc);
			   if(!msg.equals(""))
			   {
				  kcpd.setState("�ѱ���");
				  kcpdService.updateKcpd(kcpd, kcpdDesc);				  
				 
				  kcpdDesc = kcpdService.getKcpdDescs(kcpd.getId());
				  isqzxlh_flag = userService.getQzxlh();
				  storeList = storeService.getAllStoreList();
				  userList = userService.getAllEmployeeList();
				  
				  this.saveMessage(msg);
				  return "input";
			   }
			}
		kcpdService.updateKcpd(kcpd, kcpdDesc);
		return "success";
	}
	
	
	/**
	 * ɾ���̵���Ϣ
	 * @return
	 */
	public String del(){
		kcpdService.delKcpd(id);
		return "success";
	}
	
	
	/**
	 * ����̵���ϸ
	 * @return
	 */
	public String descKcpd(){
		kcpdDesc = kcpdService.getKcpdDescs(id);
		return "success";
	}
	
	
	/**
	 * ��ѡ������Ʒ�б�
	 * @return
	 */
	public String selKcProc(){
		int rowsPerPage = Constant.PAGE_SIZE;
		
		String product_xh = ParameterUtility.getStringParameter(getRequest(),"product_xh", "");
		String product_name = ParameterUtility.getStringParameter(getRequest(),"product_name", "");
		
		String con = "";
		if(!product_xh.equals("")){
			con += " and a.product_xh like '%" + product_xh + "%'";
		}
		if(!product_name.equals("")){
			product_name = ((product_name.replace("��", " ")).replace(",", "")).replace("��", " ");
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
		
		productPage = kcpdService.getAllProductKcIncludeZero(con,store_id,curPage, rowsPerPage);
		
		return "success";
	}
	
	
	public Kcpd getKcpd() {
		return kcpd;
	}
	public void setKcpd(Kcpd kcpd) {
		this.kcpd = kcpd;
	}
	public Page getKcpdPage() {
		return kcpdPage;
	}
	public void setKcpdPage(Page kcpdPage) {
		this.kcpdPage = kcpdPage;
	}
	public KcpdService getKcpdService() {
		return kcpdService;
	}
	public void setKcpdService(KcpdService kcpdService) {
		this.kcpdService = kcpdService;
	}
	public List getStoreList() {
		return storeList;
	}
	public List getKcpdDesc() {
		return kcpdDesc;
	}
	public void setKcpdDesc(List kcpdDesc) {
		this.kcpdDesc = kcpdDesc;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	public List getUserList() {
		return userList;
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


	public String getStore_id() {
		return store_id;
	}


	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}


	public UserService getUserService() {
		return userService;
	}

	public void setStoreList(List storeList) {
		this.storeList = storeList;
	}


	public void setUserList(List userList) {
		this.userList = userList;
	}


	public String getPdrq1() {
		return pdrq1;
	}


	public void setPdrq1(String pdrq1) {
		this.pdrq1 = pdrq1;
	}


	public String getPdrq2() {
		return pdrq2;
	}


	public void setPdrq2(String pdrq2) {
		this.pdrq2 = pdrq2;
	}


	public StoreService getStoreService() {
		return storeService;
	}


	public void setStoreService(StoreService storeService) {
		this.storeService = storeService;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public Page getProductPage() {
		return productPage;
	}


	public void setProductPage(Page productPage) {
		this.productPage = productPage;
	}

	public String getIsqzxlh_flag() {
		return isqzxlh_flag;
	}


	public void setIsqzxlh_flag(String isqzxlh_flag) {
		this.isqzxlh_flag = isqzxlh_flag;
	}
}
