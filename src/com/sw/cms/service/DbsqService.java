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
	 * 根据查询条件取调拨申请列表
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getDbsqList(String con,int curPage, int rowsPerPage){
		return dbsqDao.getDbsqList(con, curPage, rowsPerPage);
	}
	
	
	/**
	 * 保存调拨申请单相关信息
	 * @param dbsq
	 * @param dbsqProducts
	 */
	public void saveDbsq(Dbsq dbsq,List dbsqProducts){
		dbsqDao.saveDbsq(dbsq, dbsqProducts);
		
		if(dbsq.getState().equals("已提交")){ //添加库房调拨
			this.addKfdb(dbsq, dbsqProducts);
		}
	}
	
	
	/**
	 * 更新调拨申请单相关信息
	 * @param dbsq
	 * @param dbsqProducts
	 */
	public void updateDbsq(Dbsq dbsq,List dbsqProducts){
		
		//调拨申请单如果已经提交，不做任何操作
		if(dbsqDao.isDbsqFinish(dbsq.getId())){
			return;
		}
		
		dbsqDao.updateDbsq(dbsq, dbsqProducts);
		
		if(dbsq.getState().equals("已提交")){//添加库房调拨
			this.addKfdb(dbsq, dbsqProducts);
		}		
	}
	
	
	/**
	 * 取调拨申请单信息
	 * @param id
	 * @return
	 */
	public Object getDbsq(String id){
		return dbsqDao.getDbsq(id);
	}
	
	
	/**
	 * 取调拨申请单相关商品列表
	 * @param id
	 * @return
	 */
	public List getDbsqProducts(String id){
		return dbsqDao.getDbsqProducts(id);
	}
	
	
	/**
	 * 删除调拨申请
	 * @param id
	 */
	public void delDbsq(String id){
		
		//调拨申请单如果已经提交，不做任何操作
		if(dbsqDao.isDbsqFinish(id)){
			return;
		}
		
		dbsqDao.delDbsq(id);
	}
		
	
	/**
	 * 取当前可用的序列号
	 * @return
	 */
	public String updateDbsqID() {
		return dbsqDao.getDbsqID();
	}
	
	
	/**
	 * 判断调拨申请是否已经提交
	 * @param id
	 * @return
	 */
	public boolean isDbsqFinish(String id){
		return dbsqDao.isDbsqFinish(id);
	}
	
	
	/**
	 * 根据调拨申请生成库房调拨相关信息并保存
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
		kfdb.setState("已保存");
		kfdb.setRemark("调拨申请" + dbsq.getCreatdate() + "，申请单编号 [" + dbsq.getId() + "]");
		
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
