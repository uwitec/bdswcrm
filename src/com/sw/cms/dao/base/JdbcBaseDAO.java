package com.sw.cms.dao.base;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.sw.cms.model.Page;

/**
 * ����DAO���Ҫ�̳д��ֲ࣬����JdbcTemplate<BR>
 * ��ӷ�ҳ����������Ĵ�����չ��spring jdbc�Ĳ��ַ������޲���JdbcTemplate������� <BR>
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
	 * ��ѯ��ҳ��� ���ط�ҳ����page page.getResults()����Ϊlist��list��ΪMap���� �ֶ���Ϊkey,ֵΪvalue
	 * 
	 * @param sql
	 *            String
	 * @param curPage
	 *            int ��ǰҳ�� ��1��ʼ
	 * @param rowsPerPage
	 *            int ÿҳ����
	 * @return Page
	 */
	public Page getResultByPage(String sql, int curPage, int rowsPerPage) {
		Page page = new Page();

		int count = curPage * rowsPerPage - rowsPerPage; // ��¼���

		// ��sql�������ٰ�װ����ӷ�ҳ
		// ���spring jdbc queryForList���Ӳ�ѯ�޷�������ʾ���������
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
	 * ��ѯ��ҳ��� ���ط�ҳ����page page.getResults()����Ϊlist��list��ΪMap���� �ֶ���Ϊkey,ֵΪvalue
	 * 
	 * @param sql
	 *            String
	 * @param param
	 *            Object[] ������
	 * @param curPage
	 *            int ��ǰҳ�� ��1��ʼ
	 * @param rowsPerPage
	 *            int ÿҳ����
	 * @return Page
	 */
	public Page getResultByPage(String sql, Object[] param, int curPage,
			int rowsPerPage) {
		Page page = new Page();

		int count = curPage * rowsPerPage - rowsPerPage; // ��¼���

		// ��sql�������ٰ�װ����ӷ�ҳ
		// ���spring jdbc queryForList���Ӳ�ѯ�޷�������ʾ���������
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
	 * ��ѯ��ҳ��� ���ط�ҳ����page page.getResults()����Ϊlist��list��ΪMap���� �ֶ���Ϊkey,ֵΪvalue
	 * 
	 * @param sql
	 *            String
	 * @param param
	 *            List ����List
	 * @param curPage
	 *            int ��ǰҳ�� ��1��ʼ
	 * @param rowsPerPage
	 *            int ÿҳ����
	 * @return Page
	 */
	public Page getResultByPage(String sql, List param, int curPage,
			int rowsPerPage) {
		Page page = new Page();

		int count = curPage * rowsPerPage - rowsPerPage; // ��¼���

		// ��sql�������ٰ�װ����ӷ�ҳ
		// ���spring jdbc queryForList���Ӳ�ѯ�޷�������ʾ���������
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
	 * ��ѯ��ҳ��� ���ط�ҳ����page page.getResults()����Ϊlist��list��Ϊ�����װ��Ķ���
	 * 
	 * @param sql
	 *            String
	 * @param curPage
	 *            int ��ǰҳ�� ��1��ʼ
	 * @param rowsPerPage
	 *            int ÿҳ����
	 * @param rowMapper
	 *            org.springframework.jdbc.core.RowMapper
	 * @return Page
	 */
	public Page getResultByPage(String sql, int curPage, int rowsPerPage,
			RowMapper rowMapper) {
		Page page = new Page();

		int count = curPage * rowsPerPage - rowsPerPage; // ��¼���

		// ��sql�������ٰ�װ����ӷ�ҳ
		// ���spring jdbc queryForList���Ӳ�ѯ�޷�������ʾ���������
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
	 * ��ѯ��ҳ��� ���ط�ҳ����page page.getResults()����Ϊlist��list��Ϊ�����װ��Ķ���
	 * 
	 * @param sql
	 *            String
	 * @param curPage
	 *            int ��ǰҳ�� ��1��ʼ
	 * @param param
	 *            Object[] ������
	 * @param rowsPerPage
	 *            int ÿҳ����
	 * @param rowMapper
	 *            org.springframework.jdbc.core.RowMapper
	 * @return Page
	 */
	public Page getResultByPage(String sql, int curPage, Object[] param,
			int rowsPerPage, RowMapper rowMapper) {
		Page page = new Page();

		int count = curPage * rowsPerPage - rowsPerPage; // ��¼���

		// ��sql�������ٰ�װ����ӷ�ҳ
		// ���spring jdbc queryForList���Ӳ�ѯ�޷�������ʾ���������
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
	 * ��ѯ��ҳ��� ���ط�ҳ����page page.getResults()����Ϊlist��list��Ϊ�����װ��Ķ���
	 * 
	 * @param sql
	 *            String
	 * @param curPage
	 *            int ��ǰҳ�� ��1��ʼ
	 * @param param
	 *            List ����List
	 * @param rowsPerPage
	 *            int ÿҳ����
	 * @param rowMapper
	 *            org.springframework.jdbc.core.RowMapper
	 * @return Page
	 */
	public Page getResultByPage(String sql, int curPage, List param,
			int rowsPerPage, RowMapper rowMapper) {
		Page page = new Page();

		int count = curPage * rowsPerPage - rowsPerPage; // ��¼���

		// ��sql�������ٰ�װ����ӷ�ҳ
		// ���spring jdbc queryForList���Ӳ�ѯ�޷�������ʾ���������
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
	 * ���ز�ѯ���ΪList list��Ϊmap����
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
	 * ���ز�ѯ���ΪList list��Ϊmap����
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
	 * ���ز�ѯ���ΪList list��Ϊmap����
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
	 * ���ز�ѯ���Ϊlist list��Ϊ��װ�õĶ���
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
	 * ���ز�ѯ���Ϊlist list��Ϊ��װ�õĶ���
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
	 * ���ز�ѯ���Ϊlist list��Ϊ��װ�õĶ���
	 * 
	 * @param sql
	 *            String
	 * @param param
	 *            ����List
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
	 * ���ز�ѯ���ΪMap
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
	 * ���ز�ѯ���ΪMap
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
	 * ���ز�ѯ���ΪMap
	 * 
	 * @param sql
	 *            String
	 * @param param
	 *            List ����List
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
	 * ��ѯ�������� ��spring JdbcTemplate�������ٰ�װ
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
	 * ��ѯ�������� ��spring JdbcTemplate�������ٰ�װ
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
	 * ��ѯ�������� ��spring JdbcTemplate�������ٰ�װ
	 * 
	 * @param sql
	 *            String
	 * @param param
	 *            List ����List
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
	 * ִ��insert update delete�ȸ��²���
	 * 
	 * @param sql
	 *            String
	 * @return int ���½����
	 */
	public int update(String sql) {
		return this.getJdbcTemplate().update(sql);
	}

	/**
	 * ִ��insert update delete�ȸ��²���
	 * 
	 * @param sql
	 *            String
	 * @param param
	 *            Object[]
	 * @return int ���½����
	 */
	public int update(String sql, Object[] param) {
		return this.getJdbcTemplate().update(sql, param);
	}

	/**
	 * ��ѯ��������(������ǰ) ��spring JdbcTemplate�������ٰ�װ
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
	 * ��ѯ��������(������ǰ) ��spring JdbcTemplate�������ٰ�װ
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
	 * ��ѯ��������(������ǰ) ��spring JdbcTemplate�������ٰ�װ
	 * 
	 * @param sql
	 *            String
	 * @param param
	 *            List ����List
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
