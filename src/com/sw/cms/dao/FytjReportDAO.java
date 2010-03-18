package com.sw.cms.dao;

import java.util.List;

import com.sw.cms.dao.base.JdbcBaseDAO;

/**
 * ����ͳ�Ʊ���
 * @author liyt
 *
 */
public class FytjReportDAO extends JdbcBaseDAO {
	
	/**
	 * ȡ����ͳ����Ϣ
	 * @param start_date ��ʼʱ��
	 * @param end_date   ����ʱ��
	 * @param ywy        ҵ��Ա
	 * @param dept       ����
	 * @param fy_type    �������
	 * @return
	 */
	public List getFytjList(String start_date,String end_date,String dept,String fy_type){
		String sql = "select a.id,a.zc_date,a.type,a.zcje,a.zczh,a.sqr,a.ywy_dept from qtzc a left join sys_user b on b.user_id=a.ywy where a.state='���ύ'";
		
		if(!start_date.equals("")){
			sql += " and a.zc_date>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			sql += " and a.zc_date<='" + (end_date + " 23:59:59") + "'";
		}
		if(!dept.equals("")){
			sql += " and a.ywy_dept like '" + dept + "%'";
		}
		if(!fy_type.equals("")){
			sql += " and a.type like'" + fy_type + "%'";
		}
		return this.getResultList(sql);
	}
	
	
	/**
	 * ����ͳ�Ʒ������
	 */
	public List getFytjResult(String start_date,String end_date,String dept,int dj){
		
		String con = " and b.state='���ύ'";
		if(!start_date.equals("")){
			con += " and b.zc_date>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			con += " and b.zc_date<='" + (end_date + " 23:59:59") + "'";
		}
		if(!dept.equals("")){
			con += " and b.ywy_dept like '" + dept + "%'";
		}
		
		String sql = "select a.id,a.name," +
		"(select sum(zcje) from qtzc b where b.type like concat(a.id,'%') " + con + ") as hjje" +
		" from fy_type a where LENGTH(id)<=" + (dj*2);
		
		return this.getResultList(sql);
	}

}
