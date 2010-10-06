package com.sw.cms.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sw.cms.dao.base.JdbcBaseDAO;

/**
 * 业务员应收汇总
 * @author liyt
 *
 */
public class YshzJsrDAO extends JdbcBaseDAO {
	
	/**
	 * 取业务务应收变化期初信息<BR>
	 * 起始日期之前所有未收单据的未收部分的合计值做为期初信息
	 * @param start_date  起始日期
	 * @param client_name 客户名称
	 * @param jsr         经手人
	 * @return
	 */
	public Map getYshzJsrQc(String start_date,String client_name,String jsr){
		Map qcMap = new HashMap();
		
		String sql = "select fzr,sum(sjcjje-skje) as qcje from xsd where state='已出库' and (fzr<>'' and fzr is not null and fzr<>'null') and date_format(cz_date,'%Y-%m-%d')<'" + start_date + "'";
		
		if(!client_name.equals("")){
			sql += " and client_name='" + client_name + "'";
		}
		if(!jsr.equals("")){
			sql += " and fzr='" + jsr + "'";
		}
		
		sql += " group by fzr";
		
		List list = this.getResultList(sql);

		if(list != null && list.size() > 0){
			for(int i=0;i<list.size();i++){
				Map tempMap = (Map)list.get(i);
				qcMap.put(tempMap.get("fzr"), tempMap.get("qcje"));
			}
		}
		
		return qcMap;
	}
	
	
	/**
	 * 业务应收流水
	 * @param start_date  开始时间
	 * @param end_date    结束时间
	 * @param client_name 客户名称
	 * @param jsr         经手人
	 * @return
	 */
	public Map getYshzFlow(String start_date,String end_date,String client_name,String jsr){
		Map fsMap = new HashMap();
		
		String sql = "select fzr,sum(sjcjje) as bqfs,sum(skje) as bqsk from xsd where state='已出库' and (fzr<>'' and fzr is not null and fzr<>'null') and date_format(cz_date,'%Y-%m-%d')>='" + start_date + "' and date_format(cz_date,'%Y-%m-%d')<='" + end_date + "'";
		
		if(!client_name.equals("")){
			sql += " and client_name='" + client_name + "'";
		}
		if(!jsr.equals("")){
			sql += " and fzr='" + jsr + "'";
		}
		
		sql += " group by fzr";
		
		List list = this.getResultList(sql);
		
		if(list != null && list.size() > 0){
			for(int i=0;i<list.size();i++){
				Map tempMap = (Map)list.get(i);
				fsMap.put(tempMap.get("fzr") + "-01", tempMap.get("bqfs"));
				fsMap.put(tempMap.get("fzr") + "-02", tempMap.get("bqsk"));
			}
		}
		
		return fsMap;
	}
	
	
	/**
	 * 业务应收流水明细
	 * @param start_date  开始时间
	 * @param end_date    结束时间
	 * @param client_name 客户名称
	 * @param jsr         经手人
	 * @return
	 */
	public List getXsdList(String start_date,String end_date,String client_name,String jsr){
		Map fsMap = new HashMap();
		
		String sql = "select * from xsd where state='已出库' and (fzr<>'' and fzr is not null and fzr<>'null') and date_format(cz_date,'%Y-%m-%d')>='" + start_date + "' and date_format(cz_date,'%Y-%m-%d')<='" + end_date + "'";
		
		if(!client_name.equals("")){
			sql += " and client_name='" + client_name + "'";
		}
		if(!jsr.equals("")){
			sql += " and fzr='" + jsr + "'";
		}
		
		return this.getResultList(sql);
		
	}
	
	
	/**
	 * 业务员应收汇总表--应收列
	 * @return
	 */
	public Map getYwyYsjeMap(){
		Map ysMap = new HashMap();
		
		String sql = "select fzr,sum(sjcjje-skje) as ysje from xsd where state='已出库' group by fzr";
		
		List list = this.getResultList(sql);

		if(list != null && list.size() > 0){
			for(int i=0;i<list.size();i++){
				Map tempMap = (Map)list.get(i);
				ysMap.put(tempMap.get("fzr"), tempMap.get("ysje"));
			}
		}
		
		return ysMap;
	}
	
	
	/**
	 * 业务员应收汇总表--超期应收款
	 * @return
	 */
	public Map getYwyCqjeMap(){
		Map map = new HashMap();
		
		String sql = "select fzr,sum(sjcjje-skje) as cqysk from xsd where state='已出库' and skxs<>'已收' and (TO_DAYS(now()) - TO_DAYS(ysrq))>0 group by client_name";
		
		List list = this.getResultList(sql);

		if(list != null && list.size() > 0){
			for(int i=0;i<list.size();i++){
				Map tempMap = (Map)list.get(i);
				map.put(tempMap.get("fzr"), tempMap.get("cqysk"));
			}
		}
		return map;
	}
	
	
	/**
	 * 业务员应收汇总--最长账期、平均账期
	 * @param client_id
	 * @return
	 */
	public Map getYwyCqts(){
		
		Map map = new HashMap();
		
		String sql = "select fzr,max(TO_DAYS(now()) - TO_DAYS(creatdate)) as max_ts,avg(TO_DAYS(now()) - TO_DAYS(creatdate)) as avg_ts from xsd where state='已出库' and skxs<>'已收' group by fzr having max_ts>0 and avg_ts>0";
		
		List list = this.getResultList(sql);

		if(list != null && list.size() > 0){
			for(int i=0;i<list.size();i++){
				Map tempMap = (Map)list.get(i);
				map.put(tempMap.get("fzr")+"max", tempMap.get("max_ts"));
				map.put(tempMap.get("fzr")+"avg", tempMap.get("avg_ts"));
			}
		}
		
		return map;
		
	}

}
