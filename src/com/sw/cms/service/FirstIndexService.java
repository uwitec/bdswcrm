package com.sw.cms.service;

import java.util.List;

import com.sw.cms.dao.FirstIndexDAO;
import com.sw.cms.model.Page;

public class FirstIndexService {
	
	private FirstIndexDAO firstIndexDao;
	
	/**
	 * ���ش�������ⵥ�б�
	 * @return
	 */
	public List getDckdList(){
		return firstIndexDao.getDckdList();
	}
	
	/**
	 * ���ش���ⵥ���б�
	 * @return
	 */
	public List getDrkdList(){
		return firstIndexDao.getDrkdList();
	}
	
	
	/**
	 * ȡ����Ӧ�տ��б�
	 * @return
	 */
	public List getCqyskList(){
		return firstIndexDao.getCqyskList();
	}
	
	
	/**
	 * ȡ����Ӧ�����б�
	 * @return
	 */
	public List getCqYfkList(){
		return firstIndexDao.getCqYfkList();
	}
	
	/**
	 * ȡ��������б�
	 * @return
	 */
	public List getKcxxList(){
		return firstIndexDao.getKcxxList();
	}
	
	
	/**
	 * ���칤���б�
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getUndoWorks(String con,int curPage,int rowsPerPage){
		return firstIndexDao.getUndoWorks(con, curPage, rowsPerPage);
	}


	public FirstIndexDAO getFirstIndexDao() {
		return firstIndexDao;
	}


	public void setFirstIndexDao(FirstIndexDAO firstIndexDao) {
		this.firstIndexDao = firstIndexDao;
	}

}
