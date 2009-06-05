package com.sw.cms.action;

import java.util.Iterator;
import java.util.List;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.model.Dept;
import com.sw.cms.service.DeptService;

public class DeptAction extends BaseAction {
	
	private DeptService deptService;
	
	private Dept dept = new Dept();
	private String dept_id = "";
	private String parent_id = "";
	private String strTree = "";
	
	
	/**
	 * 取生成树的字符串
	 * @return
	 */
	public String list(){
		List results = deptService.getDepts();
		
		Iterator it = results.iterator();
		
		while(it.hasNext()){
			Dept info = (Dept)it.next();
			
			strTree += "add_item(\"" + info.getDept_id() + "\",\"" + info.getParent_id() + "\",\"" + info.getDept_name() + "\",\"\",\"\",\"listEmployee.html\",\"right\");\n";
		}
		return "success";
	}
	
	/**
	 * 打开编辑页面
	 * @return
	 */
	public String edit(){
		if(!dept_id.equals("")){
			dept = deptService.editDept(dept_id);
		}
		if(!parent_id.equals("")){
			dept.setParent_id(parent_id);
		}
		return "success";
	}
	
	/**
	 * 更新部门信息
	 * @return
	 */
	public String update(){
		deptService.updateDept(dept);
		return "success";
	}
	
	/**
	 * 删除部门信息
	 * @return
	 */
	public String del(){
		deptService.delDept(dept_id);
		return "success";
	}
	
	public Dept getDept() {
		return dept;
	}
	public void setDept(Dept dept) {
		this.dept = dept;
	}
	public String getDept_id() {
		return dept_id;
	}
	public void setDept_id(String dept_id) {
		this.dept_id = dept_id;
	}
	public DeptService getDeptService() {
		return deptService;
	}
	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}
	public String getStrTree() {
		return strTree;
	}
	public void setStrTree(String strTree) {
		this.strTree = strTree;
	}

	public String getParent_id() {
		return parent_id;
	}

	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}

}
