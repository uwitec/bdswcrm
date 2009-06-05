package com.sw.cms.service;

import java.util.List;

import com.sw.cms.dao.DeptXshzDAO;

/**
 * 部门销售汇总
 * @author liyt
 *
 */
public class DeptXshzService {
	
	private DeptXshzDAO deptXshzDao;
	
	
	/**
	 * 部门销售汇总列表
	 * @param start_date    开始时间
	 * @param end_date      结束时间
	 * @param client_name   客户编号（前台选择，行业客户、分销商等）
	 * @param dj  统计部门等级
	 * @return
	 * @throws Exception
	 */
	public List getResults(String start_date,String end_date,String client_name,int dj) throws Exception{
		return deptXshzDao.getResults(start_date, end_date, client_name, dj);
	}
	
	
	/**
	 * 部门销售汇总--明细表
	 * @param dept        部门编号
	 * @param start_date  起始时间
	 * @param end_date    结束时间
	 * @param client_name 客户编号
	 * @return
	 * @throws Exception
	 */
	public List getMxResults(String dept,String start_date,String end_date,String client_name) throws Exception{
		return deptXshzDao.getMxResults(dept, start_date, end_date, client_name);
	}
	
	
	/**
	 * 部门销售汇总--销售人员销售明细
	 * @param xsry
	 * @param start_date
	 * @param end_date
	 * @param client_name
	 * @return
	 * @throws Exception
	 */
	public List getProductMxResults(String xsry,String start_date,String end_date,String client_name) throws Exception{
		return deptXshzDao.getProductMxResults(xsry, start_date, end_date, client_name);
	}
	

	public DeptXshzDAO getDeptXshzDao() {
		return deptXshzDao;
	}

	public void setDeptXshzDao(DeptXshzDAO deptXshzDao) {
		this.deptXshzDao = deptXshzDao;
	}

}
