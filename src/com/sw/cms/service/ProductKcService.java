package com.sw.cms.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.sw.cms.dao.ProductKcDAO;
import com.sw.cms.dao.StoreDAO;
import com.sw.cms.dao.SysInitSetDAO;
import com.sw.cms.model.Page;
import com.sw.cms.model.ProductKc;
import com.sw.cms.model.SysInitSet;
import com.sw.cms.util.DateComFunc;

public class ProductKcService {
	
	private ProductKcDAO productKcDao;
	private StoreDAO storeDao;
	private SysInitSetDAO sysInitSetDao;
	
	
	/**
	 * ���ݲ�ѯ������ѯ����б�����ҳ��
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getProductKcList(String con,int curPage, int rowsPerPage){		
		return productKcDao.getProductKcList(con, curPage, rowsPerPage);
	}
	
	/**
	 * ���ݲ�ѯ������ѯ����ʼֵ
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getProductInitList(String con,String store_id,int curPage, int rowsPerPage){
		return productKcDao.getProductInitList(con,store_id, curPage, rowsPerPage);
	}
	
	/**
	 * <p>���ݲ�ѯ����ȡ����б�</p>
	 * <p>������ר�ã�����ʾ�������</p>
	 * @param con
	 * @return
	 */
	public Page getFxddProductKcList(String con,int curPage, int rowsPerPage){
		return productKcDao.getFxddProductKcList(con, curPage, rowsPerPage);
	}
	
	
	/**
	 * ����ĳһ��Ʒ�Ŀ�����
	 * @param product_id
	 * @return
	 */
	public int getProductKcByProductId(String product_id,String store_id){
		
		return productKcDao.getProductKcByProductId(product_id,store_id);
	}
	
	/**
	 * ����ĳһ��Ʒ�Ŀ�����
	 * @param product_id
	 * @return
	 */
	public HashMap getProductKcByProductId(String product_id){
		return productKcDao.getProductKcByProductId(product_id);
	}
	
	
	
	/**
	 * ��ʼ����Ʒ���
	 * @param productKc
	 */
	public void saveProductKc(String product_id,String store_id,String nums){
		SysInitSet sysInitSet = sysInitSetDao.getSysInitSet();
		
		String strQyrq = sysInitSet.getQyrq();
		
		if(strQyrq == null || strQyrq.equals("")){
			return;
		}
		
		Date qyrq = DateComFunc.strToDate(strQyrq,"yyyy-MM-dd"); //ϵͳ��������


		Date curDate = new Date();     //��ǰʱ��
		int kc_nums = new Integer(nums);  //���ֵ
		int curKcqc = 0; //����ڳ�ֵ
		
		//����ǰʱ����ڻ�����ڳ�����ʱȥ���ɿ�
		while(DateComFunc.formatDate(curDate, "yyyy-MM-dd").compareTo(DateComFunc.formatDate(qyrq, "yyyy-MM-dd")) >=0){
			
			//��ǰ�����ڳ�ֵ = ���ֵ + �������� - �������
			//���磺2008-10-12�Ŀ���ڳ�ֵ  = 2008-10-13�Ŀ���ڳ�ֵ + 2008-10-12�ĳ������� - 2008-10-12���������
			int ck_nums = productKcDao.getCkNums(product_id, store_id, DateComFunc.formatDate(curDate, "yyyy-MM-dd"));
			int rk_nums = productKcDao.getRkNums(product_id, store_id, DateComFunc.formatDate(curDate, "yyyy-MM-dd"));
			curKcqc = kc_nums + ck_nums - rk_nums;
			
			productKcDao.genKcqc(product_id, DateComFunc.formatDate(curDate, "yyyy-MM-dd"), store_id, curKcqc);
			
			kc_nums = curKcqc;
			curDate = DateComFunc.addDay(curDate, -1);  //��ǰ���1
		}
		
		//���浱ǰ���ֵ
		productKcDao.saveProductKc(product_id, store_id, nums);		
	}
	
	
	/**
	 * ȡ�ֿ��б�
	 * @return
	 */
	public List getStoreList(){
		return storeDao.getAllStoreList();
	}

	
	/**
	 * ���ݿ����Ʒ�����Ƿ���Ǹÿ��
	 * @param productKc
	 * @return
	 */
	public int getKcCounts(ProductKc productKc){
		return productKcDao.getKcCounts(productKc);
	}
	
	
	/**
	 * ���ؿ���ѯ��ϸ���
	 * @param product_id
	 * @param product_name
	 * @param product_xh
	 * @return
	 */
	public Page getProductKcMx(String kc_con,int curPage,int rowsPerPage){
		return productKcDao.getProductKcMx(kc_con, curPage, rowsPerPage);
	}
	
	
	public Page getProductKcMxWts(String kc_con,int curPage,int rowsPerPage){
		return productKcDao.getProductKcMxWts(kc_con, curPage, rowsPerPage);
	}


	public void setProductKcDao(ProductKcDAO productKcDao) {
		this.productKcDao = productKcDao;
	}



	public void setStoreDao(StoreDAO storeDao) {
		this.storeDao = storeDao;
	}

	public SysInitSetDAO getSysInitSetDao() {
		return sysInitSetDao;
	}

	public void setSysInitSetDao(SysInitSetDAO sysInitSetDao) {
		this.sysInitSetDao = sysInitSetDao;
	}

	public ProductKcDAO getProductKcDao() {
		return productKcDao;
	}

	public StoreDAO getStoreDao() {
		return storeDao;
	}
	

}
