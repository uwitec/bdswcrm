package com.sw.cms.action;

import java.util.ArrayList;
import java.util.List;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.model.LoginInfo;
import com.sw.cms.model.Page;
import com.sw.cms.model.Txfk;
import com.sw.cms.service.SjzdService;
import com.sw.cms.service.TxfkService;
import com.sw.cms.service.UserService;
import com.sw.cms.util.Constant;
import com.sw.cms.util.DateComFunc;

public class TxfkAction extends BaseAction {
	
	private TxfkService txfkService;
	private UserService userService;
	private SjzdService sjzdService;
	
	private Page txfkPage;
	private Txfk txfk = new Txfk();
	private List txfkDescs = new ArrayList();
	private List userList = new ArrayList();
	
	private String fk_date1 = "";
	private String fk_date2 = "";
	private String state = "";
	private String[] zclxArray;
	private int counts = 0;
	
	private String id = "";
	
	private String orderName = "";
	private String orderType = "";
	
	private String fkje = "";
	
	private int curPage = 1;
	
	/**
	 * 取摊销付款列表
	 * @return
	 */
	public String list(){
		int rowsPerPage = Constant.PAGE_SIZE;
		String con = "";
		
		if(!fk_date1.equals("")){
			con += " and fk_date>='" + fk_date1 + "'";
		}
		if(!fk_date2.equals("")){
			con += " and fk_date<='" + (fk_date2 + " 23:59:59") + "'";
		}
		if(!state.equals("")){
			con += " and state='" + state + "'";
		}
		
		if (orderName.equals("")) {
			orderName = "id";
		}
		if (orderType.equals("")) {
			orderType = "desc";
		}
		con += " order by " + orderName + " " + orderType;
		
		txfkPage = txfkService.getTxfkList(con, curPage, rowsPerPage);
		
		return "success";
	}
	
	
	/**
	 * 获取摊销付款明细信息
	 * @return
	 */
	public String desc(){
		txfkDescs = txfkService.getTxfkDescs(id);
		return "success";
	}
	
	
	/**
	 * 编辑摊销付款信息
	 * @return
	 */
	public String edit(){
		
		userList = userService.getAllEmployeeList();
		zclxArray = sjzdService.getSjzdXmxxByZdId("SJZD_ZCLX");  //费用支出类型
		if(!id.equals("")){
			txfk = txfkService.getTxfk(id);
			txfkDescs = txfkService.getTxfkDescs(id);
			counts = txfkDescs.size();
		}else{
			txfk.setId(txfkService.updateTxfkID());
			txfk.setFk_date(DateComFunc.getToday());
		}
		return "success";
	}
	
	
	/**
	 * 保存（更新）摊销付款信息
	 * @return
	 */
	public String update(){
		LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		String user_id = info.getUser_id();
		txfk.setCzr(user_id);
		
		txfkService.updateTxfk(txfk, txfkDescs);
		return "success";
	}
	
	
	/**
	 * 删除摊销付款信息
	 * @return
	 */
	public String del(){
		txfkService.delTxfk(id);
		return "success";
	}
	
	
	/**
	 * 打开计划制定页面
	 * @return
	 */
	public String plan(){
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

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Txfk getTxfk() {
		return txfk;
	}

	public void setTxfk(Txfk txfk) {
		this.txfk = txfk;
	}

	public List getTxfkDescs() {
		return txfkDescs;
	}

	public void setTxfkDescs(List txfkDescs) {
		this.txfkDescs = txfkDescs;
	}

	public Page getTxfkPage() {
		return txfkPage;
	}

	public void setTxfkPage(Page txfkPage) {
		this.txfkPage = txfkPage;
	}

	public TxfkService getTxfkService() {
		return txfkService;
	}

	public void setTxfkService(TxfkService txfkService) {
		this.txfkService = txfkService;
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


	public String getFk_date1() {
		return fk_date1;
	}


	public void setFk_date1(String fk_date1) {
		this.fk_date1 = fk_date1;
	}


	public String getFk_date2() {
		return fk_date2;
	}


	public void setFk_date2(String fk_date2) {
		this.fk_date2 = fk_date2;
	}


	public SjzdService getSjzdService() {
		return sjzdService;
	}


	public void setSjzdService(SjzdService sjzdService) {
		this.sjzdService = sjzdService;
	}


	public String[] getZclxArray() {
		return zclxArray;
	}


	public void setZclxArray(String[] zclxArray) {
		this.zclxArray = zclxArray;
	}


	public int getCounts() {
		return counts;
	}


	public void setCounts(int counts) {
		this.counts = counts;
	}


	public String getFkje() {
		return fkje;
	}


	public void setFkje(String fkje) {
		this.fkje = fkje;
	}

}
