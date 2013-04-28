package com.sw.cms.service;

import java.util.List;
import java.util.Map;

import com.sw.cms.dao.KcMxReportDAO;
import com.sw.cms.dao.SysInitSetDAO;

public class KcMxReportService {

	private KcMxReportDAO kcMxReportDao;
	private SysInitSetDAO sysInitSetDao;
	
	/**
	 * ����ѯ����ȡ�����Ʒ�б�
	 * @param product_name ��Ʒ���
	 * @param product_name ��Ʒ����
	 * @param store_id  �ֿ���
	 * @return
	 */
	public List getProductKcList(String product_kind,String product_name,String store_id){
		return kcMxReportDao.getProductKcList(product_kind,product_name,store_id);
	}
	
	
	/**
	 * ����ѯ����ȡ�����Ʒ�б�
	 * @param product_kind ��Ʒ���
	 * @param product_name ��Ʒ����
	 * @param store_id    �ⷿ���
	 * @return
	 */
	public List getKcProductList(String product_kind,String product_name,String store_id){
		return kcMxReportDao.getKcProductList(product_kind,product_name,store_id);
	}
	
	
	/**
	 * <p>ȡ����ڳ������Ϣ</p>
	 * <p>��Ҫ���û�ѡ����ڳ����������ж�</p>
	 * <p>���ѡ����ڳ�����С��ϵͳ��ʼ�����������ڳ�����ȡϵͳ��������</p>
	 * @param product_id
	 * @param cdate
	 * @param store_id
	 * @return map
	 */
	public Map getKcqcMxMap(String product_id,String cdate,String store_id){
		String xtqyrq = sysInitSetDao.getSysInitSet().getQyrq(); //ϵͳ��������
		if(xtqyrq == null){
			xtqyrq = "";
		}
		
		//����û�ѡ������С��ϵͳ��������
		if(cdate.compareTo(xtqyrq)<0){
			cdate = xtqyrq;
		}
		Map map = kcMxReportDao.getKcqcMxMap(product_id, cdate, store_id);
		if(map != null){
			map.put("qc_date", cdate);
		}
		return map;
	}
	
	
	/**
	 * ����ȡ����ڳ���Ϣ
	 * @param product_id
	 * @param cdate
	 * @param store_id
	 * @return map
	 */
	public Map getKcqcMxMap(String product_kind,String product_name,String cdate,String store_id){
		String xtqyrq = sysInitSetDao.getSysInitSet().getQyrq(); //ϵͳ��������
		if(xtqyrq == null){
			xtqyrq = "";
		}
		
		//����û�ѡ������С��ϵͳ��������
		if(cdate.compareTo(xtqyrq)<0){
			cdate = xtqyrq;
		}
		return kcMxReportDao.getKcqcMxMap(product_kind,product_name,cdate,store_id);
	}
	
	
	/**
	 * ����ȡ����ڳ���Ϣ���ڳ��޸�ΪXML��ʽ�����ʹ�ã�
	 * @param cdate
	 * @param store_id
	 * @return
	 */
	public Map getKcqcMap(String cdate,String store_id){
		String xtqyrq = sysInitSetDao.getSysInitSet().getQyrq(); //ϵͳ��������
		if(xtqyrq == null){
			xtqyrq = "";
		}
		
		//����û�ѡ������С��ϵͳ��������
		if(cdate.compareTo(xtqyrq)<0){
			cdate = xtqyrq;
		}
		return kcMxReportDao.getKcqcMap(cdate,store_id);
	}
	
	
	/**
	 * ȡ���仯��ϸ
	 * �������ⵥ����ⵥ���ⷿ����
	 * @param product_id
	 * @param start_date
	 * @param end_date
	 * @param store_id
	 * @param isDbd0
	 * @return
	 */
	public List getKcbhMxList(String product_id,String start_date,String end_date,String store_id,String isDbd0){
		return kcMxReportDao.getKcbhMxList(product_id, start_date, end_date, store_id,isDbd0);
	}
	
	
	/**
	 * ���ɱ��仯���
	 * @param product_id
	 * @param start_date
	 * @param end_date
	 * @return
	 */
	public List getKcCbBhMxList(String product_id,String start_date,String end_date){
		return kcMxReportDao.getKcCbBhMxList(product_id, start_date, end_date);
	}
	
	
	/**
	 * ȡ�������
	 * @param product_id
	 * @param start_date
	 * @param end_date
	 * @param store_id
	 * @return
	 */
	public int getRkNums(String product_id,String start_date,String end_date,String store_id){
		return kcMxReportDao.getRkNums(product_id, start_date, end_date, store_id);
	}
	
	
	/**
	 * ȡ��������
	 * @param product_id
	 * @param start_date
	 * @param end_date
	 * @param store_id
	 * @return
	 */
	public int getCkNums(String product_id,String start_date,String end_date,String store_id){
		return  kcMxReportDao.getCkNums(product_id, start_date, end_date, store_id);
	}
	
	
	
