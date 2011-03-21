package com.sw.cms.service;

import java.util.Map;

import com.sw.cms.dao.EmployeeDAO;
import com.sw.cms.model.Page;
import com.sw.cms.model.SysUser;


public class EmployeeService {
	
	private EmployeeDAO employeeDao;
	
	/**
	 * 取员工列表（带分页）
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getUserList(String con,int curPage, int rowsPerPage){
		return employeeDao.getUserList(con, curPage, rowsPerPage);
	}
     
	/**
	 * 系统管理员可以看到所有的员工信息，非系统管理员只能看到未删除或未离职的员工信息
	 * 取员工列表（带分页）
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getUserListFb(String con,int curPage, int rowsPerPage,String user_id){
		if(user_id.equals("AD00000001"))
		{
		   return employeeDao.getUserListAll(con, curPage, rowsPerPage);
		}
		else
		{
		  return employeeDao.getUserList(con, curPage, rowsPerPage);
		}
	}
	
	/**
	 * 取业务员列表（带分页）
	 * @param con
	 * @param curPage
	 * @param rowPerPage
	 * @return
	 */
	public Page getYwyEmployee(String con,int curPage,int rowPerPage)
	{
	    return employeeDao.getYwyEmployee(con, curPage, rowPerPage);
	}
	 
	/**
	 * 保存用户信息
	 * @param user
	 */
	public void saveUser(SysUser user){
		employeeDao.saveUser(user);
		
	}
	
	
	/**
	 * 更新用户信息
	 * @param user
	 */
	public void updateUser(SysUser user){
		employeeDao.updateUser(user);
	}
	
	
	/**
	 * 根据user_id取用户信息
	 * @param user_id
	 * @return
	 */
	public Map getUser(String user_id){
		return employeeDao.getUser(user_id);
	}
	
	/**
	 * 删除用户信息
	 * @param user_id
	 */
	public void delUser(String user_id){
		employeeDao.delUser(user_id);
	}
	
	/**
	 * 还原删除的用户信息
	 * @param user_id
	 * @param flag
	 */
	public void updateEmployeeFlag(String user_id,String flag)
	{
		employeeDao.updateEmployeeFlag(user_id,flag);
	}
	
	/**
	 * 判断员工姓名是否相同 
	 * @param employeeName
	 * @return
	 */
	public int getEmployeeByNameIsExist(String employeeName)
	{
		 return employeeDao.getEmployeeByNameIsExist(employeeName);
	}

	public String updateUserId() {
		return employeeDao.getUserID();
	}

	public EmployeeDAO getEmployeeDao() {
		return employeeDao;
	}


	public void setEmployeeDao(EmployeeDAO employeeDao) {
		this.employeeDao = employeeDao;
	}

}
