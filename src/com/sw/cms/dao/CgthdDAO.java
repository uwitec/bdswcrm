package com.sw.cms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.sw.cms.dao.base.JdbcBaseDAO;
import com.sw.cms.dao.base.SqlUtil;
import com.sw.cms.model.Cgthd;
import com.sw.cms.model.CgthdProduct;
import com.sw.cms.model.Page;
import com.sw.cms.util.DateComFunc;

public class CgthdDAO extends JdbcBaseDAO {
	
	/**
	 * 取采购退货单列表
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getCgthdList(String con,int curPage, int rowsPerPage){
		String sql = "select a.th_date,b.name as provider_name,a.jsr,a.tkfs,a.state,a.remark,a.zhmc,a.tkzje,a.czr,a.cz_date,a.id,a.jhd_id,a.th_flag from cgthd a left join clients b on b.id=a.provider_name where 1=1";
		if(!con.equals("")){
			sql = sql + con;
		}
		
		return this.getResultByPage(sql, curPage, rowsPerPage);
	}
	
	
	/**
	 * 保存采购退货单相关信息
	 * @param cgthd
	 * @param cgthdProducts
	 */
	public void saveCgthd(Cgthd cgthd,List cgthdProducts){
		String sql = "insert into cgthd(th_date,provider_name,jsr,tkfs,state,remark,zhmc,tkzje,czr,cz_date,jhd_id,id,store_id) values(?,?,?,?,?,?,?,?,?,now(),?,?,?)";
		
		Object[] params = new Object[12];
		
		params[0] = cgthd.getTh_date();
		params[1] = cgthd.getProvider_name();
		params[2] = cgthd.getJsr();
		params[3] = cgthd.getTkfs();
		params[4] = cgthd.getState();
		params[5] = cgthd.getRemark();
		params[6] = cgthd.getZhmc();
		params[7] = new Double(cgthd.getTkzje());
		params[8] = cgthd.getCzr();
		params[9] = cgthd.getJhd_id();
		params[10] = cgthd.getId();
		params[11] = cgthd.getStore_id();
		
		this.getJdbcTemplate().update(sql,params);
		this.addCgthdProducts(cgthdProducts, cgthd.getId());
	}
	
	
	/**
	 * 更新采购退货单相关信息
	 * @param cgthd
	 * @param cgthdProducts
	 */
	public void updateCgthd(Cgthd cgthd,List cgthdProducts){
		String sql = "update cgthd set th_date=?,provider_name=?,jsr=?,tkfs=?,state=?,remark=?,zhmc=?,tkzje=?,czr=?,cz_date=now(),jhd_id=?,store_id=? where id=?";
		
		Object[] params = new Object[12];
		
		params[0] = cgthd.getTh_date();
		params[1] = cgthd.getProvider_name();
		params[2] = cgthd.getJsr();
		params[3] = cgthd.getTkfs();
		params[4] = cgthd.getState();
		params[5] = cgthd.getRemark();
		params[6] = cgthd.getZhmc();
		params[7] = new Double(cgthd.getTkzje());
		params[8] = cgthd.getCzr();
		params[9] = cgthd.getJhd_id();
		params[10] = cgthd.getStore_id();
		params[11] = cgthd.getId();
		
		this.getJdbcTemplate().update(sql,params);
		
		if(!cgthd.getState().equals("已保存")){
			sql = "update cgthd set th_flag='0' where id='" + cgthd.getId() + "'";
			this.getJdbcTemplate().update(sql);
		}
		
		this.delCgthdProducts(cgthd.getId());
		this.addCgthdProducts(cgthdProducts, cgthd.getId());
	}
	
	
	/**
	 * 取采购退货单
	 * @param id
	 * @return
	 */
	public Object getCgthd(String id){
		String sql = "select * from cgthd where id='" + id + "'";
		return this.queryForObject(sql, new CgthdRowMapper());
	}
	
	
	/**
	 * 取采购退货单产品明细
	 * @param id
	 * @return
	 */
	public List getCgthdProducts(String id){
		String sql = "select a.*,b.qz_serial_num as qz_flag from cgthd_product a left join product b on b.product_id=a.product_id where a.cgthd_id='" + id + "'";
		return this.getJdbcTemplate().query(sql, new CgthdProductRowMapper());
	}
	
	
	/**
	 * 出库单退回后采购退货单的处理
	 * @param cgthd_id
	 * @param state
	 * @param th_flag
	 */
	public void updateCgthdAfterCkdTh(String cgthd_id,String state,String th_flag){
		String sql = "update cgthd set state='" + state + "', th_flag='" + th_flag + "' where id='" + cgthd_id + "'";
		this.getJdbcTemplate().update(sql);
	}
	
