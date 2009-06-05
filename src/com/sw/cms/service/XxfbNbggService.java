package com.sw.cms.service;

import com.sw.cms.dao.XxfbNbggDAO;
import com.sw.cms.model.Page;
import com.sw.cms.model.XxfbNbgg;

public class XxfbNbggService {
	
	private XxfbNbggDAO xxfbNbggDao;
	
	/**
	 * ȡ�ڲ�������Ϣ�б�
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getNbggList(int curPage, int rowsPerPage){
		return xxfbNbggDao.getNbggList(curPage, rowsPerPage);
	}
	
	
	/**
	 * ȡ�ڲ�������Ϣ�б�
	 * @param curPage
	 * @param rowsPerPage
	 * @param type
	 * @return
	 */
	public Page getNbggList(int curPage,int rowsPerPage,String type){
		return xxfbNbggDao.getNbggList(curPage, rowsPerPage, type);
	}
	
	
	/**
	 * �����ڲ�������Ϣ
	 * @param info
	 */
	public void saveNbgg(XxfbNbgg info){
		xxfbNbggDao.saveNbgg(info);
	}
	
	
	/**
	 * �����ڲ�������Ϣ
	 * @param info
	 */
	public void updateNbgg(XxfbNbgg info){
		xxfbNbggDao.updateNbgg(info);
	}
	
	
	/**
	 * ����IDȡ�ڲ�������Ϣ
	 * @param id
	 * @return
	 */
	public XxfbNbgg getNbgg(String id){
		return xxfbNbggDao.getNbgg(id);
	}
	
	
	/**
	 * ɾ���ڲ�������Ϣ
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
