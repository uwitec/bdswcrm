package com.sw.cms.service;

import java.util.Map;

import com.sw.cms.dao.AccountDzdDAO;
import com.sw.cms.dao.AccountsDAO;
import com.sw.cms.dao.FysqDAO;
import com.sw.cms.dao.QtzcDAO;
import com.sw.cms.model.AccountDzd;
import com.sw.cms.model.Page;
import com.sw.cms.model.Qtzc;

public class QtzcService {
	
	private QtzcDAO qtzcDao;
	private AccountsDAO accountsDao;
	private AccountDzdDAO accountDzdDao;
	private FysqDAO fysqDao;
	
	
	/**
	 * ȡ����֧���б�
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getQtzcList(String con,int curPage, int rowsPerPage){
		return qtzcDao.getQtzcList(con, curPage, rowsPerPage);
	}
	
	
	/**
	 * ��������֧��
	 * @param qtsr
	 */
	public void saveQtzc(Qtzc qtzc){
		qtzcDao.saveQtzc(qtzc);
		
		if(qtzc.getState().equals("���ύ")){
			this.updateAccountJe(qtzc);
		}
	}
	
	
	/**
	 * ��������֧��
	 * @param qtsr
	 */
	public void updateQtzc(Qtzc qtzc){
		qtzcDao.updateQtzc(qtzc);
		
		if(qtzc.getState().equals("���ύ")){
			
			//�����˻�����Ӷ��˵�
			this.updateAccountJe(qtzc);
			
			//���·������뵥״̬
			if(qtzc.getFysq_id() != null && !qtzc.getFysq_id().equals("")){
				fysqDao.updateFysqState(qtzc.getFysq_id(), "��֧��");
			}
		}
	}
	
	
	/**
	 * ȡ����֧��
	 * @param id
	 * @return
	 */
	public Object getQtzc(String id){
		return qtzcDao.getQtzc(id);
	}
	
	
	/**
	 * ɾ������֧��
	 * @param id
	 */
	public void delQtzc(String id){
		qtzcDao.delQtzc(id);
	}
	
	
	/**
	 * ȡ��ǰ���õ����к�
	 * @return
	 */
	public String updateQtzcID() {
		return qtzcDao.getQtzcID();
	}
	
	
	/**
	 * ��֧������ȥ
	 * @param lsd
	 */
	private void updateAccountJe(Qtzc qtzc){
		String account_id = qtzc.getZczh();
		double je = qtzc.getZcje();
		
		this.saveAccountDzd(qtzc.getId(), account_id, 0-qtzc.getZcje(), qtzc.getCzr(), qtzc.getJsr(), "һ����ã����[" + qtzc.getId() + "]");
		accountsDao.updateAccountJe(account_id, je);
	}	
	
	
	/**
	 * ����ʽ��׼�¼
	 * @param cgfk
	 */
	private void saveAccountDzd(String id,String account_id,double jyje,String czr,String jsr,String remark){
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
		accountDzd.setAction_url("viewQtzc.html?id=" + id);
		
		accountDzdDao.addDzd(accountDzd);
	}

	public QtzcDAO getQtzcDao() {
		return qtzcDao;
	}

	public void setQtzcDao(QtzcDAO qtzcDao) {
		this.qtzcDao = qtzcDao;
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


	public FysqDAO getFysqDao() {
		return fysqDao;
	}


	public void setFysqDao(FysqDAO fysqDao) {
		this.fysqDao = fysqDao;
	}

}
