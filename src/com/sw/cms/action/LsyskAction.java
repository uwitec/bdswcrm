package com.sw.cms.action;

import java.util.ArrayList;
import java.util.List;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.model.LoginInfo;
import com.sw.cms.model.Lsysk;
import com.sw.cms.model.Page;
import com.sw.cms.service.LsyskService;
import com.sw.cms.service.PosTypeService;
import com.sw.cms.service.SjzdService;
import com.sw.cms.service.UserService;
import com.sw.cms.util.Constant;

/**
 * 零售预收款处理
 * @author liyt
 *
 */
public class LsyskAction extends BaseAction {
	
	private LsyskService lsyskService;
	private UserService userService;
	private SjzdService sjzdService;
	private PosTypeService posTypeService;
	
	private Page lsyskPage;
	private Lsysk lsysk = new Lsysk();
	private List userList = new ArrayList();
	private String[] fkfs;
	private List posTypeList = new ArrayList();
	
	private String id = "";
	
	private String client_name = "";
	private String ys_date1 = "";
	private String ys_date2 = "";
	
	private String orderName ="";
	private String orderType ="";	
	private int curPage = 1;
	
	
	/**
	 * 取零售预收款列表
	 * @return
	 */
	public String list(){
		int rowsPerPage = Constant.PAGE_SIZE2;

		String con = "";

		if(!client_name.equals("")){
			con += " and client_name like'%" + client_name + "%'";
		}
		if(!ys_date1.equals("")){
			con += " and ys_date>='" + ys_date1 + "'";
		}
		if(!ys_date2.equals("")){
			con += " and ys_date<='" + ys_date2 + "'";
		}
		if(!id.equals("")){
			con += " and id='" + id + "'";
		}
		
		if(orderName.equals("")){
			orderName = "id";
		}
		if(orderType.equals("")){
			orderType = "desc";
		}
		
		con += " order by " + orderName + " " + orderType;
		lsyskPage = lsyskService.getLsyskList(con, curPage, rowsPerPage);
		
		return "success";
	}
	
	
	/**
	 * 选择未冲抵的零售预收款
	 * @return
	 */
	public String selLsysk(){
		int rowsPerPage = Constant.PAGE_SIZE;

		String con = " and type='未冲抵'";

		if(!client_name.equals("")){
			con += " and client_name like'%" + client_name + "%'";
		}
		if(!ys_date1.equals("")){
			con += " and ys_date>='" + ys_date1 + "'";
		}
		if(!ys_date2.equals("")){
			con += " and ys_date<='" + ys_date2 + "'";
		}
		if(!id.equals("")){
			con += " and id='" + id + "'";
		}
		
		if(orderName.equals("")){
			orderName = "id";
		}
		if(orderType.equals("")){
			orderType = "desc";
		}
		
		con += " order by " + orderName + " " + orderType;
		lsyskPage = lsyskService.getLsyskList(con, curPage, rowsPerPage);
		
		return "success";
	}
	
	
	
	/**
	 * 打开添加零售预收款页面
	 * @return
	 */
	public String add(){
		userList = userService.getAllEmployeeList();
		id = lsyskService.updateLsyskId();
		fkfs = sjzdService.getSjzdXmxxByZdId("SJZD_FKFS");
		posTypeList = posTypeService.getPosTypeList();
		return "success";
	}
	
	
	/**
	 * 保存零售预收款信息
	 * @return
	 */
	public String save(){
		LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		String user_id = info.getUser_id();
		
		lsysk.setCzr(user_id);
		
		lsyskService.saveLsysk(lsysk);
		
		return "success";
	}
	
	
	/**
	 * 更新零售预收款信息
	 * @return
	 */
	public String update(){
		LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		String user_id = info.getUser_id();
		
		lsysk.setCzr(user_id);
		
		lsyskService.updateLsysk(lsysk);
		
		return "success";
	}
	
	
	/**
	 * 编辑零售预收款信息
	 * @return
	 */
	public String edit(){
		userList = userService.getAllEmployeeList();
		lsysk = (Lsysk)lsyskService.getLsysk(id);
		fkfs = sjzdService.getSjzdXmxxByZdId("SJZD_FKFS");
		posTypeList = posTypeService.getPosTypeList();
		return "success";
	}
	
	
	/**
	 * 删除零售预收款信息
	 * @return
	 */
	public String del(){
		lsyskService.delLsysk(id);
		
		return "success";
	}
	
	
	public String getClient_name() {
		return client_name;
	}
	public void setClient_name(String client_name) {
		this.client_name = client_name;
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
	public Lsysk getLsysk() {
		return lsysk;
	}
	public void setLsysk(Lsysk lsysk) {
		this.lsysk = lsysk;
	}
	public Page getLsyskPage() {
		return lsyskPage;
	}
	public void setLsyskPage(Page lsyskPage) {
		this.lsyskPage = lsyskPage;
	}
	public LsyskService getLsyskService() {
		return lsyskService;
	}
	public void setLsyskService(LsyskService lsyskService) {
		this.lsyskService = lsyskService;
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
	public String getYs_date1() {
		return ys_date1;
	}
	public void setYs_date1(String ys_date1) {
		this.ys_date1 = ys_date1;
	}
	public String getYs_date2() {
		return ys_date2;
	}
	public void setYs_date2(String ys_date2) {
		this.ys_date2 = ys_date2;
	}


	public String[] getFkfs() {
		return fkfs;
	}


	public void setFkfs(String[] fkfs) {
		this.fkfs = fkfs;
	}


	public SjzdService getSjzdService() {
		return sjzdService;
	}


	public void setSjzdService(SjzdService sjzdService) {
		this.sjzdService = sjzdService;
	}


	public PosTypeService getPosTypeService() {
		return posTypeService;
	}


	public void setPosTypeService(PosTypeService posTypeService) {
		this.posTypeService = posTypeService;
	}


	public List getPosTypeList() {
		return posTypeList;
	}


	public void setPosTypeList(List posTypeList) {
		this.posTypeList = posTypeList;
	}

}
