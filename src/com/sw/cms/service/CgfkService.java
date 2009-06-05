package com.sw.cms.service;

import java.util.List;
import java.util.Map;

import com.sw.cms.dao.AccountDzdDAO;
import com.sw.cms.dao.AccountsDAO;
import com.sw.cms.dao.CgfkDAO;
import com.sw.cms.dao.YufukDAO;
import com.sw.cms.model.AccountDzd;
import com.sw.cms.model.Cgfk;
import com.sw.cms.model.Page;
import com.sw.cms.model.Yufuk;

public class CgfkService {

	private CgfkDAO cgfkDao;
	private AccountsDAO accountsDao;
	private AccountDzdDAO accountDzdDao;
	private YufukDAO yufukDao;

	/**
	 * 取采购应付款列表
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getCgfks(String con,int curPage, int rowsPerPage){
		return cgfkDao.getCgfks(con, curPage, rowsPerPage);
	}
	
	
	/**
	 * 根据采购付款ID取相关信息
	 * @param yfk_id
	 * @return
	 */
	public Object getCgfk(String id){
		return cgfkDao.getCgfk(id);
	}
	
	
	/**
	 * 根据采购付款ID取明细信息
	 * @param id
	 * @return
	 */
	public List getCgfkDesc(String id){
		return cgfkDao.getCgfkDesc(id);
	}

	
	/**
	 * 根据供应商编号取所有应付款情况
	 * @param gysbh
	 * @return
	 */
	public List getDfkDesc(String gysbh){
		return cgfkDao.getDfkDesc(gysbh);
	}
	
	/**
	 * 删除采购付款信息根据采购付款ID
	 * @param jhd_id
	 * @return
	 */
	public void delCgfk(String id){
		cgfkDao.delCgfk(id);
	}
	
	
	/**
	 * 保存采购付款信息，包括明细信息
	 * @param cgfk
	 * @param cgfkDescs
	 */
	public void saveCgfk(Cgfk cgfk,List cgfkDescs){
		cgfkDao.saveCgfk(cgfk, cgfkDescs);
		
		if(cgfk.getState().equals("已提交")){
			cgfkDao.updateJhdFkje(cgfk,cgfkDescs);
			
			//添加预付款信息
			if(cgfk.getIs_yfk() != null && cgfk.getIs_yfk().equals("是")){
				this.addYufuk(cgfk);
			}
			
			this.saveAccountDzd(cgfk); //资金往来记录
			
			accountsDao.updateAccountJe(cgfk.getFkzh(), cgfk.getFkje()); //更新账户金额
		}
	}
	
	
	/**
	 * 更新采购付款相关信息
	 * @param cgfk
	 * @param cgfkDescs
	 */
	public void updateCgfk(Cgfk cgfk,List cgfkDescs){
		cgfkDao.updateCgfk(cgfk, cgfkDescs);
		
		if(cgfk.getState().equals("已提交")){
			cgfkDao.updateJhdFkje(cgfk,cgfkDescs);
			
			//添加预付款信息
			if(cgfk.getIs_yfk() != null && cgfk.getIs_yfk().equals("是")){
				this.addYufuk(cgfk);
			}
			
			this.saveAccountDzd(cgfk); //资金往来记录
			
			accountsDao.updateAccountJe(cgfk.getFkzh(), cgfk.getFkje()); //更新账户金额
		}
	}
	
	
	/**
	 * 添加资金交易记录
	 * @param cgfk
	 */
	private void saveAccountDzd(Cgfk cgfk){
		AccountDzd accountDzd = new AccountDzd();
		
		double zhye = 0;
		Map map = accountsDao.getAccounts(cgfk.getFkzh());
		if(map != null){
			zhye = (map.get("dqje")==null?0:((Double)map.get("dqje")).doubleValue());
		}
		
		accountDzd.setAccount_id(cgfk.getFkzh());
		accountDzd.setJyje(0-cgfk.getFkje());
		accountDzd.setZhye(zhye - cgfk.getFkje());
		accountDzd.setRemark("采购付款，编号[" + cgfk.getId() + "]");
		accountDzd.setCzr(cgfk.getCzr());
		accountDzd.setJsr(cgfk.getJsr());
		accountDzd.setAction_url("viewCgfk.html?id=" + cgfk.getId());
		
		accountDzdDao.addDzd(accountDzd);
	}
	

	/**
	 * 取当前可用的序列号
	 * @return
	 */
	public String updateCgfkID() {
		return cgfkDao.getCgfkID();
	}
	
	
	/**
	 * 添加预付款信息
	 * @param cgfk
	 */
	private void addYufuk(Cgfk cgfk){
		Yufuk yfk = new Yufuk();
		
		yfk.setClient_name(cgfk.getGysbh());
		yfk.setHjje(cgfk.getFkje());
		yfk.setJsje(0);
		yfk.setJs_date(cgfk.getFk_date());
		yfk.setYwdj_id(cgfk.getId());
		yfk.setYwdj_name("采购付款（预付款）");
		yfk.setUrl("viewCgfk.html?id=");
		yfk.setCzr(cgfk.getCzr());
		yfk.setRemark("预付款");
		
		yufukDao.saveYufuk(yfk);
	}


	public CgfkDAO getCgfkDao() {
		return cgfkDao;
	}


	public void setCgfkDao(CgfkDAO cgfkDao) {
		this.cgfkDao = cgfkDao;
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


	public YufukDAO getYufukDao() {
		return yufukDao;
	}


	public void setYufukDao(YufukDAO yufukDao) {
		this.yufukDao = yufukDao;
	}
	
}
