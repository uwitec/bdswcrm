package com.sw.cms.dao;

import java.util.List;

import com.sw.cms.dao.base.JdbcBaseDAO;

/**
 *销售订单汇总统计 
 * @author liyt
 *
 */

public class XsdHzDAO extends JdbcBaseDAO {
	
	/**
	 * 取销售订单列表
	 * @param start_date 开始时间
	 * @param end_date结束时间
	 * @param client_name客户名称
	 * @param xsry_id销售人员
	 * @param dj_id单据编号
	 * @return
	 */
	public List getXsdList(String start_date,String end_date,String client_name,String xsry_id,String dj_id){
		String sql = "select DATE_FORMAT(a.cz_date,'%Y-%m-%d') as creatdate,a.id,b.name as client_name,a.fzr,a.xsdje,a.sjcjje from xsd a left join clients b on b.id=a.client_name where a.state='已出库'";
		if(!dj_id.equals("")){
			sql = sql + " and a.id='" + dj_id + "'";
		}		
		if(!start_date.equals("")){
			sql = sql + " and DATE_FORMAT(a.cz_date,'%Y-%m-%d')>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			sql = sql + " and DATE_FORMAT(a.cz_date,'%Y-%m-%d')<='" + (end_date + " 23:59:59") + "'";
		}
		if(!client_name.equals("")){
			sql = sql + " and b.name like'%" + client_name + "%'";
		}
		if(!xsry_id.equals("")){
			sql = sql + " and a.fzr='" + xsry_id + "'";
		}

		return this.getResultList(sql);
	}	

}
