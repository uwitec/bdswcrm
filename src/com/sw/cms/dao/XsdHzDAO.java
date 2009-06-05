package com.sw.cms.dao;

import java.util.List;

import com.sw.cms.dao.base.JdbcBaseDAO;

/**
 *���۶�������ͳ�� 
 * @author liyt
 *
 */

public class XsdHzDAO extends JdbcBaseDAO {
	
	/**
	 * ȡ���۶����б�
	 * @param start_date ��ʼʱ��
	 * @param end_date����ʱ��
	 * @param client_name�ͻ�����
	 * @param xsry_id������Ա
	 * @param dj_id���ݱ��
	 * @return
	 */
	public List getXsdList(String start_date,String end_date,String client_name,String xsry_id,String dj_id){
		String sql = "select DATE_FORMAT(a.cz_date,'%Y-%m-%d') as creatdate,a.id,b.name as client_name,a.fzr,a.xsdje,a.sjcjje from xsd a left join clients b on b.id=a.client_name where a.state='�ѳ���'";
		if(!dj_id.equals("")){
			sql = sql + " and a.id='" + dj_id + "'";
		}		
		if(!start_date.equals("")){
			sql = sql + " and DATE_FORMAT(a.cz_date,'%Y-%m-%d')>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			sql = sql + " and DATE_FORMAT(a.cz_date,'%Y-%m-%d')<='" + (end_date + " 23:59:59") + "'";
		}
		if(!client_name.equals("")){
			sql = sql + " and b.name like'%" + client_name + "%'";
		}
		if(!xsry_id.equals("")){
			sql = sql + " and a.fzr='" + xsry_id + "'";
		}

		return this.getResultList(sql);
	}	

}
