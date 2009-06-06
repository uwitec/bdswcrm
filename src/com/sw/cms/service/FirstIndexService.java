package com.sw.cms.service;

import java.util.List;

import com.sw.cms.dao.FirstIndexDAO;

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


	public FirstIndexDAO getFirstIndexDao() {
		return firstIndexDao;
	}


	public void setFirstIndexDao(FirstIndexDAO firstIndexDao) {
		this.firstIndexDao = firstIndexDao;
	}

}
