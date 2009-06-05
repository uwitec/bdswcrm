package com.sw.cms.service;

import java.util.List;
import java.util.Map;

import com.sw.cms.dao.DlsDzdDAO;

public class DlsDzdService {
	
	private DlsDzdDAO dlsDzdDao;
	
	/**
	 * ���ݲ�ѯ����ȡ�����̶��˵�
	 * @param start_date
	 * @param end_date
	 * @param client_name
	 * @return
	 */
	public List getDlsDzdList(String start_date,String end_date,String client_name){
		return dlsDzdDao.getDlsDzdList(start_date, end_date, client_name);
	}
	
	
	/**
	 * ��ȡ������ϸ��Ϣ
	 * @param dj_id ���ݱ��
	 * @param cdid  �ӵ����
	 * @param xwtype ҵ������
	 * @return
	 */
	public List getDjMxList(String dj_id,String cdid,String xwtype){
		return dlsDzdDao.getDjMxList(dj_id, cdid, xwtype);
	}
	
	
	/**
	 * ȡ�ͻ��ڳ���Ϣ
	 * @param client_name
	 * @param qc_date
	 * @return
	 */
	public Map getDlsQcInfo(String client_name,String cdate){
		return dlsDzdDao.getDlsQcInfo(client_name, cdate);
	}


	public DlsDzdDAO getDlsDzdDao() {
		return dlsDzdDao;
	}


	public void setDlsDzdDao(DlsDzdDAO dlsDzdDao) {
		this.dlsDzdDao = dlsDzdDao;
	}

}
