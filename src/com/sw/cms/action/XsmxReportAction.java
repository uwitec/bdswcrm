package com.sw.cms.action;

import java.util.ArrayList;
import java.util.List;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.service.DeptService;
import com.sw.cms.service.UserService;
import com.sw.cms.service.XsmxReportService;

public class XsmxReportAction extends BaseAction {
	
	private XsmxReportService xsmxReportService;
	private UserService userService;
	private DeptService deptService;
	
	private List userList = new ArrayList();
	private List deptList = new ArrayList();

	public XsmxReportService getXsmxReportService() {
		return xsmxReportService;
	}
	
	public String showCondition(){
		userList = userService.getAllEmployeeList();
		deptList = deptService.getDepts();
		return "success";
	}
	
	public String getResult(){
		return "success";
	}

	public void setXsmxReportService(XsmxReportService xsmxReportService) {
		this.xsmxReportService = xsmxReportService;
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

}
