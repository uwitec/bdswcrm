package com.sw.cms.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.model.Fysq;
import com.sw.cms.model.LoginInfo;
import com.sw.cms.model.Page;
import com.sw.cms.service.DeptService;
import com.sw.cms.service.FysqService;
import com.sw.cms.service.SjzdService;
import com.sw.cms.service.UserService;
import com.sw.cms.util.Constant;
import com.sw.cms.util.DateComFunc;
import com.sw.cms.util.StringUtils;

/**
 * 费用申请Action类
 * 
 * @author liyt
 * 
 */
public class FysqAction extends BaseAction {

	private FysqService fysqService;
	private UserService userService;
	private SjzdService sjzdService;
	private DeptService deptService;

	private Page fysqPage;
	private Fysq fysq = new Fysq();
	private String id;

	private String[] fkfs;
	private List userList = new ArrayList();
	private List deptList = new ArrayList();
	private String[] fyzclx;

	private String creatdate1 = "";
	private String creatdate2 = "";
	private String state = "";

	private String orderName = "";
	private String orderType = "";

	private int curPage = 1;

	/**
	 * 费用申请列表
	 * 
	 * @return
	 */
	public String list() {
		int rowsPerPage = Constant.PAGE_SIZE2;
		// 增加条件限制：费用申请的申请人为当前登录人，就申请人可以看到自己的记录，不能看到他人的记录
		LoginInfo info = (LoginInfo) getSession().getAttribute("LOGINUSER");
		String user_id = info.getUser_id();

		String con = "";

		if (!creatdate1.equals("")) {
			con += " and creatdate>='" + creatdate1 + "'";
		}
		if (!creatdate2.equals("")) {
			con += " and creatdate<='" + creatdate2 + "'";
		}
		if (!state.equals("")) {
			con += " and state='" + state + "'";
		}

		// 费用申请审批权限设置
		Map fysqMap = userService.getSpRight("费用申请");
		String role_id = "";
		if (fysqMap != null) {
			role_id = StringUtils.nullToStr(fysqMap.get("role_id"));
		}
		String[] fysqRoles = role_id.split(",");

		//当前用户没有费用申请审批权限时，只能看到申请为自己或自己添加的费用申请单
		if (!userService.isUserInRole(user_id, fysqRoles)) {
			con += " and (sqr='" + user_id + "' or czr='" + user_id + "')";
		}

		if (orderName.equals("")) {
			orderName = "id";
		}
		if (orderType.equals("")) {
			orderType = "desc";
		}

		con += " order by " + orderName + " " + orderType;

		fysqPage = fysqService.getFysqList(con, curPage, rowsPerPage);

		return "success";
	}


	/**
	 * 打开费用申请页面
	 * 
	 * @return
	 */
	public String edit() {
		fkfs = sjzdService.getSjzdXmxxByZdId("SJZD_FKFS"); // 付款方式
		deptList = deptService.getDepts();

		if (!id.equals("")) {
			fysq = fysqService.getFysq(id);
		} else {
			fysq.setId(fysqService.updateFysqId());
			fysq.setCreatdate(DateComFunc.getToday());
		}

		return "success";
	}

	/**
	 * 更新费用申请信息
	 * 
	 * @return
	 */
	public String update() {
		LoginInfo info = (LoginInfo) getSession().getAttribute("LOGINUSER");
		String user_id = info.getUser_id();

		fysq.setCzr(user_id);
		fysqService.updateFysq(fysq);

		return "success";
	}

	
	/**
	 * 费用审批
	 * 
	 * @return
	 */
	public String doSp() {

		// 判断费用是否审批
		if (fysqService.isFinishSp(fysq.getId())) {
			this.saveMessage("费用申请已经审批完成，不能重复审批，请检查！");

			fkfs = sjzdService.getSjzdXmxxByZdId("SJZD_FKFS"); // 付款方式
			fyzclx = sjzdService.getSjzdXmxxByZdId("SJZD_ZCLX"); // 费用支出类型

			return "input";
		}

		LoginInfo info = (LoginInfo) getSession().getAttribute("LOGINUSER");
		String user_id = info.getUser_id();

		fysq.setSpr(user_id);
		fysqService.updateFysq(fysq);

		return "success";
	}

	/**
	 * 删除费用申请信息
	 * 
	 * @return
	 */
	public String del() {
		fysqService.delFysq(id);
		return "success";
	}

	public String getCreatdate1() {
		return creatdate1;
	}

	public void setCreatdate1(String creatdate1) {
		this.creatdate1 = creatdate1;
	}

	public String getCreatdate2() {
		return creatdate2;
	}

	public void setCreatdate2(String creatdate2) {
		this.creatdate2 = creatdate2;
	}

	public int getCurPage() {
		return curPage;
	}

	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}

	public String[] getFkfs() {
		return fkfs;
	}

	public void setFkfs(String[] fkfs) {
		this.fkfs = fkfs;
	}

	public Fysq getFysq() {
		return fysq;
	}

	public void setFysq(Fysq fysq) {
		this.fysq = fysq;
	}

	public Page getFysqPage() {
		return fysqPage;
	}

	public void setFysqPage(Page fysqPage) {
		this.fysqPage = fysqPage;
	}

	public FysqService getFysqService() {
		return fysqService;
	}

	public void setFysqService(FysqService fysqService) {
		this.fysqService = fysqService;
	}

	public String[] getFyzclx() {
		return fyzclx;
	}

	public void setFyzclx(String[] fyzclx) {
		this.fyzclx = fyzclx;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public SjzdService getSjzdService() {
		return sjzdService;
	}

	public void setSjzdService(SjzdService sjzdService) {
		this.sjzdService = sjzdService;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
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

}
