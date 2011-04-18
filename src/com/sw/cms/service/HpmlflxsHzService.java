package com.sw.cms.service;

import java.util.List;

import com.sw.cms.dao.HpmlflxsHzDAO;

/**
 * 货品销售毛利分类汇总
 * @author zuohj
 *
 */
public class HpmlflxsHzService {
	
	private HpmlflxsHzDAO hpmlflxsHzDao;
	
	/**
	 * 按货品类别等级统计销售类别销售
	 * @param start_date
	 * @param end_date
	 * @param dj
	 * @return
	 */
	public List getHzResults(String start_date,String end_date,String xsry,String client_name,String dept,int dj){
		return hpmlflxsHzDao.getHzResults(start_date, end_date,xsry,client_name,dept,dj);
	}
	
	/**
	 * 根据货品销售毛利分类汇总明细信息
	 * @param product_kind   商品类别
	 * @param start_date     开始时间
	 * @param end_date       结束时间
	 * @param client_name    客户名称
	 * @param xsry_id        销售人员
	 * @return
	 */
	public List getMxResults(String product_kind,String start_date,String end_date,String client_name,String dept,String xsry){
		return hpmlflxsHzDao.getMxResults(product_kind, start_date, end_date, client_name,dept, xsry);
	}

	public HpmlflxsHzDAO getHpmlflxsHzDao() {
		return hpmlflxsHzDao;
	}

	public void setHpmlflxsHzDao(HpmlflxsHzDAO hpmlflxsHzDao) {
		this.hpmlflxsHzDao = hpmlflxsHzDao;
	}
	

}
