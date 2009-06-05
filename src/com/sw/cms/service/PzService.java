package com.sw.cms.service;

import com.sw.cms.dao.PzDAO;
import com.sw.cms.model.Page;
import com.sw.cms.model.Pz;

public class PzService {
	
	private PzDAO pzDao;
	
	/**
	 * ȡƽ���б�
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getPzList(String con,int curPage, int rowsPerPage){
		return pzDao.getPzList(con, curPage, rowsPerPage);
	}
	
	
	/**
	 * ����ƽ����Ϣ
	 * @param pz
	 */
	public void savePz(Pz pz){
		pzDao.savePz(pz);		
	}
	
	
	/**
	 * ����ƽ����Ϣ
	 * @param pz
	 */
	public void updatePz(Pz pz){
		pzDao.updatePz(pz);	
	}
	
	
	/**
	 * ȡƽ����Ϣ
	 * @param id
	 * @return
	 */
	public Object getPz(String id){
		return pzDao.getPz(id);
	}
	
	
	/**
	 * ɾ��ƽ����Ϣ
	 * @param id
	 */
	public void delPz(String id){
		pzDao.delPz(id);
	}
	
	
	/**
	 * ȡ��ǰ���õ����к�
	 * @return
	 */
	public String updatePzID() {
		return pzDao.getPzID();
	}


	public PzDAO getPzDao() {
		return pzDao;
	}


	public void setPzDao(PzDAO pzDao) {
		this.pzDao = pzDao;
	}

}