	/**
	 * 更新采购退货单状态
	 * @param id
	 * @param state
	 */
	public void updateCgthdState(String id,String state){
		String sql = "update cgthd set state='" + state + "',cz_date=now() where id='" + id + "'";
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * 删除采购退货单
	 * @param id
	 */
	public void delCgthd(String id){
		String sql = "delete from cgthd where id='" + id + "'";
		this.getJdbcTemplate().update(sql);
		
		sql = "delete from cgthd_product where cgthd_id='" + id + "'";
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * 取当前可用的序列号
	 * @return
	 */
	public String getCgthdID() {
		String sql = "select cgthdid from cms_all_seq";

		String day = DateComFunc.getCurDay();
		
		// 取当前序列号
		String curId = this.getJdbcTemplate().queryForInt(sql) + "";

		// 将序列号加1
		sql = "update cms_all_seq set cgthdid=cgthdid+1";
		this.getJdbcTemplate().update(sql);

		for (int i = curId.length(); i < 3; i++) {
			curId = "0" + curId;
		}

		return "CGTH" + day + "-" + curId;
	}
	
	
	/**
	 * 判断采购退货单是否已经提交
	 * @param id
	 * @return
	 */
	public boolean isCgthdSubmit(String id){
		boolean is = false;
		
		String sql = "select count(*) as counts from cgthd where id='" + id + "' and state<>'已保存'";
		int counts = this.getJdbcTemplate().queryForInt(sql);
		
		if(counts > 0){
			is = true;
		}
		
		return is;
	}
	
	
	/**
	 * 添加采购退货单相关联产品
	 * @param thdProducts
	 * @param thd_id
	 */
	private void addCgthdProducts(List cgthdProducts,String cgthd_id){
		String sql = "";
		Object[] param = new Object[9];
		
		if(cgthdProducts != null && cgthdProducts.size()>0){
			for(int i =0;i<cgthdProducts.size();i++){
				CgthdProduct cgthdProduct = (CgthdProduct)cgthdProducts.get(i);
				
				if(cgthdProduct != null){
					if(!(cgthdProduct.getProduct_id()).equals("")){
						sql = "insert into cgthd_product(cgthd_id,product_id,product_xh,product_name,th_price,nums,remark,xj,qz_serial_num) values(?,?,?,?,?,?,?,?,?)";
						
						param[0] = cgthd_id;
						param[1] = cgthdProduct.getProduct_id();
						param[2] = cgthdProduct.getProduct_xh();
						param[3] = cgthdProduct.getProduct_name();
						param[4] = new Double(cgthdProduct.getTh_price());
						param[5] = new Integer(cgthdProduct.getNums());
						param[6] = cgthdProduct.getRemark();
						param[7] = new Double(cgthdProduct.getXj());
						param[8] = cgthdProduct.getQz_serial_num();
						
						this.getJdbcTemplate().update(sql,param);
					}
				}
			}
		}
	}
	
	
	/**
	 * 删采购除退货单关联产品
	 * @param thd_id
	 */
	private void delCgthdProducts(String id){
		String sql = "delete from cgthd_product where cgthd_id='" + id + "'";
		this.getJdbcTemplate().update(sql);
	}
	
	
	
	/**
	 * 包装对象(采购退货单)
	 * 
	 * @author liyt
	 * 
	 */
	class CgthdRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			Cgthd cgthd = new Cgthd();

			if(SqlUtil.columnIsExist(rs,"id")) cgthd.setId(rs.getString("id"));
			if(SqlUtil.columnIsExist(rs,"th_date")) cgthd.setTh_date(rs.getString("th_date"));
			if(SqlUtil.columnIsExist(rs,"provider_name")) cgthd.setProvider_name(rs.getString("provider_name"));
			if(SqlUtil.columnIsExist(rs,"jsr")) cgthd.setJsr(rs.getString("jsr"));
			if(SqlUtil.columnIsExist(rs,"tkfs")) cgthd.setTkfs(rs.getString("tkfs"));
			if(SqlUtil.columnIsExist(rs,"state")) cgthd.setState(rs.getString("state"));
			if(SqlUtil.columnIsExist(rs,"zhmc")) cgthd.setZhmc(rs.getString("zhmc"));
			if(SqlUtil.columnIsExist(rs,"tkzje")) cgthd.setTkzje(rs.getDouble("tkzje"));
			if(SqlUtil.columnIsExist(rs,"remark")) cgthd.setRemark(rs.getString("remark"));
			if(SqlUtil.columnIsExist(rs,"czr")) cgthd.setCzr(rs.getString("czr"));
			if(SqlUtil.columnIsExist(rs,"cz_date")) cgthd.setCz_date(rs.getString("cz_date"));
			if(SqlUtil.columnIsExist(rs,"jhd_id")) cgthd.setJhd_id(rs.getString("jhd_id"));
			if(SqlUtil.columnIsExist(rs,"store_id")) cgthd.setStore_id(rs.getString("store_id"));
			if(SqlUtil.columnIsExist(rs,"th_flag")) cgthd.setTh_flag(rs.getString("th_flag"));
			if(SqlUtil.columnIsExist(rs,"cz_date")){
				cgthd.setCz_date(rs.getTimestamp("cz_date").toString());
			}
			
			return cgthd;
		}
	}
	
	
	
