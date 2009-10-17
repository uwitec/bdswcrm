package com.sw.cms.service;

import java.util.HashMap;
import java.util.List;

import com.sw.cms.dao.XstjXsryDAO;

public class XstjXsryService {
	
	private XstjXsryDAO xstjXsryDao;
	
	/**
	 * 取销售单列表
	 * @param start_date
	 * @param end_date
	 * @param xsry_id
	 * @return
	 */
	public List getXsdList(String start_date,String end_date,String xsry_id,String client_name,String dj_id){
		return xstjXsryDao.getXsdList(start_date, end_date, xsry_id,client_name,dj_id);
	}
	
	
	/**
	 * 根据销售单编号取销售明细信息
	 * @param xsd_id
	 * @return
	 */
	public List getXsdMxList(String xsd_id){
		return xstjXsryDao.getXsdMxList(xsd_id);
	}
	
	
	/**
	 * 根据查询条件取零售单列表
	 * @param start_date 开始时间
	 * @param end_date  截止时间
	 * @param client_id  客户名称
	 * @return
	 */
	public List getLsdList(String start_date,String end_date,String xsry_id,String client_name,String dj_id){
		return xstjXsryDao.getLsdList(start_date, end_date, xsry_id,client_name,dj_id);
	}
	
	
	/**
	 * 根据零售单编号取零售单元销售明细
	 * @param lsd_id
	 * @return
	 */
	public List getLsdMxList(String lsd_id){
		return xstjXsryDao.getLsdMxList(lsd_id);
	}
	
	
	/**
	 * 取退货单列表
	 * @param start_date
	 * @param end_date
	 * @param client_name
	 * @return
	 */
	public List getThdList(String start_date,String end_date,String xsry_id,String client_name,String dj_id){
		return xstjXsryDao.getThdList(start_date, end_date, xsry_id,client_name,dj_id);
	}
	
	
	/**
	 * 根据退货单编号取退货单明细
	 * @param thd_id
	 * @return
	 */
	public List getThdMxList(String thd_id){
		return xstjXsryDao.getThdMxList(thd_id);
	}	
	
	
	/**
	 * 业务员毛利汇总(销售收入、成本、考核成本)
	 * @param start_date
	 * @param end_date
	 * @return
	 */
	public HashMap<String,Double> getMlHz(String xsry_id,String start_date,String end_date){
		return xstjXsryDao.getMlHz(xsry_id, start_date, end_date);
	}
	
	
	/**
	 * 业务员考核毛利汇总
	 * @param start_date   开始时间
	 * @param end_date     结束时间
	 * @param dept_id      部门编号
	 * @param user_id      销售人员
	 * @return
	 */
	public List getYwymlHz(String start_date,String end_date,String dept_id,String user_id){
		return xstjXsryDao.getYwymlHz(start_date, end_date, dept_id, user_id);
	}
	
	
	/**
	 * 业务员毛利汇总明细
	 * @param start_date
	 * @param end_date
	 * @param user_id
	 * @return
	 */
	public List getYwymlMx(String start_date,String end_date,String user_id){
		return xstjXsryDao.getYwymlMx(start_date, end_date, user_id);
	}

	public XstjXsryDAO getXstjXsryDao() {
		return xstjXsryDao;
	}

	public void setXstjXsryDao(XstjXsryDAO xstjXsryDao) {
		this.xstjXsryDao = xstjXsryDao;
	}

}
