package com.sw.cms.action;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.service.MonthlyGainService;
import com.sw.cms.util.DateComFunc;

/**
 * 月度利润表
 * @author liyt
 *
 */
public class MonthlyGainAction extends BaseAction {

	private MonthlyGainService monthlyGainService;
	
	private double gross = 0;
	private double income = 0;
	private double cost = 0;
	private double deferredPayment = 0;
	private double goodbysr = 0;
	private double goodbszc = 0;
	
	private String year = "";
	private String month = "";
	
	/**
	 * 统计结果
	 * @return
	 */
	public String getResults(){
		
		String ny = "";
		
		//默认当前月
		if(year.equals("") || month.equals("")){
			year = DateComFunc.getYear() + "";
			if(DateComFunc.getMonth()<10){
				month = "0" + DateComFunc.getMonth();
			}else{
				month =  "" + DateComFunc.getMonth();
			}
		}
		
		ny = year + "-" + month;
		
		gross = monthlyGainService.getGrossProfit(ny);
		income = monthlyGainService.getOtherIncome(ny);
		cost = monthlyGainService.getCost(ny);
		deferredPayment = monthlyGainService.getDeferredPayment(ny);
		goodbysr = monthlyGainService.getGoodBysr(ny);
		goodbszc = monthlyGainService.getGoodBszc(ny);
		
		return "success";
	}
	
	
	public double getCost() {
		return cost;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}
	public double getDeferredPayment() {
		return deferredPayment;
	}
	public void setDeferredPayment(double deferredPayment) {
		this.deferredPayment = deferredPayment;
	}
	public double getGoodbszc() {
		return goodbszc;
	}
	public void setGoodbszc(double goodbszc) {
		this.goodbszc = goodbszc;
	}
	public double getGoodbysr() {
		return goodbysr;
	}
	public void setGoodbysr(double goodbysr) {
		this.goodbysr = goodbysr;
	}
	public double getGross() {
		return gross;
	}
	public void setGross(double gross) {
		this.gross = gross;
	}
	public double getIncome() {
		return income;
	}
	public void setIncome(double income) {
		this.income = income;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public MonthlyGainService getMonthlyGainService() {
		return monthlyGainService;
	}
	public void setMonthlyGainService(MonthlyGainService monthlyGainService) {
		this.monthlyGainService = monthlyGainService;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	
}
