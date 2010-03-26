package com.sw.cms.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.model.Page;
import com.sw.cms.model.SysUser;
import com.sw.cms.service.DeptService;
import com.sw.cms.service.EmployeeService;
import com.sw.cms.service.SjzdService;
import com.sw.cms.service.UserService;
import com.sw.cms.util.Constant;
import com.sw.cms.util.DateComFunc;
import com.sw.cms.util.ParameterUtility;

/**
 * 员工管理
 * @author liyt
 *
 */

public class EmployeeAction extends BaseAction {
	
	private EmployeeService employeeService;
	private UserService userService;
	private DeptService deptService;
	private SjzdService sjzdService;
	 
	
	private SysUser user = new SysUser();
	private Page employeePage;
	private Page ywyEmployeePage;//修改
	
	private Map userMap;
	
	private List depts = new ArrayList();
	
	private String real_name = "";
	private String dept_id = "";
	private String position = "";
	private String employee_id = "";
	
	private String[] positions;
 
	
	/**
	 * 打开员工管理框架
	 * @return
	 */
	public String showFrame(){
		return "success";
	}
	
	/**
	 * 取得用户列表
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
		employeePage = employeeService.getUserList(con, curPage, rowsPerPage);
		positions = sjzdService.getSjzdXmxxByZdId("SJZD_ZWXX");
		
		return "success";
	}
	
	/** 取业务员列表 
	 * 
	 */
	public String selYwyEmployee()
	{
		 
		try
		{
			int curPage = ParameterUtility.getIntParameter(getRequest(), "curPage",1);
			String ywyname=ParameterUtility.getStringParameter(getRequest(), "name","");
			String ywydept=ParameterUtility.getStringParameter(getRequest(), "dept","");
			String ywyposition=ParameterUtility.getStringParameter(getRequest(),"position","");
			int rowsPerPage = 15;
			String con="";
			if(!ywyname.equals(""))
			{
				con+=" and a.real_name like '%"+ywyname+"%'";
			}
			if(!ywydept.equals(""))
			{
				con+=" and b.dept_id='"+ywydept+"'";
			}
			if(!ywyposition.equals(""))
			{
				con+=" and a.position='"+ywyposition+"'";
			}
			ywyEmployeePage=employeeService.getYwyEmployee(con, curPage, rowsPerPage);
			positions = sjzdService.getSjzdXmxxByZdId("SJZD_ZWXX");
			depts = deptService.getDepts();
			return "success";
		}
		catch(Exception e)
		{
			log.error("获取业务员列表  失败原因"+e.getMessage());
			return "error";
		}
	}
	
	
	/**
	 * 打开添加页面
	 * @return
	 */
	public String add(){
		positions = sjzdService.getSjzdXmxxByZdId("SJZD_ZWXX");
		user.setUser_id(employeeService.updateUserId());
		return "success";
	}
	
	
	/**
	 * 保存用户信息
	 * @return
	 */
	public String save(){
		if(user.getIs_ywy().equals("是"))
		{
			int count=employeeService.getEmployeeByNameIsExist(user.getReal_name());
			if(count>0)
			{
			   getSession().setAttribute("MSG", "员工姓名已存在，请选用其它姓名！");
			   positions = sjzdService.getSjzdXmxxByZdId("SJZD_ZWXX");
			   dept_id=user.getDept();
			   return "input";
			}
		}
		
		user.setGl(DateComFunc.getYearSub(user.getRzrq(),DateComFunc.getToday()));
		employeeService.saveUser(user);
		return "success";
	}
	
	/**
	 * 查询用户信息
	 * @return
	 */
	public String edit(){
		userMap = employeeService.getUser(employee_id);
		depts = deptService.getDepts();
		positions = sjzdService.getSjzdXmxxByZdId("SJZD_ZWXX");
		
		return "success";
	}
	
	/**
	 * 更新用户信息
	 * @return
	 */
	public String update(){	
		
		if(user.getIs_ywy().equals("是"))
		{
			 userMap=employeeService.getUser(user.getUser_id());
			 if(!userMap.get("real_name").toString().equals(user.getReal_name()))
			 {
			
			     int count=employeeService.getEmployeeByNameIsExist(user.getReal_name());
			     if(count>0)
			     {
			    	  
			        getSession().setAttribute("MSG", "员工姓名已存在，请选用其它姓名！");
			        userMap=employeeService.getUser(user.getUser_id());
			         
			        depts = deptService.getDepts();
			        positions = sjzdService.getSjzdXmxxByZdId("SJZD_ZWXX");
			        return "input";
			    }
			 }
		}
		user.setGl(DateComFunc.getYearSub(user.getRzrq(),DateComFunc.getToday()));
		employeeService.updateUser(user);
		return "success";
	}
	
	
	/**
	 * 删除用户信息
	 * @return
	 */
	public String del(){
		employeeService.delUser(employee_id);
		return "success";
	}
	
	
	public List getDepts() {
		return depts;
	}
	public void setDepts(List depts) {
		this.depts = depts;
	}
	public EmployeeService getEmployeeService() {
		return employeeService;
	}
	public void setEmployeeService(EmployeeService employeeService) {
		this.employeeService = employeeService;
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

	public Page getYwyEmployeePage() {
		return ywyEmployeePage;
	}

	public void setYwyEmployeePage(Page ywyEmployeePage) {
		this.ywyEmployeePage = ywyEmployeePage;
	}

}
