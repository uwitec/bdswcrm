package com.sw.cms.dao;

import java.util.List;

import com.sw.cms.dao.base.BeanRowMapper;
import com.sw.cms.dao.base.JdbcBaseDAO;
import com.sw.cms.xml.productkc.ProductKc;

/**
 * ����XML�Ŀ���ڳ�����
 * @author liyt
 *
 */
public class ProductKcQcXmlDAO  extends JdbcBaseDAO {
	
	/**
	 * ȡԭ���еĿ����Ϣ���ֲֿ⣩
	 * @param store_id
	 * @param cdate
	 * @return
	 */
	public List getProducts(String store_id,String cdate){
		String tempSql = "select product_id as productId,nums,price  from product_kc_qc where store_id='" + store_id + "' and cdate='" + cdate + "'";
		return this.getResultList(tempSql, new BeanRowMapper(ProductKc.class));
	}
	
	/**
	 * ȡԭ���еĿ����Ϣ
	 * @param cdate
	 * @return
	 */
	public List getProducts(String cdate){
		String tempSql = "select product_id as productId,sum(nums) as nums,price  from product_kc_qc where cdate='" + cdate + "' group by product_id,price";
		return this.getResultList(tempSql, new BeanRowMapper(ProductKc.class));
	}
	
	/**
	 * ��ӿ���ڳ���Ϣ��XML��
	 * @param store_id
	 * @param cdate
	 * @param xmlString
	 */
	public void insertProductKcQcXml(String store_id,String cdate,String xmlString){
		String isql = "insert into product_kc_qc_xml(store_id,cdate,xmlString,createDate) values('" + store_id + "','" + cdate + "','" + xmlString + "',now())";
		this.update(isql);
	}
	
	
	/**
	 * ɾ������ڳ���Ϣ��XML��
	 * @param cdate
	 */
	public void deleteProductKcQcXml(String cdate){
		String sql = "delete from product_kc_qc_xml where cdate='" + cdate + "'";
		this.update(sql);
	}

}
