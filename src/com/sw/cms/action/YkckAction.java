package com.sw.cms.action;

import java.util.ArrayList;
import java.util.List;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.model.Kfdb;
import com.sw.cms.model.LoginInfo;
import com.sw.cms.model.Page;
import com.sw.cms.model.Ykck;
import com.sw.cms.service.StoreService;
import com.sw.cms.service.SysInitSetService;
import com.sw.cms.service.UserService;
import com.sw.cms.service.YkckService;
import com.sw.cms.util.Constant;

public class YkckAction extends BaseAction
{
	 private YkckService ykckService;
	 private StoreService storeService;
	 private UserService userService;
	 private SysInitSetService sysInitSetService;  
	 
	 private Ykck ykck = new Ykck();
     private List ykckProducts = new ArrayList();
     private List storeList = new ArrayList();
 	 private List userList = new ArrayList();
 	
 	 private String id = "";
	 private String ck_date1="";
	 private String ck_date2="";
	 private String orderName="";
	 private String orderType="";
	 private Page pageYkck;
	 private int curPage = 1;
	 private String iscs_flag = "";  //系统是否初始完成标志
		
	private String msg = "";
	    /**
		 * 移库出库列表
		 * @return
		 */
		public String list(){
			int rowsPerPage = Constant.PAGE_SIZE;

			String con = "";
			
			//查询条件
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
			
			pageYkck = ykckService.getYkckList(con, curPage, rowsPerPage);
			return "success";
		}
		
		/**
		 * 移库出库添加页面
		 * @return
		 */
		public String add(){
			ykck.setId(ykckService.updateYkckID());
			
			storeList = storeService.getAllStoreList();
			userList = userService.getAllEmployeeList();
			return "success";
		}
		
		/**
		 * 保存移库出库
		 * @return
		 */
		public String save(){
			LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
			String user_id = info.getUser_id();
			ykck.setCzr(user_id);
			
			iscs_flag = sysInitSetService.getQyFlag();
			//只有在系统正式启用后才去判断库存是否满足需求
			if(iscs_flag.equals("1")){
				if(ykck.getState().equals("已提交")){
					msg = ykckService.checkKc(ykck, ykckProducts);
					
					if(!msg.equals("")){
						ykck.setState("已保存");
						ykckService.saveYkck(ykck, ykckProducts);
						
						return "input";
					}
					 msg=ykckService.isSerialNumInKcExist(ykckProducts);
					 if(!msg.equals("")){
							ykck.setState("已保存");
							ykckService.saveYkck(ykck, ykckProducts);
							
							return "input";
						}
				}
			}
			
			
			ykckService.saveYkck(ykck, ykckProducts);
			return "success";
		}
		
		public String edit(){
			storeList = storeService.getAllStoreList();
			userList = userService.getAllEmployeeList();
			
			ykck = (Ykck)ykckService.getYkck(id);
			ykckProducts = ykckService.getykckProducts(id);
			return "success";
		}
		
		
		public String update(){
			LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
			String user_id = info.getUser_id();
			ykck.setCzr(user_id);
			
			iscs_flag = sysInitSetService.getQyFlag();
			//只有在系统正式启用后才去判断库存是否满足需求
			if(iscs_flag.equals("1")){
				if(ykck.getState().equals("已提交")){
					msg = ykckService.checkKc(ykck, ykckProducts);
					if(!msg.equals("")){
						ykck.setState("已保存");
						ykckService.updateYkck(ykck, ykckProducts);
						storeList = storeService.getAllStoreList();
						ykckProducts = ykckService.getykckProducts(ykck.getId());
						return "input";
					}
					 msg=ykckService.isSerialNumInKcExist(ykckProducts);
					 if(!msg.equals("")){
							ykck.setState("已保存");
							ykckService.saveYkck(ykck, ykckProducts);
							
							return "input";
						}
				}
			}
			
			//判断调拨单是否已经提交
			if(ykckService.isDbFinish(ykck.getId())){
				msg = "调拨单已经出库，不能重复出库，请检查！";
				
				storeList = storeService.getAllStoreList();
				ykckProducts = ykckService.getykckProducts(ykck.getId());
				
				return "input";
			}
			
			ykckService.updateYkck(ykck, ykckProducts);
			return "success";
		}
		
		public String del(){
			ykckService.delYkck(id);
			return "success";
		}	
		
		
		/**
		 * 库房调拨产品明细
		 * @return
		 */
		public String descYkck(){
			ykckProducts = ykckService.getykckProducts(id);
			return "success";
		}
		
		
		

	public YkckService getYkckService() {
		return ykckService;
	}

	public void setYkckService(YkckService ykckService) {
		this.ykckService = ykckService;
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


	 


	public Page getPageYkck() {
		return pageYkck;
	}


	public void setPageYkck(Page pageYkck) {
		this.pageYkck = pageYkck;
	}

	public StoreService getStoreService() {
		return storeService;
	}

	public void setStoreService(StoreService storeService) {
		this.storeService = storeService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	 

	public Ykck getYkck() {
		return ykck;
	}

	public List getYkckProducts() {
		return ykckProducts;
	}

	public void setYkckProducts(List ykckProducts) {
		this.ykckProducts = ykckProducts;
	}

	public List getStoreList() {
		return storeList;
	}

	public void setStoreList(List storeList) {
		this.storeList = storeList;
	}

	public List getUserList() {
		return userList;
	}

	public void setUserList(List userList) {
		this.userList = userList;
	}

	public void setYkck(Ykck ykck) {
		this.ykck = ykck;
	}

	public String getIscs_flag() {
		return iscs_flag;
	}

	public void setIscs_flag(String iscs_flag) {
		this.iscs_flag = iscs_flag;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
