package com.sw.cms.service;

import java.util.List;

import com.sw.cms.dao.CghzDAO;

public class CghzService {
	
	private CghzDAO cghzDao;
	
	/**
	 * ȡ��Ʒ�ɹ�����
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
	 * ȡ�ɹ����ܵ�����ϸ�����������ɹ��˻�����
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
	 * ȡ�ͻ��ɹ������б���Ϣ�����������ɹ��˻�����
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
	 * �ͼǲɹ����ܵ����б�(�ɹ������ɹ��˻���)
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
	 * ȡ������Ʒ��ϸ
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
