package com.sw.cms.dao;

import java.util.List;

import com.sw.cms.dao.base.JdbcBaseDAO;

public class HpxsHzDAO extends JdbcBaseDAO {
	
	
	/**
	 * ����ͳ������ͳ�ƻ�Ʒ���ۻ��ܱ�
	 * @param product_kind   ��Ʒ���
	 * @param product_name   ��Ʒ����
	 * @param product_xh     ��Ʒ���
	 * @param start_date     ��ʼʱ��
	 * @param end_date       ����ʱ��
	 * @param client_name    �ͻ�����
	 * @param xsry_id        ������Ա
	 * @return
	 */
	public List getHpxshzTjResult(String product_kind,String product_name,String product_xh,
			String start_date,String end_date,String client_name,String xsry_id,String client_type){
		
		String sql = "select a.product_id,b.product_name,b.product_xh,sum(a.nums) as nums,sum(a.hjje) as hjje from product_sale_flow a left join product b on b.product_id=a.product_id left join clients c on c.id=a.client_name where 1=1";
		
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
		
		//�ͻ����
		if(!client_name.equals("")){
			sql += " and a.client_name='" + client_name + "'";
		}
		
		//�ͻ�����
		if(!client_type.equals("")){
			sql += " and c.client_type='" + client_type + "'";
		}
		
		//������Ա
		if(!xsry_id.equals("")){
			sql += " and a.xsry='" + xsry_id + "'";
		}
		
		sql += " group by a.product_id,b.product_name,b.product_xh";
		
		return this.getResultList(sql);
	}
	
	
	/**
	 * ����ͳ������ͳ�ƻ�Ʒ���ۻ��ܱ�(��Ӧ��)
	 * @param product_kind   ��Ʒ���
	 * @param product_name   ��Ʒ����
	 * @param product_xh     ��Ʒ���
	 * @param start_date     ��ʼʱ��
	 * @param end_date       ����ʱ��
	 * @param client_name    �ͻ�����
	 * @param xsry_id        ������Ա
	 * @return
	 */
	public List getGysHpxshzTjResult(String product_kind,String start_date,String end_date){
		
		String sql = "select a.product_id,c.product_name,c.product_xh,sum(a.nums) as nums from product_sale_flow a inner join product c on c.product_id=a.product_id where c.prop='�����Ʒ'";
		
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
		
		//��ʼʱ��
		if(!start_date.equals("")){
			sql += " and a.cz_date>='" + start_date + "'";
		}
		
		//����ʱ��
		if(!end_date.equals("")){
			sql += " and a.cz_date<='" + end_date + "'";
		}
		
		sql += " group by a.product_id,c.product_name,c.product_xh having sum(a.nums)>0 ";
		
		return this.getResultList(sql);
	}
	
	
	/**
	 * ����ͳ������ͳ�ƻ�Ʒë�����ۻ��ܱ�
	 * @param product_kind   ��Ʒ���
	 * @param product_name   ��Ʒ����
	 * @param product_xh     ��Ʒ���
	 * @param start_date     ��ʼʱ��
	 * @param end_date       ����ʱ��
	 * @param client_name    �ͻ�����
	 * @param xsry_id        ������Ա
	 * @return
	 */
	public List getHpxsmlhzTjResult(String product_kind,String product_name,String product_xh,
			String start_date,String end_date,String client_name,String xsry_id){
		
		String sql = "select a.product_id,b.product_name,b.product_xh,b.prop,sum(a.nums) as nums,sum(a.hjje) as hjje,sum(a.cb) as hjcb,sum(a.ygcb) as hjygcb,sum(a.bhsje) as bhsje from product_sale_flow a left join product b on b.product_id=a.product_id where 1=1";
		
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
		
		//�ͻ����
		if(!client_name.equals("")){
			sql += " and a.client_name='" + client_name + "'";
		}
		
		//������Ա
		if(!xsry_id.equals("")){
			sql += " and a.xsry='" + xsry_id + "'";
		}
		
		sql += " group by a.product_id,b.product_name,b.product_xh,b.prop";
		
		return this.getResultList(sql);
	}

	
	
