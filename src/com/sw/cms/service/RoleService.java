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
	 * ȡ��ɫ�б�����ҳ
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getRoleList(String con,int curPage, int rowsPerPage){		
		return roleDao.getRoleList(con, curPage, rowsPerPage);		
	}
	
	
	/**
	 * �����ɫ��Ϣ
	 * @param role_name
	 * @param xh
	 */
	public void saveRole(String role_name,int xh){
		roleDao.saveRole(role_name, xh);
	}
	
	
	/**
	 * ���½�ɫ��Ϣ
	 * @param role_id
	 * @param role_name
	 * @param xh
	 */
	public void updateRole(String role_id,String role_name,int xh){
		roleDao.updateRole(role_id, role_name, xh);
	}
	
	
	/**
	 * ����role_id��ȡ��ɫ��Ϣ
	 * @param role_id
	 * @return
	 */
	public Map getRoleByID(String role_id){
		return roleDao.getRoleByID(role_id);
	}
	
	
	/**
	 * ɾ����ɫ��Ϣ
	 * @param role_id
	 */
	public void delRoleByID(String role_id){
		roleDao.delRoleByID(role_id);
	}
	
	
	/**
	 * ��ȡ���й����б�
	 * @return
	 */
	public List getAllFuncList(){
		return funcDao.getAllFuncList();
	}
	
	
	/**
	 * ����role_idȡ�����б�
	 * @param role_id
	 * @return
	 */
	public List getRoleFuncList(String role_id){
		return roleDao.getRoleFuncList(role_id);
	}
	
	
	/**
	 * �����ɫ��ع���
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
