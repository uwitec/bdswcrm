package com.sw.cms.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.model.LoginInfo;
import com.sw.cms.model.Page;
import com.sw.cms.service.FirstIndexService;
import com.sw.cms.service.MenuService;
import com.sw.cms.service.UserService;
import com.sw.cms.service.XxfbNbggService;
import com.sw.cms.util.Constant;
import com.sw.cms.util.StringUtils;

public class FirstIndexAction extends BaseAction {
	
	private FirstIndexService firstIndexService;
	private XxfbNbggService xxfbNbggService;
	private MenuService menuService;
	private UserService userService;

	private List nbggList = new ArrayList();

	
	private Page undoWorkPage;
	private int curPage = 1;
	
	List ywgnList = new ArrayList();
	
	
	/**
	 * ��ҳ��ʾ��
	 * @return
	 */
	public String listMain(){
		
		LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		String user_id = info.getUser_id();
		
		ywgnList = menuService.getUserYwgnFunc(user_id);

		//�ڲ�����
		Page page = xxfbNbggService.getNbggList(1, 10);
		nbggList = page.getResults();

		return "success";
	}
	
	
	/**
	 * ��ʾ������ҳ
	 * @return
	 */
	public String listFxMain(){
		Page page = xxfbNbggService.getNbggList(1, 20 , "1");
		nbggList = page.getResults();
		return "success";
	}
	
	
	/**
	 * ��ʾ��Ӧ����ҳ
	 * @return
	 */
	public String listGysMain(){
		return "success";
	}
	
	
	/**
	 * ���칤���б�
	 * @return
	 */
	public String listUndoWork(){
		try{
			int rowsPerPage = Constant.PAGE_SIZE2;
			
			LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
			String user_id = info.getUser_id();  //��ǰ��½�û�
			
			String con="";
			
			//����в������ⵥȨ��
			if(userService.isHasRightFuncs("FC0005", user_id)){
				con += " or yw_type='�����ⵥ��'";
			}
			
			//����в�����ⵥȨ��
			if(userService.isHasRightFuncs("FC0004", user_id)){
				con += " or yw_type='����ⵥ��'";
			}
			
			//����м۸�����Ȩ��
			if(userService.isHashJgspRight(user_id)){
				con += " or yw_type='���������۵�'";
				
				con += " or (yw_type='���������۶���' and flag='4')";
			}
			
			//����г�������Ȩ��
			if(userService.isHasCespRight(user_id)){
				con += " or (yw_type='���������۶���' and flag='3')";
			}
			
			//����г�������Ȩ��
			if(userService.isHasCqspRight(user_id)){
				con += " or (yw_type='���������۶���' and flag='1')";
			}
			
			//����г���۸�����Ȩ��
			if(userService.isHasCeAndJgspRight(user_id)){
				con += " or (yw_type='���������۶���' and flag='2')";
			}
			
			//�ɹ���������Ȩ������
			Map cgfkMap = userService.getSpRight("�ɹ�����");
			String sp_flag = "";
			String role_id = "";
			if(cgfkMap != null){
				sp_flag = StringUtils.nullToStr(cgfkMap.get("sp_flag"));
				role_id = StringUtils.nullToStr(cgfkMap.get("role_id"));
			}
			String[] roles = role_id.split(",");
			//��ǰ�û��вɹ���������Ȩ��
			if(userService.isUserInRole(user_id, roles)){
				con += " or yw_type='��������������'";
			}
			
			
			//������������Ȩ������
			Map fysqMap = userService.getSpRight("��������");
			sp_flag = "";
			role_id = "";
			if(fysqMap != null){
				sp_flag = StringUtils.nullToStr(fysqMap.get("sp_flag"));
				role_id = StringUtils.nullToStr(fysqMap.get("role_id"));
			}
			String[] fysqRoles = role_id.split(",");
			//��ǰ�û��з�����������Ȩ��
			if(userService.isUserInRole(user_id, fysqRoles)){
				con += " or yw_type='����������'";
			}
			
			if(!con.equals("")){
				undoWorkPage = firstIndexService.getUndoWorks(con, curPage, rowsPerPage);
			}
			
			return SUCCESS;
		}catch(Exception e){
			log.error("");
			return ERROR;
		}
	}


	public FirstIndexService getFirstIndexService() {
		return firstIndexService;
	}


	public void setFirstIndexService(FirstIndexService firstIndexService) {
		this.firstIndexService = firstIndexService;
	}


	public XxfbNbggService getXxfbNbggService() {
		return xxfbNbggService;
	}


	public void setXxfbNbggService(XxfbNbggService xxfbNbggService) {
		this.xxfbNbggService = xxfbNbggService;
	}


	public MenuService getMenuService() {
		return menuService;
	}


	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}


	public UserService getUserService() {
		return userService;
	}


	public void setUserService(UserService userService) {
		this.userService = userService;
	}


	public List getNbggList() {
		return nbggList;
	}


	public void setNbggList(List nbggList) {
		this.nbggList = nbggList;
	}


	public Page getUndoWorkPage() {
		return undoWorkPage;
	}


	public void setUndoWorkPage(Page undoWorkPage) {
		this.undoWorkPage = undoWorkPage;
	}


	public int getCurPage() {
		return curPage;
	}


	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}


	public List getYwgnList() {
		return ywgnList;
	}


	public void setYwgnList(List ywgnList) {
		this.ywgnList = ywgnList;
	}
}
