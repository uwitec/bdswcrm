package com.sw.cms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.sw.cms.dao.base.JdbcBaseDAO;
import com.sw.cms.dao.base.SqlUtil;
import com.sw.cms.model.ProductKcInit;
import com.sw.cms.util.StaticParamDo;

public class ProductKcInitDAO extends JdbcBaseDAO {
	
	/**
	 * ȡ���п���ʼ�ⷿ�б�
	 * @return
	 */
	public List getProductKcInitList(){
		String sql = "select * from product_kc_init";
		return this.getResultList(sql, new ProductKcInitRowMapper());
	}
	
	
	/**
	 * �����ʼ����Ϣ
	 * @param productKcInit
	 */
	public void saveProductKcInit(ProductKcInit productKcInit){
		String sql = "insert into product_kc_init(store_id,jsr,create_date,czr,cz_date) values(?,?,?,?,now())";
		
		Object[] param = new Object[4];
		param[0] = productKcInit.getStore_id();
		param[1] = productKcInit.getJsr();
		param[2] = productKcInit.getCreate_date();
		param[3] = productKcInit.getCzr();
		
		this.getJdbcTemplate().update(sql,param);
	}
	
	
	/**
	 * ȡ����ʼ
	 * @param store_id
	 * @return
	 */
	public ProductKcInit getProductKcInit(String store_id){
		String sql = "select * from product_kc_init where store_id='" + store_id + "'";
		return (ProductKcInit)this.queryForObject(sql, new ProductKcInitRowMapper());
	}
	
	
	/**
	 * �жϿⷿ��ʼ�Ƿ����
	 * @param store_id
	 * @return
	 */
	public boolean isExist(String store_id){
		
		boolean is = false;
		String sql = "select * from product_kc_init where store_id='" + store_id + "'";
		
		List list = this.getResultList(sql);		
		if(list != null && list.size()>0){
			is = true;
		}
		return is;
	}
	
	
	/**
	 * ��װ����(����ʼ��)
	 * @author liyt
	 */
	class ProductKcInitRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			ProductKcInit productKcInit = new ProductKcInit();
			
			if(SqlUtil.columnIsExist(rs,"id")) productKcInit.setId(rs.getInt("id"));
			if(SqlUtil.columnIsExist(rs,"create_date")) productKcInit.setCreate_date(rs.getString("create_date"));
			if(SqlUtil.columnIsExist(rs,"jsr")) productKcInit.setJsr(rs.getString("jsr"));
			if(SqlUtil.columnIsExist(rs,"jsr")) productKcInit.setJsr_name(StaticParamDo.getRealNameById(rs.getString("jsr")));
			if(SqlUtil.columnIsExist(rs,"store_id")) productKcInit.setStore_id(rs.getString("store_id"));
			if(SqlUtil.columnIsExist(rs,"store_id")) productKcInit.setStore_name(StaticParamDo.getStoreNameById(rs.getString("store_id")));
			
			return productKcInit;
		}
	}

}
