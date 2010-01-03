package com.sw.cms.dao;

import java.util.List;

import com.sw.cms.dao.base.BeanRowMapper;
import com.sw.cms.dao.base.JdbcBaseDAO;
import com.sw.cms.model.Cxd;
import com.sw.cms.model.CxdProduct;
import com.sw.cms.model.Page;
import com.sw.cms.util.DateComFunc;

/**
 * 拆卸单
 * @author liyt
 *
 */
public class CxdDAO extends JdbcBaseDAO {
	
	/**
	 * 取拆卸单列表
	 * @param start_date
	 * @param end_date
	 * @param state
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getCxdList(String con,int curPage, int rowsPerPage){
		String sql = "select * from cxd where 1=1";
		
		if(!con.equals("")){
			sql += con;
		}
		
		return this.getResultByPage(sql, curPage, rowsPerPage, new BeanRowMapper(Cxd.class));
	}
	
	
	/**
	 * 根据编号取拆卸单信息
	 * @param id
	 * @return
	 */
	public Cxd editCxd(String id){
		String sql = "select * from cxd where id='" + id + "'";
		return (Cxd)this.queryForObject(sql, new BeanRowMapper(Cxd.class));
	}
	
	
	/**
	 * 根据编号取拆卸单商品明细信息
	 * @param cxd_id
	 * @return
	 */
	public List editCxdProducts(String cxd_id){
		String sql = "select * from cxd_product where cxd_id='" + cxd_id + "'";
		return this.getResultList(sql, new BeanRowMapper(CxdProduct.class));
	}
	
	
	/**
	 * 根据编与判断拆卸单是否已提交
	 * @param cxd_id
	 * @return
	 */
	public boolean isCxdSubmit(String cxd_id){
		boolean is = false;
		String sql = "select count(*) as counts from cxd where id='" + cxd_id + "' and state='已提交'";
		int counts = this.getJdbcTemplate().queryForInt(sql);
		
		if(counts > 0){
			is = true;
		}
		return is;
	}
	
	
	/**
	 * 保存拆卸单信息
	 * @param cxd
	 * @param cxdProduct
	 */
	public void updateCxd(Cxd cxd,List cxdProducts){
		String sql = "select count(*) as counts from cxd where id='" + cxd.getId() + "'";
		int counts = this.getJdbcTemplate().queryForInt(sql);
		
		if(counts > 0){
			//记录存在更新
			sql = "update cxd set cdate=?,store_id=?,product_id=?,product_name=?,product_xh=?,product_dw=?,nums=?," +
					"price=?,hjje=?,jsr=?,remark=?,czr=?,cz_date=now(),serial_nums=?,qz_flag=?,state=? where id=?";
		}else{
			//记录不存在插入
			sql = "insert into cxd(cdate,store_id,product_id,product_name,product_xh,product_dw,nums,price,hjje,jsr,remark,czr,cz_date,serial_nums,qz_flag,state,id) " +
					"values(?,?,?,?,?,?,?,?,?,?,?,?,now(),?,?,?,?)";
		}
		
		Object[] param = new Object[16];
		param[0] = cxd.getCdate();
		param[1] = cxd.getStore_id();
		param[2] = cxd.getProduct_id();
		param[3] = cxd.getProduct_name();
		param[4] = cxd.getProduct_xh();
		param[5] = cxd.getProduct_dw();
		param[6] = cxd.getNums();
		param[7] = cxd.getPrice();
		param[8] = cxd.getHjje();
		param[9] = cxd.getJsr();
		param[10] = cxd.getRemark();
		param[11] = cxd.getCzr();
		param[12] = cxd.getSerial_nums();
		param[13] = cxd.getQz_flag();
		param[14] = cxd.getState();
		param[15] = cxd.getId();
		
		this.getJdbcTemplate().update(sql, param);
		
		this.delCxdProducts(cxd.getId());
		
		this.addCxdProducts(cxd, cxdProducts);
	}
	
	
	/**
	 * 删除拆卸单
	 * @param id
	 */
	public void delCxd(String id){
		String sql = "delete from cxd where id='" + id + "'";
		this.getJdbcTemplate().update(sql);
		
		sql = "delete from cxd_product where cxd_id='" + id + "'";
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * 取当前可用的序列号
	 * @return
	 */
	public String getCxdID() {
		String sql = "select cxdid from cms_all_seq";

		// 取当前序列号
		String curId = this.getJdbcTemplate().queryForInt(sql) + "";
		
		String day = DateComFunc.getCurDay();

		// 将序列号加1
		sql = "update cms_all_seq set cxdid=cxdid+1";
		this.getJdbcTemplate().update(sql);

		for (int i = curId.length(); i < 3; i++) {
			curId = "0" + curId;
		}

		return "CX" + day + "-" + curId;
	}

	
	/**
	 * 删除拆卸单相关商品
	 * @param cxd_id
	 */
	private void delCxdProducts(String cxd_id){
		String sql = "delete from cxd_product where cxd_id='" + cxd_id + "'";
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * 添加拆卸单相关商品
	 * @param cxd
	 * @param cxdProducts
	 */
	private void addCxdProducts(Cxd cxd,List cxdProducts){		
		String cxd_id = cxd.getId();
		String sql = "";
		Object[] param = new Object[11];
		
		if(cxdProducts != null && cxdProducts.size() > 0){
			for(int i=0;i<cxdProducts.size();i++){
				
				CxdProduct cxdProduct = (CxdProduct)cxdProducts.get(i);
				if(cxdProduct != null && cxdProduct.getProduct_id() != null){
					sql = "insert into cxd_product(cxd_id,product_id,product_name,product_xh,product_dw,price,nums,hj,remark,qz_serial_num,qz_flag) " +
							"values(?,?,?,?,?,?,?,?,?,?,?)";
					
					param[0] = cxd_id;
					param[1] = cxdProduct.getProduct_id();
					param[2] = cxdProduct.getProduct_name();
					param[3] = cxdProduct.getProduct_xh();
					param[4] = cxdProduct.getProduct_dw();
					param[5] = cxdProduct.getPrice();
					param[6] = cxdProduct.getNums();
					param[7] = cxdProduct.getHj();
					param[8] = cxdProduct.getRemark();
					param[9] = cxdProduct.getQz_serial_num();
					param[10] = cxdProduct.getQz_flag();
					
					this.getJdbcTemplate().update(sql, param);
				}
				
			}
		}		
	}

}
