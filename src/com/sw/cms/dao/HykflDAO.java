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
 * 积分卡分类
 * @author zuohj
 *
 */

public class HykflDAO extends JdbcBaseDAO {
	
	/**
	 * 取积分卡分类列表
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
	 * 保存积分卡分类信息
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
	 * 更新积分卡分类信息
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
	 * 根据ID取会员卡分类信息
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
	 * 删除会员卡分类信息
	 * @param id
	 */
	public void delHykfl(String id){
		String sql = "delete from hykfl where id='" + id + "'";
		this.getJdbcTemplate().update(sql);
		
	}


	/**
	 * 查看会员卡分类是否已经提交
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
	 * 返回会员卡分类可用ID
	 * 
	 * @return
	 */
	public String getHykflId() {
		String sql = "select hykflid from cms_all_seq";

		// 取当前序列号
		String curId = this.getJdbcTemplate().queryForInt(sql) + "";

		String day = DateComFunc.getCurDay();

		// 将序列号加1
		sql = "update cms_all_seq set hykflid=hykflid+1";
		this.getJdbcTemplate().update(sql);

		for (int i = curId.length(); i < 3; i++) {
			curId = "0" + curId;
		}

		return "HYFL" + day + "-" + curId;

	}
}
