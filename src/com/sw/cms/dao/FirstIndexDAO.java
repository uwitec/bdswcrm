package com.sw.cms.dao;

import java.util.List;

import com.sw.cms.dao.base.JdbcBaseDAO;

public class FirstIndexDAO extends JdbcBaseDAO {
	
	/**
	 * ���ش�������ⵥ�б�
	 * @return
	 */
	public List getDckdList(){
		String sql = "select * from ckd where state='������'";
		return this.getResultList(sql);
	}
	
	
	/**
	 * ȡ����Ӧ�տ��б�
	 * @return
	 */
	public List getCqyskList(){
		String sql = "select * from xsd where (state='���ύ' or state='�ѳ���') and skxs<>'����' and now()>DATE_FORMAT(CONCAT(ysrq ,' 23:59:59'),'%Y-%m-%d %H:%i:%s')";
		return this.getResultList(sql);
	}
	
	
	/**
	 * ȡ����Ӧ�����б�
	 * @return
	 */
	public List getCqYfkList(){
		String sql = "select * from jhd where state='���ύ' and fklx<>'�Ѹ�' and now()>DATE_FORMAT(CONCAT(yfrq ,' 23:59:59'),'%Y-%m-%d %H:%i:%s')";
		return this.getResultList(sql);
	}
	
	
	/**
	 * ȡ��������б�
	 * @return
	 */
	public List getKcxxList(){
		String sql = "SELECT * FROM view_query_kcxx where kc_nums<kcxx";
		return this.getResultList(sql);
	}

}
