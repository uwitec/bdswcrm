package com.sw.cms.dao;

import com.sw.cms.dao.base.BeanRowMapper;
import com.sw.cms.dao.base.JdbcBaseDAO;
import com.sw.cms.model.Page;
import com.sw.cms.model.Xsfpgl;
import com.sw.cms.util.UUIDGenerator;

/**
 * 销售发票管理
 * @author liyt
 *
 */
public class XsfpglDAO extends JdbcBaseDAO {
	
	/**
	 * 保存销售发票信息
	 * @param xsfp
	 */
	public void saveXsfp(Xsfpgl xsfp){
		String sql = "insert into xsfpgl(id,fplx,kpmc,fpje,kpdz,kpdh,khhzh,sh,fpxxzy,jy_jsr,jy_date,kp_jsr,kp_date,yw_type,yw_id,state,cz_date,czr) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,now(),?)";
		
		Object[] param = new Object[17];
		
		param[0] = UUIDGenerator.getUUID();
		param[1] = xsfp.getFplx();
		param[2] = xsfp.getKpmc();
		param[3] = xsfp.getFpje();
		param[4] = xsfp.getKpdz();
		param[5] = xsfp.getKpdh();
		param[6] = xsfp.getKhhzh();
		param[7] = xsfp.getSh();
		param[8] = xsfp.getFpxxzy();
		param[9] = xsfp.getJy_jsr();
		param[10] = xsfp.getJy_date();
		param[11] = xsfp.getKp_jsr();
		param[12] = xsfp.getKp_date();
		param[13] = xsfp.getYw_type();
		param[14] = xsfp.getYw_id();
		param[15] = xsfp.getState();
		param[16] = xsfp.getCzr();
		
		this.getJdbcTemplate().update(sql, param);
				
	}
	
	/**
	 * 根据查询条件取代开发票列表
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getXsfpList(String con,int curPage, int rowsPerPage){
		String sql = "select * from xsfpgl where 1=1";
		if(!con.equals("")) {
			sql = sql + con;
		}	
		return this.getResultByPage(sql, curPage, rowsPerPage, new BeanRowMapper(Xsfpgl.class));
	}

}
