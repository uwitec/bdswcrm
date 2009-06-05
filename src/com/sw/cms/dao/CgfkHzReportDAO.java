package com.sw.cms.dao;

import java.util.List;

import com.sw.cms.dao.base.JdbcBaseDAO;

/**
 *采购付款汇总 
 * @author liyt
 *
 */

public class CgfkHzReportDAO extends JdbcBaseDAO {
	
	/**
	 * 取采购付款明细列表
	 * @param start_date
	 * @param end_date
	 * @param client_name
	 * @param jsr
	 * @param isYfk
	 * @param account_id
	 * @return
	 */
	public List getCgfkMxList(String start_date,String end_date,String client_name,String jsr,String isYfk,String account_id){
		String sql = "select a.id,DATE_FORMAT(a.cz_date,'%Y-%m-%d') as fk_date,a.fkzh,a.jsr,a.fkje,a.remark,a.is_yfk,b.name as client_name from cgfk a left join clients b on b.id=a.gysbh where state='已提交'";
		
		if(!start_date.equals("")){
			sql = sql + " and DATE_FORMAT(a.cz_date,'%Y-%m-%d')>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			sql = sql + " and DATE_FORMAT(a.cz_date,'%Y-%m-%d')<='" + (end_date + " 23:59:59") + "'";
		}
		if(!jsr.equals("")){
			sql = sql + " and a.jsr='" + jsr + "'";
		}
		if(!isYfk.equals("")){
			sql = sql + " and a.is_yfk='" + isYfk + "'";
		}
		if(!account_id.equals("")){
			sql = sql + " and a.fkzh='" + account_id + "'";
		}
		if(!client_name.equals("")){
			sql = sql + " and  b.name like '%" + client_name + "%'";
		}
		
		return this.getResultList(sql);
	}

}
