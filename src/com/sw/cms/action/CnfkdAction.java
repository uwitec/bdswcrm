package com.sw.cms.action;

import java.util.List;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.model.Cnfkd;
import com.sw.cms.model.LoginInfo;
import com.sw.cms.model.Page;
import com.sw.cms.service.AccountsService;
import com.sw.cms.service.CnfkdService;
import com.sw.cms.service.SjzdService;
import com.sw.cms.util.Constant;
import com.sw.cms.util.DateComFunc;

public class CnfkdAction extends BaseAction {
	
	private CnfkdService cnfkdService;
	private AccountsService accountsService;
	private SjzdService sjzdService;
	
	private Page cnfkdPage;
	private Cnfkd cnfkd;
	private List accountList;
	private String[] arryFkfs;
	
	private String start_date = "";
	private String end_date = "";
	private String state = "待支付";
	
	private String id = "";
	
	private int curPage = 1;
	
	/**
	 * 取出纳付款单列表
	 * @return
	 */
	public String list(){
		try{
			int rowsPerPage = Constant.PAGE_SIZE2;
			String con = "";
			
			if(!start_date.equals("")){
				con += " and fk_date>='" + start_date + "'";
			}
			if(!end_date.equals("")){
				con += " and fk_date<='" + (end_date + " 23:59:59") + "'";
			}
			if(!state.equals("")){
				con += " and state='" + state + "'";
			}
			con += " order by id desc";
			
			cnfkdPage = cnfkdService.getCnfkds(con, curPage, rowsPerPage);
			return SUCCESS;
		}catch(Exception e){
			log.error("取出纳付款单列表错误，原因:" + e.getMessage());
			e.printStackTrace();
			return ERROR;
		}
	}	
	
	
	/**
	 * 取出纳付款单基本信息
	 * @return
	 */
	public String edit(){
		try{
			cnfkd = cnfkdService.getCnfkd(id);
			if(cnfkd.getFk_date() == null || cnfkd.getFk_date().equals("")){
				cnfkd.setFk_date(DateComFunc.getToday());
			}
			accountList = accountsService.getAccountList();
			arryFkfs = sjzdService.getSjzdXmxxByZdId("SJZD_FKFS");
			return SUCCESS;
		}catch(Exception e){
			log.error("取出纳付款单错误，原因:" + e.getMessage());
			e.printStackTrace();
			return ERROR;
		}
	}
	
	
	/**
	 * 更新出纳付款单相关信息
	 * @return
	 */
	public String update(){
		try{
			LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
			String user_id = info.getUser_id();
			cnfkd.setCzr(user_id);
			
			cnfkdService.updateCnfkd(cnfkd);
			return SUCCESS;
		}catch(Exception e){
			log.error("取出纳付款单错误，原因:" + e.getMessage());
			e.printStackTrace();
			return ERROR;
		}
	}
	
	
	/**
	 * 退回出纳付款单
	 * @return
	 */
	public String del(){
		try{
			cnfkdService.delCnfkd(id);
			return SUCCESS;
		}catch(Exception e){
			log.error("退回纳付款单错误，原因:" + e.getMessage());
			e.printStackTrace();
			return ERROR;
		}
	}
	

	public Page getCnfkdPage() {
		return cnfkdPage;
	}
	public void setCnfkdPage(Page cnfkdPage) {
		this.cnfkdPage = cnfkdPage;
	}
	public String getStart_date() {
		return start_date;
	}
	public void setStart_date(String startDate) {
		start_date = startDate;
	}
	public String getEnd_date() {
		return end_date;
	}
	public void setEnd_date(String endDate) {
		end_date = endDate;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}

	public int getCurPage() {
		return curPage;
	}

	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}

	public CnfkdService getCnfkdService() {
		return cnfkdService;
	}

	public void setCnfkdService(CnfkdService cnfkdService) {
		this.cnfkdService = cnfkdService;
	}


	public Cnfkd getCnfkd() {
		return cnfkd;
	}


	public void setCnfkd(Cnfkd cnfkd) {
		this.cnfkd = cnfkd;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public AccountsService getAccountsService() {
		return accountsService;
	}


	public void setAccountsService(AccountsService accountsService) {
		this.accountsService = accountsService;
	}


	public SjzdService getSjzdService() {
		return sjzdService;
	}


	public void setSjzdService(SjzdService sjzdService) {
		this.sjzdService = sjzdService;
	}


	public List getAccountList() {
		return accountList;
	}


	public void setAccountList(List accountList) {
		this.accountList = accountList;
	}


	public String[] getArryFkfs() {
		return arryFkfs;
	}


	public void setArryFkfs(String[] arryFkfs) {
		this.arryFkfs = arryFkfs;
	}

}
