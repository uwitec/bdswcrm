package com.sw.cms.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sw.cms.dao.base.JdbcBaseDAO;

/**
 * 应收汇总、应付汇总
 * @author jinyanni
 *
 */
public class ClientWlStatDAO extends JdbcBaseDAO {
	
	
	/**
	 * 取用户期初，包括应收期初、应付期初
	 * @param cdate
	 * @return Map key:client_id+应收（应付） value:期初值
	 */
	public Map getClientQc(String cdate){
		Map<String,Object> qcMap = new HashMap<String,Object>();
		
		String sql = "select * from client_qc where cdate='" + cdate + "'";
		
		List list = this.getResultList(sql);
		
		String client_id = "";
		if(list != null && list.size() > 0){
			for(int i=0;i<list.size();i++){
				Map tempMap = (Map)list.get(i);
				client_id = (String)tempMap.get("client_name");
				
				qcMap.put(client_id+"应收", tempMap.get("ysqc"));
				qcMap.put(client_id+"应付", tempMap.get("yfqc"));
			}
		}
		
		return qcMap;
	}
	
	
	/**
	 * 取用户往来所有情况
	 * @param start_date
	 * @param end_date
	 * @param client_id
	 * @return map key:client_id+je_type  value:发生金额
	 */
	public Map getClientWlInfo(String start_date,String end_date,String client_id){
		Map<String,Object> map = new HashMap<String,Object>();
		
		String sql = "select client_id,je_type,sum(fsje) as fsje from view_client_wl_info where 1=1";
		
		if(!start_date.equals("")){
			sql += " and cdate>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			sql += " and cdate<='" + end_date + "'";
		}
		if(!client_id.equals("")){
			sql += " and client_id='" + client_id + "'";
		}
		
		sql += " group by client_id,je_type";
		
		List list = this.getResultList(sql);
		
		String je_type = "";
		if(list != null && list.size() > 0){
			for(int i=0;i<list.size();i++){
				Map tempMap = (Map)list.get(i);
				client_id = (String)tempMap.get("client_id");
				je_type = (String)tempMap.get("je_type");
				
				map.put(client_id+je_type, tempMap.get("fsje"));
			}
		}
		
		return map;
	}
	
	
	/**
	 * 应收最长超期天数
	 * @param client_id
	 * @return
	 */
	public Map getClientMaxCqts(String client_id){
		
		Map map = new HashMap();
		
		String sql = "select client_name,max(TO_DAYS(now()) - TO_DAYS(ysrq)) as cqts from xsd where state='已出库' and skxs<>'已收'";
		if(!client_id.equals("")){
			sql += " and client_name='" + client_id + "'";
		}
		sql += " group by client_name having cqts>0";
		
		List list = this.getResultList(sql);

		if(list != null && list.size() > 0){
			for(int i=0;i<list.size();i++){
				Map tempMap = (Map)list.get(i);
				client_id = (String)tempMap.get("client_name");
				map.put(client_id, tempMap.get("cqts"));
			}
		}
		
		return map;
		
	}
	
	
	/**
	 * 客户未到期应收款
	 * @param client_id
	 * @return
	 */
	public Map getClientWdqysk(String client_id){
		Map map = new HashMap();
		
		String sql = "select client_name,sum(sjcjje-skje) as wdqysk from xsd where state='已出库' and skxs<>'已收' and (TO_DAYS(now()) - TO_DAYS(ysrq))<=0";
		
		if(!client_id.equals("")){
			sql += " and client_name='" + client_id + "'";
		}
		
		sql += " group by client_name";
		
		List list = this.getResultList(sql);

		if(list != null && list.size() > 0){
			for(int i=0;i<list.size();i++){
				Map tempMap = (Map)list.get(i);
				client_id = (String)tempMap.get("client_name");
				map.put(client_id, tempMap.get("wdqysk"));
			}
		}
		
		return map;
	}
	
	
	/**
	 * 客户超期应收款
	 * @param client_id
	 * @return
	 */
	public Map getClientCqysk(String client_id){
		
		Map map = new HashMap();
		
		String sql = "select client_name,sum(sjcjje-skje) as cqysk from xsd where state='已出库' and skxs<>'已收' and (TO_DAYS(now()) - TO_DAYS(ysrq))>0";
		
		if(!client_id.equals("")){
			sql += " and client_name='" + client_id + "'";
		}
		
		sql += " group by client_name";
		
		List list = this.getResultList(sql);

		if(list != null && list.size() > 0){
			for(int i=0;i<list.size();i++){
				Map tempMap = (Map)list.get(i);
				client_id = (String)tempMap.get("client_name");
				map.put(client_id, tempMap.get("cqysk"));
			}
		}
		
		return map;
		
	}
	
	
	/**
	 * 客户预收款
	 * @param client_id
	 * @return
	 */
	public Map getClientYushouk(String client_id){
		Map map = new HashMap();
		
		String sql = "select client_name,(sum(hjje)-sum(jsje)) as ysk from yushouk_list where round(hjje,2)<>round(jsje,2)";
		
		if(!client_id.equals("")){
			sql += " and client_name='" + client_id + "'";
		}
		
		sql += " group by client_name";
		
		List list = this.getResultList(sql);

		if(list != null && list.size() > 0){
			for(int i=0;i<list.size();i++){
				Map tempMap = (Map)list.get(i);
				client_id = (String)tempMap.get("client_name");
				map.put(client_id, tempMap.get("ysk"));
			}
		}
		
		return map;
	}
	
	
	/**
	 * 客户期初应收结算余额
	 * @param client_id
	 * @return
	 */
	public Map getClientQcjsye(String client_id){
		Map map = new HashMap();
		
		String sql = "select client_name,sum(ysqc-yishouje) as qcjsye from client_wl_init where 1=1";
		
		if(!client_id.equals("")){
			sql += " and client_name='" + client_id + "'";
		}
		
		sql += " group by client_name";
		
		List list = this.getResultList(sql);

		if(list != null && list.size() > 0){
			for(int i=0;i<list.size();i++){
				Map tempMap = (Map)list.get(i);
				client_id = (String)tempMap.get("client_name");
				map.put(client_id, tempMap.get("qcjsye"));
			}
		}
		
		return map;
	}
	
	
	/**
	 * 往来调账（应收）结算余额
	 * @param client_id
	 * @return
	 */
	public Map getClientTzjsye(String client_id){
		Map map = new HashMap();
		
		String sql = "select client_name,sum(pzje-jsje) as tzjsje from pz where state='已提交' and type='应收'";
		
		if(!client_id.equals("")){
			sql += " and client_name='" + client_id + "'";
		}
		
		sql += " group by client_name";
		
		List list = this.getResultList(sql);

		if(list != null && list.size() > 0){
			for(int i=0;i<list.size();i++){
				Map tempMap = (Map)list.get(i);
				client_id = (String)tempMap.get("client_name");
				map.put(client_id, tempMap.get("tzjsje"));
			}
		}
		
		return map;
	}
	
	
	/**
	 * 应付最长超期天数
	 * @param client_id
	 * @return
	 */
	public Map getClientYfMaxCqts(String client_id){
		
		Map map = new HashMap();
		
		String sql = "select gysbh,max(TO_DAYS(now()) - TO_DAYS(yfrq)) as cqts from jhd where state='已入库' and fklx<>'已付'";
		if(!client_id.equals("")){
			sql += " and gysbh='" + client_id + "'";
		}
		sql += " group by gysbh having cqts>0";
		
		List list = this.getResultList(sql);

		if(list != null && list.size() > 0){
			for(int i=0;i<list.size();i++){
				Map tempMap = (Map)list.get(i);
				client_id = (String)tempMap.get("gysbh");
				map.put(client_id, tempMap.get("cqts"));
			}
		}
		
		return map;		
	}
	
	
	/**
	 * 供应商未到期应付款
	 * @param client_id
	 * @return
	 */
	public Map getClientWdqyfk(String client_id){
		Map map = new HashMap();
		
		String sql = "select gysbh,sum(total-fkje) as wdqyfk from jhd where state='已入库' and fklx<>'已付' and (TO_DAYS(now()) - TO_DAYS(yfrq))<=0";
		
		if(!client_id.equals("")){
			sql += " and gysbh='" + client_id + "'";
		}
		
		sql += " group by gysbh";
		
		List list = this.getResultList(sql);

		if(list != null && list.size() > 0){
			for(int i=0;i<list.size();i++){
				Map tempMap = (Map)list.get(i);
				client_id = (String)tempMap.get("gysbh");
				map.put(client_id, tempMap.get("wdqyfk"));
			}
		}
		
		return map;
	}
	
	
	/**
	 * 供应商超期应付款
	 * @param client_id
	 * @return
	 */
	public Map getClientCqyfk(String client_id){
		
		Map map = new HashMap();
		
		String sql = "select gysbh,sum(total-fkje) as cqyfk from jhd where state='已出库' and fklx<>'已付' and (TO_DAYS(now()) - TO_DAYS(yfrq))>0";
		
		if(!client_id.equals("")){
			sql += " and gysbh='" + client_id + "'";
		}
		
		sql += " group by gysbh";
		
		List list = this.getResultList(sql);

		if(list != null && list.size() > 0){
			for(int i=0;i<list.size();i++){
				Map tempMap = (Map)list.get(i);
				client_id = (String)tempMap.get("gysbh");
				map.put(client_id, tempMap.get("cqyfk"));
			}
		}
		
		return map;
		
	}
	
	
	/**
	 * 供应商预付款
	 * @param client_id
	 * @return
	 */
	public Map getClientYufuk(String client_id){
		Map map = new HashMap();
		
		String sql = "select client_name,(sum(hjje)-sum(jsje)) as yfk from yufuk_list where round(hjje,2)<>round(jsje,2)";
		
		if(!client_id.equals("")){
			sql += " and client_name='" + client_id + "'";
		}
		
		sql += " group by client_name";
		
		List list = this.getResultList(sql);

		if(list != null && list.size() > 0){
			for(int i=0;i<list.size();i++){
				Map tempMap = (Map)list.get(i);
				client_id = (String)tempMap.get("client_name");
				map.put(client_id, tempMap.get("yfk"));
			}
		}
		
		return map;
	}
	
	
	/**
	 * 供应商期初应付结算余额
	 * @param client_id
	 * @return
	 */
	public Map getClientQcyfjsye(String client_id){
		Map map = new HashMap();
		
		String sql = "select client_name,sum(yfqc-yifuje) as qcyfjsye from client_wl_init where 1=1";
		
		if(!client_id.equals("")){
			sql += " and client_name='" + client_id + "'";
		}
		
		sql += " group by client_name";
		
		List list = this.getResultList(sql);

		if(list != null && list.size() > 0){
			for(int i=0;i<list.size();i++){
				Map tempMap = (Map)list.get(i);
				client_id = (String)tempMap.get("client_name");
				map.put(client_id, tempMap.get("qcyfjsye"));
			}
		}
		
		return map;
	}
	
	
	/**
	 * 往来调账（应付）结算余额
	 * @param client_id
	 * @return
	 */
	public Map getClientTzyfjsye(String client_id){
		Map map = new HashMap();
		
		String sql = "select client_name,sum(pzje-jsje) as tzjsje from pz where state='已提交' and type='应付'";
		
		if(!client_id.equals("")){
			sql += " and client_name='" + client_id + "'";
		}
		
		sql += " group by client_name";
		
		List list = this.getResultList(sql);

		if(list != null && list.size() > 0){
			for(int i=0;i<list.size();i++){
				Map tempMap = (Map)list.get(i);
				client_id = (String)tempMap.get("client_name");
				map.put(client_id, tempMap.get("tzjsje"));
			}
		}
		
		return map;
	}

}
