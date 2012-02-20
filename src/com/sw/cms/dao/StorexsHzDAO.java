package com.sw.cms.dao;

import java.util.List;

import com.sw.cms.dao.base.JdbcBaseDAO;

public class StorexsHzDAO extends JdbcBaseDAO {
	
	
	/**
	 * ����ͳ������ͳ�Ʋֿ����ۻ��ܱ�
	 * @param product_kind   ��Ʒ���
	 * @param product_name   ��Ʒ����
	 * @param product_xh     ��Ʒ���
	 * @param start_date     ��ʼʱ��
	 * @param end_date       ����ʱ��
	 * @param store_id    �ֿ�����
	 * @return
	 */
	public List getStorexshzTjResult(String product_kind,String product_name,String product_xh,
			String start_date,String end_date,String store_id){
		
		String sql = "select a.store_id,sum(a.nums) as nums,sum(a.hjje) as hjje from product_sale_flow a left join product b on b.product_id=a.product_id where 1=1";
		
		//������Ʒ���
		if(!product_kind.equals("")){
			String[] arryItems = product_kind.split(",");			
			if(arryItems != null && arryItems.length >0){
				sql += " and (";
				for(int i=0;i<arryItems.length;i++){
					if(i == 0){
						sql += " b.product_kind like '" + arryItems[i] + "%'";
					}else{
						sql += " or b.product_kind like '" + arryItems[i] + "%'";
					}
				}
				sql += ")";
			}
		}
		
		//��Ʒ����
		if(!product_name.equals("")){
			sql += " and b.product_name like '%" + product_name + "%'";
		}
		
		//��Ʒ���
		if(!product_name.equals("")){
			sql += " and b.product_xh like '%" + product_xh + "%'";
		}
		
		//��ʼʱ��
		if(!start_date.equals("")){
			sql += " and a.cz_date>='" + start_date + "'";
		}
		
		//����ʱ��
		if(!end_date.equals("")){
			sql += " and a.cz_date<='" + end_date + "'";
		}
		
		
		//�ֿ���
		if(!store_id.equals("")){
			sql += " and a.store_id='" + store_id + "'";
		}
		
		sql += " group by a.store_id";
		
		return this.getResultList(sql);
	}
	
		
	/**
	 * �ֿ����ۻ���--��ϸ��
	 * @param dept        ���ű��
	 * @param start_date  ��ʼʱ��
	 * @param end_date    ����ʱ��
	 * @param store_id �ֿ���
	 * @return
	 * @throws Exception
	 */
	public List getStoreResultMx(String store_id,String start_date,String end_date,String product_kind,String product_name,String product_xh) throws Exception{
		String sql = "select a.xsry,b.real_name,sum(a.nums) as nums,sum(a.hjje) as hjje FROM product_sale_flow a left join sys_user b on b.user_id=a.xsry left join product c on c.product_id=a.product_id where 1=1";
		
		if(!store_id.equals("")){
			sql += " and a.store_id = '" + store_id + "'";
		}
		if(!start_date.equals("")){
			sql += " and a.cz_date>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			sql += " and a.cz_date<='" + end_date + "'";
		}
		
		//������Ʒ���
		if(!product_kind.equals("")){
			String[] arryItems = product_kind.split(",");			
			if(arryItems != null && arryItems.length >0){
				sql += " and (";
				for(int i=0;i<arryItems.length;i++){
					if(i == 0){
						sql += " c.product_kind like '" + arryItems[i] + "%'";
					}else{
						sql += " or c.product_kind like '" + arryItems[i] + "%'";
					}
				}
				sql += ")";
			}
		}
		
		//��Ʒ����
		if(!product_name.equals("")){
			sql += " and c.product_name like '%" + product_name + "%'";
		}
		
		sql += " group by a.xsry,b.real_name";
		
		return this.getResultList(sql);
	}
	
	/**
	 * �ֿ����ۻ���--������Ա������ϸ
	 * @param xsry
	 * @param start_date
	 * @param end_date
	 * @param store_id
	 * @return
	 * @throws Exception
	 */
	public List getStorexsProductMxResult(String xsry,String start_date,String end_date,String store_id,String product_kind,String product_name) throws Exception{
		String sql = "select a.product_id,c.product_name,c.product_xh,sum(a.nums) as nums,sum(a.hjje) as hjje from product_sale_flow a left join product c on c.product_id=a.product_id where 1=1";
		
		if(!xsry.equals("")){
			sql += " and a.xsry='" + xsry + "'";
		}
		if(!start_date.equals("")){
			sql += " and a.cz_date>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			sql += " and a.cz_date<='" + end_date + "'";
		}
		if(!store_id.equals("")){
			sql += " and a.store_id='" + store_id + "'";
		}
		
		//������Ʒ���
		if(!product_kind.equals("")){
			String[] arryItems = product_kind.split(",");			
			if(arryItems != null && arryItems.length >0){
				sql += " and (";
				for(int i=0;i<arryItems.length;i++){
					if(i == 0){
						sql += " c.product_kind like '" + arryItems[i] + "%'";
					}else{
						sql += " or c.product_kind like '" + arryItems[i] + "%'";
					}
				}
				sql += ")";
			}
		}
		
		//��Ʒ����
		if(!product_name.equals("")){
			sql += " and c.product_name like '%" + product_name + "%'";
		}
		
		sql += " group by a.product_id,c.product_name,c.product_xh";
		
		return this.getResultList(sql);
	}
}
