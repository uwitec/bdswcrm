package com.sw.cms.service;

import com.sw.cms.dao.SysInitSetDAO;
import com.sw.cms.model.SysInitSet;

public class SysInitSetService {
	
	private SysInitSetDAO sysInitSetDao;
	
	/**
	 * 设置系统启动日期
	 * @param sysInitSet
	 */
	public void setQyrq(SysInitSet sysInitSet){
		sysInitSetDao.setQyrq(sysInitSet);
	}
	
	
	/**
	 * 设置初始完成
	 * @param sysInitSet
	 */
	public void setCswc(SysInitSet sysInitSet){
		sysInitSetDao.setCswc(sysInitSet);
	}
	
	
	/**
	 * <p>获取当前系统是否完成初始</p>
	 * @return flag 0:未完成初始；1:完成初始
	 */
	public String getQyFlag(){
		return sysInitSetDao.getQyFlag();
	}
	
	
	/**
	 * 取系统初始化对象
	 * @return
	 */
	public SysInitSet getSysInitSet(){
		
		return sysInitSetDao.getSysInitSet();
	}
	
	/**
	 * 清空系统数据
	 */
	public void updateSys_ClearData(){
		sysInitSetDao.updateSys_ClearData();
	}
	
	/**
	 * 判断是否存在负库存
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
