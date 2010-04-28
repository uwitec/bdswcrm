package com.sw.cms.service;

import java.util.List;

import com.sw.cms.dao.CgddhzDAO;

public class CgddhzService {
	
	private CgddhzDAO cgddhzDao;
	
	/**
	 * 取货品采购汇总
	 * @param product_id
	 * @param start_date
	 * @param end_date
	 * @param client_name
	 * @param xsry_id
	 * @return
	 */
	public List getHpcgList(String product_kind,String start_date,String end_date,String product_name,String product_xh){
		return cgddhzDao.getHpcgList(product_kind, start_date, end_date,product_name, product_xh);
	}
	
	/**
	 * 取采购汇总单据明细（进货单）
	 * @param proudct_id
	 * @param start_date
	 * @param end_date
	 * @param client_name
	 * @return
	 */
	public List getDjmxList(String proudct_id,String start_date,String end_date){
		return cgddhzDao.getDjmxList(proudct_id, start_date, end_date);
	}
	
	
	/**
	 * 取客户采购汇总列表信息（进货单）
	 * @param start_date
	 * @param end_date
	 * @param dj_id
	 * @param client_name
	 * @return
	 */
	public List getClientCgList(String start_date,String end_date,String dj_id,String client_name){
		return cgddhzDao.getClientCgList(start_date, end_date, dj_id, client_name);
	}
	
	/**
	 * 客记采购汇总单据列表(采购单)
	 * @param start_date
	 * @param end_date
	 * @param dj_id
	 * @param client_name
	 * @return
	 */
	public List getClientMxList(String start_date,String end_date,String dj_id,String client_name){
		return cgddhzDao.getClientMxList(start_date, end_date, dj_id, client_name);
	}
	
	/**
	 * 取单据商品明细
	 * @param dj_id
	 * @param xwtype
	 * @return
	 */
	public List getDjmxList(String dj_id){
		return cgddhzDao.getDjmxList(dj_id);
	}

	public CgddhzDAO getCgddhzDao() {
		return cgddhzDao;
	}

	public void setCgddhzDao(CgddhzDAO cgddhzDao) {
		this.cgddhzDao = cgddhzDao;
	}

}
