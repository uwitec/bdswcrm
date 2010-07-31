package com.sw.cms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.sw.cms.dao.base.JdbcBaseDAO;
import com.sw.cms.model.Page;
import com.sw.cms.model.StoreHouse;

public class StoreDAO extends JdbcBaseDAO {
	
	
	/**
	 * 取所有仓库列表
	 * @return
	 */
	public List getAllStoreList(){
		String sql = "select * from storehouse where id not like 'WX%'";
		
		return this.getJdbcTemplate().query(sql, new StoreHouseRowMapper());
	}
	
	
	/**
	 * 根据仓库ID取仓库名称
	 * @param id
	 * @return
	 */
	public String getStoreNameById(String id){
		String name = "";
		String sql = "select * from storehouse where id='" + id + "'";
		Object obj = this.queryForObject(sql, new StoreHouseRowMapper());
		if(obj != null){
			name = ((StoreHouse)obj).getName();
		}
		return name;
	}
	
	
	/**
	 * 返回分页后的库房列表
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getStores(String con,int curPage, int rowsPerPage){
		String sql = "select * from storehouse where 1=1";
		if(!con.equals("")){
			sql = sql + con;
		}
		return this.getResultByPage(sql, curPage, rowsPerPage);
	}
	
	
	
	/**
	 * 保存仓库信息
	 * @param store
	 */
	public void saveStore(StoreHouse store){
		String sql = "insert into storehouse(id,name,address,lxr,lxdh,mobile,msn,qq,remark) values(?,?,?,?,?,?,?,?,?)";
		
		Object[] param = new Object[9];
		
		param[0] = getKfID();
		param[1] = store.getName();
		param[2] = store.getAddress();
		param[3] = store.getLxr();
		param[4] = store.getLxdh();
		param[5] = store.getMobile();
		param[6] = store.getMsn();
		param[7] = store.getQq();
		param[8] = store.getRemark();
		
		this.getJdbcTemplate().update(sql,param);
	}
	
	
	
	/**
	 * 更新库房信息
	 * @param store
	 */
	public void updateStore(StoreHouse store){
		String sql = "update storehouse set name=?,address=?,lxr=?,lxdh=?,mobile=?,msn=?,qq=?,remark=? where id=?";
		
		Object[] param = new Object[9];
		
		param[0] = store.getName();
		param[1] = store.getAddress();
		param[2] = store.getLxr();
		param[3] = store.getLxdh();
		param[4] = store.getMobile();
		param[5] = store.getMsn();
		param[6] = store.getQq();
		param[7] = store.getRemark();
		param[8] = store.getId();
		
		
		this.getJdbcTemplate().update(sql,param);
	}
	
	
	
	/**
	 * 根据ID取仓库信息
	 * @param id
	 * @return
	 */
	public Object getStore(String id){
		String sql = "select * from storehouse where id='" + id + "'";
		
		return this.queryForObject(sql, new StoreHouseRowMapper());
	}
	
	
	
	/**
	 * 删除仓库信息
	 * @param id
	 */
	public void delStore(String id){
		String sql = "delete from storehouse where id='" + id + "'";
		
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * 判断库房资料是否可以删除<BR>
	 * 如果发生业务关系不可以删除<BR>
	 * 业务关系包括：入库单、出库单、调拨申请、库房调拨、存在库存值<BR>
	 * @param id boolean true:可以；false:不可以
	 */
	public boolean isCanDel(String id){
		
		//判断是否存在入库单
		String sql = "select count(*) as counts from ckd where store_id='" + id + "'";
		if(this.getJdbcTemplate().queryForInt(sql) > 0){
			return false;
		}
		
		//判断是否存在出库单
		sql = "select count(*) as counts from rkd where store_id='" + id + "'";
		if(this.getJdbcTemplate().queryForInt(sql) > 0){
			return false;
		}
		
		//判断是否存在调拨申请
		sql = "select count(*) as counts from dbsq where store_id='" + id + "'";
		if(this.getJdbcTemplate().queryForInt(sql) > 0){
			return false;
		}
		
		//判断是否存在库房调拨
		sql = "select count(*) as counts from kfdb where ck_store_id='" + id + "' or rk_store_id='" + id + "'";
		if(this.getJdbcTemplate().queryForInt(sql) > 0){
			return false;
		}	
		
		//判断是否存在库存值
		sql = "select count(*) as counts from product_kc where store_id='" + id + "'";
		if(this.getJdbcTemplate().queryForInt(sql) > 0){
			return false;
		}		
		
		return true;
	}
	
	
	/**
	 * 取当前可用的序列号
	 * @return
	 */
	private String getKfID() {
		String sql = "select kfid from cms_all_seq";

		// 取当前序列号
		String curId = this.getJdbcTemplate().queryForInt(sql) + "";

		// 将序列号加1
		sql = "update cms_all_seq set kfid=kfid+1";
		this.getJdbcTemplate().update(sql);

		for (int i = curId.length(); i < 8; i++) {
			curId = "0" + curId;
		}

		return "KF" + curId;
	}
	
	
	
	/**
	 * 包装对象(仓库对象)
	 * 
	 * @author liyt
	 * 
	 */
	class StoreHouseRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			StoreHouse store = new StoreHouse();

			store.setId(rs.getString("id"));
			store.setName(rs.getString("name"));
			store.setAddress(rs.getString("address"));
			store.setLxr(rs.getString("lxr"));
			store.setLxdh(rs.getString("lxdh"));
			store.setMobile(rs.getString("mobile"));
			store.setMsn(rs.getString("msn"));
			store.setQq(rs.getString("qq"));
			store.setRemark(rs.getString("remark"));
			
			return store;
		}
	}

}
