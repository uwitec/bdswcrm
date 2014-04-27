package com.sw.cms.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.model.LoginInfo;
import com.sw.cms.model.Page;
import com.sw.cms.model.XsfpFpxx;
import com.sw.cms.service.DeptService;
import com.sw.cms.service.UserService;
import com.sw.cms.service.XsfpService;
import com.sw.cms.util.Constant;
import com.sw.cms.util.DateComFunc;

/**
 * 销售发票管理
 * 
 * @author liyt
 * 
 */
public class XsfpAction extends BaseAction {

	private XsfpService xsfpService;
	private UserService userService;
	private DeptService deptService;
	
	private List deptList = new ArrayList();

	private Page xsfpFpxxPage;
	private List xsfpFpmxList;
	
	private List xsfpList;
	private Map xsfpMxMap;

	private XsfpFpxx xsfpFpxx = new XsfpFpxx();

	private List fpmxDescs = new ArrayList();
	private List userList = new ArrayList();

	private String id = "";

	private String start_date = DateComFunc.getToday();
	private String end_date = DateComFunc.getToday();
	private String state = "";
	private String khmc = "";
	private String fplx = "";
	private String dept_id = "";
	private String xsry_name = "";

	private String orderName = "";
	private String orderType = "";

	private int counts = 0;
	private int curPage = 1;
	private int rowsPerPage = Constant.PAGE_SIZE;

	/**
	 * 取销售发票列表
	 * 
	 * @return
	 */
	public String listXsfpFpxx() {

		String con = "";
		if (!start_date.equals("")) {
			con += " and kprq>='" + start_date + "'";
		}
		if (!end_date.equals("")) {
			con += " and kprq<='" + (end_date + " 23:59:59") + "'";
		}
		if (!state.equals("")) {
			con += " and state='" + state + "'";
		}
		if (!khmc.equals("")) {
			con += " and khmc like '%" + khmc + "%'";
		}

		if (orderName.equals("")) {
			orderName = "id";
		}
		if (orderType.equals("")) {
			orderType = "desc";
		}
		con += " order by " + orderName + " " + orderType;

		xsfpFpxxPage = xsfpService.getXsfpFpxxPage(con, curPage, rowsPerPage);

		return SUCCESS;
	}

	/**
	 * 删除销售发票信息
	 * 
	 * @return
	 */
	public String delXsfpFpxx() {
		xsfpService.delXsfp(id);
		return SUCCESS;
	}

	/**
	 * 取发票明细
	 * 
	 * @return
	 */
	public String descFpmx() {
		xsfpFpmxList = xsfpService.getXsfpFpmxList(id);
		xsfpFpxx = xsfpService.getXsfpFpxx(id);
		return this.SUCCESS;
	}

	public XsfpService getXsfpService() {
		return xsfpService;
	}

	/**
	 * 编辑销售发票信息
	 * 
	 * @return
	 */
	public String editXsfpFpxx() {
		userList = userService.getAllEmployeeList();
		if (!id.equals("")) {
			xsfpFpxx = xsfpService.getXsfpFpxx(id);
			fpmxDescs = xsfpService.getXsfpFpmxList(id);
			counts = fpmxDescs.size();
		} else {
			xsfpFpxx.setId(xsfpService.updateXsfpID());
			xsfpFpxx.setKprq(DateComFunc.getToday());
		}
		return "success";
	}

	/**
	 * 取销售待开发票列表
	 * 
	 * @return
	 */
	public String listXsfpDkfp() {
		String con = "";
		if (!start_date.equals("")) {
			con += " and a.jy_date>='" + start_date + "'";
		}
		if (!end_date.equals("")) {
			con += " and a.jy_date<='" + (end_date + " 23:59:59") + "'";
		}
		if (!khmc.equals("")) {
			con += " and a.khmc like '%" + khmc + "%'";
		}
		if(!dept_id.equals("")){
			con += " and b.dept = '" + dept_id + "'";
		}
		if(!xsry_name.equals("")){
			con += " and b.real_name like '%" + xsry_name + "%'";
		}

		con += " order by a.jy_date desc";
		
		deptList = deptService.getDepts();

		xsfpFpxxPage = xsfpService.getXsfpDkfpPage(con, curPage, rowsPerPage);

		return SUCCESS;
	}

