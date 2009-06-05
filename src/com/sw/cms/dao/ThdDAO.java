package com.sw.cms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.sw.cms.dao.base.JdbcBaseDAO;
import com.sw.cms.dao.base.SqlUtil;
import com.sw.cms.model.Page;
import com.sw.cms.model.Thd;
import com.sw.cms.model.ThdProduct;
import com.sw.cms.util.DateComFunc;

public class ThdDAO extends JdbcBaseDAO {
	
	/**
	 * 查询退货单列表，带分页
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getThdList(String con,int curPage, int rowsPerPage){		
		String sql = "select * from thd where 1=1";
		if(!con.equals("")){
			sql = sql + con;
		}
		
		return this.getResultByPage(sql, curPage, rowsPerPage);		
	}
	
	
	/**
	 * 保存退货单信息
	 * @param thd
	 * @param thdProducts
	 */
	public void saveThd(Thd thd,List thdProducts){
		
		String sql = "insert into thd(client_name,thdje,th_fzr,remark,th_date,state,tkzh,czr,cz_date,type,xsd_id,thd_id,store_id) values(?,?,?,?,?,?,?,?,now(),?,?,?,?)";
		
		Object[] param = new Object[12];
		
		param[0] = thd.getClient_name();
		param[1] = new Double(thd.getThdje());
		param[2] = thd.getTh_fzr();
		param[3] = thd.getRemark();
		param[4] = thd.getTh_date();
		param[5] = thd.getState();
		param[6] = thd.getTkzh();
		param[7] = thd.getCzr();
		param[8] = thd.getType();
		param[9] = thd.getXsd_id();
		param[10] = thd.getThd_id();
		param[11] = thd.getStore_id();
		
		this.getJdbcTemplate().update(sql,param);
		
		this.addThdProducts(thdProducts, thd.getThd_id());

	}
	
	
	/**
	 * 更新退货单信息
	 * @param thd
	 * @param thdProducts
	 */
	public void updateThd(Thd thd,List thdProducts){
		
		String sql = "update thd set client_name=?,thdje=?,th_fzr=?,remark=?,th_date=?,state=?,tkzh=?,czr=?,cz_date=now(),type=?,xsd_id=?,store_id=? where thd_id=?";
		
		Object[] param = new Object[12];
		
		param[0] = thd.getClient_name();
		param[1] = new Double(thd.getThdje());
		param[2] = thd.getTh_fzr();
		param[3] = thd.getRemark();
		param[4] = thd.getTh_date();
		param[5] = thd.getState();
		param[6] = thd.getTkzh();
		param[7] = thd.getCzr();
		param[8] = thd.getType();
		param[9] = thd.getXsd_id();
		param[10] = thd.getStore_id();
		param[11] = thd.getThd_id();
		
		this.getJdbcTemplate().update(sql,param);
		
		//修改退货标志
		if(!thd.getState().equals("已保存")){
			sql = "update thd set th_flag='0' where thd_id='" + thd.getThd_id() + "'";
			this.getJdbcTemplate().update(sql);
		}
		
		this.delThdProducts(thd.getThd_id());
		
		this.addThdProducts(thdProducts, thd.getThd_id());
		
	}
	
	
	/**
	 * 根据退货单ID返回产品明细
	 * @param thd_id
	 * @return
	 */
	public List getThdProducts(String thd_id){
		String sql = "select a.*,b.qz_serial_num as qz_flag from thd_product a left join product b on b.product_id=a.product_id where a.thd_id='" + thd_id + "'";
		return this.getJdbcTemplate().query(sql, new ThdProductRowMapper());
	}
	
	
	/**
	 * 根据退货单ID返回退货单相关信息
	 */
	public Object getThd(String thd_id){
		String sql = "select * from thd where thd_id='" + thd_id + "'";
		return this.queryForObject(sql, new ThdRowMapper());
	}
	
	
	
