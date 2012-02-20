package com.sw.cms.service;

import java.util.List;

import com.sw.cms.dao.StorexsHzDAO;

public class StorexsHzService {
	
	private StorexsHzDAO storexsHzDao;
	
	
	
	
	
	/**
	 * 根据统计条件统计仓库销售汇总表
	 * @param product_kind   商品类别
	 * @param product_name   商品名称
	 * @param product_xh     商品规格
	 * @param start_date     开始时间
	 * @param end_date       结束时间
	 * @param store_id    仓库编号
	 * @return
	 */
	public List getStorexshzTjResult(String product_kind,String product_name,String product_xh,
			String start_date,String end_date,String store_id){
		return storexsHzDao.getStorexshzTjResult(product_kind, product_name, product_xh, start_date, end_date, store_id);
	}
	
	
	/**
	 * 仓库销售汇总--明细表
	 * @param dept        部门编号
	 * @param start_date  起始时间
	 * @param end_date    结束时间
	 * @param store_id 仓库编号
	 * @return
	 * @throws Exception
	 */
	public List getStoreResultMx(String store_id,String start_date,String end_date,String product_kind,String product_name,String product_xh) throws Exception{
		return storexsHzDao.getStoreResultMx(store_id, start_date, end_date, product_kind, product_name, product_xh);
	}

	/**
	 * 仓库销售汇总--销售人员销售明细
	 * @param xsry
	 * @param start_date
	 * @param end_date
	 * @param store_id
	 * @return
	 * @throws Exception
	 */
	public List getStorexsProductMxResult(String xsry,String start_date,String end_date,String store_id,String product_kind,String product_name) throws Exception{
		return storexsHzDao.getStorexsProductMxResult(xsry, start_date, end_date, store_id, product_kind, product_name);
	}
	
	public StorexsHzDAO getStorexsHzDao() {
		return storexsHzDao;
	}

	public void setStorexsHzDao(StorexsHzDAO storexsHzDao) {
		this.storexsHzDao = storexsHzDao;
	}

}
