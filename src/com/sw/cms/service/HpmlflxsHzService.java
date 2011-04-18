package com.sw.cms.service;

import java.util.List;

import com.sw.cms.dao.HpmlflxsHzDAO;

/**
 * ��Ʒ����ë���������
 * @author zuohj
 *
 */
public class HpmlflxsHzService {
	
	private HpmlflxsHzDAO hpmlflxsHzDao;
	
	/**
	 * ����Ʒ���ȼ�ͳ�������������
	 * @param start_date
	 * @param end_date
	 * @param dj
	 * @return
	 */
	public List getHzResults(String start_date,String end_date,String xsry,String client_name,String dept,int dj){
		return hpmlflxsHzDao.getHzResults(start_date, end_date,xsry,client_name,dept,dj);
	}
	
	/**
	 * ���ݻ�Ʒ����ë�����������ϸ��Ϣ
	 * @param product_kind   ��Ʒ���
	 * @param start_date     ��ʼʱ��
	 * @param end_date       ����ʱ��
	 * @param client_name    �ͻ�����
	 * @param xsry_id        ������Ա
	 * @return
	 */
	public List getMxResults(String product_kind,String start_date,String end_date,String client_name,String dept,String xsry){
		return hpmlflxsHzDao.getMxResults(product_kind, start_date, end_date, client_name,dept, xsry);
	}

	public HpmlflxsHzDAO getHpmlflxsHzDao() {
		return hpmlflxsHzDao;
	}

	public void setHpmlflxsHzDao(HpmlflxsHzDAO hpmlflxsHzDao) {
		this.hpmlflxsHzDao = hpmlflxsHzDao;
	}
	

}