	/**
	 * ȡ���۵��б�
	 * @param product_id
	 * @param start_date
	 * @param end_date
	 * @param client_name
	 * @param xsry_id
	 * @return
	 */
	public List getXsdList(String product_id,String start_date,String end_date,String client_name,String xsry_id,String client_type){
		
		String sql = "select distinct a.id,DATE_FORMAT(cz_date,'%Y-%m-%d') as creatdate,a.client_name,a.fzr from xsd a join xsd_product b on b.xsd_id=a.id left join clients c on c.id=a.client_name where a.state='�ѳ���'";
		if(!start_date.equals("")){
			sql = sql + " and DATE_FORMAT(a.cz_date,'%Y-%m-%d')>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			sql = sql + " and DATE_FORMAT(a.cz_date,'%Y-%m-%d')<='" + (end_date + " 23:59:59") + "'";
		}
		if(!client_name.equals("")){
			sql = sql + " and a.client_name='" + client_name + "'";
		}
		if(!xsry_id.equals("")){
			sql = sql + " and a.fzr='" + xsry_id + "'";
		}
		if(!product_id.equals("")){
			sql = sql + " and b.product_id='" + product_id + "'";
		}
		if(!client_type.equals("")){
			sql = sql + " and c.client_type='" + client_type + "'";
		}

		return this.getResultList(sql);
	}
	
	
	/**
	 * ȡ�˻����б�
	 * @param product_id
	 * @param start_date
	 * @param end_date
	 * @param client_name
	 * @param xsry_id
	 * @return
	 */
	public List getThdList(String product_id,String start_date,String end_date,String client_name,String xsry_id,String client_type){
		String sql = "select distinct a.thd_id,a.client_name,DATE_FORMAT(a.cz_date,'%Y-%m-%d') as th_date,a.th_fzr from thd a join thd_product b on b.thd_id=a.thd_id left join clients c on c.id=a.client_name where a.state='�����'";
		if(!start_date.equals("")){
			sql = sql + " and DATE_FORMAT(a.cz_date,'%Y-%m-%d')>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			sql = sql + " and DATE_FORMAT(a.cz_date,'%Y-%m-%d')<='" + (end_date + " 23:59:59") + "'";
		}
		if(!client_name.equals("")){
			sql = sql + " and a.client_name='" + client_name + "'";
		}
		if(!xsry_id.equals("")){
			sql = sql + " and a.th_fzr='" + xsry_id + "'";
		}
		if(!product_id.equals("")){
			sql = sql + " and b.product_id='" + product_id + "'";
		}
		if(!client_type.equals("")){
			sql = sql + " and c.client_type='" + client_type + "'";
		}
		return this.getResultList(sql);
	}
	
	
	/**
	 * ȡ���۵��б�
	 * @param product_id
	 * @param start_date
	 * @param end_date
	 * @param client_name
	 * @param xsry_id
	 * @return
	 */
	public List getLsdList(String product_id,String start_date,String end_date,String client_name,String xsry_id){
		String sql = "select distinct a.id,a.client_name,DATE_FORMAT(a.cz_date,'%Y-%m-%d') as creatdate,a.xsry from lsd a join lsd_product b on b.lsd_id=a.id where a.state='���ύ'";
		if(!start_date.equals("")){
			sql = sql + " and DATE_FORMAT(a.cz_date,'%Y-%m-%d')>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			sql = sql + " and DATE_FORMAT(a.cz_date,'%Y-%m-%d')<='" + (end_date + " 23:59:59") + "'";
		}
		if(!client_name.equals("")){
			sql = sql + " and a.client_name='" + client_name + "'";
		}
		if(!xsry_id.equals("")){
			sql = sql + " and a.xsry='" + xsry_id + "'";
		}
		if(!product_id.equals("")){
			sql = sql + " and b.product_id='" + product_id + "'";
		}

		return this.getResultList(sql);
	}
	
	
	/**
	 * ȡ��Ʒ���ۻ�����ϸ�б�
	 * @param product_id
	 * @param start_date
	 * @param end_date
	 * @param client_name
	 * @param xsry_id
	 * @return
	 */
	public List getHpxs_resultList(String product_id,String start_date,String end_date,String client_name,String xsry_id,String client_type){
		String sql = "select distinct a.id,a.client_name,a.cz_date,a.xsry,a.yw_type,a.store_id,sum(nums) as nums,sum(hjje) as hjje,sum(bhsje) as bhsje from product_sale_flow a  left join clients c on c.id=a.client_name where 1=1";
		if(!start_date.equals("")){
			sql = sql + " and a.cz_date>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			sql = sql + " and a.cz_date<='" + (end_date + " 23:59:59") + "'";
		}
		if(!client_name.equals("")){
			sql = sql + " and a.client_name='" + client_name + "'";
		}
		if(!xsry_id.equals("")){
			sql = sql + " and a.xsry='" + xsry_id + "'";
		}
		if(!product_id.equals("")){
			sql = sql + " and a.product_id='" + product_id + "'";
		}
		if(!client_type.equals("")){
			sql = sql + " and c.client_type='" + client_type + "'";
		}
		
		sql += " group by a.id,a.client_name,a.cz_date,a.xsry,a.yw_type,a.store_id order by a.yw_type  ";
		
		return this.getResultList(sql);
	}
}
