package com.sw.cms.dao;

import java.util.List;

import com.sw.cms.dao.base.JdbcBaseDAO;

/**
 *���۶�������ͳ�� 
 * @author liyt
 *
 */

public class XsdHzDAO extends JdbcBaseDAO {
	
	/**
	 * ȡ���۶����б�
	 * @param start_date ��ʼʱ��
	 * @param end_date����ʱ��
	 * @param client_name�ͻ�����
	 * @param xsry_id������Ա
	 * @param dj_id���ݱ��
	 * @return
	 */
	public List getXsdList(String start_date,String end_date,String client_name,String dj_id){
		String sql = "select DATE_FORMAT(a.cz_date,'%Y-%m-%d') as creatdate,a.id,b.name as client_name,a.fzr,a.xsdje,a.sjcjje from xsd a left join clients b on b.id=a.client_name where a.state='�ѳ���'";
		if(!dj_id.equals("")){
			sql = sql + " and a.id='" + dj_id + "'";
		}		
		if(!start_date.equals("")){
			sql = sql + " and DATE_FORMAT(a.cz_date,'%Y-%m-%d')>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			sql = sql + " and DATE_FORMAT(a.cz_date,'%Y-%m-%d')<='" + (end_date + " 23:59:59") + "'";
		}
		if(!client_name.equals("")){
			sql = sql + " and b.name like'%" + client_name + "%'";
		}
		

		return this.getResultList(sql);
	}	
	
	/**
	 * ȡ�ͻ����ۻ����б���Ϣ�����۵���
	 * @param start_date
	 * @param end_date
	 * @param dj_id
	 * @param client_name
	 * @return
	 */
	public List getClientXsList(String start_date,String end_date,String dj_id,String client_name){
		
		//���۵�δ����
		String xsdw_sql = "select m.client_name as client_id,n.name as client_name,sum(m.xsdje) as je,'δ����' as state,n.lxr,n.lxdh  "+
		                 "from xsd m left join clients n on n.id=m.client_name where m.state!='�ѳ���' and  m.th_flag='0' ";
		if(!dj_id.equals("")){
			xsdw_sql += " and m.id='" + dj_id + "'";
		}
		if(!start_date.equals("")){
			xsdw_sql += " and DATE_FORMAT(m.cz_date,'%Y-%m-%d')>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			xsdw_sql += " and DATE_FORMAT(m.cz_date,'%Y-%m-%d')<='" + (end_date + " 23:59:59") + "'";
		}
		if(!client_name.equals("")){
			xsdw_sql = xsdw_sql + " and n.name like '%" + client_name + "%'";
		}
		
		xsdw_sql += " group by m.client_name,n.name,m.state,n.lxr,n.lxdh";
		
		
       //���۵��ѳ���
		String xsd_sql = "select m.client_name as client_id,n.name as client_name,sum(m.xsdje) as je,'�ѳ���' as state,n.lxr,n.lxdh  "+
		                 "from xsd m left join clients n on n.id=m.client_name where m.state='�ѳ���' and  m.th_flag='0' ";
		if(!dj_id.equals("")){
			xsd_sql += " and id='" + dj_id + "'";
		}
		if(!start_date.equals("")){
			xsd_sql += " and DATE_FORMAT(cz_date,'%Y-%m-%d')>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			xsd_sql += " and DATE_FORMAT(cz_date,'%Y-%m-%d')<='" + (end_date + " 23:59:59") + "'";
		}
		if(!client_name.equals("")){
			xsd_sql = xsd_sql + " and n.name like '%" + client_name + "%'";
		}
		
		xsd_sql += " group by m.client_name,n.name,m.state,n.lxr,n.lxdh";
		
		String sql = "select * from ((" + xsdw_sql + ") union (" + xsd_sql + ")) x  order by client_id,state desc";
		
		return this.getResultList(sql);
	}

	
	/**
	 * �ͻ����ۻ��ܵ����б�(���۵��������˻���)
	 * @param start_date
	 * @param end_date
	 * @param dj_id
	 * @param client_name
	 * @return
	 */
	public List getClientMxList(String start_date,String end_date,String dj_id,String client_name){
		
		//���۵�
		String xsd_sql = "select id as dj_id,state,client_name as client_name,fzr as jsr,DATE_FORMAT(cz_date,'%Y-%m-%d') as creatdate,xsdje,sjcjje,'viewXsd.html?id=' as url,cz_date from xsd where th_flag='0'";
		if(!dj_id.equals("")){
			xsd_sql += " and id='" + dj_id + "'";
		}
		if(!start_date.equals("")){
			xsd_sql += " and DATE_FORMAT(cz_date,'%Y-%m-%d')>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			xsd_sql += " and DATE_FORMAT(cz_date,'%Y-%m-%d')<='" + (end_date + " 23:59:59") + "'";
		}
		if(!client_name.equals("")){
			xsd_sql += " and client_name='" + client_name + "'";
		}
		
		String sql = "select * from ((" + xsd_sql + ")) x order by state,cz_date";
		
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
		sql = "select product_id,product_name,product_xh,nums,(price*nums) as je from xsd_product where xsd_id='" + dj_id + "'";
				
		return this.getResultList(sql);
	}
	
