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
	 * ȡӦ���ڳ�
	 * @param client_name �ͻ����
	 * @param cdate �ڳ�����
	 * @return �ڳ�ֵ
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
	 * ȡ���ڷ�����
	 * @param client_name �ͻ����
	 * @param start_date  ��ʼ����
	 * @param end_date    ��������
	 * @return ���ڷ�����
	 */
	public double getBqfs(String client_name,String start_date,String end_date){
		double bqfs = 0;
		
		//���ڲɹ���
		double bqcg = 0;
		String sql = "select sum(total) as bqcg from jhd where gysbh='" + client_name + "' and DATE_FORMAT(cz_date,'%Y-%m-%d')>='" + start_date + "' and DATE_FORMAT(cz_date,'%Y-%m-%d')<='" + (end_date + " 23:59:59") + "' and state='�����'";
		List list = this.getResultList(sql);		
		if(list != null && list.size()>0){
			Map map = (Map)list .get(0);
			bqcg = map.get("bqcg")==null?0:((Double)map.get("bqcg")).doubleValue();
		}
		
		
		//���ڲɹ��˻���
		double bqth = 0;
		sql = "select sum(tkzje) as bqth from cgthd where provider_name='" + client_name + "' and DATE_FORMAT(cz_date,'%Y-%m-%d')>='" + start_date + "' and DATE_FORMAT(cz_date,'%Y-%m-%d')<='" + (end_date + " 23:59:59") + "' and state='�ѳ���'";
		list = this.getResultList(sql);		
		if(list != null && list.size()>0){
			Map map = (Map)list .get(0);
			bqth = map.get("bqth")==null?0:((Double)map.get("bqth")).doubleValue();
		}
		
		
		//�����������
		double tzje = 0l;
		sql = "select sum(pzje) as je from pz where state='���ύ' and type='Ӧ��' and client_name='" + client_name + "' and DATE_FORMAT(cz_date,'%Y-%m-%d')>='" + start_date + "' and DATE_FORMAT(cz_date,'%Y-%m-%d')<='" + (end_date + " 23:59:59") + "'";
		list = this.getResultList(sql);
		if(list != null && list.size()>0){
			Map tzMap = (Map)list .get(0);
			tzje = tzMap.get("je")==null?0:((Double)tzMap.get("je")).doubleValue();
		}	
		
		//���ڷ���=���ڲɹ���-���ڲɹ��˻���+���˽��
		bqfs  = bqcg - bqth + tzje;
		
		return bqfs;
	}
	
	
	/**
	 * ȡ�����Ѹ���
	 * @param client_name �ͻ����
	 * @param start_date  ��ʼ����
	 * @param end_date    ��������
	 * @return �����Ѹ���
	 */
	public double getBqyf(String client_name,String start_date,String end_date){
		double bqyf = 0;
		
		String sql = "select sum(fkje) as bqyf from cgfk where gysbh='" + client_name + "' and DATE_FORMAT(cz_date,'%Y-%m-%d')>='" + start_date + "' and DATE_FORMAT(cz_date,'%Y-%m-%d')<='" + (end_date + " 23:59:59") + "' and state='���ύ'";
		List list = this.getResultList(sql);		
		if(list != null && list.size()>0){
			Map map = (Map)list .get(0);
			bqyf = map.get("bqyf")==null?0:((Double)map.get("bqyf")).doubleValue();
		}
		
		return bqyf;
	}
	
	
	/**
	 * ȡ��ϸ��Ϣ
	 * @param client_name
	 * @param start_date
	 * @param end_date
	 * @return
	 */
	public List getYfmxList(String client_name,String start_date,String end_date){
		
		String jhd_sql = "select id as dj_id,'viewJhd.html?id=' as url,'�ɹ����' as ywtype,cg_date as creatdate,fzr as jsr,total as je,cz_date from jhd where gysbh='" + client_name + "' and DATE_FORMAT(cz_date,'%Y-%m-%d')>='" + start_date + "' and DATE_FORMAT(cz_date,'%Y-%m-%d')<='" + (end_date + " 23:59:59") + "' and state='�����'";
		
		String cgthd_sql = "select id as dj_id,'viewCgthd.html?id=' as url,'�ɹ��˻���' as ywtype,th_date as creatdate,jsr,(0-tkzje) as je,cz_date from cgthd where provider_name='" + client_name + "' and DATE_FORMAT(cz_date,'%Y-%m-%d')>='" + start_date + "' and DATE_FORMAT(cz_date,'%Y-%m-%d')<='" + (end_date + " 23:59:59") + "' and state='�ѳ���'";
		
		String cgfk_sql = "select id as dj_id,'viewCgfk.html?id=' as url,'�ɹ�����' as ywtype,fk_date as creatdate,jsr,(0-fkje) as je,cz_date from cgfk where gysbh='" + client_name + "' and DATE_FORMAT(cz_date,'%Y-%m-%d')>='" + start_date + "' and DATE_FORMAT(cz_date,'%Y-%m-%d')<='" + (end_date + " 23:59:59") + "' and state='���ύ'";
		
		String wltz_sql = "select id as dj_id,'viewPz.html?id=' as url,'��������' as ywtype,pz_date as creatdate,jsr,pzje as je,cz_date from pz where state='���ύ' and type='Ӧ��' and client_name='" + client_name + "' and DATE_FORMAT(cz_date,'%Y-%m-%d')>='" + start_date + "' and DATE_FORMAT(cz_date,'%Y-%m-%d')<='" + (end_date + " 23:59:59") + "'";
		
		String sql = "select * from ((" + jhd_sql + ") union (" + cgthd_sql + ") union (" + cgfk_sql + ") union (" + wltz_sql + ")) x order by cz_date asc";
		
		return this.getResultList(sql);
	}
	
	
	/**
	 * Ӧ�����˵�
	 * @param client_name
	 * @param start_date
	 * @param end_date
	 * @return
	 */
	public List getYfDzd(String client_name,String start_date,String end_date){
		String sql = "";
		
		
		//�ɹ�������
		String jhd_sql = "select DATE_FORMAT(cz_date,'%Y-%m-%d') as creatdate,'�ɹ�' as xwtype,id as dj_id,total as je,cz_date from jhd where state='�����'";
		if(!start_date.equals("")){
			jhd_sql = jhd_sql + " and DATE_FORMAT(cz_date,'%Y-%m-%d')>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			jhd_sql = jhd_sql + " and DATE_FORMAT(cz_date,'%Y-%m-%d')<='" + (end_date + " 23:59:59") + "'";
		}
		if(!client_name.equals("")){
			jhd_sql = jhd_sql + " and gysbh = '" + client_name + "'";
		}
		
		//�ɹ��˻���
		String cgthd_sql = "select DATE_FORMAT(cz_date,'%Y-%m-%d') as creatdate,'�ɹ��˻�' as xwtype,id as dj_id,(0-tkzje) as je,cz_date from cgthd where state='�ѳ���'";
		if(!start_date.equals("")){
			cgthd_sql = cgthd_sql + " and DATE_FORMAT(cz_date,'%Y-%m-%d')>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			cgthd_sql = cgthd_sql + " and DATE_FORMAT(cz_date,'%Y-%m-%d')<='" + (end_date + " 23:59:59") + "'";
		}
		if(!client_name.equals("")){
			cgthd_sql = cgthd_sql + " and provider_name = '" + client_name + "'";
		}
		
		//�ɹ�����
		String cgfk_sql = "select DATE_FORMAT(cz_date,'%Y-%m-%d') as creatdate,'����' as xwtype,id as dj_id,fkje as je,cz_date from cgfk where state='���ύ'";
		if(!start_date.equals("")){
			cgfk_sql = cgfk_sql + " and DATE_FORMAT(cz_date,'%Y-%m-%d')>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			cgfk_sql = cgfk_sql + " and DATE_FORMAT(cz_date,'%Y-%m-%d')<='" + (end_date + " 23:59:59") + "'";
		}
		if(!client_name.equals("")){
			cgfk_sql = cgfk_sql + " and gysbh = '" + client_name + "'";
		}		
		
		//�������ˣ�Ӧ����
		String wltz_yf_sql = "select DATE_FORMAT(cz_date,'%Y-%m-%d') as creatdate,'��������(Ӧ��)' as xwtype,id as dj_id,pzje as je,cz_date from pz where state='���ύ' and type='Ӧ��'";
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
