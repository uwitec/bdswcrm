package com.sw.cms.dao;

import java.util.List;

import com.sw.cms.dao.base.JdbcBaseDAO;
import com.sw.cms.model.Page;

public class FirstIndexDAO extends JdbcBaseDAO {
	
	/**
	 * 返回待出库出库单列表
	 * @return
	 */
	public List getDckdList(){
		String sql = "select * from ckd where state='待出库'";
		return this.getResultList(sql);
	}
	
	
	/**
	 * 返回待入库单据列表
	 * @return
	 */
	public List getDrkdList(){
		String sql = "select * from rkd where state='已保存'";
		return this.getResultList(sql);
	}
	
	
	/**
	 * 取超期应收款列表
	 * @return
	 */
	public List getCqyskList(){
		String sql = "select * from xsd where (state='已提交' or state='已出库') and skxs<>'已收' and now()>DATE_FORMAT(CONCAT(ysrq ,' 23:59:59'),'%Y-%m-%d %H:%i:%s')";
		return this.getResultList(sql);
	}
	
	
	/**
	 * 取超期应付款列表
	 * @return
	 */
	public List getCqYfkList(){
		String sql = "select * from jhd where state='已提交' and fklx<>'已付' and now()>DATE_FORMAT(CONCAT(yfrq ,' 23:59:59'),'%Y-%m-%d %H:%i:%s')";
		return this.getResultList(sql);
	}
	
	
	/**
	 * 取库存下限列表
	 * @return
	 */
	public List getKcxxList(){
		String sql = "SELECT * FROM view_query_kcxx where kc_nums<kcxx";
		return this.getResultList(sql);
	}
	
	
	/**
	 * 待办工作列表
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getUndoWorks(String con,int curPage,int rowsPerPage){
		String sql = "select * from view_undo_work where 1<>1";
		
		if(!con.equals("")){
			sql += con;
		}
		sql += " order by cz_date desc";
		
		return this.getResultByPage(sql, curPage, rowsPerPage);
	}

}
