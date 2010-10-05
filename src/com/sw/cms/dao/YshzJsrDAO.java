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
	 * ȡҵ����Ӧ�ջ����ڳ���Ϣ<BR>
	 * ��ʼ����֮ǰ����δ�յ��ݵ�δ�ղ��ֵĺϼ�ֵ��Ϊ�ڳ���Ϣ
	 * @param start_date  ��ʼ����
	 * @param client_name �ͻ�����
	 * @param jsr         ������
	 * @return
	 */
	public Map getYshzJsrQc(String start_date,String client_name,String jsr){
		Map qcMap = new HashMap();
		
		String sql = "select fzr,sum(xsdje-skje) as qcje from xsd where state='�ѳ���' and (fzr<>'' and fzr is not null and fzr<>'null') and date_format(cz_date,'%Y-%m-%d')<'" + start_date + "'";
		
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
		
		String sql = "select fzr,sum(xsdje) as bqfs,sum(skje) as bqsk from xsd where state='�ѳ���' and (fzr<>'' and fzr is not null and fzr<>'null') and date_format(cz_date,'%Y-%m-%d')>='" + start_date + "' and date_format(cz_date,'%Y-%m-%d')<='" + end_date + "'";
		
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
	 * ҵ��Ӧ����ˮ
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

}
