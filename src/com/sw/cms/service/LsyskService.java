package com.sw.cms.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sw.cms.dao.AccountDzdDAO;
import com.sw.cms.dao.AccountsDAO;
import com.sw.cms.dao.LsyskDAO;
import com.sw.cms.dao.PosTypeDAO;
import com.sw.cms.dao.QtzcDAO;
import com.sw.cms.dao.XsskDAO;
import com.sw.cms.model.AccountDzd;
import com.sw.cms.model.Lsysk;
import com.sw.cms.model.Page;
import com.sw.cms.model.Qtzc;
import com.sw.cms.model.Xssk;
import com.sw.cms.model.XsskDesc;
import com.sw.cms.util.DateComFunc;

/**
 * 零售预收款处理
 * @author liyt
 *
 */
public class LsyskService {
	
	private LsyskDAO lsyskDao;
	private AccountsDAO accountsDao;
	private AccountDzdDAO accountDzdDao;
	private XsskDAO xsskDao;
	private PosTypeDAO posTypeDao;
	private QtzcDAO qtzcDao;
	
	public PosTypeDAO getPosTypeDao() {
		return posTypeDao;
	}


	public void setPosTypeDao(PosTypeDAO posTypeDao) {
		this.posTypeDao = posTypeDao;
	}


	public QtzcDAO getQtzcDao() {
		return qtzcDao;
	}


	public void setQtzcDao(QtzcDAO qtzcDao) {
		this.qtzcDao = qtzcDao;
	}


	/**
	 * 取零售预收款列表
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getLsyskList(String con,int curPage, int rowsPerPage){
		return lsyskDao.getLsyskList(con, curPage, rowsPerPage);
	}
	
	
	/**
	 * 保存零售预收款信息
	 * @param lsysk
	 */
	public void saveLsysk(Lsysk lsysk){		
		lsyskDao.saveLsysk(lsysk);
		
		if(lsysk.getState().equals("已提交")){
			this.saveXssk(lsysk);
			this.saveAccountDzd(lsysk.getSkzh(), lsysk.getYsje(), lsysk.getCzr(), lsysk.getJsr(), "零售预收款，编号[" + lsysk.getId() + "]",lsysk.getId());
			accountsDao.addAccountJe(lsysk.getSkzh(), lsysk.getYsje());
			
			//如果付款方式为刷卡，并且POS机编号不为空，自动保存费用信息
			if(lsysk.getFkfs().equals("刷卡") && !lsysk.getPos_id().equals("")){
				this.saveQtzc(lsysk);
			}
		}
	}
	
	
	/**
	 * 更新零售预收款信息
	 * @param lsysk
	 */
	public void updateLsysk(Lsysk lsysk){	
		
		//判断零售预收款是否已经提交，如果已经提交不做任何操作
		if(lsyskDao.isLsyskSubmit(lsysk.getId())){
			return;
		}
		
		lsyskDao.updateLsysk(lsysk);
		
		if(lsysk.getState().equals("已提交")){
			this.saveXssk(lsysk);
			this.saveAccountDzd(lsysk.getSkzh(), lsysk.getYsje(), lsysk.getCzr(), lsysk.getJsr(), "零售预收款，编号[" + lsysk.getId() + "]",lsysk.getId());
			accountsDao.addAccountJe(lsysk.getSkzh(), lsysk.getYsje());
			
			//如果付款方式为刷卡，并且POS机编号不为空，自动保存费用信息
			if(lsysk.getFkfs().equals("刷卡") && !lsysk.getPos_id().equals("")){
				this.saveQtzc(lsysk);
			}
		}
	}
	
	
	/**
	 * 零售预收款退款
	 * @param id
	 */
	public void updateLsyskTh(Lsysk lsysk){
		if(lsysk!= null){
			
			//如果零售预收款已冲抵，不做任何操作返回
			if(lsysk.getType().equals("已冲抵")) return;
			
			//修改当前零售预收款的冲抵状态
			lsysk.setType("已冲抵");
			lsyskDao.updateLsysk(lsysk);
			
			//添加新的冲抵的零售预收款，状态为冲抵
			lsysk.setFkfs("现金");
			lsysk.setPos_id("");
			lsysk.setYsje(0-lsysk.getYsje());
			lsysk.setYs_date(DateComFunc.getToday());
			lsysk.setState("已提交");	
			lsysk.setType("已冲抵");
			lsysk.setRemark("零售预收退款自动冲抵生成，对应零收预款编号为：" + lsysk.getId());
			lsysk.setId(lsyskDao.getLsyskId());
			this.saveLsysk(lsysk);
		}
		
	}
	
	
	/**
	 * 添加资金交易记录
	 * @param cgfk
	 */
	private void saveAccountDzd(String account_id,double jyje,String czr,String jsr,String remark,String lsysk_id){
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
		accountDzd.setAction_url("viewLsysk.html?id=" + lsysk_id);
		
		accountDzdDao.addDzd(accountDzd);
	}
	
	
	/**
	 * 根据ID取零售预收款信息
	 * @param id
	 * @return
	 */
	public Object getLsysk(String id){
		return lsyskDao.getLsysk(id);
	}
	
	
	/**
	 * 删除零售预收款信息
	 * @param id
	 */
	public void delLsysk(String id){
		
		//判断零售预收款是否已经提交，如果已经提交不做任何操作
		if(lsyskDao.isLsyskSubmit(id)){
			return;
		}
		
		lsyskDao.delLsysk(id);
	}
	
	
	/**
	 * 取当前可用的零售预收款ID
	 * @return
	 */
	public String updateLsyskId(){
		return lsyskDao.getLsyskId();
	}
	
	
	/**
	 * 根据零售预收款生成销售收款记录
	 * @param lsysk
	 */
	private void saveXssk(Lsysk lsysk){
		Xssk xssk = new Xssk();
		
		String xssk_id = xsskDao.getXsskID();
		
		xssk.setId(xssk_id);
		xssk.setSk_date(lsysk.getYs_date());
		xssk.setClient_name(lsysk.getClient_name());
		xssk.setJsr(lsysk.getJsr());
		xssk.setSkzh(lsysk.getSkzh());
		xssk.setSkje(lsysk.getYsje());
		xssk.setState("已提交");
		xssk.setIs_ysk("是");
		xssk.setCzr(lsysk.getCzr());
		xssk.setRemark("零售预收款，编号 [" + lsysk.getId() + "]");
		
		List xsskDescs = new ArrayList();
		
		XsskDesc xsskDesc = new XsskDesc();
		xsskDesc.setXsd_id(lsysk.getId());
		xsskDesc.setXssk_id(xssk_id);
		xsskDesc.setBcsk(lsysk.getYsje());
		xsskDesc.setRemark("零售预收款编号 [" + lsysk.getId() + "]");
		
		xsskDescs.add(xsskDesc);
		
		xsskDao.saveXssk(xssk, xsskDescs);
	}
	
	
	
