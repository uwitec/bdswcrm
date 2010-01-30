package com.sw.cms.service;

import java.util.List;
import java.util.Map;

import com.sw.cms.dao.AccountDzdDAO;
import com.sw.cms.dao.AccountsDAO;
import com.sw.cms.dao.CgfkDAO;
import com.sw.cms.dao.CnfkdDAO;
import com.sw.cms.dao.YufukDAO;
import com.sw.cms.model.AccountDzd;
import com.sw.cms.model.Cgfk;
import com.sw.cms.model.Cnfkd;
import com.sw.cms.model.Page;
import com.sw.cms.model.Yufuk;

/**
 * 出纳付款单
 * @author jinyanni
 *
 */
public class CnfkdService {
	
	private CnfkdDAO cnfkdDao;
	private AccountsDAO accountsDao;
	private AccountDzdDAO accountDzdDao;
	private YufukDAO yufukDao;	
	private CgfkDAO cgfkDao;

	
	/**
	 * 根据查询条件查询出纳付款单
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getCnfkds(String con,int curPage, int rowsPerPage){
		return cnfkdDao.getCnfkds(con, curPage, rowsPerPage);
	}
	
	
	/**
	 * 更新出纳付款单
	 * @param cnfkd
	 */
	public void updateCnfkd(Cnfkd cnfkd){
		
		Cnfkd tempCnfkd = cnfkdDao.getCnfkd(cnfkd.getId());
		
		if(!tempCnfkd.getState().equals("已支付") && cnfkd.getState().equals("已支付")){
			
			//如果采购付款不需要审批，更新采购订单相应已付金额	
			Cgfk cgfk = (Cgfk)cgfkDao.getCgfk(cnfkd.getCgfk_id());
			List cgfkDescs = cgfkDao.getCgfkDescObj(cnfkd.getCgfk_id());
			cgfkDao.updateJhdFkje(cgfk,cgfkDescs);
			
			//更新采购付款申请单状态
			cgfk.setState("已支付");
			cgfkDao.updateCgfkStat(cgfk);
			
			//如果是预付款则添加预付款信息
			if(cnfkd.getFklx().equals("预付款")){
				this.addYufuk(cnfkd);
			}
			
			//保存资金往来记录
			this.saveAccountDzd(cnfkd);
			
			//更新账户金额
			accountsDao.updateAccountJe(cnfkd.getFkzh(), cnfkd.getFkje());
		}
		
		cnfkdDao.updateCnfkd(cnfkd);
		
	}
	
	
	/**
	 * 添加资金交易记录
	 * @param cgfk
	 */
	private void saveAccountDzd(Cnfkd cnfkd){
		AccountDzd accountDzd = new AccountDzd();
		
		double zhye = 0;
		Map map = accountsDao.getAccounts(cnfkd.getFkzh());
		if(map != null){
			zhye = (map.get("dqje")==null?0:((Double)map.get("dqje")).doubleValue());
		}
		
		accountDzd.setAccount_id(cnfkd.getFkzh());
		accountDzd.setJyje(0-cnfkd.getFkje());
		accountDzd.setZhye(zhye - cnfkd.getFkje());
		accountDzd.setRemark("对应出纳付款单，编号[" + cnfkd.getId() + "]");
		accountDzd.setCzr(cnfkd.getCzr());
		accountDzd.setJsr(cnfkd.getJsr());
		accountDzd.setAction_url("viewCnfkd.html?id=" + cnfkd.getId());
		
		accountDzdDao.addDzd(accountDzd);
	}
	
	
	/**
	 * 添加预付款信息
	 * @param cgfk
	 */
	private void addYufuk(Cnfkd cnfkd){
		Yufuk yfk = new Yufuk();
		yfk.setClient_name(cnfkd.getClient_name());
		yfk.setHjje(cnfkd.getFkje());
		yfk.setJsje(0);
		yfk.setJs_date(cnfkd.getFk_date());
		yfk.setYwdj_id(cnfkd.getId());
		yfk.setYwdj_name("采购付款（预付款）");
		yfk.setUrl("viewCgfk.html?id=");
		yfk.setCzr(cnfkd.getCzr());
		yfk.setRemark("预付款");
		
		yufukDao.saveYufuk(yfk);
	}
	
	
	public Cnfkd getCnfkd(String id){
		return cnfkdDao.getCnfkd(id);
	}


	public CnfkdDAO getCnfkdDao() {
		return cnfkdDao;
	}


	public void setCnfkdDao(CnfkdDAO cnfkdDao) {
		this.cnfkdDao = cnfkdDao;
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


	public CgfkDAO getCgfkDao() {
		return cgfkDao;
	}


	public void setCgfkDao(CgfkDAO cgfkDao) {
		this.cgfkDao = cgfkDao;
	}
	
}
