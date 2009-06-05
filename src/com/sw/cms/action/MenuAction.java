package com.sw.cms.action;

import java.util.ArrayList;
import java.util.List;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.service.MenuService;

public class MenuAction extends BaseAction {
	
	private MenuService menuService;
	
	private List menuList = new ArrayList();
	
	
	public String list(){
		return "success";
	}
	
	public String topMenu(){
		return "success";
	}
	

	public List getMenuList() {
		return menuList;
	}

	public void setMenuList(List menuList) {
		this.menuList = menuList;
	}

	public MenuService getMenuService() {
		return menuService;
	}

	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}

}
