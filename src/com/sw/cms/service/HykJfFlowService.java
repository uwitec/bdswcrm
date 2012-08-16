package com.sw.cms.service;

import java.util.List;

import com.sw.cms.dao.HykJfFlowDAO;
import com.sw.cms.model.Hykda;
import com.sw.cms.model.Hykzz;

public class HykJfFlowService {
	
	private HykJfFlowDAO hykJfFlowDao;
	
	/**
	 * 根据会员卡号或者是手机号，及记分周期查询会员积分流水
	 * @param start_date
	 * @param end_date
	 * @param con_vl
	 * @return
	 */
	public List getHyjfFlowList(String start_date,String end_date,String con_vl){
		return hykJfFlowDao.getHyjfFlowList(start_date, end_date, con_vl);
	}
	
	
	/**
	 * 根据会员编号/手机号取会员信息列表
	 * @param hyk_id
	 * @return
	 */
	public List getHykdaList(String con_vl){
		return hykJfFlowDao.getHykdaList(con_vl);
	}
	
	
	/**
	 * 根据会员卡号取会员卡制作信息（初始积分）
	 */
	public Hykzz getHykzzInfo(String hyk_id){
		return hykJfFlowDao.getHykzzInfo(hyk_id);
	}
	
	/**
	 * 根据时间段及会员卡编号取积分
	 * @param hyk_id
	 * @param start_date
	 * @param end_date
	 * @return
	 */
	public double getHyjf(String hyk_id,String start_date,String end_date){
		return hykJfFlowDao.getHyjf(hyk_id, start_date, end_date);
	}
	

	public HykJfFlowDAO getHykJfFlowDao() {
		return hykJfFlowDao;
	}

	public void setHykJfFlowDao(HykJfFlowDAO hykJfFlowDao) {
		this.hykJfFlowDao = hykJfFlowDao;
	}

}
