package com.sw.cms.dao;

import java.util.List;

import com.sw.cms.dao.base.JdbcBaseDAO;

public class YstjDAO extends JdbcBaseDAO {
	
	
	/**
	 * 应收统计
	 * @param ysrq1
	 * @param ysrq2
	 * @return
	 */
	public List getYsktj(String ysrq1,String ysrq2){
		String sql = "select client_name,sum(ysk) as yshj from view_ysktj where 1=1";
		if(!ysrq1.equals("")){
			sql = sql + " and ysrq>='" + ysrq1 + "'";
		}
		if(!ysrq2.equals("")){
			sql = sql + " and ysrq<='" + ysrq2 + "'"; 
		}
		sql = sql + " group by client_name order by yshj desc";
		
		return this.getResultList(sql);
	}
	
	
	/**
	 * 应收统计明细
	 * @param client_name
	 * @return
	 */
	public List getYskMx(String client_name){
		String sql = "select * from view_ysktj where client_name='" + client_name + "'";
		return this.getResultList(sql);
	}

}
