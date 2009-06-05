package com.sw.cms.dao;

import java.util.List;

import com.sw.cms.dao.base.JdbcBaseDAO;

public class PositionDAO extends JdbcBaseDAO  {
	
	
	/**
	 * 取所有职位列表
	 * @return
	 */
	public List getAllPositionList(){
		String sql = "select * from position order by xh desc";
		
		return this.getJdbcTemplate().queryForList(sql);
	}

}
