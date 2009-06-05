package com.sw.cms.service;

import java.util.List;
import java.util.Map;

import com.sw.cms.dao.FuncDAO;
import com.sw.cms.dao.RoleDAO;
import com.sw.cms.model.Page;

public class RoleService {
	
	private RoleDAO roleDao;
	private FuncDAO funcDao;
	
	/**
	 * 取角色列表，带分页
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getRoleList(String con,int curPage, int rowsPerPage){		
		return roleDao.getRoleList(con, curPage, rowsPerPage);		
	}
	
	
	/**
	 * 保存角色信息
	 * @param role_name
	 * @param xh
	 */
	public void saveRole(String role_name,int xh){
		roleDao.saveRole(role_name, xh);
	}
	
	
	/**
	 * 更新角色信息
	 * @param role_id
	 * @param role_name
	 * @param xh
	 */
	public void updateRole(String role_id,String role_name,int xh){
		roleDao.updateRole(role_id, role_name, xh);
	}
	
	
	/**
	 * 根据role_id获取角色信息
	 * @param role_id
	 * @return
	 */
	public Map getRoleByID(String role_id){
		return roleDao.getRoleByID(role_id);
	}
	
	
	/**
	 * 删除角色信息
	 * @param role_id
	 */
	public void delRoleByID(String role_id){
		roleDao.delRoleByID(role_id);
	}
	
	
	/**
	 * 获取所有功能列表
	 * @return
	 */
	public List getAllFuncList(){
		return funcDao.getAllFuncList();
	}
	
	
	/**
	 * 根据role_id取功能列表
	 * @param role_id
	 * @return
	 */
	public List getRoleFuncList(String role_id){
		return roleDao.getRoleFuncList(role_id);
	}
	
	
	/**
	 * 保存角色相关功能
	 * @param role_id
	 * @param func_ids
	 */
	public void saveRoleFuncs(String role_id,String[] func_ids){
		roleDao.saveRoleFuncs(role_id, func_ids);
	}
	
	

	public void setRoleDao(RoleDAO roleDao) {
		this.roleDao = roleDao;
	}


	public void setFuncDao(FuncDAO funcDao) {
		this.funcDao = funcDao;
	}

}
