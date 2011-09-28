package com.sw.cms.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sw.cms.dao.base.JdbcBaseDAO;
import com.sw.cms.util.StringUtils;

public class MenuDAO extends JdbcBaseDAO {	
	
	
	/**
	 * �����û�����Ȩ�޵�ҵ���ܲ˵�
	 * @param user_id
	 * @param column_id
	 * @return
	 */
	public List getUserYwgzFunc(String user_id,String column_id){
		String sql = "select * from " +
				"(select distinct(c.func_name) as func_name,c.url,c.img from user_role a,role_func b,funcs c,column_funcs d " +
				"where c.func_id=b.func_id and b.role_id=a.role_id and d.func_id=b.func_id and c.ywflag='1' and  a.user_id='" + user_id + "' and d.column_id='" + column_id + "' order by c.xh) x";
		
		return this.getResultList(sql);		
	}
	
	
	/**
	 * �����û����е�ҵ�����б�
	 * @param user_id
	 * @return
	 */
	public List getUserYwgnFunc(String user_id){
		String sql = "select * from " +
			"(select distinct(c.func_id) as func_id from user_role a,role_func b,funcs c " +
			"where c.func_id=b.func_id and b.role_id=a.role_id and c.ywflag='1' and  a.user_id='" + user_id + "'  order by c.xh) x";

		return this.getResultList(sql);	
	}
	
	
	/**
	 * ���ݸ���ĿID���û����ȡ�û�����Ȩ�޵�ҵ������Ŀ���
	 * @param user_id
	 * @param parent_id
	 * @return
	 */
	public List getColumnList(String user_id,String parent_id,String yw_flag){
		List<Map> list = new ArrayList<Map>();
		String sql = "select * from column_mng where 1=1";
		if(!parent_id.equals("")){
			sql += " and parent_id='" + parent_id + "'";
		}
		if(!yw_flag.equals("")){
			sql += " and yw_flag='" + yw_flag + "'";
		}
		sql += " order by xh";
		List allList = this.getResultList(sql);
		if(allList != null && allList.size() > 0){
			for(int i=0;i<allList.size();i++){
				Map map = (Map)allList.get(i);
				String column_id = (StringUtils.nullToStr(map.get("id")));
				
				if(getHasFlag(user_id,column_id)){
					list.add(map);
				}
			}
		}
		return list;
	}
	
	
	/**
	 * �ж��û��Ƿ����ҵ��˵�Ȩ��
	 * @param user_id
	 * @param column_id
	 * @return
	 */
	private boolean getHasFlag(String user_id,String column_id){
		boolean is = false;
		String sql = "select * from (select distinct(c.func_name) as func_name,c.url,c.img from user_role a,role_func b,funcs c,column_funcs d " +
					 "where c.func_id=b.func_id and b.role_id=a.role_id and d.func_id=b.func_id and c.ywflag='1' and a.user_id='" + user_id + "' and d.column_id like '" + column_id + "%' order by c.xh) x";

		List list = this.getResultList(sql);
		
		if(list != null && list.size() > 0){
			is = true;
		}
		return is;
	}
	
	
	
	/**
	 * �����û���ȡ�ڲ��칫�˵��б�
	 * @param user_id
	 * @return
	 */
	public List getNbbgFuncsByUserID(String user_id){
		String sql = "select * from (select distinct(c.func_name) as func_name,c.url,c.img from user_role a,role_func b,funcs c" +
				" where c.func_id=b.func_id and b.role_id=a.role_id and a.user_id='"+user_id+"' and c.ywflag='0'  order by c.xh) x";
		return this.getJdbcTemplate().queryForList(sql);		
	}

}
