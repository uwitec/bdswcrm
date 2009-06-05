package com.sw.cms.action;

import java.util.ArrayList;
import java.util.List;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.service.DeptService;
import com.sw.cms.service.GfReportService;
import com.sw.cms.service.UserService;

/**
 * ����ͳ��
 * @author liyt
 *
 */
public class GfReportAction extends BaseAction {
	
	private static final long serialVersionUID = 8393802998137923439L;
	
	private GfReportService gfReportService;
	private UserService userService;
	private DeptService deptService;
	
	private List userList = new ArrayList();
	private List deptList = new ArrayList();
	private List resultList = new ArrayList();
	private List resultMxList = new ArrayList();
	
	private String start_date = "";
	private String end_date = "";
	private String dept_id = "";
	private String xsry_id = "";
	private String flag = "";
	
	
	/**
	 * ��ѯ����ҳ��
	 * @return
	 */
	public String showCondition(){
		userList = userService.getAllEmployeeList();  //ҵ��Ա�б�
		deptList = deptService.getDepts();   //�����б�
		return "success";
	}
	
	
	/**
	 * ͳ�ƽ��
	 * @return
	 */
	public String getResults(){
		resultList = gfReportService.getGfReportByCon(start_date, end_date, dept_id, xsry_id,flag);
		return "success";
	}
	
	
	/**
	 * ͳ����ϸ�б�
	 * @return
	 */
	public String getMxResults(){
		resultMxList = gfReportService.getMxList(start_date, end_date, xsry_id);
		return "success";
	}
	
	
	public String getDept_id() {
		return dept_id;
	}
	public void setDept_id(String dept_id) {
		this.dept_id = dept_id;
	}
	public List getDeptList() {
		return deptList;
	}
	public void setDeptList(List deptList) {
		this.deptList = deptList;
	}
	public DeptService getDeptService() {
		return deptService;
	}
	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}
	public String getEnd_date() {
		return end_date;
	}
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
	public GfReportService getGfReportService() {
		return gfReportService;
	}
	public void setGfReportService(GfReportService gfReportService) {
		this.gfReportService = gfReportService;
	}
	public String getStart_date() {
		return start_date;
	}
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}
	public List getUserList() {
		return userList;
	}
	public void setUserList(List userList) {
		this.userList = userList;
	}
	public UserService getUserService() {
		return userService;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	public String getXsry_id() {
		return xsry_id;
	}
	public void setXsry_id(String xsry_id) {
		this.xsry_id = xsry_id;
	}
	public List getResultList() {
		return resultList;
	}
	public void setResultList(List resultList) {
		this.resultList = resultList;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}


	public List getResultMxList() {
		return resultMxList;
	}


	public void setResultMxList(List resultMxList) {
		this.resultMxList = resultMxList;
	}
	

}
