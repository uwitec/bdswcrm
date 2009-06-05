package com.sw.cms.dao;

import java.util.List;

import com.sw.cms.dao.base.JdbcBaseDAO;

public class DzdDAO extends JdbcBaseDAO {
	
	/**
	 * 根据查询条件查询对账单列表
	 * @param client_id
	 * @param s_date
	 * @param e_date
	 * @return
	 */
	public List getDzdList(String client_id,String s_date,String e_date){
		String sql = "select * from view_dzd where 1=1";
		
		if(!client_id.equals("")){
			sql = sql + " and client_id='" + client_id + "'";
		}
		if(!s_date.equals("")){
			sql = sql + " and jyrq>='" + s_date + "'";
		}
		if(!e_date.equals("")){
			sql = sql + " and jyrq<='" + e_date + "'";
		}
		
		sql = sql + "order by jyrq";
		
		return this.getResultList(sql);
	}

}
