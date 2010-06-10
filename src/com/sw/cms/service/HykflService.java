package com.sw.cms.service;

import com.sw.cms.dao.HykflDAO;
import com.sw.cms.model.Page;
import com.sw.cms.model.Hykfl;

public class HykflService {
	
	private HykflDAO hykflDao;
	
	/**
	 * ȡ��Ա�������б�
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getHykflList(int curPage, int rowsPerPage,String con){
		return hykflDao.getHykflList(curPage, rowsPerPage,con);
	}
	
	
	public String updateHykflId() {
		return hykflDao.getHykflId();
	}
	
	/**
	 * �����Ա��������Ϣ
	 * @param info
	 */
	public void saveHykfl(Hykfl info){
//		 �����Ա���������ύ����������
		if(hykflDao.isHykflSubmit(info.getId())){
			return;
		}
		hykflDao.saveHykfl(info);
	}
	
	
	/**
	 * ���»�Ա��������Ϣ
	 * @param info
	 */
	public void updateHykfl(Hykfl info){
		hykflDao.updateHykfl(info);
	}
	
	
	/**
	 * ����IDȡ��Ա��������Ϣ
	 * @param id
	 * @return
	 */
	public Hykfl getHykfl(String id){
		return hykflDao.getHykfl(id);
	}
	
	
	/**
	 * ɾ����Ա��������Ϣ
	 * @param id
	 */
	public void delHykfl(String id){
		hykflDao.delHykfl(id);
	}
	
   
	public HykflDAO getHykflDao() {
		return hykflDao;
	}

	public void setHykflDao(HykflDAO hykflDao) {
		this.hykflDao = hykflDao;
	}
}
