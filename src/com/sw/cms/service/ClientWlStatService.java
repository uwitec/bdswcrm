package com.sw.cms.service;

import java.util.Map;

import com.sw.cms.dao.ClientWlStatDAO;

public class ClientWlStatService {
	
	private ClientWlStatDAO clientWlStatDao;
	
	/**
	 * ȡ�û��ڳ�������Ӧ���ڳ���Ӧ���ڳ�
	 * @param cdate
	 * @return Map key:client_id+Ӧ�գ�Ӧ���� value:�ڳ�ֵ
	 */
	public Map getClientQc(String cdate){
		return clientWlStatDao.getClientQc(cdate);
	}
	
	
	/**
	 * ȡ�û������������
	 * @param start_date
	 * @param end_date
	 * @param client_id
	 * @return map key:client_id+je_type  value:�������
	 */
	public Map getClientWlInfo(String start_date,String end_date,String client_id){
		return clientWlStatDao.getClientWlInfo(start_date, end_date, client_id);
	}

	public ClientWlStatDAO getClientWlStatDao() {
		return clientWlStatDao;
	}

	public void setClientWlStatDao(ClientWlStatDAO clientWlStatDao) {
		this.clientWlStatDao = clientWlStatDao;
	}

}
