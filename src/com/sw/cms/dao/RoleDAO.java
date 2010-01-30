package com.sw.cms.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sw.cms.dao.base.JdbcBaseDAO;
import com.sw.cms.model.Page;

public class RoleDAO extends JdbcBaseDAO {
	
	/**
	 * 取角色列表，带分页
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getRoleList(String con,int curPage, int rowsPerPage){
		
		String sql = "select * from role where 1=1";
		
		if(!con.equals("")){
			sql = sql + con;
		}
		
		sql = sql + " order by xh desc";
		
		return this.getResultByPage(sql, curPage, rowsPerPage);
		
	}
	
	/**
	 * 取所有角色列表
	 * @return
	 */
	public List getAllRoles(){
		String sql = "select * from role order by xh desc";
		return this.getJdbcTemplate().queryForList(sql);
	}
	
	
	/**
	 * 保存角色信息
	 * @param role_name
	 * @param xh
	 */
	public void saveRole(String role_name,int xh){
		String role_id = getRoleID();
		String sql = "insert into role(role_id,role_name,xh) values(?,?,?)";
		
		Object[] param = new Object[3];
		param[0] = role_id;
		param[1] = role_name;
		param[2] = new Integer(xh);
		
		this.getJdbcTemplate().update(sql,param);
	}
	
	
	/**
	 * 更新角色信息
	 * @param role_id
	 * @param role_name
	 * @param xh
	 */
	public void updateRole(String role_id,String role_name,int xh){
		String sql = "update role set role_name=?,xh=? where role_id=?";
		
		Object[] param = new Object[3];
		
		param[0] = role_name;
		param[1] = new Integer(xh);
		param[2] = role_id;
		
		this.getJdbcTemplate().update(sql,param);
	}
	
	
	/**
	 * 根据role_id获取角色信息
	 * @param role_id
	 * @return
	 */
	public Map getRoleByID(String role_id){
		String sql = "select * from role where role_id='" + role_id + "'";
		
		return this.getJdbcTemplate().queryForMap(sql);
	}
	
	
	/**
	 * 删除角色信息
	 * @param role_id
	 */
	public void delRoleByID(String role_id){
		String sql = "delete from role where role_id='" + role_id + "'";
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * 根据role_id取功能列表
	 * @param role_id
	 * @return
	 */
	public List getRoleFuncList(String role_id){
		List<String> roleFuncs = new ArrayList<String>();
		String sql = "select * from role_func where role_id='" + role_id + "'";
		List list = this.getJdbcTemplate().queryForList(sql);
		if(list != null && list.size()>0){
			for(int i=0;i<list.size();i++){
				Map map = (Map)list.get(i);
				roleFuncs.add((String)map.get("func_id"));
			}
		}
		return roleFuncs;
	}
	
	
	/**
	 * 保存角色相关功能
	 * @param role_id
	 * @param func_ids
	 */
	public void saveRoleFuncs(String role_id,String[] func_ids){
		String sql = "delete from role_func where role_id='" + role_id + "'";
		this.getJdbcTemplate().update(sql);
		
		if(func_ids != null && func_ids.length>0){
			for(int i=0;i<func_ids.length;i++){
				sql = "insert into role_func(role_id,func_id) values('"+ role_id +"','" + func_ids[i] + "')";
				this.getJdbcTemplate().update(sql);
			}
		}
	}
	
	
	/**
	 * 取具价格审批权限角色列表
	 * @return
	 */
	public String getJgspRoles(){
		String roles = "";
		
		String sql = "select * from jgsp_right_roles";		
		List list = this.getResultList(sql);
		if(list != null && list.size() > 0){
			for(int i=0;i<list.size();i++){
				Map map = (Map)list.get(i);				
				if(roles.equals("")){
					roles = (String)map.get("role_id");
				}else{
					roles += "," + (String)map.get("role_id");
				}
			}
		}
		
		return roles;
	}
	
	
	/**
	 * 保存价格审批权限角色信息
	 * @param role_id
	 */
	public void saveJgSpRightRoles(String[] role_id){
		String sql = "delete from jgsp_right_roles";
		this.getJdbcTemplate().update(sql);
		
		if(role_id != null && role_id.length>0){
			for(int i=0;i<role_id.length;i++){
				sql = "insert into jgsp_right_roles(role_id) values('" + role_id[i] + "')";
				this.getJdbcTemplate().update(sql);
			}
		}
	}
	
	
	/**
	 * 获取超期审批权限角色列表
	 * @return
	 */
	public String getCqspRoles(){
		String roles = "";
		
		String sql = "select * from cqsp_right_roles";		
		List list = this.getResultList(sql);
		if(list != null && list.size() > 0){
			for(int i=0;i<list.size();i++){
				Map map = (Map)list.get(i);				
				if(roles.equals("")){
					roles = (String)map.get("role_id");
				}else{
					roles += "," + (String)map.get("role_id");
				}
			}
		}
		
		return roles;
	}
	
	
	/**
	 * 保存超期审批权限角色信息
	 * @param role_id
	 */
	public void saveCqSpRightRoles(String[] role_id){
		String sql = "delete from cqsp_right_roles";
		this.getJdbcTemplate().update(sql);
		
		if(role_id != null && role_id.length>0){
			for(int i=0;i<role_id.length;i++){
				sql = "insert into cqsp_right_roles(role_id) values('" + role_id[i] + "')";
				this.getJdbcTemplate().update(sql);
			}
		}
	}
	
	
	/**
	 * 获取审批相关信息
	 * @return
	 */
	public Map getSpRight(String yw_type){
		String sql = "select * from sp_right where yw_type='" + yw_type + "'";
		return this.getResultMap(sql);
	}
	
	
	/**
	 * 保存审批相关信息
	 * @param sp_flag
	 * @param role_id
	 */
	public void saveSpRight(String sp_flag,String role_id,String yw_type){
		String sql = "delete from sp_right where yw_type='" + yw_type + "'";
		this.getJdbcTemplate().execute(sql);
		
		sql = "insert into sp_right(sp_flag,role_id,yw_type) values('" + sp_flag + "','" + role_id + "','" + yw_type + "')";
		this.getJdbcTemplate().execute(sql);
	}
	
	
	/**
	 * 取当前可用的序列号
	 * @return
	 */
	private String getRoleID() {
		String sql = "select roleid from cms_all_seq";

		// 取当前序列号
		String curId = this.getJdbcTemplate().queryForInt(sql) + "";

		// 将序列号加1
		sql = "update cms_all_seq set roleid=roleid+1";
		this.getJdbcTemplate().update(sql);

		for (int i = curId.length(); i < 8; i++) {
			curId = "0" + curId;
		}

		return "RL" + curId;
	}

}
