package com.sw.cms.service;

import java.util.List;
import java.util.Map;

import com.sw.cms.dao.SerialNumDAO;
import com.sw.cms.model.Page;
import com.sw.cms.model.SerialNumMng;
import com.sw.cms.model.SerialNumPd;

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
	
	
	/**
	 * ���ݿⷿIDȡ���к��б�
	 * @param store_id
	 * @return
	 */
	public List getSerialNumMngListByStoreId(String store_id){
		return serialNumDao.getSerialNumMngListByStoreId(store_id);
	}
	
	/**
	 * �������к��̵���
	 * @param info
	 */
	public void insertSerialNumPd(SerialNumPd info){
		serialNumDao.insertSerialNumPd(info);
	}
	
	/**
	 * �̵��Ƿ��Ա���
	 * @param cdate
	 * @param store_id
	 * @return
	 */
	public boolean isSerialNumPdExist(String cdate,String store_id){
		return serialNumDao.isSerialNumPdExist(cdate, store_id);
	}
	
	/**
	 * ���к��̵��¼
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getSerialNumPdPage(String con,int curPage, int rowsPerPage){
		return serialNumDao.getSerialNumPdPage(con, curPage, rowsPerPage);
	}
	
	public SerialNumDAO getSerialNumDao() {
		return serialNumDao;
	}

	public void setSerialNumDao(SerialNumDAO serialNumDao) {
		this.serialNumDao = serialNumDao;
	}	
}