	/**
	 * 根据零售预收款信息生成其它支出
	 * @param lsd
	 */
	private void saveQtzc(Lsysk lsysk){
		Qtzc qtzc = new Qtzc();
		
		String id = qtzcDao.getQtzcID();
		
		qtzc.setId(id);
		qtzc.setZc_date(lsysk.getYs_date());
		qtzc.setType("财务费用-刷卡手续费");
		qtzc.setZcje(posTypeDao.getBrushCardfy(lsysk.getPos_id(), lsysk.getYsje()));
		qtzc.setZczh(lsysk.getSkzh());
		qtzc.setJsr(lsysk.getJsr());
		qtzc.setRemark("刷卡手续费，由零售预收款单[" + lsysk.getId() + "]自动生成");
		qtzc.setCzr(lsysk.getCzr());
		qtzc.setState("已提交");
		qtzc.setYwy(lsysk.getJsr());
		qtzc.setZcxm("刷卡手续费");
		qtzc.setFklx("刷卡");
		qtzc.setFysq_id("无");
		
		qtzcDao.saveQtzc(qtzc);  //保存其它支出（一般费用）
		
		accountsDao.updateAccountJe(lsysk.getSkzh(),posTypeDao.getBrushCardfy(lsysk.getPos_id(), lsysk.getYsje())); //修改账户金额
		
		double jyje = 0 - posTypeDao.getBrushCardfy(lsysk.getPos_id(), lsysk.getYsje());
		AccountDzd accountDzd = new AccountDzd();
		accountDzd.setAccount_id(lsysk.getSkzh());
		accountDzd.setJyje(jyje);		
		double zhye = 0;
		Map map = accountsDao.getAccounts(lsysk.getSkzh());
		if(map != null){
			zhye = (map.get("dqje")==null?0:((Double)map.get("dqje")).doubleValue());
		}		
		accountDzd.setZhye(zhye + jyje);
		accountDzd.setRemark("一般费用，编号[" + id + "]");
		accountDzd.setCzr(lsysk.getCzr());
		accountDzd.setJsr(lsysk.getJsr());
		accountDzd.setAction_url("viewQtzc.html?id=" + id);		
		accountDzdDao.addDzd(accountDzd);   //添加资金交易记录
		
	}
	

	public LsyskDAO getLsyskDao() {
		return lsyskDao;
	}

	public void setLsyskDao(LsyskDAO lsyskDao) {
		this.lsyskDao = lsyskDao;
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


	public XsskDAO getXsskDao() {
		return xsskDao;
	}


	public void setXsskDao(XsskDAO xsskDao) {
		this.xsskDao = xsskDao;
	}
	
	

}
