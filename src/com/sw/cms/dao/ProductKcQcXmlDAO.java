package com.sw.cms.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.sw.cms.dao.base.BeanRowMapper;
import com.sw.cms.dao.base.JdbcBaseDAO;
import com.sw.cms.util.DateComFunc;
import com.sw.cms.xml.productkc.ProductKc;
import com.sw.cms.xml.productkc.ProductKcQc;
import com.sw.cms.xml.productkc.ProductKcQcXmlDo;

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
	
	
	/**
	 * ���ɿ���ڳ�
	 * ���µ�ҵ���߼����ɣ�ÿ���ÿ���ֿ�Ŀ����Ϣ���һ��XML�ļ�
	 * �ܿ������һXML�ļ����浽���ݿ��У��ⷿ��ʾ�á�all��
	 */
	public void genKcQcNew(Date cdate){
		try{
			//��ǰ����
			String qc_date = DateComFunc.formatDate(cdate);
			
			//ɾ���������ڳ�ֵ����������
			String dsql = "delete from product_kc_qc_xml where cdate='" + qc_date + "'";
			this.update(dsql);
			
			//ȡ��Ч�Ŀⷿ�б����ɸ��ⷿ���ڳ����
			String sql = "select * from storehouse where id not like 'WX%' and flag='1'";
			List storeList = this.getResultList(sql);
			if(storeList != null && storeList.size() > 0){
				for(int i=0; i < storeList.size(); i++){
					Map map = (Map)storeList .get(i);
					String store_id = (String)map.get("id");  //�ⷿ���
					
					String tempSql = "SELECT a.product_id AS productId,a.nums,b.price FROM product_kc_qc a LEFT JOIN product b ON b.product_id=a.product_id  where a.store_id='" + store_id + "' and a.cdate='" + qc_date + "'";
					List productList = this.getResultList(tempSql, new BeanRowMapper(ProductKc.class));
					
					ProductKcQc productKcQc = new ProductKcQc();
					productKcQc.setCdate(qc_date);
					productKcQc.setStoreId(store_id);
					productKcQc.setProducts(productList);
					
					ProductKcQcXmlDo xmlDo = new ProductKcQcXmlDo();
					String tempXml = xmlDo.toXml(productKcQc);  //���XML�ļ�
					
					//�������ڳ���Ϣ
					String isql = "insert into product_kc_qc_xml(store_id,cdate,xmlString,createDate) values('" + store_id + "','" + qc_date + "','" + tempXml + "',now())";
					this.update(isql);
				}
			}
			
			//�����ܵÿ���ڳ�ֵ
			String zsql = "select a.product_id as productId,sum(a.nums) as nums,b.price from product_kc_qc a left join product b on b.product_id=a.product_id where a.cdate='" + qc_date + "' GROUP by a.product_id,b.price";
			List productList = this.getResultList(zsql, new BeanRowMapper(ProductKc.class));
			ProductKcQc productKcQc = new ProductKcQc();
			productKcQc.setCdate(qc_date);
			productKcQc.setStoreId("all");
			productKcQc.setProducts(productList);
			
			ProductKcQcXmlDo xmlDo = new ProductKcQcXmlDo();
			String tempXml = xmlDo.toXml(productKcQc);  //���XML�ļ�
			
			//�����ܿ���ڳ���Ϣ
			String isql = "insert into product_kc_qc_xml(store_id,cdate,xmlString,createDate) values('all','" + qc_date + "','" + tempXml + "',now())";
			this.update(isql);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

}
