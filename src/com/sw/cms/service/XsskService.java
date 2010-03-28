package com.sw.cms.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sw.cms.dao.AccountDzdDAO;
import com.sw.cms.dao.AccountsDAO;
import com.sw.cms.dao.XsskDAO;
import com.sw.cms.dao.YushoukDAO;
import com.sw.cms.model.AccountDzd;
import com.sw.cms.model.CgfkDesc;
import com.sw.cms.model.Page;
import com.sw.cms.model.Xssk;
import com.sw.cms.model.XsskDesc;
import com.sw.cms.model.Yushouk;

public class XsskService {
	
	private XsskDAO xsskDao;
	private AccountsDAO accountsDao;
	private AccountDzdDAO accountDzdDao;
	private YushoukDAO yushoukDao;
	
	/**
	 * ����Ӧ�տ��б�����ҳ��
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getXsskList(String con,int curPage, int rowsPerPage){
		return xsskDao.getXsskList(con, curPage, rowsPerPage);
	}
	
	
	/**
	 * ����Ӧ�տ���Ϣ
	 * �����˺����
	 * �������۵��տ����ͼ��տ���
	 * @param ysk
	 */
	public void saveXssk(Xssk xssk,List xsskDescs){
		
		//��������տ��Ѿ��ύ�������κβ�����ֱ�ӷ���
		if(xsskDao.isXsskSub(xssk.getId())){
			return;
		}
		xsskDao.saveXssk(xssk, xsskDescs);
		
		if(xssk.getState().equals("���ύ")){
			this.addAccountJe(xssk);  //�����˺����
			
			if(xssk.getIs_ysk() != null && xssk.getIs_ysk().equals("��")){
				this.addYushouk(xssk);
			}
			
			xsskDao.updateXsdFk(xssk,xsskDescs);  //�������۵�����տ���Ϣ
		}
		
	}
	
	
	/**
	 * ����Ӧ�տ���Ϣ
	 * @param ysk
	 */
	public void updateXssk(Xssk xssk,List xsskDescs){
		
		//��������տ��Ѿ��ύ�������κβ�����ֱ�ӷ���
		if(xsskDao.isXsskSub(xssk.getId())){
			return;
		}
		
		xsskDao.updateXssk(xssk, xsskDescs);
		
		if(xssk.getState().equals("���ύ")){
			this.addAccountJe(xssk);  //�����˺����
			
			if(xssk.getIs_ysk() != null && xssk.getIs_ysk().equals("��")){
				this.addYushouk(xssk);
			}
			
			xsskDao.updateXsdFk(xssk,xsskDescs);  //�������۵�����տ���Ϣ
		}
	}
	

	/**
	 * ȡӦ�տ���Ϣ
	 * @param id
	 * @return
	 */
	public Object getXssk(String id){
		return xsskDao.getXssk(id);
	}
	
	
	
	/**
	 * ɾ�������տ���Ϣ
	 * @param id
	 */
	public void delXssk(String id){
		
		//��������տ��Ѿ��ύ�������κβ�����ֱ�ӷ���
		if(xsskDao.isXsskSub(id)){
			return;
		}
		
		xsskDao.delXssk(id);
	}
	
	
	/**
	 * ȡ�����տ���ϸ��Ϣ
	 * @param id
	 * @return
	 */
	public List getXsskDescs(String id){
		return xsskDao.getXsskDescs(id);
	}
	
	/**
	 * ���ؿͻ���ǰ����Ӧ�տ��б�
	 * @param client_id
	 * @return
	 */
	public List getYskByClientId(String client_id){
		return xsskDao.getYskByClientId(client_id);
	}
	
	
	/**
	 * ȡ��ǰ�������۵����
	 * @return
	 */
	public String updateXsskId(){
		return xsskDao.getXsskID();
	}
	
	
	/**
	 * ���տ������˻���
	 * @param lsd
	 */
	private void addAccountJe(Xssk xssk){
		String account_id = xssk.getSkzh();
		double je = xssk.getSkje();
		
		this.saveAccountDzd(account_id, je, xssk.getCzr(), xssk.getJsr(), "�����տ���[" + xssk.getId() + "]",xssk.getId());
		accountsDao.addAccountJe(account_id, je);
	}
	
	/**
	 * ����ʽ��׼�¼
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
	 * �ڴ�����Ԥ�տ������һ����¼��
	 * ���ڼ�¼Ԥ�տ�������
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
		ysk.setYwdj_name("�����տ�");
		ysk.setRemark("Ԥ�տ�");
		ysk.setCzr(xssk.getCzr());
		
		yushoukDao.saveYushouk(ysk);
	}
	
	
	/**
	 * �ж��ύ�������տ���ϸ���Ƿ���������������տ��ͻ��������ڷ��ر�ţ������ڷ��ؿ�
	 * @param cgfk
	 * @param cgfkDescs
	 * @return
	 */
	public String getExistXsskDesc(Xssk xssk,List xsskDescs){
		String temp = "";
		
		String client_name = xssk.getClient_name();
		
		if(xsskDescs != null && xsskDescs.size()>0){
			for(int i =0;i<xsskDescs.size();i++){
				XsskDesc xssKdesc = (XsskDesc)xsskDescs.get(i);
				if(xssKdesc != null && xssKdesc.getBcsk() != 0){
					String xsd_id = xssKdesc.getXsd_id();
					
					if(xsskDao.isXsskDescExist(xsd_id, client_name)){
						//������ڳ�ͻ�����¼��Ӧ���������
						if(temp.equals("")){
							temp = xsd_id;
						}else{
							temp += "," + xsd_id;
						}
					}
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
