package com.sw.cms.service;

import java.util.List;

import com.sw.cms.dao.CalendarPlanDAO;
import com.sw.cms.model.CalendarPlan;

/**
 * 日程安排
 * @author jinyanni
 *
 */
public class CalendarPlanService {

	private CalendarPlanDAO calendarPlanDao;
	
	/**
	 * 根据查询条件取日程安排列表
	 * @param con
	 * @return
	 */
	public List getCalendarPlans(String con){
		return calendarPlanDao.getCalendarPlans(con);
	}
	
	
	/**
	 * 更新日程安排
	 * @param info
	 */
	public void updateCalendarPlan(CalendarPlan info){
		calendarPlanDao.updateCalendarPlan(info);
	}
	
	
	/**
	 * 删除日程安排
	 * @param id
	 */
	public void delCalendarPlan(String id){
		calendarPlanDao.delCalendarPlan(id);
	}
	
	
	/**
	 * 取当前月中有日程记录的日期列表
	 * @param user_id
	 * @param year
	 * @param month
	 * @return
	 */
	public List<String> getDateHasPlan(String user_id,String year,String month){
		return calendarPlanDao.getDateHasPlan(user_id, year, month);
	}
	
	
	/**
	 * 根据日程安排编号取日程信息
	 * @param id
	 * @return
	 */
	public CalendarPlan getCalendarPlan(String id){
		return calendarPlanDao.getCalendarPlan(id);
	}


	public CalendarPlanDAO getCalendarPlanDao() {
		return calendarPlanDao;
	}


	public void setCalendarPlanDao(CalendarPlanDAO calendarPlanDao) {
		this.calendarPlanDao = calendarPlanDao;
	}

}
