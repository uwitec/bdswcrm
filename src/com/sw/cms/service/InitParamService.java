package com.sw.cms.service;

import com.sw.cms.dao.InitParamDAO;

public class InitParamService {
	
	private InitParamDAO initParamDao;
	
	/**
	 * ����ҵ����ز���Ϊ1
	 *
	 */
	public void updateParam(){
		initParamDao.updateParam();
	}
	
	/**
	 * �����˻��ڳ�
	 *
	 */
	public void genAccountQc(){
		initParamDao.genAccountQc();
	}
	
	
	/**
	 * ���ɿ���ڳ�
	 *
	 */
	public void genKcQc(){
		initParamDao.genKcQc();
	}
	
	
	/**
	 * ���ɿͻ������ڳ�
	 * ������ڳ���������������ڽ�����ڳ�
	 *
	 */
	public void genYsQc(){
		initParamDao.genYsQc();
	}
	
	
	/**
	 * ɾ�������޵���Ϣ
	 *
	 */
	public void delExpireMsg(){
		initParamDao.delExpireMsg();
	}
	

	public InitParamDAO getInitParamDao() {
		return initParamDao;
	}

	public void setInitParamDao(InitParamDAO initParamDao) {
		this.initParamDao = initParamDao;
	}

}
