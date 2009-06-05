package com.sw.cms.service;

import java.util.List;

import com.sw.cms.dao.HpflxsHzDAO;

/**
 * ��Ʒ���۷������
 * @author liyt
 *
 */
public class HpflxsHzService {
	
	private HpflxsHzDAO hpflxsHzDao;
	
	/**
	 * ����Ʒ���ȼ�ͳ�������������
	 * @param start_date
	 * @param end_date
	 * @param dj
	 * @return
	 */
	public List getHzResults(String start_date,String end_date,String xsry,String client_name,int dj){
		return hpflxsHzDao.getHzResults(start_date, end_date,xsry,client_name ,dj);
	}
	
	/**
	 * ���ݻ�Ʒ���۷��������ϸ��Ϣ
	 * @param product_kind   ��Ʒ���
	 * @param start_date     ��ʼʱ��
	 * @param end_date       ����ʱ��
	 * @param client_name    �ͻ�����
	 * @param xsry_id        ������Ա
	 * @return
	 */
	public List getMxResults(String product_kind,String start_date,String end_date,String client_name,String xsry){
		return hpflxsHzDao.getMxResults(product_kind, start_date, end_date, client_name, xsry);
	}

	public HpflxsHzDAO getHpflxsHzDao() {
		return hpflxsHzDao;
	}

	public void setHpflxsHzDao(HpflxsHzDAO hpflxsHzDao) {
		this.hpflxsHzDao = hpflxsHzDao;
	}
	

}
