package com.sw.cms.service;

import java.util.Map;

import com.sw.cms.dao.YgbbDAO;
import com.sw.cms.model.Page;
import com.sw.cms.model.SysUser;


public class YgbbService {
	
	private YgbbDAO ygbbDao;
	
	/**
	 * ȡԱ���б�����ҳ��
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getUserList(String con,int curPage, int rowsPerPage){
		return ygbbDao.getUserList(con, curPage, rowsPerPage);
	}
	
	/**
	 * ����user_idȡ�û���Ϣ
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
