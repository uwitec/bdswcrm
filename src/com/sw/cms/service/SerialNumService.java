package com.sw.cms.service;

import java.util.List;
import java.util.Map;

import com.sw.cms.dao.SerialNumDAO;
import com.sw.cms.model.Page;
import com.sw.cms.model.SerialNumMng;

public class SerialNumService {

	private SerialNumDAO serialNumDao;

	/**
	 * ���ݲ�ѯ����ȡ���к��б�
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getSerialNumPage(String con, int curPage, int rowsPerPage){
		return serialNumDao.getSerialNumPage(con, curPage, rowsPerPage);
	}
	
	/**
	 * <p>�ж����к��Ƿ����</p>
	 * @param serial_num ���к�
	 * @return ���ڷ���true;�����ڷ���false
	 */
	public boolean chkSerialNum(String serial_num){
		return serialNumDao.chkSerialNum(serial_num);
	}
	
	
	/**
	 * <p>���桢�������к�</p>
	 * <p>������кŴ��������</p>
	 * <p>������кŲ����������</p>
	 * @param serialNumMng
	 */
	public void updateSerialNumState(SerialNumMng serialNumMng){
		serialNumDao.updateSerialNumState(serialNumMng);
	}
	
	
	/**
	 * �༭���к�
	 * @param serial_num
	 * @return
	 */
	public SerialNumMng editSerialNumMng(String serial_num){
		return serialNumDao.editSerialNumMng(serial_num);
	}
	
	
	/**
	 * ɾ�����к�
	 * @param serial_num
	 */
	public void delSerialNum(String serial_num){
		serialNumDao.delSerialNum(serial_num);
	}
	
	
	/**
	 * ��ѯ���к�����
	 * @param serial_num
	 * @return
	 */
	public List getSerialFlow(String serial_num){
		return serialNumDao.getSerialFlow(serial_num);
	}
	
	
	/**
	 * ��ѯ���кŵ�ǰ״̬
	 * ��Ӧ��Ʒ�����Ϣ
	 * @param serial_num
	 * @return
	 */
	public Map getSerialState(String serial_num){
		return serialNumDao.getSerialState(serial_num);
	}
	
	
	public SerialNumDAO getSerialNumDao() {
		return serialNumDao;
	}

	public void setSerialNumDao(SerialNumDAO serialNumDao) {
		this.serialNumDao = serialNumDao;
	}	
}
