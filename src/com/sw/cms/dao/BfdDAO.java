package com.sw.cms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

import com.sw.cms.dao.LsdDAO.LsdProductRowMapper;
import com.sw.cms.dao.base.JdbcBaseDAO;
import com.sw.cms.dao.base.SqlUtil;
import com.sw.cms.model.Bfd;
import com.sw.cms.model.BfdProduct;

import com.sw.cms.model.Page;
import com.sw.cms.util.DateComFunc;

public class BfdDAO extends JdbcBaseDAO {
	/**
	 * 返回报废单列表（带分页）
	 * 
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getBfdList(String con, int curPage, int rowsPerPage) {
		String sql = "select b.*,(select sum(p.nums) as totalNums  from bfd_product p where b.id=p.bfd_id ) as productNums from bfd b  where 1=1  ";
		if (!con.equals("")) {
			sql = sql + con;
		}
		return this.getResultByPage(sql, curPage, rowsPerPage);
	}

	/**
	 * 根据报废单ID返回报废单
	 * 
	 * @param bfd_id
	 * @return
	 */
	public Object getBfd(String bfd_id) {
		String sql = "select *  from bfd where id='" + bfd_id + "'";
		return this.getJdbcTemplate().queryForObject(sql, new BfdRowMapper());
	}

	/**
	 * 根据报废单ID返回保废单所修商品
	 * 
	 * @param bfd_id
	 * @return
	 */
	public List getBfdProducts(String bfd_id) {
		String sql = "select * from bfd_product where bfd_id='" + bfd_id + "'";
		return this.getJdbcTemplate().query(sql, new BfdProductRowMapper());
	}

	/**
	 * 删除报废单
	 * 
	 * @param bfd_id
	 */
	public void delBfd(String bfd_id) {
		String sql = "delete from bfd where id='" + bfd_id + "'";

		this.getJdbcTemplate().update(sql);

		sql = "delete from bfd_product where bfd_id='" + bfd_id + "'";
		this.getJdbcTemplate().update(sql);
	}
	
	/**
	 * 删除报废单商品
	 * @param bfdProductId
	 */
	public void delBfdProductByBfdId(String bfd_id)
	{
		String sql="delete from bfd_product where bfd_id='"+bfd_id+"'";
		this.getJdbcTemplate().update(sql);
	}

	/**
	 * 返回报废单可用ID
	 * 
	 * @return
	 */
	public String getBfdId() {
		String sql = "select bfdid from cms_all_seq";

		// 取当前序列号
		String curId = this.getJdbcTemplate().queryForInt(sql) + "";

		String day = DateComFunc.getCurDay();

		// 将序列号加1
		sql = "update cms_all_seq set bfdid=bfdid+1";
		this.getJdbcTemplate().update(sql);

		for (int i = curId.length(); i < 3; i++) {
			curId = "0" + curId;
		}

		return "BF" + day + "-" + curId;

	}

	/**
	 * 根据序列号查询最近一次报废记录
	 * 
	 * @param num
	 * @return
	 */
	public Object getBfdRecord(String num) {
		String sql = " select p.product_id,p.product_name,p.product_xh,p.product_gg,p.product_serial_num,p.product_remark,p.gzfx,p.pcgc,b.client_name,b.lxr,b.lxdh,b.email,b.address,b.remark from bfd b left join bfd_product p on b.id=p.bfd_id  where  b.id=(select b.id from bfd b  left join bfd_product p on b.id=p.bfd_id  where p.product_serial_num='"
				+ num + "'  order by p.id desc limit 1 )";
		return this.getResultMap(sql);
	}

	/**
	 * 保存报修单信息
	 * 
	 * @param bfd
	 */
	public void saveBfd(Bfd bfd, List bfdProducts) {
		Object[] param = new Object[7];
		String sql = "insert into bfd(id,bf_date,cj_date,jsr,cjr,state,remark) values(?,?,?,?,?,?,?)";
		
		param[0] = bfd.getId();
		param[1] = bfd.getBf_date();
		param[2] = DateComFunc.getToday();		
		param[3] = bfd.getJsr();
		param[4] = bfd.getCjr();		
		param[5] = bfd.getState();		
		param[6] = bfd.getRemark();
		this.getJdbcTemplate().update(sql, param);		
		
		this.saveBfdProduct(bfd, bfdProducts);
	}

