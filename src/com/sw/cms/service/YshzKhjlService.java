package com.sw.cms.service;

import java.util.List;
import java.util.Map;

import com.sw.cms.dao.YshzKhjlDAO;

public class YshzKhjlService {
	
	private YshzKhjlDAO yshzKhjlDao;
	
	/**
	 * 取期初相关信息
	 * @param start_date   期初时间
	 * @param client_name  客户编号
	 * @param khjl         客户经理
	 * @return
	 */
	public Map getQc(String start_date,String client_name,String khjl){
		return yshzKhjlDao.getQc(start_date, client_name, khjl);
	}
	
	
	/**
	 * 取客户经理往来所有情况
	 * @param start_date   开始日期
	 * @param end_date     结束日期
	 * @param client_id    客户编号
	 * @param khjl         客户经理
	 * @return map key:client_id+je_type  value:发生金额
	 */
	public Map getKhjlWlInfo(String start_date,String end_date,String client_name,String khjl){
		return yshzKhjlDao.getKhjlWlInfo(start_date, end_date, client_name, khjl);
	}
	
	
	/**
	 * 取所有客户经理列表
	 * @param client_name  客户编号
	 * @param khjl         客户经理
	 * @return
	 */
	public List getKhjlList(String client_name,String khjl){
		return yshzKhjlDao.getKhjlList(client_name, khjl);
	}
	
	
	/**
	 * 客户经理应收对账单
	 * @param client_name
	 * @param start_date
	 * @param end_date
	 * @return
	 */
	public List getKhjlYsDzd(String start_date,String end_date,String client_name,String khjl){
		return yshzKhjlDao.getKhjlYsDzd(start_date, end_date, client_name, khjl);
	}

	public YshzKhjlDAO getYshzKhjlDao() {
		return yshzKhjlDao;
	}

	public void setYshzKhjlDao(YshzKhjlDAO yshzKhjlDao) {
		this.yshzKhjlDao = yshzKhjlDao;
	}
}
