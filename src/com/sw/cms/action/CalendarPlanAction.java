package com.sw.cms.action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.model.CalendarPlan;
import com.sw.cms.model.LoginInfo;
import com.sw.cms.service.CalendarPlanService;
import com.sw.cms.util.DateComFunc;

public class CalendarPlanAction extends BaseAction {
	
	private CalendarPlanService calendarPlanService;
	
	private String year = "";
	private String month = "";
	private String cdate = DateComFunc.getToday();
	
	private List cdateList = new ArrayList();
	private List calendarPlanList = new ArrayList();
	
	private CalendarPlan calendarPlan = new CalendarPlan();
	
	private String id = "";
	
	public String showCalendar(){
		
		LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		String user_id = info.getUser_id();
		
		if(year.equals("") || month.equals("")){
		
			//默认取当前日期
			Calendar calendar = Calendar.getInstance();
			int nYear = calendar.get(calendar.YEAR);//取当前年份       
			int nMonth = calendar.get(calendar.MONTH) + 1 ;//取当前月份
			
			//当前年月格式化
			String sYear = nYear + "";
			String sMonth = nMonth + "";
			if(sMonth.length() == 1){
				sMonth = "0" + sMonth;
			}
			
			cdateList = calendarPlanService.getDateHasPlan(user_id, sYear, sMonth);
		}else{
			if(month.length() == 1){
				month = "0" + month;
			}
			cdateList = calendarPlanService.getDateHasPlan(user_id, year, month);
		}
		
		return SUCCESS;
	}
	
	
	public String edit(){
		if(!id.equals("")){
			calendarPlan = calendarPlanService.getCalendarPlan(id);
		}else{
			calendarPlan.setCdate(cdate);
		}
		
		return SUCCESS;
	}
	
	public String update(){
		LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		String user_id = info.getUser_id();
		
		calendarPlan.setCzr(user_id);
		calendarPlanService.updateCalendarPlan(calendarPlan);
		return SUCCESS;
	}
	
	public String listPlan(){
		LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		String user_id = info.getUser_id();
		
		String con = " and czr='" + user_id + "' and cdate='" + cdate + "'";
		calendarPlanList = calendarPlanService.getCalendarPlans(con);
		
		return SUCCESS;
	}
	
	public String del(){
		calendarPlanService.delCalendarPlan(id);
		return SUCCESS;
	}

	public CalendarPlanService getCalendarPlanService() {
		return calendarPlanService;
	}

	public void setCalendarPlanService(CalendarPlanService calendarPlanService) {
		this.calendarPlanService = calendarPlanService;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public List getCdateList() {
		return cdateList;
	}

	public void setCdateList(List cdateList) {
		this.cdateList = cdateList;
	}


	public String getCdate() {
		return cdate;
	}


	public void setCdate(String cdate) {
		this.cdate = cdate;
	}


	public CalendarPlan getCalendarPlan() {
		return calendarPlan;
	}


	public void setCalendarPlan(CalendarPlan calendarPlan) {
		this.calendarPlan = calendarPlan;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public List getCalendarPlanList() {
		return calendarPlanList;
	}


	public void setCalendarPlanList(List calendarPlanList) {
		this.calendarPlanList = calendarPlanList;
	}

}
