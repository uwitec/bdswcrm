package com.sw.cms.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.model.LoginInfo;
import com.sw.cms.model.Page;
import com.sw.cms.service.FirstIndexService;
import com.sw.cms.service.LsdService;
import com.sw.cms.service.MenuService;
import com.sw.cms.service.UserService;
import com.sw.cms.service.XsdService;
import com.sw.cms.service.XxfbNbggService;

public class FirstIndexAction extends BaseAction {
	
	private FirstIndexService firstIndexService;
	private XxfbNbggService xxfbNbggService;
	private MenuService menuService;
	private LsdService lsdService;
	private XsdService xsdService;
	private UserService userService;
	
	private List dckList = new ArrayList();
	private List drkList = new ArrayList();
	private List cqysList = new ArrayList();
	private List cqyfList = new ArrayList();
	private List kcxxList = new ArrayList();
	private List nbggList = new ArrayList();
	private String isLsdSpRight = "0"; 
	private String isXsdSpRight = "0";
	
	private List dspLsdList = new ArrayList();
	private List dspXsdList = new ArrayList();
	
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
		
		
		//��������ⵥ
		dckList = firstIndexService.getDckdList();
		
		//�������ⵥ
		drkList = firstIndexService.getDrkdList();
		
		
		//���������۵�
		List list = userService.getJgspUsers();		//���м۸�����Ȩ�޵��û��б�
		if(list != null && list.size() > 0){
			for(int i=0;i<list.size();i++){
				Map map = (Map)list.get(i);
				
				String cur_user = (String)map.get("user_id");
				if(cur_user.equals(user_id)){
					isLsdSpRight = "1";
					dspLsdList = lsdService.getDspLsdList(); //���������۵��б�
					break;
				}
			}
		}
		
		String con = "";		
		list = userService.getCqspUsers(); //���������û��б�
		if(list != null && list.size() > 0){
			for(int i=0;i<list.size();i++){
				Map map = (Map)list.get(i);
				String cur_user = (String)map.get("user_id");
				if(cur_user.equals(user_id)){
					isXsdSpRight = "1";
					
					con += "a.sp_type='1'";
					
					break;
				}
			}
		}
		
		list = userService.getCeAndJgSpUsers(); //����ҵ����޼�
		if(list != null && list.size() > 0){
			for(int i=0;i<list.size();i++){
				Map map = (Map)list.get(i);
				String cur_user = (String)map.get("user_id");
				if(cur_user.equals(user_id)){
					isXsdSpRight = "1";
					if(con.equals("")){
						con += "a.sp_type='2'";
					}else{
						con += " or a.sp_type='2'";
					}
					break;
				}
			}
		}
		
		list = userService.getCespUsers();  //��������
		if(list != null && list.size() > 0){
			for(int i=0;i<list.size();i++){
				Map map = (Map)list.get(i);
				String cur_user = (String)map.get("user_id");
				if(cur_user.equals(user_id)){
					isXsdSpRight = "1";
					if(con.equals("")){
						con += "a.sp_type='3'";
					}else{
						con += " or a.sp_type='3'";
					}
					break;
				}
			}
		}
		
		list = userService.getJgspUsers();  //�����޼�����
		if(list != null && list.size() > 0){
			for(int i=0;i<list.size();i++){
				Map map = (Map)list.get(i);
				String cur_user = (String)map.get("user_id");
				if(cur_user.equals(user_id)){
					isXsdSpRight = "1";
					if(con.equals("")){
						con += "a.sp_type='4'";
					}else{
						con += " or a.sp_type='4'";
					}
					break;
				}
			}
		}
		
		//���������۶���
		if(isXsdSpRight.equals("1")){
			dspXsdList = xsdService.getDspXsdList(con);
		}
		
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
	
	
	public List getCqyfList() {
		return cqyfList;
	}
	public void setCqyfList(List cqyfList) {
		this.cqyfList = cqyfList;
	}
	public List getCqysList() {
		return cqysList;
	}
	public void setCqysList(List cqysList) {
		this.cqysList = cqysList;
	}
	public List getDckList() {
		return dckList;
	}
	public void setDckList(List dckList) {
		this.dckList = dckList;
	}
	public FirstIndexService getFirstIndexService() {
		return firstIndexService;
	}
	public void setFirstIndexService(FirstIndexService firstIndexService) {
		this.firstIndexService = firstIndexService;
	}
	public List getKcxxList() {
		return kcxxList;
	}
	public void setKcxxList(List kcxxList) {
		this.kcxxList = kcxxList;
	}
	public XxfbNbggService getXxfbNbggService() {
		return xxfbNbggService;
	}
	public void setXxfbNbggService(XxfbNbggService xxfbNbggService) {
		this.xxfbNbggService = xxfbNbggService;
	}
	public List getNbggList() {
		return nbggList;
	}
	public void setNbggList(List nbggList) {
		this.nbggList = nbggList;
	}
	public MenuService getMenuService() {
		return menuService;
	}
	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}
	public List getYwgnList() {
		return ywgnList;
	}
	public void setYwgnList(List ywgnList) {
		this.ywgnList = ywgnList;
	}
	public List getDspLsdList() {
		return dspLsdList;
	}
	public void setDspLsdList(List dspLsdList) {
		this.dspLsdList = dspLsdList;
	}
	public LsdService getLsdService() {
		return lsdService;
	}
	public void setLsdService(LsdService lsdService) {
		this.lsdService = lsdService;
	}
	public UserService getUserService() {
		return userService;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	public String getIsLsdSpRight() {
		return isLsdSpRight;
	}
	public void setIsLsdSpRight(String isLsdSpRight) {
		this.isLsdSpRight = isLsdSpRight;
	}
	public String getIsXsdSpRight() {
		return isXsdSpRight;
	}
	public void setIsXsdSpRight(String isXsdSpRight) {
		this.isXsdSpRight = isXsdSpRight;
	}
	public List getDspXsdList() {
		return dspXsdList;
	}
	public void setDspXsdList(List dspXsdList) {
		this.dspXsdList = dspXsdList;
	}
	public XsdService getXsdService() {
		return xsdService;
	}
	public void setXsdService(XsdService xsdService) {
		this.xsdService = xsdService;
	}


	public List getDrkList() {
		return drkList;
	}


	public void setDrkList(List drkList) {
		this.drkList = drkList;
	}
}
