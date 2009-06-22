package com.sw.cms.service;

import java.util.Map;

import com.sw.cms.dao.EmployeeDAO;
import com.sw.cms.model.Page;
import com.sw.cms.model.SysUser;


public class EmployeeService {
	
	private EmployeeDAO employeeDao;
	
	/**
	 * ȡԱ���б�����ҳ��
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getUserList(String con,int curPage, int rowsPerPage){
		return employeeDao.getUserList(con, curPage, rowsPerPage);
	}
    //�޸�----------------------------------------------------------------	
	/**
	 * ȡҵ��Ա�б�����ҳ��
	 * @param con
	 * @param curPage
	 * @param rowPerPage
	 * @return
	 */
	public Page getYwyEmployee(String con,int curPage,int rowPerPage)
	{
	    return employeeDao.getYwyEmployee(con, curPage, rowPerPage);
	}
	//�޸�--------------------------------------------------------------
	
	/**
	 * �����û���Ϣ
	 * @param user
	 */
	public void saveUser(SysUser user){
		employeeDao.saveUser(user);
		
	}
	
	
	/**
	 * �����û���Ϣ
	 * @param user
	 */
	public void updateUser(SysUser user){
		employeeDao.updateUser(user);
	}
	
	
	/**
	 * ����user_idȡ�û���Ϣ
	 * @param user_id
	 * @return
	 */
	public Map getUser(String user_id){
		return employeeDao.getUser(user_id);
	}
	
	
	/**
	 * ɾ���û���Ϣ
	 * @param user_id
	 */
	public void delUser(String user_id){
		employeeDao.delUser(user_id);
	}
	
	/**
	 * �ж�Ա�������Ƿ���ͬ 
	 * @param employeeName
	 * @return
	 */
	public int getEmployeeByNameIsExist(String employeeName)
	{
		 return employeeDao.getEmployeeByNameIsExist(employeeName);
	}


	public EmployeeDAO getEmployeeDao() {
		return employeeDao;
	}


	public void setEmployeeDao(EmployeeDAO employeeDao) {
		this.employeeDao = employeeDao;
	}

}
