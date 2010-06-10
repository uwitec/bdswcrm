package com.sw.cms.service;

import com.sw.cms.dao.HykflDAO;
import com.sw.cms.model.Page;
import com.sw.cms.model.Hykfl;

public class HykflService {
	
	private HykflDAO hykflDao;
	
	/**
	 * 取会员卡分类列表
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
	 * 保存会员卡分类信息
	 * @param info
	 */
	public void saveHykfl(Hykfl info){
//		 如果会员卡分类已提交，不做处理
		if(hykflDao.isHykflSubmit(info.getId())){
			return;
		}
		hykflDao.saveHykfl(info);
	}
	
	
	/**
	 * 更新会员卡分类信息
	 * @param info
	 */
	public void updateHykfl(Hykfl info){
		hykflDao.updateHykfl(info);
	}
	
	
	/**
	 * 根据ID取会员卡分类信息
	 * @param id
	 * @return
	 */
	public Hykfl getHykfl(String id){
		return hykflDao.getHykfl(id);
	}
	
	
	/**
	 * 删除会员卡分类信息
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
