package com.sw.cms.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sw.cms.dao.base.BeanRowMapper;
import com.sw.cms.dao.base.JdbcBaseDAO;
import com.sw.cms.model.Page;
import com.sw.cms.model.XsfpDkfp;
import com.sw.cms.model.XsfpFpmx;
import com.sw.cms.model.XsfpFpxx;
import com.sw.cms.util.DateComFunc;
import com.sw.cms.util.UUIDGenerator;

/**
 * 销售发票管理
 * @author liyt
 *
 */
public class XsfpDAO extends JdbcBaseDAO {
	
	/**
	 * 生成代开发票信息
	 * @param xsfpDkfp
	 */
	public void saveXsfpDkfp(XsfpDkfp xsfpDkfp) {
		
		String sql = "insert into xsfp_dkfp(id,yw_type,yw_id,khmc,ddje,ykpje,fplx,kpmc,kpdz,kpdh,khhzh,sh,fpxxzy,jy_jsr,jy_date,state,cz_date,czr) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,now(),?)";
		
		Object[] param = new Object[17];
		
		param[0] = UUIDGenerator.getUUID();
		param[1] = xsfpDkfp.getYw_type();
		param[2] = xsfpDkfp.getYw_id();
		param[3] = xsfpDkfp.getKhmc();
		param[4] = xsfpDkfp.getDdje();
		param[5] = xsfpDkfp.getYkpje();
		param[6] = xsfpDkfp.getFplx();
		param[7] = xsfpDkfp.getKpmc();
		param[8] = xsfpDkfp.getKpdz();
		param[9] = xsfpDkfp.getKpdh();
		param[10] = xsfpDkfp.getKhhzh();
		param[11] = xsfpDkfp.getSh();
		param[12] = xsfpDkfp.getFpxxzy();
		param[13] = xsfpDkfp.getJy_jsr();
		param[14] = xsfpDkfp.getJy_date();
		param[15] = xsfpDkfp.getState();
		param[16] = xsfpDkfp.getCzr();
		
		this.getJdbcTemplate().update(sql, param);
		
	}
	
	
	/**
	 * 取待开发票信息
	 * @param id
	 * @return
	 */
	public XsfpDkfp getXsfpDkfp (String yw_id) {
		String sql = "select * from xsfp_dkfp where yw_id = '" + yw_id + "'";
		return (XsfpDkfp)this.getResultObject(sql, new BeanRowMapper(XsfpDkfp.class));
	}
	
	
	/**
	 * 更新销售待开发票信息
	 * @param xsfpDkfp
	 */
	public void updateXsfpDkfp(XsfpDkfp xsfpDkfp) {
		String sql = "update xsfp_dkfp set yw_type=?,yw_id=?,khmc=?,ddje=?,ykpje=?,fplx=?,kpmc=?,kpdz=?,kpdh=?,khhzh=?,sh=?,fpxxzy=?,jy_jsr=?,jy_date=?,state=?,cz_date=now(),czr=? where id = ?";
		
		Object[] param = new Object[17];
		
		param[0] = xsfpDkfp.getYw_type();
		param[1] = xsfpDkfp.getYw_id();
		param[2] = xsfpDkfp.getKhmc();
		param[3] = xsfpDkfp.getDdje();
		param[4] = xsfpDkfp.getYkpje();
		param[5] = xsfpDkfp.getFplx();
		param[6] = xsfpDkfp.getKpmc();
		param[7] = xsfpDkfp.getKpdz();
		param[8] = xsfpDkfp.getKpdh();
		param[9] = xsfpDkfp.getKhhzh();
		param[10] = xsfpDkfp.getSh();
		param[11] = xsfpDkfp.getFpxxzy();
		param[12] = xsfpDkfp.getJy_jsr();
		param[13] = xsfpDkfp.getJy_date();
		param[14] = xsfpDkfp.getState();
		param[15] = xsfpDkfp.getCzr();
		param[16] = xsfpDkfp.getId();
		
		this.getJdbcTemplate().update(sql, param);
	}
	
	
	/**
	 * 取销售发票待开发票列表
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getXsfpDkfpPage(String con , int curPage, int rowsPerPage){
		String sql = "SELECT a.* FROM xsfp_dkfp a LEFT JOIN sys_user b on b.user_id=a.jy_jsr where a.state<>'已开'";
		
		if(!con.equals("")){
			sql = sql + con;
		}
		
		return this.getResultByPage(sql, curPage, rowsPerPage, new BeanRowMapper(XsfpDkfp.class));
	}
	
	
	/**
	 * 取开票信息列表
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getXsfpFpxxPage(String con,int curPage, int rowsPerPage){
		
		String sql = "select * from xsfp_fpxx where 1=1";
		if(!con.equals("")){
			sql = sql + con;
		}
		
		return this.getResultByPage(sql, curPage, rowsPerPage, new BeanRowMapper(XsfpFpxx.class));
	}
	
	
	/**
	 * 取发票明细
	 * @param id
	 * @return
	 */
	public List getXsfpFpmxList(String id) {
		String sql = "select * from xsfp_fpmx where fpxx_id='" + id + "'";
		
		return this.getResultList(sql, new BeanRowMapper(XsfpFpmx.class));
	}
	
	
	/**
	 * 取发票信息
	 * @param id
	 * @return
	 */
	public XsfpFpxx getXsfpFpxx(String id){
		String sql = "select * from xsfp_fpxx where id='" + id + "'";
		return (XsfpFpxx)this.getResultObject(sql, new BeanRowMapper(XsfpFpxx.class));
	}
	
	
	/**
	 * 取当前可用的摊销付款编号
	 * @return
	 */
	public String getXsfpID() {
		String sql = "select xsfpid from cms_all_seq";

		// 取当前序列号
		String curId = this.getJdbcTemplate().queryForInt(sql) + "";
		
		String day = DateComFunc.getCurDay();
		// 将序列号加1
		sql = "update cms_all_seq set xsfpid=xsfpid+1";
		this.getJdbcTemplate().update(sql);

		for (int i = curId.length(); i < 3; i++) {
			curId = "0" + curId;
		}

		return "XSFP" + day + "-" + curId;
	}
	
	
	/**
	 * 编辑销售发票信息
	 * @param xsfpFpxx
	 * @param fpmxDescs
	 */
	public void updateXsfp(XsfpFpxx xsfpFpxx, List<XsfpFpmx> fpmxDescs) {
		String sql = "select count(*) as counts from xsfp_fpxx where id='" + xsfpFpxx.getId() + "'";
		int counts = this.getJdbcTemplate().queryForInt(sql);
		
		if(counts > 0){
			sql = "update xsfp_fpxx set khmc=?,fplx=?,fph=?,fpje=?,kpr=?,remark=?,kprq=?,czr=?,state=?,cz_date=now(),fpje_bdd=?,remark_bdd=? where id=?";
		} else {
			sql = "insert into xsfp_fpxx(khmc,fplx,fph,fpje,kpr,remark,kprq,czr,state,cz_date,fpje_bdd,remark_bdd,id) values (?,?,?,?,?,?,?,?,?,now(),?,?,?)";
		}
		
		Object[] param = new Object[12];
		param[0] = xsfpFpxx.getKhmc();
		param[1] = xsfpFpxx.getFplx();
		param[2] = xsfpFpxx.getFph();
		param[3] = xsfpFpxx.getFpje();
		param[4] = xsfpFpxx.getKpr();
		param[5] = xsfpFpxx.getRemark();
		param[6] = xsfpFpxx.getKprq();
		param[7] = xsfpFpxx.getCzr();
		param[8] = xsfpFpxx.getState();
		param[9] = xsfpFpxx.getFpje_bdd();
		param[10] = xsfpFpxx.getRemark_bdd();
		param[11] = xsfpFpxx.getId();
		
		//更新发票信息基本信息
		this.getJdbcTemplate().update(sql, param);
		
		//删除发票明细
		this.delXsfpFpmx(xsfpFpxx.getId());
		
		//保存发票明细信息
		this.insertXsfpFpmx(xsfpFpxx, fpmxDescs);
		
	}
	
	
	/**
	 * 删除销售发票信息
	 * @param id
	 */
	public void delXsfp(String id){
		String sql = "delete from xsfp_fpxx where id= '" + id + "'";
		this.getJdbcTemplate().update(sql);
		
		sql = "delete from xsfp_fpmx where fpxx_id = '" + id + "'";
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * 删除发票明细信息
	 * @param fpxx_id
	 */
	private void delXsfpFpmx(String fpxx_id) {
		String sql = "delete from xsfp_fpmx where fpxx_id='" + fpxx_id + "'";
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * 保存发票明细信息
	 * @param xsfpFpxx
	 * @param fpmxDescs
	 */
	private void insertXsfpFpmx(XsfpFpxx xsfpFpxx, List<XsfpFpmx> fpmxDescs) {
		Object param[] = new Object[8];
		String sql = "";
		if(fpmxDescs != null && fpmxDescs.size() > 0){
			for(XsfpFpmx xsfpFpmx : fpmxDescs){
				if(xsfpFpmx != null && xsfpFpmx.getYw_id() != null && xsfpFpmx.getKpje_bc() > 0){
					sql = "insert into xsfp_fpmx(fpxx_id,khmc,yw_id,cdate,kpje_ying,kpje_yi,kpje_bc,remark) values (?,?,?,?,?,?,?,?)";
					param[0] = xsfpFpxx.getId();
					param[1] = xsfpFpmx.getKhmc();
					param[2] = xsfpFpmx.getYw_id();
					param[3] = xsfpFpmx.getCdate();
					param[4] = xsfpFpmx.getKpje_ying();
					param[5] = xsfpFpmx.getKpje_yi();
					param[6] = xsfpFpmx.getKpje_bc();
					param[7] = xsfpFpmx.getRemark();
					this.getJdbcTemplate().update(sql, param);
				}
			}
		}
	}
	
	
	/**
	 * 销售发票统计，状态为已提交
	 * @param start_date
	 * @param end_date
	 * @param fplx
	 * @return
	 */
	public List getXsfpList(String start_date, String end_date, String fplx) {
		String sql = "select * from xsfp_fpxx where state='已提交'";
		if(start_date != null && !start_date.equals("")) {
			sql += " and kprq >='" + start_date + "'";
		}
		if(end_date != null && !end_date.equals("")) {
			sql += " and kprq <='" + (end_date + " 23:59:59")  + "'";
		}
		if(fplx != null && !fplx.equals("")){
			sql += " and fplx = '" + fplx + "'";
		}
		sql += " order by kprq desc , id desc";
		return this.getResultList(sql, new BeanRowMapper(XsfpFpxx.class));
	}
	
	
	/**
	 * 销售发票通-明细信息
	 * @param start_date
	 * @param end_date
	 * @param fplx
	 * @return
	 */
	public Map getXsfpMxMap(String start_date, String end_date, String fplx) {
		Map <String,List> tempMap = new HashMap<String,List>();
		String sql = "SELECT a.fpxx_id,a.khmc,a.yw_id,a.kpje_ying,a.kpje_yi,a.kpje_bc,a.remark FROM xsfp_fpmx a LEFT JOIN xsfp_fpxx b ON b.id = a.fpxx_id WHERE b.state='已提交'";
		if(start_date != null && !start_date.equals("")) {
			sql += " and b.kprq >='" + start_date + "'";
		}
		if(end_date != null && !end_date.equals("")) {
			sql += " and b.kprq <='" + (end_date + " 23:59:59")  + "'";
		}
		if(fplx != null && !fplx.equals("")){
			sql += " and b.fplx = '" + fplx + "' order by fpxx_id desc";
		}
		
		List <Map> xsfpMxList = this.getResultList(sql);
		if(xsfpMxList != null && xsfpMxList.size() > 0) {
			for(Map map : xsfpMxList) {
				String fpxx_id = (String)map.get("fpxx_id");
				if(tempMap.get(fpxx_id) == null) {
					List tempList = new ArrayList();
					tempList.add(map);
					tempMap.put(fpxx_id, tempList);
				} else {
					tempMap.get(fpxx_id).add(map);
				}
			}
		}
		return tempMap;
	}

}
