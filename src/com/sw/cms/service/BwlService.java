package com.sw.cms.service;

import com.sw.cms.dao.BwlDAO;
import com.sw.cms.model.Page;
import com.sw.cms.model.Bwl;

public class BwlService {
	
	private BwlDAO bwlDao;
	
	/**
	 * 取备忘录信息列表
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getBwlList(int curPage, int rowsPerPage,String user_id){
		return bwlDao.getBwlList(curPage, rowsPerPage,user_id);
	}
	
	/**
	 * 取备忘录共享信息列表
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getBwlShareList(int curPage, int rowsPerPage,String user_id){
		return bwlDao.getBwlShareList(curPage, rowsPerPage,user_id);
	}
	
	/**
	 * 取用户名列表
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getLxrAll(String real_name, int curPage, int rowsPerPage) {
		return bwlDao.getLxrAll(real_name, curPage, rowsPerPage);
	}
	
	/**
	 * 保存备忘录信息
	 * @param info
	 */
	public void saveBwl(Bwl info){
//		 如果备忘录已提交，不做处理
		if(bwlDao.isBwlSubmit(info.getId())){
			return;
		}
		bwlDao.saveBwl(info);
	}
	
	
	/**
	 * 更新备忘录信息
	 * @param info
	 */
	public void updateBwl(Bwl info){
		bwlDao.updateBwl(info);
	}
	
	
	/**
	 * 根据ID取备忘录信息
	 * @param id
	 * @return
	 */
	public Bwl getBwl(String id){
		return bwlDao.getBwl(id);
	}
	
	
	/**
	 * 删除备忘录信息
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
