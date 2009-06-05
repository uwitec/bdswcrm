package com.sw.cms.action;

import java.util.ArrayList;
import java.util.List;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.model.LoginInfo;
import com.sw.cms.model.Page;
import com.sw.cms.model.Qtsr;
import com.sw.cms.service.QtsrService;
import com.sw.cms.service.SjzdService;
import com.sw.cms.service.UserService;
import com.sw.cms.util.Constant;

public class QtsrAction extends BaseAction {
	
	private QtsrService qtsrService;
	private UserService userService;
	private SjzdService sjzdService;
	
	private Qtsr qtsr = new Qtsr();
	private Page pageQtsr;
	private List userList = new ArrayList();
	private String[] srlx;
	
	private String sr_date1 = "";
	private String sr_date2 = "";
	
	private String id = "";
	
	private String orderName ="";
	private String orderType ="";
	
	private int curPage = 1;
	
	/**
	 * 其它收入列表
	 * @return
	 */
	public String list(){
		int rowsPerPage = Constant.PAGE_SIZE2;

		String con = "";
		
		if(!sr_date1.equals("")){
			con += " and sr_date>='" + sr_date1 + "'";
		}
		if(!sr_date2.equals("")){
			con += " and sr_date<='" + sr_date2 + "'";
		}
		
		if(orderName.equals("")){
			orderName = "id";
		}
		if(orderType.equals("")){
			orderType = "desc";
		}
		
		con += " order by " + orderName + " " + orderType;
		
		pageQtsr = qtsrService.getQtsrList(con, curPage, rowsPerPage);
		
		return "success";
	}
	
	
	/**
	 * 添加其它收入
	 * @return
	 */
	public String add(){
		userList = userService.getAllEmployeeList();
		id = qtsrService.updateQtsrID();
		srlx = sjzdService.getSjzdXmxxByZdId("SJZD_SRLX");
		return "success";
	}
	
	
	/**
	 * 保存其它收入
	 * @return
	 */
	public String save(){
		LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		String user_id = info.getUser_id();
		qtsr.setCzr(user_id);
		
		qtsrService.saveQtsr(qtsr);
		return "success";
	}
	
	/**
	 * 编辑其它收入
	 * @return
	 */
	public String edit(){
		qtsr = (Qtsr)qtsrService.getQtsr(id);
		userList = userService.getAllEmployeeList();
		srlx = sjzdService.getSjzdXmxxByZdId("SJZD_SRLX");
		return "success";
	}
	
	
	/**
	 * 更新其它收入
	 * @return
	 */
	public String update(){
		LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		String user_id = info.getUser_id();
		qtsr.setCzr(user_id);
		
		qtsrService.updateQtsr(qtsr);
		return "success";
	}
	
	
	/**
	 * 删除其它收入
	 * @return
	 */
	public String del(){
		qtsrService.delQtsr(id);
		return "success";
	}
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Page getPageQtsr() {
		return pageQtsr;
	}

	public void setPageQtsr(Page pageQtsr) {
		this.pageQtsr = pageQtsr;
	}

	public Qtsr getQtsr() {
		return qtsr;
	}

	public void setQtsr(Qtsr qtsr) {
		this.qtsr = qtsr;
	}

	public QtsrService getQtsrService() {
		return qtsrService;
	}

	public void setQtsrService(QtsrService qtsrService) {
		this.qtsrService = qtsrService;
	}

	public String getSr_date1() {
		return sr_date1;
	}

	public void setSr_date1(String sr_date1) {
		this.sr_date1 = sr_date1;
	}

	public String getSr_date2() {
		return sr_date2;
	}

	public void setSr_date2(String sr_date2) {
		this.sr_date2 = sr_date2;
	}


	public int getCurPage() {
		return curPage;
	}


	public void setCurPage(int curPage) {
		this.curPage = curPage;
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


	public UserService getUserService() {
		return userService;
	}


	public void setUserService(UserService userService) {
		this.userService = userService;
	}


	public List getUserList() {
		return userList;
	}


	public void setUserList(List userList) {
		this.userList = userList;
	}


	public SjzdService getSjzdService() {
		return sjzdService;
	}


	public void setSjzdService(SjzdService sjzdService) {
		this.sjzdService = sjzdService;
	}


	public String[] getSrlx() {
		return srlx;
	}


	public void setSrlx(String[] srlx) {
		this.srlx = srlx;
	}

}
