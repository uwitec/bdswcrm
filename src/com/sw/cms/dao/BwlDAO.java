package com.sw.cms.dao;

import com.sw.cms.dao.base.BeanRowMapper;
import com.sw.cms.dao.base.JdbcBaseDAO;
import com.sw.cms.model.Page;
import com.sw.cms.model.Bwl;
import com.sw.cms.util.UUIDGenerator;

/**
 * ��Ϣ�����ڲ�����
 * @author liyt
 *
 */

public class BwlDAO extends JdbcBaseDAO {
	
	/**
	 * ȡ����¼��Ϣ�б�
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getBwlList(int curPage, int rowsPerPage,String user_id){
		String sql = "select * from bwl where czr='"+user_id+"' order by cz_date desc";
		return this.getResultByPage(sql, curPage, rowsPerPage,new BeanRowMapper(Bwl.class));
	}
	
	
		
	/**
	 * ���汸��¼��Ϣ
	 * @param info
	 */
	public void saveBwl(Bwl info){
		String sql = "insert into bwl(title,content,czr,cz_date,id) values(?,?,?,now(),?)";
		Object[] param = new Object[4];
		
		param[0] = info.getTitle();
		param[1] = info.getContent();
		param[2] = info.getCzr();
		param[3] = UUIDGenerator.getUUID();
		
		this.getJdbcTemplate().update(sql,param);
	}
	
	
	/**
	 * ���±���¼��Ϣ
	 * @param info
	 */
	public void updateBwl(Bwl info){
		String sql = "update bwl set title=?,content=?,czr=?,cz_date=now() where id=?";
		Object[] param = new Object[4];
		
		param[0] = info.getTitle();
		param[1] = info.getContent();
		param[2] = info.getCzr();
		param[3] = info.getId();
		
		this.getJdbcTemplate().update(sql,param);
	}
	
	
	/**
	 * ����IDȡ����¼��Ϣ
	 * @param id
	 * @return
	 */
	public Bwl getBwl(String id){
		Bwl info = new Bwl();
		String sql = "select * from bwl where id='" + id + "'";		
		Object obj =this.queryForObject(sql, new BeanRowMapper(Bwl.class));
		if(obj != null){
			info = (Bwl)obj;
		}
		return info;
	}
	
	
	/**
	 * ɾ������¼��Ϣ
	 * @param id
	 */
	public void delBwl(String id){
		String sql = "delete from bwl where id='" + id + "'";
		this.getJdbcTemplate().update(sql);
	}

}
