package com.sw.cms.dao;

import java.util.List;
import java.util.Map;

import com.sw.cms.dao.base.JdbcBaseDAO;

public class DlsDzdDAO extends JdbcBaseDAO {
	
	/**
	 * ���ݲ�ѯ����ȡ�����̶��˵�
	 * @param start_date
	 * @param end_date
	 * @param client_name
	 * @return
	 */
	public List getDlsDzdList(String start_date,String end_date,String client_name){		
		String sql = "";
		
		//���۵��б�
		String xsd_sql = "select DATE_FORMAT(cz_date,'%Y-%m-%d') as creatdate,'�ɹ�' as xwtype,id as dj_id,sjcjje as je,cz_date from xsd where state='�ѳ���'";
		if(!start_date.equals("")){
			xsd_sql = xsd_sql + " and DATE_FORMAT(cz_date,'%Y-%m-%d')>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			xsd_sql = xsd_sql + " and DATE_FORMAT(cz_date,'%Y-%m-%d')<='" + (end_date + " 23:59:59") + "'";
		}
		if(!client_name.equals("")){
			xsd_sql = xsd_sql + " and client_name = '" + client_name + "'";
		}
		
		//�����˻�
		String thd_sql = "select DATE_FORMAT(cz_date,'%Y-%m-%d') as creatdate,'�ɹ��˻�' as xwtype,thd_id as dj_id,(0-thdje) as je,cz_date from thd where state='�����'";
		if(!start_date.equals("")){
			thd_sql = thd_sql + " and DATE_FORMAT(cz_date,'%Y-%m-%d')>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			thd_sql = thd_sql + " and DATE_FORMAT(cz_date,'%Y-%m-%d')<='" + (end_date + " 23:59:59") + "'";
		}
		if(!client_name.equals("")){
			thd_sql = thd_sql + " and client_name = '" + client_name + "'";
		}
		
		//�����տ�
		String xssk_sql = "select DATE_FORMAT(cz_date,'%Y-%m-%d') as creatdate,'����' as xwtype,id as dj_id,skje as je,cz_date from xssk where state='���ύ'";
		if(!start_date.equals("")){
			xssk_sql = xssk_sql + " and DATE_FORMAT(cz_date,'%Y-%m-%d')>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			xssk_sql = xssk_sql + " and DATE_FORMAT(cz_date,'%Y-%m-%d')<='" + (end_date + " 23:59:59") + "'";
		}
		if(!client_name.equals("")){
			xssk_sql = xssk_sql + " and client_name = '" + client_name + "'";
		}
		
		//��������(Ӧ��)
		String wltz_ys_sql = "select DATE_FORMAT(cz_date,'%Y-%m-%d') as creatdate,'��������(Ӧ��)' as xwtype,id as dj_id,pzje as je,cz_date from pz where state='���ύ' and type='Ӧ��'";
		if(!start_date.equals("")){
			wltz_ys_sql = wltz_ys_sql + " and DATE_FORMAT(cz_date,'%Y-%m-%d')>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			wltz_ys_sql = wltz_ys_sql + " and DATE_FORMAT(cz_date,'%Y-%m-%d')<='" + (end_date + " 23:59:59") + "'";
		}
		if(!client_name.equals("")){
			wltz_ys_sql = wltz_ys_sql + " and client_name = '" + client_name + "'";
		}
		
		
		
		sql = "select * from ((" + xsd_sql + ") union (" + thd_sql + ") union (" + xssk_sql + ") union (" + wltz_ys_sql + ")) m order by cz_date asc";
		
		return this.getResultList(sql);
	}
	
	
	/**
	 * ��ȡ������ϸ��Ϣ
	 * @param dj_id ���ݱ��
	 * @param cdid  �ӵ����
	 * @param xwtype ҵ������
	 * @return
	 */
	public List getDjMxList(String dj_id,String cdid,String xwtype){
		String sql = "";
		
		if(xwtype.equals("�ɹ�")){
			sql = "select product_id,product_name,product_xh,nums,((price+jgtz) * nums) as xj from xsd_product where xsd_id='" + dj_id + "'";
		}else if(xwtype.equals("�ɹ��˻�")){
			sql = "select product_id,product_name,product_xh,nums,(0-th_price * nums) as xj from thd_product where thd_id='" + dj_id + "'";
		}		
		
		return this.getResultList(sql);
	}
	
	
	/**
	 * ȡ�ͻ��ڳ���Ϣ
	 * @param client_name
	 * @param qc_date
	 * @return
	 */
	public Map getDlsQcInfo(String client_name,String cdate){
		Map map = null;
		String sql = "select * from client_qc where client_name='" + client_name + "' and cdate='" + cdate + "'";		
		List list = this.getResultList(sql);
		if(list != null && list.size()>0){
			map = (Map)list.get(0);
		}
		return map;
	}

}
