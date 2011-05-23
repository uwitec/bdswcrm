package com.sw.cms.dao;

import java.util.List;

import com.sw.cms.dao.base.JdbcBaseDAO;

/**
 * �������ۻ���
 * @author liyt
 *
 */
public class DeptXshzDAO extends JdbcBaseDAO {
	
	/**
	 * �������ۻ����б�(���Ż��ܱ�)
	 * @param start_date    ��ʼʱ��
	 * @param end_date      ����ʱ��
	 * @param client_name   �ͻ���ţ�ǰ̨ѡ����ҵ�ͻ��������̵ȣ�
	 * @param dj  ͳ�Ʋ��ŵȼ�
	 * @return
	 * @throws Exception
	 */
	public List getResults(String start_date,String end_date,String client_name,int dj,String product_kind,String product_name) throws Exception{
	
		String sql = "select a.xsry_dept as dept,sum(nums) as nums,sum(hjje) as hjje from product_sale_flow a where 1=1";
		if(!start_date.equals("")){
			sql += " and a.cz_date>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			sql += " and a.cz_date<='" + end_date + "'"; 
		}
		if(!client_name.equals("")){
			sql += " and a.client_name='" + client_name + "'"; 
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
		sql += " group by a.xsry_dept";
		
		sql = "select y.dept_id,y.dept_name," +
				"(select sum(x.nums) from (" + sql + ") x where x.dept like concat(y.dept_id,'%')) as nums," +
				"(select sum(x.hjje) from (" + sql + ") x where x.dept like concat(y.dept_id,'%')) as hjje " +
				"from dept y where LENGTH(y.dept_id)<=" + (dj*2);
		
		return this.getResultList(sql);
	}
	
	
	
	/**
	 * �������ۻ���--������Ա���ܱ�
	 * @param dept        ���ű��
	 * @param start_date  ��ʼʱ��
	 * @param end_date    ����ʱ��
	 * @param client_name �ͻ����
	 * @return
	 * @throws Exception
	 */
	public List getMxResults(String dept,String start_date,String end_date,String client_name,String product_kind,String product_name) throws Exception{
		String sql = "select a.xsry,b.real_name,sum(a.nums) as nums,sum(a.hjje) as hjje FROM product_sale_flow a left join sys_user b on b.user_id=a.xsry left join product c on c.product_id=a.product_id where 1=1";
		
		if(!dept.equals("")){
			sql += " and a.xsry_dept like '" + dept + "%'";
		}
		if(!start_date.equals("")){
			sql += " and a.cz_date>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			sql += " and a.cz_date<='" + end_date + "'";
		}
		if(!client_name.equals("")){
			sql += " and a.client_name='" + client_name + "'";
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
	 * �������ۻ���--������Ա������ϸ
	 * @param xsry
	 * @param start_date
	 * @param end_date
	 * @param client_name
	 * @return
	 * @throws Exception
	 */
	public List getProductMxResults(String xsry,String start_date,String end_date,String client_name,String product_kind,String product_name) throws Exception{
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
		if(!client_name.equals("")){
			sql += " a.client_name='" + client_name + "'";
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
