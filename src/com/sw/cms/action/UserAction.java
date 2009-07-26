package com.sw.cms.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.model.LoginInfo;
import com.sw.cms.model.Page;
import com.sw.cms.model.SysUser;
import com.sw.cms.service.MenuService;
import com.sw.cms.service.SjzdService;
import com.sw.cms.service.StoreService;
import com.sw.cms.service.UserService;
import com.sw.cms.service.XsdService;
import com.sw.cms.servlet.OnlineSessionAndApplication;
import com.sw.cms.util.Constant;
import com.sw.cms.util.ParameterUtility;
import com.sw.cms.util.StringUtils;

/**
 * <br>�û��������ϵͳ��½
 * <br>ͬʱ���Ա����Ϣ,�ñ�־is_sys_user����־
 * <br>is_sys_user����1Ϊ�ɵ�½�û�
 * <br>����ΪԱ����Ϣ
 * @author liyt
 *
 */

public class UserAction extends BaseAction {

	private UserService userService;
	private StoreService storeService;
	private MenuService menuService;
	private XsdService xsdService;
	private SjzdService sjzdService;
	
	private SysUser user = new SysUser();
	private Page userPage;
	
	private List depts = new ArrayList();
	private String[] positions;
	private List userList = new ArrayList();
	private List employList = new ArrayList();
	
	private String user_name;
	private String curPass;
	private String newPass;
	private String relogin_flage = "";
	
	
	private List userRoles = new ArrayList();
	private List roleList = new ArrayList();
	private List storeList = new ArrayList();
	
	private String user_id;
	private String[] role_id;
	
	private String real_name = "";
	private String dept = "";
	private String position = "";
	
	private String orderName ="";
	private String orderType ="";
	
	private String cespRoles = "";
	private String jgspRoles = "";
	private String cqspRoles = "";
	
	private int curPage = 1;
	
	private String paramValue = "";
	private String jsrTips = "";
	
	public String getJsrTips() {
		return jsrTips;
	}


	public void setJsrTips(String jsrTips) {
		this.jsrTips = jsrTips;
	}


	/**
	 * �û���¼
	 * @return
	 */
	public String login(){
		String user_name = ParameterUtility.getStringParameter(getRequest(), "user_name","");
		String password = ParameterUtility.getStringParameter(getRequest(), "password","");
		String authNums = ParameterUtility.getStringParameter(getRequest(), "authNums","");
		
		String checkNums = (String)getSession().getAttribute("RAND_AUTH_NUMS");
		getSession().removeAttribute("RAND_AUTH_NUMS");
		if(!authNums.equals(checkNums)){
			return "cancel";
		}
		
		
		Object obj = userService.getUserByNameAndPass(user_name, password);
		if(obj == null){
			return "cancel";
		}
		SysUser sysUser = (SysUser)obj;
		
		//�����¼��Ϣ
		LoginInfo loginInfo = new LoginInfo();
		loginInfo.setUser_id(sysUser.getUser_id());
		loginInfo.setUser_name(sysUser.getUser_name());
		loginInfo.setReal_name(sysUser.getReal_name());
		loginInfo.setDept(sysUser.getDept());
		loginInfo.setPosition(sysUser.getPosition());
		loginInfo.setIs_dls(sysUser.getIs_dls());
		loginInfo.setClient_name(sysUser.getClient_name());
		
		List list = userService.getUserRoles(sysUser.getUser_id());
		loginInfo.setUserRoles(list);
		
		//�ж��û��Ƿ��������ط���½
		//û�е�½sessionIdΪ""
		//��½�򷵻����½��session id
		String sessionId = "";
		List onlineUserList = (List) getApplication().getAttribute("onlineUserList");
		if(onlineUserList != null && onlineUserList.size()>0){
			for(int i=0;i<onlineUserList.size();i++){
				LoginInfo info = (LoginInfo)onlineUserList.get(i);
				if(info.getUser_id().equals(sysUser.getUser_id())){
					sessionId = info.getSessionId();
					break;
				}
			}
		}
		
		//�û��������ط��е�½
		if(!sessionId.equals("")){
			if(relogin_flage.equals("1")){
				//�����ȷ�ϵ�ǿ�Ƶ�¼�������������½�û���session
				HttpSession session = OnlineSessionAndApplication.getSession(sessionId);
				if (session!=null){
					session.invalidate();
				}
			}else{
				//�����û�����ǿ�Ƶ�¼
				this.saveMessage("�û����������ط���¼��ǿ�Ƶ�½ԭ�е�½�û����˳�ϵͳ��");
				return "reLogin";
			}
		}
		
		loginInfo.setSessionId(getSession().getId()); //��½sessionID
		loginInfo.setLast_login_ip(getRequest().getRemoteAddr());//�û�IP��ַ
		Date cdate = new Date();
		loginInfo.setLast_login_time(cdate);          //��½ʱ��
		
		userService.updateUserLastLogin(loginInfo.getUser_id(), cdate, getRequest().getRemoteAddr()); //���µ�¼��Ϣ
		
		getSession().setAttribute("LOGINUSER", loginInfo);
		getSession().setAttribute("onlineUserBindingListener", new OnlineSessionAndApplication(loginInfo));

		//�жϵ�½�û������
		if(loginInfo.getIs_dls().equals("1")){
			//����Ƿ��������½��������ҳ
			return "fxSuccess";
		}else if(loginInfo.getIs_dls().equals("2")){
			//����ǹ�Ӧ�����½��Ӧ����ҳ
			return "gysSuccess";
		}else{
			//�����û���½������ҳ
			return "success";
		}
	}
	
	
	/**
	 * �û���½�ɹ�
	 * @return
	 */
	public String logined(){
		
		LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER"); //��½�û���Ϣ
		
		//����Ǵ����̵���ϵͳ�ڲ��û���ҳ����ʾ����
		if(info.getIs_dls().equals("1") || info.getIs_dls().equals("2")){
			return "error";
		}
		
		return "success";
	}
	
