package com.sw.cms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.sw.cms.dao.base.JdbcBaseDAO;
import com.sw.cms.model.Nbzz;
import com.sw.cms.model.Page;
import com.sw.cms.util.DateComFunc;

public class NbzzDAO extends JdbcBaseDAO {
	
	
	/**
	 * 内部转账列表
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getNbzzList(String con,int curPage, int rowsPerPage){
		String sql = "select * from nbzz where 1=1";
		if(!con.equals("")){
			sql = sql + con;
		}
		return this.getResultByPage(sql, curPage, rowsPerPage, new NbzzRowMapper());
	}
	
	
	/**
	 * 保存内部转账信息
	 * @param nbzz
	 */
	public void saveNbzz(Nbzz nbzz){
		String sql = "insert into nbzz(zz_date,zczh,zrzh,zzje,jsr,state,remark,czr,cz_date,id) values(?,?,?,?,?,?,?,?,now(),?)";
		
		Object[] param = new Object[9];
		
		param[0] = nbzz.getZz_date();
		param[1] = nbzz.getZczh();
		param[2] = nbzz.getZrzh();
		param[3] = new Double(nbzz.getZzje());
		param[4] = nbzz.getJsr();
		param[5] = nbzz.getState();
		param[6] = nbzz.getRemark();
		param[7] = nbzz.getCzr();
		param[8] = nbzz.getId();
		
		this.getJdbcTemplate().update(sql,param);
	}
	
	
	/**
	 * 更新内部转账信息
	 * @param nbzz
	 */
	public void updateNbzz(Nbzz nbzz){
		String sql = "update nbzz set zz_date=?,zczh=?,zrzh=?,zzje=?,jsr=?,state=?,remark=?,czr=?,cz_date=now() where id=?";
		
		Object[] param = new Object[9];
		
		param[0] = nbzz.getZz_date();
		param[1] = nbzz.getZczh();
		param[2] = nbzz.getZrzh();
		param[3] = new Double(nbzz.getZzje());
		param[4] = nbzz.getJsr();
		param[5] = nbzz.getState();
		param[6] = nbzz.getRemark();
		param[7] = nbzz.getCzr();
		param[8] = nbzz.getId();
		
		this.getJdbcTemplate().update(sql,param);
	}
	
	
	/**
	 * 取内部转账信息
	 */
	public Object getNbzz(String id){
		String sql = "select * from nbzz where id='" + id + "'";
		
		return this.queryForObject(sql, new NbzzRowMapper());
	}
	
	
	/**
	 * 删除内部转账信息
	 * @param id
	 */
	public void delNbzz(String id){
		
		String sql = "delete from nbzz where id='" + id +"'";
		this.getJdbcTemplate().update(sql);
		
	}
	
	
	/**
	 * 取当前可用的序列号
	 * @return
	 */
	public String getNbzzId() {
		String sql = "select nbzzid from cms_all_seq";

		// 取当前序列号
		String curId = this.getJdbcTemplate().queryForInt(sql) + "";

		String day = DateComFunc.getCurDay();
		
		// 将序列号加1
		sql = "update cms_all_seq set nbzzid=nbzzid+1";
		this.getJdbcTemplate().update(sql);

		for (int i = curId.length(); i < 3; i++) {
			curId = "0" + curId;
		}

		return "ZZ" + day + "-" + curId;
	}
	
	/**
	 * 包装对象(内部转账)
	 * 
	 * @author liyt
	 * 
	 */
	class NbzzRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			Nbzz nbzz = new Nbzz();

			nbzz.setId(rs.getString("id"));
			nbzz.setZz_date(rs.getString("zz_date"));
			nbzz.setZczh(rs.getString("zczh"));
			nbzz.setZrzh(rs.getString("zrzh"));
			nbzz.setZzje(rs.getDouble("zzje"));
			nbzz.setJsr(rs.getString("jsr"));
			nbzz.setState(rs.getString("state"));
			nbzz.setRemark(rs.getString("remark"));
			nbzz.setCzr(rs.getString("czr"));
			
			return nbzz;
		}
	}	


}
