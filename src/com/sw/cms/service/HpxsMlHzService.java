package com.sw.cms.service;

import java.util.List;

import com.sw.cms.dao.HpxsMlHzDAO;

public class HpxsMlHzService {
	
	private HpxsMlHzDAO hpxsMlHzDao;
	
	/**
	 * 产品列表信息
	 * @param productKind 产品分类编号
	 * @return   产品列表
	 */
	public List getProductList(String productKind){
		return hpxsMlHzDao.getProductList(productKind);
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
		return hpxsMlHzDao.getHpxsmlhzTjResult(product_kind, product_name, product_xh, start_date, end_date, client_name, xsry_id);
	}
	
	
	/**
	 * 货品销售毛利汇总列表
	 * 包括销售单、零售单、退货单
	 * @param product_id
	 * @param start_date
	 * @param end_date
	 * @param client_name
	 * @param xsry_id
	 * @return
	 */
	public List getHpxsList(String product_id,String start_date,String end_date,String client_name,String xsry_id){
		return hpxsMlHzDao.getHpxsList(product_id, start_date, end_date, client_name, xsry_id);
	}

	
	public HpxsMlHzDAO getHpxsMlHzDao() {
		return hpxsMlHzDao;
	}

	public void setHpxsMlHzDao(HpxsMlHzDAO hpxsMlHzDao) {
		this.hpxsMlHzDao = hpxsMlHzDao;
	}

}
