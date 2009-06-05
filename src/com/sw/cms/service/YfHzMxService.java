package com.sw.cms.service;

import java.util.List;
import java.util.Map;

import com.sw.cms.dao.SysInitSetDAO;
import com.sw.cms.dao.YfHzMxDAO;

public class YfHzMxService {
	
	private YfHzMxDAO yfHzMxDao;
	private SysInitSetDAO sysInitSetDao;
	
	/**
	 * 取应付期初
	 * @param client_name 客户编号
	 * @param cdate 期初日期
	 * @return 期初值
	 */
	public Map getYfQc(String client_name,String cdate){
		String xtqyrq = sysInitSetDao.getSysInitSet().getQyrq(); //系统启用日期
		if(xtqyrq == null){
			xtqyrq = "";
		}
		
		//如果用户选择日期小于系统启用日期
		if(cdate.compareTo(xtqyrq)<0){
			cdate = xtqyrq;
		}
		Map map = yfHzMxDao.getYfQc(client_name, cdate);
		
		return map;
	}
	
	
	/**
	 * 取本期发生数
	 * @param client_name 客户编号
	 * @param start_date  开始日期
	 * @param end_date    结束日期
	 * @return 本期发生数
	 */
	public double getBqfs(String client_name,String start_date,String end_date){
		return yfHzMxDao.getBqfs(client_name, start_date, end_date);
	}
	
	
	/**
	 * 取本期已付数(所有在账户中反映出支出情况的数据)
	 * @param client_name 客户编号
	 * @param start_date  开始日期
	 * @param end_date    结束日期
	 * @return 本期已付数
	 */
	public double getBqyf(String client_name,String start_date,String end_date){
		return yfHzMxDao.getBqyf(client_name, start_date, end_date);
	}
	
	/**
	 * 取明细信息
	 * @param client_name
	 * @param start_date
	 * @param end_date
	 * @return
	 */
	public List getYfmxList(String client_name,String start_date,String end_date){
		return yfHzMxDao.getYfmxList(client_name, start_date, end_date);
	}


	public YfHzMxDAO getYfHzMxDao() {
		return yfHzMxDao;
	}


	public void setYfHzMxDao(YfHzMxDAO yfHzMxDao) {
		this.yfHzMxDao = yfHzMxDao;
	}


	public SysInitSetDAO getSysInitSetDao() {
		return sysInitSetDao;
	}


	public void setSysInitSetDao(SysInitSetDAO sysInitSetDao) {
		this.sysInitSetDao = sysInitSetDao;
	}

}
