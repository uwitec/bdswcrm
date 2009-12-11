package com.sw.cms.service;

import java.util.List;

import com.sw.cms.dao.MailDAO;
import com.sw.cms.model.MailSet;
import com.sw.cms.model.Page;

public class MailService {
	
	private MailDAO mailDao;
	
	
	/**
	 * ȡѡ���ʼ��������б�
	 * @param type
	 * @return
	 */
	public Page getLxrList(String client_type, int curPage, int rowsPerPage){
		return mailDao.getLxrList(client_type, curPage, rowsPerPage);
	}
	/**
	 * ��ȡ�ʼ������ˣ�����ҳ��
	 * @param client_type
	 * @return
	 */
	public List getLxrAll(String client_type)
	{
		return mailDao.getLxrAll(client_type);
	}
	
	
	/**
	 * ȡ��ǰ�û���������
	 * @param user_id
	 * @return
	 */
	public MailSet getMailSet(String user_id){
		return mailDao.getMailSet(user_id);
	}
	
	
	/**
	 * �����û���������
	 * @param mailSet
	 */
	public void updateMailSet(MailSet mailSet){
		mailDao.updateMailSet(mailSet);
	}

	
	public MailDAO getMailDao() {
		return mailDao;
	}

	public void setMailDao(MailDAO mailDao) {
		this.mailDao = mailDao;
	}

}
