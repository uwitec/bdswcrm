package com.sw.cms.service;

import java.util.List;

import com.sw.cms.dao.DeptDAO;
import com.sw.cms.model.Dept;

public class DeptService {
	
	private DeptDAO deptDao;
	
	/**
	 * 部门列表（封装好的部门对象）
	 * @return
	 */
	public List getDepts(){
		return deptDao.getDepts();
	}
	
	/**
	 * 根据部门名称获取部门ID
	 * @param name
	 * @return
	 */
	public List getDeptsByName(String name)
	{
		return deptDao.getDeptsByName(name);
	}
	
	/**
	 * 编辑部门信息
	 * @param dept_id
	 * @return
	 */
	public Dept editDept(String dept_id){
		return deptDao.editDept(dept_id);
	}
	
	/**
	 * 更新部门信息
	 * @param dept
	 */
	public void updateDept(Dept dept){
		deptDao.updateDept(dept);
	}
	
	
	/**
	 * 判断部门信息是否可以删除<BR>
	 * 发生了费用支付的部门信息不能删除<BR>
	 * 
	 * @param dept_id
	 * @return boolean true:可以；false:不可以
	 */
	public boolean isCanDel(String dept_id){
		return deptDao.isCanDel(dept_id);
	}
	
	/**
	 * 判断部门信息是否可以删除<BR>
	 * 有下属部门的部门信息不能删除<BR>
	 * 
	 * @param dept_id
	 * @return boolean true:可以；false:不可以
	 */
	public boolean hasChild(String dept_id){
		return deptDao.hasChild(dept_id);
	}
	
	/**
	 * 删除部门信息
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
