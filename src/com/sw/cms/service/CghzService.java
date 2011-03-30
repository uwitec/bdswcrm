package com.sw.cms.service;

import java.util.List;

import com.sw.cms.dao.CghzDAO;

public class CghzService {
	
	private CghzDAO cghzDao;
	
	/**
	 * 取货品采购汇总
	 * @param product_kind
	 * @param start_date
	 * @param end_date
	 * @param client_name
	 * @param product_name
	 * @param product_xh
	 * @param cgry_id
	 * @return
	 */
	public List getHpcgList(String product_kind,String start_date,String end_date,String client_name,String product_name,String product_xh,String cgry_id){
		return cghzDao.getHpcgList(product_kind, start_date, end_date, client_name, product_name, product_xh,cgry_id);
	}
	
	/**
	 * 取采购汇总单据明细（进货单、采购退货单）
	 * @param proudct_id
	 * @param start_date
	 * @param end_date
	 * @param client_name
	 * @param cgry_id
	 * @return
	 */
	public List getDjmxList(String proudct_id,String start_date,String end_date,String client_name,String cgry_id){
		return cghzDao.getDjmxList(proudct_id, start_date, end_date, client_name,cgry_id);
	}
	
	
	/**
	 * 取客户采购汇总列表信息（进货单、采购退货单）
	 * @param start_date
	 * @param end_date
	 * @param dj_id
	 * @param client_name
	 * @return
	 */
	public List getClientCgList(String start_date,String end_date,String dj_id,String client_name){
		return cghzDao.getClientCgList(start_date, end_date, dj_id, client_name);
	}
	
	/**
	 * 客记采购汇总单据列表(采购单、采购退货单)
	 * @param start_date
	 * @param end_date
	 * @param dj_id
	 * @param client_name
	 * @return
	 */
	public List getClientMxList(String start_date,String end_date,String dj_id,String client_name){
		return cghzDao.getClientMxList(start_date, end_date, dj_id, client_name);
	}
	
	/**
	 * 取单据商品明细
	 * @param dj_id
	 * @param xwtype
	 * @return
	 */
	public List getDjmxList(String dj_id,String xwtype){
		return cghzDao.getDjmxList(dj_id, xwtype);
	}

	public CghzDAO getCghzDao() {
		return cghzDao;
	}

	public void setCghzDao(CghzDAO cghzDao) {
		this.cghzDao = cghzDao;
	}

}
