package com.sw.cms.service;

import java.util.List;
import java.util.Map;

import com.sw.cms.dao.AccountDzdDAO;
import com.sw.cms.dao.AccountsDAO;
import com.sw.cms.dao.XsskDAO;
import com.sw.cms.dao.YushoukDAO;
import com.sw.cms.model.AccountDzd;
import com.sw.cms.model.Page;
import com.sw.cms.model.Xssk;
import com.sw.cms.model.Yushouk;

public class XsskService {
	
	private XsskDAO xsskDao;
	private AccountsDAO accountsDao;
	private AccountDzdDAO accountDzdDao;
	private YushoukDAO yushoukDao;
	
	/**
	 * 返回应收款列表（带分页）
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getXsskList(String con,int curPage, int rowsPerPage){
		return xsskDao.getXsskList(con, curPage, rowsPerPage);
	}
	
	
	/**
	 * 保存应收款信息
	 * 更新账号余款
	 * 更新销售单收款类型及收款金额
	 * @param ysk
	 */
	public void saveXssk(Xssk xssk,List xsskDescs){
		
		//如果销售收款已经提交，不做任何操作，直接返回
		if(xsskDao.isXsskSub(xssk.getId())){
			return;
		}
		xsskDao.saveXssk(xssk, xsskDescs);
		
		if(xssk.getState().equals("已提交")){
			this.addAccountJe(xssk);  //更新账号余额
			
			if(xssk.getIs_ysk() != null && xssk.getIs_ysk().equals("是")){
				this.addYushouk(xssk);
			}
			
			xsskDao.updateXsdFk(xssk,xsskDescs);  //更新销售单相关收款信息
		}
		
	}
	
	
	/**
	 * 更新应收款信息
	 * @param ysk
	 */
	public void updateXssk(Xssk xssk,List xsskDescs){
		
		//如果销售收款已经提交，不做任何操作，直接返回
		if(xsskDao.isXsskSub(xssk.getId())){
			return;
		}
		
		xsskDao.updateXssk(xssk, xsskDescs);
		
		if(xssk.getState().equals("已提交")){
			this.addAccountJe(xssk);  //更新账号余额
			
			if(xssk.getIs_ysk() != null && xssk.getIs_ysk().equals("是")){
				this.addYushouk(xssk);
			}
			
			xsskDao.updateXsdFk(xssk,xsskDescs);  //更新销售单相关收款信息
		}
	}
	

	/**
	 * 取应收款信息
	 * @param id
	 * @return
	 */
	public Object getXssk(String id){
		return xsskDao.getXssk(id);
	}
	
	
	
	/**
	 * 删除销售收款信息
	 * @param id
	 */
	public void delXssk(String id){
		
		//如果销售收款已经提交，不做任何操作，直接返回
		if(xsskDao.isXsskSub(id)){
			return;
		}
		
		xsskDao.delXssk(id);
	}
	
	
	/**
	 * 取销售收款明细信息
	 * @param id
	 * @return
	 */
	public List getXsskDescs(String id){
		return xsskDao.getXsskDescs(id);
	}
	
	/**
	 * 返回客户当前所有应收款列表
	 * @param client_id
	 * @return
	 */
	public List getYskByClientId(String client_id){
		return xsskDao.getYskByClientId(client_id);
	}
	
	
	/**
	 * 取当前可用销售单编号
	 * @return
	 */
	public String updateXsskId(){
		return xsskDao.getXsskID();
	}
	
	
	/**
	 * 将收款金额入账户中
	 * @param lsd
	 */
	private void addAccountJe(Xssk xssk){
		String account_id = xssk.getSkzh();
		double je = xssk.getSkje();
		
		this.saveAccountDzd(account_id, je, xssk.getCzr(), xssk.getJsr(), "销售收款，编号[" + xssk.getId() + "]",xssk.getId());
		accountsDao.addAccountJe(account_id, je);
	}
	
	/**
	 * 添加资金交易记录
	 * @param cgfk
	 */
	private void saveAccountDzd(String account_id,double jyje,String czr,String jsr,String remark,String xssk_id){
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
		accountDzd.setAction_url("viewXssk.html?id=" + xssk_id);
		
		accountDzdDao.addDzd(accountDzd);
	}
	
	
	/**
	 * 在待结算预收款中添加一条记录，
	 * 用于记录预收款结算情况
	 * @param xssk
	 */
	private void addYushouk(Xssk xssk){
		Yushouk ysk = new Yushouk();
		
		ysk.setClient_name(xssk.getClient_name());
		ysk.setHjje(xssk.getSkje());
		ysk.setJsje(0);
		ysk.setJs_date(xssk.getSk_date());
		ysk.setUrl("viewXssk?id=");
		ysk.setYwdj_id(xssk.getId());
		ysk.setYwdj_name("销售收款");
		ysk.setRemark("预收款");
		ysk.setCzr(xssk.getCzr());
		
		yushoukDao.saveYushouk(ysk);
	}


	public XsskDAO getXsskDao() {
		return xsskDao;
	}


	public void setXsskDao(XsskDAO xsskDao) {
		this.xsskDao = xsskDao;
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


	public YushoukDAO getYushoukDao() {
		return yushoukDao;
	}


	public void setYushoukDao(YushoukDAO yushoukDao) {
		this.yushoukDao = yushoukDao;
	}

}
