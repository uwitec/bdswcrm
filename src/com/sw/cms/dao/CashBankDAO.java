package com.sw.cms.dao;

/**
 * �ֽ����б���DAO
 * author by liyt
 * 2008-08-22
 */

import java.util.List;
import java.util.Map;

import com.sw.cms.dao.base.JdbcBaseDAO;

public class CashBankDAO extends JdbcBaseDAO {
	
	
	/**
	 * �����˻�ID�����˻��б�
	 * ����˻�IDΪ���򷵻������˻��б�
	 * @param account_id
	 * @return
	 */
	public List getAccountList(String account_id,String type){
		String sql = "select * from accounts where flag='1'";
		if(!account_id.equals("")){
			sql = sql + " and id='" + account_id + "'";
		}
		if(!type.equals("")){
			sql = sql + " and type='" + type + "'";
		}
		
		return this.getResultList(sql);
	}

	/**
	 * �����˻�ID���ڳ�����ȡ�˻��ڳ���Ϣ
	 * @param cdate
	 * @param account_id
	 * @return
	 */
	public Map getCashBankQc(String cdate,String account_id){
		Map map = null;

		String sql = "select * from account_qc where qc_date=? and account_id=?" ;
		
		Object[] param = new Object[2];
		param[0] = cdate;
		param[1] = account_id;
		
		List list = this.getJdbcTemplate().queryForList(sql, param);
		
		if(list != null && list.size()>0){
			map = (Map)list.get(0);
		}
		
		return map;
	}
	
	
	/**
	 * �����˻���ˮ��ϸ
	 * @param account_id
	 * @param start_date
	 * @param end_date
	 * @return
	 */
	public List getAccountSeqList(String account_id,String start_date,String end_date){
		String sql = "select * from account_dzd where 1=1";
		
		if(!account_id.equals("")){
			sql = sql + " and account_id='" + account_id + "'";
		}
		if(!start_date.equals("")){
			sql = sql + " and jy_date>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			sql = sql + " and jy_date<='" + (end_date+" 23:59:59") + "'";
		}
		
		return this.getResultList(sql);
	}
	
}
