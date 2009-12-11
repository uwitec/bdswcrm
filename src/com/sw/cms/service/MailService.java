package com.sw.cms.service;

import java.util.List;

import com.sw.cms.dao.MailDAO;
import com.sw.cms.model.MailSet;
import com.sw.cms.model.Page;

public class MailService {
	
	private MailDAO mailDao;
	
	
	/**
	 * 取选择邮件接受人列表
	 * @param type
	 * @return
	 */
	public Page getLxrList(String client_type, int curPage, int rowsPerPage){
		return mailDao.getLxrList(client_type, curPage, rowsPerPage);
	}
	/**
	 * 获取邮件接收人（不分页）
	 * @param client_type
	 * @return
	 */
	public List getLxrAll(String client_type)
	{
		return mailDao.getLxrAll(client_type);
	}
	
	
	/**
	 * 取当前用户邮箱设置
	 * @param user_id
	 * @return
	 */
	public MailSet getMailSet(String user_id){
		return mailDao.getMailSet(user_id);
	}
	
	
	/**
	 * 更新用户邮箱设置
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
