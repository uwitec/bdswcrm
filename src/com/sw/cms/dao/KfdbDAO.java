package com.sw.cms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.sw.cms.dao.base.JdbcBaseDAO;
import com.sw.cms.model.Kfdb;
import com.sw.cms.model.KfdbProduct;
import com.sw.cms.model.Page;
import com.sw.cms.util.DateComFunc;

public class KfdbDAO extends JdbcBaseDAO {
	
	/**
	 * 根据查询条件取库房调拨列表信息
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getKfdbList(String con,int curPage, int rowsPerPage){
		String sql = "select * from kfdb where 1=1";
		
		if(!con.equals("")){
			sql = sql + con;
		}
		
		return this.getResultByPage(sql, curPage, rowsPerPage, new KfdbRowMapper());
	}
	
	
	/**
	 * 保存库房调拨相关信息
	 * @param kfdb
	 * @param kfdbProducts
	 */
	public void saveKfdb(Kfdb kfdb,List kfdbProducts){
		String sql = "insert into kfdb(ck_date,jsr,ck_store_id,dbsq_id,rk_store_id,sqr,czr,state,remark,cz_date,id) values(?,?,?,?,?,?,?,?,?,now(),?)";
		
		Object[] param = new Object[10];
		
		param[0] = kfdb.getCk_date();
		param[1] = kfdb.getJsr();
		param[2] = kfdb.getCk_store_id();
		param[3] = kfdb.getDbsq_id();
		param[4] = kfdb.getRk_store_id();
		param[5] = kfdb.getSqr();
		param[6] = kfdb.getCzr();
		param[7] = kfdb.getState();
		param[8] = kfdb.getRemark();
		param[9] = kfdb.getId();
		
		this.getJdbcTemplate().update(sql,param);
		
		this.addKfdbProducts(kfdbProducts, kfdb.getId());
	}
	
	
	/**
	 * 更新库房调拨相关信息
	 * @param kfdb
	 * @param kfdbProducts
	 */
	public void updateKfdb(Kfdb kfdb,List kfdbProducts){
		String sql = "update kfdb set ck_date=?,jsr=?,ck_store_id=?,dbsq_id=?,rk_store_id=?,sqr=?,czr=?,state=?,remark=?,cz_date=now() where id=?";
		
		Object[] param = new Object[10];
		
		param[0] = kfdb.getCk_date();
		param[1] = kfdb.getJsr();
		param[2] = kfdb.getCk_store_id();
		param[3] = kfdb.getDbsq_id();
		param[4] = kfdb.getRk_store_id();
		param[5] = kfdb.getSqr();
		param[6] = kfdb.getCzr();
		param[7] = kfdb.getState();
		param[8] = kfdb.getRemark();
		param[9] = kfdb.getId();
		
		this.getJdbcTemplate().update(sql,param);
		
		this.delKfdbProducts(kfdb.getId());
		
		this.addKfdbProducts(kfdbProducts, kfdb.getId());
	}
	
	
	/**
	 * 取库房调拨信息
	 * @param id
	 * @return
	 */
	public Object getKfdb(String id){
		String sql = "select * from kfdb where id='" + id + "'";
		return this.getJdbcTemplate().queryForObject(sql, new KfdbRowMapper());
	}
	
	
	/**
	 * 取库房调拨相关产品明细
	 * @param id
	 * @return
	 */
	public List getKfdbProducts(String id){
		String sql = "select a.*,b.qz_serial_num as qz_flag from kfdb_product a left join product b on b.product_id=a.product_id where a.kfdb_id='" + id + "'";
		return this.getResultList(sql);
	}
	
	
	/**
	 * 删除库房调拨相关信息
	 * @param id
	 */
	public void delKfdb(String id){
		String sql = "delete from kfdb where id='" + id + "'";
		this.getJdbcTemplate().update(sql);
		
		sql = "delete from kfdb_product where kfdb_id='" + id + "'";
		this.getJdbcTemplate().update(sql);
	}
	
	

	/**
	 * 取当前可用的序列号
	 * @return
	 */
	public String getKfdbID() {
		String sql = "select kfdbid from cms_all_seq";

		// 取当前序列号
		String curId = this.getJdbcTemplate().queryForInt(sql) + "";
		
		String day = DateComFunc.getCurDay();

		// 将序列号加1
		sql = "update cms_all_seq set kfdbid=kfdbid+1";
		this.getJdbcTemplate().update(sql);

		for (int i = curId.length(); i < 3; i++) {
			curId = "0" + curId;
		}

		return "DB" + day + "-" + curId;
	}
	
	
	
	/**
	 * 添加库房调拨相关联产品
	 * @param lsdProducts
	 * @param lsd_id
	 */
	private void addKfdbProducts(List KfdbProducts,String kfdb_id){
		String sql = "";
		Object[] param = new Object[7];
		
		if(KfdbProducts != null && KfdbProducts.size()>0){
			for(int i=0;i<KfdbProducts.size();i++){
				KfdbProduct kfdbProduct = (KfdbProduct)KfdbProducts.get(i);
				if(kfdbProduct != null){
					if(!kfdbProduct.getProduct_id().equals("") && !kfdbProduct.getProduct_name().equals("")){
						sql = "insert into kfdb_product(kfdb_id,product_id,product_xh,product_name,nums,remark,qz_serial_num) values(?,?,?,?,?,?,?)";
						
						param[0] = kfdb_id;
						param[1] = kfdbProduct.getProduct_id();
						param[2] = kfdbProduct.getProduct_xh();
						param[3] = kfdbProduct.getProduct_name();
						param[4] = new Integer(kfdbProduct.getNums());
						param[5] = kfdbProduct.getRemark();
						param[6] = kfdbProduct.getQz_serial_num();
						
						this.getJdbcTemplate().update(sql,param);
					}
				}
			}
		}
	}
	
	
	/**
	 * 删除库房调拨关联产品
	 * @param lsd_id
	 */
	private void delKfdbProducts(String kfdb_id){
		String sql = "delete from kfdb_product where kfdb_id='" + kfdb_id + "'";
		
		this.getJdbcTemplate().update(sql);
	}
	
	
	
	/**
	 * 包装对象(库房调拨)
	 * 
	 * @author liyt
	 * 
	 */
	class KfdbRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			Kfdb kfdb = new Kfdb();

			kfdb.setId(rs.getString("id"));
			kfdb.setCk_date(rs.getString("ck_date"));
			kfdb.setJsr(rs.getString("jsr"));
			kfdb.setCk_store_id(rs.getString("ck_store_id"));
			kfdb.setDbsq_id(rs.getString("dbsq_id"));
			kfdb.setRk_store_id(rs.getString("rk_store_id"));
			kfdb.setSqr(rs.getString("sqr"));
			kfdb.setCzr(rs.getString("czr"));
			kfdb.setState(rs.getString("state"));
			kfdb.setRemark(rs.getString("remark"));
			
			return kfdb;
		}
	}	
	
	
	/**
	 * 包装对象(库房调拨产品)
	 * 
	 * @author liyt
	 * 
	 */
	class KfdbProductRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			KfdbProduct kfdbProduct = new KfdbProduct();

			kfdbProduct.setKfdb_id(rs.getString("kfdb_id"));
			kfdbProduct.setProduct_id(rs.getString("product_id"));
			kfdbProduct.setProduct_name(rs.getString("product_name"));
			kfdbProduct.setProduct_xh(rs.getString("product_xh"));
			kfdbProduct.setNums(rs.getInt("nums"));
			
			return kfdbProduct;
		}
	}	

}
