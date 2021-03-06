package com.sw.cms.dao.base;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.sw.cms.model.Page;

/**
 * 所有DAO类均要继承此类，植入了JdbcTemplate<BR>
 * 添加分页及单个对象的处理，扩展了spring jdbc的部分方法，修补了JdbcTemplate相关问题 <BR>
 * 
 * @author liyt
 */

public class JdbcBaseDAO {

	protected transient final Log log = LogFactory.getLog(getClass());

	private JdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	/**
	 * 查询分页结果 返回分页对象page page.getResults()返回为list，list中为Map对象 字段名为key,值为value
	 * 
	 * @param sql
	 *            String
	 * @param curPage
	 *            int 当前页数 从1开始
	 * @param rowsPerPage
	 *            int 每页行数
	 * @return Page
	 */
	public Page getResultByPage(String sql, int curPage, int rowsPerPage) {
		Page page = new Page();

		int count = curPage * rowsPerPage - rowsPerPage; // 记录起点

		// 对sql语句进行再包装，添加分页
		// 解决spring jdbc queryForList连接查询无法正常显示结果列问题
		String strSql = "select t.* from (" + sql + " limit " + rowsPerPage
				+ " offset " + count + ") as t";

		List result = jdbcTemplate.queryForList(strSql);

		String countSql = "select count(*) as allcount from (" + sql + ") as t";
		int allcount = jdbcTemplate.queryForInt(countSql);

		int totalPage = 0;
		if (allcount % rowsPerPage == 0) {
			totalPage = allcount / rowsPerPage;
		} else {
			totalPage = allcount / rowsPerPage + 1;
		}

		page.setCurPage(curPage);
		page.setPerPage(rowsPerPage);
		page.setResults(result);
		page.setTotalNum(allcount);
		page.setTotalPage(totalPage);

		return page;
	}

	/**
	 * 查询分页结果 返回分页对象page page.getResults()返回为list，list中为Map对象 字段名为key,值为value
	 * 
	 * @param sql
	 *            String
	 * @param param
	 *            Object[] 参数组
	 * @param curPage
	 *            int 当前页数 从1开始
	 * @param rowsPerPage
	 *            int 每页行数
	 * @return Page
	 */
	public Page getResultByPage(String sql, Object[] param, int curPage,
			int rowsPerPage) {
		Page page = new Page();

		int count = curPage * rowsPerPage - rowsPerPage; // 记录起点

		// 对sql语句进行再包装，添加分页
		// 解决spring jdbc queryForList连接查询无法正常显示结果列问题
		String strSql = "select t.* from (" + sql + " limit " + rowsPerPage
				+ " offset " + count + ") as t";

		List result = jdbcTemplate.queryForList(strSql, param);

		String countSql = "select count(*) as allcount from (" + sql + ") as t";
		int allcount = jdbcTemplate.queryForInt(countSql);

		int totalPage = 0;
		if (allcount % rowsPerPage == 0) {
			totalPage = allcount / rowsPerPage;
		} else {
			totalPage = allcount / rowsPerPage + 1;
		}

		page.setCurPage(curPage);
		page.setPerPage(rowsPerPage);
		page.setResults(result);
		page.setTotalNum(allcount);
		page.setTotalPage(totalPage);

		return page;
	}

	/**
	 * 查询分页结果 返回分页对象page page.getResults()返回为list，list中为Map对象 字段名为key,值为value
	 * 
	 * @param sql
	 *            String
	 * @param param
	 *            List 参数List
	 * @param curPage
	 *            int 当前页数 从1开始
	 * @param rowsPerPage
	 *            int 每页行数
	 * @return Page
	 */
	public Page getResultByPage(String sql, List param, int curPage,
			int rowsPerPage) {
		Page page = new Page();

		int count = curPage * rowsPerPage - rowsPerPage; // 记录起点

		// 对sql语句进行再包装，添加分页
		// 解决spring jdbc queryForList连接查询无法正常显示结果列问题
		String strSql = "select t.* from (" + sql + " limit " + rowsPerPage
				+ " offset " + count + ") as t";

		Object[] args = new Object[param.size()];
		for (int i = 0; i < param.size(); i++) {
			args[i] = param.get(i);
		}

		List result = jdbcTemplate.queryForList(strSql, args);

		String countSql = "select count(*) as allcount from (" + sql + ") as t";
		int allcount = jdbcTemplate.queryForInt(countSql);

		int totalPage = 0;
		if (allcount % rowsPerPage == 0) {
			totalPage = allcount / rowsPerPage;
		} else {
			totalPage = allcount / rowsPerPage + 1;
		}

		page.setCurPage(curPage);
		page.setPerPage(rowsPerPage);
		page.setResults(result);
		page.setTotalNum(allcount);
		page.setTotalPage(totalPage);

		return page;
	}

