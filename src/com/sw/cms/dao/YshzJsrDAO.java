package com.sw.cms.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sw.cms.dao.base.JdbcBaseDAO;

/**
 * ҵ��ԱӦ�ջ���
 * @author liyt
 *
 */
public class YshzJsrDAO extends JdbcBaseDAO {
	
	/**
	 * ȡҵ����Ӧ�ձ仯�ڳ���Ϣ<BR>
	 * ��ʼ����֮ǰ����δ�յ��ݵ�δ�ղ��ֵĺϼ�ֵ��Ϊ�ڳ���Ϣ
	 * @param start_date  ��ʼ����
	 * @param client_name �ͻ�����
	 * @param jsr         ������
	 * @return
	 */
	public Map getYshzJsrQc(String start_date,String client_name,String jsr){
		Map qcMap = new HashMap();
		
		String sql = "select fzr,sum(sjcjje-skje) as qcje from xsd where state='�ѳ���' and (fzr<>'' and fzr is not null and fzr<>'null') and date_format(cz_date,'%Y-%m-%d')<'" + start_date + "'";
		
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
	 * ҵ��Ӧ����ˮ
	 * @param start_date  ��ʼʱ��
	 * @param end_date    ����ʱ��
	 * @param client_name �ͻ�����
	 * @param jsr         ������
	 * @return
	 */
	public Map getYshzFlow(String start_date,String end_date,String client_name,String jsr){
		Map fsMap = new HashMap();
		
		String sql = "select fzr,sum(sjcjje) as bqfs,sum(skje) as bqsk from xsd where state='�ѳ���' and (fzr<>'' and fzr is not null and fzr<>'null') and date_format(cz_date,'%Y-%m-%d')>='" + start_date + "' and date_format(cz_date,'%Y-%m-%d')<='" + end_date + "'";
		
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
	 * ҵ��Ӧ����ˮ��ϸ
	 * @param start_date  ��ʼʱ��
	 * @param end_date    ����ʱ��
	 * @param client_name �ͻ�����
	 * @param jsr         ������
	 * @return
	 */
	public List getXsdList(String start_date,String end_date,String client_name,String jsr){
		Map fsMap = new HashMap();
		
		String sql = "select * from xsd where state='�ѳ���' and (fzr<>'' and fzr is not null and fzr<>'null') and date_format(cz_date,'%Y-%m-%d')>='" + start_date + "' and date_format(cz_date,'%Y-%m-%d')<='" + end_date + "'";
		
		if(!client_name.equals("")){
			sql += " and client_name='" + client_name + "'";
		}
		if(!jsr.equals("")){
			sql += " and fzr='" + jsr + "'";
		}
		
		return this.getResultList(sql);
		
	}
	
	
	/**
	 * ҵ��ԱӦ�ջ��ܱ�--Ӧ����
	 * @return
	 */
	public Map getYwyYsjeMap(){
		Map ysMap = new HashMap();
		
		String sql = "select fzr,sum(sjcjje-skje) as ysje from xsd where state='�ѳ���' group by fzr";
		
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
	 * ҵ��ԱӦ�ջ��ܱ�--����Ӧ�տ�
	 * @return
	 */
	public Map getYwyCqjeMap(){
		Map map = new HashMap();
		
		String sql = "select fzr,sum(sjcjje-skje) as cqysk from xsd where state='�ѳ���' and skxs<>'����' and (TO_DAYS(now()) - TO_DAYS(ysrq))>0 group by client_name";
		
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
	 * ҵ��ԱӦ�ջ���--����ڡ�ƽ������
	 * @param client_id
	 * @return
	 */
	public Map getYwyCqts(){
		
		Map map = new HashMap();
		
		String sql = "select fzr,max(TO_DAYS(now()) - TO_DAYS(creatdate)) as max_ts,avg(TO_DAYS(now()) - TO_DAYS(creatdate)) as avg_ts from xsd where state='�ѳ���' and skxs<>'����' group by fzr having max_ts>0 and avg_ts>0";
		
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
