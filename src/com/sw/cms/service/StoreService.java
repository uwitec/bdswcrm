package com.sw.cms.service;

import java.util.List;

import com.sw.cms.dao.StoreDAO;
import com.sw.cms.model.Page;
import com.sw.cms.model.StoreHouse;

public class StoreService {
	
	private StoreDAO storeDao;
	
	
	/**
	 * 返回分页后的库房列表
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getStores(String con,int curPage, int rowsPerPage){
		return storeDao.getStores(con, curPage, rowsPerPage);
	}
	
	
	/**
	 * 取所有仓库列表
	 * @return
	 */
	public List getAllStoreList(){
		return storeDao.getAllStoreList();
	}
	
	
	/**
	 * 保存仓库信息
	 * @param store
	 */
	public void saveStore(StoreHouse store){
		storeDao.saveStore(store);
	}
	
	
	
	/**
	 * 更新库房信息
	 * @param store
	 */
	public void updateStore(StoreHouse store){
		storeDao.updateStore(store);
	}
	
	
	
	/**
	 * 根据ID取仓库信息
	 * @param id
	 * @return
	 */
	public Object getStore(String id){
		return storeDao.getStore(id);
	}
	
	
	
	/**
	 * 删除仓库信息
	 * @param id
	 */
	public void delStore(String id){
		storeDao.delStore(id);
	}
	
	
	/**
	 * 判断库房资料是否可以删除<BR>
	 * 如果发生业务关系不可以删除<BR>
	 * 业务关系包括：入库单、出库单、调拨申请、库房调拨、存在库存值<BR>
	 * @param id boolean true:可以；false:不可以
	 */
	public boolean isCanDel(String id){
		return storeDao.isCanDel(id);
	}
	

	public void setStoreDao(StoreDAO storeDao) {
		this.storeDao = storeDao;
	}
	

}
