package com.sw.cms.dao;

import java.util.List;
import java.util.Map;

import com.sw.cms.dao.base.JdbcBaseDAO;

/**
 * 
 * @author liyt
 * create at 2008-09-14
 */

public class YfHzMxDAO extends JdbcBaseDAO {
	
	/**
	 * 取应付期初
	 * @param client_name 客户编号
	 * @param cdate 期初日期
	 * @return 期初值
	 */
	public Map getYfQc(String client_name,String cdate){
		Map qc_map = null;
		
		String sql = "select yfqc from client_qc where client_name='" + client_name + "' and cdate='" + cdate + "'";
		
		List list = this.getResultList(sql);
		
		if(list != null && list.size()>0){
			qc_map = (Map)list .get(0);
			//yfqc = map.get("yfqc")==null?0:((Double)map.get("yfqc")).doubleValue();
		}
		
		return qc_map;
	}
	
	
	/**
	 * 取本期发生数
	 * @param client_name 客户编号
	 * @param start_date  开始日期
	 * @param end_date    结束日期
	 * @return 本期发生数
	 */
	public double getBqfs(String client_name,String start_date,String end_date){
		double bqfs = 0;
		
		//本期采购数
		double bqcg = 0;
		String sql = "select sum(total) as bqcg from jhd where gysbh='" + client_name + "' and DATE_FORMAT(cz_date,'%Y-%m-%d')>='" + start_date + "' and DATE_FORMAT(cz_date,'%Y-%m-%d')<='" + (end_date + " 23:59:59") + "' and state='已入库'";
		List list = this.getResultList(sql);		
		if(list != null && list.size()>0){
			Map map = (Map)list .get(0);
			bqcg = map.get("bqcg")==null?0:((Double)map.get("bqcg")).doubleValue();
		}
		
		
		//本期采购退货数
		double bqth = 0;
		sql = "select sum(tkzje) as bqth from cgthd where provider_name='" + client_name + "' and DATE_FORMAT(cz_date,'%Y-%m-%d')>='" + start_date + "' and DATE_FORMAT(cz_date,'%Y-%m-%d')<='" + (end_date + " 23:59:59") + "' and state='已出库'";
		list = this.getResultList(sql);		
		if(list != null && list.size()>0){
			Map map = (Map)list .get(0);
			bqth = map.get("bqth")==null?0:((Double)map.get("bqth")).doubleValue();
		}
		
		
		//发生调账情况
		double tzje = 0l;
		sql = "select sum(pzje) as je from pz where state='已提交' and type='应付' and client_name='" + client_name + "' and DATE_FORMAT(cz_date,'%Y-%m-%d')>='" + start_date + "' and DATE_FORMAT(cz_date,'%Y-%m-%d')<='" + (end_date + " 23:59:59") + "'";
		list = this.getResultList(sql);
		if(list != null && list.size()>0){
			Map tzMap = (Map)list .get(0);
			tzje = tzMap.get("je")==null?0:((Double)tzMap.get("je")).doubleValue();
		}	
		
		//本期发生=本期采购数-本期采购退货数+调账金额
		bqfs  = bqcg - bqth + tzje;
		
		return bqfs;
	}
	
	
	/**
	 * 取本期已付数
	 * @param client_name 客户编号
	 * @param start_date  开始日期
	 * @param end_date    结束日期
	 * @return 本期已付数
	 */
	public double getBqyf(String client_name,String start_date,String end_date){
		double bqyf = 0;
		
		String sql = "select sum(fkje) as bqyf from cgfk where gysbh='" + client_name + "' and DATE_FORMAT(cz_date,'%Y-%m-%d')>='" + start_date + "' and DATE_FORMAT(cz_date,'%Y-%m-%d')<='" + (end_date + " 23:59:59") + "' and state='已提交'";
		List list = this.getResultList(sql);		
		if(list != null && list.size()>0){
			Map map = (Map)list .get(0);
			bqyf = map.get("bqyf")==null?0:((Double)map.get("bqyf")).doubleValue();
		}
		
		return bqyf;
	}
	
	
	/**
	 * 取明细信息
	 * @param client_name
	 * @param start_date
	 * @param end_date
	 * @return
	 */
	public List getYfmxList(String client_name,String start_date,String end_date){
		
		String jhd_sql = "select id as dj_id,'viewJhd.html?id=' as url,'采购入库' as ywtype,cg_date as creatdate,fzr as jsr,total as je,cz_date from jhd where gysbh='" + client_name + "' and DATE_FORMAT(cz_date,'%Y-%m-%d')>='" + start_date + "' and DATE_FORMAT(cz_date,'%Y-%m-%d')<='" + (end_date + " 23:59:59") + "' and state='已入库'";
		
		String cgthd_sql = "select id as dj_id,'viewCgthd.html?id=' as url,'采购退货单' as ywtype,th_date as creatdate,jsr,(0-tkzje) as je,cz_date from cgthd where provider_name='" + client_name + "' and DATE_FORMAT(cz_date,'%Y-%m-%d')>='" + start_date + "' and DATE_FORMAT(cz_date,'%Y-%m-%d')<='" + (end_date + " 23:59:59") + "' and state='已出库'";
		
		String cgfk_sql = "select id as dj_id,'viewCgfk.html?id=' as url,'采购付款' as ywtype,fk_date as creatdate,jsr,(0-fkje) as je,cz_date from cgfk where gysbh='" + client_name + "' and DATE_FORMAT(cz_date,'%Y-%m-%d')>='" + start_date + "' and DATE_FORMAT(cz_date,'%Y-%m-%d')<='" + (end_date + " 23:59:59") + "' and state='已提交'";
		
		String wltz_sql = "select id as dj_id,'viewPz.html?id=' as url,'往来调账' as ywtype,pz_date as creatdate,jsr,pzje as je,cz_date from pz where state='已提交' and type='应付' and client_name='" + client_name + "' and DATE_FORMAT(cz_date,'%Y-%m-%d')>='" + start_date + "' and DATE_FORMAT(cz_date,'%Y-%m-%d')<='" + (end_date + " 23:59:59") + "'";
		
		String sql = "select * from ((" + jhd_sql + ") union (" + cgthd_sql + ") union (" + cgfk_sql + ") union (" + wltz_sql + ")) x order by cz_date asc";
		
		return this.getResultList(sql);
	}
	
	
	/**
	 * 应付对账单
	 * @param client_name
	 * @param start_date
	 * @param end_date
	 * @return
	 */
	public List getYfDzd(String client_name,String start_date,String end_date){
		String sql = "";
		
		
		//采购进货单
		String jhd_sql = "select DATE_FORMAT(cz_date,'%Y-%m-%d') as creatdate,'采购' as xwtype,id as dj_id,total as je,cz_date from jhd where state='已入库'";
		if(!start_date.equals("")){
			jhd_sql = jhd_sql + " and DATE_FORMAT(cz_date,'%Y-%m-%d')>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			jhd_sql = jhd_sql + " and DATE_FORMAT(cz_date,'%Y-%m-%d')<='" + (end_date + " 23:59:59") + "'";
		}
		if(!client_name.equals("")){
			jhd_sql = jhd_sql + " and gysbh = '" + client_name + "'";
		}
		
		//采购退货单
		String cgthd_sql = "select DATE_FORMAT(cz_date,'%Y-%m-%d') as creatdate,'采购退货' as xwtype,id as dj_id,(0-tkzje) as je,cz_date from cgthd where state='已出库'";
		if(!start_date.equals("")){
			cgthd_sql = cgthd_sql + " and DATE_FORMAT(cz_date,'%Y-%m-%d')>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			cgthd_sql = cgthd_sql + " and DATE_FORMAT(cz_date,'%Y-%m-%d')<='" + (end_date + " 23:59:59") + "'";
		}
		if(!client_name.equals("")){
			cgthd_sql = cgthd_sql + " and provider_name = '" + client_name + "'";
		}
		
		//采购付款
		String cgfk_sql = "select DATE_FORMAT(cz_date,'%Y-%m-%d') as creatdate,'付款' as xwtype,id as dj_id,fkje as je,cz_date from cgfk where state='已提交'";
		if(!start_date.equals("")){
			cgfk_sql = cgfk_sql + " and DATE_FORMAT(cz_date,'%Y-%m-%d')>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			cgfk_sql = cgfk_sql + " and DATE_FORMAT(cz_date,'%Y-%m-%d')<='" + (end_date + " 23:59:59") + "'";
		}
		if(!client_name.equals("")){
			cgfk_sql = cgfk_sql + " and gysbh = '" + client_name + "'";
		}		
		
		//往来调账（应付）
		String wltz_yf_sql = "select DATE_FORMAT(cz_date,'%Y-%m-%d') as creatdate,'往来调账(应付)' as xwtype,id as dj_id,pzje as je,cz_date from pz where state='已提交' and type='应付'";
		if(!start_date.equals("")){
			wltz_yf_sql = wltz_yf_sql + " and DATE_FORMAT(cz_date,'%Y-%m-%d')>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			wltz_yf_sql = wltz_yf_sql + " and DATE_FORMAT(cz_date,'%Y-%m-%d')<='" + (end_date + " 23:59:59") + "'";
		}
		if(!client_name.equals("")){
			wltz_yf_sql = wltz_yf_sql + " and client_name = '" + client_name + "'";
		}
		
		
		
		sql = "select * from ((" + jhd_sql + ") union(" + cgthd_sql + ") union(" + cgfk_sql + ") union(" + wltz_yf_sql + ")) m order by cz_date asc";
		
		return this.getResultList(sql);
	}

}
