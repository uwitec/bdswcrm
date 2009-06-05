package com.sw.cms.dao;

import java.util.List;

import com.sw.cms.dao.base.JdbcBaseDAO;
import com.sw.cms.model.ClientWlInit;
import com.sw.cms.model.Page;

/**
 * �ͻ�������ʼ
 * @author liyt
 *
 */

public class ClientWlInitDAO extends JdbcBaseDAO {
	
	/**
	 * ȡ�ͻ�������ʼ�б�
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return ��ʼ��ҳ����
	 */
	public Page getWlInitList(String con,int curPage, int rowsPerPage){
		String sql = "select a.* from client_wl_init a left join clients b on b.id=a.client_name where 1=1";
		
		if(!con.equals("")){
			sql = sql + con;
		}
		
		sql += " order by a.cz_date desc";
		
		return this.getResultByPage(sql, curPage, rowsPerPage);
	}
	
	
	/**
	 * ����������ʼ��ֵ
	 * @param clientWlInit
	 */
	public void insertWlInitInfo(ClientWlInit clientWlInit){
		String sql = "insert into client_wl_init(client_name,ysqc,yfqc,remark,czr,cz_date) values(?,?,?,?,?,now())";
		
		Object[] param = new Object[5];
		
		param[0] = clientWlInit.getClient_name();
		param[1] = clientWlInit.getYsqc();
		param[2] = clientWlInit.getYfqc();
		param[3] = clientWlInit.getRemark();
		param[4] = clientWlInit.getCzr();
		
		this.getJdbcTemplate().update(sql,param);
		
	}
	
	
	/**
	 * ��������ʼֵ����ͻ��ڳ���
	 * @param clientWlInit
	 */
	public void insertClientQc(String client_name,double ysqc,double yfqc,String cdate){
		String sql = "delete from client_qc where client_name='" + client_name + "' and cdate='" + cdate + "'";
		this.getJdbcTemplate().update(sql);
		
		sql = "insert into client_qc(client_name,ysqc,yfqc,cdate) values(?,?,?,?)";
		
		Object[] param = new Object[4];
		
		param[0] = client_name;
		param[1] = ysqc;
		param[2] = yfqc;
		param[3] = cdate;
		
		this.getJdbcTemplate().update(sql,param);
	}
	
	
	/**
	 * ɾ���ͻ�������Ϣ
	 * @param id
	 */
	public void delClientWlInit(String id){
		String sql = "delete from client_wl_init where seq_id=" + id;
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * �����û�IDȡ��ʼ�б�
	 * @param client_name
	 * @return ��ʼ�б�
	 */
	public List getWlInitList(String client_name){
		String sql = "select * from client_wl_init where client_name='" + client_name + "'";
		
		return this.getResultList(sql);
	}

}
