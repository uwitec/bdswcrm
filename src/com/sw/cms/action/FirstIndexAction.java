package com.sw.cms.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.model.LoginInfo;
import com.sw.cms.model.Page;
import com.sw.cms.service.CgfkService;
import com.sw.cms.service.FirstIndexService;
import com.sw.cms.service.FysqService;
import com.sw.cms.service.LsdService;
import com.sw.cms.service.MenuService;
import com.sw.cms.service.UserService;
import com.sw.cms.service.XsdService;
import com.sw.cms.service.XxfbNbggService;
import com.sw.cms.service.BwlService;
import com.sw.cms.util.Constant;
import com.sw.cms.util.StringUtils;

public class FirstIndexAction extends BaseAction {
	
	private FirstIndexService firstIndexService;
	private XxfbNbggService xxfbNbggService;
	private BwlService bwlService;
	private MenuService menuService;
	private UserService userService;
	private LsdService lsdService;
	private XsdService xsdService;
	private CgfkService cgfkService;
	private FysqService fysqService;

	private List nbggList = new ArrayList();
	private List bwlList = new ArrayList();
	private List dckList = new ArrayList();
	private List drkList = new ArrayList();
	private List dspLsdList = new ArrayList();
	private List dspXsdList = new ArrayList();
	private List dspCgfkList = new ArrayList();
	private List dspFysqList = new ArrayList();
	
	private String isCkdRight = "0"; 
	private String isRkdRight = "0";
	private String isLsdSpRight = "0"; 
	private String isXsdSpRight = "0";
	private String isCgfkSpRight = "0";
	private String isFysqSpRight = "0";
	private int curPage = 1;
	
	
	/**
	 * ��ҳ��ʾ��
	 * @return
	 */
	public String listMain(){
		
		LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		String user_id = info.getUser_id();

		//�ڲ�����
		Page page = xxfbNbggService.getNbggList(1, 10);
		nbggList = page.getResults();

		//����¼
		Page bwlpage = bwlService.getBwlList(curPage, 10,user_id);
		bwlList = bwlpage.getResults();

		
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
			
			//����в������ⵥȨ��
			if(userService.isHasRightFuncs("FC0005", user_id)){
				isCkdRight = "1";
				dckList = firstIndexService.getDckdList();
			}
			
			//����в�����ⵥȨ��
			if(userService.isHasRightFuncs("FC0004", user_id)){
				isRkdRight = "1";
				drkList = firstIndexService.getDrkdList();
			}
			
			
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
			//����м۸�����Ȩ��
			if(userService.isHashJgspRight(user_id)){
				isXsdSpRight = "1";
				if(con.equals("")){
					con += "a.sp_type='4'";
				}else{
					con += " or a.sp_type='4'";
				}
			}
			//����г�������Ȩ��
			if(userService.isHasCespRight(user_id)){
				isXsdSpRight = "1";
				if(con.equals("")){
					con += "a.sp_type='3'";
				}else{
					con += " or a.sp_type='3'";
				}
			}
			//����г�������Ȩ��
			if(userService.isHasCqspRight(user_id)){
				isXsdSpRight = "1";
				if(con.equals("")){
					con += "a.sp_type='1'";
				}else{
					con += " or a.sp_type='1'";
				}
			}
			//����г���۸�����Ȩ��
			if(userService.isHasCeAndJgspRight(user_id)){
				isXsdSpRight = "1";
				if(con.equals("")){
					con += "a.sp_type='2'";
				}else{
					con += " or a.sp_type='2'";
				}
			}
			//���������۶���
			if(isXsdSpRight.equals("1")){
				dspXsdList = xsdService.getDspXsdList(con);
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
				isCgfkSpRight = "1";
				dspCgfkList = cgfkService.getCgfks(" and state='������'");
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
				isFysqSpRight = "1";
				dspFysqList = fysqService.getFysqList(" and (state='�ύ' or state='������')");
			}
			
			return SUCCESS;
		}catch(Exception e){
			log.error("ȡ�����б���Ϣ��������ԭ��" + e.getMessage());
			return ERROR;
		}
	}

	/**
	 * ��ʾ����¼
	 * @return
	 */
	public String listUndoMyWork(){
		int rowsPerPage = Constant.PAGE_SIZE2;
		LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		String user_id = info.getUser_id();

		//����¼
		Page page = bwlService.getBwlList(curPage, rowsPerPage,user_id);
		bwlList = page.getResults();

		return "success";
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

	public LsdService getLsdService() {
		return lsdService;
	}


	public void setLsdService(LsdService lsdService) {
		this.lsdService = lsdService;
	}


	public XsdService getXsdService() {
		return xsdService;
	}


	public void setXsdService(XsdService xsdService) {
		this.xsdService = xsdService;
	}


	public CgfkService getCgfkService() {
		return cgfkService;
	}


	public void setCgfkService(CgfkService cgfkService) {
		this.cgfkService = cgfkService;
	}


	public List getDckList() {
		return dckList;
	}


	public void setDckList(List dckList) {
		this.dckList = dckList;
	}


	public List getDrkList() {
		return drkList;
	}


	public void setDrkList(List drkList) {
		this.drkList = drkList;
	}


	public List getDspLsdList() {
		return dspLsdList;
	}


	public void setDspLsdList(List dspLsdList) {
		this.dspLsdList = dspLsdList;
	}


	public List getDspXsdList() {
		return dspXsdList;
	}


	public void setDspXsdList(List dspXsdList) {
		this.dspXsdList = dspXsdList;
	}


	public List getDspCgfkList() {
		return dspCgfkList;
	}


	public void setDspCgfkList(List dspCgfkList) {
		this.dspCgfkList = dspCgfkList;
	}


	public List getDspFysqList() {
		return dspFysqList;
	}


	public void setDspFysqList(List dspFysqList) {
		this.dspFysqList = dspFysqList;
	}


	public String getIsCkdRight() {
		return isCkdRight;
	}


	public void setIsCkdRight(String isCkdRight) {
		this.isCkdRight = isCkdRight;
	}


	public String getIsRkdRight() {
		return isRkdRight;
	}


	public void setIsRkdRight(String isRkdRight) {
		this.isRkdRight = isRkdRight;
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


	public String getIsCgfkSpRight() {
		return isCgfkSpRight;
	}


	public void setIsCgfkSpRight(String isCgfkSpRight) {
		this.isCgfkSpRight = isCgfkSpRight;
	}


	public String getIsFysqSpRight() {
		return isFysqSpRight;
	}


	public void setIsFysqSpRight(String isFysqSpRight) {
		this.isFysqSpRight = isFysqSpRight;
	}


	public FysqService getFysqService() {
		return fysqService;
	}


	public void setFysqService(FysqService fysqService) {
		this.fysqService = fysqService;
	}
	
	public List getBwlList() {
		return bwlList;
	}


	public void setBwlList(List bwlList) {
		this.bwlList = bwlList;
	}
	
	public BwlService getBwlService() {
		return bwlService;
	}


	public void setBwlService(BwlService bwlService) {
		this.bwlService = bwlService;
	}
}
