package com.sw.cms.dao;

import java.util.List;

import com.sw.cms.dao.base.BeanRowMapper;
import com.sw.cms.dao.base.JdbcBaseDAO;
import com.sw.cms.model.Hykda;
import com.sw.cms.model.Hykfl;
import com.sw.cms.model.Page;

/**
 * 会员卡档案
 * @author liyt
 */

public class HykdaDAO extends JdbcBaseDAO {

	/**
	 * 取会员卡档案列表
	 * 
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getHykdaList(int curPage, int rowsPerPage, String con) {
		String sql = "select * from hykda where 1=1 ";
		if (!con.equals("")) {
			sql = sql + con;
		}
		return this.getResultByPage(sql, curPage, rowsPerPage,
				new BeanRowMapper(Hykda.class));
	}

	/**
	 * 取所有会员卡分类列表
	 * 
	 * @return
	 */
	public List getAllHykflList() {
		String sql = "select * from hykfl";
		return this.getJdbcTemplate().query(sql, new BeanRowMapper(Hykfl.class));
	}

	/**
	 * 保存会员卡信息
	 * 
	 * @param info
	 */
	public void saveHykfk(Hykda info) {
		String sql = "insert into hykda(hykh,hymc,lxrname,lxdh,mobile,address,mail,sfzh,sex,gzdw,birth," +
				     "fkrq,fkjsr,fkbz,state,cz_date,czr,id) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,now(),?,?)";
		Object[] param = new Object[17];

		param[0] = info.getHykh();
		param[1] = info.getHymc();
		param[2] = info.getLxrname();
		param[3] = info.getLxdh();
		param[4] = info.getMobile();
		param[5] = info.getAddress();
		param[6] = info.getMail();
		param[7] = info.getSfzh();
		param[8] = info.getSex();
		param[9] = info.getGzdw();
		param[10] = info.getBirth();
		param[11] = info.getFkrq();
		param[12] = info.getFkjsr();
		param[13] = info.getFkbz();
		param[14] = info.getState();
		param[15] = info.getCzr();
		param[16] = info.getId();

		this.getJdbcTemplate().update(sql, param);
	}

	/**
	 * 更新会员卡档案信息
	 * 
	 * @param info
	 */
	public void updateHykda(Hykda info) {
		String sql = "update hykda set hykh=?,hymc=?,lxrname=?,lxdh=?,mobile=?,address=?,mail=?,sfzh=?,sex=?," +
				     "gzdw=?,birth=?,fkrq=?,fkjsr=?,fkbz=?,state=?,cz_date=now(),czr=? where id=?";
		Object[] param = new Object[17];

		param[0] = info.getHykh();
		param[1] = info.getHymc();
		param[2] = info.getLxrname();
		param[3] = info.getLxdh();
		param[4] = info.getMobile();
		param[5] = info.getAddress();
		param[6] = info.getMail();
		param[7] = info.getSfzh();
		param[8] = info.getSex();
		param[9] = info.getGzdw();
		param[10] = info.getBirth();
		param[11] = info.getFkrq();
		param[12] = info.getFkjsr();
		param[13] = info.getFkbz();
		param[14] = info.getState();
		param[15] = info.getCzr();
		param[16] = info.getId();

		this.getJdbcTemplate().update(sql, param);
	}

	/**
	 * 根据hykh取会员卡档案信息
	 * 
	 * @param id
	 * @return
	 */
	public Hykda getHykda(String hykh) {
		String sql = "select * from hykda where hykh='" + hykh + "' and state='正常'";
		Object obj = this.queryForObject(sql, new BeanRowMapper(Hykda.class));
		return (Hykda) obj;
	}
	
	/**
	 * 根据ID取会员卡档案信息
	 * 
	 * @param id
	 * @return
	 */
	public Hykda getHykdaById(String id) {
		String sql = "select * from hykda where id='" + id + "'";
		Object obj = this.queryForObject(sql, new BeanRowMapper(Hykda.class));
		return (Hykda) obj;
	}
	
	
	public Hykda getHykdaByHykh(String hykh) {
		String sql = "select * from hykda where hykh='" + hykh + "' and state='正常'";
		Object obj = this.queryForObject(sql, new BeanRowMapper(Hykda.class));
		return (Hykda) obj;
	}
	

	/**
	 * 删除会员卡档案信息
	 * 
	 * @param hykh
	 */
	public void delHykda(String id) {
		String sql = "delete from hykda where id='" + id + "'";
		this.getJdbcTemplate().update(sql);

	}
	
}