	/**
	 * ȡ��Ʒ���۶�������
	 * @param product_id
	 * @param start_date
	 * @param end_date
	 * @param product_xh
	 * @param product_kind
	 * @return
	 */
	public List getHpxsList(String product_kind,String start_date,String end_date,String product_name,String product_xh){
		
         //���۵�δ����
		String xsdw_sql = "select 'δ����' as state,a.product_id,c.sp_txm,c.product_name,c.product_xh,sum(a.nums) as nums,sum(a.price*a.nums) as je "+
				"from xsd_product a join xsd b on b.id=a.xsd_id left join product c on c.product_id=a.product_id "+
				"where b.state!='�ѳ���' and  c.prop='�����Ʒ' and b.th_flag='0' ";
		//������Ʒ���
		if(!product_kind.equals("")){
			String[] arryItems = product_kind.split(",");
			
			if(arryItems != null && arryItems.length >0){
				xsdw_sql += " and (";
				for(int i=0;i<arryItems.length;i++){
					if(i == 0){
						xsdw_sql += " a.product_id like '" + arryItems[i] + "%'";
					}else{
						xsdw_sql += " or a.product_id like '" + arryItems[i] + "%'";
					}
				}
				xsdw_sql += ")";
			}
			
		}
		if(!start_date.equals("")){
			xsdw_sql += " and DATE_FORMAT(b.cz_date,'%Y-%m-%d')>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			xsdw_sql += " and DATE_FORMAT(b.cz_date,'%Y-%m-%d')<='" + (end_date + " 23:59:59") + "'";
		}
		
		if(!product_name.equals("")){
			xsdw_sql += " and a.product_name like '%" + product_name + "%'";
		}
		if(!product_xh.equals("")){
			xsdw_sql += " and a.product_xh like '%" + product_xh + "%'";
		}
		xsdw_sql += " group by a.product_id,c.product_name,c.product_xh";
		
		
        //���۵��ѳ���
		String xsd_sql = "select '�ѳ���' as state,a.product_id,c.sp_txm,c.product_name,c.product_xh,sum(a.nums) as nums,sum(a.price*a.nums) as je "+
				"from xsd_product a join xsd b on b.id=a.xsd_id left join product c on c.product_id=a.product_id "+
				"where b.state='�ѳ���' and  c.prop='�����Ʒ' and b.th_flag='0' ";
		//������Ʒ���
		if(!product_kind.equals("")){
			String[] arryItems = product_kind.split(",");
			
			if(arryItems != null && arryItems.length >0){
				xsd_sql += " and (";
				for(int i=0;i<arryItems.length;i++){
					if(i == 0){
						xsd_sql += " a.product_id like '" + arryItems[i] + "%'";
					}else{
						xsd_sql += " or a.product_id like '" + arryItems[i] + "%'";
					}
				}
				xsd_sql += ")";
			}
			
		}
		if(!start_date.equals("")){
			xsd_sql += " and DATE_FORMAT(b.cz_date,'%Y-%m-%d')>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			xsd_sql += " and DATE_FORMAT(b.cz_date,'%Y-%m-%d')<='" + (end_date + " 23:59:59") + "'";
		}
		
		if(!product_name.equals("")){
			xsd_sql += " and a.product_name like '%" + product_name + "%'";
		}
		if(!product_xh.equals("")){
			xsd_sql += " and a.product_xh like '%" + product_xh + "%'";
		}
		xsd_sql += " group by a.product_id,c.product_name,c.product_xh";
		
		String sql = "select * from ((" + xsdw_sql + ") union (" + xsd_sql + ")) x order by product_id,state desc";
		
		return this.getResultList(sql);
	}
	
	
	/**
	 * ȡ��Ʒ���ۻ��ܵ�����ϸ�����۵���
	 * @param proudct_id
	 * @param start_date
	 * @param end_date
	 * 
	 * @return
	 */
	public List getHpDjmxList(String proudct_id,String start_date,String end_date){
		//���۵�
		String xsd_sql = "select b.id as dj_id,b.state,b.client_name as client_name,b.fzr as jsr,b.xsdje,b.sjcjje,DATE_FORMAT(b.cz_date,'%Y-%m-%d') as creatdate,'viewXsd.html?id=' as url,b.cz_date from xsd_product a left join xsd b on b.id=a.xsd_id where b.th_flag='0'  ";
		if(!proudct_id.equals("")){
			xsd_sql += " and a.product_id='" + proudct_id + "'";
		}
		if(!start_date.equals("")){
			xsd_sql += " and DATE_FORMAT(b.cz_date,'%Y-%m-%d')>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			xsd_sql += " and DATE_FORMAT(b.cz_date,'%Y-%m-%d')<='" + (end_date + " 23:59:59") + "'";
		}
		
		
		
		String sql = "select * from ((" + xsd_sql + ")) x order by cz_date asc";
		
		return this.getResultList(sql);
	}
}
