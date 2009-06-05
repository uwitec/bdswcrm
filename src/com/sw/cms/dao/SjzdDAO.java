package com.sw.cms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.sw.cms.dao.base.JdbcBaseDAO;
import com.sw.cms.dao.base.SqlUtil;
import com.sw.cms.model.SjzdJbxx;
import com.sw.cms.model.SjzdXmxx;

public class SjzdDAO extends JdbcBaseDAO {
	
	/**
	 * 获取所有字典基本字段
	 * @return
	 */
	public List getAllSjzdJbxx(){
		String sql = "select * from sjzd_jbxx order by xh desc";
		return this.getResultList(sql, new SjzdJbxxRowMapper());
	}
	
	/**
	 * 获取字典基本信息
	 * @param zd_id
	 * @return
	 */
	public SjzdJbxx getSjzdJbxxByZdId(String zd_id){
		String sql = "select * from sjzd_jbxx where zd_id='" + zd_id + "'";
		return (SjzdJbxx)this.queryForObject(sql, new SjzdJbxxRowMapper());
	}
	
	
	/**
	 * 根据字典编号取所有字典信息
	 * @param zd_id
	 * @return
	 */
	public List getZdxmByZdId(String zd_id){
		String sql = "select * from sjzd_xmxx where zd_id='" + zd_id + "' order by xh desc";
		return this.getResultList(sql, new SjzdXmxxRowMapper());
	}
	
	
	/**
	 * 保存字典信息
	 * @param sjzdXmxx
	 */
	public void saveZdxm(SjzdXmxx sjzdXmxx){
		String sql = "insert into sjzd_xmxx(zd_id,xm_name,xm_ms,xh) values(?,?,?,?)";
		
		Object[] param = new Object[4];
		param[0] = sjzdXmxx.getZd_id();
		param[1] = sjzdXmxx.getXm_name();
		param[2] = sjzdXmxx.getXm_ms();
		param[3] = sjzdXmxx.getXh();
		
		this.getJdbcTemplate().update(sql,param);
	}
	
	
	/**
	 * 更新字典信息
	 * @param sjzdXmxx
	 */
	public void updateZdxm(SjzdXmxx sjzdXmxx){
		String sql = "update sjzd_xmxx set zd_id=?,xm_name=?,xm_ms=?,xh=? where xm_id=?";
		
		Object[] param = new Object[5];
		param[0] = sjzdXmxx.getZd_id();
		param[1] = sjzdXmxx.getXm_name();
		param[2] = sjzdXmxx.getXm_ms();
		param[3] = sjzdXmxx.getXh();
		param[4] = sjzdXmxx.getXm_id();
		
		this.getJdbcTemplate().update(sql,param);
	}
	
	
	/**
	 * 删字典信息
	 * @param xm_id
	 */
	public void delZdxm(String xm_id){
		String sql = "delete from sjzd_xmxx where xm_id=" + xm_id;
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * 根据项目编号取字典信息
	 * @param xm_id
	 */
	public SjzdXmxx getZdxm(String xm_id){
		String sql = "select * from sjzd_xmxx where xm_id=" + xm_id;
		return (SjzdXmxx)this.queryForObject(sql, new SjzdXmxxRowMapper());
	}
	
	
	/**
	 * 包装对象(数据字典--具体信息)
	 * @author liyt
	 */
	class SjzdJbxxRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			SjzdJbxx sjzdJbxx = new SjzdJbxx();
			
			if(SqlUtil.columnIsExist(rs,"zd_id")) sjzdJbxx.setZd_id(rs.getString("zd_id"));
			if(SqlUtil.columnIsExist(rs,"zd_name")) sjzdJbxx.setZd_name(rs.getString("zd_name"));
			if(SqlUtil.columnIsExist(rs,"zdms")) sjzdJbxx.setZdms(rs.getString("zdms"));
			if(SqlUtil.columnIsExist(rs,"xh")) sjzdJbxx.setXh(rs.getInt("xh"));
			
			return sjzdJbxx;
		}
	}
	
	
	/**
	 * 包装对象(数据字典--项目信息)
	 * @author liyt
	 */
	class SjzdXmxxRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			SjzdXmxx sjzdXmxx = new SjzdXmxx();
			
			if(SqlUtil.columnIsExist(rs,"xm_id")) sjzdXmxx.setXm_id(rs.getInt("xm_id"));
			if(SqlUtil.columnIsExist(rs,"zd_id")) sjzdXmxx.setZd_id(rs.getString("zd_id"));
			if(SqlUtil.columnIsExist(rs,"xm_name")) sjzdXmxx.setXm_name(rs.getString("xm_name"));
			if(SqlUtil.columnIsExist(rs,"xm_ms")) sjzdXmxx.setXm_ms(rs.getString("xm_ms"));
			if(SqlUtil.columnIsExist(rs,"xh")) sjzdXmxx.setXh(rs.getInt("xh"));
			
			return sjzdXmxx;
		}
	}

}
