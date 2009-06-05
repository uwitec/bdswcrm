package com.sw.cms.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sw.cms.dao.MenuDAO;

public class MenuService {
	
	private MenuDAO menuDao;
	
	/**
	 * 返回用户具有权限的业务功能菜单
	 * @param user_id
	 * @param column_id
	 * @return
	 */
	public List getUserYwgzFunc(String user_id,String column_id){
		return menuDao.getUserYwgzFunc(user_id, column_id);
	}	
	
	/**
	 * 返回用户具有的业务功能列表
	 * @param user_id
	 * @return
	 */
	public List getUserYwgnFunc(String user_id){
		List list = new ArrayList();
		List menuList = menuDao.getUserYwgnFunc(user_id);
		if(menuList != null && menuList.size()>0){
			for(int i=0;i<menuList.size();i++){
				Map map = (Map)menuList.get(i);
				list.add((String)map.get("func_id"));
			}
		}
		
		return list;
	}
	
	/**
	 * 根据父栏目ID，用户编号取用户具有权限的栏目编号
	 * @param user_id
	 * @param parent_id
	 * @return
	 */
	public List getColumnList(String user_id,String parent_id,String yw_flag){
		return menuDao.getColumnList(user_id, parent_id,yw_flag);
	}
	
	/**
	 * 根据用户名取内部办公菜单列表
	 * @param user_id
	 * @return
	 */
	public List getNbbgFuncsByUserID(String user_id){
		return menuDao.getNbbgFuncsByUserID(user_id);
	}
	

	public void setMenuDao(MenuDAO menuDao) {
		this.menuDao = menuDao;
	}

	public MenuDAO getMenuDao() {
		return menuDao;
	}

}
