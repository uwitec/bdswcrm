package com.sw.cms.service;

import java.util.List;

import com.sw.cms.dao.CalendarPlanDAO;
import com.sw.cms.model.CalendarPlan;

/**
 * �ճ̰���
 * @author jinyanni
 *
 */
public class CalendarPlanService {

	private CalendarPlanDAO calendarPlanDao;
	
	/**
	 * ���ݲ�ѯ����ȡ�ճ̰����б�
	 * @param con
	 * @return
	 */
	public List getCalendarPlans(String con){
		return calendarPlanDao.getCalendarPlans(con);
	}
	
	
	/**
	 * �����ճ̰���
	 * @param info
	 */
	public void updateCalendarPlan(CalendarPlan info){
		calendarPlanDao.updateCalendarPlan(info);
	}
	
	
	/**
	 * ɾ���ճ̰���
	 * @param id
	 */
	public void delCalendarPlan(String id){
		calendarPlanDao.delCalendarPlan(id);
	}
	
	
	/**
	 * ȡ��ǰ�������ճ̼�¼�������б�
	 * @param user_id
	 * @param year
	 * @param month
	 * @return
	 */
	public List<String> getDateHasPlan(String user_id,String year,String month){
		return calendarPlanDao.getDateHasPlan(user_id, year, month);
	}
	
	
	/**
	 * �����ճ̰��ű��ȡ�ճ���Ϣ
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
