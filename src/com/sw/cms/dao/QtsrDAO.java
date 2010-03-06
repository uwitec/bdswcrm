package com.sw.cms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.sw.cms.dao.base.JdbcBaseDAO;
import com.sw.cms.model.Page;
import com.sw.cms.model.Qtsr;
import com.sw.cms.util.DateComFunc;

public class QtsrDAO extends JdbcBaseDAO {
	
	/**
	 * ȡ���������б�
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getQtsrList(String con,int curPage, int rowsPerPage){
		String sql = "select * from qtsr where 1=1";
		
		if(!con.equals("")){
			sql = sql + con;
		}
		
		return this.getResultByPage(sql, curPage, rowsPerPage, new QtsrRowMapper());
	}
	
	
	/**
	 * ������������
	 * @param qtsr
	 */
	public void saveQtsr(Qtsr qtsr){
		String sql = "insert into qtsr(sr_date,type,skje,skzh,jsr,remark,czr,cz_date,state,id) values(?,?,?,?,?,?,?,now(),?,?)";
		
		Object[] param = new Object[9];
		
		param[0] = qtsr.getSr_date();
		param[1] = qtsr.getType();
		param[2] = new Double(qtsr.getSkje());
		param[3] = qtsr.getSkzh();
		param[4] = qtsr.getJsr();
		param[5] = qtsr.getRemark();
		param[6] = qtsr.getCzr();
		param[7] = qtsr.getState();
		param[8] = qtsr.getId();
		
		this.getJdbcTemplate().update(sql,param);
	}
	
	
	/**
	 * ������������
	 * @param qtsr
	 */
	public void updateQtsr(Qtsr qtsr){
		String sql = "update qtsr set sr_date=?,type=?,skje=?,skzh=?,jsr=?,remark=?,czr=?,cz_date=now(),state=? where id=?";
		
		Object[] param = new Object[9];
		
		param[0] = qtsr.getSr_date();
		param[1] = qtsr.getType();
		param[2] = new Double(qtsr.getSkje());
		param[3] = qtsr.getSkzh();
		param[4] = qtsr.getJsr();
		param[5] = qtsr.getRemark();
		param[6] = qtsr.getCzr();
		param[7] = qtsr.getState();
		param[8] = qtsr.getId();
		
		this.getJdbcTemplate().update(sql,param);
	}
	
	
	/**
	 * ȡ��������
	 * @param id
	 * @return
	 */
	public Object getQtsr(String id){
		String sql = "select * from qtsr where id='" + id + "'";
		return this.queryForObject(sql, new QtsrRowMapper());
	}
	
	
	/**
	 * ɾ����������
	 * @param id
	 */
	public void delQtsr(String id){
		String sql = "delete from qtsr where id='" + id + "'";
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * ȡ��ǰ���õ����к�
	 * @return
	 */
	public String getQtsrID() {
		String sql = "select qtsrid from cms_all_seq";

		// ȡ��ǰ���к�
		String curId = this.getJdbcTemplate().queryForInt(sql) + "";

		String day = DateComFunc.getCurDay();
		
		// �����кż�1
		sql = "update cms_all_seq set qtsrid=qtsrid+1";
		this.getJdbcTemplate().update(sql);

		for (int i = curId.length(); i < 3; i++) {
			curId = "0" + curId;
		}

		return "SR" + day + "-" + curId;
	}
	
	
	/**
	 * ���������Ƿ��Ѿ��ύ
	 * @param id
	 * @return
	 */
	public boolean isQtsrSubmit(String id){
		boolean is = false;
		
		String sql = "select count(*) as counts from qtsr where id='" + id + "' and state='���ύ'";
		int counts = this.getJdbcTemplate().queryForInt(sql);
		
		if(counts > 0){
			is = true;
		}
		
		return is;
	}
	
	/**
	 * ��װ����(��������)
	 * 
	 * @author liyt
	 * 
	 */
	class QtsrRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			Qtsr qtsr = new Qtsr();

			qtsr.setId(rs.getString("id"));
			qtsr.setJsr(rs.getString("jsr"));
			qtsr.setRemark(rs.getString("remark"));
			qtsr.setSkje(rs.getDouble("skje"));
			qtsr.setSkzh(rs.getString("skzh"));
			qtsr.setSr_date(rs.getString("sr_date"));
			qtsr.setType(rs.getString("type"));
			qtsr.setState(rs.getString("state"));
			qtsr.setCzr(rs.getString("czr"));
			
			return qtsr;
		}
	}	

}
