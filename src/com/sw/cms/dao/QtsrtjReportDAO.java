package com.sw.cms.dao;

import java.util.List;

import com.sw.cms.dao.base.JdbcBaseDAO;

/**
 * ����ͳ�Ʊ���
 * @author liyt
 *
 */
public class QtsrtjReportDAO extends JdbcBaseDAO {
	
	/**
	 * ����ͳ�Ʒ������
	 * @param start_date  ��ʼʱ��
	 * @param end_date    ����ʱ��
	 * @param srlx     ��������
	 * @return
	 */
	public List getQtsrtjResult(String start_date,String end_date,String srlx){
		
		String con = " and state='���ύ'";
		if(!start_date.equals("")){
			con += " and sr_date>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			con += " and sr_date<='" + (end_date + " 23:59:59") + "'";
		}
		if(!srlx.equals("")){
			con += " and type like '" + srlx + "%'";
		}
		
		String sql = "select b.xm_id,a.type,sum(a.skje) as hjje" +
		" from qtsr a left join SJZD_XMXX b on a.type=b.xm_name where 1=1"+ con+" group by a.type,b.xm_id";
		
		return this.getResultList(sql);
	}
	
	
	/**
	 * ���������ϸ��Ϣ
	 * @param start_date  ��ʼʱ��
	 * @param end_date    ����ʱ��
	 * @param srlx     ��������
	 * @return
	 */
	public List getQtsrtjList(String start_date,String end_date,String srlx){
		String sql = "select * from qtsr where state='���ύ'";
		
		if(!start_date.equals("")){
			sql += " and sr_date>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			sql += " and sr_date<='" + (end_date + " 23:59:59") + "'";
		}
		
		if(!srlx.equals("")){
			sql += " and type like'" + srlx + "%'";
		}
		return this.getResultList(sql);
	}

}
