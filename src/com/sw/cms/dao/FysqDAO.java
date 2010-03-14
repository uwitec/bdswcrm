package com.sw.cms.dao;

import java.util.List;

import com.sw.cms.dao.base.BeanRowMapper;
import com.sw.cms.dao.base.JdbcBaseDAO;
import com.sw.cms.model.Fysq;
import com.sw.cms.model.Page;
import com.sw.cms.util.DateComFunc;

/**
 * 费用申请
 * @author liyt
 *
 */
public class FysqDAO extends JdbcBaseDAO {
	
	
	/**
	 * 根据查询条件取费用申请单列表
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getFysqList(String con,int curPage, int rowsPerPage){
		String sql = "select * from fysq where 1=1  ";
		if(!con.equals("")){
			sql += con;
		}
		return this.getResultByPage(sql, curPage, rowsPerPage, new BeanRowMapper(Fysq.class));
	}
	

	/**
	 * 根据查询条件取费用申请单列表
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public List getFysqList(String con){
		String sql = "select * from fysq where 1=1  ";
		if(!con.equals("")){
			sql += con;
		}
		return this.getResultList(sql, new BeanRowMapper(Fysq.class));
	}
	
	
	/**
	 * 更新费用申请信息
	 * @param fysq
	 */
	public void updateFysq(Fysq fysq){
		String sql = "select * from fysq where id='" + fysq.getId() + "'";
		List list = this.getResultList(sql);
		if(list != null && list.size() > 0){
			//数据存在，更新
			sql = "update fysq set creatdate=?,ywy_id=?,xgkh=?,fy_type=?,je=?,fklx=?,zfzh=?,remark=?,state=?,czr=?,spr=?,sp_date=now(),cz_date=now(),sqr=?,ywy_dept=? where id=?";
		}else{
			//数据不存在，插入
			sql = "insert into fysq(creatdate,ywy_id,xgkh,fy_type,je,fklx,zfzh,remark,state,czr,spr,sp_date,cz_date,sqr,ywy_dept,id) values(?,?,?,?,?,?,?,?,?,?,?,now(),now(),?,?,?)";
		}
		
		Object[] param = new Object[14];
		param[0] = fysq.getCreatdate();
		param[1] = fysq.getYwy_id();
		param[2] = fysq.getXgkh();
		param[3] = fysq.getFy_type();
		param[4] = fysq.getJe();
		param[5] = fysq.getFklx();
		param[6] = fysq.getZfzh();
		param[7] = fysq.getRemark();
		param[8] = fysq.getState();
		param[9] = fysq.getCzr();
		param[10] = fysq.getSpr();
		param[11] = fysq.getSqr();
		param[12] = fysq.getYwy_dept();
		param[13] = fysq.getId();
		
		this.getJdbcTemplate().update(sql,param);
	}
	
	
	/**
	 * 取费用申请相关信息
	 * @param id
	 * @return
	 */
	public Fysq getFysq(String id){
		String sql = "select * from fysq where id='" + id + "'";
		return (Fysq)this.queryForObject(sql, new BeanRowMapper(Fysq.class));
	}
	
	
	/**
	 * 删除费用申请信息
	 * @param id
	 */
	public void delFysq(String id){
		String sql = "delete from fysq where id='" + id + "'";
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * 更新费用申请单状态
	 * @param id
	 * @param state
	 */
	public void updateFysqState(String id,String state){
		String sql = "update fysq set state='" + state + "' where id='" + id + "'";
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * 取当前可用的序列号
	 * @return
	 */
	public String getFysqID() {
		String sql = "select fysqid from cms_all_seq";

		// 取当前序列号
		String curId = this.getJdbcTemplate().queryForInt(sql) + "";

		String day = DateComFunc.getCurDay();
		
		// 将序列号加1
		sql = "update cms_all_seq set fysqid=fysqid+1";
		this.getJdbcTemplate().update(sql);

		for (int i = curId.length(); i < 3; i++) {
			curId = "0" + curId;
		}

		return "FYSQ" + day + "-" + curId;
	}
	
	/**
	 * 费用申请是否审批完成
	 * @param id
	 * @return
	 */
	public boolean isFinishSp(String id){
		boolean is = false;
		
		String sql = "select count(*) from fysq where id='" + id + "' and (state='审批通过' or state='审批不通过' or state='已支付')";
		
		int count = this.getJdbcTemplate().queryForInt(sql);
		
		if(count > 0){
			is = true;
		}
		
		return is;
	}

}
