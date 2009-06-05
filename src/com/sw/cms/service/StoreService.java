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
	

	public void setStoreDao(StoreDAO storeDao) {
		this.storeDao = storeDao;
	}
	

}
