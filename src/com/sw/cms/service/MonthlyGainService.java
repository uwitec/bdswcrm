package com.sw.cms.service;

import com.sw.cms.dao.MonthlyGainDAO;

/**
 * 月度利润表
 * @author liyt
 *
 */
public class MonthlyGainService {
	
	private MonthlyGainDAO monthlyGainDao;
	
	/**
	 * 取毛利
	 * @param ny 毛利月份
	 * @return
	 */
	public double getGrossProfit(String ny){
		return monthlyGainDao.getGrossProfit(ny);
	}
	
	
	/**
	 * 取其它收入
	 * @param ny
	 * @return
	 */
	public double getOtherIncome(String ny){
		return monthlyGainDao.getOtherIncome(ny);
	}
	
	
	/**
	 * 取费用
	 * @param ny
	 * @return
	 */
	public double getCost(String ny){
		return monthlyGainDao.getCost(ny);
	}
	
	
	/**
	 * 待摊费用
	 * @param ny
	 * @return
	 */
	public double getDeferredPayment(String ny){
		return monthlyGainDao.getDeferredPayment(ny);
	}
	
	
	/**
	 * 商品报溢收入
	 * @param ny
	 * @return
	 */
	public double getGoodBysr(String ny){
		return monthlyGainDao.getGoodBysr(ny);
	}
	
	
	/**
	 * 商品报损支出
	 * @param ny
	 * @return
	 */
	public double getGoodBszc(String ny){
		return monthlyGainDao.getGoodBszc(ny);
	}


	public MonthlyGainDAO getMonthlyGainDao() {
		return monthlyGainDao;
	}


	public void setMonthlyGainDao(MonthlyGainDAO monthlyGainDao) {
		this.monthlyGainDao = monthlyGainDao;
	}

}
