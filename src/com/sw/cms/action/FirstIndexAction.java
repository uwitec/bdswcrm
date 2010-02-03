package com.sw.cms.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.model.LoginInfo;
import com.sw.cms.model.Page;
import com.sw.cms.service.FirstIndexService;
import com.sw.cms.service.MenuService;
import com.sw.cms.service.UserService;
import com.sw.cms.service.XxfbNbggService;
import com.sw.cms.util.Constant;
import com.sw.cms.util.StringUtils;

public class FirstIndexAction extends BaseAction {
	
	private FirstIndexService firstIndexService;
	private XxfbNbggService xxfbNbggService;
	private MenuService menuService;
	private UserService userService;

	private List nbggList = new ArrayList();

	
	private Page undoWorkPage;
	private int curPage = 1;
	
	List ywgnList = new ArrayList();
	
	
	/**
	 * 首页显示项
	 * @return
	 */
	public String listMain(){
		
		LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		String user_id = info.getUser_id();
		
		ywgnList = menuService.getUserYwgnFunc(user_id);

		//内部公告
		Page page = xxfbNbggService.getNbggList(1, 10);
		nbggList = page.getResults();

		return "success";
	}
	
	
	/**
	 * 显示分销首页
	 * @return
	 */
	public String listFxMain(){
		Page page = xxfbNbggService.getNbggList(1, 20 , "1");
		nbggList = page.getResults();
		return "success";
	}
	
	
	/**
	 * 显示供应商首页
	 * @return
	 */
	public String listGysMain(){
		return "success";
	}
	
	
	/**
	 * 待办工作列表
	 * @return
	 */
	public String listUndoWork(){
		try{
			int rowsPerPage = Constant.PAGE_SIZE2;
			
			LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
			String user_id = info.getUser_id();  //当前登陆用户
			
			String con="";
			
			//如果有操作出库单权限
			if(userService.isHasRightFuncs("FC0005", user_id)){
				con += " or yw_type='待出库单据'";
			}
			
			//如果有操作入库单权限
			if(userService.isHasRightFuncs("FC0004", user_id)){
				con += " or yw_type='待入库单据'";
			}
			
			//如果有价格审批权限
			if(userService.isHashJgspRight(user_id)){
				con += " or yw_type='待审批零售单'";
				
				con += " or (yw_type='待审批销售订单' and flag='4')";
			}
			
			//如果有超额审批权限
			if(userService.isHasCespRight(user_id)){
				con += " or (yw_type='待审批销售订单' and flag='3')";
			}
			
			//如果有超期审批权限
			if(userService.isHasCqspRight(user_id)){
				con += " or (yw_type='待审批销售订单' and flag='1')";
			}
			
			//如查有超额及价格审批权限
			if(userService.isHasCeAndJgspRight(user_id)){
				con += " or (yw_type='待审批销售订单' and flag='2')";
			}
			
			//采购付款审批权限设置
			Map cgfkMap = userService.getSpRight("采购付款");
			String sp_flag = "";
			String role_id = "";
			if(cgfkMap != null){
				sp_flag = StringUtils.nullToStr(cgfkMap.get("sp_flag"));
				role_id = StringUtils.nullToStr(cgfkMap.get("role_id"));
			}
			String[] roles = role_id.split(",");
			//当前用户有采购付款审批权限
			if(userService.isUserInRole(user_id, roles)){
				con += " or yw_type='待审批付款申请'";
			}
			
			
			//费用申请审批权限设置
			Map fysqMap = userService.getSpRight("费用申请");
			sp_flag = "";
			role_id = "";
			if(fysqMap != null){
				sp_flag = StringUtils.nullToStr(fysqMap.get("sp_flag"));
				role_id = StringUtils.nullToStr(fysqMap.get("role_id"));
			}
			String[] fysqRoles = role_id.split(",");
			//当前用户有费用申请审批权限
			if(userService.isUserInRole(user_id, fysqRoles)){
				con += " or yw_type='待审批费用'";
			}
			
			if(!con.equals("")){
				undoWorkPage = firstIndexService.getUndoWorks(con, curPage, rowsPerPage);
			}
			
			return SUCCESS;
		}catch(Exception e){
			log.error("");
			return ERROR;
		}
	}


	public FirstIndexService getFirstIndexService() {
		return firstIndexService;
	}


	public void setFirstIndexService(FirstIndexService firstIndexService) {
		this.firstIndexService = firstIndexService;
	}


	public XxfbNbggService getXxfbNbggService() {
		return xxfbNbggService;
	}


	public void setXxfbNbggService(XxfbNbggService xxfbNbggService) {
		this.xxfbNbggService = xxfbNbggService;
	}


	public MenuService getMenuService() {
		return menuService;
	}


	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}


	public UserService getUserService() {
		return userService;
	}


	public void setUserService(UserService userService) {
		this.userService = userService;
	}


	public List getNbggList() {
		return nbggList;
	}


	public void setNbggList(List nbggList) {
		this.nbggList = nbggList;
	}


	public Page getUndoWorkPage() {
		return undoWorkPage;
	}


	public void setUndoWorkPage(Page undoWorkPage) {
		this.undoWorkPage = undoWorkPage;
	}


	public int getCurPage() {
		return curPage;
	}


	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}


	public List getYwgnList() {
		return ywgnList;
	}


	public void setYwgnList(List ywgnList) {
		this.ywgnList = ywgnList;
	}
}
