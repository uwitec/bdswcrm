package com.sw.cms.service;

import java.util.List;
import java.util.Map;

import com.sw.cms.dao.KcMxReportDAO;
import com.sw.cms.dao.SysInitSetDAO;

public class KcMxReportService {

	private KcMxReportDAO kcMxReportDao;
	private SysInitSetDAO sysInitSetDao;
	
	/**
	 * 按查询条件取库存产品列表
	 * @param product_name 产品类别
	 * @param product_name 产品名称
	 * @param store_id  仓库编号
	 * @return
	 */
	public List getProductKcList(String product_kind,String product_name,String store_id){
		return kcMxReportDao.getProductKcList(product_kind,product_name,store_id);
	}
	
	
	/**
	 * <p>取库存期初相关信息</p>
	 * <p>需要对用户选择的期初日期做出判断</p>
	 * <p>如果选择的期初日期小于系统正始启用日期则期初日期取系统启用日期</p>
	 * @param product_id
	 * @param cdate
	 * @param store_id
	 * @return map
	 */
	public Map getKcqcMxMap(String product_id,String cdate,String store_id){
		String xtqyrq = sysInitSetDao.getSysInitSet().getQyrq(); //系统启用日期
		if(xtqyrq == null){
			xtqyrq = "";
		}
		
		//如果用户选择日期小于系统启用日期
		if(cdate.compareTo(xtqyrq)<0){
			cdate = xtqyrq;
		}
		Map map = kcMxReportDao.getKcqcMxMap(product_id, cdate, store_id);
		if(map != null){
			map.put("qc_date", cdate);
		}
		return map;
	}
	
	
	/**
	 * 取库存变化明细
	 * 包括出库单、入库单、库房调拨
	 * @param product_id
	 * @param start_date
	 * @param end_date
	 * @param store_id
	 * @return
	 */
	public List getKcbhMxList(String product_id,String start_date,String end_date,String store_id){
		return kcMxReportDao.getKcbhMxList(product_id, start_date, end_date, store_id);
	}
	
	
	/**
	 * 库存成本变化情况
	 * @param product_id
	 * @param start_date
	 * @param end_date
	 * @return
	 */
	public List getKcCbBhMxList(String product_id,String start_date,String end_date){
		return kcMxReportDao.getKcCbBhMxList(product_id, start_date, end_date);
	}
	
	
	/**
	 * 取入库数量
	 * @param product_id
	 * @param start_date
	 * @param end_date
	 * @param store_id
	 * @return
	 */
	public int getRkNums(String product_id,String start_date,String end_date,String store_id){
		return kcMxReportDao.getRkNums(product_id, start_date, end_date, store_id);
	}
	
	
	/**
	 * 取出库数量
	 * @param product_id
	 * @param start_date
	 * @param end_date
	 * @param store_id
	 * @return
	 */
	public int getCkNums(String product_id,String start_date,String end_date,String store_id){
		return  kcMxReportDao.getCkNums(product_id, start_date, end_date, store_id);
	}
	
	
	
	/**
	 * 库存数量汇总
	 * @param product_kind   商品类别
	 * @param product_name   商口名称
	 * @param store_id       库房编号
	 * @param state          是否显示停售商品
	 * @param flag           是否显示0库存商品
	 * @return
	 */
	public List getKcNumsResults(String product_kind,String product_name,String store_id,String state,String flag){
		return  kcMxReportDao.getKcNumsResults(product_kind, product_name, store_id,state, flag);
	}
	
	
	/**
	 * 库存数量汇总(供应商)
	 * @param product_kind   商品类别
	 * @param product_name   商口名称
	 * @param store_id       库房编号
	 * @param state          是否显示停售商品
	 * @param flag           是否显示0库存商品
	 * @return
	 */
	public List getGysKcNumsResults(String product_kind){
		return  kcMxReportDao.getGysKcNumsResults(product_kind);
	}
	
	
	/**
	 * 历史库存金额查询
	 * @param product_kind  商品类别
	 * @param product_name  商品名称
	 * @param product_xh    商品规格
	 * @param store_id      库房编号
	 * @param cdate         库存日期
	 * @return
	 */
	public List getHisKcjeResults(String product_kind,String product_name,String product_xh,String store_id,String cdate){
		return kcMxReportDao.getHisKcjeResults(product_kind, product_name, product_xh, store_id, cdate);
	}

	
	public KcMxReportDAO getKcMxReportDao() {
		return kcMxReportDao;
	}

	public void setKcMxReportDao(KcMxReportDAO kcMxReportDao) {
		this.kcMxReportDao = kcMxReportDao;
	}


	public SysInitSetDAO getSysInitSetDao() {
		return sysInitSetDao;
	}


	public void setSysInitSetDao(SysInitSetDAO sysInitSetDao) {
		this.sysInitSetDao = sysInitSetDao;
	}
	
}