	/**
	 * 
	 * 查询分页结果 返回分页对象page page.getResults()返回为list，list中为具体包装后的对象
	 * 
	 * @param sql
	 *            String
	 * @param curPage
	 *            int 当前页数 从1开始
	 * @param rowsPerPage
	 *            int 每页行数
	 * @param rowMapper
	 *            org.springframework.jdbc.core.RowMapper
	 * @return Page
	 */
	public Page getResultByPage(String sql, int curPage, int rowsPerPage,
			RowMapper rowMapper) {
		Page page = new Page();

		int count = curPage * rowsPerPage - rowsPerPage; // 记录起点

		// 对sql语句进行再包装，添加分页
		// 解决spring jdbc queryForList连接查询无法正常显示结果列问题
		String strSql = "select t.* from (" + sql + " limit " + rowsPerPage
				+ " offset " + count + ") as t";

		List result = jdbcTemplate.query(strSql, rowMapper);

		String countSql = "select count(*) as allcount from (" + sql + ") as t";
		int allcount = jdbcTemplate.queryForInt(countSql);

		int totalPage = 0;
		if (allcount % rowsPerPage == 0) {
			totalPage = allcount / rowsPerPage;
		} else {
			totalPage = allcount / rowsPerPage + 1;
		}

		page.setCurPage(curPage);
		page.setPerPage(rowsPerPage);
		page.setResults(result);
		page.setTotalNum(allcount);
		page.setTotalPage(totalPage);

		return page;
	}

	/**
	 * 
	 * 查询分页结果 返回分页对象page page.getResults()返回为list，list中为具体包装后的对象
	 * 
	 * @param sql
	 *            String
	 * @param curPage
	 *            int 当前页数 从1开始
	 * @param param
	 *            Object[] 参数组
	 * @param rowsPerPage
	 *            int 每页行数
	 * @param rowMapper
	 *            org.springframework.jdbc.core.RowMapper
	 * @return Page
	 */
	public Page getResultByPage(String sql, int curPage, Object[] param,
			int rowsPerPage, RowMapper rowMapper) {
		Page page = new Page();

		int count = curPage * rowsPerPage - rowsPerPage; // 记录起点

		// 对sql语句进行再包装，添加分页
		// 解决spring jdbc queryForList连接查询无法正常显示结果列问题
		String strSql = "select t.* from (" + sql + " limit " + rowsPerPage
				+ " offset " + count + ") as t";

		List result = jdbcTemplate.query(strSql, param, rowMapper);

		String countSql = "select count(*) as allcount from (" + sql + ") as t";
		int allcount = jdbcTemplate.queryForInt(countSql);

		int totalPage = 0;
		if (allcount % rowsPerPage == 0) {
			totalPage = allcount / rowsPerPage;
		} else {
			totalPage = allcount / rowsPerPage + 1;
		}

		page.setCurPage(curPage);
		page.setPerPage(rowsPerPage);
		page.setResults(result);
		page.setTotalNum(allcount);
		page.setTotalPage(totalPage);

		return page;
	}

