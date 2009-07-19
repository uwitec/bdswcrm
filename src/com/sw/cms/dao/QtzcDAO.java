package com.sw.cms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.sw.cms.dao.base.JdbcBaseDAO;
import com.sw.cms.dao.base.SqlUtil;
import com.sw.cms.model.Page;
import com.sw.cms.model.Qtzc;
import com.sw.cms.util.DateComFunc;

public class QtzcDAO extends JdbcBaseDAO {
	
	/**
	 * 取其它支出列表
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getQtzcList(String con,int curPage, int rowsPerPage){
		String sql = "select * from qtzc where 1=1";
		
		if(!con.equals("")){
			sql = sql + con;
		}
		
		return this.getResultByPage(sql, curPage, rowsPerPage, new QtzcRowMapper());
	}
	
	
	/**
	 * 保存其它支出
	 * @param qtsr
	 */
	public void saveQtzc(Qtzc qtzc){
		String sql = "insert into qtzc(zc_date,type,zcje,zczh,jsr,remark,czr,cz_date,state,ywy,zcxm,spr,fklx,sp_date,fysq_id,id) values(?,?,?,?,?,?,?,now(),?,?,?,?,?,now(),?,?)";
		
		Object[] param = new Object[14];
		
		param[0] = qtzc.getZc_date();
		param[1] = qtzc.getType();
		param[2] = new Double(qtzc.getZcje());
		param[3] = qtzc.getZczh();
		param[4] = qtzc.getJsr();
		param[5] = qtzc.getRemark();
		param[6] = qtzc.getCzr();
		param[7] = qtzc.getState();
		param[8] = qtzc.getYwy();
		param[9] = qtzc.getZcxm();
		param[10] = qtzc.getSpr();
		param[11] = qtzc.getFklx();
		param[12] = qtzc.getFysq_id();
		param[13] = qtzc.getId();
		
		this.getJdbcTemplate().update(sql,param);
	}
	
	
	/**
	 * 更新其它支出
	 * @param qtsr
	 */
	public void updateQtzc(Qtzc qtzc){
		String sql = "update qtzc set zc_date=?,type=?,zcje=?,zczh=?,jsr=?,remark=?,czr=?,cz_date=now(),state=?,ywy=?,zcxm=?,fklx=? where id=?";
		
		Object[] param = new Object[12];
		
		param[0] = qtzc.getZc_date();
		param[1] = qtzc.getType();
		param[2] = new Double(qtzc.getZcje());
		param[3] = qtzc.getZczh();
		param[4] = qtzc.getJsr();
		param[5] = qtzc.getRemark();
		param[6] = qtzc.getCzr();
		param[7] = qtzc.getState();
		param[8] = qtzc.getYwy();
		param[9] = qtzc.getZcxm();
		param[10] = qtzc.getFklx();
		param[11] = qtzc.getId();
		
		this.getJdbcTemplate().update(sql,param);
	}
	
	
	/**
	 * 取其它支出
	 * @param id
	 * @return
	 */
	public Object getQtzc(String id){
		String sql = "select * from qtzc where id='" + id + "'";
		return this.queryForObject(sql, new QtzcRowMapper());
	}
	
	
	/**
	 * 删除其它支出
	 * @param id
	 */
	public void delQtzc(String id){
		String sql = "delete from qtzc where id='" + id + "'";
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * 取当前可用的序列号
	 * @return
	 */
	public String getQtzcID() {
		String sql = "select qtzcid from cms_all_seq";

		// 取当前序列号
		String curId = this.getJdbcTemplate().queryForInt(sql) + "";

		String day = DateComFunc.getCurDay();
		
		// 将序列号加1
		sql = "update cms_all_seq set qtzcid=qtzcid+1";
		this.getJdbcTemplate().update(sql);

		for (int i = curId.length(); i < 3; i++) {
			curId = "0" + curId;
		}

		return "ZC" + day + "-" + curId;
	}
	
	
	/**
	 * 判断费用是否已经支出
	 * @param id
	 * @return
	 */
	public boolean isFinishZc(String id){
		boolean is = false;
		
		String sql = "select count(*) from qtzc where id='" + id + "' and state='已提交'";
		
		int count = this.getJdbcTemplate().queryForInt(sql);
		
		if(count > 0){
			is = true;
		}
		
		return is;
	}
	
	
	/**
	 * 包装对象(其它支出)
	 * 
	 * @author liyt
	 * 
	 */
	class QtzcRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			Qtzc qtzc = new Qtzc();

			if(SqlUtil.columnIsExist(rs,"id")) qtzc.setId(rs.getString("id"));
			if(SqlUtil.columnIsExist(rs,"jsr")) qtzc.setJsr(rs.getString("jsr"));
			if(SqlUtil.columnIsExist(rs,"remark")) qtzc.setRemark(rs.getString("remark"));
			if(SqlUtil.columnIsExist(rs,"zcje")) qtzc.setZcje(rs.getDouble("zcje"));
			if(SqlUtil.columnIsExist(rs,"zczh")) qtzc.setZczh(rs.getString("zczh"));
			if(SqlUtil.columnIsExist(rs,"zc_date")) qtzc.setZc_date(rs.getString("zc_date"));
			if(SqlUtil.columnIsExist(rs,"type")) qtzc.setType(rs.getString("type"));
			if(SqlUtil.columnIsExist(rs,"state")) qtzc.setState(rs.getString("state"));
			if(SqlUtil.columnIsExist(rs,"czr")) qtzc.setCzr(rs.getString("czr"));
			if(SqlUtil.columnIsExist(rs,"ywy")) qtzc.setYwy(rs.getString("ywy"));
			if(SqlUtil.columnIsExist(rs,"zcxm")) qtzc.setZcxm(rs.getString("zcxm"));
			if(SqlUtil.columnIsExist(rs,"spr")) qtzc.setSpr(rs.getString("spr"));
			if(SqlUtil.columnIsExist(rs,"sp_date") && rs.getTimestamp("sp_date")!=null)
				qtzc.setSp_date(rs.getTimestamp("sp_date").toString());
			if(SqlUtil.columnIsExist(rs,"fklx")) qtzc.setFklx(rs.getString("fklx"));
			if(SqlUtil.columnIsExist(rs,"fysq_id")) qtzc.setFysq_id(rs.getString("fysq_id"));
			return qtzc;
		}
	}	

}
