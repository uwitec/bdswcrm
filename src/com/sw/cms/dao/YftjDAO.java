package com.sw.cms.dao;

import java.util.List;

import com.sw.cms.dao.base.JdbcBaseDAO;

public class YftjDAO extends JdbcBaseDAO {
	
	/**
	 * 应付列表
	 * @param yfrq1
	 * @param yfrq2
	 * @return
	 */
	public List getYftjList(String yfrq1,String yfrq2){
		String sql = "select gysbh,sum(yfk) as yfhj from view_yftj where 1=1";
		
		if(!yfrq1.equals("")){
			sql = sql + " and yfrq>='" + yfrq1 + "'";
		}
		if(!yfrq2.equals("")){
			sql = sql + " and yfrq<='" + yfrq2 + "'"; 
		}
		sql = sql + " group by gysbh order by yfhj desc";
		
		return this.getResultList(sql);
	}
	
	
	/**
	 * 应付明细
	 * @param gysbh
	 * @return
	 */
	public List getYftjMxList(String gysbh){
		String sql = "select * from view_yftj where gysbh='" + gysbh + "'";
		return this.getResultList(sql);
	}

}
