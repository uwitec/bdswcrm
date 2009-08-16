package com.sw.cms.service;

import java.util.List;
import java.util.Map;

import com.sw.cms.dao.AccountsDAO;
import com.sw.cms.model.Accounts;

public class AccountsService {
	
	private AccountsDAO accountsDao;
	
	/**
	 * ȡ�˻��б�
	 * @return
	 */
	public List getAccountList(){
		return accountsDao.getAccountList();
	}
	
	
	/**
	 * �����˻���Ϣ
	 * @param accounts
	 */
	public void saveAccounts(Accounts accounts){
		accountsDao.saveAccounts(accounts);
		
	}
	
	
	/**
	 * �����˻���Ϣ
	 * @param accounts
	 */
	public void updateAccounts(Accounts accounts){
		accountsDao.updateAccounts(accounts);
	}
	
	
	/**
	 * ȡ�˺���Ϣ
	 * @param id
	 * @return
	 */
	public Map getAccounts(String id){
		return accountsDao.getAccounts(id);
	}
	
	
	/**
	 * ɾ���˺���Ϣ
	 * @param id
	 */
	public void delAccounts(String id){
		accountsDao.delAccounts(id);
	}
	
	
	/**
	 * У���˻������ٺ����Ƿ�С��0
	 * @param account_id �˻����
	 * @param je  ���ٽ��
	 * @return true:С��0;false:����0
	 */
	public boolean isZhjeXyZero(String account_id,double je){
		return accountsDao.isZhjeXyZero(account_id, je);
	}
	
	
	/**
	 * �ж��˻��Ƿ����ɾ��������ҵ���������˻�����ɾ��<BR>
	 * ����ҵ����������˵����˻��ڳ�
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
