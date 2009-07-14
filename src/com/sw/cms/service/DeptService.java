package com.sw.cms.service;

import java.util.List;

import com.sw.cms.dao.DeptDAO;
import com.sw.cms.model.Dept;

public class DeptService {
	
	private DeptDAO deptDao;
	
	/**
	 * �����б���װ�õĲ��Ŷ���
	 * @return
	 */
	public List getDepts(){
		return deptDao.getDepts();
	}
	
	/**
	 * ���ݲ������ƻ�ȡ����ID
	 * @param name
	 * @return
	 */
	public List getDeptsByName(String name)
	{
		return deptDao.getDeptsByName(name);
	}
	
	/**
	 * �༭������Ϣ
	 * @param dept_id
	 * @return
	 */
	public Dept editDept(String dept_id){
		return deptDao.editDept(dept_id);
	}
	
	/**
	 * ���²�����Ϣ
	 * @param dept
	 */
	public void updateDept(Dept dept){
		deptDao.updateDept(dept);
	}
	
	
	/**
	 * ɾ��������Ϣ
	 * @param dept_id
	 */
	public void delDept(String dept_id){
		deptDao.delDept(dept_id);
	}

	public DeptDAO getDeptDao() {
		return deptDao;
	}

	public void setDeptDao(DeptDAO deptDao) {
		this.deptDao = deptDao;
	}

}
