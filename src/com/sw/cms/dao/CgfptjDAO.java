package com.sw.cms.dao;

import java.util.List;
import java.util.Map;

import com.sw.cms.dao.base.JdbcBaseDAO;

/**
 * �ͻ��������˵�
 * �б��ֶΰ�����(���ڡ�ҵ�����͡����ݺš���Ʒ���ơ���Ʒ���������Ӧ�տ�����տ��ĩӦ�ա�Ӧ����ɹ������ĩӦ��)
 * @author liyt
 * add ad 2008-09-15
 */
public class CgfptjDAO extends JdbcBaseDAO {
	
	
	/**
	 * ���ݲ�ѯ�����ɹ���Ʊ�б�
	 * ����δ��⡢�����
	 * �б��ֶΰ�����(��λ���ơ������������)
	 * @param start_date
	 * @param end_date
	 * @param client_name
	 * @return
	 */
	public List getCgfpHzList(String start_date,String end_date,String client_name){
		
		//�ɹ���Ʊ
		String rk_sql = "select count(*) as cgnums,sum(a.total) as cgmoney,b.name as gysmc,a.gysbh,a.state from cgfpd a  left join clients b on a.gysbh=b.id  where 1=1 ";
		if(!start_date.equals("")){
			rk_sql = rk_sql + " and DATE_FORMAT(a.cg_date,'%Y-%m-%d')>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			rk_sql = rk_sql + " and DATE_FORMAT(a.cg_date,'%Y-%m-%d')<='" + (end_date + " 23:59:59") + "'";
		}
		if(!client_name.equals("")){
			rk_sql = rk_sql + " and b.name = '" + client_name + "'";
		}
		
		rk_sql = rk_sql +" group by a.gysbh,b.name,a.state order by a.gysbh  asc,a.state desc";
				
		return this.getResultList(rk_sql);
	}
	
	
	/**
	 * ���زɹ���Ʊ��ϸ
	 * @param gysmc
	 * @param start_date
	 * @param end_date
	 * @return
	 */
	public List getCgfpSeqList(String client_name,String start_date,String end_date){
		String sql = "select * from cgfpd where 1=1";
		
		if(!client_name.equals("")){
			sql = sql + " and gysbh='" + client_name + "'";
		}
		if(!start_date.equals("")){
			sql = sql + " and cg_date>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			sql = sql + " and cg_date<='" + (end_date+" 23:59:59") + "'";
		}
		
		return this.getResultList(sql);
	}
	

}
