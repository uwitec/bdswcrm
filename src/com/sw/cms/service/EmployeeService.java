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
     
	/**
	 * ϵͳ����Ա���Կ������е�Ա����Ϣ����ϵͳ����Աֻ�ܿ���δɾ����δ��ְ��Ա����Ϣ
	 * ȡԱ���б�����ҳ��
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
	 * ��ԭɾ�����û���Ϣ
	 * @param user_id
	 * @param flag
	 */
	public void updateEmployeeFlag(String user_id,String flag)
	{
		employeeDao.updateEmployeeFlag(user_id,flag);
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
