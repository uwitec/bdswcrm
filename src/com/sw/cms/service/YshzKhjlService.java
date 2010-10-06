package com.sw.cms.service;

import java.util.List;
import java.util.Map;

import com.sw.cms.dao.YshzKhjlDAO;

public class YshzKhjlService {
	
	private YshzKhjlDAO yshzKhjlDao;
	
	/**
	 * ȡ�ڳ������Ϣ
	 * @param start_date   �ڳ�ʱ��
	 * @param client_name  �ͻ����
	 * @param khjl         �ͻ�����
	 * @return
	 */
	public Map getQc(String start_date,String client_name,String khjl){
		return yshzKhjlDao.getQc(start_date, client_name, khjl);
	}
	
	
	/**
	 * ȡ�ͻ����������������
	 * @param start_date   ��ʼ����
	 * @param end_date     ��������
	 * @param client_id    �ͻ����
	 * @param khjl         �ͻ�����
	 * @return map key:client_id+je_type  value:�������
	 */
	public Map getKhjlWlInfo(String start_date,String end_date,String client_name,String khjl){
		return yshzKhjlDao.getKhjlWlInfo(start_date, end_date, client_name, khjl);
	}
	
	
	/**
	 * ȡ���пͻ������б�
	 * @param client_name  �ͻ����
	 * @param khjl         �ͻ�����
	 * @return
	 */
	public List getKhjlList(String client_name,String khjl){
		return yshzKhjlDao.getKhjlList(client_name, khjl);
	}
	
	
	/**
	 * �ͻ�����Ӧ�ն��˵�
	 * @param client_name
	 * @param start_date
	 * @param end_date
	 * @return
	 */
	public List getKhjlYsDzd(String start_date,String end_date,String client_name,String khjl){
		return yshzKhjlDao.getKhjlYsDzd(start_date, end_date, client_name, khjl);
	}

	public YshzKhjlDAO getYshzKhjlDao() {
		return yshzKhjlDao;
	}

	public void setYshzKhjlDao(YshzKhjlDAO yshzKhjlDao) {
		this.yshzKhjlDao = yshzKhjlDao;
	}
}
