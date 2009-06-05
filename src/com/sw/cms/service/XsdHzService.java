package com.sw.cms.service;

import java.util.List;

import com.sw.cms.dao.XsdHzDAO;

public class XsdHzService {
	
	private XsdHzDAO xsdHzDao;
	
	/**
	 * ȡ���۶����б�
	 * @param start_date ��ʼʱ��
	 * @param end_date����ʱ��
	 * @param client_name�ͻ�����
	 * @param xsry_id������Ա
	 * @param dj_id���ݱ��
	 * @return
	 */
	public List getXsdList(String start_date,String end_date,String client_name,String xsry_id,String dj_id){
		return xsdHzDao.getXsdList(start_date, end_date, client_name, xsry_id, dj_id);
	}

	public XsdHzDAO getXsdHzDao() {
		return xsdHzDao;
	}

	public void setXsdHzDao(XsdHzDAO xsdHzDao) {
		this.xsdHzDao = xsdHzDao;
	}
	

}
