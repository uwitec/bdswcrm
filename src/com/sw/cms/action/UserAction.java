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
import com.sw.cms.service.SysInitSetService;
import com.sw.cms.service.UserService;
import com.sw.cms.service.XsdService;
import com.sw.cms.servlet.OnlineSessionAndApplication;
import com.sw.cms.util.Constant;
import com.sw.cms.util.ParameterUtility;
import com.sw.cms.util.StringUtils;

/**
 * <br>用户管理包括系统登陆
 * <br>同时存放员工信息,用标志is_sys_user来标志
 * <br>is_sys_user等于1为可登陆用户
 * <br>其它为员工信息
 * @author liyt
 *
 */

public class UserAction extends BaseAction {

	private UserService userService;
	private StoreService storeService;
	private MenuService menuService;
	private XsdService xsdService;
	private SjzdService sjzdService;
	private SysInitSetService sysInitSetService;
	
	private SysUser user = new SysUser();
	private Page userPage;
	
	private List depts = new ArrayList();
	private String[] positions;
	private List userList = new ArrayList();
	private List employList = new ArrayList();
	private Map spRightMap;
	private Map qzxlhRightMap;
	private Map styleMap;
	
	
	private String user_name;
	private String curPass;
	private String newPass;
	private String relogin_flage = "";
	
	
	private List userRoles = new ArrayList();
	private List roleList = new ArrayList();
	private List storeList = new ArrayList();
	
	private String user_id;
	
	private String sp_flag;
	private String[] role_id;
	private String style_flag;
	
	private String real_name = "";
	private String dept = "";
	private String position = "";
	private String zzzt = "";
	private String is_del = "0";
	
	private String orderName ="";
	private String orderType ="";
	
	private String cespRoles = "";
	private String jgspRoles = "";
	private String cqspRoles = "";
	
	private String cpy_name = "";
	private String logo_url = "";
	
	private int curPage = 1;
	
	private String paramValue = "";
	private String jsrTips = "";
	
	private int nums = 0;
	
	public int getNums() {
		return nums;
	}


	public void setNums(int nums) {
		this.nums = nums;
	}


	public String getJsrTips() {
		return jsrTips;
	}


	public void setJsrTips(String jsrTips) {
		this.jsrTips = jsrTips;
	}
	
	
	/**
	 * 转向登陆页面
	 * @return
	 */
	public String toLogin(){
		try{
			Map map = sysInitSetService.getSysLogo();
			
			if(map != null){
				cpy_name = StringUtils.nullToStr(map.get("cpy_name"));
			}
			
			return SUCCESS;
		}catch(Exception e){
			log.error("打开登录页面报错,原因:" + e);
			return ERROR;
		}
	}


