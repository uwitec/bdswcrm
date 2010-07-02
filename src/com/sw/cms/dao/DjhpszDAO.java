package com.sw.cms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

import com.sw.cms.dao.base.BeanRowMapper;
import com.sw.cms.dao.base.JdbcBaseDAO;
import com.sw.cms.dao.base.SqlUtil;
import com.sw.cms.model.Djhpsz;
import com.sw.cms.model.Lsd;
import com.sw.cms.model.LsdProduct;
import com.sw.cms.model.Page;
import com.sw.cms.util.DateComFunc;
import com.sw.cms.util.StringUtils;

/**
 * 兑奖货品设置
 * @author zuohj
 *
 */

public class DjhpszDAO extends JdbcBaseDAO {
	
	
	/**
	 * 兑奖货品设置列表(带分页）
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getDjhpszList(String con,int curPage, int rowsPerPage){
		String sql = "select * from djhpsz where 1=1";
		
		if(!con.equals("")){
			sql = sql + con;
		}
		
		return this.getResultByPage(sql, curPage, rowsPerPage);
	}
	
	/**
	 * 根据编号获取兑奖货品设置信息
	 * @param product_id
	 * @return
	 */
	public Object getDjhpsz(String product_id){
		String sql = "select * from djhpsz where product_id='" + product_id + "'";
		return this.queryForObject(sql, new DjhpszRowMapper());
	}
	
		
	
	
	/**
	 * 删除兑奖货品设置信息
	 * @param product_id
	 */
	public void delDjhpsz(String product_id){
		
		String sql = "delete from djhpsz where product_id='" + product_id + "'";		
		this.getJdbcTemplate().update(sql);
	}
	
		
	/**
	 * 保存兑奖货品设置信息
	 * @param djhpszProducts
	 * @return
	 */
	public void saveDjhpsz(List djhpszProducts,String czr)
	{		
     	if(djhpszProducts != null && djhpszProducts.size()>0){
			for(int i=0;i<djhpszProducts.size();i++){
				Djhpsz djhpsz = (Djhpsz)djhpszProducts.get(i);
				if(!djhpsz.getProduct_id().equals("")){
		        String sql = "insert into djhpsz(product_id,product_name,product_xh,dwjf,czr,cz_date) values(?,?,?,?,?,now())";
		        Object[] param = new Object[5];
		        param[0] = djhpsz.getProduct_id();
				param[1] = djhpsz.getProduct_name();
				param[2] = djhpsz.getProduct_xh();
				param[3] = djhpsz.getDwjf();
				param[4] = czr;		
				this.getJdbcTemplate().update(sql,param);
				}
			}
     	}
	}
	
	
	/**
	 * 更新兑奖货品设置信息
	 * @param djhpsz
	 * 
	 */
	public void updateDjhpsz(Djhpsz djhpsz){
		String sql = "update djhpsz set cz_date=now(),czr=?,product_name=?,product_xh=?,dwjf=? where product_id=?";		
		Object[] param = new Object[5];
		
		param[0] = djhpsz.getCzr();
		param[1] = djhpsz.getProduct_name();
		param[2] = djhpsz.getProduct_xh();
		param[3] = djhpsz.getDwjf();
		param[4] = djhpsz.getProduct_id();		
		this.getJdbcTemplate().update(sql,param);
		
	} 
	
	
	/**
	 * 包装对象(兑奖货品设置)
	 * 
	 * @author zuohj
	 * 
	 */
	class DjhpszRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			Djhpsz djhpsz = new Djhpsz();

			if(SqlUtil.columnIsExist(rs,"product_id")) djhpsz.setProduct_id(rs.getString("product_id"));
			if(SqlUtil.columnIsExist(rs,"cz_date")) djhpsz.setCz_date(rs.getString("cz_date"));
			if(SqlUtil.columnIsExist(rs,"czr")) djhpsz.setCzr(rs.getString("czr"));	
			if(SqlUtil.columnIsExist(rs,"dwjf")) djhpsz.setDwjf(rs.getString("dwjf"));
			if(SqlUtil.columnIsExist(rs,"product_name")) djhpsz.setProduct_name(rs.getString("product_name"));
			if(SqlUtil.columnIsExist(rs,"product_xh")) djhpsz.setProduct_xh(rs.getString("product_xh"));
			return djhpsz;
		}
	}
	
}
