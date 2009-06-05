package com.sw.cms.service;

import java.util.List;

import com.sw.cms.dao.YftjDAO;

public class YftjService {

	private YftjDAO yftjDao;
	
	/**
	 * Ӧ���б�
	 * @param yfrq1
	 * @param yfrq2
	 * @return
	 */
	public List getYftjList(String yfrq1,String yfrq2){
		return yftjDao.getYftjList(yfrq1, yfrq2);
	}
	
	
	/**
	 * Ӧ����ϸ
	 * @param gysbh
	 * @return
	 */
	public List getYftjMxList(String gysbh){
		return yftjDao.getYftjMxList(gysbh);
	}


	public YftjDAO getYftjDao() {
		return yftjDao;
	}


	public void setYftjDao(YftjDAO yftjDao) {
		this.yftjDao = yftjDao;
	}
	
}
