package com.sw.cms.service;

import java.util.List;
import java.util.Map;

import com.sw.cms.dao.ClientWlDzdDAO;
import com.sw.cms.dao.SysInitSetDAO;

public class ClientWlDzdService {
	
	private ClientWlDzdDAO clientWlDzdDao;
	private SysInitSetDAO sysInitSetDao;
	
	/**
	 * 根据查询条件客户往来对账单列表
	 * 包括销售、退货、采购、采购退货、销售收款、采购付款
	 * 列表字段包括：(日期、业务类型、单据号、金额(根据业务类型区分金额所放置位置))
	 * @param start_date
	 * @param end_date
	 * @param client_name
	 * @return
	 */
	public List getClientWlDzdList(String start_date,String end_date,String client_name){
		return clientWlDzdDao.getClientWlDzdList(start_date, end_date, client_name);
	}
	
	
	/**
	 * 获取单据明细信息
	 * @param dj_id 单据编号
	 * @param cdid  从单编号
	 * @param xwtype 业务类型
	 * @return
	 */
	public List getDjMxList(String dj_id,String xwtype){
		return clientWlDzdDao.getDjMxList(dj_id, xwtype);
	}
	
	/**
	 * 取客户期初信息
	 * @param client_name
	 * @param qc_date
	 * @return
	 */
	public Map getClientQcInfo(String client_name,String cdate){
		
		String xtqyrq = sysInitSetDao.getSysInitSet().getQyrq(); //系统启用日期
		if(xtqyrq == null){
			xtqyrq = "";
		}
		
		//如果用户选择日期小于系统启用日期
		if(cdate.compareTo(xtqyrq)<0){
			cdate = xtqyrq;
		}
		
		return clientWlDzdDao.getClientQcInfo(client_name, cdate);
	}

	public ClientWlDzdDAO getClientWlDzdDao() {
		return clientWlDzdDao;
	}

	public void setClientWlDzdDao(ClientWlDzdDAO clientWlDzdDao) {
		this.clientWlDzdDao = clientWlDzdDao;
	}


	public SysInitSetDAO getSysInitSetDao() {
		return sysInitSetDao;
	}


	public void setSysInitSetDao(SysInitSetDAO sysInitSetDao) {
		this.sysInitSetDao = sysInitSetDao;
	}
	
	

}
