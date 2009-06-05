package com.sw.cms.dao;

import java.util.List;
import java.util.Map;

import com.sw.cms.dao.base.JdbcBaseDAO;

/**
 * <p>应收汇总、明细统计</p>
 * <p>统计条件包括客户名称、销售人员</p> 
 * 如果是现金退货在明细中需要在已收部分用负数表示
 * @author liyt
 *
 */

public class YsmxReportDAO extends JdbcBaseDAO {
	
	
	/**
	 * 根据客户编号及日期取期初数
	 * @param client_name
	 * @param cdate
	 * @return  期初应收额，如无返回0
	 */
	public double getQc(String client_name,String cdate){
		double ysqc = 0;
		
		String sql = "select ysqc from client_qc where client_name='" + client_name + "' and cdate='" + cdate + "'";
		
		List list = this.getResultList(sql);
		
		if(list != null && list.size()>0){
			Map map = (Map)list .get(0);
			ysqc = map.get("ysqc")==null?0:((Double)map.get("ysqc")).doubleValue();
		}
		
		return ysqc;
	}
	
	
	/**
	 * 根据客户编号及起始日期取本期发生数
	 * @param client_name
	 * @param start_date
	 * @param end_date
	 * @return
	 */
	public double getBqfs(String client_name,String start_date,String end_date){
		double sjysje = 0;
		
		//本期发生数=销售总额+退货总额+调账总额
		
		//发生的销售单情况
		double xsdje = 0;
		String sql = "select sum(sjcjje) as xsdje from xsd where state='已出库' and client_name='" + client_name + "' and DATE_FORMAT(cz_date,'%Y-%m-%d')>='" + start_date + "' and DATE_FORMAT(cz_date,'%Y-%m-%d')<='" + (end_date + " 23:59:59") + "'";
		List list = this.getResultList(sql);		
		if(list != null && list.size()>0){
			Map map = (Map)list .get(0);
			xsdje = map.get("xsdje")==null?0:((Double)map.get("xsdje")).doubleValue();
		}
		
		//发生的退货单情况
		double thdje = 0;
		sql = "select sum((0-thdje)) as thdje from thd where state='已入库' and client_name='" + client_name + "' and DATE_FORMAT(cz_date,'%Y-%m-%d')>='" + start_date + "' and DATE_FORMAT(cz_date,'%Y-%m-%d')<='" + (end_date + " 23:59:59") + "'";
		list = this.getResultList(sql);
		
		if(list != null && list.size()>0){
			Map map = (Map)list .get(0);
			thdje = map.get("thdje")==null?0:((Double)map.get("thdje")).doubleValue();
		}
		
		//发生调账情况
		double tzje = 0l;
		sql = "select sum(pzje) as je from pz where state='已提交' and type='应收' and client_name='" + client_name + "' and DATE_FORMAT(cz_date,'%Y-%m-%d')>='" + start_date + "' and DATE_FORMAT(cz_date,'%Y-%m-%d')<='" + (end_date + " 23:59:59") + "'";
		list = this.getResultList(sql);
		if(list != null && list.size()>0){
			Map tzMap = (Map)list .get(0);
			tzje = tzMap.get("je")==null?0:((Double)tzMap.get("je")).doubleValue();
		}	
		
		sjysje = xsdje + thdje + tzje;
		
		return sjysje;
	}
	
	
	/**
	 * 根据客户编号及起始日期取本期已收
	 * @param client_name
	 * @param start_date
	 * @param end_date
	 * @return
	 */
	public double getBqyishou(String client_name,String start_date,String end_date){
		
		//所有已收款
		double syys = 0;
		String sql = "select sum(skje) as skje from xssk where client_name='" + client_name + "' and DATE_FORMAT(cz_date,'%Y-%m-%d')>='" + start_date + "' and DATE_FORMAT(cz_date,'%Y-%m-%d')<='" + (end_date + " 23:59:59") + "' and state='已提交'";
		List list = this.getResultList(sql);
		if(list != null && list.size()>0){
			Map map = (Map)list .get(0);
			syys = map.get("skje")==null?0:((Double)map.get("skje")).doubleValue();
		}
		
		return syys;
	}
	
	
	/**
	 * 根据条件取销售明细列表
	 * @param client_name
	 * @param start_date
	 * @param end_date
	 * @return
	 */
	public List getYsMxList(String client_name,String start_date,String end_date){
		
		//销售单部分
		String xsd_sql = "select id as dj_id,'viewXsd.html?id=' as url,'销售' as ywtype,DATE_FORMAT(cz_date,'%Y-%m-%d') as creatdate,fzr as jsr,sjcjje as je,cz_date from xsd where state='已出库'";
		if(!client_name.equals("")){
			xsd_sql += " and client_name='" + client_name + "'";
		}
		if(!start_date.equals("")){
			xsd_sql += " and creatdate>='" + start_date + "'" ;
		}
		if(!end_date.equals("")){
			xsd_sql += " and creatdate<='" + (end_date + " 23:59:59") + "'";
		}
		
		//退货单部分
		String thd_sql = "select thd_id as dj_id,'viewThd.html?thd_id=' as url,'退货' as ywtype,DATE_FORMAT(cz_date,'%Y-%m-%d') as creatdate,th_fzr as jsr,(0-thdje) as je,cz_date from thd where state='已入库'";
		if(!client_name.equals("")){
			thd_sql += " and client_name='" + client_name + "'";
		}
		if(!start_date.equals("")){
			thd_sql += " and th_date>='" + start_date + "'" ;
		}
		if(!end_date.equals("")){
			thd_sql += " and th_date<='" + (end_date + " 23:59:59") + "'";
		}
		
		//往来调账
		String wltj_sql = "select id as dj_id,'viewPz.html?id=' as url,'往来调账' as ywtype,DATE_FORMAT(cz_date,'%Y-%m-%d') as creatdate,jsr,pzje as je,cz_date from pz where state='已提交' and type='应收'";
		if(!client_name.equals("")){
			wltj_sql += " and client_name='" + client_name + "'";
		}
		if(!start_date.equals("")){
			wltj_sql += " and pz_date>='" + start_date + "'" ;
		}
		if(!end_date.equals("")){
			wltj_sql += " and pz_date<='" + (end_date + " 23:59:59") + "'";
		}
		
		//销售收款
		String xssk_sql = "select id as dj_id,'viewXssk.html?id=' as url,'销售收款' as ywtype,DATE_FORMAT(cz_date,'%Y-%m-%d') as creatdate,jsr,(0-skje) as je,cz_date from xssk where state='已提交'";
		if(!client_name.equals("")){
			xssk_sql += " and client_name='" + client_name + "'";
		}
		if(!start_date.equals("")){
			xssk_sql += " and sk_date>='" + start_date + "'" ;
		}
		if(!end_date.equals("")){
			xssk_sql += " and sk_date<='" + (end_date + " 23:59:59") + "'";
		}
		
		
		String sql = "select * from ((" + xsd_sql + ") union (" + thd_sql + ") union (" + wltj_sql + ") union (" + xssk_sql + ")) z order by creatdate asc";
		
		return this.getResultList(sql);
	}	
	
	
	/**
	 * 取客户预收款合计值
	 * @param client_name
	 * @param start_date
	 * @param end_date
	 * @return
	 */
	public double getClientYushoukHj(String client_name,String start_date,String end_date){
		double yushouk = 0;
		
		String sql = "select sum(skje) as hjje from xssk where state='已提交' and is_ysk='是'";
		if(!client_name.equals("")){
			sql += " and client_name='" + client_name + "'";
		}
		if(!start_date.equals("")){
			sql += " and DATE_FORMAT(cz_date,'%Y-%m-%d')>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			sql += " and DATE_FORMAT(cz_date,'%Y-%m-%d')<='" + (end_date + " 23:59:59") + "'";
		}
		
		List list = this.getResultList(sql);
		if(list != null && list.size()>0){
			Map map = (Map)list .get(0);
			yushouk = map.get("hjje")==null?0:((Double)map.get("hjje")).doubleValue();
		}
		
		return yushouk;
	}

}
