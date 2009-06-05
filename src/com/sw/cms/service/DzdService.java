package com.sw.cms.service;

import java.util.List;

import com.sw.cms.dao.DzdDAO;

public class DzdService {
	
	private DzdDAO dzdDao;
	
	
	/**
	 * 根据查询条件查询对账单列表
	 * @param client_id
	 * @param s_date
	 * @param e_date
	 * @return
	 */
	public List getDzdList(String client_id,String s_date,String e_date){
		return dzdDao.getDzdList(client_id, s_date, e_date);
	}
	

	public DzdDAO getDzdDao() {
		return dzdDao;
	}

	public void setDzdDao(DzdDAO dzdDao) {
		this.dzdDao = dzdDao;
	}

}
