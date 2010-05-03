package com.sw.cms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.sw.cms.dao.base.JdbcBaseDAO;
import com.sw.cms.model.Lsysk;
import com.sw.cms.model.Page;
import com.sw.cms.util.DateComFunc;

/**
 * 零售预收款处理
 * @author liyt
 *
 */
public class LsyskDAO extends JdbcBaseDAO {
	
	/**
	 * 取零售预收款列表
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getLsyskList(String con,int curPage, int rowsPerPage){
		String sql = "select * from lsysk where 1=1";
		
		if(!con.equals("")){
			sql = sql + con;
		}
		
		return this.getResultByPage(sql, curPage, rowsPerPage, new LsyskRowMapper());
	}
	
	
	/**
	 * 保存零售预收款信息
	 * @param lsysk
	 */
	public void saveLsysk(Lsysk lsysk){
		String sql = "insert into lsysk(client_name,lxr,lxdh,mobile,jsr,ysje,skzh,fkfs,state,remark,czr,ys_date,type,cz_date,pos_id,id) values(?,?,?,?,?,?,?,?,?,?,?,?,?,now(),?,?)";
		
		Object[] param = new Object[15];
		
		param[0] = lsysk.getClient_name();
		param[1] = lsysk.getLxr();
		param[2] = lsysk.getLxdh();
		param[3] = lsysk.getMobile();
		param[4] = lsysk.getJsr();
		param[5] = new Double(lsysk.getYsje());
		param[6] = lsysk.getSkzh();
		param[7] = lsysk.getFkfs();
		param[8] = lsysk.getState();
		param[9] = lsysk.getRemark();
		param[10] = lsysk.getCzr();
		param[11] = lsysk.getYs_date();
		param[12] = lsysk.getType();
		param[13] = lsysk.getPos_id();
		param[14] = lsysk.getId();
		
		this.getJdbcTemplate().update(sql,param);
		
	}
	
	
	/**
	 * 更新零售预收款信息
	 * @param lsysk
	 */
	public void updateLsysk(Lsysk lsysk){
		String sql = "update lsysk set client_name=?,lxr=?,lxdh=?,mobile=?,jsr=?,ysje=?,skzh=?,fkfs=?,state=?,remark=?,czr=?,ys_date=?,type=?,cz_date=now(),pos_id=? where id =?";
		
		Object[] param = new Object[15];
		
		param[0] = lsysk.getClient_name();
		param[1] = lsysk.getLxr();
		param[2] = lsysk.getLxdh();
		param[3] = lsysk.getMobile();
		param[4] = lsysk.getJsr();
		param[5] = new Double(lsysk.getYsje());
		param[6] = lsysk.getSkzh();
		param[7] = lsysk.getFkfs();
		param[8] = lsysk.getState();
		param[9] = lsysk.getRemark();
		param[10] = lsysk.getCzr();
		param[11] = lsysk.getYs_date();
		param[12] = lsysk.getType();
		param[13] = lsysk.getPos_id();
		param[14] = lsysk.getId();
		
		this.getJdbcTemplate().update(sql,param);
	}
	
	
	/**
	 * 根据ID取零售预收款信息
	 * @param id
	 * @return
	 */
	public Object getLsysk(String id){
		String sql = "select * from lsysk where id='" + id + "'";
		return this.queryForObject(sql, new LsyskRowMapper());
	}
	
	
	/**
	 * 删除零售预收款信息
	 * @param id
	 */
	public void delLsysk(String id){
		String sql = "delete from lsysk where id='" + id + "'";
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * 修改零售预收款类型
	 * @param id
	 * @param type
	 */
	public void updateLsyskType(String id,String type){
		String sql = "update lsysk set type='" + type + "' where id='" + id + "'";
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * 取当前可用的零售预收款ID
	 * @return
	 */
	public String getLsyskId(){
		String sql = "select lsyskid from cms_all_seq";

		// 取当前序列号
		String curId = this.getJdbcTemplate().queryForInt(sql) + "";
		
		String day = DateComFunc.getCurDay();

		// 将序列号加1
		sql = "update cms_all_seq set lsyskid=lsyskid+1";
		this.getJdbcTemplate().update(sql);

		for (int i = curId.length(); i < 3; i++) {
			curId = "0" + curId;
		}

		return "LYSK" + day + "-" + curId;
	}
	
	
	/**
	 * 判断零售预收款是否已经提交
	 * @param id
	 * @return
	 */
	public boolean isLsyskSubmit(String id){
		boolean is = false;
		
		String sql = "select count(*) as nums from lsysk where id='" + id + "' and state='已提交'";
		int counts = this.getJdbcTemplate().queryForInt(sql);
		
		if(counts > 0){
			is = true;
		}
		
		return is;
	}
	
	
	/**
	 * 包装对象(零售预收款)
	 * 
	 * @author liyt
	 * 
	 */
	class LsyskRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			Lsysk lsysk = new Lsysk();
			
			lsysk.setId(rs.getString("id"));
			lsysk.setClient_name(rs.getString("client_name"));
			lsysk.setLxr(rs.getString("lxr"));
			lsysk.setJsr(rs.getString("jsr"));
			lsysk.setLxdh(rs.getString("lxdh"));
			lsysk.setMobile(rs.getString("mobile"));
			lsysk.setYsje(rs.getDouble("ysje"));
			lsysk.setSkzh(rs.getString("skzh"));
			lsysk.setState(rs.getString("state"));
			lsysk.setFkfs(rs.getString("fkfs"));
			lsysk.setRemark(rs.getString("remark"));
			lsysk.setCzr(rs.getString("czr"));
			lsysk.setYs_date(rs.getString("ys_date"));
			lsysk.setType(rs.getString("type"));
			lsysk.setPos_id(rs.getString("pos_id"));
			
			return lsysk;
		}
			
	}

}
