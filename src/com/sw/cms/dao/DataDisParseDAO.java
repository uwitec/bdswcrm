package com.sw.cms.dao;

import java.util.List;
import java.util.Map;

import com.sw.cms.dao.base.JdbcBaseDAO;

/**
 * 字典解析处理
 * @author liyt
 *
 */
public class DataDisParseDAO extends JdbcBaseDAO {
	
	/**
	 * 根据用户ID返回用户姓名
	 * @param user_id
	 * @return
	 */
	public String getRealNameById(String user_id){
		String name = user_id;
		String sql = "select real_name from sys_user where user_id='" + user_id + "'";
		
		Map map = this.getResultMap(sql);
		if(map != null){
			name = (String)map.get("real_name");
		}
		
		return name;
	}
	
	
	/**
	 * 根据客户ID取客户名称
	 * @param client_id
	 * @return
	 */
	public String getClientNameById(String client_id){
		String name = client_id;
		
		String sql = "select * from clients where id='" + client_id + "'";
		
		List list = this.getJdbcTemplate().queryForList(sql);
		if(list != null && list.size()>0){
			Map map = (Map)list.get(0);
			name = (String)map.get("name");
		}
		
		return name;
	}
	
	
	/**
	 * 根据仓库ID取仓库名称
	 * @param id
	 * @return
	 */
	public String getStoreNameById(String id){
		String name = id;
		String sql = "select * from storehouse where id='" + id + "'";
		List list = this.getJdbcTemplate().queryForList(sql);
		if(list != null && list.size()>0){
			Map map = (Map)list.get(0);
			name = (String)map.get("name");
		}
		return name;
	}
	
	
	/**
	 * 根据商品类别编号取商品类别名称
	 * @param kind_id
	 * @return
	 */
	public String getKindNameByKindId(String kind_id){
		String name = kind_id;
		String sql = "select * from product_kind where id='" + kind_id + "'";
		List list = this.getJdbcTemplate().queryForList(sql);
		if(list != null && list.size()>0){
			Map map = (Map)list.get(0);
			name = (String)map.get("name");
		}
		
		return name;
	}
	
	
	/**
	 * 根据部门编号取部门名称
	 * @param dept_id
	 * @return
	 */
	public String getDeptNameById(String dept_id){
		String name = dept_id;
		String sql = "select * from dept where dept_id='" + dept_id + "'";
		
		List list = this.getJdbcTemplate().queryForList(sql);
		if(list != null && list.size()>0){
			Map map = (Map)list.get(0);
			name = (String)map.get("dept_name");
		}
		
		return name;
	}
	
	
	/**
	 * 根据账户编号取账户名称
	 * @param id
	 * @return
	 */
	public String getAccountNameById(String id){
		String name = id;
		String sql = "select * from accounts where id='" + id + "'";
		Map map = this.getResultMap(sql);
		if(map != null){
			name = (String)map.get("name");
		}
		return name;
	}
	
	
	/**
	 * 根据费用类型编号取费用类型名称
	 * @param id
	 * @return
	 */
	public String getFyTypeNameById(String id){
		String name = id;
		String sql = "select * from fy_type where id='" + id + "'";
		Map map = this.getResultMap(sql);
		if(map != null){
			name = (String)map.get("name");
		}
		return name;
	}
	
	
	/**
	 * 根据用户ID返回用户部门名称
	 * @param user_id
	 * @return
	 */
	public String gerDeptNameByUserId(String user_id) {
		String name = "";
		String sql = "select dept_name from sys_user a left join dept b on b.dept_id = a.dept where a.user_id = '" + user_id + "'";
		Map map = this.getResultMap(sql);
		if(map != null){
			name = (String)map.get("dept_name");
		}
		return name;
	}

}
