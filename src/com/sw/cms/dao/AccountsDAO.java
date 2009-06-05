package com.sw.cms.dao;

import java.util.List;
import java.util.Map;

import com.sw.cms.dao.base.JdbcBaseDAO;
import com.sw.cms.model.Accounts;
import com.sw.cms.util.DateComFunc;

public class AccountsDAO extends JdbcBaseDAO {
	
	/**
	 * 取账户列表
	 * @return
	 */
	public List getAccountList(){
		String sql = "select * from accounts";
		return this.getJdbcTemplate().queryForList(sql);
	}
	
	
	/**
	 * 保存账户信息
	 * @param accounts
	 */
	public void saveAccounts(Accounts accounts){
		String sql = "insert into accounts(name,type,bank,bank_count,remark,id) values(?,?,?,?,?,?)";
		
		Object[] param = new Object[6];
		
		String id = this.getAccountsId();
		
		param[0] = accounts.getName();
		param[1] = accounts.getType();
		param[2] = accounts.getBank();
		param[3] = accounts.getBank_count();
		param[4] = accounts.getRemark();
		param[5] = id;
		
		this.getJdbcTemplate().update(sql,param);
		
	}
	
	
	/**
	 * 更新账户信息
	 * @param accounts
	 */
	public void updateAccounts(Accounts accounts){
		String sql = "update accounts set name=?,type=?,bank=?,bank_count=?,remark=? where id=?";
		
		Object[] param = new Object[6];
		
		param[0] = accounts.getName();
		param[1] = accounts.getType();
		param[2] = accounts.getBank();
		param[3] = accounts.getBank_count();
		param[4] = accounts.getRemark();
		param[5] = accounts.getId();
		
		this.getJdbcTemplate().update(sql,param);
	}
	
	
	/**
	 * 取账号信息
	 * @param id
	 * @return
	 */
	public Map getAccounts(String id){
		String sql = "select * from accounts where id='" + id + "'";
		return this.getJdbcTemplate().queryForMap(sql);
	}
	
	
	/**
	 * 删除账号信息
	 * @param id
	 */
	public void delAccounts(String id){
		String sql = "delete from accounts where id='" + id + "'";
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * 根据账户编号取账户名称
	 * @param id
	 * @return
	 */
	public String getNameById(String id){
		String name = "";
		String sql = "select * from accounts where id='" + id + "'";
		Map map = this.getJdbcTemplate().queryForMap(sql);
		if(map != null){
			name = (String)map.get("name");
		}
		return name;
	}
	
	/**
	 * 减账户余额
	 * @param account_id
	 * @param je
	 */
	public void updateAccountJe(String account_id,double je){
		String sql = "update accounts set dqje=dqje-" + je + " where id='" + account_id + "'";
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * 加账户余额
	 * @param account_id
	 * @param je
	 */
	public void addAccountJe(String account_id,double je){
		String sql = "update accounts set dqje=dqje+" + je + " where id='" + account_id + "'";
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * 校验账户金额减少后金额是否小于0
	 * @param account_id
	 * @param je
	 * @return true:小于0;false:大于0
	 */
	public boolean isZhjeXyZero(String account_id,double je){
		boolean is = false;
		String sql = "select (dqje-" + je + ") as dqje from accounts where id='" + account_id + "'";
		double dqje = 0;
		List list = this.getResultList(sql);
		if(list != null && list.size()>0){
			Map map = (Map)list.get(0);
			dqje = (map.get("dqje")==null?0:((Double)map.get("dqje")).doubleValue());
		}
		if(dqje < 0){
			is = true;
		}
		return is;
	}
	
	
	/**
	 * 初始账户金额
	 * @param account_id
	 * @param qcje
	 */
	public void initAccountJe(String account_id,double qcje){
		String sql = "update accounts set qcje=" + qcje + ",dqje=" + qcje + " where id='" + account_id + "'";
		this.getJdbcTemplate().update(sql);
		
		sql = "delete from account_qc where account_id='" + account_id + "' and qc_date='" + DateComFunc.getToday() + "'";
		this.getJdbcTemplate().update(sql);
		
		sql = "insert into account_qc(account_id,qcje,qc_date) values('" + account_id + "'," + qcje + ",'" + DateComFunc.getToday() + "')";
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * 取当前可用的序列号
	 * @return
	 */
	private String getAccountsId() {
		String sql = "select accountid from cms_all_seq";

		// 取当前序列号
		String curId = this.getJdbcTemplate().queryForInt(sql) + "";

		
		// 将序列号加1
		sql = "update cms_all_seq set accountid=accountid+1";
		this.getJdbcTemplate().update(sql);

		for (int i = curId.length(); i < 4; i++) {
			curId = "0" + curId;
		}

		return curId;
	}
	

}
