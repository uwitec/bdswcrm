package com.sw.cms.service;

import java.util.List;
import java.util.Map;

import com.sw.cms.dao.AccountDzdDAO;
import com.sw.cms.dao.AccountsDAO;
import com.sw.cms.dao.CgfkDAO;
import com.sw.cms.dao.CnfkdDAO;
import com.sw.cms.dao.QtzcDAO;
import com.sw.cms.dao.UserDAO;
import com.sw.cms.dao.YufukDAO;
import com.sw.cms.model.AccountDzd;
import com.sw.cms.model.Cgfk;
import com.sw.cms.model.Cnfkd;
import com.sw.cms.model.Page;
import com.sw.cms.model.Qtzc;
import com.sw.cms.model.SysUser;
import com.sw.cms.model.Yufuk;
import com.sw.cms.util.StringUtils;

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
	private QtzcDAO qtzcDao;
	private UserDAO userDao;

	
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
		
		//如果出纳付款单状态为退回或已支付，返回不做任何操作
		if(tempCnfkd == null || tempCnfkd.getState().equals("已支付")){
			return;
		}
		
		cnfkdDao.updateCnfkd(cnfkd);
		
		if(cnfkd.getState().equals("已支付")){
			
			//更新出纳付款单对应的付款申请单相应的进货单金额
			Cgfk cgfk = (Cgfk)cgfkDao.getCgfk(cnfkd.getCgfk_id());
			List cgfkDescs = cgfkDao.getCgfkDescObj(cnfkd.getCgfk_id());
			cgfkDao.updateJhdFkje(cgfk,cgfkDescs);
			
			//更新采购付款申请单信息
			cgfk.setState("已支付");
			cgfk.setFkzh(cnfkd.getFkzh());
			cgfkDao.updateCgfkStat(cgfk);
			
			//如果是预付款则添加预付款信息
			if(cnfkd.getFklx().equals("预付款")){
				this.addYufuk(cnfkd);
			}
			
			//保存资金往来记录
			this.saveAccountDzd(cnfkd);
			
			//更新账户金额
			accountsDao.updateAccountJe(cnfkd.getFkzh(), cnfkd.getFkje());
			
			//如果出纳付款单包括费用
			if(cnfkd.getHas_fy().equals("是")){
				this.saveQtzc(cnfkd);
			}
		}
	}
	
	
	/**
	 * 退回出纳付款单
	 * @param id
	 */
	public void delCnfkd(String id){
		Cnfkd tempCnfkd = cnfkdDao.getCnfkd(id);
		
		//如果出纳付款单状态为退回或已支付，返回不做任何操作
		if(tempCnfkd == null || tempCnfkd.getState().equals("已支付")){
			return;
		}
		
		//删除出纳付款单
		cnfkdDao.delCnfkd(id);
		
		//更新对应采购付款申请状态
		Cgfk cgfk = (Cgfk)cgfkDao.getCgfk(tempCnfkd.getCgfk_id());
		if(cgfk != null){
			cgfk.setState("出纳退回");
			cgfkDao.updateCgfkStat(cgfk);
		}
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
	
	
	/**
	 * 根据销售单信息添加刷卡支出费用
	 * @param xsd
	 */
	private void saveQtzc(Cnfkd cnfkd){
		Qtzc qtzc = new Qtzc();
		
		String id = qtzcDao.getQtzcID();
		
		String dept = "";
		if(!StringUtils.nullToStr(cnfkd.getJsr()).equals("")){
			dept = ((SysUser)userDao.getUser(cnfkd.getJsr())).getDept();
		}
		
		qtzc.setId(id);
		qtzc.setZc_date(cnfkd.getFk_date());
		qtzc.setType(cnfkd.getFy_type());
		qtzc.setZcje(cnfkd.getFy_je());
		qtzc.setZczh(cnfkd.getFy_account());
		qtzc.setJsr(cnfkd.getJsr());
		qtzc.setRemark("一般费用，由出纳付款单[" + cnfkd.getId() + "]自动生成");
		qtzc.setCzr(cnfkd.getCzr());
		qtzc.setState("已提交");
		qtzc.setYwy(cnfkd.getJsr());
		qtzc.setSqr(cnfkd.getJsr());
		qtzc.setYwy_dept(dept);
		qtzc.setZcxm("付款手续费");
		qtzc.setFklx(cnfkd.getFkfs());
		qtzc.setFysq_id("无");
		
		qtzcDao.saveQtzc(qtzc);  //保存其它支出（一般费用）
		
		accountsDao.updateAccountJe(cnfkd.getFy_account(),cnfkd.getFy_je()); //修改账户金额
		
		double jyje = 0 - cnfkd.getFy_je();
		AccountDzd accountDzd = new AccountDzd();
		accountDzd.setAccount_id(cnfkd.getFy_account());
		accountDzd.setJyje(jyje);		
		double zhye = 0;
		Map map = accountsDao.getAccounts(cnfkd.getFy_account());
		if(map != null){
			zhye = (map.get("dqje")==null?0:((Double)map.get("dqje")).doubleValue());
		}		
		accountDzd.setZhye(zhye + jyje);
		accountDzd.setRemark("一般费用，编号[" + id + "]");
		accountDzd.setCzr(cnfkd.getCzr());
		accountDzd.setJsr(cnfkd.getJsr());
		accountDzd.setAction_url("viewQtzc.html?id=" + id);		
		accountDzdDao.addDzd(accountDzd);   //添加资金交易记录
		
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


	public QtzcDAO getQtzcDao() {
		return qtzcDao;
	}


	public void setQtzcDao(QtzcDAO qtzcDao) {
		this.qtzcDao = qtzcDao;
	}


	public UserDAO getUserDao() {
		return userDao;
	}


	public void setUserDao(UserDAO userDao) {
		this.userDao = userDao;
	}
	
}
