package com.sw.cms.dao;

/**
 * ������ϸͳ��
 */

import java.util.List;

import com.sw.cms.dao.base.JdbcBaseDAO;

public class XsmxReportDAO extends JdbcBaseDAO {
	
	/**
	 * ȡ���۵��б�
	 * @param start_date ��ʼʱ��
	 * @param end_date����ʱ��
	 * @param client_name�ͻ�����
	 * @param xsry_id������Ա
	 * @param dj_id���ݱ��
	 * @return
	 */
	public List getXsdList(String start_date,String end_date,String client_name,String xsry_id,String dj_id,String dept_id){
		String sql = "select DATE_FORMAT(a.cz_date,'%Y-%m-%d') as creatdate,a.id,b.name as client_name,a.fzr,a.sjcjje as xsdje " +
				     "from xsd a left join clients b on b.id=a.client_name left join sys_user c on c.user_id=a.fzr where a.state='�ѳ���'";
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
		if(!dept_id.equals("")){
			sql = sql + " and c.dept like '" + dept_id + "%'";
		}

		return this.getResultList(sql);
	}
	
	
	
	/**
	 * �������۵����ȡ������ϸ��Ϣ
	 * @param xsd_id
	 * @return
	 */
	public List getXsdMxList(String xsd_id){
		String sql = "select * from xsd_product where xsd_id='" + xsd_id + "'";
		
		return this.getResultList(sql);
	}
	
	
	/**
	 * ���ݲ�ѯ����ȡ���۵��б�
	 * @param start_date ��ʼʱ��
	 * @param end_date  ��ֹʱ��
	 * @param client_id  �ͻ�����
	 * @return
	 */
	public List getLsdList(String start_date,String end_date,String client_name,String xsry_id,String dj_id,String dept_id){
		String sql = "select DATE_FORMAT(a.cz_date,'%Y-%m-%d') as creatdate,a.id,a.client_name,a.xsry,a.lsdje " +
				     "from lsd a left join sys_user b on user_id=a.xsry where a.state='���ύ'";
		
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
			sql = sql + " and a.client_name like'%" + client_name + "%'";
		}
		if(!xsry_id.equals("")){
			sql = sql + " and a.xsry='" + xsry_id + "'";
		}
		if(!dept_id.equals("")){
			sql = sql + " and b.dept like '" + dept_id + "%'";
		}
		return this.getResultList(sql);
	}
	
	
	/**
	 * �������۵����ȡ���۵�������ϸ
	 * @param lsd_id
	 * @return
	 */
	public List getLsdMxList(String lsd_id){
		String sql = "select * from lsd_product where lsd_id='" + lsd_id + "'";
		return this.getResultList(sql);
	}
	
	
	/**
	 * ȡ�˻����б�
	 * @param start_date
	 * @param end_date
	 * @param client_name
	 * @return
	 */
	public List getThdList(String start_date,String end_date,String client_name,String xsry_id,String dj_id,String dept_id){
		String sql = "select DATE_FORMAT(a.cz_date,'%Y-%m-%d') as th_date,a.thd_id,b.name as client_name,a.th_fzr,thdje " +
				     "from thd a left join clients b on b.id=a.client_name left join sys_user c on c.user_id=a.th_fzr where a.state='�����'";
		if(!dj_id.equals("")){
			sql = sql + " and a.thd_id='" + dj_id + "'";
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
			sql = sql + " and a.th_fzr='" + xsry_id + "'";
		}
		if(!dept_id.equals("")){
			sql = sql + " and c.dept like'" + dept_id + "%'";
		}
		return this.getResultList(sql);
	}
	
	
	/**
	 * �����˻������ȡ�˻�����ϸ
	 * @param thd_id
	 * @return
	 */
	public List getThdMxList(String thd_id){
		String sql = "select * from thd_product where thd_id='" + thd_id + "'";
		return this.getResultList(sql);
	}
	
	
	/**
	 * ȡδ�յ����б�
	 * @param start_date
	 * @param end_date
	 * @param dept_id
	 * @param xsry_id
	 * @return
	 */
	public List getWsdjList(String start_date,String end_date,String dept_id,String xsry_id,String client_name){
		String sql = "select a.creatdate,a.ysrq,a.id,a.client_name,a.kh_lxr,a.kh_lxdh,a.fzr,a.xsdje,(a.xsdje-a.skje) as je from xsd a left join sys_user b on b.user_id=a.fzr where a.state='�ѳ���' and a.skxs<>'����'";
		if(!start_date.equals("")){
			sql += " and DATE_FORMAT(a.cz_date,'%Y-%m-%d')>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			sql += " and DATE_FORMAT(a.cz_date,'%Y-%m-%d')<='" + (end_date + " 23:59:59") + "'"; 
		}
		if(!dept_id.equals("")){
			sql += " and b.dept like '" + dept_id + "%'";
		}
		if(!xsry_id.equals("")){
			sql += " and a.fzr='" + xsry_id + "'";
		}
		if(!client_name.equals("")){
			sql += " and a.client_name='"+ client_name +"'";
		}
		
		return this.getResultList(sql);
	}

}
