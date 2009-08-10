package com.sw.cms.dao;

import java.util.List;

import com.sw.cms.dao.base.JdbcBaseDAO;

/**
 * �ɹ����ܰ������ͻ���Ͱ���Ʒ��������
 * @author liyt
 *
 */

public class CghzDAO extends JdbcBaseDAO {

	
	/**
	 * ȡ��Ʒ�ɹ�����
	 * @param product_id
	 * @param start_date
	 * @param end_date
	 * @param client_name
	 * @param xsry_id
	 * @return
	 */
	public List getHpcgList(String product_kind,String start_date,String end_date,String client_name,String product_name,String product_xh){
		
		//������
		String jhd_sql = "select a.product_id,c.product_name,c.product_xh,sum(nums) as nums,sum(a.price*a.nums) as je from jhd_product a join jhd b on b.id=a.jhd_id left join product c on c.product_id=a.product_id where b.state='�����' and c.prop='�����Ʒ'";
		//������Ʒ���
		if(!product_kind.equals("")){
			String[] arryItems = product_kind.split(",");
			
			if(arryItems != null && arryItems.length >0){
				jhd_sql += " and (";
				for(int i=0;i<arryItems.length;i++){
					if(i == 0){
						jhd_sql += " a.product_id like '" + arryItems[i] + "%'";
					}else{
						jhd_sql += " or a.product_id like '" + arryItems[i] + "%'";
					}
				}
				jhd_sql += ")";
			}
			
		}
		if(!start_date.equals("")){
			jhd_sql += " and DATE_FORMAT(b.cz_date,'%Y-%m-%d')>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			jhd_sql += " and DATE_FORMAT(b.cz_date,'%Y-%m-%d')<='" + (end_date + " 23:59:59") + "'";
		}
		if(!client_name.equals("")){
			jhd_sql += " and b.gysbh ='" + client_name + "'";
		}
		if(!product_name.equals("")){
			jhd_sql += " and a.product_name like '%" + product_name + "%'";
		}
		if(!product_xh.equals("")){
			jhd_sql += " and a.product_xh like '%" + product_xh + "%'";
		}
		jhd_sql += " group by a.product_id,c.product_name,c.product_xh";
		
		//�ɹ��˻���
		String cgthd_sql = "select a.product_id,c.product_name,c.product_xh,(0-sum(nums)) as nums,(0-sum(th_price*nums)) as je from cgthd_product a join cgthd b on b.id=a.cgthd_id left join product c on c.product_id=a.product_id where b.state='�ѳ���' and c.prop='�����Ʒ'";
		//������Ʒ���
		if(!product_kind.equals("")){
			String[] arryItems = product_kind.split(",");
			
			if(arryItems != null && arryItems.length >0){
				cgthd_sql += " and (";
				for(int i=0;i<arryItems.length;i++){
					if(i == 0){
						cgthd_sql += " a.product_id like '" + arryItems[i] + "%'";
					}else{
						cgthd_sql += " or a.product_id like '" + arryItems[i] + "%'";
					}
				}
				cgthd_sql += ")";
			}
			
		}
		if(!start_date.equals("")){
			cgthd_sql += " and DATE_FORMAT(b.cz_date,'%Y-%m-%d')>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			cgthd_sql += " and DATE_FORMAT(b.cz_date,'%Y-%m-%d')<='" + (end_date + " 23:59:59") + "'";
		}
		if(!client_name.equals("")){
			cgthd_sql += " and b.provider_name='" + client_name + "'";
		}
		if(!product_name.equals("")){
			cgthd_sql += " and a.product_name like '%" + product_name + "%'";
		}
		if(!product_xh.equals("")){
			cgthd_sql += " and a.product_xh like '%" + product_xh + "%'";
		}		
		cgthd_sql += " group by a.product_id,c.product_name,c.product_xh";
		
		String sql = "select product_id,product_name,product_xh,sum(nums) as nums,sum(je) as je from ((" + jhd_sql + ") union all (" + cgthd_sql + ")) x group by product_id,product_name,product_xh";
		
		return this.getResultList(sql);
	}
	
	
	/**
	 * ȡ��Ʒ�ɹ����ܵ�����ϸ�����������ɹ��˻�����
	 * @param proudct_id
	 * @param start_date
	 * @param end_date
	 * @param client_name
	 * @return
	 */
	public List getDjmxList(String proudct_id,String start_date,String end_date,String client_name){
		//������
		String jhd_sql = "select b.id as dj_id,'�ɹ�' as xwtype,b.gysbh as client_name,b.fzr as jsr,DATE_FORMAT(b.cz_date,'%Y-%m-%d') as creatdate,'viewJhd.html?id=' as url,b.cz_date from jhd_product a left join jhd b on b.id=a.jhd_id where b.state='�����'";
		if(!proudct_id.equals("")){
			jhd_sql += " and a.product_id='" + proudct_id + "'";
		}
		if(!start_date.equals("")){
			jhd_sql += " and DATE_FORMAT(b.cz_date,'%Y-%m-%d')>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			jhd_sql += " and DATE_FORMAT(b.cz_date,'%Y-%m-%d')<='" + (end_date + " 23:59:59") + "'";
		}
		if(!client_name.equals("")){
			jhd_sql += " and b.gysbh='" + client_name + "'";
		}
		
		//�ɹ��˻���
		String cgthd_sql = "select b.id as dj_id,'�˻�' as xwtype,b.provider_name as client_name,b.jsr,DATE_FORMAT(b.cz_date,'%Y-%m-%d') as creatdate,'viewCgthd.html?id=' as url,b.cz_date from cgthd_product a left join cgthd b on b.id=a.cgthd_id where b.state='�ѳ���'";
		if(!proudct_id.equals("")){
			cgthd_sql += " and a.product_id='" + proudct_id + "'";
		}
		if(!start_date.equals("")){
			cgthd_sql += " and DATE_FORMAT(b.cz_date,'%Y-%m-%d')>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			cgthd_sql += " and DATE_FORMAT(b.cz_date,'%Y-%m-%d')<='" + (end_date + " 23:59:59") + "'";
		}
		if(!client_name.equals("")){
			cgthd_sql += " and b.provider_name='" + client_name + "'";
		}
		
		String sql = "select * from ((" + jhd_sql + ") union (" + cgthd_sql + ")) x order by cz_date asc";
		
		return this.getResultList(sql);
	}
	
	
	/**
	 * ȡ�ͻ��ɹ������б���Ϣ�����������ɹ��˻�����
	 * @param start_date
	 * @param end_date
	 * @param dj_id
	 * @param client_name
	 * @return
	 */
	public List getClientCgList(String start_date,String end_date,String dj_id,String client_name){
		
		//������
		String jhd_sql = "select gysbh as client_id,sum(total) as je  from jhd where state='�����'";
		if(!dj_id.equals("")){
			jhd_sql += " and id='" + dj_id + "'";
		}
		if(!start_date.equals("")){
			jhd_sql += " and DATE_FORMAT(cz_date,'%Y-%m-%d')>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			jhd_sql += " and DATE_FORMAT(cz_date,'%Y-%m-%d')<='" + (end_date + " 23:59:59") + "'";
		}
		jhd_sql += " group by client_id";

		
		//�ɹ��˻���
		String cgthd_sql = "select provider_name as client_id,(0-sum(tkzje)) as je from cgthd where state='�ѳ���'";
		if(!dj_id.equals("")){
			cgthd_sql += " and id='" + dj_id + "'";
		}
		if(!start_date.equals("")){
			cgthd_sql += " and DATE_FORMAT(cz_date,'%Y-%m-%d')>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			cgthd_sql += " and DATE_FORMAT(cz_date,'%Y-%m-%d')<='" + (end_date + " 23:59:59") + "'";
		}
		cgthd_sql += " group by client_id";

		
		String sql = "select client_id,sum(je) as je from ((" + jhd_sql + ") union (" + cgthd_sql + ")) x group by x.client_id";
		
		sql = "select m.client_id,n.name as client_name,n.lxr,n.lxdh,m.je from (" + sql + ") m left join clients n on n.id=m.client_id where 1=1";
		if(!client_name.equals("")){
			sql = sql + " and n.name like '%" + client_name + "%'";
		}
		
		return this.getResultList(sql);
	}
	
	
	/**
	 * �ͼǲɹ����ܵ����б�(�ɹ������ɹ��˻���)
	 * @param start_date
	 * @param end_date
	 * @param dj_id
	 * @param client_name
	 * @return
	 */
	public List getClientMxList(String start_date,String end_date,String dj_id,String client_name){
		
		//������
		String jhd_sql = "select id as dj_id,'�ɹ�' as xwtype,gysbh as client_name,fzr as jsr,DATE_FORMAT(cz_date,'%Y-%m-%d') as creatdate,total as je,'viewJhd.html?id=' as url,cz_date from jhd where state='�����'";
		if(!dj_id.equals("")){
			jhd_sql += " and id='" + dj_id + "'";
		}
		if(!start_date.equals("")){
			jhd_sql += " and DATE_FORMAT(cz_date,'%Y-%m-%d')>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			jhd_sql += " and DATE_FORMAT(cz_date,'%Y-%m-%d')<='" + (end_date + " 23:59:59") + "'";
		}
		if(!client_name.equals("")){
			jhd_sql += " and gysbh='" + client_name + "'";
		}

		
		//�ɹ��˻���
		String cgthd_sql = "select id as dj_id,'�ɹ��˻�' as xwtype,provider_name as client_name,jsr,DATE_FORMAT(cz_date,'%Y-%m-%d') as creatdate,(0-tkzje) as je,'viewCgthd.html?id=' as url,cz_date from cgthd where state='�ѳ���'";
		if(!dj_id.equals("")){
			cgthd_sql += " and id='" + dj_id + "'";
		}
		if(!start_date.equals("")){
			cgthd_sql += " and DATE_FORMAT(cz_date,'%Y-%m-%d')>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			cgthd_sql += " and DATE_FORMAT(cz_date,'%Y-%m-%d')<='" + (end_date + " 23:59:59") + "'";
		}
		if(!client_name.equals("")){
			cgthd_sql += " and provider_name='" + client_name + "'";
		}
		
		String sql = "select * from ((" + jhd_sql + ") union (" + cgthd_sql + ")) x order by cz_date";
		
		return this.getResultList(sql);
	}
	
	
	/**
	 * ȡ���ݲ�Ʒ��ϸ
	 * @param dj_id
	 * @param xwtype
	 * @return
	 */
	public List getDjmxList(String dj_id,String xwtype){
		String sql ="";
		
		if(xwtype.equals("�ɹ�")){
			sql = "select product_id,product_name,product_xh,nums,(price*nums) as je from jhd_product where jhd_id='" + dj_id + "'";
		}else{
			sql = "select product_id,product_name,product_xh,(0-nums) as nums,(0-xj) as je from cgthd_product where cgthd_id='" + dj_id + "'";
		}
		
		return this.getResultList(sql);
	}
	
}
