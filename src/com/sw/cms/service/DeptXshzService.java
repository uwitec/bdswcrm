package com.sw.cms.service;

import java.util.List;

import com.sw.cms.dao.DeptXshzDAO;

/**
 * �������ۻ���
 * @author liyt
 *
 */
public class DeptXshzService {
	
	private DeptXshzDAO deptXshzDao;
	
	
	/**
	 * �������ۻ����б�
	 * @param start_date    ��ʼʱ��
	 * @param end_date      ����ʱ��
	 * @param client_name   �ͻ���ţ�ǰ̨ѡ����ҵ�ͻ��������̵ȣ�
	 * @param dj  ͳ�Ʋ��ŵȼ�
	 * @return
	 * @throws Exception
	 */
	public List getResults(String start_date,String end_date,String client_name,int dj) throws Exception{
		return deptXshzDao.getResults(start_date, end_date, client_name, dj);
	}
	
	
	/**
	 * �������ۻ���--��ϸ��
	 * @param dept        ���ű��
	 * @param start_date  ��ʼʱ��
	 * @param end_date    ����ʱ��
	 * @param client_name �ͻ����
	 * @return
	 * @throws Exception
	 */
	public List getMxResults(String dept,String start_date,String end_date,String client_name) throws Exception{
		return deptXshzDao.getMxResults(dept, start_date, end_date, client_name);
	}
	
	
	/**
	 * �������ۻ���--������Ա������ϸ
	 * @param xsry
	 * @param start_date
	 * @param end_date
	 * @param client_name
	 * @return
	 * @throws Exception
	 */
	public List getProductMxResults(String xsry,String start_date,String end_date,String client_name) throws Exception{
		return deptXshzDao.getProductMxResults(xsry, start_date, end_date, client_name);
	}
	

	public DeptXshzDAO getDeptXshzDao() {
		return deptXshzDao;
	}

	public void setDeptXshzDao(DeptXshzDAO deptXshzDao) {
		this.deptXshzDao = deptXshzDao;
	}

}
