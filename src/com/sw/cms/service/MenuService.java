package com.sw.cms.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sw.cms.dao.MenuDAO;

public class MenuService {
	
	private MenuDAO menuDao;
	
	/**
	 * �����û�����Ȩ�޵�ҵ���ܲ˵�
	 * @param user_id
	 * @param column_id
	 * @return
	 */
	public List getUserYwgzFunc(String user_id,String column_id){
		return menuDao.getUserYwgzFunc(user_id, column_id);
	}	
	
	/**
	 * �����û����е�ҵ�����б�
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
	 * ���ݸ���ĿID���û����ȡ�û�����Ȩ�޵���Ŀ���
	 * @param user_id
	 * @param parent_id
	 * @return
	 */
	public List getColumnList(String user_id,String parent_id,String yw_flag){
		return menuDao.getColumnList(user_id, parent_id,yw_flag);
	}
	
	/**
	 * �����û���ȡ�ڲ��칫�˵��б�
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
