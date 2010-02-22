package com.sw.cms.action;

import java.util.ArrayList;
import java.util.List;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.model.LoginInfo;
import com.sw.cms.model.Page;
import com.sw.cms.model.Ykck;
import com.sw.cms.model.Ykrk;
import com.sw.cms.service.ShkcService;
import com.sw.cms.service.StoreService;
import com.sw.cms.service.SysInitSetService;
import com.sw.cms.service.UserService;
import com.sw.cms.service.YkckService;
import com.sw.cms.service.YkrkService;
import com.sw.cms.util.Constant;
import com.sw.cms.util.ParameterUtility;

public class YkrkAction extends BaseAction
{
	 private YkrkService ykrkService;
	 private StoreService storeService;
	 private UserService userService;
	 private SysInitSetService sysInitSetService;  
	 private ShkcService shkcService;
	 
	 private Ykrk ykrk = new Ykrk();
     private List ykrkProducts = new ArrayList();
     private List storeList = new ArrayList();
 	 private List userList = new ArrayList();
 	
 	 private String id = "";
	 private String rk_date1="";
	 private String rk_date2="";
	 private String orderName="";
	 private String orderType="";
	 private Page pageYkrk;
	 private Page shkcPage;
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
			if(!rk_date1.equals("")){
				con += " and rk_date>='" + rk_date1 + "'";
			}
			if(!rk_date2.equals("")){
				con += " and rk_date<='" + rk_date2 + "'";
			}
			
			if(orderName.equals("")){
				orderName = "id";
			}
			if(orderType.equals("")){
				orderType = "desc";
			}
			
			con += " order by " + orderName + " " + orderType;
			
			pageYkrk = ykrkService.getYkrkList(con, curPage, rowsPerPage);
			return "success";
		}
		
		/**
		 * 移库出库添加页面
		 * @return
		 */
		public String add(){
			ykrk.setId(ykrkService.updateYkrkID());
			
			storeList = storeService.getAllStoreList();
			userList = userService.getAllEmployeeList();
			return "success";
		}
		
		public String selShkcByhao()throws Exception
		{
			try {
				String product_name = ParameterUtility.getStringParameter(
						getRequest(), "product_name", "");
				            
				int rowsPerPage = 15;

				String con = "";
			 
				if(!product_name.equals("")){
					con += " and (product_name like '%" + product_name + "%' or product_xh like '%" + product_name + "%')";
				}

				shkcPage = shkcService.getShkcIsHaoProduct(con, curPage, rowsPerPage);

				return "success";
			} catch (Exception e) {
				log.error("获取好件库列表 错误原因:" + e.getMessage());
				return "error";
			}

		}

		
		/**
		 * 保存移库入库
		 * @return
		 */
		public String save(){
			LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
			String user_id = info.getUser_id();
			ykrk.setCzr(user_id);
			
			iscs_flag = sysInitSetService.getQyFlag();
			//只有在系统正式启用后才去判断库存是否满足需求
			if(iscs_flag.equals("1"))
			{
				if(ykrk.getState().equals("已提交"))
				{
                    msg = ykrkService.checkHaoKc(ykrk, ykrkProducts);
					
					if(!msg.equals(""))
					
					{
						ykrk.setState("已保存");
						ykrkService.saveYkrk(ykrk, ykrkProducts);
						
						return "input";
					}
					 msg=ykrkService.isSerialNumInKcExist(ykrkProducts);
					 if(!msg.equals(""))
					 {
							ykrk.setState("已保存");
							ykrkService.saveYkrk(ykrk, ykrkProducts);							
							return "input";
					 }
				}
			}
			
			if(ykrk.getState().equals("已保存"))
			{
			  ykrkService.saveYkrk(ykrk, ykrkProducts);
			}
			return "success";
		}
		
		public String edit(){
			storeList = storeService.getAllStoreList();
			userList = userService.getAllEmployeeList();
			
			ykrk = (Ykrk)ykrkService.getYkrk(id);
			ykrkProducts = ykrkService.getykrkProducts(id);
			return "success";
		}
		
		
		public String update(){
			LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
			String user_id = info.getUser_id();
			ykrk.setCzr(user_id);
			
			iscs_flag = sysInitSetService.getQyFlag();
			//只有在系统正式启用后才去判断库存是否满足需求
			if(iscs_flag.equals("1")){
				if(ykrk.getState().equals("已提交"))
				{
                    msg = ykrkService.checkHaoKc(ykrk, ykrkProducts);
					
					if(!msg.equals(""))
					{
						ykrk.setState("已保存");
						ykrkService.saveYkrk(ykrk, ykrkProducts);
						
						return "input";
					} 
					
					msg=ykrkService.isSerialNumInKcExist(ykrkProducts);
					 if(!msg.equals(""))
					 {
							ykrk.setState("已保存");
							ykrkService.updateYkrk(ykrk, ykrkProducts);							
							return "input";
						}
				}
			}
			
			//判断调拨单是否已经提交
			if(ykrkService.isDbFinish(ykrk.getId())){
				msg = "调拨单已经出库，不能重复出库，请检查！";
				
				storeList = storeService.getAllStoreList();
				ykrkProducts = ykrkService.getykrkProducts(ykrk.getId());
				
				return "input";
			}
			
			ykrkService.updateYkrk(ykrk, ykrkProducts);
			return "success";
		}
		
		public String del(){
			ykrkService.delYkrk(id);
			return "success";
		}	
		
		
		/**
		 * 库房调拨产品明细
		 * @return
		 */
		public String descYkrk(){
			ykrkProducts = ykrkService.getykrkProducts(id);
			return "success";
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

	public YkrkService getYkrkService() {
		return ykrkService;
	}

	public void setYkrkService(YkrkService ykrkService) {
		this.ykrkService = ykrkService;
	}

	public String getRk_date1() {
		return rk_date1;
	}

	public void setRk_date1(String rk_date1) {
		this.rk_date1 = rk_date1;
	}

	public String getRk_date2() {
		return rk_date2;
	}

	public void setRk_date2(String rk_date2) {
		this.rk_date2 = rk_date2;
	}

	public Page getPageYkrk() {
		return pageYkrk;
	}

	public void setPageYkrk(Page pageYkrk) {
		this.pageYkrk = pageYkrk;
	}

	public List getYkrkProducts() {
		return ykrkProducts;
	}

	public void setYkrkProducts(List ykrkProducts) {
		this.ykrkProducts = ykrkProducts;
	}

	public Ykrk getYkrk() {
		return ykrk;
	}

	public void setYkrk(Ykrk ykrk) {
		this.ykrk = ykrk;
	}

	public Page getShkcPage() {
		return shkcPage;
	}

	public void setShkcPage(Page shkcPage) {
		this.shkcPage = shkcPage;
	}

	public ShkcService getShkcService() {
		return shkcService;
	}

	public void setShkcService(ShkcService shkcService) {
		this.shkcService = shkcService;
	}

 
}
