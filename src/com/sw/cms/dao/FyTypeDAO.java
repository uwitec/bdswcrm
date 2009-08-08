package com.sw.cms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

import com.sw.cms.dao.base.JdbcBaseDAO;
import com.sw.cms.dao.base.SqlUtil;
import com.sw.cms.model.FyType;

/**
 * �������͹���
 * @author jinyanni
 *
 */

public class FyTypeDAO extends JdbcBaseDAO {
	
	
	/**
	 * ȡ���������б�
	 * @return
	 */
	public List getFyTypeList(){
		String sql = "select * from fy_type where flag='1'";
		return this.getResultList(sql, new FyTypeRowMapper());
	}
	
	
	/**
	 * ����IDȡ�����������
	 * @param id
	 * @return
	 */
	public FyType getFyType(String id){
		String sql = "select * from fy_type where id='" + id + "'";
		Object obj = this.getJdbcTemplate().queryForObject(sql, new FyTypeRowMapper());
		if(obj == null){
			return null;
		}else{
			return (FyType)obj;
		}
	}
	
	
	/**
	 * ���·������ͣ����ڸ��£������ڲ���
	 * @param fyType
	 */
	public void updateFyType(FyType fyType){
		String sql = "";
		
		Object[] param = new Object[4];
		param[0] = fyType.getName();
		param[1] = fyType.getParent_id();
		param[2] = fyType.getMs();
		
		if(!fyType.getId().equals("")){
			//�����ڸ���
			sql = "update fy_type set name=?,parent_id=?,ms=? where id=?";
			param[3] = fyType.getId();
		}else{
			//��𲻴������
			sql = "insert into fy_type(name,parent_id,ms,id) values(?,?,?,?)";
			param[3] = this.getFyTypeID(fyType.getParent_id());
		}
		
		this.getJdbcTemplate().update(sql,param);		
	}
	
	
	
	/**
	 * ɾ����������
	 * @param id
	 */
	public void delFyType(String id){
		String sql = "update fy_type set flag='2' where id='" + id + "'";
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * �ж�һ�����������Ƿ�������
	 * @param id
	 * @return
	 */
	public boolean isChildren(String id){
		boolean is = false;
		
		String sql = "select count(*) as counts from fy_type where parent_id='" + id + "' and flag='1'";
		int count = this.getJdbcTemplate().queryForInt(sql);
		if(count > 0){
			is = true;
		}
		
		return is;
	}
	
	
	
	/**
	 * ȡ��һ�����õ����ID
	 * @return
	 */
	private String getFyTypeID(String parent_id) {
		
		String ID = parent_id;
		if(parent_id.equals("")){
			ID = "00";
		}
		String rtId = "";
		String sql = "select max(id) as id from fy_type where parent_id='" + ID + "'";
		List list = this.getResultList(sql);
		
		if(list!=null && list.size()>0){
			Map map = (Map)list.get(0);
			
			String curMaxId = (String)map.get("id");
			
			if(curMaxId == null || curMaxId.equals("")){
				rtId = parent_id + "01";
				return rtId;
			}
			curMaxId = curMaxId.substring(curMaxId.length()-2, curMaxId.length());
			
			curMaxId = ((new Integer(curMaxId).intValue())+1) + "";
			
			for (int i = curMaxId.length(); i < 2; i++) {
				curMaxId = "0" + curMaxId;
			}
			rtId = parent_id + curMaxId;
		}else{
			rtId = parent_id + "01";
		}
		
		return rtId;
	}
	
	
	
	/**
	 * ��װ����
	 * @author liyt
	 * 
	 */
	class FyTypeRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			FyType fyType = new FyType();

			if (SqlUtil.columnIsExist(rs, "id")) fyType.setId(rs.getString("id"));
			if (SqlUtil.columnIsExist(rs, "name")) fyType.setName(rs.getString("name"));
			if (SqlUtil.columnIsExist(rs, "parent_id")) fyType.setParent_id(rs.getString("parent_id"));
			if (SqlUtil.columnIsExist(rs, "ms")) fyType.setMs(rs.getString("ms"));
			
			return fyType;
		}
	}

}
