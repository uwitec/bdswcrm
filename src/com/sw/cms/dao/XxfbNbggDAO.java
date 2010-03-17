package com.sw.cms.dao;

import java.util.List;

import com.sw.cms.dao.base.BeanRowMapper;
import com.sw.cms.dao.base.JdbcBaseDAO;
import com.sw.cms.model.Page;
import com.sw.cms.model.XxfbNbgg;
import com.sw.cms.util.UUIDGenerator;

/**
 * 信息发布内部公告
 * @author liyt
 *
 */

public class XxfbNbggDAO extends JdbcBaseDAO {
	
	/**
	 * 取内部公告信息列表
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getNbggList(int curPage, int rowsPerPage){
		String sql = "select * from xxfb_nbgg where parent_id='0' order by cz_date desc";
		return this.getResultByPage(sql, curPage, rowsPerPage,new BeanRowMapper(XxfbNbgg.class));
	}
	
	
	/**
	 * 取内部公告信息列表
	 * @param curPage
	 * @param rowsPerPage
	 * @param type
	 * @return
	 */
	public Page getNbggList(int curPage,int rowsPerPage,String type){
		String sql = "select * from xxfb_nbgg where parent_id='0' and type='" + type + "' order by cz_date desc";
		return this.getResultByPage(sql, curPage, rowsPerPage,new BeanRowMapper(XxfbNbgg.class));
	}
	
	
	/**
	 * 保存内部公告信息
	 * @param info
	 */
	public void saveNbgg(XxfbNbgg info){
		String sql = "insert into xxfb_nbgg(title,content,pub_date,czr,cz_date,type,parent_id,id) values(?,?,?,?,now(),?,?,?)";
		Object[] param = new Object[7];
		
		param[0] = info.getTitle();
		param[1] = info.getContent();
		param[2] = info.getPub_date();
		param[3] = info.getCzr();
		param[4] = info.getType();
		param[5] = info.getParent_id();
		param[6] = UUIDGenerator.getUUID();
		
		this.getJdbcTemplate().update(sql,param);
		
		//如果添加的是新的回复，则需要修改主题的操作时间
		if(!info.getParent_id().equals("0")){
			sql = "update xxfb_nbgg set cz_date=now() where id='" + info.getParent_id() + "'";
			this.getJdbcTemplate().update(sql);
		}
	}
	
	
	/**
	 * 更新内部公告信息
	 * @param info
	 */
	public void updateNbgg(XxfbNbgg info){
		String sql = "update xxfb_nbgg set title=?,content=?,pub_date=?,czr=?,cz_date=now(),type=?,parent_id=? where id=?";
		Object[] param = new Object[7];
		
		param[0] = info.getTitle();
		param[1] = info.getContent();
		param[2] = info.getPub_date();
		param[3] = info.getCzr();
		param[4] = info.getType();
		param[5] = info.getParent_id();
		param[6] = info.getId();
		
		this.getJdbcTemplate().update(sql,param);
	}
	
	
	/**
	 * 根据ID取内部公告信息
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
	 * 取内部公告回复列表
	 * @param parent_id
	 * @return
	 */
	public List getNbggListByParentId(String parent_id){
		String sql = "select * from xxfb_nbgg where parent_id='" + parent_id + "' order by cz_date desc";
		return this.getResultList(sql, new BeanRowMapper(XxfbNbgg.class));
	}
	
	
	/**
	 * 删除内部公告信息（包括回复信息）
	 * @param id
	 */
	public void delNbgg(String id){
		String sql = "delete from xxfb_nbgg where id='" + id + "'";
		this.getJdbcTemplate().update(sql);
		
		sql = "delete from xxfb_nbgg where parent_id='" + id + "'";
		this.getJdbcTemplate().update(sql);
	}

}
