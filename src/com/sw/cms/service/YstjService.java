package com.sw.cms.service;

import java.util.List;

import com.sw.cms.dao.YstjDAO;

public class YstjService {
	
	private YstjDAO ystjDao;
	
	/**
	 * Ӧ��ͳ��
	 * @param ysrq1
	 * @param ysrq2
	 * @return
	 */
	public List getYsktj(String ysrq1,String ysrq2){
		return ystjDao.getYsktj(ysrq1, ysrq2);
	}
	
	
	/**
	 * Ӧ��ͳ����ϸ
	 * @param client_name
	 * @return
	 */
	public List getYskMx(String client_name){
		return ystjDao.getYskMx(client_name);
	}


	public YstjDAO getYstjDao() {
		return ystjDao;
	}


	public void setYstjDao(YstjDAO ystjDao) {
		this.ystjDao = ystjDao;
	}

}
