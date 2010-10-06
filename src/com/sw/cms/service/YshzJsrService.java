package com.sw.cms.service;

import java.util.List;
import java.util.Map;

import com.sw.cms.dao.YshzJsrDAO;

public class YshzJsrService {

	private YshzJsrDAO yshzJsrDao;

	/**
	 * ȡҵ����Ӧ�ջ����ڳ���Ϣ<BR>
	 * ��ʼ����֮ǰ����δ�յ��ݵ�δ�ղ��ֵĺϼ�ֵ��Ϊ�ڳ���Ϣ
	 * 
	 * @param start_date
	 *            ��ʼ����
	 * @param client_name
	 *            �ͻ�����
	 * @param jsr
	 *            ������
	 * @return
	 */
	public Map getYshzJsrQc(String start_date, String client_name, String jsr) {
		return yshzJsrDao.getYshzJsrQc(start_date, client_name, jsr);
	}

	/**
	 * ҵ��Ӧ����ˮ
	 * 
	 * @param start_date
	 *            ��ʼʱ��
	 * @param end_date
	 *            ����ʱ��
	 * @param client_name
	 *            �ͻ�����
	 * @param jsr
	 *            ������
	 * @return
	 */
	public Map getYshzFlow(String start_date, String end_date,
			String client_name, String jsr) {
		return yshzJsrDao.getYshzFlow(start_date, end_date, client_name, jsr);
	}
	
	
	/**
	 * ҵ��Ӧ����ˮ
	 * @param start_date  ��ʼʱ��
	 * @param end_date    ����ʱ��
	 * @param client_name �ͻ�����
	 * @param jsr         ������
	 * @return
	 */
	public List getXsdList(String start_date,String end_date,String client_name,String jsr){
		return yshzJsrDao.getXsdList(start_date, end_date, client_name, jsr);
	}
	
	
	/**
	 * ҵ��ԱӦ�ջ��ܱ�--Ӧ����
	 * @return
	 */
	public Map getYwyYsjeMap(){
		return yshzJsrDao.getYwyYsjeMap();
	}
	
	
	/**
	 * ҵ��ԱӦ�ջ��ܱ�--����Ӧ�տ�
	 * @return
	 */
	public Map getYwyCqjeMap(){
		return yshzJsrDao.getYwyCqjeMap();
	}
	
	
	/**
	 * ҵ��ԱӦ�ջ���--����ڡ�ƽ������
	 * @param client_id
	 * @return
	 */
	public Map getYwyCqts(){
		return yshzJsrDao.getYwyCqts();
	}

	public YshzJsrDAO getYshzJsrDao() {
		return yshzJsrDao;
	}

	public void setYshzJsrDao(YshzJsrDAO yshzJsrDao) {
		this.yshzJsrDao = yshzJsrDao;
	}

}
