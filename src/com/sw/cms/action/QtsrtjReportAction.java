package com.sw.cms.action;

import java.util.ArrayList;
import java.util.List;

import com.sw.cms.action.base.BaseAction;

import com.sw.cms.service.QtsrtjReportService;
import com.sw.cms.service.SjzdService;
import com.sw.cms.service.UserService;
import com.sw.cms.model.Accounts;
import com.sw.cms.model.SjzdXmxx;

public class QtsrtjReportAction extends BaseAction {
	
	private QtsrtjReportService qtsrtjReportService;
	private UserService userService;
	private SjzdService sjzdService;
	
	private List userList = new ArrayList();
	private List deptList = new ArrayList();
	private String[] srlxs;
	private SjzdXmxx sjzdXmxx;
	private List reustls = new ArrayList();
	
	private String start_date = "";
	private String end_date = "";
	private String type = "";
	private String srlx = "";
	private int dj;
	
	/**
	 * 查询条件页面
	 * @return
	 */
	public String showCondition(){
		userList = userService.getAllEmployeeList();  //业务员列表
		srlxs = sjzdService.getSjzdXmxxByZdId("SJZD_SRLX");  //收入类型
		return "success";
	}
	
	/**
	 * 收入分类汇总统计结果
	 * @return
	 */
	
	public String getResutls(){
		reustls = qtsrtjReportService.getQtsrtjResult(start_date, end_date, type);
		return SUCCESS;
	}
	
	/**
	 * 收入分类统计结果明细
	 * @return
	 */
	public String getResultMx(){
		String srlx=sjzdService.getZdxm(type).getXm_name();
		reustls = qtsrtjReportService.getQtsrtjList(start_date, end_date,srlx);
		return SUCCESS;
	}
	
	public List getDeptList() {
		return deptList;
	}
	public void setDeptList(List deptList) {
		this.deptList = deptList;
	}
	
	public QtsrtjReportService getQtsrtjReportService() {
		return qtsrtjReportService;
	}
	public void setQtsrtjReportService(QtsrtjReportService qtsrtjReportService) {
		this.qtsrtjReportService = qtsrtjReportService;
	}
	public SjzdService getSjzdService() {
		return sjzdService;
	}
	public void setSjzdService(SjzdService sjzdService) {
		this.sjzdService = sjzdService;
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

	
	public String getEnd_date() {
		return end_date;
	}

	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String[] getSrlxs() {
		return srlxs;
	}

	public void setSrlxs(String[] srlxs) {
		this.srlxs = srlxs;
	}

	public String getStart_date() {
		return start_date;
	}

	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}

	public List getReustls() {
		return reustls;
	}

	public void setReustls(List reustls) {
		this.reustls = reustls;
	}

	public int getDj() {
		return dj;
	}

	public void setDj(int dj) {
		this.dj = dj;
	}

	public String getSrlx() {
		return srlx;
	}

	public void setSrlx(String srlx) {
		this.srlx = srlx;
	}
	
	public SjzdXmxx getSjzdXmxx() {
		return sjzdXmxx;
	}


	public void setSjzdXmxx(SjzdXmxx sjzdXmxx) {
		this.sjzdXmxx = sjzdXmxx;
	}
}
