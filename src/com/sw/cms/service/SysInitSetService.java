package com.sw.cms.service;

import java.util.Map;

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

	
	/**
	 * 返回系统LOGO设置
	 * @return
	 */
	public Map getSysLogo(){
		return sysInitSetDao.getSysLogo();
	}
	
	
	/**
	 * 保存系统LOGO设置
	 * @param cpy_name
	 * @param logo_url
	 */
	public void saveSysLogo(String cpy_name,String logo_url){
		sysInitSetDao.saveSysLogo(cpy_name, logo_url);
	}
	
	
	/**
	 * 返回报表相关设置
	 * @return
	 */
	public Map getReportSet(){
		return sysInitSetDao.getReportSet();
	}
	
	
	/**
	 * 保存报表相关设置
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
