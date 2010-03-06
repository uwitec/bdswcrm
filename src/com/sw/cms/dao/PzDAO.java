package com.sw.cms.dao;

import com.sw.cms.dao.base.BeanRowMapper;
import com.sw.cms.dao.base.JdbcBaseDAO;
import com.sw.cms.model.Page;
import com.sw.cms.model.Pz;
import com.sw.cms.util.DateComFunc;

/**
 * ��������
 * @author liyt
 *
 */

public class PzDAO extends JdbcBaseDAO {
	
	/**
	 * ȡƽ���б�
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
	 * ����ƽ����Ϣ
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
	 * ����ƽ����Ϣ
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
	 * ȡƽ����Ϣ
	 * @param id
	 * @return
	 */
	public Object getPz(String id){
		String sql = "select * from pz where id='" + id + "'";
		return this.queryForObject(sql, new BeanRowMapper(Pz.class));
	}
	
	
	/**
	 * ɾ��ƽ����Ϣ
	 * @param id
	 */
	public void delPz(String id){
		String sql = "delete from pz where id='" + id + "'";
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * ȡ��ǰ���õ����к�
	 * @return
	 */
	public String getPzID() {
		String sql = "select pzid from cms_all_seq";

		// ȡ��ǰ���к�
		String curId = this.getJdbcTemplate().queryForInt(sql) + "";

		String day = DateComFunc.getCurDay();
		
		// �����кż�1
		sql = "update cms_all_seq set pzid=pzid+1";
		this.getJdbcTemplate().update(sql);

		for (int i = curId.length(); i < 3; i++) {
			curId = "0" + curId;
		}

		return "PZ" + day + "-" + curId;
	}	

}
