package com.sw.cms.action;

import java.util.ArrayList;
import java.util.List;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.service.DeptService;
import com.sw.cms.service.FytjReportService;
import com.sw.cms.service.SjzdService;
import com.sw.cms.service.UserService;

public class FytjReportAction extends BaseAction {
	
	private FytjReportService fytjReportService;
	private UserService userService;
	private DeptService deptService;
	private SjzdService sjzdService;
	
	private List userList = new ArrayList();
	private List deptList = new ArrayList();
	private String[] fy_types;
	
	private List reustls = new ArrayList();
	
	private String start_date = "";
	private String end_date = "";
	private String dept = "";
	private String fy_type = "";
	private int dj;
	
	/**
	 * 查询条件页面
	 * @return
	 */
	public String showCondition(){
		userList = userService.getAllEmployeeList();  //业务员列表
		deptList = deptService.getDepts();   //部门列表
		fy_types = sjzdService.getSjzdXmxxByZdId("SJZD_ZCLX");  //费用支出类型
		return "success";
	}
	
	/**
	 * 费用分类汇总统计结果
	 * @return
	 */
	public String getResutls(){
		reustls = fytjReportService.getFytjResult(start_date, end_date, dept, dj);
		return "success";
	}
	
	/**
	 * 部门费用汇总统计结果
	 * @return
	 */
	public String getDeptResults(){
		reustls = fytjReportService.getDeptFytjResult(start_date, end_date, fy_type, dj);
		return SUCCESS;
	}
	
	/**
	 * 费用统计结果明细
	 * @return
	 */
	public String getResultMx(){
		reustls = fytjReportService.getFytjList(start_date, end_date, dept, fy_type);
		return SUCCESS;
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
	public FytjReportService getFytjReportService() {
		return fytjReportService;
	}
	public void setFytjReportService(FytjReportService fytjReportService) {
		this.fytjReportService = fytjReportService;
	}
	public SjzdService getSjzdService() {
		return sjzdService;
	}
	public void setSjzdService(SjzdService sjzdService) {
		this.sjzdService = sjzdService;
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

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getEnd_date() {
		return end_date;
	}

	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}

	public String getFy_type() {
		return fy_type;
	}

	public void setFy_type(String fy_type) {
		this.fy_type = fy_type;
	}

	public String[] getFy_types() {
		return fy_types;
	}

	public void setFy_types(String[] fy_types) {
		this.fy_types = fy_types;
	}

	public String getStart_date() {
		return start_date;
	}

	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}

	public List getReustls() {
		return reustls;
	}

	public void setReustls(List reustls) {
		this.reustls = reustls;
	}

	public int getDj() {
		return dj;
	}

	public void setDj(int dj) {
		this.dj = dj;
	}

}
