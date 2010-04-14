package com.sw.cms.dao;

import java.util.Map;

import com.sw.cms.dao.base.BeanRowMapper;
import com.sw.cms.dao.base.JdbcBaseDAO;
import com.sw.cms.model.Page;
import com.sw.cms.model.SysUser;
import com.sw.cms.util.GB2Alpha;

/**
 * <p>
 * 员工报表
 * </p>
 * <p>
 * 与系统用户共用一个表(sys_user)
 * </p>
 * <p>
 * is_sys_user==0
 * </p>
 * 
 * @author zhj
 */

public class YgbbDAO extends JdbcBaseDAO {

	/**
	 * 取员工列表（带分页）
	 * 
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getUserList(String con, int curPage, int rowsPerPage) {
		String sql = "select a.*,b.dept_name from sys_user a left join dept b on b.dept_id=a.dept where a.is_sys_user=0 and a.is_del='0'";

		if (!con.equals("")) {
			sql = sql + con;
		}

		return this.getResultByPage(sql, curPage, rowsPerPage);
	}

	
	/**
	 * 根据user_id取用户信息
	 * 
	 * @param user_id
	 * @return
	 */
	public Map getUser(String user_id) {
		String sql = "select * from sys_user where user_id='" + user_id + "'";
		return this.getResultMap(sql);
	}
}
