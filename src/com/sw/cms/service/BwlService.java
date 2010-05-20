package com.sw.cms.service;

import com.sw.cms.dao.BwlDAO;
import com.sw.cms.model.Page;
import com.sw.cms.model.Bwl;

public class BwlService {
	
	private BwlDAO bwlDao;
	
	/**
	 * ȡ����¼��Ϣ�б�
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getBwlList(int curPage, int rowsPerPage,String user_id){
		return bwlDao.getBwlList(curPage, rowsPerPage,user_id);
	}
	
	/**
	 * ȡ����¼������Ϣ�б�
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getBwlShareList(int curPage, int rowsPerPage,String user_id){
		return bwlDao.getBwlShareList(curPage, rowsPerPage,user_id);
	}
	
	/**
	 * ȡ�û����б�
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getLxrAll(String real_name, int curPage, int rowsPerPage) {
		return bwlDao.getLxrAll(real_name, curPage, rowsPerPage);
	}
	
	/**
	 * ���汸��¼��Ϣ
	 * @param info
	 */
	public void saveBwl(Bwl info){
//		 �������¼���ύ����������
		if(bwlDao.isBwlSubmit(info.getId())){
			return;
		}
		bwlDao.saveBwl(info);
	}
	
	
	/**
	 * ���±���¼��Ϣ
	 * @param info
	 */
	public void updateBwl(Bwl info){
		bwlDao.updateBwl(info);
	}
	
	
	/**
	 * ����IDȡ����¼��Ϣ
	 * @param id
	 * @return
	 */
	public Bwl getBwl(String id){
		return bwlDao.getBwl(id);
	}
	
	
	/**
	 * ɾ������¼��Ϣ
	 * @param id
	 */
	public void delBwl(String id){
		bwlDao.delBwl(id);
	}
	
   
	public BwlDAO getBwlDao() {
		return bwlDao;
	}

	public void setBwlDao(BwlDAO bwlDao) {
		this.bwlDao = bwlDao;
	}
}
