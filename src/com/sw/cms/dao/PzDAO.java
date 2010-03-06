package com.sw.cms.dao;

import com.sw.cms.dao.base.BeanRowMapper;
import com.sw.cms.dao.base.JdbcBaseDAO;
import com.sw.cms.model.Page;
import com.sw.cms.model.Pz;
import com.sw.cms.util.DateComFunc;

/**
 * 往来调账
 * @author liyt
 *
 */

public class PzDAO extends JdbcBaseDAO {
	
	/**
	 * 取平账列表
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getPzList(String con,int curPage, int rowsPerPage){
		String sql = "select * from pz where 1=1";
		
		if(!con.equals("")){
			sql = sql + con;
		}
		
		return this.getResultByPage(sql, curPage, rowsPerPage, new BeanRowMapper(Pz.class));
	}
	
	
	/**
	 * 保存平账信息
	 * @param pz
	 */
	public void savePz(Pz pz){
		String sql = "insert into pz(pz_date,jsr,client_name,pzje,state,type,remark,czr,cz_date,pzxm,id) values(?,?,?,?,?,?,?,?,now(),?,?)";
		
		Object[] param = new Object[10];
		
		param[0] = pz.getPz_date();
		param[1] = pz.getJsr();
		param[2] = pz.getClient_name();
		param[3] = new Double(pz.getPzje());
		param[4] = pz.getState();
		param[5] = pz.getType();
		param[6] = pz.getRemark();
		param[7] = pz.getCzr();
		param[8] = pz.getPzxm();
		param[9] = pz.getId();
		
		this.getJdbcTemplate().update(sql,param);
		
	}
	
	
	/**
	 * 更新平账信息
	 * @param pz
	 */
	public void updatePz(Pz pz){
		String sql = "update pz set pz_date=?,jsr=?,client_name=?,pzje=?,state=?,type=?,remark=?,czr=?,cz_date=now(),pzxm=? where id=?";
		
		Object[] param = new Object[10];
		
		param[0] = pz.getPz_date();
		param[1] = pz.getJsr();
		param[2] = pz.getClient_name();
		param[3] = new Double(pz.getPzje());
		param[4] = pz.getState();
		param[5] = pz.getType();
		param[6] = pz.getRemark();
		param[7] = pz.getCzr();
		param[8] = pz.getPzxm();
		param[9] = pz.getId();
		
		this.getJdbcTemplate().update(sql,param);		
	}
	
	
	/**
	 * 取平账信息
	 * @param id
	 * @return
	 */
	public Object getPz(String id){
		String sql = "select * from pz where id='" + id + "'";
		return this.queryForObject(sql, new BeanRowMapper(Pz.class));
	}
	
	
	/**
	 * 删除平账信息
	 * @param id
	 */
	public void delPz(String id){
		String sql = "delete from pz where id='" + id + "'";
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * 取当前可用的序列号
	 * @return
	 */
	public String getPzID() {
		String sql = "select pzid from cms_all_seq";

		// 取当前序列号
		String curId = this.getJdbcTemplate().queryForInt(sql) + "";

		String day = DateComFunc.getCurDay();
		
		// 将序列号加1
		sql = "update cms_all_seq set pzid=pzid+1";
		this.getJdbcTemplate().update(sql);

		for (int i = curId.length(); i < 3; i++) {
			curId = "0" + curId;
		}

		return "PZ" + day + "-" + curId;
	}	

}
