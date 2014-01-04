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
	private QtzcDAO qtzcDao;
	private UserDAO userDao;

	
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
		
		//������ɸ��״̬Ϊ�˻ػ���֧�������ز����κβ���
		if(tempCnfkd == null || tempCnfkd.getState().equals("��֧��")){
			return;
		}
		
		cnfkdDao.updateCnfkd(cnfkd);
		
		if(cnfkd.getState().equals("��֧��")){
			
			//���³��ɸ����Ӧ�ĸ������뵥��Ӧ�Ľ��������
			Cgfk cgfk = (Cgfk)cgfkDao.getCgfk(cnfkd.getCgfk_id());
			List cgfkDescs = cgfkDao.getCgfkDescObj(cnfkd.getCgfk_id());
			cgfkDao.updateJhdFkje(cgfk,cgfkDescs);
			
			//���²ɹ��������뵥��Ϣ
			cgfk.setState("��֧��");
			cgfk.setFkzh(cnfkd.getFkzh());
			cgfkDao.updateCgfkStat(cgfk);
			
			//�����Ԥ���������Ԥ������Ϣ
			if(cnfkd.getFklx().equals("Ԥ����")){
				this.addYufuk(cnfkd);
			}
			
			//�����ʽ�������¼
			this.saveAccountDzd(cnfkd);
			
			//�����˻����
			accountsDao.updateAccountJe(cnfkd.getFkzh(), cnfkd.getFkje());
			
			//������ɸ����������
			if(cnfkd.getHas_fy().equals("��")){
				this.saveQtzc(cnfkd);
			}
		}
	}
	
	
	/**
	 * �˻س��ɸ��
	 * @param id
	 */
	public void delCnfkd(String id){
		Cnfkd tempCnfkd = cnfkdDao.getCnfkd(id);
		
		//������ɸ��״̬Ϊ�˻ػ���֧�������ز����κβ���
		if(tempCnfkd == null || tempCnfkd.getState().equals("��֧��")){
			return;
		}
		
		//ɾ�����ɸ��
		cnfkdDao.delCnfkd(id);
		
		//���¶�Ӧ�ɹ���������״̬
		Cgfk cgfk = (Cgfk)cgfkDao.getCgfk(tempCnfkd.getCgfk_id());
		if(cgfk != null){
			cgfk.setState("�����˻�");
			cgfkDao.updateCgfkStat(cgfk);
		}
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
	
	
	/**
	 * �������۵���Ϣ���ˢ��֧������
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
		qtzc.setRemark("һ����ã��ɳ��ɸ��[" + cnfkd.getId() + "]�Զ�����");
		qtzc.setCzr(cnfkd.getCzr());
		qtzc.setState("���ύ");
		qtzc.setYwy(cnfkd.getJsr());
		qtzc.setSqr(cnfkd.getJsr());
		qtzc.setYwy_dept(dept);
		qtzc.setZcxm("����������");
		qtzc.setFklx(cnfkd.getFkfs());
		qtzc.setFysq_id("��");
		
		qtzcDao.saveQtzc(qtzc);  //��������֧����һ����ã�
		
		accountsDao.updateAccountJe(cnfkd.getFy_account(),cnfkd.getFy_je()); //�޸��˻����
		
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
		accountDzd.setRemark("һ����ã����[" + id + "]");
		accountDzd.setCzr(cnfkd.getCzr());
		accountDzd.setJsr(cnfkd.getJsr());
		accountDzd.setAction_url("viewQtzc.html?id=" + id);		
		accountDzdDao.addDzd(accountDzd);   //����ʽ��׼�¼
		
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