	/**
	 * 
	 * 查询分页结果 返回分页对象page page.getResults()返回为list，list中为具体包装后的对象
	 * 
	 * @param sql
	 *            String
	 * @param curPage
	 *            int 当前页数 从1开始
	 * @param param
	 *            List 参数List
	 * @param rowsPerPage
	 *            int 每页行数
	 * @param rowMapper
	 *            org.springframework.jdbc.core.RowMapper
	 * @return Page
	 */
	public Page getResultByPage(String sql, int curPage, List param,
			int rowsPerPage, RowMapper rowMapper) {
		Page page = new Page();

		int count = curPage * rowsPerPage - rowsPerPage; // 记录起点

		// 对sql语句进行再包装，添加分页
		// 解决spring jdbc queryForList连接查询无法正常显示结果列问题
		String strSql = "select t.* from (" + sql + " limit " + rowsPerPage
				+ " offset " + count + ") as t";

		Object[] args = new Object[param.size()];
		for (int i = 0; i < param.size(); i++) {
			args[i] = param.get(i);
		}

		List result = jdbcTemplate.query(strSql, args, rowMapper);

		String countSql = "select count(*) as allcount from (" + sql + ") as t";
		int allcount = jdbcTemplate.queryForInt(countSql);

		int totalPage = 0;
		if (allcount % rowsPerPage == 0) {
			totalPage = allcount / rowsPerPage;
		} else {
			totalPage = allcount / rowsPerPage + 1;
		}

		page.setCurPage(curPage);
		page.setPerPage(rowsPerPage);
		page.setResults(result);
		page.setTotalNum(allcount);
		page.setTotalPage(totalPage);

		return page;
	}

	/**
	 * 返回查询结果为List list中为map对象
	 * 
	 * @param sql
	 *            String
	 * @return List<Map>
	 */
	public List getResultList(String sql) {
		sql = "select * from (" + sql + ") as x";
		return this.getJdbcTemplate().queryForList(sql);
	}

	/**
	 * 返回查询结果为List list中为map对象
	 * 
	 * @param sql
	 *            String
	 * @param param
	 *            Object[]
	 * @return List<Map>
	 */
	public List getResultList(String sql, Object[] param) {
		return this.getJdbcTemplate().queryForList(sql, param);
	}

	/**
	 * 返回查询结果为List list中为map对象
	 * 
	 * @param sql
	 *            String
	 * @param param
	 *            List
	 * @return List<Map>
	 */
	public List getResultList(String sql, List param) {
		Object[] args = new Object[param.size()];
		for (int i = 0; i < param.size(); i++) {
			args[i] = param.get(i);
		}
		return this.getJdbcTemplate().queryForList(sql, args);
	}

	/**
	 * 返回查询结果为list list中为封装好的对象
	 * 
	 * @param sql
	 *            String
	 * @param rowMapper
	 *            org.springframework.jdbc.core.RowMapper
	 * @return List
	 */
	public List getResultList(String sql, RowMapper rowMapper) {
		sql = "select * from (" + sql + ") as x";
		return this.getJdbcTemplate().query(sql, rowMapper);
	}

	/**
	 * 返回查询结果为list list中为封装好的对象
	 * 
	 * @param sql
	 *            String
	 * @param param
	 *            Object[]
	 * @param rowMapper
	 *            org.springframework.jdbc.core.RowMapper
	 * @return List
	 */
	public List getResultList(String sql, Object[] param, RowMapper rowMapper) {
		return this.getJdbcTemplate().query(sql, param, rowMapper);
	}

	/**
	 * 返回查询结果为list list中为封装好的对象
	 * 
	 * @param sql
	 *            String
	 * @param param
	 *            参数List
	 * @param rowMapper
	 *            org.springframework.jdbc.core.RowMapper
	 * @return List
	 */
	public List getResultList(String sql, List param, RowMapper rowMapper) {
		Object[] args = new Object[param.size()];
		for (int i = 0; i < param.size(); i++) {
			args[i] = param.get(i);
		}
		return this.getJdbcTemplate().query(sql, args, rowMapper);
	}

	/**
	 * 返回查询结果为Map
	 * 
	 * @param sql
	 *            String
	 * @return Map
	 */
	public Map getResultMap(String sql) {
		Map map = null;
		sql = "select * from (" + sql + ") as x";
		List list = jdbcTemplate.queryForList(sql);
		if (list != null && list.size() > 0) {
			map = (Map) list.get(0);
		}
		return map;
	}

	/**
	 * 返回查询结果为Map
	 * 
	 * @param sql
	 *            String
	 * @param param
	 *            Object[]
	 * @return Map
	 */
	public Map getResultMap(String sql, Object[] param) {
		Map map = null;
		List list = this.getJdbcTemplate().queryForList(sql, param);
		if (list != null && list.size() > 0) {
			map = (Map) list.get(0);
		}
		return map;
	}