	/**
	 * 更新销售发票
	 * 
	 * @return
	 */
	public String updateXsfp() {
		LoginInfo info = (LoginInfo) getSession().getAttribute("LOGINUSER");
		String user_id = info.getUser_id();
		xsfpFpxx.setCzr(user_id);

		XsfpFpxx tempFpxx = xsfpService.getXsfpFpxx(xsfpFpxx.getId());
		if (tempFpxx != null && tempFpxx.getState().equals("已提交")) {
			// 销售发票已经提交
			return SUCCESS;
		}

		xsfpService.updateXsfp(xsfpFpxx, fpmxDescs);

		return SUCCESS;
	}
	
	
	/**
	 * 跳转到销售发票统计条件页面
	 * @return
	 */
	public String xsfptjCondition() {
		return SUCCESS;
	}
	
	
	/**
	 * 销售发票统计结果页面
	 * @return
	 */
	public String xsfptjResult() {
		xsfpList = xsfpService.getXsfpList(start_date, end_date, fplx);
		xsfpMxMap = xsfpService.getXsfpMxMap(start_date, end_date, fplx);
		return SUCCESS;
	}

	public void setXsfpService(XsfpService xsfpService) {
		this.xsfpService = xsfpService;
	}

	public String getStart_date() {
		return start_date;
	}

	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}

	public String getEnd_date() {
		return end_date;
	}

	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getKhmc() {
		return khmc;
	}

	public void setKhmc(String khmc) {
		this.khmc = khmc;
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

	public int getCurPage() {
		return curPage;
	}

	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}

	public int getRowsPerPage() {
		return rowsPerPage;
	}

	public void setRowsPerPage(int rowsPerPage) {
		this.rowsPerPage = rowsPerPage;
	}

	public Page getXsfpFpxxPage() {
		return xsfpFpxxPage;
	}

	public void setXsfpFpxxPage(Page xsfpFpxxPage) {
		this.xsfpFpxxPage = xsfpFpxxPage;
	}

	public List getXsfpFpmxList() {
		return xsfpFpmxList;
	}

	public void setXsfpFpmxList(List xsfpFpmxList) {
		this.xsfpFpmxList = xsfpFpmxList;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List getFpmxDescs() {
		return fpmxDescs;
	}

	public void setFpmxDescs(List fpmxDescs) {
		this.fpmxDescs = fpmxDescs;
	}

	public List getUserList() {
		return userList;
	}

	public void setUserList(List userList) {
		this.userList = userList;
	}

	public XsfpFpxx getXsfpFpxx() {
		return xsfpFpxx;
	}

	public void setXsfpFpxx(XsfpFpxx xsfpFpxx) {
		this.xsfpFpxx = xsfpFpxx;
	}

	public int getCounts() {
		return counts;
	}

	public void setCounts(int counts) {
		this.counts = counts;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public List getXsfpList() {
		return xsfpList;
	}

	public void setXsfpList(List xsfpList) {
		this.xsfpList = xsfpList;
	}

	public Map getXsfpMxMap() {
		return xsfpMxMap;
	}

	public void setXsfpMxMap(Map xsfpMxMap) {
		this.xsfpMxMap = xsfpMxMap;
	}

	public String getFplx() {
		return fplx;
	}

	public void setFplx(String fplx) {
		this.fplx = fplx;
	}

	public DeptService getDeptService() {
		return deptService;
	}

	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}

	public List getDeptList() {
		return deptList;
	}

	public void setDeptList(List deptList) {
		this.deptList = deptList;
	}

	public String getDept_id() {
		return dept_id;
	}

	public void setDept_id(String dept_id) {
		this.dept_id = dept_id;
	}

	public String getXsry_name() {
		return xsry_name;
	}

	public void setXsry_name(String xsry_name) {
		this.xsry_name = xsry_name;
	}

}
