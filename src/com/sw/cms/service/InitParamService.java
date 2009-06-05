package com.sw.cms.service;

import com.sw.cms.dao.InitParamDAO;

public class InitParamService {
	
	private InitParamDAO initParamDao;
	
	/**
	 * 重置业务相关参数为1
	 *
	 */
	public void updateParam(){
		initParamDao.updateParam();
	}
	
	/**
	 * 生成账户期初
	 *
	 */
	public void genAccountQc(){
		initParamDao.genAccountQc();
	}
	
	
	/**
	 * 生成库存期初
	 *
	 */
	public void genKcQc(){
		initParamDao.genKcQc();
	}
	
	
	/**
	 * 生成客户往来期初
	 * 昨天的期初加昨天的往来等于今天的期初
	 *
	 */
	public void genYsQc(){
		initParamDao.genYsQc();
	}
	
	
	/**
	 * 删除超期限的消息
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
