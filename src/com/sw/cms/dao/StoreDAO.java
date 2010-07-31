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
	 * ȡ���вֿ��б�
	 * @return
	 */
	public List getAllStoreList(){
		String sql = "select * from storehouse where id not like 'WX%'";
		
		return this.getJdbcTemplate().query(sql, new StoreHouseRowMapper());
	}
	
	
	/**
	 * ���ݲֿ�IDȡ�ֿ�����
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
	 * ���ط�ҳ��Ŀⷿ�б�
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
	 * ����ֿ���Ϣ
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
	 * ���¿ⷿ��Ϣ
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
	 * ����IDȡ�ֿ���Ϣ
	 * @param id
	 * @return
	 */
	public Object getStore(String id){
		String sql = "select * from storehouse where id='" + id + "'";
		
		return this.queryForObject(sql, new StoreHouseRowMapper());
	}
	
	
	
	/**
	 * ɾ���ֿ���Ϣ
	 * @param id
	 */
	public void delStore(String id){
		String sql = "delete from storehouse where id='" + id + "'";
		
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * �жϿⷿ�����Ƿ����ɾ��<BR>
	 * �������ҵ���ϵ������ɾ��<BR>
	 * ҵ���ϵ��������ⵥ�����ⵥ���������롢�ⷿ���������ڿ��ֵ<BR>
	 * @param id boolean true:���ԣ�false:������
	 */
	public boolean isCanDel(String id){
		
		//�ж��Ƿ������ⵥ
		String sql = "select count(*) as counts from ckd where store_id='" + id + "'";
		if(this.getJdbcTemplate().queryForInt(sql) > 0){
			return false;
		}
		
		//�ж��Ƿ���ڳ��ⵥ
		sql = "select count(*) as counts from rkd where store_id='" + id + "'";
		if(this.getJdbcTemplate().queryForInt(sql) > 0){
			return false;
		}
		
		//�ж��Ƿ���ڵ�������
		sql = "select count(*) as counts from dbsq where store_id='" + id + "'";
		if(this.getJdbcTemplate().queryForInt(sql) > 0){
			return false;
		}
		
		//�ж��Ƿ���ڿⷿ����
		sql = "select count(*) as counts from kfdb where ck_store_id='" + id + "' or rk_store_id='" + id + "'";
		if(this.getJdbcTemplate().queryForInt(sql) > 0){
			return false;
		}	
		
		//�ж��Ƿ���ڿ��ֵ
		sql = "select count(*) as counts from product_kc where store_id='" + id + "'";
		if(this.getJdbcTemplate().queryForInt(sql) > 0){
			return false;
		}		
		
		return true;
	}
	
	
	/**
	 * ȡ��ǰ���õ����к�
	 * @return
	 */
	private String getKfID() {
		String sql = "select kfid from cms_all_seq";

		// ȡ��ǰ���к�
		String curId = this.getJdbcTemplate().queryForInt(sql) + "";

		// �����кż�1
		sql = "update cms_all_seq set kfid=kfid+1";
		this.getJdbcTemplate().update(sql);

		for (int i = curId.length(); i < 8; i++) {
			curId = "0" + curId;
		}

		return "KF" + curId;
	}
	
	
	
	/**
	 * ��װ����(�ֿ����)
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
