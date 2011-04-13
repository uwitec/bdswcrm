package com.sw.cms.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sw.cms.dao.AccountDzdDAO;
import com.sw.cms.dao.AccountsDAO;
import com.sw.cms.dao.PosTypeDAO;
import com.sw.cms.dao.QtzcDAO;
import com.sw.cms.dao.UserDAO;
import com.sw.cms.dao.XsskDAO;
import com.sw.cms.dao.YushoukDAO;
import com.sw.cms.model.AccountDzd;
import com.sw.cms.model.Page;
import com.sw.cms.model.Qtzc;
import com.sw.cms.model.SysUser;
import com.sw.cms.model.Xssk;
import com.sw.cms.model.XsskDesc;
import com.sw.cms.model.Yushouk;
import com.sw.cms.util.StringUtils;

public class XsskService {
	
	private XsskDAO xsskDao;
	private AccountsDAO accountsDao;
	private AccountDzdDAO accountDzdDao;
	private YushoukDAO yushoukDao;
	private UserDAO userDao;
	private PosTypeDAO posTypeDao;
	private QtzcDAO qtzcDao;
	
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
			
			//如果收款方式为刷卡，并且POS机编号不为空，自动保存费用信息
			if(xssk.getSkfs().equals("刷卡") && !xssk.getPos_id().equals("")){
				this.saveQtzc(xssk);
			}
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
			
			//如果收款方式为刷卡，并且POS机编号不为空，自动保存费用信息
			if(xssk.getSkfs().equals("刷卡") && !xssk.getPos_id().equals("")){
				this.saveQtzc(xssk);
			}
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
	
	
	/**
	 * 判断提交的销售收款明细中是否存在与其他销售收款单冲突，如果存在返回编号，不存在返回空
	 * @param xssk
	 * @param xsskDescs
	 * @return
	 */
	public String getExistXsskDesc(Xssk xssk,List xsskDescs){
		String temp = "";
		
		String client_name = xssk.getClient_name();
		String xssk_id = xssk.getId();
		
		if(xsskDescs != null && xsskDescs.size()>0){
			for(int i =0;i<xsskDescs.size();i++){
				XsskDesc xssKdesc = (XsskDesc)xsskDescs.get(i);
				if(xssKdesc != null && xssKdesc.getBcsk() != 0){
					String xsd_id = xssKdesc.getXsd_id();
					double bcsk=xssKdesc.getBcsk();
					if(xsskDao.isXsskDescExist(xssk_id, xsd_id, client_name,bcsk)){
						//如果存在冲突，则记录相应进货单编号
						if(temp.equals("")){
							temp = xsd_id;
						}else{
							temp += "," + xsd_id;
						}
					}
					
					double ysk=xssKdesc.getYsk();
					
				}
			}
		}
		
		return temp;
	}
	
	
	public List xsskDescObjToMap(List xsskDescs){
		List list = new ArrayList();
		if(xsskDescs != null && xsskDescs.size()>0){
			for(int i =0;i<xsskDescs.size();i++){
				XsskDesc xsskDesc = (XsskDesc)xsskDescs.get(i);
				if(xsskDesc != null){
					
					String xsd_id = xsskDesc.getXsd_id();
					String fsrq = xsskDesc.getFsrq();
					double fsje = xsskDesc.getFsje();
					double ysk = xsskDesc.getYsk();
					double bcsk = xsskDesc.getBcsk();
					
					Map map = new HashMap();
					map.put("xsd_id", xsd_id);
					map.put("fsrq", fsrq);
					map.put("fsje", fsje);
					map.put("ysk", ysk);
					map.put("bcsk", bcsk);
					
					list.add(map);
				}
			}
		}
		return list;
	}
	
	
	private void saveQtzc(Xssk xssk){
		Qtzc qtzc = new Qtzc();
		
		String id = qtzcDao.getQtzcID();
		
		String dept = "";
		if(!StringUtils.nullToStr(xssk.getJsr()).equals("")){
			dept = ((SysUser)userDao.getUser(xssk.getJsr())).getDept();
		}
		
		qtzc.setId(id);
		qtzc.setZc_date(xssk.getSk_date());
		qtzc.setType("02");
		qtzc.setZcje(posTypeDao.getBrushCardfy(xssk.getPos_id(), xssk.getSkje()));
		qtzc.setZczh(xssk.getSkzh());
		qtzc.setJsr(xssk.getJsr());
		qtzc.setRemark("刷卡手续费，由销售收款[" + xssk.getId() + "]自动生成");
		qtzc.setCzr(xssk.getCzr());
		qtzc.setState("已提交");
		qtzc.setYwy(xssk.getJsr());
		qtzc.setSqr(xssk.getJsr());
		qtzc.setYwy_dept(dept);
		qtzc.setZcxm("刷卡手续费");
		qtzc.setFklx("刷卡");
		qtzc.setFysq_id("无");
		
		qtzcDao.saveQtzc(qtzc);  //保存其它支出（一般费用）
		
		accountsDao.updateAccountJe(xssk.getSkzh(),posTypeDao.getBrushCardfy(xssk.getPos_id(), xssk.getSkje())); //修改账户金额
		
		double jyje = 0 - posTypeDao.getBrushCardfy(xssk.getPos_id(), xssk.getSkje());
		AccountDzd accountDzd = new AccountDzd();
		accountDzd.setAccount_id(xssk.getSkzh());
		accountDzd.setJyje(jyje);
		double zhye = 0;
		Map map = accountsDao.getAccounts(xssk.getSkzh());
		if(map != null){
			zhye = (map.get("dqje")==null?0:((Double)map.get("dqje")).doubleValue());
		}		
		accountDzd.setZhye(zhye + jyje);
		accountDzd.setRemark("一般费用，编号[" + id + "]");
		accountDzd.setCzr(xssk.getCzr());
		accountDzd.setJsr(xssk.getJsr());
		accountDzd.setAction_url("viewQtzc.html?id=" + id);		
		accountDzdDao.addDzd(accountDzd);   //添加资金交易记录
		
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


	public UserDAO getUserDao() {
		return userDao;
	}


	public void setUserDao(UserDAO userDao) {
		this.userDao = userDao;
	}


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

}
