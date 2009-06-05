package com.sw.cms.service;

import com.sw.cms.dao.XxfbNbggDAO;
import com.sw.cms.model.Page;
import com.sw.cms.model.XxfbNbgg;

public class XxfbNbggService {
	
	private XxfbNbggDAO xxfbNbggDao;
	
	/**
	 * 取内部公告信息列表
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getNbggList(int curPage, int rowsPerPage){
		return xxfbNbggDao.getNbggList(curPage, rowsPerPage);
	}
	
	
	/**
	 * 取内部公告信息列表
	 * @param curPage
	 * @param rowsPerPage
	 * @param type
	 * @return
	 */
	public Page getNbggList(int curPage,int rowsPerPage,String type){
		return xxfbNbggDao.getNbggList(curPage, rowsPerPage, type);
	}
	
	
	/**
	 * 保存内部公告信息
	 * @param info
	 */
	public void saveNbgg(XxfbNbgg info){
		xxfbNbggDao.saveNbgg(info);
	}
	
	
	/**
	 * 更新内部公告信息
	 * @param info
	 */
	public void updateNbgg(XxfbNbgg info){
		xxfbNbggDao.updateNbgg(info);
	}
	
	
	/**
	 * 根据ID取内部公告信息
	 * @param id
	 * @return
	 */
	public XxfbNbgg getNbgg(String id){
		return xxfbNbggDao.getNbgg(id);
	}
	
	
	/**
	 * 删除内部公告信息
	 * @param id
	 */
	public void delNbgg(String id){
		xxfbNbggDao.delNbgg(id);
	}
	

	public XxfbNbggDAO getXxfbNbggDao() {
		return xxfbNbggDao;
	}

	public void setXxfbNbggDao(XxfbNbggDAO xxfbNbggDao) {
		this.xxfbNbggDao = xxfbNbggDao;
	}
}
