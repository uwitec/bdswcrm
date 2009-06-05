package com.sw.cms.service;

import java.util.Date;
import java.util.List;

import com.sw.cms.dao.ClientWlInitDAO;
import com.sw.cms.dao.InitParamDAO;
import com.sw.cms.dao.SysInitSetDAO;
import com.sw.cms.model.ClientWlInit;
import com.sw.cms.model.Page;
import com.sw.cms.model.SysInitSet;
import com.sw.cms.util.DateComFunc;

public class ClientWlInitService {

	private ClientWlInitDAO clientWlInitDao;
	private SysInitSetDAO sysInitSetDao;
	private InitParamDAO initParamDao;
	
	/**
	 * 取客户往来初始列表
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getWlInitList(String con,int curPage, int rowsPerPage){
		return clientWlInitDao.getWlInitList(con, curPage, rowsPerPage);
	}
	
	
	/**
	 * 插入往来初始数值
	 * 同时将往来初始值插入客户期初中
	 * @param clientWlInit
	 */
	public void insertWlInitInfo(ClientWlInit clientWlInit){
		
		
		
		SysInitSet sysInitSet = sysInitSetDao.getSysInitSet();		
		String strQyrq = sysInitSet.getQyrq();		
		Date qyrq = DateComFunc.strToDate(strQyrq,"yyyy-MM-dd"); //系统启用日期
		
		String client_name = clientWlInit.getClient_name();
		
		Date curDate = new Date();     //当前时间
		
		double curYs = clientWlInit.getYsqc();   //初始界面录入应收金额
		double curYf = clientWlInit.getYfqc();   //初始界面录入应付金额
		
		//默认初始值为当前录入值
		double qcys = curYs;
		double qcyf = curYf;
		while(DateComFunc.formatDate(curDate, "yyyy-MM-dd").compareToIgnoreCase(DateComFunc.formatDate(qyrq, "yyyy-MM-dd")) >= 0){
			
			//当天的期初应收值 = 期初应收 - 当期发生 + 当期收款
			qcys = qcys - initParamDao.getFsYingshouJe(client_name, DateComFunc.formatDate(curDate, "yyyy-MM-dd")) + initParamDao.getFsYishouJe(client_name, DateComFunc.formatDate(curDate, "yyyy-MM-dd"));
			
			//当天的期初应付值 = 期初应付 - 当期发生 + 当期付款
			qcyf = qcyf - initParamDao.getFsYingfuJe(client_name, DateComFunc.formatDate(curDate, "yyyy-MM-dd")) + initParamDao.getFsYifuJe(client_name, DateComFunc.formatDate(curDate, "yyyy-MM-dd"));
			
			//保存期初值
			clientWlInitDao.insertClientQc(client_name, qcys, qcyf, DateComFunc.formatDate(curDate, "yyyy-MM-dd"));
			
			curDate = DateComFunc.addDay(curDate, -1);  //当前天数减1
		}
		
		
		//保存往来初始值
		clientWlInit.setYfqc(qcyf);
		clientWlInit.setYsqc(qcys);
		
		clientWlInitDao.insertWlInitInfo(clientWlInit);
		
	}
	
	
	/**
	 * 根据用户ID取初始列表
	 * @param client_name
	 * @return 初始列表
	 */
	public List getWlInitList(String client_name){
		return clientWlInitDao.getWlInitList(client_name);
	}
	
	
	/**
	 * 删除客户往来信息
	 * @param id
	 */
	public void delClientWlInit(String id){
		clientWlInitDao.delClientWlInit(id);
	}


	public ClientWlInitDAO getClientWlInitDao() {
		return clientWlInitDao;
	}


	public void setClientWlInitDao(ClientWlInitDAO clientWlInitDao) {
		this.clientWlInitDao = clientWlInitDao;
	}


	public SysInitSetDAO getSysInitSetDao() {
		return sysInitSetDao;
	}


	public void setSysInitSetDao(SysInitSetDAO sysInitSetDao) {
		this.sysInitSetDao = sysInitSetDao;
	}


	public InitParamDAO getInitParamDao() {
		return initParamDao;
	}


	public void setInitParamDao(InitParamDAO initParamDao) {
		this.initParamDao = initParamDao;
	}
	
}
