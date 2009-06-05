package com.sw.cms.service;

import java.util.List;
import java.util.Map;

import com.sw.cms.dao.AccountDzdDAO;
import com.sw.cms.dao.AccountsDAO;
import com.sw.cms.dao.TxfkDAO;
import com.sw.cms.model.AccountDzd;
import com.sw.cms.model.Page;
import com.sw.cms.model.Txfk;

/**
 * ̯������
 * @author liyt
 *
 */
public class TxfkService {
	
	private TxfkDAO txfkDao;
	private AccountsDAO accountsDao;
	private AccountDzdDAO accountDzdDao;
	
	
	/**
	 * ���ݲ�ѯ����ȡ̯�������б�
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getTxfkList(String con,int curPage, int rowsPerPage){
		return txfkDao.getTxfkList(con, curPage, rowsPerPage);
	}
	
	
	/**
	 * ����̯��������Ϣ
	 * ��Ų�������ӣ���Ŵ��ڸ���
	 * @param txfk
	 * @param txfkDescs
	 */
	public void updateTxfk(Txfk txfk,List txfkDescs){
		txfkDao.updateTxfk(txfk, txfkDescs);
		
		//���״̬Ϊ���ύ����������Ӧ���˵����޸��˻����
		if(txfk.getState().equals("���ύ")){
			//�޸��˻����
			accountsDao.updateAccountJe(txfk.getAccount_id(), txfk.getFkje());
			
			//������˵�
			this.saveAccountDzd(txfk);
		}
	}
	
	
	/**
	 * ���ݱ�Ż�ȡ̯��������Ϣ
	 * @param id
	 * @return
	 */
	public Txfk getTxfk(String id){
		return txfkDao.getTxfk(id);
	}
	
	
	/**
	 * ����̯��������ȡ��ϸ��Ϣ�б�
	 * @param id
	 * @return
	 */
	public List getTxfkDescs(String id){
		return txfkDao.getTxfkDescs(id);
	}
	
	
	/**
	 * ɾ��̯�����������Ϣ
	 * @param id
	 */
	public void delTxfk(String id){
		txfkDao.delTxfk(id);
	}
	
	
	/**
	 * ȡ��ǰ���õ�̯��������
	 * @return
	 */
	public String updateTxfkID() {
		return txfkDao.getTxfkID();
	}
	
	
	/**
	 * ����ʽ��׼�¼
	 * @param cgfk
	 */
	private void saveAccountDzd(Txfk txfk){
		AccountDzd accountDzd = new AccountDzd();
		
		double zhye = 0;
		Map map = accountsDao.getAccounts(txfk.getAccount_id());
		if(map != null){
			zhye = (map.get("dqje")==null?0:((Double)map.get("dqje")).doubleValue());
		}
		
		accountDzd.setAccount_id(txfk.getAccount_id());
		accountDzd.setJyje(0-txfk.getFkje());
		accountDzd.setZhye(zhye - txfk.getFkje());
		accountDzd.setRemark("��̯��������[" + txfk.getId() + "]");
		accountDzd.setCzr(txfk.getCzr());
		accountDzd.setJsr(txfk.getJsr());
		accountDzd.setAction_url("viewTxfk.html?id=" + txfk.getId());
		
		accountDzdDao.addDzd(accountDzd);
	}
	

	public TxfkDAO getTxfkDao() {
		return txfkDao;
	}

	public void setTxfkDao(TxfkDAO txfkDao) {
		this.txfkDao = txfkDao;
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
