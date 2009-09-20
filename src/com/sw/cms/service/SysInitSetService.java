package com.sw.cms.service;

import java.util.Map;

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

	
	/**
	 * ����ϵͳLOGO����
	 * @return
	 */
	public Map getSysLogo(){
		return sysInitSetDao.getSysLogo();
	}
	
	
	/**
	 * ����ϵͳLOGO����
	 * @param cpy_name
	 * @param logo_url
	 */
	public void saveSysLogo(String cpy_name,String logo_url){
		sysInitSetDao.saveSysLogo(cpy_name, logo_url);
	}
	
	
	/**
	 * ���ر����������
	 * @return
	 */
	public Map getReportSet(){
		return sysInitSetDao.getReportSet();
	}
	
	
	/**
	 * ���汨���������
	 * @param title_name
	 * @param foot_name
	 */
	public void saveReportSet(String title_name,String foot_name){
		sysInitSetDao.saveReportSet(title_name, foot_name);
	}
	

	public SysInitSetDAO getSysInitSetDao() {
		return sysInitSetDao;
	}


	public void setSysInitSetDao(SysInitSetDAO sysInitSetDao) {
		this.sysInitSetDao = sysInitSetDao;
	}

}
