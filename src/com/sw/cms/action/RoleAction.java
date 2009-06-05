package com.sw.cms.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.model.Page;
import com.sw.cms.service.RoleService;
import com.sw.cms.util.Constant;
import com.sw.cms.util.ParameterUtility;
import com.sw.cms.util.StringUtils;

public class RoleAction extends BaseAction {
	
	private RoleService roleService;
	
	private String role_id = "";
	private String role_name = "";
	private int xh = 0;
	
	private Page rolePage;
	
	private String[] func_id;
	private List funcList = new ArrayList();
	private List roleFuncs = new ArrayList();
	
	
	
	public String list(){
		
		String con = "";
		if(!role_name.equals("")){
			con += " and role_name like'%" + role_name + "%'";
		}
		
		int curPage = ParameterUtility.getIntParameter(getRequest(), "curPage",1);
		int rowsPerPage =  Constant.PAGE_SIZE2;
		
		rolePage = roleService.getRoleList(con, curPage, rowsPerPage);
		
		return "success";
	}
	
	
	public String add(){
		return "success";
	}
	
	
	
	public String edit(){
		String id = ParameterUtility.getStringParameter(getRequest(), "id","");
		Map map = roleService.getRoleByID(id);
		if(map != null){
			role_id = (String)map.get("role_id");
			role_name = (String)map.get("role_name");
			xh = new Integer(StringUtils.nullToStr(map.get("xh"))).intValue();
		}
		return "success";
	}
	
	
	public String save(){
		roleService.saveRole(role_name, xh);
		return "success";
	}
	
	
	public String update(){
		roleService.updateRole(role_id, role_name, xh);
		return "success";
	}
	
	public String del(){
		String id = ParameterUtility.getStringParameter(getRequest(), "id","");
		roleService.delRoleByID(id);
		return "success";
	}
	
	/**
	 * 打开功能列表页面
	 * 为角色选择功能
	 * @return
	 */
	public String openFuns(){
		funcList = roleService.getAllFuncList();
		roleFuncs = roleService.getRoleFuncList(role_id);
		return "success";
	}
	
	
	/**
	 * 保存角色功能
	 * @return
	 */
	public String saveRoleFuncs(){
		roleService.saveRoleFuncs(role_id, func_id);
		getSession().setAttribute("MSG", "角色功能设定成功！");
		return "success";
	}
	

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	public String getRole_id() {
		return role_id;
	}

	public void setRole_id(String role_id) {
		this.role_id = role_id;
	}

	public String getRole_name() {
		return role_name;
	}

	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}

	public int getXh() {
		return xh;
	}

	public void setXh(int xh) {
		this.xh = xh;
	}
	
	public Page getRolePage() {
		return rolePage;
	}


	public String[] getFunc_id() {
		return func_id;
	}


	public void setFunc_id(String[] func_id) {
		this.func_id = func_id;
	}


	public List getFuncList() {
		return funcList;
	}


	public void setFuncList(List funcList) {
		this.funcList = funcList;
	}


	public List getRoleFuncs() {
		return roleFuncs;
	}


	public void setRoleFuncs(List roleFuncs) {
		this.roleFuncs = roleFuncs;
	}

}
