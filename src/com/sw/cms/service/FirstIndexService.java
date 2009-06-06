package com.sw.cms.service;

import java.util.List;

import com.sw.cms.dao.FirstIndexDAO;

public class FirstIndexService {
	
	private FirstIndexDAO firstIndexDao;
	
	/**
	 * 返回待出库出库单列表
	 * @return
	 */
	public List getDckdList(){
		return firstIndexDao.getDckdList();
	}
	
	/**
	 * 返回待入库单据列表
	 * @return
	 */
	public List getDrkdList(){
		return firstIndexDao.getDrkdList();
	}
	
	
	/**
	 * 取超期应收款列表
	 * @return
	 */
	public List getCqyskList(){
		return firstIndexDao.getCqyskList();
	}
	
	
	/**
	 * 取超期应付款列表
	 * @return
	 */
	public List getCqYfkList(){
		return firstIndexDao.getCqYfkList();
	}
	
	/**
	 * 取库存下限列表
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
