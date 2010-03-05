package com.sw.cms.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sw.cms.dao.base.JdbcBaseDAO;

/**
 * Ӧ�ջ��ܡ�Ӧ������
 * @author jinyanni
 *
 */
public class ClientWlStatDAO extends JdbcBaseDAO {
	
	
	/**
	 * ȡ�û��ڳ�������Ӧ���ڳ���Ӧ���ڳ�
	 * @param cdate
	 * @return Map key:client_id+Ӧ�գ�Ӧ���� value:�ڳ�ֵ
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
				
				qcMap.put(client_id+"Ӧ��", tempMap.get("ysqc"));
				qcMap.put(client_id+"Ӧ��", tempMap.get("yfqc"));
			}
		}
		
		return qcMap;
	}
	
	
	/**
	 * ȡ�û������������
	 * @param start_date
	 * @param end_date
	 * @param client_id
	 * @return map key:client_id+je_type  value:�������
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
	 * ���������
	 * @param client_id
	 * @return
	 */
	public Map getClientMaxCqts(String client_id){
		
		Map map = new HashMap();
		
		String sql = "select client_name,max(TO_DAYS(now()) - TO_DAYS(ysrq)) as cqts from xsd where state='�ѳ���' and skxs<>'����'";
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
	 * �ͻ�δ����Ӧ�տ�
	 * @param client_id
	 * @return
	 */
	public Map getClientWdqysk(String client_id){
		Map map = new HashMap();
		
		String sql = "select client_name,sum(sjcjje-skje) as wdqysk from xsd where state='�ѳ���' and skxs<>'����' and (TO_DAYS(now()) - TO_DAYS(ysrq))<=0";
		
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
	 * �ͻ�����Ӧ�տ�
	 * @param client_id
	 * @return
	 */
	public Map getClientCqysk(String client_id){
		
		Map map = new HashMap();
		
		String sql = "select client_name,sum(sjcjje-skje) as cqysk from xsd where state='�ѳ���' and skxs<>'����' and (TO_DAYS(now()) - TO_DAYS(ysrq))>0";
		
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
	 * �ͻ�Ԥ�տ�
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
	 * �ͻ��ڳ��������
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
	 * �������ˣ�Ӧ�գ��������
	 * @param client_id
	 * @return
	 */
	public Map getClientTzjsye(String client_id){
		Map map = new HashMap();
		
		String sql = "select client_name,sum(pzje-jsje) as tzjsje from pz where state='���ύ' and type='Ӧ��'";
		
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
