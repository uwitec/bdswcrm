package com.sw.cms.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.sw.cms.dao.DeptDAO;
import com.sw.cms.dao.PositionDAO;
import com.sw.cms.dao.RoleDAO;
import com.sw.cms.dao.UserDAO;
import com.sw.cms.model.Page;
import com.sw.cms.model.SysUser;

public class UserService {

	private UserDAO userDao;
	private DeptDAO deptDao;
	private PositionDAO positionDao;
	private RoleDAO roleDao;
	
	/**
	 * 根据用户名及密码取用户信息
	 * 用于登陆验证
	 * @param user_name
	 * @param password
	 * @return
	 */
	public Object getUserByNameAndPass(String user_name,String password){
		return userDao.getUserByNameAndPass(user_name, password);
	}
	
	
	
	/**
	 * 取用户列表（带分页）
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getUserList(String con,int curPage, int rowsPerPage){
		return userDao.getUserList(con, curPage, rowsPerPage);
	}
	
	
	/**
	 * 保存用户信息
	 * @param user
	 */
	public void saveUser(SysUser user){
		userDao.saveUser(user);
		
	}
	
	
	/**
	 * 更新用户信息
	 * @param user
	 */
	public void updateUser(SysUser user){
		userDao.updateUser(user);
	}
	
	
	/**
	 * 根据user_id取用户信息
	 * @param user_id
	 * @return
	 */
	public Object getUser(String user_id){
		return userDao.getUser(user_id);
	}
	
	
	/**
	 * 取所有部门列表
	 * @return
	 */
	public List getAllDeptList(){
		return deptDao.getAllDeptList();
	}
	
	/**
	 * 取所有有职位列表
	 * @return
	 */
	public List getAllPositionList(){
		return positionDao.getAllPositionList();
	}
	
	/**
	 * 删除用户信息
	 * @param user_id
	 */
	public void delUser(String user_id){
		userDao.delUser(user_id);
	}
	
	/**
	 * 获取所有业务员列表
	 * @return
	 */
	public List getAllEmployeeList(){
		return userDao.getAllUserList();
	}
	
	
	/**
	 * 取所有系统用户列表
	 * @return
	 */
	public List getAllSysUserList(){
		return userDao.getAllSysUserList();
	}
	
	/**
	 * 根据条件获取经手人提示信息
	 * @param paramValue
	 * @return
	 */
	public List getUserListAjaxTip(String paramValue){
		return userDao.getUserListAjaxTip(paramValue);
	}
	
	/**
	 * 取系统所有用户列表，返回结果中为USER对象
	 * @return
	 */
	public List getAllSysUserListObj(){
		return userDao.getAllSysUserListObj();
	}
	
	
	/**
	 * 根据条件取业务员列表
	 * @param condition
	 * @return
	 */
	public List getUserListByStr(String condition){
		return userDao.getUserListByStr(condition);
	}
	
	/**
	 * 根据条件取业务员列表
	 * @param con
	 * @return
	 */
	public List getYwryListByCon(String con){
		return userDao.getYwryListByCon(con);
	}
	
	
	/**
	 * 取所有角色列表
	 * @return
	 */
	public List getAllRoles(){
		return roleDao.getAllRoles();
	}
	
	/**
	 * 取具价格审批权限角色列表
	 * @return
	 */
	public String getJgspRoles(){
		return roleDao.getJgspRoles();
	}
	
	/**
	 * 保存价格审批权限角色信息
	 * @param role_id
	 */
	public void saveJgSpRightRoles(String[] role_id){
		roleDao.saveJgSpRightRoles(role_id);
	}
	
	
	/**
	 * 获取超期审批权限角色列表
	 * @return
	 */
	public String getCqspRoles(){
		return roleDao.getCqspRoles();
	}
	
	
	/**
	 * 保存超期审批权限角色信息
	 * @param role_id
	 */
	public void saveCqSpRightRoles(String[] role_id){
		roleDao.saveCqSpRightRoles(role_id);
	}
	
	
	/**
	 * 根据user_id取角色列表
	 * @param user_id
	 * @return
	 */
	public List getUserRoles(String user_id){
		return userDao.getUserRoles(user_id);
	}
	
	
	/**
	 * 保存用户角色
	 * @param user_id
	 * @param role_ids
	 */
	public void saveUserRoles(String user_id,String[] role_ids){
		userDao.saveUserRoles(user_id, role_ids);
	}
	
	/**
	 * 保存销售单审批权限角色信息
	 * @param role_id
	 */
	public void saveXsdSpRightRoles(String[] role_id){
		userDao.saveXsdSpRightRoles(role_id);
	}
	
	
	
	public int getUserByName(String user_name){
		return userDao.getUserByName(user_name);
	}
	
	
	/**
	 * 更新用户末次登录时间
	 * @param user_id
	 */
	public void updateUserLastLogin(String user_id,Date last_login_time,String last_login_ip){
		userDao.updateUserLastLogin(user_id, last_login_time, last_login_ip);
	}
	
	
	/**
	 * 更新用户末次退出信息
	 * @param user_id
	 * @param last_logout_date
	 */
	public void updateUserLastLogout(String user_id,Date last_logout_time){
		userDao.updateUserLastLogout(user_id, last_logout_time);
	}
	
	
	/**
	 * 返回具有价格审批权限的用户列表
	 * @return
	 */
	public List getJgspUsers(){
		return userDao.getJgspUsers();
	}
	
	/**
	 * 返回具有超期审批权限的用户列表
	 * @return
	 */
	public List getCqspUsers(){
		return userDao.getCqspUsers();
	}
	
	
	/**
	 * 返回具有超额审批权限的用户列表
	 * @return
	 */
	public List getCespUsers(){
		return userDao.getCespUsers();
	}
	
	
	/**
	 * 返回具有超额审批及价格审批权限的用户列表
	 * @return
	 */
	public List getCeAndJgSpUsers(){
		return userDao.getCeAndJgSpUsers();	
	}
	
	
	/**
	 * dwr test
	 *
	 */
	public void test(){
		System.out.println("dwr test!");
	}


	public void setUserDao(UserDAO userDao) {
		this.userDao = userDao;
	}



	public void setDeptDao(DeptDAO deptDao) {
		this.deptDao = deptDao;
	}



	public void setPositionDao(PositionDAO positionDao) {
		this.positionDao = positionDao;
	}
	
	
	public void updatePass(String user_name,String password){
		userDao.updatePass(user_name, password);
	}



	public void setRoleDao(RoleDAO roleDao) {
		this.roleDao = roleDao;
	}
	
}
