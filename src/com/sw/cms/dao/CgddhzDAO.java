package com.sw.cms.dao;

import java.util.List;

import com.sw.cms.dao.base.JdbcBaseDAO;

/**
 * �ɹ��������ܰ������ͻ���Ͱ���Ʒ��������
 * @author zhj
 *
 */

public class CgddhzDAO extends JdbcBaseDAO {

	
	/**
	 * ȡ��Ʒ�ɹ���������
	 * @param product_id
	 * @param start_date
	 * @param end_date
	 * @param product_xh
	 * @param product_kind
	 * @return
	 */
	public List getHpcgList(String product_kind,String start_date,String end_date,String product_name,String product_xh){
		
         //������δ���
		String jhdw_sql = "select 'δ���' as state,a.product_id,c.product_name,c.product_xh,sum(a.nums) as nums,sum(b.total) as je "+
				"from jhd_product a join jhd b on b.id=a.jhd_id left join product c on c.product_id=a.product_id "+
				"where b.state!='�����' and  c.prop='�����Ʒ' and b.th_flag='0' ";
		//������Ʒ���
		if(!product_kind.equals("")){
			String[] arryItems = product_kind.split(",");
			
			if(arryItems != null && arryItems.length >0){
				jhdw_sql += " and (";
				for(int i=0;i<arryItems.length;i++){
					if(i == 0){
						jhdw_sql += " a.product_id like '" + arryItems[i] + "%'";
					}else{
						jhdw_sql += " or a.product_id like '" + arryItems[i] + "%'";
					}
				}
				jhdw_sql += ")";
			}
			
		}
		if(!start_date.equals("")){
			jhdw_sql += " and DATE_FORMAT(b.cz_date,'%Y-%m-%d')>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			jhdw_sql += " and DATE_FORMAT(b.cz_date,'%Y-%m-%d')<='" + (end_date + " 23:59:59") + "'";
		}
		
		if(!product_name.equals("")){
			jhdw_sql += " and a.product_name like '%" + product_name + "%'";
		}
		if(!product_xh.equals("")){
			jhdw_sql += " and a.product_xh like '%" + product_xh + "%'";
		}
		jhdw_sql += " group by a.product_id,c.product_name,c.product_xh";
		
		
        //�����������
		String jhd_sql = "select '�����' as state,a.product_id,c.product_name,c.product_xh,sum(a.nums) as nums,sum(b.total) as je "+
				"from jhd_product a join jhd b on b.id=a.jhd_id left join product c on c.product_id=a.product_id "+
				"where b.state='�����' and  c.prop='�����Ʒ' and b.th_flag='0' ";
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
		
		if(!product_name.equals("")){
			jhd_sql += " and a.product_name like '%" + product_name + "%'";
		}
		if(!product_xh.equals("")){
			jhd_sql += " and a.product_xh like '%" + product_xh + "%'";
		}
		jhd_sql += " group by a.product_id,c.product_name,c.product_xh";
		
		String sql = "select * from ((" + jhdw_sql + ") union (" + jhd_sql + ")) x order by product_id,state desc";
		
		return this.getResultList(sql);
	}
	
	
	/**
	 * ȡ��Ʒ�ɹ����ܵ�����ϸ����������
	 * @param proudct_id
	 * @param start_date
	 * @param end_date
	 * @param client_name
	 * @return
	 */
	public List getDjmxList(String proudct_id,String start_date,String end_date){
		//������
		String jhd_sql = "select b.id as dj_id,b.state,b.gysbh as client_name,b.fzr as jsr,b.total,DATE_FORMAT(b.cz_date,'%Y-%m-%d') as creatdate,'viewJhd.html?id=' as url,b.cz_date from jhd_product a left join jhd b on b.id=a.jhd_id where b.th_flag='0'  ";
		if(!proudct_id.equals("")){
			jhd_sql += " and a.product_id='" + proudct_id + "'";
		}
		if(!start_date.equals("")){
			jhd_sql += " and DATE_FORMAT(b.cz_date,'%Y-%m-%d')>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			jhd_sql += " and DATE_FORMAT(b.cz_date,'%Y-%m-%d')<='" + (end_date + " 23:59:59") + "'";
		}
		
		
		
		String sql = "select * from ((" + jhd_sql + ")) x order by cz_date asc";
		
		return this.getResultList(sql);
	}
	
	
	/**
	 * ȡ�ͻ��ɹ������б���Ϣ����������
	 * @param start_date
	 * @param end_date
	 * @param dj_id
	 * @param client_name
	 * @return
	 */
	public List getClientCgList(String start_date,String end_date,String dj_id,String client_name){
		
		//������δ���
		String jhdw_sql = "select m.gysbh as client_id,n.name as client_name,sum(m.total) as je,'δ���' as state,n.lxr,n.lxdh  "+
		                 "from jhd m left join clients n on n.id=m.gysbh where m.state!='�����'  and m.th_flag='0' ";
		if(!dj_id.equals("")){
			jhdw_sql += " and id='" + dj_id + "'";
		}
		if(!start_date.equals("")){
			jhdw_sql += " and DATE_FORMAT(cz_date,'%Y-%m-%d')>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			jhdw_sql += " and DATE_FORMAT(cz_date,'%Y-%m-%d')<='" + (end_date + " 23:59:59") + "'";
		}
		if(!client_name.equals("")){
			jhdw_sql = jhdw_sql + " and n.name like '%" + client_name + "%'";
		}
		
		jhdw_sql += " group by m.gysbh,n.name,m.state,n.lxr,n.lxdh";
		
		
       //�����������
		String jhd_sql = "select m.gysbh as client_id,n.name as client_name,sum(m.total) as je,'�����' as state,n.lxr,n.lxdh  "+
		                 "from jhd m left join clients n on n.id=m.gysbh where m.state='�����' and m.th_flag='0' ";
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
			jhd_sql = jhd_sql + " and n.name like '%" + client_name + "%'";
		}
		
		jhd_sql += " group by m.gysbh,n.name,m.state,n.lxr,n.lxdh";
		
		String sql = "select * from ((" + jhdw_sql + ") union (" + jhd_sql + ")) x  order by client_id,state desc";
		
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
		String jhd_sql = "select id as dj_id,state,gysbh as client_name,fzr as jsr,DATE_FORMAT(cz_date,'%Y-%m-%d') as creatdate,total as je,'viewJhd.html?id=' as url,cz_date from jhd where th_flag='0'";
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
		
		String sql = "select * from ((" + jhd_sql + ")) x order by state,cz_date";
		
		return this.getResultList(sql);
	}
	
	
	/**
	 * ȡ������Ʒ��ϸ
	 * @param dj_id
	 * @param xwtype
	 * @return
	 */
	public List getDjmxList(String dj_id){
		String sql ="";
		sql = "select product_id,product_name,product_xh,nums,(price*nums) as je from jhd_product where jhd_id='" + dj_id + "'";
				
		return this.getResultList(sql);
	}
	
}
