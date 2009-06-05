package com.sw.cms.service;

import java.util.List;
import java.util.Map;

import com.sw.cms.dao.ClientWlDzdDAO;
import com.sw.cms.dao.SysInitSetDAO;

public class ClientWlDzdService {
	
	private ClientWlDzdDAO clientWlDzdDao;
	private SysInitSetDAO sysInitSetDao;
	
	/**
	 * ���ݲ�ѯ�����ͻ��������˵��б�
	 * �������ۡ��˻����ɹ����ɹ��˻��������տ�ɹ�����
	 * �б��ֶΰ�����(���ڡ�ҵ�����͡����ݺš����(����ҵ���������ֽ��������λ��))
	 * @param start_date
	 * @param end_date
	 * @param client_name
	 * @return
	 */
	public List getClientWlDzdList(String start_date,String end_date,String client_name){
		return clientWlDzdDao.getClientWlDzdList(start_date, end_date, client_name);
	}
	
	
	/**
	 * ��ȡ������ϸ��Ϣ
	 * @param dj_id ���ݱ��
	 * @param cdid  �ӵ����
	 * @param xwtype ҵ������
	 * @return
	 */
	public List getDjMxList(String dj_id,String xwtype){
		return clientWlDzdDao.getDjMxList(dj_id, xwtype);
	}
	
	/**
	 * ȡ�ͻ��ڳ���Ϣ
	 * @param client_name
	 * @param qc_date
	 * @return
	 */
	public Map getClientQcInfo(String client_name,String cdate){
		
		String xtqyrq = sysInitSetDao.getSysInitSet().getQyrq(); //ϵͳ��������
		if(xtqyrq == null){
			xtqyrq = "";
		}
		
		//����û�ѡ������С��ϵͳ��������
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
