package com.sw.cms.action;

import java.util.ArrayList;
import java.util.List;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.model.LoginInfo;
import com.sw.cms.model.Page;
import com.sw.cms.model.Qtzc;
import com.sw.cms.service.QtzcService;
import com.sw.cms.service.SjzdService;
import com.sw.cms.service.UserService;
import com.sw.cms.util.Constant;
import com.sw.cms.util.DateComFunc;

public class QtzcAction extends BaseAction {
	
	private QtzcService qtzcService;
	private UserService userService;
	private SjzdService sjzdService;
	
	private Page pageQtzc;
	private Qtzc qtzc;
	private List userList = new ArrayList();
	private String[] zclx;
	private String[] fkfs;
	
	private String zc_date1 = "";
	private String zc_date2 = "";
	
	private String id = "";
	
	private String orderName ="";
	private String orderType ="";
	
	private int curPage = 1;
	
	
	/**
	 * 其它支出列表
	 * @return
	 */
	public String list(){
		int rowsPerPage = Constant.PAGE_SIZE2;

		String con = "";
		
		if(!zc_date1.equals("")){
			con += " and zc_date>='" + zc_date1 + "'";
		}
		if(!zc_date2.equals("")){
			con += " and zc_date<='" + zc_date2 + "'";
		}
		
		if(orderName.equals("")){
			orderName = "id";
		}
		if(orderType.equals("")){
			orderType = "desc";
		}
		
		con += " order by " + orderName + " " + orderType;
		
		pageQtzc = qtzcService.getQtzcList(con, curPage, rowsPerPage);
		
		return "success";
	}
	
	
	/**
	 * 添加其它支出
	 * @return
	 */
	public String add(){
		userList = userService.getAllEmployeeList();
		id = qtzcService.updateQtzcID();
		zclx = sjzdService.getSjzdXmxxByZdId("SJZD_ZCLX");
		return "success";
	}
	
	
	/**
	 * 保存其它支出
	 * @return
	 */
	public String save(){
		LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		String user_id = info.getUser_id();
		qtzc.setCzr(user_id);
		
		qtzcService.saveQtzc(qtzc);
		return "success";
	}
	
	/**
	 * 编辑其它支出
	 * @return
	 */
	public String edit(){
		qtzc = (Qtzc)qtzcService.getQtzc(id);
		if(qtzc.getZc_date() == null || qtzc.getZc_date().equals("")){
			qtzc.setZc_date(DateComFunc.getToday());
		}
		
		userList = userService.getAllEmployeeList();
		zclx = sjzdService.getSjzdXmxxByZdId("SJZD_ZCLX");
		fkfs = sjzdService.getSjzdXmxxByZdId("SJZD_FKFS");   //付款方式
		
		return "success";
	}
	
	
	/**
	 * 更新其它支出
	 * @return
	 */
	public String update(){
		
		//判断支出费用是否已经提交
		if(qtzcService.isFinishZc(qtzc.getId())){
			this.saveMessage("费用已支出，不能重复支出，请检查！");
			zclx = sjzdService.getSjzdXmxxByZdId("SJZD_ZCLX");
			fkfs = sjzdService.getSjzdXmxxByZdId("SJZD_FKFS");   //付款方式
			return "input";
		}
		
		LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		String user_id = info.getUser_id();
		qtzc.setCzr(user_id);
		
		qtzcService.updateQtzc(qtzc);
		return "success";
	}
	
	
	/**
	 * 删除其它支出
	 * @return
	 */
	public String del(){
		qtzcService.delQtzc(id);
		return "success";
	}
	
	

	public int getCurPage() {
		return curPage;
	}

	public void setCurPage(int curPage) {
		this.curPage = curPage;
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

	public Page getPageQtzc() {
		return pageQtzc;
	}

	public void setPageQtzc(Page pageQtzc) {
		this.pageQtzc = pageQtzc;
	}

	public Qtzc getQtzc() {
		return qtzc;
	}

	public void setQtzc(Qtzc qtzc) {
		this.qtzc = qtzc;
	}

	public QtzcService getQtzcService() {
		return qtzcService;
	}

	public void setQtzcService(QtzcService qtzcService) {
		this.qtzcService = qtzcService;
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

	public String getZc_date1() {
		return zc_date1;
	}

	public void setZc_date1(String zc_date1) {
		this.zc_date1 = zc_date1;
	}

	public String getZc_date2() {
		return zc_date2;
	}

	public void setZc_date2(String zc_date2) {
		this.zc_date2 = zc_date2;
	}


	public SjzdService getSjzdService() {
		return sjzdService;
	}


	public void setSjzdService(SjzdService sjzdService) {
		this.sjzdService = sjzdService;
	}


	public String[] getZclx() {
		return zclx;
	}


	public void setZclx(String[] zclx) {
		this.zclx = zclx;
	}


	public String[] getFkfs() {
		return fkfs;
	}


	public void setFkfs(String[] fkfs) {
		this.fkfs = fkfs;
	}

}
