package com.sw.cms.dao;

import java.util.List;
import java.util.Map;

import com.sw.cms.dao.StoreDAO.StoreHouseRowMapper;
import com.sw.cms.dao.base.BeanRowMapper;
import com.sw.cms.dao.base.JdbcBaseDAO;

import com.sw.cms.model.Page;
import com.sw.cms.model.Hykfl;
import com.sw.cms.model.StoreHouse;

import com.sw.cms.util.DateComFunc;
import com.sw.cms.util.StaticParamDo;

/**
 * ���ֿ�����
 * @author zuohj
 *
 */

public class HykflDAO extends JdbcBaseDAO {
	
	/**
	 * ȡ���ֿ������б�
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getHykflList(int curPage, int rowsPerPage,String con){
		
		String sql = "select * from hykfl ";
		if (!con.equals("")) {
			sql = sql + con;
		}
		return this.getResultByPage(sql, curPage, rowsPerPage,new BeanRowMapper(Hykfl.class));
	}
	
	
		
	/**
	 * ������ֿ�������Ϣ
	 * @param info
	 */
	public void saveHykfl(Hykfl info){
		String sql = "insert into hykfl(id,name,czr,cz_date,jffs) values(?,?,?,now(),?)";
		Object[] param = new Object[4];
		
		param[0] = info.getId();
		param[1] = info.getName();
		param[2] = info.getCzr();		
		
		param[3] = info.getJffs();
		
		this.getJdbcTemplate().update(sql,param);
		
	}
	
	
	/**
	 * ���»��ֿ�������Ϣ
	 * @param info
	 */
	public void updateHykfl(Hykfl info){
		String sql = "update hykfl set jffs=?,name=?,czr=?,cz_date=now() where id=?";
		Object[] param = new Object[4];
		
		param[0] = info.getJffs();
		param[1] = info.getName();
		
		param[2] = info.getCzr();
		param[3] = info.getId();
		
		this.getJdbcTemplate().update(sql,param);
	}
	
	
	/**
	 * ����IDȡ��Ա��������Ϣ
	 * @param id
	 * @return
	 */
	public Hykfl getHykfl(String id){
		Hykfl info = new Hykfl();
		String sql = "select * from hykfl where id='" + id + "'";		
		Object obj =this.queryForObject(sql, new BeanRowMapper(Hykfl.class));
		if(obj != null){
			info = (Hykfl)obj;
		}
		return info;
	}
	
	
	/**
	 * ɾ����Ա��������Ϣ
	 * @param id
	 */
	public void delHykfl(String id){
		String sql = "delete from hykfl where id='" + id + "'";
		this.getJdbcTemplate().update(sql);
		
	}


	/**
	 * �鿴��Ա�������Ƿ��Ѿ��ύ
	 * @param jfgz_id
	 * @return
	 */
	public boolean isHykflSubmit(String hykfl_id){
		boolean is = false;
		String sql = "select count(*) from Hykfl where id='" + hykfl_id + "'";
		int counts = this.getJdbcTemplate().queryForInt(sql);
		if(counts > 0){
			is = true;
		}
		return is;
	}
	
	/**
	 * ���ػ�Ա���������ID
	 * 
	 * @return
	 */
	public String getHykflId() {
		String sql = "select hykflid from cms_all_seq";

		// ȡ��ǰ���к�
		String curId = this.getJdbcTemplate().queryForInt(sql) + "";

		String day = DateComFunc.getCurDay();

		// �����кż�1
		sql = "update cms_all_seq set hykflid=hykflid+1";
		this.getJdbcTemplate().update(sql);

		for (int i = curId.length(); i < 3; i++) {
			curId = "0" + curId;
		}

		return "HYFL" + day + "-" + curId;

	}
}
