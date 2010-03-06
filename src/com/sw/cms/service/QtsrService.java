package com.sw.cms.service;

import java.util.Map;

import com.sw.cms.dao.AccountDzdDAO;
import com.sw.cms.dao.AccountsDAO;
import com.sw.cms.dao.QtsrDAO;
import com.sw.cms.model.AccountDzd;
import com.sw.cms.model.Page;
import com.sw.cms.model.Qtsr;

public class QtsrService {
	
	private QtsrDAO qtsrDao;
	private AccountsDAO accountsDao;
	private AccountDzdDAO accountDzdDao;
	
	
	/**
	 * 取其它收入列表
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getQtsrList(String con,int curPage, int rowsPerPage){
		return qtsrDao.getQtsrList(con, curPage, rowsPerPage);
	}
	
	
	/**
	 * 保存其它收入
	 * @param qtsr
	 */
	public void saveQtsr(Qtsr qtsr){
		qtsrDao.saveQtsr(qtsr);
		
		if(qtsr.getState().equals("已提交")){
			this.addAccountJe(qtsr);
		}
	}
	
	
	/**
	 * 更新其它收入
	 * @param qtsr
	 */
	public void updateQtsr(Qtsr qtsr){
		
		//如果其它收入已经提交，不做其它处理
		if(qtsrDao.isQtsrSubmit(qtsr.getId())){
			return;
		}
		qtsrDao.updateQtsr(qtsr);
		
		if(qtsr.getState().equals("已提交")){
			this.addAccountJe(qtsr);
		}
	}
	
	
	/**
	 * 取其它收入
	 * @param id
	 * @return
	 */
	public Object getQtsr(String id){
		return qtsrDao.getQtsr(id);
	}
	
	
	/**
	 * 删除其它收入
	 * @param id
	 */
	public void delQtsr(String id){
		qtsrDao.delQtsr(id);
	}
	
	
	/**
	 * 取当前可用的序列号
	 * @return
	 */
	public String updateQtsrID() {
		return qtsrDao.getQtsrID();
	}
	
	
	
	/**
	 * 将收款金额入账户中
	 * @param lsd
	 */
	private void addAccountJe(Qtsr qtsr){
		String account_id = qtsr.getSkzh();
		double je = qtsr.getSkje();
		
		this.saveAccountDzd(account_id, je, qtsr.getCzr(), qtsr.getJsr(), "其它收入，编号[" + qtsr.getId() + "]",qtsr.getId());
		accountsDao.addAccountJe(account_id, je);
	}
	
	/**
	 * 添加资金交易记录
	 * @param cgfk
	 */
	private void saveAccountDzd(String account_id,double jyje,String czr,String jsr,String remark,String qtsr_id){
		AccountDzd accountDzd = new AccountDzd();
		
		double zhye = 0;
		Map map = accountsDao.getAccounts(account_id);
		if(map != null){
			zhye = (map.get("dqje")==null?0:((Double)map.get("dqje")).doubleValue());
		}
		
		accountDzd.setAccount_id(account_id);
		accountDzd.setJyje(jyje);
		accountDzd.setZhye(zhye + jyje);
		accountDzd.setRemark(remark);
		accountDzd.setCzr(czr);
		accountDzd.setJsr(jsr);
		accountDzd.setAction_url("viewQtsr.html?id=" + qtsr_id);
		
		accountDzdDao.addDzd(accountDzd);
	}		
	

	public QtsrDAO getQtsrDao() {
		return qtsrDao;
	}

	public void setQtsrDao(QtsrDAO qtsrDao) {
		this.qtsrDao = qtsrDao;
	}


	public AccountsDAO getAccountsDao() {
		return accountsDao;
	}


	public void setAccountsDao(AccountsDAO accountsDao) {
		this.accountsDao = accountsDao;
	}


	public AccountDzdDAO getAccountDzdDao() {
		return accountDzdDao;
	}


	public void setAccountDzdDao(AccountDzdDAO accountDzdDao) {
		this.accountDzdDao = accountDzdDao;
	}
	

}
