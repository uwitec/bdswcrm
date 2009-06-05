package com.sw.cms.dao;

import java.util.List;

import com.sw.cms.dao.base.JdbcBaseDAO;

public class FuncDAO extends JdbcBaseDAO {
	
	public List getAllFuncList(){
		String sql = "select * from funcs";
		return this.getJdbcTemplate().queryForList(sql);
	}

}
