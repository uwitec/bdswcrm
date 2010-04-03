package com.sw.cms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

import com.sw.cms.dao.base.JdbcBaseDAO;
import com.sw.cms.model.ProductKind;

public class ProductKindDAO extends JdbcBaseDAO{
	
	/**
	 * ȡ������Ʒ����б�
	 * @return
	 */
	public List getAllProductKindList(){		
		String sql = "select * from product_kind";		
		return this.getJdbcTemplate().queryForList(sql);
	}
	
	/**
	 * ����IDȡ��Ʒ���
	 * @param id
	 * @return
	 */
	public Object getProductKindInfoById(String id){
		String sql = "select id,name,parent_id,ms from product_kind where id='" + id + "'";
		
		return this.queryForObject(sql, new ProductKindRowMapper());
	}
	
	
	/**
	 * �����Ʒ�����Ϣ
	 * @param productKind
	 */
	public void saveProductKind(ProductKind productKind){
		String sql = "insert into product_kind(id,name,parent_id,ms) values(?,?,?,?)";
		String parent_id = productKind.getParent_id();
		if(parent_id.equals("")){
			parent_id = "0";
		}
		Object[] params = new Object[4];
		params[0] = getProductKindID(productKind.getParent_id());
		params[1] = productKind.getName();
		params[2] = parent_id;
		params[3] = productKind.getMs();
		
		this.getJdbcTemplate().update(sql,params);
	}
	
	
	
	/**
	 * ������Ʒ��Ϣ
	 * @param productKind
	 */
	public void updateProductKind(ProductKind productKind){
		String sql = "update product_kind set name=?,ms=? where id=?";
		
		Object[] params = new Object[3];
		
		params[0] = productKind.getName();
		params[1] = productKind.getMs();
		params[2] = productKind.getId();
		
		this.getJdbcTemplate().update(sql,params);
	}
	
	
	/**
	 * �������������
	 * @param id
	 * @return
	 */
	public int getChildKindCount(String id){
		String sql = "select count(*) as acount from product_kind where parent_id='" + id + "'";		
		return this.getJdbcTemplate().queryForInt(sql);
	}
	
	/**
	 * �������������Ʒ������
	 * @param id
	 * @return
	 */
	public int getChildProductCount(String id){
		String sql = "select count(*) from product where product_kind='" + id + "'";
		return this.getJdbcTemplate().queryForInt(sql);
	}
	
	/**
	 * ɾ����Ʒ������Ϣ
	 * @param id
	 */
	public void delProductKind(String id){
		String sql = "delete from product_kind where id='" + id + "'";
		
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * ������Ʒ�����ȡ��Ʒ�������
	 * @param kind_id
	 * @return
	 */
	public String getKindNameByKindId(String kind_id){
		String name = "";
		String sql = "select * from product_kind where id='" + kind_id + "'";
		Object obj = this.queryForObject(sql, new ProductKindRowMapper());
		
		if(obj != null){
			name = ((ProductKind)obj).getName();
		}
		
		return name;
	}
	
	
	/**
	 * ȡ��һ�����õ����ID
	 * @return
	 */
	private String getProductKindID(String parent_id) {
		
		String ID = parent_id;
		if(parent_id.equals("")){
			ID = "0";
		}
		String rtId = "";
		String sql = "select max(id) as id from product_kind where parent_id='" + ID + "'";
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
	 * 
	 * @author liyt
	 * 
	 */
	class ProductKindRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			ProductKind productKind = new ProductKind();

			productKind.setId(rs.getString("id"));
			productKind.setName(rs.getString("name"));
			productKind.setParent_id(rs.getString("parent_id"));
			productKind.setMs(rs.getString("ms"));
			
			return productKind;
		}
	}

}
