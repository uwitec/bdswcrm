package com.sw.cms.dao;

import com.sw.cms.dao.base.BeanRowMapper;
import com.sw.cms.dao.base.JdbcBaseDAO;
import com.sw.cms.model.Page;
import com.sw.cms.model.XxfbNbgg;
import com.sw.cms.util.UUIDGenerator;

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
		String sql = "select * from xxfb_nbgg order by cz_date desc";
		return this.getResultByPage(sql, curPage, rowsPerPage,new BeanRowMapper(XxfbNbgg.class));
	}
	
	
	/**
	 * ȡ�ڲ�������Ϣ�б�
	 * @param curPage
	 * @param rowsPerPage
	 * @param type
	 * @return
	 */
	public Page getNbggList(int curPage,int rowsPerPage,String type){
		String sql = "select * from xxfb_nbgg where type='" + type + "' order by cz_date desc";
		return this.getResultByPage(sql, curPage, rowsPerPage,new BeanRowMapper(XxfbNbgg.class));
	}
	
	
	/**
	 * �����ڲ�������Ϣ
	 * @param info
	 */
	public void saveNbgg(XxfbNbgg info){
		String sql = "insert into xxfb_nbgg(title,content,pub_date,czr,cz_date,type,id) values(?,?,?,?,now(),?,?)";
		Object[] param = new Object[6];
		
		param[0] = info.getTitle();
		param[1] = info.getContent();
		param[2] = info.getPub_date();
		param[3] = info.getCzr();
		param[4] = info.getType();
		param[5] = UUIDGenerator.getUUID();
		
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
		Object obj =this.queryForObject(sql, new BeanRowMapper(XxfbNbgg.class));
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

}
