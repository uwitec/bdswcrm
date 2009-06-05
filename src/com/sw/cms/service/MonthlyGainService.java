package com.sw.cms.service;

import com.sw.cms.dao.MonthlyGainDAO;

/**
 * �¶������
 * @author liyt
 *
 */
public class MonthlyGainService {
	
	private MonthlyGainDAO monthlyGainDao;
	
	/**
	 * ȡë��
	 * @param ny ë���·�
	 * @return
	 */
	public double getGrossProfit(String ny){
		return monthlyGainDao.getGrossProfit(ny);
	}
	
	
	/**
	 * ȡ��������
	 * @param ny
	 * @return
	 */
	public double getOtherIncome(String ny){
		return monthlyGainDao.getOtherIncome(ny);
	}
	
	
	/**
	 * ȡ����
	 * @param ny
	 * @return
	 */
	public double getCost(String ny){
		return monthlyGainDao.getCost(ny);
	}
	
	
	/**
	 * ��̯����
	 * @param ny
	 * @return
	 */
	public double getDeferredPayment(String ny){
		return monthlyGainDao.getDeferredPayment(ny);
	}
	
	
	/**
	 * ��Ʒ��������
	 * @param ny
	 * @return
	 */
	public double getGoodBysr(String ny){
		return monthlyGainDao.getGoodBysr(ny);
	}
	
	
	/**
	 * ��Ʒ����֧��
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
