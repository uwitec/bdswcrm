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
	 * ȡ�ͻ�������ʼ�б�
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getWlInitList(String con,int curPage, int rowsPerPage){
		return clientWlInitDao.getWlInitList(con, curPage, rowsPerPage);
	}
	
	
	/**
	 * ����������ʼ��ֵ
	 * ͬʱ��������ʼֵ����ͻ��ڳ���
	 * @param clientWlInit
	 */
	public void insertWlInitInfo(ClientWlInit clientWlInit){
		
		
		
		SysInitSet sysInitSet = sysInitSetDao.getSysInitSet();		
		String strQyrq = sysInitSet.getQyrq();		
		Date qyrq = DateComFunc.strToDate(strQyrq,"yyyy-MM-dd"); //ϵͳ��������
		
		String client_name = clientWlInit.getClient_name();
		
		Date curDate = new Date();     //��ǰʱ��
		
		double curYs = clientWlInit.getYsqc();   //��ʼ����¼��Ӧ�ս��
		double curYf = clientWlInit.getYfqc();   //��ʼ����¼��Ӧ�����
		
		//Ĭ�ϳ�ʼֵΪ��ǰ¼��ֵ
		double qcys = curYs;
		double qcyf = curYf;
		while(DateComFunc.formatDate(curDate, "yyyy-MM-dd").compareToIgnoreCase(DateComFunc.formatDate(qyrq, "yyyy-MM-dd")) >= 0){
			
			//������ڳ�Ӧ��ֵ = �ڳ�Ӧ�� - ���ڷ��� + �����տ�
			qcys = qcys - initParamDao.getFsYingshouJe(client_name, DateComFunc.formatDate(curDate, "yyyy-MM-dd")) + initParamDao.getFsYishouJe(client_name, DateComFunc.formatDate(curDate, "yyyy-MM-dd"));
			
			//������ڳ�Ӧ��ֵ = �ڳ�Ӧ�� - ���ڷ��� + ���ڸ���
			qcyf = qcyf - initParamDao.getFsYingfuJe(client_name, DateComFunc.formatDate(curDate, "yyyy-MM-dd")) + initParamDao.getFsYifuJe(client_name, DateComFunc.formatDate(curDate, "yyyy-MM-dd"));
			
			//�����ڳ�ֵ
			clientWlInitDao.insertClientQc(client_name, qcys, qcyf, DateComFunc.formatDate(curDate, "yyyy-MM-dd"));
			
			curDate = DateComFunc.addDay(curDate, -1);  //��ǰ������1
		}
		
		
		//����������ʼֵ
		clientWlInit.setYfqc(qcyf);
		clientWlInit.setYsqc(qcys);
		
		clientWlInitDao.insertWlInitInfo(clientWlInit);
		
	}
	
	
	/**
	 * �����û�IDȡ��ʼ�б�
	 * @param client_name
	 * @return ��ʼ�б�
	 */
	public List getWlInitList(String client_name){
		return clientWlInitDao.getWlInitList(client_name);
	}
	
	
	/**
	 * ɾ���ͻ�������Ϣ
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
