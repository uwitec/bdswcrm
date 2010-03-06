package com.sw.cms.service;

import java.util.Map;

import com.sw.cms.dao.AccountDzdDAO;
import com.sw.cms.dao.AccountsDAO;
import com.sw.cms.dao.NbzzDAO;
import com.sw.cms.model.AccountDzd;
import com.sw.cms.model.Nbzz;
import com.sw.cms.model.Page;

public class NbzzService {
	
	private NbzzDAO nbzzDao;
	private AccountsDAO accountsDao;
	private AccountDzdDAO accountDzdDao;
	
	/**
	 * �ڲ�ת���б�
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getNbzzList(String con,int curPage, int rowsPerPage){
		return nbzzDao.getNbzzList(con, curPage, rowsPerPage);
	}
	
	
	/**
	 * �����ڲ�ת����Ϣ
	 * @param nbzz
	 */
	public void saveNbzz(Nbzz nbzz){
		nbzzDao.saveNbzz(nbzz);
		
		if(nbzz.getState().equals("���ύ")){
			this.updateAccountJe(nbzz);
		}
	}
	
	
	/**
	 * �����ڲ�ת����Ϣ
	 * @param nbzz
	 */
	public void updateNbzz(Nbzz nbzz){
		
		if(nbzzDao.isNbzzSubmit(nbzz.getId())){
			return;
		}
		
		nbzzDao.updateNbzz(nbzz);
		
		if(nbzz.getState().equals("���ύ")){
			this.updateAccountJe(nbzz);
		}
	}
	
	
	/**
	 * ȡ�ڲ�ת����Ϣ
	 */
	public Object getNbzz(String id){
		return nbzzDao.getNbzz(id);
	}
	
	
	/**
	 * ɾ���ڲ�ת����Ϣ
	 * @param id
	 */
	public void delNbzz(String id){
		nbzzDao.delNbzz(id);
		
	}
	
	
	/**
	 * ȡ��ǰ���õ����к�
	 * @return
	 */
	public String updateNbzzId() {
		return nbzzDao.getNbzzId();
	}
	
	
	/**
	 * ��������˻����
	 * @param nbzz
	 */
	private void updateAccountJe(Nbzz nbzz){
		String zczh = nbzz.getZczh();
		String zrzh = nbzz.getZrzh();
		
		double zzje = nbzz.getZzje();
		
		this.saveAccountDzd(nbzz.getId(), zczh, 0-zzje, nbzz.getCzr(), nbzz.getJsr(), "�ڲ�ת�ˣ����[" + nbzz.getId() + "]");
		accountsDao.updateAccountJe(zczh, zzje);
		
		this.saveAccountDzd(nbzz.getId(), zrzh, zzje, nbzz.getCzr(), nbzz.getJsr(), "�ڲ�ת�ˣ����[" + nbzz.getId() + "]");
		accountsDao.addAccountJe(zrzh, zzje);		
	}
	
	/**
	 * ����ʽ��׼�¼
	 * @param cgfk
	 */
	private void saveAccountDzd(String nbzz_id,String account_id,double jyje,String czr,String jsr,String remark){
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
		accountDzd.setAction_url("viewNbzz.html?id=" + nbzz_id);
		
		accountDzdDao.addDzd(accountDzd);
	}	
	
	
	public AccountsDAO getAccountsDao() {
		return accountsDao;
	}
	public void setAccountsDao(AccountsDAO accountsDao) {
		this.accountsDao = accountsDao;
	}
	public NbzzDAO getNbzzDao() {
		return nbzzDao;
	}
	public void setNbzzDao(NbzzDAO nbzzDao) {
		this.nbzzDao = nbzzDao;
	}


	public AccountDzdDAO getAccountDzdDao() {
		return accountDzdDao;
	}


	public void setAccountDzdDao(AccountDzdDAO accountDzdDao) {
		this.accountDzdDao = accountDzdDao;
	}

}
