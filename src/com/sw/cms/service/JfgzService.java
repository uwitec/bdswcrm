package com.sw.cms.service;

import java.util.List;

import com.sw.cms.dao.JfgzDAO;
import com.sw.cms.model.Page;
import com.sw.cms.model.Jfgz;

public class JfgzService {
	
	private JfgzDAO jfgzDao;
	
	/**
	 * 取积分规则列表
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getJfgzList(int curPage, int rowsPerPage,String con){
		return jfgzDao.getJfgzList(curPage, rowsPerPage,con);
	}
	
	
	public String updateJfgzId() {
		return jfgzDao.getJfgzId();
	}
	
	/**
	 * 保存积分规则信息
	 * @param info
	 */
	public void saveJfgz(Jfgz info){
//		 如果积分规则已提交，不做处理
		if(jfgzDao.isJfgzSubmit(info.getId())){
			return;
		}
		jfgzDao.saveJfgz(info);
	}
	
	
	/**
	 * 更新积分规则信息
	 * @param info
	 */
	public void updateJfgz(Jfgz info){
		jfgzDao.updateJfgz(info);
	}
	
	
	/**
	 * 根据ID取积分规则信息
	 * @param id
	 * @return
	 */
	public Jfgz getJfgz(String id){
		return jfgzDao.getJfgz(id);
	}
	
	
	/**
	 * 删除积分规则信息
	 * @param id
	 */
	public void delJfgz(String id){
		jfgzDao.delJfgz(id);
	}
	
	 /**
	 * 取所有积分规则列表
	 * @return
	 */
	public List getAllJfgzList(){
		return jfgzDao.getAllJfgzList();
	}
	
	public JfgzDAO getJfgzDao() {
		return jfgzDao;
	}

	public void setJfgzDao(JfgzDAO jfgzDao) {
		this.jfgzDao = jfgzDao;
	}
}
