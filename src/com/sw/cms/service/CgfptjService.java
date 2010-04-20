package com.sw.cms.service;

import java.util.List;
import java.util.Map;

import com.sw.cms.dao.CgfptjDAO;
import com.sw.cms.dao.SysInitSetDAO;

public class CgfptjService {
	
	private CgfptjDAO cgfptjDao;
	private SysInitSetDAO sysInitSetDao;
	
	/**
	 * ���ݲ�ѯ�����ɹ���Ʊ�б�
	 * ����δ��⡢�����
	 * �б��ֶΰ�����(��λ���ơ������������)
	 * @param start_date
	 * @param end_date
	 * @param client_name
	 * @return
	 */
	public List getCgfpHzList(String start_date,String end_date,String client_name){
		return cgfptjDao.getCgfpHzList(start_date, end_date, client_name);
	}
	
	
	/**
	 * ���زɹ���Ʊ��ϸ
	 * @param account_id
	 * @param start_date
	 * @param end_date
	 * @return
	 */
	public List getCgfpSeqList(String client_name,String start_date,String end_date){
		return cgfptjDao.getCgfpSeqList(client_name, start_date, end_date);
	}
	
	
	public CgfptjDAO getCgfptjDao() {
		return cgfptjDao;
	}

	public void setCgfptjDao(CgfptjDAO cgfptjDao) {
		this.cgfptjDao = cgfptjDao;
	}


	public SysInitSetDAO getSysInitSetDao() {
		return sysInitSetDao;
	}


	public void setSysInitSetDao(SysInitSetDAO sysInitSetDao) {
		this.sysInitSetDao = sysInitSetDao;
	}
	
	

}
