package com.sw.cms.service;

import java.util.List;
import java.util.Map;

import com.sw.cms.dao.SysInitSetDAO;
import com.sw.cms.dao.YfHzMxDAO;

public class YfHzMxService {
	
	private YfHzMxDAO yfHzMxDao;
	private SysInitSetDAO sysInitSetDao;
	
	/**
	 * ȡӦ���ڳ�
	 * @param client_name �ͻ����
	 * @param cdate �ڳ�����
	 * @return �ڳ�ֵ
	 */
	public Map getYfQc(String client_name,String cdate){
		String xtqyrq = sysInitSetDao.getSysInitSet().getQyrq(); //ϵͳ��������
		if(xtqyrq == null){
			xtqyrq = "";
		}
		
		//����û�ѡ������С��ϵͳ��������
		if(cdate.compareTo(xtqyrq)<0){
			cdate = xtqyrq;
		}
		Map map = yfHzMxDao.getYfQc(client_name, cdate);
		
		return map;
	}
	
	
	/**
	 * ȡ���ڷ�����
	 * @param client_name �ͻ����
	 * @param start_date  ��ʼ����
	 * @param end_date    ��������
	 * @return ���ڷ�����
	 */
	public double getBqfs(String client_name,String start_date,String end_date){
		return yfHzMxDao.getBqfs(client_name, start_date, end_date);
	}
	
	
	/**
	 * ȡ�����Ѹ���(�������˻��з�ӳ��֧�����������)
	 * @param client_name �ͻ����
	 * @param start_date  ��ʼ����
	 * @param end_date    ��������
	 * @return �����Ѹ���
	 */
	public double getBqyf(String client_name,String start_date,String end_date){
		return yfHzMxDao.getBqyf(client_name, start_date, end_date);
	}
	
	/**
	 * ȡ��ϸ��Ϣ
	 * @param client_name
	 * @param start_date
	 * @param end_date
	 * @return
	 */
	public List getYfmxList(String client_name,String start_date,String end_date){
		return yfHzMxDao.getYfmxList(client_name, start_date, end_date);
	}


	public YfHzMxDAO getYfHzMxDao() {
		return yfHzMxDao;
	}


	public void setYfHzMxDao(YfHzMxDAO yfHzMxDao) {
		this.yfHzMxDao = yfHzMxDao;
	}


	public SysInitSetDAO getSysInitSetDao() {
		return sysInitSetDao;
	}


	public void setSysInitSetDao(SysInitSetDAO sysInitSetDao) {
		this.sysInitSetDao = sysInitSetDao;
	}

}
