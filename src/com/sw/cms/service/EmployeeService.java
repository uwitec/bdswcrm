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


	public EmployeeDAO getEmployeeDao() {
		return employeeDao;
	}


	public void setEmployeeDao(EmployeeDAO employeeDao) {
		this.employeeDao = employeeDao;
	}

}