	/**
	 * �����̵�½�ɹ�
	 * @return
	 */
	public String fxlogined(){
		return "success";
	}
	
	
	/**
	 * ��Ӧ�̵�½�ɹ�
	 * @return
	 */
	public String gyslogined(){
		return "success";
	}	
	
	
	/**
	 * ȡ���û��б�
	 * @return
	 */
	public String list(){
		String real_name = ParameterUtility.getStringParameter(getRequest(), "real_name","");
		String dept = ParameterUtility.getStringParameter(getRequest(), "dept",""); 
		String position = ParameterUtility.getStringParameter(getRequest(), "position",""); 
		
		int curPage = ParameterUtility.getIntParameter(getRequest(), "curPage",1);
		int rowsPerPage =  Constant.PAGE_SIZE2;
		
		String con = "";
		if(!real_name.equals("")){
			con += " and real_name like'%" + real_name + "%'";
		}
		if(!dept.equals("")){
			con += " and dept='" + dept + "'";
		}
		if(!position.equals("")){
			con += " and position='" + position + "'";
		}		
		if(orderName.equals("")){
			orderName = "xh";
		}
		if(orderType.equals("")){
			orderType = "desc";
		}
		
		con += " order by " + orderName + " " + orderType;
		userPage = userService.getUserList(con, curPage, rowsPerPage);
		
		depts = userService.getAllDeptList();
		positions = sjzdService.getSjzdXmxxByZdId("SJZD_ZWXX");
		
		return "success";
	}
	
	
	/**
	 * �����ҳ��
	 * @return
	 */
	public String add(){
		depts = userService.getAllDeptList();
		positions = sjzdService.getSjzdXmxxByZdId("SJZD_ZWXX");
		storeList = storeService.getAllStoreList();
		employList = userService.getAllEmployeeList();
		return "success";
	}
	
	
	/**
	 * �����û���Ϣ
	 * @return
	 */
	public String save(){
		int i = userService.getUserByName(user.getUser_name());
		if(i>0){
			getSession().setAttribute("MSG", "�û����Ѵ��ڣ���ѡ�������û�����");
			employList = userService.getAllEmployeeList();
			return "input";
		}
		userService.saveUser(user);
		return "success";
	}
	
	
	/**
	 * ��ѯ�û���Ϣ
	 * @return
	 */
	public String edit(){
		String user_id = ParameterUtility.getStringParameter(getRequest(), "user_id","");
		user = (SysUser)userService.getUser(user_id);
		depts = userService.getAllDeptList();
		positions = sjzdService.getSjzdXmxxByZdId("SJZD_ZWXX");
		storeList = storeService.getAllStoreList();
		employList = userService.getAllEmployeeList();
		return "success";
	}
	
