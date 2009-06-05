package com.sw.cms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.sw.cms.dao.base.JdbcBaseDAO;
import com.sw.cms.model.Page;
import com.sw.cms.model.XxfbNbgg;

/**
 * ��Ϣ�����ڲ�����
 * @author liyt
 *
 */

public class XxfbNbggDAO extends JdbcBaseDAO {
	
	/**
	 * ȡ�ڲ�������Ϣ�б�
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getNbggList(int curPage, int rowsPerPage){
		String sql = "select * from xxfb_nbgg order by id desc";
		return this.getResultByPage(sql, curPage, rowsPerPage,new XxfbNbggRowMapper());
	}
	
	
	/**
	 * ȡ�ڲ�������Ϣ�б�
	 * @param curPage
	 * @param rowsPerPage
	 * @param type
	 * @return
	 */
	public Page getNbggList(int curPage,int rowsPerPage,String type){
		String sql = "select * from xxfb_nbgg where type='" + type + "' order by id desc";
		return this.getResultByPage(sql, curPage, rowsPerPage,new XxfbNbggRowMapper());
	}
	
	
	/**
	 * �����ڲ�������Ϣ
	 * @param info
	 */
	public void saveNbgg(XxfbNbgg info){
		String sql = "insert into xxfb_nbgg(title,content,pub_date,czr,cz_date,type) values(?,?,?,?,now(),?)";
		Object[] param = new Object[5];
		
		param[0] = info.getTitle();
		param[1] = info.getContent();
		param[2] = info.getPub_date();
		param[3] = info.getCzr();
		param[4] = info.getType();
		
		this.getJdbcTemplate().update(sql,param);
	}
	
	
	/**
	 * �����ڲ�������Ϣ
	 * @param info
	 */
	public void updateNbgg(XxfbNbgg info){
		String sql = "update xxfb_nbgg set title=?,content=?,pub_date=?,czr=?,cz_date=now(),type=? where id=?";
		Object[] param = new Object[6];
		
		param[0] = info.getTitle();
		param[1] = info.getContent();
		param[2] = info.getPub_date();
		param[3] = info.getCzr();
		param[4] = info.getType();
		param[5] = info.getId();
		
		this.getJdbcTemplate().update(sql,param);
	}
	
	
	/**
	 * ����IDȡ�ڲ�������Ϣ
	 * @param id
	 * @return
	 */
	public XxfbNbgg getNbgg(String id){
		XxfbNbgg info = new XxfbNbgg();
		String sql = "select * from xxfb_nbgg where id='" + id + "'";		
		Object obj =this.queryForObject(sql, new XxfbNbggRowMapper());
		if(obj != null){
			info = (XxfbNbgg)obj;
		}
		return info;
	}
	
	
	/**
	 * ɾ���ڲ�������Ϣ
	 * @param id
	 */
	public void delNbgg(String id){
		String sql = "delete from xxfb_nbgg where id='" + id + "'";
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * ��װ����(�����տ���ϸ)
	 * 
	 * @author liyt
	 * 
	 */
	class XxfbNbggRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			XxfbNbgg info = new XxfbNbgg();

			info.setId(rs.getInt("id"));
			info.setTitle(rs.getString("title"));
			info.setContent(rs.getString("content"));
			info.setPub_date(rs.getString("pub_date"));
			info.setCzr(rs.getString("czr"));
			info.setType(rs.getString("type"));
			
			return info;
		}
	}

}
