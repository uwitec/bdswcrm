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
	 * ȡ�ɹ�Ӧ�����б�
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getCgfks(String con,int curPage, int rowsPerPage){
		return cgfkDao.getCgfks(con, curPage, rowsPerPage);
	}
	
	
	/**
	 * ���ݲɹ�����IDȡ�����Ϣ
	 * @param yfk_id
	 * @return
	 */
	public Object getCgfk(String id){
		return cgfkDao.getCgfk(id);
	}
	
	
	/**
	 * ���ݲɹ�����IDȡ��ϸ��Ϣ
	 * @param id
	 * @return
	 */
	public List getCgfkDesc(String id){
		return cgfkDao.getCgfkDesc(id);
	}

	
	/**
	 * ���ݹ�Ӧ�̱��ȡ����Ӧ�������
	 * @param gysbh
	 * @return
	 */
	public List getDfkDesc(String gysbh){
		return cgfkDao.getDfkDesc(gysbh);
	}
	
	/**
	 * ɾ���ɹ�������Ϣ���ݲɹ�����ID
	 * @param jhd_id
	 * @return
	 */
	public void delCgfk(String id){
		cgfkDao.delCgfk(id);
	}
	
	
	/**
	 * ����ɹ�������Ϣ��������ϸ��Ϣ
	 * @param cgfk
	 * @param cgfkDescs
	 */
	public void saveCgfk(Cgfk cgfk,List cgfkDescs){
		cgfkDao.saveCgfk(cgfk, cgfkDescs);
		
		if(cgfk.getState().equals("���ύ")){
			cgfkDao.updateJhdFkje(cgfk,cgfkDescs);
			
			//���Ԥ������Ϣ
			if(cgfk.getIs_yfk() != null && cgfk.getIs_yfk().equals("��")){
				this.addYufuk(cgfk);
			}
			
			this.saveAccountDzd(cgfk); //�ʽ�������¼
			
			accountsDao.updateAccountJe(cgfk.getFkzh(), cgfk.getFkje()); //�����˻����
		}
	}
	
	
	/**
	 * ���²ɹ����������Ϣ
	 * @param cgfk
	 * @param cgfkDescs
	 */
	public void updateCgfk(Cgfk cgfk,List cgfkDescs){
		cgfkDao.updateCgfk(cgfk, cgfkDescs);
		
		if(cgfk.getState().equals("���ύ")){
			cgfkDao.updateJhdFkje(cgfk,cgfkDescs);
			
			//���Ԥ������Ϣ
			if(cgfk.getIs_yfk() != null && cgfk.getIs_yfk().equals("��")){
				this.addYufuk(cgfk);
			}
			
			this.saveAccountDzd(cgfk); //�ʽ�������¼
			
			accountsDao.updateAccountJe(cgfk.getFkzh(), cgfk.getFkje()); //�����˻����
		}
	}
	
	
	/**
	 * ����ʽ��׼�¼
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
		accountDzd.setRemark("�ɹ�������[" + cgfk.getId() + "]");
		accountDzd.setCzr(cgfk.getCzr());
		accountDzd.setJsr(cgfk.getJsr());
		accountDzd.setAction_url("viewCgfk.html?id=" + cgfk.getId());
		
		accountDzdDao.addDzd(accountDzd);
	}
	

	/**
	 * ȡ��ǰ���õ����к�
	 * @return
	 */
	public String updateCgfkID() {
		return cgfkDao.getCgfkID();
	}
	
	
	/**
	 * ���Ԥ������Ϣ
	 * @param cgfk
	 */
	private void addYufuk(Cgfk cgfk){
		Yufuk yfk = new Yufuk();
		
		yfk.setClient_name(cgfk.getGysbh());
		yfk.setHjje(cgfk.getFkje());
		yfk.setJsje(0);
		yfk.setJs_date(cgfk.getFk_date());
		yfk.setYwdj_id(cgfk.getId());
		yfk.setYwdj_name("�ɹ����Ԥ���");
		yfk.setUrl("viewCgfk.html?id=");
		yfk.setCzr(cgfk.getCzr());
		yfk.setRemark("Ԥ����");
		
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