	/**
	 * 保存报废单商品信息
	 * 
	 * @param bxdProduct
	 */
	public void saveBfdProduct(Bfd bfd, List bfdProducts) 
	{ 
		String sql = "";
		Object[] param = new Object[9];	
		String bfd_id = bfd.getId();
		String sqlStore=""; 
		sqlStore= "select id from storehouse where name='坏件库'";		
		Map map=getResultMap(sqlStore);		
		String storeId= (String)map.get("id") ; 
		
		if(bfdProducts != null && bfdProducts.size()>0)
		{
			for(int i=0;i<bfdProducts.size();i++)
			{
				BfdProduct bfdProduct = (BfdProduct)bfdProducts.get(i);
				if(bfdProduct != null){
				  if(!bfdProduct.getProduct_id().equals("") && !bfdProduct.getProduct_name().equals(""))
					{
		               sql = "insert into bfd_product(bfd_id,product_name,product_xh,product_id,qz_serial_num,product_remark,store_id,storestate,nums) values(?,?,?,?,?,?,?,?,?)";
             		   param[0] = bfd_id;
		               param[1] = bfdProduct.getProduct_name();
		               param[2] = bfdProduct.getProduct_xh();
		               param[3] = bfdProduct.getProduct_id();
		               param[4] = bfdProduct.getQz_serial_num();
		               param[5] = bfdProduct.getProduct_remark();
		               param[6] = storeId;
		               param[7] = "4";
		               param[8] = bfdProduct.getNums();
		               this.getJdbcTemplate().update(sql,param);
					}
				}	
			}
		}
	}

	/**
	 * 更改报废单
	 * 
	 * @param id
	 * @param bfdProduct
	 */
	public void updateBfd(Bfd bfd, List bfdProducts) 
	{
		Object param[] = new Object[5];
				
		String sql = "update bfd set bf_date=?,jsr=?,state=?,remark=? where id=? ";
        param[0] = bfd.getBf_date();
		param[1] = bfd.getJsr();		
		param[2] = bfd.getState();	
		param[3] = bfd.getRemark();
		param[4] = bfd.getId();
		this.getJdbcTemplate().update(sql, param);
		delBfdProductByBfdId(bfd.getId());
		
		this.saveBfdProduct(bfd, bfdProducts);
	}

	/**
	 * 查看报废单是否已经提交
	 * @param ckd_id
	 * @return
	 */
	public boolean isBfdSubmit(String bfd_id){
		boolean is = false;
		String sql = "select count(*) from bfd where id='" + bfd_id + "' and state='已提交'";
		int counts = this.getJdbcTemplate().queryForInt(sql);
		if(counts > 0){
			is = true;
		}
		return is;
	}
	
	/**
	 * 报废单封装对象
	 * 
	 * @author Administrator
	 * 
	 */
	class BfdRowMapper implements RowMapper 
	{
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			Bfd bfd = new Bfd();
			if (SqlUtil.columnIsExist(rs, "id"))
				bfd.setId(rs.getString("id"));
			if (SqlUtil.columnIsExist(rs, "bf_date"))
				bfd.setBf_date(rs.getString("bf_date"));
			if (SqlUtil.columnIsExist(rs, "cj_date"))
				bfd.setCj_date(rs.getString("cj_date"));
			if (SqlUtil.columnIsExist(rs, "jsr"))
				bfd.setJsr(rs.getString("jsr"));
			if (SqlUtil.columnIsExist(rs, "cjr"))
				bfd.setCjr(rs.getString("cjr"));			
			if (SqlUtil.columnIsExist(rs, "state"))
				bfd.setState(rs.getString("state"));			
			if (SqlUtil.columnIsExist(rs, "remark"))
				bfd.setRemark(rs.getString("remark"));
			return bfd;
		}
	}

	/**
	 * 报修单商品封装对象
	 * 
	 * @author Administrator
	 * 
	 */
	class BfdProductRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			BfdProduct bfdProduct = new BfdProduct();
			if (SqlUtil.columnIsExist(rs, "id"))
				bfdProduct.setId(rs.getInt("id"));
			if (SqlUtil.columnIsExist(rs, "bfd_id"))
				bfdProduct.setBfd_id(rs.getString("bfd_id"));
			if (SqlUtil.columnIsExist(rs, "product_name"))
				bfdProduct.setProduct_name(rs.getString("product_name"));
			if (SqlUtil.columnIsExist(rs, "product_id"))
				bfdProduct.setProduct_id(rs.getString("product_id"));
			if (SqlUtil.columnIsExist(rs, "product_xh"))
				bfdProduct.setProduct_xh(rs.getString("product_xh"));
			if (SqlUtil.columnIsExist(rs, "qz_serial_num"))
				bfdProduct.setQz_serial_num(rs
						.getString("qz_serial_num"));
			if (SqlUtil.columnIsExist(rs, "product_remark"))
				bfdProduct.setProduct_remark(rs.getString("product_remark"));
			if (SqlUtil.columnIsExist(rs, "store_id"))
				bfdProduct.setStore_id(rs.getString("store_id"));
			if (SqlUtil.columnIsExist(rs, "storestate"))
				bfdProduct.setStorestate(rs.getString("storestate"));
			if (SqlUtil.columnIsExist(rs, "nums"))
				bfdProduct.setNums(rs.getInt("nums"));
			
			return bfdProduct;
		}
	}

}
