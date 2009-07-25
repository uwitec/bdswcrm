package com.sw.cms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.sw.cms.dao.base.JdbcBaseDAO;
import com.sw.cms.model.Dbsq;
import com.sw.cms.model.DbsqProduct;
import com.sw.cms.model.Page;
import com.sw.cms.util.DateComFunc;

public class DbsqDAO extends JdbcBaseDAO {
	
	
	/**
	 * ���ݲ�ѯ����ȡ���������б�
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getDbsqList(String con,int curPage, int rowsPerPage){
		String sql = "select * from dbsq where 1=1";
		
		if(!con.equals("")){
			sql = sql + con;
		}
		
		return this.getResultByPage(sql, curPage, rowsPerPage, new DbsqRowMapper());
	}
	
	
	/**
	 * ����������뵥�����Ϣ
	 * @param dbsq
	 * @param dbsqProducts
	 */
	public void saveDbsq(Dbsq dbsq,List dbsqProducts){
		String sql = "insert into dbsq(creatdate,jsr,store_id,state,remark,czr,cz_date,id) value(?,?,?,?,?,?,now(),?)";
		
		Object[] param = new Object[7];
		param[0] = dbsq.getCreatdate();
		param[1] = dbsq.getJsr();
		param[2] = dbsq.getStore_id();
		param[3] = dbsq.getState();
		param[4] = dbsq.getRemark();
		param[5] = dbsq.getCzr();
		param[6] = dbsq.getId();
		
		this.getJdbcTemplate().update(sql,param);
		
		this.addDbsqProducts(dbsqProducts, dbsq.getId());
	}
	
	
	/**
	 * ���µ������뵥�����Ϣ
	 * @param dbsq
	 * @param dbsqProducts
	 */
	public void updateDbsq(Dbsq dbsq,List dbsqProducts){
		String sql = "update dbsq set creatdate=?,jsr=?,store_id=?,state=?,remark=?,czr=?,cz_date=now() where id=?";
		
		Object[] param = new Object[7];
		param[0] = dbsq.getCreatdate();
		param[1] = dbsq.getJsr();
		param[2] = dbsq.getStore_id();
		param[3] = dbsq.getState();
		param[4] = dbsq.getRemark();
		param[5] = dbsq.getCzr();
		param[6] = dbsq.getId();
		
		this.getJdbcTemplate().update(sql,param);
		
		this.delDbsqProducts(dbsq.getId());
		
		this.addDbsqProducts(dbsqProducts, dbsq.getId());
	}
	
	
	/**
	 * ȡ�������뵥��Ϣ
	 * @param id
	 * @return
	 */
	public Object getDbsq(String id){
		String sql = "select * from dbsq where id='" + id + "'";
		return this.queryForObject(sql, new DbsqRowMapper());
	}
	
	
	/**
	 * ȡ�������뵥��ز�Ʒ�б�
	 * @param id
	 * @return
	 */
	public List getDbsqProducts(String id){
		String sql = "select * from dbsq_product where dbsq_id='" + id + "'";
		return this.getJdbcTemplate().query(sql, new DbsqProductRowMapper());
	}
	
	
	/**
	 * ɾ����������
	 * @param id
	 */
	public void delDbsq(String id){
		String sql = "delete from dbsq where id='" + id + "'";
		this.getJdbcTemplate().update(sql);
		
		sql = "delete from dbsq_product where dbsq_id='" + id + "'";
		this.getJdbcTemplate().update(sql);
	}
	
	
	
	/**
	 * ȡ��ǰ���õ����к�
	 * @return
	 */
	public String getDbsqID() {
		String sql = "select dbsqid from cms_all_seq";

		// ȡ��ǰ���к�
		String curId = this.getJdbcTemplate().queryForInt(sql) + "";
		
		String day = DateComFunc.getCurDay();

		// �����кż�1
		sql = "update cms_all_seq set dbsqid=dbsqid+1";
		this.getJdbcTemplate().update(sql);

		for (int i = curId.length(); i < 3; i++) {
			curId = "0" + curId;
		}

		return "SQ" + day + "-" + curId;
	}
	
	
	/**
	 * �жϵ��������Ƿ��Ѿ��ύ
	 * @param id
	 * @return
	 */
	public boolean isDbsqFinish(String id){
		boolean is = false;
		String sql = "select count(*) from dbsq where id='" + id + "' and state='���ύ'";
		int counts = this.getJdbcTemplate().queryForInt(sql);
		if(counts > 0){
			is = true;
		}
		return is;
	}
	
	
	
	/**
	 * ��ӵ��������������Ʒ
	 * @param lsdProducts
	 * @param lsd_id
	 */
	private void addDbsqProducts(List dbsqProducts,String dbsq_id){
		String sql = "";
		Object[] param = new Object[6];
		
		if(dbsqProducts != null && dbsqProducts.size()>0){
			for(int i=0;i<dbsqProducts.size();i++){
				DbsqProduct dbsqProduct = (DbsqProduct)dbsqProducts.get(i);
				if(dbsqProduct != null){
					if(!dbsqProduct.getProduct_id().equals("") && !dbsqProduct.getProduct_name().equals("")){
						sql = "insert into dbsq_product(dbsq_id,product_id,product_xh,product_name,nums,remark) values(?,?,?,?,?,?)";
						
						param[0] = dbsq_id;
						param[1] = dbsqProduct.getProduct_id();
						param[2] = dbsqProduct.getProduct_xh();
						param[3] = dbsqProduct.getProduct_name();
						param[4] = new Integer(dbsqProduct.getNums());
						param[5] = dbsqProduct.getRemark();
						
						this.getJdbcTemplate().update(sql,param);
					}
				}
			}
		}
	}
	
	
	/**
	 * ɾ���������뵥������Ʒ
	 * @param lsd_id
	 */
	private void delDbsqProducts(String dbsq_id){
		String sql = "delete from dbsq_product where dbsq_id='" + dbsq_id + "'";
		
		this.getJdbcTemplate().update(sql);
	}
	
	
	
	/**
	 * ��װ����(��������)
	 * 
	 * @author liyt
	 * 
	 */
	class DbsqRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			Dbsq dbsq = new Dbsq();

			dbsq.setId(rs.getString("id"));
			dbsq.setCreatdate(rs.getString("creatdate"));
			dbsq.setStore_id(rs.getString("store_id"));
			dbsq.setCzr(rs.getString("czr"));
			dbsq.setJsr(rs.getString("jsr"));
			dbsq.setState(rs.getString("state"));
			dbsq.setRemark(rs.getString("remark"));
			
			return dbsq;
		}
	}	
	
	
	/**
	 * ��װ����(��������)
	 * 
	 * @author liyt
	 * 
	 */
	class DbsqProductRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			DbsqProduct dbsqProduct = new DbsqProduct();

			dbsqProduct.setDbsq_id(rs.getString("dbsq_id"));
			dbsqProduct.setNums(rs.getInt("nums"));
			dbsqProduct.setProduct_id(rs.getString("product_id"));
			dbsqProduct.setProduct_name(rs.getString("product_name"));
			dbsqProduct.setProduct_xh(rs.getString("product_xh"));
			dbsqProduct.setRemark(rs.getString("remark"));
			
			return dbsqProduct;
		}
	}	

}
