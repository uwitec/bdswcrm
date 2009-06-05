package com.sw.cms.service;

import com.sw.cms.dao.PzDAO;
import com.sw.cms.model.Page;
import com.sw.cms.model.Pz;

public class PzService {
	
	private PzDAO pzDao;
	
	/**
	 * 取平账列表
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getPzList(String con,int curPage, int rowsPerPage){
		return pzDao.getPzList(con, curPage, rowsPerPage);
	}
	
	
	/**
	 * 保存平账信息
	 * @param pz
	 */
	public void savePz(Pz pz){
		pzDao.savePz(pz);		
	}
	
	
	/**
	 * 更新平账信息
	 * @param pz
	 */
	public void updatePz(Pz pz){
		pzDao.updatePz(pz);	
	}
	
	
	/**
	 * 取平账信息
	 * @param id
	 * @return
	 */
	public Object getPz(String id){
		return pzDao.getPz(id);
	}
	
	
	/**
	 * 删除平账信息
	 * @param id
	 */
	public void delPz(String id){
		pzDao.delPz(id);
	}
	
	
	/**
	 * 取当前可用的序列号
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
