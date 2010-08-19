package com.sw.cms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

import com.sw.cms.dao.base.JdbcBaseDAO;
import com.sw.cms.dao.base.SqlUtil;
import com.sw.cms.model.Dept;

/**
 * 部门处理
 * 
 * @author liyt
 * 
 */
public class DeptDAO extends JdbcBaseDAO {

	/**
	 * 取所有部门列表
	 * 
	 * @return
	 */
	public List getAllDeptList() {
		String sql = "select * from dept order by xh desc";
		return this.getJdbcTemplate().queryForList(sql);
	}

	/**
	 * 根据部门名称获取部门ID
	 * 
	 * @param name
	 * @return
	 */
	public List getDeptsByName(String name) {
		String sql = "select *from dept where dept_name='" + name + "'";
		return this.getJdbcTemplate().queryForList(sql);
	}

	/**
	 * 编辑部门信息
	 * 
	 * @param dept_id
	 * @return
	 */
	public Dept editDept(String dept_id) {
		String sql = "select * from dept where dept_id='" + dept_id + "'";
		return (Dept) this.queryForObject(sql, new DeptRowMapper());
	}

	/**
	 * 根据部门编号取部门名称
	 * 
	 * @param dept_id
	 * @return
	 */
	public String getDeptNameById(String dept_id) {
		String name = "";
		String sql = "select * from dept where dept_id='" + dept_id + "'";

		Object obj = this.queryForObject(sql, new DeptRowMapper());
		if (obj != null) {
			name = ((Dept) obj).getDept_name();
		}

		return name;
	}

	/**
	 * 部门列表（封装好的部门对象）
	 * 
	 * @return
	 */
	public List getDepts() {
		String sql = "select * from dept  order by dept_id";
		return this.getResultList(sql, new DeptRowMapper());
	}

	/**
	 * 更新部门信息
	 * 
	 * @param dept
	 */
	public void updateDept(Dept dept) {
		if (dept.getDept_id().equals("")) {
			// 添加
			String sql = "insert into dept(dept_id,dept_name,fzr,phone,xh,parent_id) values(?,?,?,?,?,?)";
			Object[] param = new Object[6];

			String parent_id = dept.getParent_id();
			if (parent_id == null || parent_id.equals("")) {
				parent_id = "0";
			}

			param[0] = this.getDeptId(dept.getParent_id());
			param[1] = dept.getDept_name();
			param[2] = dept.getFzr();
			param[3] = dept.getPhone();
			param[4] = dept.getXh();
			param[5] = parent_id;

			this.getJdbcTemplate().update(sql, param);
		} else {
			// 修改
			String sql = "update dept set dept_name=?,fzr=?,phone=?,xh=?,parent_id=? where dept_id=?";

			Object[] param = new Object[6];

			String parent_id = dept.getParent_id();
			if (parent_id == null || parent_id.equals("")) {
				parent_id = "0";
			}

			param[0] = dept.getDept_name();
			param[1] = dept.getFzr();
			param[2] = dept.getPhone();
			param[3] = dept.getXh();
			param[4] = parent_id;
			param[5] = dept.getDept_id();

			this.getJdbcTemplate().update(sql, param);
		}
	}

	/**
	 * 判断部门信息是否可以删除<BR>
	 * 发生了费用支付的部门信息不能删除<BR>
	 * 
	 * @param dept_id
	 * @return boolean true:可以；false:不可以
	 */
	public boolean isCanDel(String dept_id){
		
		//判断是否存在费用申请
		String sql = "select count(*) from fysq where ywy_dept='" + dept_id + "'";
		if(this.getJdbcTemplate().queryForInt(sql) > 0){
			return false;
		}		
		
		return true;
	}
	
	/**
	 * 判断部门信息是否可以删除<BR>
	 * 有下属部门的部门信息不能删除<BR>
	 * 
	 * @param dept_id
	 * @return boolean true:可以；false:不可以
	 */
	public boolean hasChild(String dept_id){
		
		//判断是否存在下属部门
		String sql = "select count(*) from dept where parent_id='" + dept_id + "'";
		if(this.getJdbcTemplate().queryForInt(sql) > 0){
			return false;
		}		
		
		return true;
	}
	
	/**
	 * 删除部门信息
	 * 
	 * @param dept_id
	 */
	public void delDept(String dept_id) {
		String sql = "delete from dept where dept_id ='" + dept_id + "'";
		this.getJdbcTemplate().update(sql);
	}

	/**
	 * 取下一个可用的部门编号
	 * 
	 * @return
	 */
	private String getDeptId(String parent_id) {

		String ID = parent_id;
		if (parent_id.equals("")) {
			ID = "0";
		}
		String rtId = "";
		String sql = "select max(dept_id) as id from dept where parent_id='"
				+ ID + "'";
		List list = this.getResultList(sql);

		if (list != null && list.size() > 0) {
			Map map = (Map) list.get(0);

			String curMaxId = (String) map.get("id");

			if (curMaxId == null || curMaxId.equals("")) {
				rtId = parent_id + "01";
				return rtId;
			}
			curMaxId = curMaxId.substring(curMaxId.length() - 2, curMaxId
					.length());

			curMaxId = ((new Integer(curMaxId).intValue()) + 1) + "";

			for (int i = curMaxId.length(); i < 2; i++) {
				curMaxId = "0" + curMaxId;
			}
			rtId = parent_id + curMaxId;
		} else {
			rtId = parent_id + "01";
		}

		return rtId;
	}

	/**
	 * 包装对象(部门)
	 * 
	 * @author liyt
	 */
	class DeptRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			Dept dept = new Dept();

			if (SqlUtil.columnIsExist(rs, "dept_id"))
				dept.setDept_id(rs.getString("dept_id"));
			if (SqlUtil.columnIsExist(rs, "dept_name"))
				dept.setDept_name(rs.getString("dept_name"));
			if (SqlUtil.columnIsExist(rs, "fzr"))
				dept.setFzr(rs.getString("fzr"));
			if (SqlUtil.columnIsExist(rs, "phone"))
				dept.setPhone(rs.getString("phone"));
			if (SqlUtil.columnIsExist(rs, "xh"))
				dept.setXh(rs.getInt("xh"));
			if (SqlUtil.columnIsExist(rs, "parent_id"))
				dept.setParent_id(rs.getString("parent_id"));

			return dept;
		}
	}

}
