package com.sw.cms.service;

import java.util.List;

import com.sw.cms.dao.HpxsHzDAO;

public class HpxsHzService {
	
	private HpxsHzDAO hpxsHzDao;
	
	
	
	/**
	 * 根据统计条件统计货品销售汇总表
	 * @param product_kind   商品类别
	 * @param product_name   商品名称
	 * @param product_xh     商品规格
	 * @param start_date     开始时间
	 * @param end_date       结束时间
	 * @param client_name    客户名称
	 * @param xsry_id        销售人员
	 * @return
	 */
	public List getHpxshzTjResult(String product_kind,String product_name,String product_xh,
			String start_date,String end_date,String client_name,String xsry_id,String client_type){
		return hpxsHzDao.getHpxshzTjResult(product_kind, product_name, product_xh, start_date, end_date, client_name, xsry_id,client_type);
	}
	
	
	/**
	 * 根据统计条件统计货品销售汇总表(供应商)
	 * @param product_kind   商品类别
	 * @param product_name   商品名称
	 * @param product_xh     商品规格
	 * @param start_date     开始时间
	 * @param end_date       结束时间
	 * @param client_name    客户名称
	 * @param xsry_id        销售人员
	 * @return
	 */
	public List getGysHpxshzTjResult(String product_kind,String start_date,String end_date){
		return hpxsHzDao.getGysHpxshzTjResult(product_kind, start_date, end_date);
	}
	
	
	/**
	 * 根据统计条件统计货品毛利销售汇总表
	 * @param product_kind   商品类别
	 * @param product_name   商品名称
	 * @param product_xh     商品规格
	 * @param start_date     开始时间
	 * @param end_date       结束时间
	 * @param client_name    客户名称
	 * @param xsry_id        销售人员
	 * @return
	 */
	public List getHpxsmlhzTjResult(String product_kind,String product_name,String product_xh,
			String start_date,String end_date,String client_name,String xsry_id){
		return hpxsHzDao.getHpxsmlhzTjResult(product_kind, product_name, product_xh, start_date, end_date, client_name, xsry_id);
	}
	
	
	/**
	 * 取销售单列表
	 * @param product_id
	 * @param start_date
	 * @param end_date
	 * @param client_name
	 * @param xsry_id
	 * @return
	 */
	public List getXsdList(String product_id,String start_date,String end_date,String client_name,String xsry_id,String client_type){
		return hpxsHzDao.getXsdList(product_id, start_date, end_date, client_name, xsry_id,client_type);
	}
	
	
	/**
	 * 取退货单列表
	 * @param product_id
	 * @param start_date
	 * @param end_date
	 * @param client_name
	 * @param xsry_id
	 * @return
	 */
	public List getThdList(String product_id,String start_date,String end_date,String client_name,String xsry_id,String client_type){
		return hpxsHzDao.getThdList(product_id, start_date, end_date, client_name, xsry_id,client_type);
	}
	
	
	/**
	 * 取零售单列表
	 * @param product_id
	 * @param start_date
	 * @param end_date
	 * @param client_name
	 * @param xsry_id
	 * @return
	 */
	public List getLsdList(String product_id,String start_date,String end_date,String client_name,String xsry_id){
		return hpxsHzDao.getLsdList(product_id, start_date, end_date, client_name, xsry_id);
	}


	public HpxsHzDAO getHpxsHzDao() {
		return hpxsHzDao;
	}

	public void setHpxsHzDao(HpxsHzDAO hpxsHzDao) {
		this.hpxsHzDao = hpxsHzDao;
	}

}
