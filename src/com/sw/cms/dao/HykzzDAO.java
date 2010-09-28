package com.sw.cms.dao;

import java.util.List;

import com.sw.cms.dao.base.BeanRowMapper;
import com.sw.cms.dao.base.JdbcBaseDAO;
import com.sw.cms.model.Hykfl;
import com.sw.cms.model.Hykzz;
import com.sw.cms.model.Page;

/**
 * 会员卡设置
 * @author zuohj
 *
 */

public class HykzzDAO extends JdbcBaseDAO {
	
	
	
	/**
	 * 取会员卡列表
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getHykzzList(int curPage, int rowsPerPage,String con){
		
		String sql = "select * from hykzz where 1=1 ";
		if (!con.equals("")) {
			sql = sql + con;
		}
		return this.getResultByPage(sql, curPage, rowsPerPage,new BeanRowMapper(Hykzz.class));
	}
	
	/**
	 * 取所有会员卡分类列表
	 * @return
	 */
	public List getAllHykflList(){
		String sql = "select * from hykfl";
		
		return this.getJdbcTemplate().query(sql, new BeanRowMapper(Hykfl.class));
	}
		
	/**
	 * 保存会员卡信息
	 * @param info
	 */
	public void saveHykzz(Hykzz info){
		String sql = "insert into hykzz(card_type,dept,csjf,yxrq,sxrq,hykh,ssfl,cz_date,id,czr,state) values(?,?,?,?,?,?,?,now(),?,?,?)";
		Object[] param = new Object[10];
		
		param[0] = info.getCard_type();
		param[1] = info.getDept();
		param[2] = info.getCsjf();	
		param[3] = info.getYxrq();
		param[4] = info.getSxrq();
		param[5] = info.getHykh();	
		param[6] = info.getSsfl();
		param[7] = info.getId();
		param[8] = info.getCzr();
		param[9] = info.getState();
		
		this.getJdbcTemplate().update(sql,param);
		
	}
	
	
	/**
	 * 更新会员卡信息
	 * @param info
	 */
	public void updateHykzz(Hykzz info){
		String sql = "update hykzz set card_type=?,dept=?,csjf=?,yxrq=?,sxrq=?,hykh=?,ssfl=?,cz_date=now(),czr=?,state=? where id=?";
		Object[] param = new Object[10];
		
		param[0] = info.getCard_type();
		param[1] = info.getDept();
		param[2] = info.getCsjf();
		param[3] = info.getYxrq();
		param[4] = info.getSxrq();
		param[5] = info.getHykh();
		param[6] = info.getSsfl();
		param[7] = info.getCzr();
		param[8] = info.getState();
		param[9] = info.getId();
		
		
		this.getJdbcTemplate().update(sql,param);
	}
	
	
	/**
	 * 根据ID取会员卡信息
	 * @param id
	 * @return
	 */
	public Hykzz getHykzz(String id){
		Hykzz info = new Hykzz();
		String sql = "select * from hykzz where id='" + id + "'";		
		Object obj =this.queryForObject(sql, new BeanRowMapper(Hykzz.class));
		if(obj != null){
			info = (Hykzz)obj;
		}
		return info;
	}
	
	
	/**
	 * 根据ID取会员卡档案信息
	 * @param hykh
	 * @return
	 */
	public Hykzz getHykzzda(String hykh){
		String sql = "select * from hykzz where hykh='" + hykh + "'";		
		Object obj =this.queryForObject(sql, new BeanRowMapper(Hykzz.class));
		return (Hykzz)obj;
	}
	
	/**
	 * 删除会员卡信息
	 * @param id
	 */
	public void delHykzz(String hykh){
		String sql = "delete from hykzz where hykh='" + hykh + "'";
		this.getJdbcTemplate().update(sql);
		
	}	
	
}
