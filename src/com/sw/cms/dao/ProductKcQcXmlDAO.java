package com.sw.cms.dao;

import java.util.List;

import com.sw.cms.dao.base.BeanRowMapper;
import com.sw.cms.dao.base.JdbcBaseDAO;
import com.sw.cms.xml.productkc.ProductKc;

/**
 * 基于XML的库存期初处理
 * @author liyt
 *
 */
public class ProductKcQcXmlDAO  extends JdbcBaseDAO {
	
	/**
	 * 取原表中的库存信息（分仓库）
	 * @param store_id
	 * @param cdate
	 * @return
	 */
	public List getProducts(String store_id,String cdate){
		String tempSql = "select product_id as productId,nums,price  from product_kc_qc where store_id='" + store_id + "' and cdate='" + cdate + "'";
		return this.getResultList(tempSql, new BeanRowMapper(ProductKc.class));
	}
	
	/**
	 * 取原表中的库存信息
	 * @param cdate
	 * @return
	 */
	public List getProducts(String cdate){
		String tempSql = "select product_id as productId,sum(nums) as nums,price  from product_kc_qc where cdate='" + cdate + "' group by product_id,price";
		return this.getResultList(tempSql, new BeanRowMapper(ProductKc.class));
	}
	
	/**
	 * 添加库存期初信息（XML）
	 * @param store_id
	 * @param cdate
	 * @param xmlString
	 */
	public void insertProductKcQcXml(String store_id,String cdate,String xmlString){
		String isql = "insert into product_kc_qc_xml(store_id,cdate,xmlString,createDate) values('" + store_id + "','" + cdate + "','" + xmlString + "',now())";
		this.update(isql);
	}
	
	
	/**
	 * 删除库存期初信息（XML）
	 * @param cdate
	 */
	public void deleteProductKcQcXml(String cdate){
		String sql = "delete from product_kc_qc_xml where cdate='" + cdate + "'";
		this.update(sql);
	}

}
