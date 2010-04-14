package com.sw.cms.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.model.Page;
import com.sw.cms.model.SysUser;
import com.sw.cms.service.DeptService;
import com.sw.cms.service.YgbbService;
import com.sw.cms.service.SjzdService;
import com.sw.cms.service.UserService;
import com.sw.cms.util.Constant;
import com.sw.cms.util.DateComFunc;
import com.sw.cms.util.ParameterUtility;

/**
 * 员工报表
 * @author zhj
 *
 */

public class YgbbAction extends BaseAction {
	
	private YgbbService ygbbService;
	private UserService userService;
	private DeptService deptService;
	private SjzdService sjzdService;
	 
	
	private SysUser user = new SysUser();
	private Page employeePage;
		
	private Map userMap;
	
	private List depts = new ArrayList();
	
	private String real_name = "";
	private String dept_id = "";
	
	private String position = "";
	private String employee_id = "";
	
	private String[] positions;
 
	/**
	 * 取得员工列表
	 * @return
	 */
	public String list(){
		
		int curPage = ParameterUtility.getIntParameter(getRequest(), "curPage",1);
		int rowsPerPage = Constant.PAGE_SIZE2;
		
		String con = "";
		if(!real_name.equals("")){
			con += " and real_name like'%" + real_name + "%'";
		}
		if(!dept_id.equals("")){
			con += " and dept='" + dept_id + "'";
		}
		if(!position.equals("")){
			con += " and position='" + position + "'";
		}
		employeePage = ygbbService.getUserList(con, curPage, rowsPerPage);
		positions = sjzdService.getSjzdXmxxByZdId("SJZD_ZWXX");
		depts = deptService.getDepts();
		return "success";
	}
	
	/**
	 * 查询员工信息
	 * @return
	 */
	public String edit(){
		userMap = ygbbService.getUser(employee_id);
		depts = deptService.getDepts();
		positions = sjzdService.getSjzdXmxxByZdId("SJZD_ZWXX");
		
		return "success";
	}
	
		
	public List getDepts() {
		return depts;
	}
	public void setDepts(List depts) {
		this.depts = depts;
	}
	public YgbbService getYgbbService() {
		return ygbbService;
	}
	public void setYgbbService(YgbbService ygbbService) {
		this.ygbbService = ygbbService;
	}
	public SysUser getUser() {
		return user;
	}
	public void setUser(SysUser user) {
		this.user = user;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getReal_name() {
		return real_name;
	}
	public void setReal_name(String real_name) {
		this.real_name = real_name;
	}
	public Map getUserMap() {
		return userMap;
	}
	public void setUserMap(Map userMap) {
		this.userMap = userMap;
	}
	public UserService getUserService() {
		return userService;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	public String getEmployee_id() {
		return employee_id;
	}
	public void setEmployee_id(String employee_id) {
		this.employee_id = employee_id;
	}


	public Page getEmployeePage() {
		return employeePage;
	}


	public void setEmployeePage(Page employeePage) {
		this.employeePage = employeePage;
	}

	public String getDept_id() {
		return dept_id;
	}

	public void setDept_id(String dept_id) {
		this.dept_id = dept_id;
	}

	public DeptService getDeptService() {
		return deptService;
	}

	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}

	public SjzdService getSjzdService() {
		return sjzdService;
	}

	public void setSjzdService(SjzdService sjzdService) {
		this.sjzdService = sjzdService;
	}

	public String[] getPositions() {
		return positions;
	}

	public void setPositions(String[] positions) {
		this.positions = positions;
	}
}
