package com.sw.cms.service;

import java.util.Map;

import com.sw.cms.dao.YgbbDAO;
import com.sw.cms.model.Page;
import com.sw.cms.model.SysUser;


public class YgbbService {
	
	private YgbbDAO ygbbDao;
	
	/**
	 * 取员工列表（带分页）
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getUserList(String con,int curPage, int rowsPerPage){
		return ygbbDao.getUserList(con, curPage, rowsPerPage);
	}
	
	/**
	 * 根据user_id取用户信息
	 * @param user_id
	 * @return
	 */
	public Map getUser(String user_id){
		return ygbbDao.getUser(user_id);
	}
	
	public YgbbDAO getYgbbDao() {
		return ygbbDao;
	}


	public void setYgbbDao(YgbbDAO ygbbDao) {
		this.ygbbDao = ygbbDao;
	}

}
