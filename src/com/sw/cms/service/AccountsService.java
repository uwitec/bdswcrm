package com.sw.cms.service;

import java.util.List;
import java.util.Map;

import com.sw.cms.dao.AccountsDAO;
import com.sw.cms.model.Accounts;

public class AccountsService {
	
	private AccountsDAO accountsDao;
	
	/**
	 * 取账户列表
	 * @return
	 */
	public List getAccountList(){
		return accountsDao.getAccountList();
	}
	
	
	/**
	 * 保存账户信息
	 * @param accounts
	 */
	public void saveAccounts(Accounts accounts){
		accountsDao.saveAccounts(accounts);
		
	}
	
	
	/**
	 * 更新账户信息
	 * @param accounts
	 */
	public void updateAccounts(Accounts accounts){
		accountsDao.updateAccounts(accounts);
	}
	
	
	/**
	 * 取账号信息
	 * @param id
	 * @return
	 */
	public Map getAccounts(String id){
		return accountsDao.getAccounts(id);
	}
	
	
	/**
	 * 删除账号信息
	 * @param id
	 */
	public void delAccounts(String id){
		accountsDao.delAccounts(id);
	}
	
	
	/**
	 * 校验账户金额减少后金额是否小于0
	 * @param account_id 账户编号
	 * @param je  减少金额
	 * @return true:小于0;false:大于0
	 */
	public boolean isZhjeXyZero(String account_id,double je){
		return accountsDao.isZhjeXyZero(account_id, je);
	}
	
	
	/**
	 * 判断账户是否可以删除，发生业务往来的账户不能删除<BR>
	 * 往来业务包括：对账单、账户期初
	 * @param account_id
	 * @return
	 */
	public boolean isCanDel(String account_id){
		return accountsDao.isCanDel(account_id);
	}
	
	
	public void initAccountJe(String account_id,double qcje){
		accountsDao.initAccountJe(account_id, qcje);
	}
	

	public AccountsDAO getAccountsDao() {
		return accountsDao;
	}

	public void setAccountsDao(AccountsDAO accountsDao) {
		this.accountsDao = accountsDao;
	}

}
