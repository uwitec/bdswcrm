package com.sw.cms.dao;

import java.util.List;
import java.util.Map;

import com.sw.cms.dao.base.JdbcBaseDAO;

/**
 * <p>Ӧ�ջ��ܡ���ϸͳ��</p>
 * <p>ͳ�����������ͻ����ơ�������Ա</p> 
 * ������ֽ��˻�����ϸ����Ҫ�����ղ����ø�����ʾ
 * @author liyt
 *
 */

public class YsmxReportDAO extends JdbcBaseDAO {
	
	
	/**
	 * ���ݿͻ���ż�����ȡ�ڳ���
	 * @param client_name
	 * @param cdate
	 * @return  �ڳ�Ӧ�ն���޷���0
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
	 * ���ݿͻ���ż���ʼ����ȡ���ڷ�����
	 * @param client_name
	 * @param start_date
	 * @param end_date
	 * @return
	 */
	public double getBqfs(String client_name,String start_date,String end_date){
		double sjysje = 0;
		
		//���ڷ�����=�����ܶ�+�˻��ܶ�+�����ܶ�
		
		//���������۵����
		double xsdje = 0;
		String sql = "select sum(sjcjje) as xsdje from xsd where state='�ѳ���' and client_name='" + client_name + "' and DATE_FORMAT(cz_date,'%Y-%m-%d')>='" + start_date + "' and DATE_FORMAT(cz_date,'%Y-%m-%d')<='" + (end_date + " 23:59:59") + "'";
		List list = this.getResultList(sql);		
		if(list != null && list.size()>0){
			Map map = (Map)list .get(0);
			xsdje = map.get("xsdje")==null?0:((Double)map.get("xsdje")).doubleValue();
		}
		
		//�������˻������
		double thdje = 0;
		sql = "select sum((0-thdje)) as thdje from thd where state='�����' and client_name='" + client_name + "' and DATE_FORMAT(cz_date,'%Y-%m-%d')>='" + start_date + "' and DATE_FORMAT(cz_date,'%Y-%m-%d')<='" + (end_date + " 23:59:59") + "'";
		list = this.getResultList(sql);
		
		if(list != null && list.size()>0){
			Map map = (Map)list .get(0);
			thdje = map.get("thdje")==null?0:((Double)map.get("thdje")).doubleValue();
		}
		
		//�����������
		double tzje = 0l;
		sql = "select sum(pzje) as je from pz where state='���ύ' and type='Ӧ��' and client_name='" + client_name + "' and DATE_FORMAT(cz_date,'%Y-%m-%d')>='" + start_date + "' and DATE_FORMAT(cz_date,'%Y-%m-%d')<='" + (end_date + " 23:59:59") + "'";
		list = this.getResultList(sql);
		if(list != null && list.size()>0){
			Map tzMap = (Map)list .get(0);
			tzje = tzMap.get("je")==null?0:((Double)tzMap.get("je")).doubleValue();
		}	
		
		sjysje = xsdje + thdje + tzje;
		
		return sjysje;
	}
	
	
	/**
	 * ���ݿͻ���ż���ʼ����ȡ��������
	 * @param client_name
	 * @param start_date
	 * @param end_date
	 * @return
	 */
	public double getBqyishou(String client_name,String start_date,String end_date){
		
		//�������տ�
		double syys = 0;
		String sql = "select sum(skje) as skje from xssk where client_name='" + client_name + "' and DATE_FORMAT(cz_date,'%Y-%m-%d')>='" + start_date + "' and DATE_FORMAT(cz_date,'%Y-%m-%d')<='" + (end_date + " 23:59:59") + "' and state='���ύ'";
		List list = this.getResultList(sql);
		if(list != null && list.size()>0){
			Map map = (Map)list .get(0);
			syys = map.get("skje")==null?0:((Double)map.get("skje")).doubleValue();
		}
		
		return syys;
	}
	
	
	/**
	 * ��������ȡ������ϸ�б�
	 * @param client_name
	 * @param start_date
	 * @param end_date
	 * @return
	 */
	public List getYsMxList(String client_name,String start_date,String end_date){
		
		//���۵�����
		String xsd_sql = "select id as dj_id,'viewXsd.html?id=' as url,'����' as ywtype,DATE_FORMAT(cz_date,'%Y-%m-%d') as creatdate,fzr as jsr,sjcjje as je,cz_date from xsd where state='�ѳ���'";
		if(!client_name.equals("")){
			xsd_sql += " and client_name='" + client_name + "'";
		}
		if(!start_date.equals("")){
			xsd_sql += " and creatdate>='" + start_date + "'" ;
		}
		if(!end_date.equals("")){
			xsd_sql += " and creatdate<='" + (end_date + " 23:59:59") + "'";
		}
		
		//�˻�������
		String thd_sql = "select thd_id as dj_id,'viewThd.html?thd_id=' as url,'�˻�' as ywtype,DATE_FORMAT(cz_date,'%Y-%m-%d') as creatdate,th_fzr as jsr,(0-thdje) as je,cz_date from thd where state='�����'";
		if(!client_name.equals("")){
			thd_sql += " and client_name='" + client_name + "'";
		}
		if(!start_date.equals("")){
			thd_sql += " and th_date>='" + start_date + "'" ;
		}
		if(!end_date.equals("")){
			thd_sql += " and th_date<='" + (end_date + " 23:59:59") + "'";
		}
		
		//��������
		String wltj_sql = "select id as dj_id,'viewPz.html?id=' as url,'��������' as ywtype,DATE_FORMAT(cz_date,'%Y-%m-%d') as creatdate,jsr,pzje as je,cz_date from pz where state='���ύ' and type='Ӧ��'";
		if(!client_name.equals("")){
			wltj_sql += " and client_name='" + client_name + "'";
		}
		if(!start_date.equals("")){
			wltj_sql += " and pz_date>='" + start_date + "'" ;
		}
		if(!end_date.equals("")){
			wltj_sql += " and pz_date<='" + (end_date + " 23:59:59") + "'";
		}
		
		//�����տ�
		String xssk_sql = "select id as dj_id,'viewXssk.html?id=' as url,'�����տ�' as ywtype,DATE_FORMAT(cz_date,'%Y-%m-%d') as creatdate,jsr,(0-skje) as je,cz_date from xssk where state='���ύ'";
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
	 * ȡ�ͻ�Ԥ�տ�ϼ�ֵ
	 * @param client_name
	 * @param start_date
	 * @param end_date
	 * @return
	 */
	public double getClientYushoukHj(String client_name,String start_date,String end_date){
		double yushouk = 0;
		
		String sql = "select sum(skje) as hjje from xssk where state='���ύ' and is_ysk='��'";
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
