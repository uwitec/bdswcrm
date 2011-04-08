package com.sw.cms.service;

import java.util.List;

import com.sw.cms.dao.XsmxReportDAO;

public class XsmxReportService {
	
	private XsmxReportDAO xsmxReportDao;
	
	/**
	 * 取销售单列表
	 * @param start_date 开始时间
	 * @param end_date  截止时间
	 * @param client_id  客户名称
	 * @return
	 */
	public List getXsdList(String start_date,String end_date,String client_name,String xsry_id,String dj_id,String dept_id){
		return xsmxReportDao.getXsdList(start_date, end_date, client_name,xsry_id,dj_id,dept_id);
	}
	
	
	/**
	 * 根据销售单编号取销售明细信息
	 * @param xsd_id
	 * @return
	 */
	public List getXsdMxList(String xsd_id){
		return xsmxReportDao.getXsdMxList(xsd_id);
	}
	
	
	/**
	 * 根据查询条件取零售单列表
	 * @param start_date 开始时间
	 * @param end_date  截止时间
	 * @param client_id  客户名称
	 * @return
	 */
	public List getLsdList(String start_date,String end_date,String client_name,String xsry_id,String dj_id,String dept_id){
		return xsmxReportDao.getLsdList(start_date, end_date, client_name,xsry_id,dj_id,dept_id);
	}
	
	
	/**
	 * 根据零售单编号取零售单元销售明细
	 * @param lsd_id
	 * @return
	 */
	public List getLsdMxList(String lsd_id){
		return xsmxReportDao.getLsdMxList(lsd_id);
	}
	
	
	/**
	 * 取退货单列表
	 * @param start_date
	 * @param end_date
	 * @param client_name
	 * @return
	 */
	public List getThdList(String start_date,String end_date,String client_name,String xsry_id,String dj_id,String dept_id){
		return xsmxReportDao.getThdList(start_date, end_date, client_name,xsry_id,dj_id,dept_id);
	}
	
	
	/**
	 * 根据退货单编号取退货单明细
	 * @param thd_id
	 * @return
	 */
	public List getThdMxList(String thd_id){
		return xsmxReportDao.getThdMxList(thd_id);
	}
	
	
	/**
	 * 取未收单据列表
	 * @param start_date 开始时间
	 * @param end_date 结束时间
	 * @param dept_id 部门编号
	 * @param xsry_id 销售人员
	 * @param khjl 客户经理
	 * @return
	 */
	public List getWsdjKhjlList(String start_date,String end_date,String dept_id,String xsry_id,String khjl,String client_name,String cq_flag){
		return xsmxReportDao.getWsdjKhjlList(start_date, end_date, dept_id, xsry_id,khjl,client_name,cq_flag);
	}

	/**
	 * 取未收单据列表
	 * @param start_date 开始时间
	 * @param end_date 结束时间
	 * @param dept_id 部门编号
	 * @param xsry_id 销售人员
	 * 
	 * @return
	 */
	public List getWsdjList(String start_date,String end_date,String dept_id,String xsry_id,String client_name,String cq_flag){
		return xsmxReportDao.getWsdjList(start_date, end_date, dept_id, xsry_id,client_name,cq_flag);
	}
	
	public XsmxReportDAO getXsmxReportDao() {
		return xsmxReportDao;
	}

	public void setXsmxReportDao(XsmxReportDAO xsmxReportDao) {
		this.xsmxReportDao = xsmxReportDao;
	}

}