	/**
	 * 用户登录
	 * @return
	 */
	public String login(){
		String user_name = ParameterUtility.getStringParameter(getRequest(), "user_name","");
		String password = ParameterUtility.getStringParameter(getRequest(), "password","");
		String authNums = ParameterUtility.getStringParameter(getRequest(), "authNums","");
		
		String checkNums = (String)getSession().getAttribute("RAND_AUTH_NUMS");
		getSession().removeAttribute("RAND_AUTH_NUMS");
		if(!authNums.equalsIgnoreCase(checkNums)){
			return "cancel";
		}
		
		
		Object obj = userService.getUserByNameAndPass(user_name, password);
		if(obj == null){
			return "cancel";
		}
		SysUser sysUser = (SysUser)obj;
		
		//保存登录信息
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
		
		//判断用户是否在其它地方登陆
		//没有登陆sessionId为""
		//登陆则返回其登陆的session id
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
		
		//用户在其它地方有登陆
		if(!sessionId.equals("")){
			if(relogin_flage.equals("1")){
				//如果是确认的强制登录，则清空其它登陆用户的session
				HttpSession session = OnlineSessionAndApplication.getSession(sessionId);
				if (session!=null){
					try{
						session.invalidate();
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			}else{
				//提醒用户可以强制登录
				this.saveMessage("用户已在其它地方登录，强制登陆原有登陆用户将退出系统！");
				return "reLogin";
			}
		}
		
		loginInfo.setSessionId(getSession().getId()); //登陆sessionID
		loginInfo.setLast_login_ip(getRequest().getRemoteAddr());//用户IP地址
		Date cdate = new Date();
		loginInfo.setLast_login_time(cdate);          //登陆时间
		
		userService.updateUserLastLogin(loginInfo.getUser_id(), cdate, getRequest().getRemoteAddr()); //更新登录信息
		
		getSession().setAttribute("LOGINUSER", loginInfo);
		getSession().setAttribute("onlineUserBindingListener", new OnlineSessionAndApplication(loginInfo));

		//判断登陆用户的类别
		if(loginInfo.getIs_dls().equals("1")){
			//如果是分销商则登陆分销商首页
			return "fxSuccess";
		}else if(loginInfo.getIs_dls().equals("2")){
			//如果是供应商则登陆供应商首页
			return "gysSuccess";
		}else{
			//其它用户登陆内网首页
			String agent = getRequest().getHeader("User-agent");
			log.info("用户浏览器信息：" + agent);
//			String sendUrl = "success";
//			try{
//				if(agent != null){
//					if(agent.indexOf("MSIE") != -1 && agent.indexOf("MSIE 9.0") == -1 && agent.indexOf("MSIE 6.0") == -1){
//						//是IE浏览器，但不是6.0,9.0的版本
//						sendUrl = "success";
//					}else{
//						sendUrl = "otherBrowserSuccess";
//					}
//				}
//			}catch(Exception e){
//				e.printStackTrace();
//			}
			return "otherBrowserSuccess";
			
		}
	}
	
	
	/**
	 * 用户登陆成功
	 * @return
	 */
	public String logined(){
		
		LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER"); //登陆用户信息
		
		Map map = sysInitSetService.getSysLogo();
		
		if(map != null){
			cpy_name = StringUtils.nullToStr(map.get("cpy_name"));
			logo_url = StringUtils.nullToStr(map.get("logo_url"));
		}
		
		//如果是代理商调用系统内部用户首页，提示错误
		if(info.getIs_dls().equals("1") || info.getIs_dls().equals("2")){
			return "error";
		}
		
		return "success";
	}
	
	
	/**
	 * 显示TOP
	 * @return
	 */
	public String showTop(){
		Map map = sysInitSetService.getSysLogo();
		
		if(map != null){
			cpy_name = StringUtils.nullToStr(map.get("cpy_name"));
			logo_url = StringUtils.nullToStr(map.get("logo_url"));
		}
		return "success";
	}
	
	
	/**
	 * 分销商登陆成功
	 * @return
	 */
	public String fxlogined(){
		Map map = sysInitSetService.getSysLogo();
		
		if(map != null){
			cpy_name = StringUtils.nullToStr(map.get("cpy_name"));
			logo_url = StringUtils.nullToStr(map.get("logo_url"));
		}
		return "success";
	}
	
	
	/**
	 * 供应商登陆成功
	 * @return
	 */
	public String gyslogined(){
		Map map = sysInitSetService.getSysLogo();
		
		if(map != null){
			cpy_name = StringUtils.nullToStr(map.get("cpy_name"));
			logo_url = StringUtils.nullToStr(map.get("logo_url"));
		}
		return "success";
	}	
	
	
	/**
	 * 取得用户列表
	 * @return
	 */
	public String list(){
		
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
		if(!zzzt.equals("")){
			con += " and zzzt='" + zzzt + "'";
		}
		if(!is_del.equals("")){
			con += " and is_del='" + is_del + "'";
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
	 * 打开添加页面
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
	 * 保存用户信息
	 * @return
	 */
	public String save(){
		int i = userService.getUserByName(user.getUser_name());
		if(i>0){
			getSession().setAttribute("MSG", "用户名已存在，请选用其它用户名！");
			employList = userService.getAllEmployeeList();
			return "input";
		}
		userService.saveUser(user);
		return "success";
	}
	
	
	/**
	 * 查询用户信息
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
	 * 更新用户信息
	 * @return
	 */
	public String update(){	
		userService.updateUser(user);
		return "success";
	}
	
	
	/**
	 * 删除用户信息
	 * @return
	 */
	public String del(){
		String user_id = ParameterUtility.getStringParameter(getRequest(), "user_id","");
		userService.delUser(user_id);
		return "success";
	}
	
	/**
	 * 还原删除的用户信息
	 * @return
	 */
	public String restore(){
		String user_id = ParameterUtility.getStringParameter(getRequest(), "user_id","");
		userService.restoreUser(user_id,"0");
		return "success";
	}
	
	/**
	 * 重置密码
	 * @return
	 */
	public String resetPass(){
		try{
			userService.updatePass(user_name, "111111");
			this.setMsg("密码重置成功，默认为：111111,请通知用户及时修改！");
			return SUCCESS;
		}catch(Exception e){
			this.setMsg("重置密码失败，请与管理员联系！");
			return ERROR;
		}
	}
	
	
	/**
	 * 修改用户密码
	 * @return
	 */
	public String savePass(){
		Object obj = userService.getUserByNameAndPass(user_name, curPass);
		if(obj == null){
			getSession().setAttribute("MSG", "原始密码输入不正确，修改失败！");
			return "cancel";
		}
		userService.updatePass(user_name, newPass);
		
		getSession().setAttribute("MSG", "密码修改成功！");
		return "success";
	}
	
	
	/**
	 * 打开修改密码页面
	 * @return
	 */
	public String changePass(){
		return "success";
	}
	
	
	/**
	 * 打开设定用户角色页面
	 * @return
	 */
	public String openRoles(){
		roleList = userService.getAllRoles();
		userRoles = userService.getUserRoles(user_id);
		return "success";
	}
	
	
	/**
	 * 保存用户角色
	 * @return
	 */
	public String saveUserRoles(){
		userService.saveUserRoles(user_id, role_id);
		getSession().setAttribute("MSG", "用户角色设定成功！");
		return "success";
	}
	
	/**
	 * 取系统所有可登录用户
	 * @return
	 */
	public String showOnlineUser(){
		userList = userService.getAllSysUserList();
		return "success";
	}
	
	
	/**
	 * 打开销售单审批权限设置页面
	 */
	public String openXsdSpRightRoles(){
		roleList = userService.getAllRoles();
		cespRoles = xsdService.getCespRoles();
		return "success";
	}
	
	
	/**
	 * 保存销售单审批角色列表
	 * @return
	 */
	public String saveXsdSpRightRoles(){
		userService.saveXsdSpRightRoles(role_id);
		getSession().setAttribute("MSG", "销售单超额审批角色设定成功！");
		return "success";
	}
	
	
	/**
	 * 打开价格审批权限设置页面
	 */
	public String openJgSpRightRoles(){
		roleList = userService.getAllRoles();
		jgspRoles = userService.getJgspRoles();
		return "success";
	}
	
	
	/**
	 * 保存价格审批角色列表
	 * @return
	 */
	public String saveJgSpRightRoles(){
		userService.saveJgSpRightRoles(role_id);
		getSession().setAttribute("MSG", "价格审批角色设定成功！");
		return "success";
	}
	
	
	/**
	 * 打开超期审批权限设置页面
	 * @return
	 */
	public String openCqSpRightRoles(){
		roleList = userService.getAllRoles();
		cqspRoles = userService.getCqspRoles();
		return "success";
	}
	
	
	/**
	 * 保存超期审批角色列表
	 * @return
	 */
	public String saveCqSpRightRoles(){
		userService.saveCqSpRightRoles(role_id);
		getSession().setAttribute("MSG", "超期审批角色设定成功！");
		return "success";
	}
	
	
	/**
	 * 打开采购付款审批权限设置页面
	 * @return
	 */
	public String openCgfkSpRight(){
		try{
			roleList = userService.getAllRoles();
			spRightMap = userService.getSpRight("采购付款");
			
			if(spRightMap != null){
				sp_flag = StringUtils.nullToStr(spRightMap.get("sp_flag"));
				role_id = (StringUtils.nullToStr(spRightMap.get("role_id"))).split(",");
			}
			return SUCCESS;
		}catch(Exception e){
			log.error("打开采购付款审批权限设置页面错误码，错误原因：" + e.getMessage());
			e.printStackTrace();
			return ERROR;
		}
	}
	
	
	/**
	 * 保存采购付款审批相关信息
	 * @return
	 */
	public String saveCgfkSpRight(){
		try{
			userService.saveSpRight(sp_flag, role_id,"采购付款");
			this.setMsg("保存采购付款审批设置成功！");
			return SUCCESS;
		}catch(Exception e){
			log.error("保存采购付款审批相关信息错误，错误原因：" + e.getMessage());
			e.printStackTrace();
			return ERROR;
		}
	}
	
	
	/**
	 * 打开费用申请审批权限设置页面
	 * @return
	 */
	public String openFysqSpRight(){
		try{
			roleList = userService.getAllRoles();
			spRightMap = userService.getSpRight("费用申请");
			
			if(spRightMap != null){
				sp_flag = StringUtils.nullToStr(spRightMap.get("sp_flag"));
				role_id = (StringUtils.nullToStr(spRightMap.get("role_id"))).split(",");
			}
			return SUCCESS;
		}catch(Exception e){
			log.error("打开费用申请审批权限设置页面错误码，错误原因：" + e.getMessage());
			e.printStackTrace();
			return ERROR;
		}
	}
	
	
	/**
	 * 保存费用申请审批相关信息
	 * @return
	 */
	public String saveFysqSpRight(){
		try{
			userService.saveSpRight(sp_flag, role_id,"费用申请");
			this.setMsg("保存费用申请审批设置成功！");
			return SUCCESS;
		}catch(Exception e){
			log.error("保存费用申请审批相关信息错误，错误原因：" + e.getMessage());
			e.printStackTrace();
			return ERROR;
		}
	}	
	
	
	/**
	 * 打开强制序列号设置页面
	 * @return
	 */
	public String openQzxlhRight(){
		try{
			qzxlhRightMap = userService.getQzxlhRight();
			
			if(qzxlhRightMap != null){
				sp_flag = StringUtils.nullToStr(qzxlhRightMap.get("sp_flag"));				
			}
			return SUCCESS;
		}catch(Exception e){
			log.error("打开强制序列号设置页面错误码，错误原因：" + e.getMessage());
			e.printStackTrace();
			return ERROR;
		}
	}
	
	
	/**
	 * 保存强制序列号设置相关信息
	 * @return
	 */
	public String saveQzxlhRight(){
		try{
			userService.saveQzxlhRight(sp_flag);
			this.setMsg("保存强制序列号设置成功！");
			return SUCCESS;
		}catch(Exception e){
			log.error("保存强制序列号设置相关信息错误，错误原因：" + e.getMessage());
			e.printStackTrace();
			return ERROR;
		}
	}
	
	
	/**
	 * 打开报表样式设置页面
	 * @return
	 */
	public String openStyle(){
		try{
			styleMap = userService.getStyle();
			
			if(styleMap != null){
				style_flag = StringUtils.nullToStr(styleMap.get("style_flag"));				
			}
			return SUCCESS;
		}catch(Exception e){
			log.error("打开报表样式设置页面错误码，错误原因：" + e.getMessage());
			e.printStackTrace();
			return ERROR;
		}
	}
	
	
	/**
	 * 保存报表样式设置相关信息
	 * @return
	 */
	public String saveStyle(){
		try{
			userService.saveStyle(style_flag);
			this.setMsg("保存报表样式设置成功！");
			return SUCCESS;
		}catch(Exception e){
			log.error("保存报表样式设置相关信息错误，错误原因：" + e.getMessage());
			e.printStackTrace();
			return ERROR;
		}
	}
	
	/**
	 * 查询ajax经手人提示信息
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
			log.error("Ajax查询经手人信息出错，错误原因：" + e.getMessage());
			return "error";
		}
	}
	
	
	/**
	 * 取所有经手人信息
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
			log.error("Ajax查询经手人信息出错，错误原因：" + e.getMessage());
			return "error";
		}
	}
	
	/**
	 * 检查用户数量是否超出了限制，返回true超出限制，返回false没有超出限制
	 */
	public void checkUserLimit(){
		try{
			int sysUserLimit = Constant.SYS_USER_NUMS;
			if(sysUserLimit == 0){
				//limit==0不限制用户数
				this.writeStringToResponse("false");
			}else{
				int curUserNums = 0; //当前系统有效用户数量				
				List userList = userService.getAllSysUserList();
				if(userList != null) curUserNums = userList.size();
				
				if(curUserNums >= sysUserLimit){
					//超出用户限制
					this.writeStringToResponse("true");
				}else{
					//没有超出限制，可以继续添加用户
					this.writeStringToResponse("false");
				}
			}
		}catch(Exception e){
			e.printStackTrace();
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


	public String getCpy_name() {
		return cpy_name;
	}


	public void setCpy_name(String cpy_name) {
		this.cpy_name = cpy_name;
	}


	public String getLogo_url() {
		return logo_url;
	}


	public void setLogo_url(String logo_url) {
		this.logo_url = logo_url;
	}


	public SysInitSetService getSysInitSetService() {
		return sysInitSetService;
	}


	public void setSysInitSetService(SysInitSetService sysInitSetService) {
		this.sysInitSetService = sysInitSetService;
	}

	public String getSp_flag() {
		return sp_flag;
	}


	public void setSp_flag(String spFlag) {
		sp_flag = spFlag;
	}

	
	public String getStyle_flag() {
		return style_flag;
	}


	public void setStyle_flag(String styleflag) {
		style_flag = styleflag;
	}

	public Map getSpRightMap() {
		return spRightMap;
	}


	public void setSpRightMap(Map spRightMap) {
		this.spRightMap = spRightMap;
	}
	
	public Map getStyleMap() {
		return styleMap;
	}


	public void setStyleMap(Map styleMap) {
		this.styleMap = styleMap;
	}
	
	public Map getQzxlhRightMap() {
		return qzxlhRightMap;
	}


	public void setQzxlhRightMap(Map qzxlhRightMap) {
		this.qzxlhRightMap = qzxlhRightMap;
	}


	public String getZzzt() {
		return zzzt;
	}


	public void setZzzt(String zzzt) {
		this.zzzt = zzzt;
	}


	public String getIs_del() {
		return is_del;
	}


	public void setIs_del(String isDel) {
		is_del = isDel;
	}
}