	/**
	 * �����������
	 * @param product_kind   ��Ʒ���
	 * @param product_name   �̿�����
	 * @param store_id       �ⷿ���
	 * @param state          �Ƿ���ʾͣ����Ʒ
	 * @param flag           �Ƿ���ʾ0�����Ʒ
	 * @return
	 */
	public List getKcNumsResults(String product_kind,String product_name,String store_id,String state,String flag,String px){
		return  kcMxReportDao.getKcNumsResults(product_kind, product_name, store_id,state, flag,px);
	}
	
	/**
	 * ��������(��ʷ)
	 * @param product_kind   ��Ʒ���
	 * @param product_name   �̿�����
	 * @param store_id       �ⷿ���
	 * @param state          �Ƿ���ʾͣ����Ʒ
	 * @param flag           �Ƿ���ʾ0�����Ʒ
	 * @return
	 */
	public List getHisKcResults(String product_kind,String product_name,String store_id,String state,String flag,String cdate){
		return kcMxReportDao.getHisKcResults(product_kind, product_name, store_id, state, flag, cdate);
	}
	
	
	/**
	 * �����������(��Ӧ��)
	 * @param product_kind   ��Ʒ���
	 * @param product_name   �̿�����
	 * @param store_id       �ⷿ���
	 * @param state          �Ƿ���ʾͣ����Ʒ
	 * @param flag           �Ƿ���ʾ0�����Ʒ
	 * @return
	 */
	public List getGysKcNumsResults(String product_kind){
		return  kcMxReportDao.getGysKcNumsResults(product_kind);
	}
	
	
	/**
	 * ��ʷ������ѯ
	 * @param product_kind  ��Ʒ���
	 * @param product_name  ��Ʒ����
	 * @param product_xh    ��Ʒ���
	 * @param store_id      �ⷿ���
	 * @param cdate         �������
	 * @return
	 */
	public List getHisKcjeResults(String product_kind,String product_name,String product_xh,String store_id,String cdate){
		return kcMxReportDao.getHisKcjeResults(product_kind, product_name, product_xh, store_id, cdate);
	}
	
	
	/**
	 * �ֲֿ���������ܣ���Ʒ�б�
	 * @param product_kind
	 * @param product_name
	 * @param state
	 * @return
	 */
	public List getProductList(String product_kind,String product_name,String state){
		return kcMxReportDao.getProductList(product_kind, product_name, state);		
	}
	
	
	/**
	 * �ֲֿ���������ܣ�ȡ�ⷿ�б�
	 * @param store_id
	 * @return
	 */
	public List getStoreList(String store_id){
		return kcMxReportDao.getStoreList(store_id);
	}
	
	
	/**
	 * ͳ�ƽ��д��HashMap�У�ǰ̨��Map��ȡ
	 * @param product_kind
	 * @param product_name
	 * @param state
	 * @param store_id
	 * @return Map(key,value)  key=product_id+store_id,value=�������
	 */
	public Map getKcStatResult(String product_kind,String product_name,String state,String store_id){
		return kcMxReportDao.getKcStatResult(product_kind, product_name, state, store_id);
	}

	
	/**
	 * ȡ�����������
	 * @param product_kind
	 * @param product_name
	 * @param start_date
	 * @param end_date
	 * @param store_id
	 * @param isDbd0 �Ƿ����������
	 * @return
	 */
	public Map getRkNums(String product_kind,String product_name,String start_date,String end_date,String store_id,String isDbd0){
		return kcMxReportDao.getRkNums(product_kind, product_name, start_date, end_date, store_id,isDbd0);
	}
	
	/**
	 * ȡ������������
	 * @param product_kind
	 * @param product_name
	 * @param start_date
	 * @param end_date
	 * @param store_id
	 * @param isDbd0 �Ƿ����������
	 * @return
	 */
	public Map getCkNums(String product_kind,String product_name,String start_date,String end_date,String store_id,String isDbd0){
		return kcMxReportDao.getCkNums(product_kind, product_name, start_date, end_date, store_id,isDbd0);
	}
	
	
	
	public KcMxReportDAO getKcMxReportDao() {
		return kcMxReportDao;
	}

	public void setKcMxReportDao(KcMxReportDAO kcMxReportDao) {
		this.kcMxReportDao = kcMxReportDao;
	}


	public SysInitSetDAO getSysInitSetDao() {
		return sysInitSetDao;
	}


	public void setSysInitSetDao(SysInitSetDAO sysInitSetDao) {
		this.sysInitSetDao = sysInitSetDao;
	}
	
}
