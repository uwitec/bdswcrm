package com.sw.cms.dao;

import java.util.List;

import com.sw.cms.dao.base.BeanRowMapper;
import com.sw.cms.dao.base.JdbcBaseDAO;
import com.sw.cms.model.Jfgz;
import com.sw.cms.model.Page;
import com.sw.cms.util.DateComFunc;

/**
 * ���ֹ�������
 * @author zuohj
 *
 */

public class JfgzDAO extends JdbcBaseDAO {
	
	/**
	 * ȡ���ֹ����б�
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getJfgzList(int curPage, int rowsPerPage,String con){
		
		String sql = "select * from jfgz ";
		if (!con.equals("")) {
			sql = sql + con;
		}
		return this.getResultByPage(sql, curPage, rowsPerPage,new BeanRowMapper(Jfgz.class));
	}
	
	/**
	 * ȡ���л��ֹ����б�
	 * @return
	 */
	public List getAllJfgzList(){
		String sql = "select * from jfgz";
		
		return this.getJdbcTemplate().query(sql, new BeanRowMapper(Jfgz.class));
	}
		
	/**
	 * ������ֹ�����Ϣ
	 * @param info
	 */
	public void saveJfgz(Jfgz info){
		String sql = "insert into jfgz(jfff,xfje,czr,cz_date,id,dyjf) values(?,?,?,now(),?,?)";
		Object[] param = new Object[5];
		
		param[0] = info.getJfff();
		param[1] = info.getXfje();
		param[2] = info.getCzr();		
		param[3] = info.getId();
		param[4] = info.getDyjf();
		
		this.getJdbcTemplate().update(sql,param);
		
	}
	
	
	/**
	 * ���»��ֹ�����Ϣ
	 * @param info
	 */
	public void updateJfgz(Jfgz info){
		String sql = "update jfgz set jfff=?,xfje=?,dyjf=?,czr=?,cz_date=now() where id=?";
		Object[] param = new Object[5];
		
		param[0] = info.getJfff();
		param[1] = info.getXfje();
		param[2] = info.getDyjf();
		param[3] = info.getCzr();
		param[4] = info.getId();
		
		this.getJdbcTemplate().update(sql,param);
	}
	
	
	/**
	 * ����IDȡ���ֹ�����Ϣ
	 * @param id
	 * @return
	 */
	public Jfgz getJfgz(String id){
		Jfgz info = new Jfgz();
		String sql = "select * from jfgz where id='" + id + "'";		
		Object obj =this.queryForObject(sql, new BeanRowMapper(Jfgz.class));
		if(obj != null){
			info = (Jfgz)obj;
		}
		return info;
	}
	
	
	/**
	 * ɾ�����ֹ�����Ϣ
	 * @param id
	 */
	public void delJfgz(String id){
		String sql = "delete from jfgz where id='" + id + "'";
		this.getJdbcTemplate().update(sql);
		
	}


	/**
	 * �鿴���ֹ����Ƿ��Ѿ��ύ
	 * @param jfgz_id
	 * @return
	 */
	public boolean isJfgzSubmit(String jfgz_id){
		boolean is = false;
		String sql = "select count(*) from jfgz where id='" + jfgz_id + "'";
		int counts = this.getJdbcTemplate().queryForInt(sql);
		if(counts > 0){
			is = true;
		}
		return is;
	}
	
	/**
	 * ���ػ��ֹ������ID
	 * 
	 * @return
	 */
	public String getJfgzId() {
		String sql = "select jfgzid from cms_all_seq";

		// ȡ��ǰ���к�
		String curId = this.getJdbcTemplate().queryForInt(sql) + "";

		String day = DateComFunc.getCurDay();

		// �����кż�1
		sql = "update cms_all_seq set jfgzid=jfgzid+1";
		this.getJdbcTemplate().update(sql);

		for (int i = curId.length(); i < 3; i++) {
			curId = "0" + curId;
		}

		return "JFGZ" + day + "-" + curId;

	}
	
	/**
	 * ����IDȡ���ֹ�������
	 * @param id
	 * @return
	 */
	public String getJfgzNameById(String id){
		String jfff = "";
		String sql = "select * from jfgz where id='" + id + "'";
		Object obj = this.queryForObject(sql, new BeanRowMapper(Jfgz.class));
		if(obj != null){
			jfff = ((Jfgz)obj).getJfff();
		}
		return jfff;
	}
}
