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
 * 摊销付款
 * @author liyt
 *
 */
public class TxfkService {
	
	private TxfkDAO txfkDao;
	private AccountsDAO accountsDao;
	private AccountDzdDAO accountDzdDao;
	
	
	/**
	 * 根据查询条件取摊销付款列表
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getTxfkList(String con,int curPage, int rowsPerPage){
		return txfkDao.getTxfkList(con, curPage, rowsPerPage);
	}
	
	
	/**
	 * 更新摊销付款信息
	 * 编号不存在添加，编号存在更新
	 * @param txfk
	 * @param txfkDescs
	 */
	public void updateTxfk(Txfk txfk,List txfkDescs){
		txfkDao.updateTxfk(txfk, txfkDescs);
		
		//如果状态为已提交，则生成相应对账单，修改账户余额
		if(txfk.getState().equals("已提交")){
			//修改账户余额
			accountsDao.updateAccountJe(txfk.getAccount_id(), txfk.getFkje());
			
			//保存对账单
			this.saveAccountDzd(txfk);
		}
	}
	
	
	/**
	 * 根据编号获取摊销付款信息
	 * @param id
	 * @return
	 */
	public Txfk getTxfk(String id){
		return txfkDao.getTxfk(id);
	}
	
	
	/**
	 * 根据摊销付款编号取明细信息列表
	 * @param id
	 * @return
	 */
	public List getTxfkDescs(String id){
		return txfkDao.getTxfkDescs(id);
	}
	
	
	/**
	 * 删除摊销付款相关信息
	 * @param id
	 */
	public void delTxfk(String id){
		txfkDao.delTxfk(id);
	}
	
	
	/**
	 * 取当前可用的摊销付款编号
	 * @return
	 */
	public String updateTxfkID() {
		return txfkDao.getTxfkID();
	}
	
	
	/**
	 * 添加资金交易记录
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
		accountDzd.setRemark("付摊销付款，编号[" + txfk.getId() + "]");
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
