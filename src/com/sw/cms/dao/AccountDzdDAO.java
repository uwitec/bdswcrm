package com.sw.cms.dao;

import java.util.List;

import com.sw.cms.dao.base.JdbcBaseDAO;
import com.sw.cms.model.AccountDzd;

public class AccountDzdDAO extends JdbcBaseDAO {
	
	/**
	 * 添加账户流水明细
	 * @param dzd
	 */
	public void addDzd(AccountDzd dzd){
		String sql = "insert into account_dzd(account_id,jyje,zhye,remark,jy_date,jsr,czr,action_url) values(?,?,?,?,now(),?,?,?)";
		
		Object[] param = new Object[7];
		
		param[0] = dzd.getAccount_id();
		param[1] = dzd.getJyje();
		param[2] = dzd.getZhye();
		param[3] = dzd.getRemark();
		param[4] = dzd.getJsr();
		param[5] = dzd.getCzr();
		param[6] = dzd.getAction_url();
				
		this.getJdbcTemplate().update(sql,param);
	}
	
	
	/**
	 * 
	 * @param jy_date1
	 * @param jy_date2
	 * @return
	 */
	public List queryAccountDzd(String jy_date1,String jy_date2,String account_id){
		String sql = "select * from account_dzd where 1=1";
		
		return this.getJdbcTemplate().queryForList(sql);
	}
	
	
	/**
	 * 根据URL删除资金流水记录
	 * @param xssk_id
	 */
	public void delDzd(String xssk_id){
		String sql = "delete from account_dzd where action_url like '%" + xssk_id + "'";
		this.getJdbcTemplate().update(sql);
	}

}
