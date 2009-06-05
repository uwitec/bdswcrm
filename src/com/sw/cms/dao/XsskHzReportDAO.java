package com.sw.cms.dao;

import java.util.List;

import com.sw.cms.dao.base.JdbcBaseDAO;

/**
 * 销售收款汇总统计
 * @author liyt
 *
 */

public class XsskHzReportDAO extends JdbcBaseDAO {

	/**
	 * 取销售收款明细信息
	 * @param start_date
	 * @param end_date
	 * @param client_name
	 * @param jsr
	 * @param isYsk
	 * @param account_id
	 * @return
	 */
	public List getXsskMx(String start_date,String end_date,String client_name,String jsr,String isYsk,String account_id){
		String sql = "select a.client_name,a.id,a.jsr,DATE_FORMAT(a.cz_date,'%Y-%m-%d') as sk_date,a.skzh,a.is_ysk,a.skje,a.remark from xssk a left join clients b on b.id=a.client_name where a.state='已提交'";
		
		if(!start_date.equals("")){
			sql = sql + " and DATE_FORMAT(a.cz_date,'%Y-%m-%d')>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			sql = sql + " and DATE_FORMAT(a.cz_date,'%Y-%m-%d')<='" + (end_date + " 23:59:59") + "'";
		}
		if(!jsr.equals("")){
			sql = sql + " and jsr='" + jsr + "'";
		}
		if(!isYsk.equals("")){
			sql = sql + " and is_ysk='" + isYsk + "'";
		}
		if(!account_id.equals("")){
			sql = sql + " and skzh='" + account_id + "'";
		}
		if(!client_name.equals("")){
			sql = sql + " and (a.client_name like'%" + client_name + "%' or b.name like '%" + client_name + "%')";
		}


		return this.getResultList(sql);
	}
	
}
