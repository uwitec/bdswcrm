package com.sw.cms.service;

import java.util.ArrayList;
import java.util.List;

import com.sw.cms.dao.DbsqDAO;
import com.sw.cms.dao.KfdbDAO;
import com.sw.cms.model.Dbsq;
import com.sw.cms.model.DbsqProduct;
import com.sw.cms.model.Kfdb;
import com.sw.cms.model.KfdbProduct;
import com.sw.cms.model.Page;

public class DbsqService {
	
	private DbsqDAO dbsqDao;
	private KfdbDAO kfdbDao;
	
	/**
	 * ���ݲ�ѯ����ȡ���������б�
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getDbsqList(String con,int curPage, int rowsPerPage){
		return dbsqDao.getDbsqList(con, curPage, rowsPerPage);
	}
	
	
	/**
	 * ����������뵥�����Ϣ
	 * @param dbsq
	 * @param dbsqProducts
	 */
	public void saveDbsq(Dbsq dbsq,List dbsqProducts){
		dbsqDao.saveDbsq(dbsq, dbsqProducts);
		
		if(dbsq.getState().equals("���ύ")){ //��ӿⷿ����
			this.addKfdb(dbsq, dbsqProducts);
		}
	}
	
	
	/**
	 * ���µ������뵥�����Ϣ
	 * @param dbsq
	 * @param dbsqProducts
	 */
	public void updateDbsq(Dbsq dbsq,List dbsqProducts){
		
		//�������뵥����Ѿ��ύ�������κβ���
		if(dbsqDao.isDbsqFinish(dbsq.getId())){
			return;
		}
		
		dbsqDao.updateDbsq(dbsq, dbsqProducts);
		
		if(dbsq.getState().equals("���ύ")){//��ӿⷿ����
			this.addKfdb(dbsq, dbsqProducts);
		}		
	}
	
	
	/**
	 * ȡ�������뵥��Ϣ
	 * @param id
	 * @return
	 */
	public Object getDbsq(String id){
		return dbsqDao.getDbsq(id);
	}
	
	
	/**
	 * ȡ�������뵥�����Ʒ�б�
	 * @param id
	 * @return
	 */
	public List getDbsqProducts(String id){
		return dbsqDao.getDbsqProducts(id);
	}
	
	
	/**
	 * ɾ����������
	 * @param id
	 */
	public void delDbsq(String id){
		
		//�������뵥����Ѿ��ύ�������κβ���
		if(dbsqDao.isDbsqFinish(id)){
			return;
		}
		
		dbsqDao.delDbsq(id);
	}
		
	
	/**
	 * ȡ��ǰ���õ����к�
	 * @return
	 */
	public String updateDbsqID() {
		return dbsqDao.getDbsqID();
	}
	
	
	/**
	 * �жϵ��������Ƿ��Ѿ��ύ
	 * @param id
	 * @return
	 */
	public boolean isDbsqFinish(String id){
		return dbsqDao.isDbsqFinish(id);
	}
	
	
	/**
	 * ���ݵ����������ɿⷿ���������Ϣ������
	 * @param dbsq
	 * @param dbsqProducts
	 */
	private void addKfdb(Dbsq dbsq,List dbsqProducts){
		Kfdb kfdb = new Kfdb();
		List kfdbProducts = new ArrayList();
		
		String kfdb_id = kfdbDao.getKfdbID();
		kfdb.setId(kfdb_id);
		kfdb.setCk_date(dbsq.getCreatdate());
		kfdb.setDbsq_id(dbsq.getId());
		kfdb.setRk_store_id(dbsq.getStore_id());
		kfdb.setSqr(dbsq.getJsr());
		kfdb.setCzr(dbsq.getCzr());
		kfdb.setState("�ѱ���");
		kfdb.setRemark("��������" + dbsq.getCreatdate() + "�����뵥��� [" + dbsq.getId() + "]");
		
		if(dbsqProducts != null && dbsqProducts.size()>0){
			for(int i=0;i<dbsqProducts.size();i++){
				DbsqProduct dbsqProduct = (DbsqProduct)dbsqProducts.get(i);
				if(dbsqProduct != null){
					if(!dbsqProduct.getProduct_id().equals("") && !dbsqProduct.getProduct_name().equals("")){
						KfdbProduct kfdbProduct = new KfdbProduct();
						
						kfdbProduct.setKfdb_id(kfdb_id);
						kfdbProduct.setProduct_id(dbsqProduct.getProduct_id());
						kfdbProduct.setProduct_name(dbsqProduct.getProduct_name());
						kfdbProduct.setProduct_xh(dbsqProduct.getProduct_xh());
						kfdbProduct.setNums(dbsqProduct.getNums());
						kfdbProduct.setRemark(dbsqProduct.getRemark());
						
						kfdbProducts.add(kfdbProduct);
					}
				}
			}
		}
		
		kfdbDao.saveKfdb(kfdb, kfdbProducts);
	}


	public DbsqDAO getDbsqDao() {
		return dbsqDao;
	}


	public void setDbsqDao(DbsqDAO dbsqDao) {
		this.dbsqDao = dbsqDao;
	}


	public KfdbDAO getKfdbDao() {
		return kfdbDao;
	}


	public void setKfdbDao(KfdbDAO kfdbDao) {
		this.kfdbDao = kfdbDao;
	}

}
