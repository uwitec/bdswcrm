package com.sw.cms.dao;

import java.util.List;

import com.sw.cms.dao.base.JdbcBaseDAO;

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

}