	/**
	 * 包装对象(采购退货单产品)
	 * 
	 * @author liyt
	 * 
	 */
	class CgthdProductRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			CgthdProduct cgthdProduct = new CgthdProduct();

			if(SqlUtil.columnIsExist(rs,"id")) cgthdProduct.setId(rs.getInt("id"));
			if(SqlUtil.columnIsExist(rs,"cgthd_id")) cgthdProduct.setCgthd_id(rs.getString("cgthd_id"));
			if(SqlUtil.columnIsExist(rs,"product_id")) cgthdProduct.setProduct_id(rs.getString("product_id"));
			if(SqlUtil.columnIsExist(rs,"product_xh")) cgthdProduct.setProduct_xh(rs.getString("product_xh"));
			if(SqlUtil.columnIsExist(rs,"product_name")) cgthdProduct.setProduct_name(rs.getString("product_name"));
			if(SqlUtil.columnIsExist(rs,"th_price")) cgthdProduct.setTh_price(rs.getDouble("th_price"));
			if(SqlUtil.columnIsExist(rs,"nums")) cgthdProduct.setNums(rs.getInt("nums"));
			if(SqlUtil.columnIsExist(rs,"remark")) cgthdProduct.setRemark(rs.getString("remark"));
			if(SqlUtil.columnIsExist(rs,"xj")) cgthdProduct.setXj(rs.getDouble("xj"));
			if(SqlUtil.columnIsExist(rs,"qz_serial_num")) cgthdProduct.setQz_serial_num(rs.getString("qz_serial_num"));
			if(SqlUtil.columnIsExist(rs,"qz_flag")) cgthdProduct.setQz_flag(rs.getString("qz_flag"));
			
			return cgthdProduct;
		}
	}	

}
