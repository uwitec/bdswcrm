package com.sw.cms.action;

import java.util.ArrayList;
import java.util.List;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.service.DeptService;
import com.sw.cms.service.UserService;
import com.sw.cms.service.XstjXsryService;

public class XstjXsryAction extends BaseAction {
	
	private XstjXsryService xstjXsryService;
	private UserService userService;
	private DeptService deptService;
	
	private String[] xsry_id = null;
	private String role_sale = "";
	
	private List userList = new ArrayList();
	private List deptList = new ArrayList();
	
	private String dept_id = "";
	private String user_id = "";
	
	public String showCondition(){
		userList = userService.getAllEmployeeList();
		deptList = deptService.getDepts();
		return "success";
	}
	
	public String getResult(){
		String condition = "";
		if(xsry_id != null && xsry_id.length>0){
			for(int i=0;i<xsry_id.length;i++){
				if(condition.equals("")){
					condition += "'" + xsry_id[i] + "'";
				}else{
					condition += ",'" + xsry_id[i] + "'";
				}
			}
		}
		userList = userService.getUserListByStr(condition);
		return "success";
	}
	
	/**
	 * 考核毛利汇总结果
	 * @return
	 */
	public String getKhmlhzResult(){
		String con = "";
		
		if(!dept_id.equals("")){
			con += " and dept like '" + dept_id + "%'";
		}
		if(!user_id.equals("")){
			con += " and user_id='" + user_id + "'";
		}
		userList = userService.getYwryListByCon(con);
		return "success";
	}

	public XstjXsryService getXstjXsryService() {
		return xstjXsryService;
	}

	public void setXstjXsryService(XstjXsryService xstjXsryService) {
		this.xstjXsryService = xstjXsryService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public List getUserList() {
		return userList;
	}

	public void setUserList(List userList) {
		this.userList = userList;
	}

	public String[] getXsry_id() {
		return xsry_id;
	}

	public void setXsry_id(String[] xsry_id) {
		this.xsry_id = xsry_id;
	}

	public String getRole_sale() {
		return role_sale;
	}

	public void setRole_sale(String role_sale) {
		this.role_sale = role_sale;
	}

	public List getDeptList() {
		return deptList;
	}

	public void setDeptList(List deptList) {
		this.deptList = deptList;
	}

	public DeptService getDeptService() {
		return deptService;
	}

	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}

	public String getDept_id() {
		return dept_id;
	}

	public void setDept_id(String dept_id) {
		this.dept_id = dept_id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

}
