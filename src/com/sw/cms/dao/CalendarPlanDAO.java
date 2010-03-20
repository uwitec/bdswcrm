package com.sw.cms.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sw.cms.dao.base.BeanRowMapper;
import com.sw.cms.dao.base.JdbcBaseDAO;
import com.sw.cms.model.CalendarPlan;
import com.sw.cms.util.StringUtils;
import com.sw.cms.util.UUIDGenerator;

/**
 * �ճ̰���
 * @author liyt
 *
 */
public class CalendarPlanDAO extends JdbcBaseDAO {

	/**
	 * ���ݲ�ѯ����ȡ�ճ̰����б�
	 * @param con
	 * @return
	 */
	public List getCalendarPlans(String con){
		String sql = "select * from calendar_plan where 1=1";
		
		if(!con.equals("")){
			sql += con;
		}
		
		return this.getResultList(sql, new BeanRowMapper(CalendarPlan.class));
	}
	
	
	/**
	 * �����ճ̰���
	 * @param info
	 */
	public void updateCalendarPlan(CalendarPlan info){
		
		String sql = "";
		
		if(info.getId() == null || info.getId().equals("")){
			info.setId(UUIDGenerator.getUUID());
			//����
			sql = "insert into calendar_plan(cdate,start_time,end_time,address,content,is_remind,remind_time,grade,czr,cz_date,id)" +
					" values(?,?,?,?,?,?,?,?,?,now(),?)";
		}else{
			//�޸�
			sql = "update calendar_plan set cdate=?,start_time=?,end_time=?,address=?,content=?,is_remind=?,remind_time=?,grade=?,czr=?,cz_date=now() where id=?";
			
		}
		
		Object[] param = new Object[10];
		
		param[0] = info.getCdate();
		param[1] = info.getStart_time();
		param[2] = info.getEnd_time();
		param[3] = info.getAddress();
		param[4] = info.getContent();
		param[5] = info.getIs_remind();
		param[6] = info.getRemind_time();
		param[7] = info.getGrade();
		param[8] = info.getCzr();
		param[9] = info.getId();
		
		this.getJdbcTemplate().update(sql, param);
	}
	
	
	/**
	 * �����ճ̰��ű��ȡ�ճ���Ϣ
	 * @param id
	 * @return
	 */
	public CalendarPlan getCalendarPlan(String id){
		String sql = "select * from calendar_plan where id='" + id + "'";
		return (CalendarPlan)this.queryForObject(sql, new BeanRowMapper(CalendarPlan.class));
	}
	
	
	/**
	 * ɾ���ճ̰���
	 * @param id
	 */
	public void delCalendarPlan(String id){
		String sql = "delete from calendar_plan where id='" + id + "'";
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * ȡ��ǰ�������ճ̼�¼�������б�
	 * @param user_id
	 * @param year
	 * @param month
	 * @return
	 */
	public List<String> getDateHasPlan(String user_id,String year,String month){
		List<String> list = new ArrayList<String>();
		
		String start_date = year + "-" + month + "-01";
		String end_date = year + "-" + month + "-31";
		String sql = "select distinct cdate  from calendar_plan where czr='" + user_id + "' and cdate>='" + start_date + "' and cdate<='" + end_date + "'";
		
		List tempList = this.getResultList(sql);
		if(tempList != null && tempList.size() > 0){
			for(int i=0;i<tempList.size();i++){
				Map map = (Map)tempList.get(i);
				
				list.add(StringUtils.nullToStr(map.get("cdate")));
			}
		}
		
		return list;
	}
}
