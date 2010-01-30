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
 * ���ɸ��
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
	 * ���ݲ�ѯ������ѯ���ɸ��
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getCnfkds(String con,int curPage, int rowsPerPage){
		return cnfkdDao.getCnfkds(con, curPage, rowsPerPage);
	}
	
	
	/**
	 * ���³��ɸ��
	 * @param cnfkd
	 */
	public void updateCnfkd(Cnfkd cnfkd){
		
		Cnfkd tempCnfkd = cnfkdDao.getCnfkd(cnfkd.getId());
		
		if(!tempCnfkd.getState().equals("��֧��") && cnfkd.getState().equals("��֧��")){
			
			//����ɹ������Ҫ���������²ɹ�������Ӧ�Ѹ����	
			Cgfk cgfk = (Cgfk)cgfkDao.getCgfk(cnfkd.getCgfk_id());
			List cgfkDescs = cgfkDao.getCgfkDescObj(cnfkd.getCgfk_id());
			cgfkDao.updateJhdFkje(cgfk,cgfkDescs);
			
			//���²ɹ��������뵥״̬
			cgfk.setState("��֧��");
			cgfkDao.updateCgfkStat(cgfk);
			
			//�����Ԥ���������Ԥ������Ϣ
			if(cnfkd.getFklx().equals("Ԥ����")){
				this.addYufuk(cnfkd);
			}
			
			//�����ʽ�������¼
			this.saveAccountDzd(cnfkd);
			
			//�����˻����
			accountsDao.updateAccountJe(cnfkd.getFkzh(), cnfkd.getFkje());
		}
		
		cnfkdDao.updateCnfkd(cnfkd);
		
	}
	
	
	/**
	 * ����ʽ��׼�¼
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
		accountDzd.setRemark("��Ӧ���ɸ�������[" + cnfkd.getId() + "]");
		accountDzd.setCzr(cnfkd.getCzr());
		accountDzd.setJsr(cnfkd.getJsr());
		accountDzd.setAction_url("viewCnfkd.html?id=" + cnfkd.getId());
		
		accountDzdDao.addDzd(accountDzd);
	}
	
	
	/**
	 * ���Ԥ������Ϣ
	 * @param cgfk
	 */
	private void addYufuk(Cnfkd cnfkd){
		Yufuk yfk = new Yufuk();
		yfk.setClient_name(cnfkd.getClient_name());
		yfk.setHjje(cnfkd.getFkje());
		yfk.setJsje(0);
		yfk.setJs_date(cnfkd.getFk_date());
		yfk.setYwdj_id(cnfkd.getId());
		yfk.setYwdj_name("�ɹ����Ԥ���");
		yfk.setUrl("viewCgfk.html?id=");
		yfk.setCzr(cnfkd.getCzr());
		yfk.setRemark("Ԥ����");
		
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
