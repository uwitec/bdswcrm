package com.sw.cms.service;

import java.util.List;

import com.sw.cms.dao.SysInitSetDAO;
import com.sw.cms.dao.XsmxReportDAO;
import com.sw.cms.dao.YsmxReportDAO;

public class YsmxReportService {
	
	private YsmxReportDAO ysmxReprotDao;
	private SysInitSetDAO sysInitSetDao;
	private XsmxReportDAO xsmxReportDao;
	
	
	/**
	 * 根据客户编号及日期取期初数
	 * @param client_name
	 * @param cdate
	 * @return  期初应收额，如无返回0
	 */
	public double getQc(String client_name,String cdate){
		String xtqyrq = sysInitSetDao.getSysInitSet().getQyrq(); //系统启用日期
		if(xtqyrq == null){
			xtqyrq = "";
		}
		
		//如果用户选择日期小于系统启用日期
		if(cdate.compareTo(xtqyrq)<0){
			cdate = xtqyrq;
		}
		return ysmxReprotDao.getQc(client_name, cdate);
	}
	
	
	/**
	 * 根据客户编号及起始日期取本期发生数
	 * @param client_name
	 * @param start_date
	 * @param end_date
	 * @return
	 */
	public double getBqfs(String client_name,String start_date,String end_date){
		return ysmxReprotDao.getBqfs(client_name, start_date, end_date);
	}
	
	
	/**
	 * 根据客户编号及起始日期取本期已收
	 * @param client_name
	 * @param start_date
	 * @param end_date
	 * @return
	 */
	public double getBqyishou(String client_name,String start_date,String end_date){
		return ysmxReprotDao.getBqyishou(client_name, start_date, end_date);
	}
	
	/**
	 * 根据条件取销售明细列表
	 * @param client_name
	 * @param start_date
	 * @param end_date
	 * @return
	 */
	public List getYsMxList(String client_name,String start_date,String end_date){
		return ysmxReprotDao.getYsMxList(client_name, start_date, end_date);
	}
	
	
	/**
	 * 取未收单据列表
	 * @param start_date 开始时间
	 * @param end_date 结束时间
	 * @param dept_id 部门编号
	 * @param xsry_id 销售人员
	 * @return
	 */
	public List getWsdjList(String start_date,String end_date,String dept_id,String xsry_id,String client_name){
		return xsmxReportDao.getWsdjList(start_date, end_date, dept_id, xsry_id,client_name,"");
	}
	
	
	/**
	 * 取客户预收款合计值
	 * @param client_name
	 * @param start_date
	 * @param end_date
	 * @return
	 */
	public double getClientYushoukHj(String client_name,String start_date,String end_date){
		return ysmxReprotDao.getClientYushoukHj(client_name, start_date, end_date);
	}
	
	public YsmxReportDAO getYsmxReprotDao() {
		return ysmxReprotDao;
	}

	public void setYsmxReprotDao(YsmxReportDAO ysmxReprotDao) {
		this.ysmxReprotDao = ysmxReprotDao;
	}


	public SysInitSetDAO getSysInitSetDao() {
		return sysInitSetDao;
	}


	public void setSysInitSetDao(SysInitSetDAO sysInitSetDao) {
		this.sysInitSetDao = sysInitSetDao;
	}


	public XsmxReportDAO getXsmxReportDao() {
		return xsmxReportDao;
	}


	public void setXsmxReportDao(XsmxReportDAO xsmxReportDao) {
		this.xsmxReportDao = xsmxReportDao;
	}

}
