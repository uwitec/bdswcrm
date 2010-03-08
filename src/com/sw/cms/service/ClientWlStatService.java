package com.sw.cms.service;

import java.util.Map;

import com.sw.cms.dao.ClientWlStatDAO;

public class ClientWlStatService {
	
	private ClientWlStatDAO clientWlStatDao;
	
	/**
	 * 取用户期初，包括应收期初、应付期初
	 * @param cdate
	 * @return Map key:client_id+应收（应付） value:期初值
	 */
	public Map getClientQc(String cdate){
		return clientWlStatDao.getClientQc(cdate);
	}
	
	
	/**
	 * 取用户往来所有情况
	 * @param start_date
	 * @param end_date
	 * @param client_id
	 * @return map key:client_id+je_type  value:发生金额
	 */
	public Map getClientWlInfo(String start_date,String end_date,String client_id){
		return clientWlStatDao.getClientWlInfo(start_date, end_date, client_id);
	}
	
	/**
	 * 最长超期天数
	 * @param client_id
	 * @return
	 */
	public Map getClientMaxCqts(String client_id){
		return clientWlStatDao.getClientMaxCqts(client_id);
	}
	
	
	/**
	 * 客户未到期应收款
	 * @param client_id
	 * @return
	 */
	public Map getClientWdqysk(String client_id){
		return clientWlStatDao.getClientWdqysk(client_id);
	}
	
	
	/**
	 * 客户超期应收款
	 * @param client_id
	 * @return
	 */
	public Map getClientCqysk(String client_id){
		return clientWlStatDao.getClientCqysk(client_id);
	}
	
	
	/**
	 * 客户预收款
	 * @param client_id
	 * @return
	 */
	public Map getClientYushouk(String client_id){
		return clientWlStatDao.getClientYushouk(client_id);
	}
	
	
	/**
	 * 客户期初结算余额
	 * @param client_id
	 * @return
	 */
	public Map getClientQcjsye(String client_id){
		return clientWlStatDao.getClientQcjsye(client_id);
	}
	
	
	/**
	 * 往来调账（应收）结算余额
	 * @param client_id
	 * @return
	 */
	public Map getClientTzjsye(String client_id){
		return clientWlStatDao.getClientTzjsye(client_id);
	}
	
	/**
	 * 应付最长超期天数
	 * @param client_id
	 * @return
	 */
	public Map getClientYfMaxCqts(String client_id){
		return clientWlStatDao.getClientYfMaxCqts(client_id);	
	}
	
	
	/**
	 * 供应商未到期应付款
	 * @param client_id
	 * @return
	 */
	public Map getClientWdqyfk(String client_id){
		return clientWlStatDao.getClientWdqyfk(client_id);
	}
	
	
	/**
	 * 供应商超期应付款
	 * @param client_id
	 * @return
	 */
	public Map getClientCqyfk(String client_id){
		return clientWlStatDao.getClientCqyfk(client_id);
	}
	
	
	/**
	 * 供应商预付款
	 * @param client_id
	 * @return
	 */
	public Map getClientYufuk(String client_id){
		return clientWlStatDao.getClientYufuk(client_id);
	}
	
	
	/**
	 * 供应商期初应付结算余额
	 * @param client_id
	 * @return
	 */
	public Map getClientQcyfjsye(String client_id){
		return clientWlStatDao.getClientQcyfjsye(client_id);
	}
	
	
	/**
	 * 往来调账（应付）结算余额
	 * @param client_id
	 * @return
	 */
	public Map getClientTzyfjsye(String client_id){
		return clientWlStatDao.getClientTzyfjsye(client_id);
	}
	

	public ClientWlStatDAO getClientWlStatDao() {
		return clientWlStatDao;
	}

	public void setClientWlStatDao(ClientWlStatDAO clientWlStatDao) {
		this.clientWlStatDao = clientWlStatDao;
	}

}
