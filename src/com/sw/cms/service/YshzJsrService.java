package com.sw.cms.service;

import java.util.List;
import java.util.Map;

import com.sw.cms.dao.YshzJsrDAO;

public class YshzJsrService {

	private YshzJsrDAO yshzJsrDao;

	/**
	 * 取业务务应收汇总期初信息<BR>
	 * 起始日期之前所有未收单据的未收部分的合计值做为期初信息
	 * 
	 * @param start_date
	 *            起始日期
	 * @param client_name
	 *            客户名称
	 * @param jsr
	 *            经手人
	 * @return
	 */
	public Map getYshzJsrQc(String start_date, String client_name, String jsr) {
		return yshzJsrDao.getYshzJsrQc(start_date, client_name, jsr);
	}

	/**
	 * 业务应收流水
	 * 
	 * @param start_date
	 *            开始时间
	 * @param end_date
	 *            结束时间
	 * @param client_name
	 *            客户名称
	 * @param jsr
	 *            经手人
	 * @return
	 */
	public Map getYshzFlow(String start_date, String end_date,
			String client_name, String jsr) {
		return yshzJsrDao.getYshzFlow(start_date, end_date, client_name, jsr);
	}
	
	
	/**
	 * 业务应收流水
	 * @param start_date  开始时间
	 * @param end_date    结束时间
	 * @param client_name 客户名称
	 * @param jsr         经手人
	 * @return
	 */
	public List getXsdList(String start_date,String end_date,String client_name,String jsr){
		return yshzJsrDao.getXsdList(start_date, end_date, client_name, jsr);
	}
	
	
	/**
	 * 业务员应收汇总表--应收列
	 * @return
	 */
	public Map getYwyYsjeMap(){
		return yshzJsrDao.getYwyYsjeMap();
	}
	
	
	/**
	 * 业务员应收汇总表--超期应收款
	 * @return
	 */
	public Map getYwyCqjeMap(){
		return yshzJsrDao.getYwyCqjeMap();
	}
	
	
	/**
	 * 业务员应收汇总--最长账期、平均账期
	 * @param client_id
	 * @return
	 */
	public Map getYwyCqts(){
		return yshzJsrDao.getYwyCqts();
	}

	public YshzJsrDAO getYshzJsrDao() {
		return yshzJsrDao;
	}

	public void setYshzJsrDao(YshzJsrDAO yshzJsrDao) {
		this.yshzJsrDao = yshzJsrDao;
	}

}
