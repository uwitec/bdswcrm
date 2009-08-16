package com.sw.cms.service;

import java.util.List;

import com.sw.cms.dao.StoreDAO;
import com.sw.cms.model.Page;
import com.sw.cms.model.StoreHouse;

public class StoreService {
	
	private StoreDAO storeDao;
	
	
	/**
	 * ���ط�ҳ��Ŀⷿ�б�
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getStores(String con,int curPage, int rowsPerPage){
		return storeDao.getStores(con, curPage, rowsPerPage);
	}
	
	
	/**
	 * ȡ���вֿ��б�
	 * @return
	 */
	public List getAllStoreList(){
		return storeDao.getAllStoreList();
	}
	
	
	/**
	 * ����ֿ���Ϣ
	 * @param store
	 */
	public void saveStore(StoreHouse store){
		storeDao.saveStore(store);
	}
	
	
	
	/**
	 * ���¿ⷿ��Ϣ
	 * @param store
	 */
	public void updateStore(StoreHouse store){
		storeDao.updateStore(store);
	}
	
	
	
	/**
	 * ����IDȡ�ֿ���Ϣ
	 * @param id
	 * @return
	 */
	public Object getStore(String id){
		return storeDao.getStore(id);
	}
	
	
	
	/**
	 * ɾ���ֿ���Ϣ
	 * @param id
	 */
	public void delStore(String id){
		storeDao.delStore(id);
	}
	
	
	/**
	 * �жϿⷿ�����Ƿ����ɾ��<BR>
	 * �������ҵ���ϵ������ɾ��<BR>
	 * ҵ���ϵ��������ⵥ�����ⵥ���������롢�ⷿ���������ڿ��ֵ<BR>
	 * @param id boolean true:���ԣ�false:������
	 */
	public boolean isCanDel(String id){
		return storeDao.isCanDel(id);
	}
	

	public void setStoreDao(StoreDAO storeDao) {
		this.storeDao = storeDao;
	}
	

}