	/**
	 * 返回查询结果为Map
	 * 
	 * @param sql
	 *            String
	 * @param param
	 *            List 参数List
	 * @return Map
	 */
	public Map getResultMap(String sql, List param) {
		Map map = null;

		Object[] args = new Object[param.size()];
		for (int i = 0; i < param.size(); i++) {
			args[i] = param.get(i);
		}

		List list = this.getJdbcTemplate().queryForList(sql, args);
		if (list != null && list.size() > 0) {
			map = (Map) list.get(0);
		}
		return map;
	}

	/**
	 * 查询单个数据 对spring JdbcTemplate进行了再包装
	 * 
	 * @param sql
	 *            String
	 * @param rowMapper
	 *            org.springframework.jdbc.core.RowMapper
	 * @return Object
	 */
	public Object getResultObject(String sql, RowMapper rowMapper) {
		List list = jdbcTemplate.query(sql, rowMapper);

		if (list != null && list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 查询单个数据 对spring JdbcTemplate进行了再包装
	 * 
	 * @param sql
	 *            String
	 * @param param
	 *            Object[]
	 * @param rowMapper
	 *            org.springframework.jdbc.core.RowMapper
	 * @return Object
	 */
	public Object getResultObject(String sql, Object[] param,
			RowMapper rowMapper) {
		List list = this.getJdbcTemplate().query(sql, param, rowMapper);

		if (list != null && list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 查询单个数据 对spring JdbcTemplate进行了再包装
	 * 
	 * @param sql
	 *            String
	 * @param param
	 *            List 参数List
	 * @param rowMapper
	 *            org.springframework.jdbc.core.RowMapper
	 * @return Object
	 */
	public Object getResultObject(String sql, List param, RowMapper rowMapper) {

		Object[] args = new Object[param.size()];
		for (int i = 0; i < param.size(); i++) {
			args[i] = param.get(i);
		}

		List list = this.getJdbcTemplate().query(sql, args, rowMapper);

		if (list != null && list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 执行insert update delete等更新操作
	 * 
	 * @param sql
	 *            String
	 * @return int 更新结果数
	 */
	public int update(String sql) {
		return this.getJdbcTemplate().update(sql);
	}

	/**
	 * 执行insert update delete等更新操作
	 * 
	 * @param sql
	 *            String
	 * @param param
	 *            Object[]
	 * @return int 更新结果数
	 */
	public int update(String sql, Object[] param) {
		return this.getJdbcTemplate().update(sql, param);
	}

	/**
	 * 查询单个数据(兼容以前) 对spring JdbcTemplate进行了再包装
	 * 
	 * @param sql
	 *            String
	 * @param rowMapper
	 *            org.springframework.jdbc.core.RowMapper
	 * @return Object
	 */
	public Object queryForObject(String sql, RowMapper rowMapper) {
		List list = jdbcTemplate.query(sql, rowMapper);

		if (list != null && list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 查询单个数据(兼容以前) 对spring JdbcTemplate进行了再包装
	 * 
	 * @param sql
	 *            String
	 * @param param
	 *            Object[]
	 * @param rowMapper
	 *            org.springframework.jdbc.core.RowMapper
	 * @return Object
	 */
	public Object queryForObject(String sql, Object[] param, RowMapper rowMapper) {
		List list = this.getJdbcTemplate().query(sql, param, rowMapper);

		if (list != null && list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 查询单个数据(兼容以前) 对spring JdbcTemplate进行了再包装
	 * 
	 * @param sql
	 *            String
	 * @param param
	 *            List 参数List
	 * @param rowMapper
	 *            org.springframework.jdbc.core.RowMapper
	 * @return Object
	 */
	public Object queryForObject(String sql, List param, RowMapper rowMapper) {

		Object[] args = new Object[param.size()];
		for (int i = 0; i < param.size(); i++) {
			args[i] = param.get(i);
		}

		List list = this.getJdbcTemplate().query(sql, args, rowMapper);

		if (list != null && list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

}
