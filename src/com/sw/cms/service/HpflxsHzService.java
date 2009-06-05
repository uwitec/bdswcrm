package com.sw.cms.service;

import java.util.List;

import com.sw.cms.dao.HpflxsHzDAO;

/**
 * 货品销售分类汇总
 * @author liyt
 *
 */
public class HpflxsHzService {
	
	private HpflxsHzDAO hpflxsHzDao;
	
	/**
	 * 按货品类别等级统计销售类别销售
	 * @param start_date
	 * @param end_date
	 * @param dj
	 * @return
	 */
	public List getHzResults(String start_date,String end_date,String xsry,String client_name,int dj){
		return hpflxsHzDao.getHzResults(start_date, end_date,xsry,client_name ,dj);
	}
	
	/**
	 * 根据货品销售分类汇总明细信息
	 * @param product_kind   商品类别
	 * @param start_date     开始时间
	 * @param end_date       结束时间
	 * @param client_name    客户名称
	 * @param xsry_id        销售人员
	 * @return
	 */
	public List getMxResults(String product_kind,String start_date,String end_date,String client_name,String xsry){
		return hpflxsHzDao.getMxResults(product_kind, start_date, end_date, client_name, xsry);
	}

	public HpflxsHzDAO getHpflxsHzDao() {
		return hpflxsHzDao;
	}

	public void setHpflxsHzDao(HpflxsHzDAO hpflxsHzDao) {
		this.hpflxsHzDao = hpflxsHzDao;
	}
	

}
