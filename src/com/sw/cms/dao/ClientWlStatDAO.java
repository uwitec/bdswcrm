package com.sw.cms.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sw.cms.dao.base.JdbcBaseDAO;

/**
 * �ͻ��������ܣ�����Ӧ�ջ��ܡ�Ӧ������
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

}
