package com.sw.cms.service;

import java.util.List;
import java.util.Map;

import com.sw.cms.dao.CashBankDAO;
import com.sw.cms.dao.SysInitSetDAO;

public class CashBankService {
	
	private CashBankDAO cashBankDao;
	private SysInitSetDAO sysInitSetDao;
	
	/**
	 * �����˻�ID�����˻��б�
	 * ����˻�IDΪ���򷵻������˻��б�
	 * @param account_id
	 * @return
	 */
	public List getAccountList(String account_id,String type){
		return cashBankDao.getAccountList(account_id,type);
	}

	/**
	 * �����˻�ID���ڳ�����ȡ�˻��ڳ���Ϣ
	 * @param cdate
	 * @param account_id
	 * @return
	 */
	public Map getCashBankQc(String cdate,String account_id){
		
		String xtqyrq = sysInitSetDao.getSysInitSet().getQyrq(); //ϵͳ��������
		if(xtqyrq == null){
			xtqyrq = "";
		}
		
		//����û�ѡ������С��ϵͳ��������
		if(cdate.compareTo(xtqyrq)<0){
			cdate = xtqyrq;
		}
		Map map = cashBankDao.getCashBankQc(cdate, account_id);
		
		if(map != null){
			map.put("qc_date", cdate);
		}
		
		return map;
	}
	
	
	/**
	 * �����˻���ˮ��ϸ
	 * @param account_id
	 * @param start_date
	 * @param end_date
	 * @return
	 */
	public List getAccountSeqList(String account_id,String start_date,String end_date){
		return cashBankDao.getAccountSeqList(account_id, start_date, end_date);
	}

	public CashBankDAO getCashBankDao() {
		return cashBankDao;
	}

	public void setCashBankDao(CashBankDAO cashBankDao) {
		this.cashBankDao = cashBankDao;
	}

	public SysInitSetDAO getSysInitSetDao() {
		return sysInitSetDao;
	}

	public void setSysInitSetDao(SysInitSetDAO sysInitSetDao) {
		this.sysInitSetDao = sysInitSetDao;
	}

}
