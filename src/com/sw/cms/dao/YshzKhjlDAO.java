package com.sw.cms.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sw.cms.dao.base.JdbcBaseDAO;

/**
 * �ͻ�����Ӧ�ջ���
 * @author liyt
 *
 */
public class YshzKhjlDAO extends JdbcBaseDAO {
	
	/**
	 * ȡ�ڳ������Ϣ
	 * @param start_date   �ڳ�ʱ��
	 * @param client_name  �ͻ����
	 * @param khjl         �ͻ�����
	 * @return
	 */
	public Map getQc(String start_date,String client_name,String khjl){
		Map qcMap = new HashMap();
		
		String sql = "select b.khjl,sum(a.ysqc) as ysqc from client_qc a JOIN clients b on b.id=a.client_name where a.cdate='" + start_date + "'";
		
		if(!client_name.equals("")){
			sql += " and a.client_name='" + client_name + "'";
		}
		
		if(!khjl.equals("")){
			sql += " and b.khjl='" + khjl + "'";
		}
		
		sql += " group by b.khjl";
		
		List list = this.getResultList(sql);

		if(list != null && list.size() > 0){
			for(int i=0;i<list.size();i++){
				Map tempMap = (Map)list.get(i);
				qcMap.put(tempMap.get("khjl"), tempMap.get("ysqc"));
			}
		}
		
		return qcMap;
	}
	
	
	/**
	 * ȡ�ͻ����������������
	 * @param start_date   ��ʼ����
	 * @param end_date     ��������
	 * @param client_id    �ͻ����
	 * @param khjl         �ͻ�����
	 * @return map key:client_id+je_type  value:�������
	 */
	public Map getKhjlWlInfo(String start_date,String end_date,String client_name,String khjl){
		Map<String,Object> map = new HashMap<String,Object>();
		
		String sql = "select b.khjl,a.je_type,sum(a.fsje) as fsje from view_client_wl_info a join clients b on b.id=a.client_id where 1=1";
		
		if(!start_date.equals("")){
			sql += " and a.cdate>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			sql += " and a.cdate<='" + end_date + "'";
		}
		if(!client_name.equals("")){
			sql += " and a.client_id='" + client_name + "'";
		}
		if(!khjl.equals("")){
			sql += " and b.khjl='" + khjl + "'";
		}
		
		sql += " group by b.khjl,a.je_type";
		
		List list = this.getResultList(sql);
		
		if(list != null && list.size() > 0){
			for(int i=0;i<list.size();i++){
				Map tempMap = (Map)list.get(i);
				map.put(((String)tempMap.get("khjl")) + ((String)tempMap.get("je_type")), tempMap.get("fsje"));
			}
		}
		
		return map;
	}
	
	
	/**
	 * ȡ���пͻ������б�
	 * @param client_name  �ͻ����
	 * @param khjl         �ͻ�����
	 * @return
	 */
	public List getKhjlList(String client_name,String khjl){
		String sql = "select distinct b.user_id,b.real_name from clients a join sys_user b on b.user_id=a.khjl where 1=1";
		
		if(!client_name.equals("")){
			sql += " and a.id='" + client_name + "'";
		}
		if(!khjl.equals("")){
			sql += " and a.khjl='" + khjl + "'";
		}
		
		return this.getResultList(sql);
	}
	
	
	/**
	 * �ͻ�����Ӧ�ն��˵�
	 * @param client_name
	 * @param start_date
	 * @param end_date
	 * @return
	 */
	public List getKhjlYsDzd(String start_date,String end_date,String client_name,String khjl){
		String sql = "";
		
		//���۵��б�
		String xsd_sql = "select a.client_name as client_id,b.name as client_name,DATE_FORMAT(a.cz_date,'%Y-%m-%d') as creatdate,'����' as xwtype,a.id as dj_id,a.sjcjje as je,a.cz_date from xsd a join clients b on b.id=a.client_name where a.state='�ѳ���'";
		if(!start_date.equals("")){
			xsd_sql += " and DATE_FORMAT(a.cz_date,'%Y-%m-%d')>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			xsd_sql += " and DATE_FORMAT(a.cz_date,'%Y-%m-%d')<='" + (end_date + " 23:59:59") + "'";
		}
		if(!client_name.equals("")){
			xsd_sql += " and a.client_name = '" + client_name + "'";
		}
		if(!khjl.equals("")){
			xsd_sql += " and b.khjl='" + khjl + "'";
		}
		
		//�����˻�
		String thd_sql = "select a.client_name as client_id,b.name as client_name,DATE_FORMAT(a.cz_date,'%Y-%m-%d') as creatdate,'�����˻�' as xwtype,a.thd_id as dj_id,(0-a.thdje) as je,a.cz_date from thd a join clients b on b.id=a.client_name where a.state='�����'";
		if(!start_date.equals("")){
			thd_sql += " and DATE_FORMAT(a.cz_date,'%Y-%m-%d')>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			thd_sql += " and DATE_FORMAT(a.cz_date,'%Y-%m-%d')<='" + (end_date + " 23:59:59") + "'";
		}
		if(!client_name.equals("")){
			thd_sql += " and a.client_name = '" + client_name + "'";
		}
		if(!khjl.equals("")){
			thd_sql += " and b.khjl='" + khjl + "'";
		}
		
		//�����տ�
		String xssk_sql = "select a.client_name as client_id,b.name as client_name,DATE_FORMAT(a.cz_date,'%Y-%m-%d') as creatdate,'�տ�' as xwtype,a.id as dj_id,a.skje as je,a.cz_date from xssk a join clients b on b.id=a.client_name where state='���ύ'";
		if(!start_date.equals("")){
			xssk_sql += " and DATE_FORMAT(a.cz_date,'%Y-%m-%d')>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			xssk_sql += " and DATE_FORMAT(a.cz_date,'%Y-%m-%d')<='" + (end_date + " 23:59:59") + "'";
		}
		if(!client_name.equals("")){
			xssk_sql += " and a.client_name = '" + client_name + "'";
		}	
		if(!khjl.equals("")){
			xssk_sql += " and b.khjl='" + khjl + "'";
		}
		
		//��������(Ӧ��)
		String wltz_ys_sql = "select a.client_name as client_id,b.name as client_name,DATE_FORMAT(a.cz_date,'%Y-%m-%d') as creatdate,'��������(Ӧ��)' as xwtype,a.id as dj_id,a.pzje as je,a.cz_date from pz a join clients b on b.id=a.client_name where a.state='���ύ' and a.type='Ӧ��'";
		if(!start_date.equals("")){
			wltz_ys_sql += " and DATE_FORMAT(a.cz_date,'%Y-%m-%d')>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			wltz_ys_sql += " and DATE_FORMAT(a.cz_date,'%Y-%m-%d')<='" + (end_date + " 23:59:59") + "'";
		}
		if(!client_name.equals("")){
			wltz_ys_sql += " and a.client_name = '" + client_name + "'";
		}
		if(!khjl.equals("")){
			wltz_ys_sql += " and b.khjl='" + khjl + "'";
		}
		
		sql = "select * from ((" + xsd_sql + ") union (" + thd_sql + ") union (" + xssk_sql + ")  union (" + wltz_ys_sql + ")) m order by cz_date asc";
		
		return this.getResultList(sql);
	}

}
