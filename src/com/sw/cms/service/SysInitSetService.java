package com.sw.cms.service;

import com.sw.cms.dao.SysInitSetDAO;
import com.sw.cms.model.SysInitSet;

public class SysInitSetService {
	
	private SysInitSetDAO sysInitSetDao;
	
	/**
	 * ����ϵͳ��������
	 * @param sysInitSet
	 */
	public void setQyrq(SysInitSet sysInitSet){
		sysInitSetDao.setQyrq(sysInitSet);
	}
	
	
	/**
	 * ���ó�ʼ���
	 * @param sysInitSet
	 */
	public void setCswc(SysInitSet sysInitSet){
		sysInitSetDao.setCswc(sysInitSet);
	}
	
	
	/**
	 * <p>��ȡ��ǰϵͳ�Ƿ���ɳ�ʼ</p>
	 * @return flag 0:δ��ɳ�ʼ��1:��ɳ�ʼ
	 */
	public String getQyFlag(){
		return sysInitSetDao.getQyFlag();
	}
	
	
	/**
	 * ȡϵͳ��ʼ������
	 * @return
	 */
	public SysInitSet getSysInitSet(){
		
		return sysInitSetDao.getSysInitSet();
	}
	
	/**
	 * ���ϵͳ����
	 */
	public void updateSys_ClearData(){
		sysInitSetDao.updateSys_ClearData();
	}
	
	/**
	 * �ж��Ƿ���ڸ����
	 * @return
	 */
	public boolean isExistFkc(){
		return sysInitSetDao.isExistFkc();
	}


	public SysInitSetDAO getSysInitSetDao() {
		return sysInitSetDao;
	}


	public void setSysInitSetDao(SysInitSetDAO sysInitSetDao) {
		this.sysInitSetDao = sysInitSetDao;
	}

}
