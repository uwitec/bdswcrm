package com.sw.cms.action;

import java.util.ArrayList;
import java.util.List;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.service.AccountsService;
import com.sw.cms.service.CashBankService;

public class CashBankAction extends BaseAction {
	
	private CashBankService cashBankService;
	private AccountsService accountsService;
	private List accountList = new ArrayList();
	
	public String showCondition(){
		accountList = accountsService.getAccountList("1");
		return "success";
	}
	
	public String getResult(){
		return "success";
	}

	public CashBankService getCashBankService() {
		
		return cashBankService;
	}

	public void setCashBankService(CashBankService cashBankService) {
		this.cashBankService = cashBankService;
	}

	public AccountsService getAccountsService() {
		return accountsService;
	}

	public void setAccountsService(AccountsService accountsService) {
		this.accountsService = accountsService;
	}

	public List getAccountList() {
		return accountList;
	}

	public void setAccountList(List accountList) {
		this.accountList = accountList;
	}

}
