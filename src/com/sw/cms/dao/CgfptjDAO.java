package com.sw.cms.dao;

import java.util.List;
import java.util.Map;

import com.sw.cms.dao.base.JdbcBaseDAO;

/**
 * 客户往来对账单
 * 列表字段包括：(日期、业务类型、单据号、商品名称、商品规格、数量、应收款、销售收款、期末应收、应付款、采购付款、期末应付)
 * @author liyt
 * add ad 2008-09-15
 */
public class CgfptjDAO extends JdbcBaseDAO {
	
	
	/**
	 * 根据查询条件采购发票列表
	 * 包括未入库、已入库
	 * 列表字段包括：(单位名称、单据数、金额)
	 * @param start_date
	 * @param end_date
	 * @param client_name
	 * @return
	 */
	public List getCgfpHzList(String start_date,String end_date,String client_name){
		
		//采购发票
		String rk_sql = "select count(*) as cgnums,sum(a.total) as cgmoney,b.name as gysmc,a.gysbh,a.state from cgfpd a  left join clients b on a.gysbh=b.id  where 1=1 ";
		if(!start_date.equals("")){
			rk_sql = rk_sql + " and DATE_FORMAT(a.cg_date,'%Y-%m-%d')>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			rk_sql = rk_sql + " and DATE_FORMAT(a.cg_date,'%Y-%m-%d')<='" + (end_date + " 23:59:59") + "'";
		}
		if(!client_name.equals("")){
			rk_sql = rk_sql + " and b.name = '" + client_name + "'";
		}
		
		rk_sql = rk_sql +" group by a.gysbh,b.name,a.state order by a.gysbh  asc,a.state desc";
				
		return this.getResultList(rk_sql);
	}
	
	
	/**
	 * 返回采购发票明细
	 * @param gysmc
	 * @param start_date
	 * @param end_date
	 * @return
	 */
	public List getCgfpSeqList(String client_name,String start_date,String end_date){
		String sql = "select * from cgfpd where 1=1";
		
		if(!client_name.equals("")){
			sql = sql + " and gysbh='" + client_name + "'";
		}
		if(!start_date.equals("")){
			sql = sql + " and cg_date>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			sql = sql + " and cg_date<='" + (end_date+" 23:59:59") + "'";
		}
		
		return this.getResultList(sql);
	}
	

}