	/**
	 * �����û���Ϣ
	 * @return
	 */
	public String update(){	
		userService.updateUser(user);
		return "success";
	}
	
	
	/**
	 * ɾ���û���Ϣ
	 * @return
	 */
	public String del(){
		String user_id = ParameterUtility.getStringParameter(getRequest(), "user_id","");
		userService.delUser(user_id);
		return "success";
	}
	
	
	/**
	 * �޸��û�����
	 * @return
	 */
	public String savePass(){
		Object obj = userService.getUserByNameAndPass(user_name, curPass);
		if(obj == null){
			getSession().setAttribute("MSG", "ԭʼ�������벻��ȷ���޸�ʧ�ܣ�");
			return "cancel";
		}
		userService.updatePass(user_name, newPass);
		
		getSession().setAttribute("MSG", "�����޸ĳɹ���");
		return "success";
	}
	
	
	/**
	 * ���޸�����ҳ��
	 * @return
	 */
	public String changePass(){
		return "success";
	}
	
	
	/**
	 * ���趨�û���ɫҳ��
	 * @return
	 */
	public String openRoles(){
		roleList = userService.getAllRoles();
		userRoles = userService.getUserRoles(user_id);
		return "success";
	}
	
	
	/**
	 * �����û���ɫ
	 * @return
	 */
	public String saveUserRoles(){
		userService.saveUserRoles(user_id, role_id);
		getSession().setAttribute("MSG", "�û���ɫ�趨�ɹ���");
		return "success";
	}
	
