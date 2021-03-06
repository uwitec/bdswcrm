package com.sw.cms.dao;

/**
 * 现金银行报表DAO
 * author by liyt
 * 2008-08-22
 */

import java.util.List;
import java.util.Map;

import com.sw.cms.dao.base.JdbcBaseDAO;

public class CashBankDAO extends JdbcBaseDAO {
	
	
	/**
	 * 根据账户ID返回账户列表
	 * 如果账户ID为空则返回所有账户列表
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
	 * 根据账户ID，期初日期取账户期初信息
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
	 * 返回账户流水明细
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
	
	/**
	 * 根据日期取现金期初值
	 * @param cdate
	 * @return
	 */
	public List getBankQcByBatch(String cdate){
		String sql = "select account_id,qcje from account_qc where qc_date=?" ;
		return this.getResultList(sql, new Object[]{cdate});
	}
	
	
	/**
	 * 根据区间取账户交易记录
	 * @param start_date 开始时间
	 * @param end_date  结束时间
	 * @param flag  交易标志  0：支出  1：收入
	 * @return List
	 */
	public List getAccountSeqBatch(String start_date,String end_date,int flag){
		
		String sql = "select account_id,sum(jyje) as jyje from account_dzd where 1=1";
		
		if(!start_date.equals("")){
			sql = sql + " and jy_date>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			sql = sql + " and jy_date<='" + (end_date+" 23:59:59") + "'";
		}
		if(flag == 0){
			sql = sql + " and jyje<0";
		}else{
			sql = sql + " and jyje>=0";
		}
		sql  += " group by account_id";
		
		return this.getResultList(sql);
	}
	
}
