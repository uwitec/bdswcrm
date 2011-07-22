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
	 * �����û���������ȡ�û���Ϣ
	 * ���ڵ�½��֤
	 * @param user_name
	 * @param password
	 * @return
	 */
	public Object getUserByNameAndPass(String user_name,String password){
		return userDao.getUserByNameAndPass(user_name, password);
	}
	
	
	
	/**
	 * ȡ�û��б�����ҳ��
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getUserList(String con,int curPage, int rowsPerPage){
		return userDao.getUserList(con, curPage, rowsPerPage);
	}
	
	
	/**
	 * �����û���Ϣ
	 * @param user
	 */
	public void saveUser(SysUser user){
		userDao.saveUser(user);
		
	}
	
	
	/**
	 * �����û���Ϣ
	 * @param user
	 */
	public void updateUser(SysUser user){
		userDao.updateUser(user);
	}
	
	
	/**
	 * ����user_idȡ�û���Ϣ
	 * @param user_id
	 * @return
	 */
	public Object getUser(String user_id){
		return userDao.getUser(user_id);
	}
	
	
	/**
	 * ȡ���в����б�
	 * @return
	 */
	public List getAllDeptList(){
		return deptDao.getAllDeptList();
	}
	
	/**
	 * ȡ������ְλ�б�
	 * @return
	 */
	public List getAllPositionList(){
		return positionDao.getAllPositionList();
	}
	
	/**
	 * ɾ���û���Ϣ
	 * @param user_id
	 */
	public void delUser(String user_id){
		userDao.delUser(user_id);
	}
	
	/**
	 * ��ԭɾ�����û���Ϣ
	 * @param user_id
	 */
	public void restoreUser(String user_id,String flag){
		userDao.restoreUser(user_id,flag);
	}
	
	/**
	 * ��ȡ����ҵ��Ա�б�
	 * @return
	 */
	public List getAllEmployeeList(){
		return userDao.getAllUserList();
	}
	
	
	/**
	 * ȡ����ϵͳ�û��б�
	 * @return
	 */
	public List getAllSysUserList(){
		return userDao.getAllSysUserList();
	}
	
	/**
	 * ����������ȡ��������ʾ��Ϣ
	 * @param paramValue
	 * @return
	 */
	public List getUserListAjaxTip(String paramValue){
		return userDao.getUserListAjaxTip(paramValue);
	}
	
	/**
	 * ȡϵͳ�����û��б����ؽ����ΪUSER����
	 * @return
	 */
	public List getAllSysUserListObj(){
		return userDao.getAllSysUserListObj();
	}
	
	
	/**
	 * ��������ȡҵ��Ա�б�
	 * @param condition
	 * @return
	 */
	public List getUserListByStr(String condition){
		return userDao.getUserListByStr(condition);
	}
	
	/**
	 * ��������ȡҵ��Ա�б�
	 * @param con
	 * @return
	 */
	public List getYwryListByCon(String con){
		return userDao.getYwryListByCon(con);
	}
	
	
	/**
	 * ȡ���н�ɫ�б�
	 * @return
	 */
	public List getAllRoles(){
		return roleDao.getAllRoles();
	}
	
	/**
	 * ȡ�߼۸�����Ȩ�޽�ɫ�б�
	 * @return
	 */
	public String getJgspRoles(){
		return roleDao.getJgspRoles();
	}
	
	/**
	 * ����۸�����Ȩ�޽�ɫ��Ϣ
	 * @param role_id
	 */
	public void saveJgSpRightRoles(String[] role_id){
		roleDao.saveJgSpRightRoles(role_id);
	}
	
	
	/**
	 * ��ȡ��������Ȩ�޽�ɫ�б�
	 * @return
	 */
	public String getCqspRoles(){
		return roleDao.getCqspRoles();
	}
	
	
	/**
	 * ���泬������Ȩ�޽�ɫ��Ϣ
	 * @param role_id
	 */
	public void saveCqSpRightRoles(String[] role_id){
		roleDao.saveCqSpRightRoles(role_id);
	}
	
	
	/**
	 * ����user_idȡ��ɫ�б�
	 * @param user_id
	 * @return
	 */
	public List getUserRoles(String user_id){
		return userDao.getUserRoles(user_id);
	}
	
	
	/**
	 * �����û���ɫ
	 * @param user_id
	 * @param role_ids
	 */
	public void saveUserRoles(String user_id,String[] role_ids){
		userDao.saveUserRoles(user_id, role_ids);
	}
	
	/**
	 * �������۵�����Ȩ�޽�ɫ��Ϣ
	 * @param role_id
	 */
	public void saveXsdSpRightRoles(String[] role_id){
		userDao.saveXsdSpRightRoles(role_id);
	}
	
	
	
	public int getUserByName(String user_name){
		return userDao.getUserByName(user_name);
	}
	
	
	/**
	 * �����û�ĩ�ε�¼ʱ��
	 * @param user_id
	 */
	public void updateUserLastLogin(String user_id,Date last_login_time,String last_login_ip){
		userDao.updateUserLastLogin(user_id, last_login_time, last_login_ip);
	}
	
	
	/**
	 * �����û�ĩ���˳���Ϣ
	 * @param user_id
	 * @param last_logout_date
	 */
	public void updateUserLastLogout(String user_id,Date last_logout_time){
		userDao.updateUserLastLogout(user_id, last_logout_time);
	}
	
	
	/**
	 * ���ؾ��м۸�����Ȩ�޵��û��б�
	 * @return
	 */
	public List getJgspUsers(){
		return userDao.getJgspUsers();
	}
	
	/**
	 * ���ؾ��г�������Ȩ�޵��û��б�
	 * @return
	 */
	public List getCqspUsers(){
		return userDao.getCqspUsers();
	}
	
	
	/**
	 * ���ؾ��г�������Ȩ�޵��û��б�
	 * @return
	 */
	public List getCespUsers(){
		return userDao.getCespUsers();
	}
	
	
	/**
	 * ���ؾ��г����������۸�����Ȩ�޵��û��б�
	 * @return
	 */
	public List getCeAndJgSpUsers(){
		return userDao.getCeAndJgSpUsers();	
	}
	
	
	/**
	 * �жϵ�ǰ�û��Ƿ��ڽ�ɫ����
	 * @param con
	 * @return
	 */
	public boolean isUserInRole(String user_id, String[] roles){
		if(user_id.equals("")){
			return false;
		}
		String con = "";
		if(roles != null && roles.length > 0){
			con += " and(";
			for(int i=0;i<roles.length;i++){
				if(i == 0){
					con += "b.role_id='" + roles[i] + "'";
				}else{
					con += " or b.role_id='" + roles[i] + "'";
				}
			}
			con += ")";
		}
		
		con += " and a.user_id='" + user_id + "'";
		return userDao.isUserInRole(con);
	}
	
	/**
	 * ��ȡ���������Ϣ
	 * @return
	 */
	public Map getSpRight(String yw_type){
		return roleDao.getSpRight(yw_type);
	}
	
	/**
	 * ��ȡǿ�����к������Ϣ
	 * @return
	 */
	public Map getQzxlhRight(){
		return userDao.getQzxlhRight();
	}
	
	
	/**
	 * ��ȡǿ�����кŵı�־
	 * @return
	 */
	public String getQzxlh(){
		return userDao.getQzxlh();
	}
	
	/**
	 * �������������Ϣ
	 * @param sp_flag
	 * @param role_id
	 */
	public void saveSpRight(String sp_flag,String[] role_id,String yw_type){
		String strRole = "";
		if(role_id != null && role_id.length>0){
			for(int i=0;i<role_id.length;i++){
				if(strRole.equals("")){
					strRole = role_id[i];
				}else{
					strRole += "," + role_id[i];
				}
			}
		}
		roleDao.saveSpRight(sp_flag, strRole, yw_type);
	}
	
	/**
	 * ����ǿ�����к������Ϣ
	 * @param sp_flag
	 * @param role_id
	 */
	public void saveQzxlhRight(String sp_flag){
		
		userDao.saveQzxlhRight(sp_flag);
	}
	
	/**
	 * �ж��û��Ƿ���ĳ���ܲ���Ȩ��
	 * @param func_id
	 * @param user_id
	 * @return
	 */
	public boolean isHasRightFuncs(String func_id,String user_id){
		return userDao.isHasRightFuncs(func_id, user_id);
	}
	
	/**
	 * �ж��û��Ƿ��м۸�����Ȩ��
	 * @return
	 */
	public boolean isHashJgspRight(String user_id){
		return userDao.isHasJgspRight(user_id);
	}
	
	/**
	 * �ж��û��Ƿ��г�������Ȩ��
	 * @return
	 */
	public boolean isHasCqspRight(String user_id){
		return userDao.isHasCqspRight(user_id);
	}
	
	/**
	 * �ж��û��Ƿ��г�������Ȩ��
	 * @return
	 */
	public boolean isHasCespRight(String user_id){
		return userDao.isHasCespRight(user_id);
	}
	
	/**
	 * �ж��û��Ƿ��г��ڶ�۸�����Ȩ��
	 * @return
	 */
	public boolean isHasCeAndJgspRight(String user_id){
		return userDao.isHasCeAndJgspRight(user_id);
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