	/**
	 * ȡϵͳ���пɵ�¼�û�
	 * @return
	 */
	public String showOnlineUser(){
		userList = userService.getAllSysUserList();
		return "success";
	}
	
	
	/**
	 * �����۵�����Ȩ������ҳ��
	 */
	public String openXsdSpRightRoles(){
		roleList = userService.getAllRoles();
		cespRoles = xsdService.getCespRoles();
		return "success";
	}
	
	
	/**
	 * �������۵�������ɫ�б�
	 * @return
	 */
	public String saveXsdSpRightRoles(){
		userService.saveXsdSpRightRoles(role_id);
		getSession().setAttribute("MSG", "���۵�����������ɫ�趨�ɹ���");
		return "success";
	}
	
	
	/**
	 * �򿪼۸�����Ȩ������ҳ��
	 */
	public String openJgSpRightRoles(){
		roleList = userService.getAllRoles();
		jgspRoles = userService.getJgspRoles();
		return "success";
	}
	
	
	/**
	 * ����۸�������ɫ�б�
	 * @return
	 */
	public String saveJgSpRightRoles(){
		userService.saveJgSpRightRoles(role_id);
		getSession().setAttribute("MSG", "�۸�������ɫ�趨�ɹ���");
		return "success";
	}
	
	
	/**
	 * �򿪳�������Ȩ������ҳ��
	 * @return
	 */
	public String openCqSpRightRoles(){
		roleList = userService.getAllRoles();
		cqspRoles = userService.getCqspRoles();
		return "success";
	}
	
	
	/**
	 * ���泬��������ɫ�б�
	 * @return
	 */
	public String saveCqSpRightRoles(){
		userService.saveCqSpRightRoles(role_id);
		getSession().setAttribute("MSG", "����������ɫ�趨�ɹ���");
		return "success";
	}
	
	
	/**
	 * ��ѯajax��������ʾ��Ϣ
	 * @return
	 */
	public String getLikeJsrInfo(){
		try{
			if(paramValue.equals("")){
				return "success";
			}
			List jsrs = userService.getUserListAjaxTip(paramValue);
			if(jsrs != null && jsrs.size() > 0){
				for(int i=0;i<jsrs.size();i++){
					Map jsrMap = (Map)jsrs.get(i);
					if(jsrTips.equals("")){
						jsrTips = StringUtils.nullToStr(jsrMap.get("user_id")) + "$" + StringUtils.nullToStr(jsrMap.get("real_name"));
					}else{
						jsrTips +=  "%" + StringUtils.nullToStr(jsrMap.get("user_id")) + "$" + StringUtils.nullToStr(jsrMap.get("real_name"));
					}
				}
			}
			return "success";
		}catch(Exception e){
			log.error("Ajax��ѯ��������Ϣ��������ԭ��" + e.getMessage());
			return "error";
		}
	}
	
	
	/**
	 * ȡ���о�������Ϣ
	 * @return
	 */
	public String getAllJsrInfo(){
		try{
			List jsrs = userService.getUserListAjaxTip("");
			if(jsrs != null && jsrs.size() > 0){
				for(int i=0;i<jsrs.size();i++){
					Map jsrMap = (Map)jsrs.get(i);
					if(jsrTips.equals("")){
						jsrTips = StringUtils.nullToStr(jsrMap.get("user_id")) + "$" + StringUtils.nullToStr(jsrMap.get("real_name"));
					}else{
						jsrTips +=  "%" + StringUtils.nullToStr(jsrMap.get("user_id")) + "$" + StringUtils.nullToStr(jsrMap.get("real_name"));
					}
				}
			}
			return "success";
		}catch(Exception e){
			log.error("Ajax��ѯ��������Ϣ��������ԭ��" + e.getMessage());
			return "error";
		}
	}
	
	
	public SysUser getUser() {
		return user;
	}
	public void setUser(SysUser user) {
		this.user = user;
	}
	public Page getUserPage() {
		return userPage;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public List getDepts() {
		return depts;
	}
	
	public List getUserList() {
		return userList;
	}

	public String getNewPass() {
		return newPass;
	}

	public void setNewPass(String newPass) {
		this.newPass = newPass;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getCurPass() {
		return curPass;
	}

	public void setCurPass(String curPass) {
		this.curPass = curPass;
	}

	public List getRoleList() {
		return roleList;
	}

	public void setRoleList(List roleList) {
		this.roleList = roleList;
	}

	public List getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(List userRoles) {
		this.userRoles = userRoles;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String[] getRole_id() {
		return role_id;
	}

	public void setRole_id(String[] role_id) {
		this.role_id = role_id;
	}

	public int getCurPage() {
		return curPage;
	}

	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getOrderName() {
		return orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getReal_name() {
		return real_name;
	}

	public void setReal_name(String real_name) {
		this.real_name = real_name;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setDepts(List depts) {
		this.depts = depts;
	}
	
	public void setUserList(List userList) {
		this.userList = userList;
	}

	public void setUserPage(Page userPage) {
		this.userPage = userPage;
	}

	public StoreService getStoreService() {
		return storeService;
	}

	public void setStoreService(StoreService storeService) {
		this.storeService = storeService;
	}

	public List getStoreList() {
		return storeList;
	}

	public void setStoreList(List storeList) {
		this.storeList = storeList;
	}

	public String getRelogin_flage() {
		return relogin_flage;
	}

	public void setRelogin_flage(String relogin_flage) {
		this.relogin_flage = relogin_flage;
	}

	public MenuService getMenuService() {
		return menuService;
	}

	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}

	public String getCespRoles() {
		return cespRoles;
	}

	public void setCespRoles(String cespRoles) {
		this.cespRoles = cespRoles;
	}

	public XsdService getXsdService() {
		return xsdService;
	}

	public void setXsdService(XsdService xsdService) {
		this.xsdService = xsdService;
	}

	public String getJgspRoles() {
		return jgspRoles;
	}

	public void setJgspRoles(String jgspRoles) {
		this.jgspRoles = jgspRoles;
	}

	public String getCqspRoles() {
		return cqspRoles;
	}

	public void setCqspRoles(String cqspRoles) {
		this.cqspRoles = cqspRoles;
	}

	public String[] getPositions() {
		return positions;
	}

	public void setPositions(String[] positions) {
		this.positions = positions;
	}

	public SjzdService getSjzdService() {
		return sjzdService;
	}

	public void setSjzdService(SjzdService sjzdService) {
		this.sjzdService = sjzdService;
	}


	public List getEmployList() {
		return employList;
	}


	public void setEmployList(List employList) {
		this.employList = employList;
	}


	public String getParamValue() {
		return paramValue;
	}


	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}
	
}
