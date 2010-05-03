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
	 * 根据查询条件查询库存列表（带分页）
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getProductKcList(String con,int curPage, int rowsPerPage){		
		return productKcDao.getProductKcList(con, curPage, rowsPerPage);
	}
	
	/**
	 * 根据查询条件查询库存初始值
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getProductInitList(String con,String store_id,int curPage, int rowsPerPage){
		return productKcDao.getProductInitList(con,store_id, curPage, rowsPerPage);
	}
	
	/**
	 * <p>根据查询条件取库存列表</p>
	 * <p>分销商专用，不显示库存数量</p>
	 * @param con
	 * @return
	 */
	public Page getFxddProductKcList(String con,int curPage, int rowsPerPage){
		return productKcDao.getFxddProductKcList(con, curPage, rowsPerPage);
	}
	
	
	/**
	 * 返回某一商品的库存情况
	 * @param product_id
	 * @return
	 */
	public int getProductKcByProductId(String product_id,String store_id){
		
		return productKcDao.getProductKcByProductId(product_id,store_id);
	}
	
	/**
	 * 返回某一商品的库存情况
	 * @param product_id
	 * @return
	 */
	public HashMap getProductKcByProductId(String product_id){
		return productKcDao.getProductKcByProductId(product_id);
	}
	
	
	
	/**
	 * 初始化商品库存
	 * @param productKc
	 */
	public void saveProductKc(String product_id,String store_id,String nums){
		SysInitSet sysInitSet = sysInitSetDao.getSysInitSet();
		
		String strQyrq = sysInitSet.getQyrq();
		
		if(strQyrq == null || strQyrq.equals("")){
			return;
		}
		
		Date qyrq = DateComFunc.strToDate(strQyrq,"yyyy-MM-dd"); //系统启用日期


		Date curDate = new Date();     //当前时间
		int kc_nums = new Integer(nums);  //库存值
		int curKcqc = 0; //库存期初值
		
		//当当前时间大于或等于期初日期时去生成库
		while(DateComFunc.formatDate(curDate, "yyyy-MM-dd").compareTo(DateComFunc.formatDate(qyrq, "yyyy-MM-dd")) >=0){
			
			//当前天库存期初值 = 库存值 + 出库数量 - 入库数量
			//例如：2008-10-12的库存期初值  = 2008-10-13的库存期初值 + 2008-10-12的出库数量 - 2008-10-12的入库数量
			int ck_nums = productKcDao.getCkNums(product_id, store_id, DateComFunc.formatDate(curDate, "yyyy-MM-dd"));
			int rk_nums = productKcDao.getRkNums(product_id, store_id, DateComFunc.formatDate(curDate, "yyyy-MM-dd"));
			curKcqc = kc_nums + ck_nums - rk_nums;
			
			productKcDao.genKcqc(product_id, DateComFunc.formatDate(curDate, "yyyy-MM-dd"), store_id, curKcqc);
			
			kc_nums = curKcqc;
			curDate = DateComFunc.addDay(curDate, -1);  //当前天减1
		}
		
		//保存当前库存值
		productKcDao.saveProductKc(product_id, store_id, nums);		
	}
	
	
	/**
	 * 取仓库列表
	 * @return
	 */
	public List getStoreList(){
		return storeDao.getAllStoreList();
	}

	
	/**
	 * 根据库存商品返回是否存是该库存
	 * @param productKc
	 * @return
	 */
	public int getKcCounts(ProductKc productKc){
		return productKcDao.getKcCounts(productKc);
	}
	
	
	/**
	 * 返回库存查询明细结果
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