	/**
	 * 删除退货单相关信息
	 * @param thd_id
	 */
	public void delThd(String thd_id){
		String sql = "delete from thd where thd_id='" + thd_id + "'";
		this.getJdbcTemplate().update(sql);
		
		sql = "delete from thd_product where thd_id='" + thd_id + "'";
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * 入库单退回后修改销售退货单相关信息
	 * @param id
	 * @param state
	 * @param th_flag
	 */
	public void updateThdAfterRkdTh(String id,String state,String th_flag){
		String sql = "update thd set state='" + state + "',th_flag='" + th_flag + "' where thd_id='" + id + "'";
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * 修改退货单状态
	 * @param thd_id
	 * @param state
	 */
	public void updateThdState(String thd_id,String state){
		String sql = "update thd set state='" + state + "' where thd_id='" + thd_id + "'";
		this.getJdbcTemplate().update(sql);
	}
	
	
	
	/**
	 * 添加退货单相关联产品
	 * @param thdProducts
	 * @param thd_id
	 */
	private void addThdProducts(List thdProducts,String thd_id){
		String sql = "";
		Object[] param = new Object[11];
		
		if(thdProducts != null && thdProducts.size()>0){
			for(int i =0;i<thdProducts.size();i++){
				ThdProduct thdProduct = (ThdProduct)thdProducts.get(i);
				
				if(thdProduct != null){
					if(!(thdProduct.getProduct_id()).equals("")){
						sql = "insert into thd_product(thd_id,product_id,product_xh,product_name,th_price,nums,remark,xj,cbj,qz_serial_num,kh_cbj) values(?,?,?,?,?,?,?,?,?,?,?)";
						
						param[0] = thd_id;
						param[1] = thdProduct.getProduct_id();
						param[2] = thdProduct.getProduct_xh();
						param[3] = thdProduct.getProduct_name();
						param[4] = thdProduct.getTh_price();
						param[5] = thdProduct.getNums();
						param[6] = thdProduct.getRemark();
						param[7] = thdProduct.getXj();
						param[8] = thdProduct.getCbj();
						param[9] = thdProduct.getQz_serial_num();
						param[10] = thdProduct.getKh_cbj();
						
						this.getJdbcTemplate().update(sql,param);
					}
				}
			}
		}
	}
	
	
	
	/**
	 * 删除退货单关联产品
	 * @param thd_id
	 */
	private void delThdProducts(String thd_id){
		String sql = "delete from thd_product where thd_id='" + thd_id + "'";
		this.getJdbcTemplate().update(sql);
	}

	
	
	/**
	 * 取当前可用的序列号
	 * @return
	 */
	public String getThdID() {
		String sql = "select thdid from cms_all_seq";

		String day = DateComFunc.getCurDay();
		
		// 取当前序列号
		String curId = this.getJdbcTemplate().queryForInt(sql) + "";

		// 将序列号加1
		sql = "update cms_all_seq set thdid=thdid+1";
		this.getJdbcTemplate().update(sql);

		for (int i = curId.length(); i < 3; i++) {
			curId = "0" + curId;
		}

		return "TH" + day + "-" + curId;
	}
	
	
	/**
	 * 包装对象(退货单)
	 * 
	 * @author liyt
	 * 
	 */
	class ThdRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			Thd thd = new Thd();

			if(SqlUtil.columnIsExist(rs,"thd_id")) thd.setThd_id(rs.getString("thd_id"));
			if(SqlUtil.columnIsExist(rs,"client_name")) thd.setClient_name(rs.getString("client_name"));
			if(SqlUtil.columnIsExist(rs,"thdje")) thd.setThdje(rs.getDouble("thdje"));
			if(SqlUtil.columnIsExist(rs,"th_fzr")) thd.setTh_fzr(rs.getString("th_fzr"));
			if(SqlUtil.columnIsExist(rs,"remark")) thd.setRemark(rs.getString("remark"));
			if(SqlUtil.columnIsExist(rs,"th_date")) thd.setTh_date(rs.getString("th_date"));
			if(SqlUtil.columnIsExist(rs,"state")) thd.setState(rs.getString("state"));
			if(SqlUtil.columnIsExist(rs,"tkzh")) thd.setTkzh(rs.getString("tkzh"));
			if(SqlUtil.columnIsExist(rs,"type")) thd.setType(rs.getString("type"));
			if(SqlUtil.columnIsExist(rs,"xsd_id")) thd.setXsd_id(rs.getString("xsd_id"));
			if(SqlUtil.columnIsExist(rs,"store_id")) thd.setStore_id(rs.getString("store_id"));
			if(SqlUtil.columnIsExist(rs,"th_flag")) thd.setTh_flag(rs.getString("th_flag"));
			
			return thd;
		}
	}
	
	
	
	/**
	 * 包装对象(退货单产品)
	 * 
	 * @author liyt
	 * 
	 */
	class ThdProductRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			ThdProduct thdProduct = new ThdProduct();

			if(SqlUtil.columnIsExist(rs,"id")) thdProduct.setId(rs.getInt("id"));
			if(SqlUtil.columnIsExist(rs,"thd_id")) thdProduct.setThd_id(rs.getString("thd_id"));
			if(SqlUtil.columnIsExist(rs,"product_id")) thdProduct.setProduct_id(rs.getString("product_id"));
			if(SqlUtil.columnIsExist(rs,"product_xh")) thdProduct.setProduct_xh(rs.getString("product_xh"));
			if(SqlUtil.columnIsExist(rs,"product_name")) thdProduct.setProduct_name(rs.getString("product_name"));
			if(SqlUtil.columnIsExist(rs,"th_price")) thdProduct.setTh_price(rs.getDouble("th_price"));
			if(SqlUtil.columnIsExist(rs,"nums")) thdProduct.setNums(rs.getInt("nums"));
			if(SqlUtil.columnIsExist(rs,"remark")) thdProduct.setRemark(rs.getString("remark"));
			if(SqlUtil.columnIsExist(rs,"xj")) thdProduct.setXj(rs.getDouble("xj"));
			if(SqlUtil.columnIsExist(rs,"qz_flag")) thdProduct.setQz_flag(rs.getString("qz_flag"));
			if(SqlUtil.columnIsExist(rs,"qz_serial_num")) thdProduct.setQz_serial_num(rs.getString("qz_serial_num"));
			if(SqlUtil.columnIsExist(rs,"cbj")) thdProduct.setCbj(rs.getDouble("cbj"));
			if(SqlUtil.columnIsExist(rs,"kh_cbj")) thdProduct.setKh_cbj(rs.getDouble("kh_cbj"));
			
			return thdProduct;
		}
	}	
}
