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
 * ����Ԥ�տ��
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
	 * ȡ����Ԥ�տ��б�
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getLsyskList(String con,int curPage, int rowsPerPage){
		return lsyskDao.getLsyskList(con, curPage, rowsPerPage);
	}
	
	
	/**
	 * ��������Ԥ�տ���Ϣ
	 * @param lsysk
	 */
	public void saveLsysk(Lsysk lsysk){		
		lsyskDao.saveLsysk(lsysk);
		
		if(lsysk.getState().equals("���ύ")){
			this.saveXssk(lsysk);
			this.saveAccountDzd(lsysk.getSkzh(), lsysk.getYsje(), lsysk.getCzr(), lsysk.getJsr(), "����Ԥ�տ���[" + lsysk.getId() + "]",lsysk.getId());
			accountsDao.addAccountJe(lsysk.getSkzh(), lsysk.getYsje());
			
			//������ʽΪˢ��������POS����Ų�Ϊ�գ��Զ����������Ϣ
			if(lsysk.getFkfs().equals("ˢ��") && !lsysk.getPos_id().equals("")){
				this.saveQtzc(lsysk);
			}
		}
	}
	
	
	/**
	 * ��������Ԥ�տ���Ϣ
	 * @param lsysk
	 */
	public void updateLsysk(Lsysk lsysk){	
		
		//�ж�����Ԥ�տ��Ƿ��Ѿ��ύ������Ѿ��ύ�����κβ���
		if(lsyskDao.isLsyskSubmit(lsysk.getId())){
			return;
		}
		
		lsyskDao.updateLsysk(lsysk);
		
		if(lsysk.getState().equals("���ύ")){
			this.saveXssk(lsysk);
			this.saveAccountDzd(lsysk.getSkzh(), lsysk.getYsje(), lsysk.getCzr(), lsysk.getJsr(), "����Ԥ�տ���[" + lsysk.getId() + "]",lsysk.getId());
			accountsDao.addAccountJe(lsysk.getSkzh(), lsysk.getYsje());
			
			//������ʽΪˢ��������POS����Ų�Ϊ�գ��Զ����������Ϣ
			if(lsysk.getFkfs().equals("ˢ��") && !lsysk.getPos_id().equals("")){
				this.saveQtzc(lsysk);
			}
		}
	}
	
	
	/**
	 * ����Ԥ�տ��˿�
	 * @param id
	 */
	public void updateLsyskTh(Lsysk lsysk){
		if(lsysk!= null){
			
			//�������Ԥ�տ��ѳ�֣������κβ�������
			if(lsysk.getType().equals("�ѳ��")) return;
			
			//�޸ĵ�ǰ����Ԥ�տ�ĳ��״̬
			lsysk.setType("�ѳ��");
			lsyskDao.updateLsysk(lsysk);
			
			//����µĳ�ֵ�����Ԥ�տ״̬Ϊ���
			lsysk.setFkfs("�ֽ�");
			lsysk.setPos_id("");
			lsysk.setYsje(0-lsysk.getYsje());
			lsysk.setYs_date(DateComFunc.getToday());
			lsysk.setState("���ύ");	
			lsysk.setType("�ѳ��");
			lsysk.setRemark("����Ԥ���˿��Զ�������ɣ���Ӧ����Ԥ����Ϊ��" + lsysk.getId());
			lsysk.setId(lsyskDao.getLsyskId());
			this.saveLsysk(lsysk);
		}
		
	}
	
	
	/**
	 * ����ʽ��׼�¼
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
	 * ����IDȡ����Ԥ�տ���Ϣ
	 * @param id
	 * @return
	 */
	public Object getLsysk(String id){
		return lsyskDao.getLsysk(id);
	}
	
	
	/**
	 * ɾ������Ԥ�տ���Ϣ
	 * @param id
	 */
	public void delLsysk(String id){
		
		//�ж�����Ԥ�տ��Ƿ��Ѿ��ύ������Ѿ��ύ�����κβ���
		if(lsyskDao.isLsyskSubmit(id)){
			return;
		}
		
		lsyskDao.delLsysk(id);
	}
	
	
	/**
	 * ȡ��ǰ���õ�����Ԥ�տ�ID
	 * @return
	 */
	public String updateLsyskId(){
		return lsyskDao.getLsyskId();
	}
	
	
	/**
	 * ��������Ԥ�տ����������տ��¼
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
		xssk.setState("���ύ");
		xssk.setIs_ysk("��");
		xssk.setCzr(lsysk.getCzr());
		xssk.setRemark("����Ԥ�տ��� [" + lsysk.getId() + "]");
		
		List xsskDescs = new ArrayList();
		
		XsskDesc xsskDesc = new XsskDesc();
		xsskDesc.setXsd_id(lsysk.getId());
		xsskDesc.setXssk_id(xssk_id);
		xsskDesc.setBcsk(lsysk.getYsje());
		xsskDesc.setRemark("����Ԥ�տ��� [" + lsysk.getId() + "]");
		
		xsskDescs.add(xsskDesc);
		
		xsskDao.saveXssk(xssk, xsskDescs);
	}
	
	
	
	/**
	 * ��������Ԥ�տ���Ϣ��������֧��
	 * @param lsd
	 */
	private void saveQtzc(Lsysk lsysk){
		Qtzc qtzc = new Qtzc();
		
		String id = qtzcDao.getQtzcID();
		
		qtzc.setId(id);
		qtzc.setZc_date(lsysk.getYs_date());
		qtzc.setType("�������-ˢ��������");
		qtzc.setZcje(posTypeDao.getBrushCardfy(lsysk.getPos_id(), lsysk.getYsje()));
		qtzc.setZczh(lsysk.getSkzh());
		qtzc.setJsr(lsysk.getJsr());
		qtzc.setRemark("ˢ�������ѣ�������Ԥ�տ[" + lsysk.getId() + "]�Զ�����");
		qtzc.setCzr(lsysk.getCzr());
		qtzc.setState("���ύ");
		qtzc.setYwy(lsysk.getJsr());
		qtzc.setZcxm("ˢ��������");
		qtzc.setFklx("ˢ��");
		qtzc.setFysq_id("��");
		
		qtzcDao.saveQtzc(qtzc);  //��������֧����һ����ã�
		
		accountsDao.updateAccountJe(lsysk.getSkzh(),posTypeDao.getBrushCardfy(lsysk.getPos_id(), lsysk.getYsje())); //�޸��˻����
		
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
		accountDzd.setRemark("һ����ã����[" + id + "]");
		accountDzd.setCzr(lsysk.getCzr());
		accountDzd.setJsr(lsysk.getJsr());
		accountDzd.setAction_url("viewQtzc.html?id=" + id);		
		accountDzdDao.addDzd(accountDzd);   //����ʽ��׼�¼
		
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
