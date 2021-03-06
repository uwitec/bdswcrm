package com.sw.cms.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.model.Accounts;
import com.sw.cms.service.AccountsService;
import com.sw.cms.service.SysInitSetService;

public class AccountsAction extends BaseAction {
	
	private AccountsService accountsService;
	private SysInitSetService sysInitSetService;
	
	private List accountList = new ArrayList();
	
	private Accounts accounts;
	private Map accountMap;
	private String id;
	private String account_id;
	private double qcje;
	private String iscs_flag = "0";
	private double zcje;
	
	
	/**
	 * 账户列表
	 * @return
	 */
	public String list(){
		accountList = accountsService.getAccountList("");
		iscs_flag = sysInitSetService.getQyFlag();
		return "success";
	}
	
	
	/**
	 * 添加账户
	 * @return
	 */
	public String add(){
		return "success";
	}
	
	/**
	 * 保存账号信息
	 * @return
	 */
	public String save(){
		accountsService.saveAccounts(accounts);
		return "success";
	}
	
	
	/**
	 * 编辑账号信息
	 * @return
	 */
	public String edit(){
		accountMap = accountsService.getAccounts(id);
		return "success";
	}
	
	
	/**
	 * 更新账号信息
	 * @return
	 */
	public String update(){
		accountsService.updateAccounts(accounts);
		return "success";
	}
	
	
	/**
	 * 删除账号信息
	 * @return
	 */
	public String del(){		
		accountsService.updateAccountFlag(id,"0");
		return "success";
	}
	
	
	public String restore(){
		accountsService.updateAccountFlag(id,"1");
		return "success";
	}
	
	
	/**
	 * 打开选择账户页面
	 * @return
	 */
	public String sel(){
		accountList = accountsService.getAccountList("1");
		return "success";
	}
	
	/**
	 * 打开初始账户金额页面
	 * @return
	 */
	public String initAdd(){
		accountMap = accountsService.getAccounts(id);
		return "success";
	}
	
	/**
	 * 初始账户金额
	 * @return
	 */
	public String initAccountJe(){
		accountsService.initAccountJe(account_id, qcje);
		return "success";
	}
	
	
	/**
	 * 检查账户金额是否满足支出需求,返回true满足，返回false不满足
	 */
	public void checkAccountJe(){
		if(accountsService.isZhjeXyZero(account_id, zcje)){
			this.writeStringToResponse("false");
		}else{
			this.writeStringToResponse("true");
		}
	}
	

	public List getAccountList() {
		return accountList;
	}

	public void setAccountList(List accountList) {
		this.accountList = accountList;
	}

	public AccountsService getAccountsService() {
		return accountsService;
	}

	public void setAccountsService(AccountsService accountsService) {
		this.accountsService = accountsService;
	}


	public Map getAccountMap() {
		return accountMap;
	}


	public void setAccountMap(Map accountMap) {
		this.accountMap = accountMap;
	}


	public Accounts getAccounts() {
		return accounts;
	}


	public void setAccounts(Accounts accounts) {
		this.accounts = accounts;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getAccount_id() {
		return account_id;
	}


	public void setAccount_id(String account_id) {
		this.account_id = account_id;
	}


	public double getQcje() {
		return qcje;
	}


	public void setQcje(double qcje) {
		this.qcje = qcje;
	}


	public SysInitSetService getSysInitSetService() {
		return sysInitSetService;
	}


	public void setSysInitSetService(SysInitSetService sysInitSetService) {
		this.sysInitSetService = sysInitSetService;
	}


	public String getIscs_flag() {
		return iscs_flag;
	}


	public void setIscs_flag(String iscs_flag) {
		this.iscs_flag = iscs_flag;
	}


	public double getZcje() {
		return zcje;
	}


	public void setZcje(double zcje) {
		this.zcje = zcje;
	}
}
