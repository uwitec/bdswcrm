package com.sw.cms.dao;

import java.util.List;

import com.sw.cms.dao.base.JdbcBaseDAO;

/**
 * 收入统计报表
 * @author liyt
 *
 */
public class QtsrtjReportDAO extends JdbcBaseDAO {
	
	/**
	 * 收入统计分类汇总
	 * @param start_date  开始时间
	 * @param end_date    结束时间
	 * @param srlx     收入类型
	 * @return
	 */
	public List getQtsrtjResult(String start_date,String end_date,String srlx){
		
		String con = " and state='已提交'";
		if(!start_date.equals("")){
			con += " and sr_date>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			con += " and sr_date<='" + (end_date + " 23:59:59") + "'";
		}
		if(!srlx.equals("")){
			con += " and type like '" + srlx + "%'";
		}
		
		String sql = "select b.xm_id,a.type,sum(a.skje) as hjje" +
		" from qtsr a left join SJZD_XMXX b on a.type=b.xm_name where 1=1"+ con+" group by a.type,b.xm_id";
		
		return this.getResultList(sql);
	}
	
	
	/**
	 * 收入汇总明细信息
	 * @param start_date  开始时间
	 * @param end_date    结束时间
	 * @param srlx     收入类型
	 * @return
	 */
	public List getQtsrtjList(String start_date,String end_date,String srlx){
		String sql = "select * from qtsr where state='已提交'";
		
		if(!start_date.equals("")){
			sql += " and sr_date>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			sql += " and sr_date<='" + (end_date + " 23:59:59") + "'";
		}
		
		if(!srlx.equals("")){
			sql += " and type like'" + srlx + "%'";
		}
		return this.getResultList(sql);
	}

}
