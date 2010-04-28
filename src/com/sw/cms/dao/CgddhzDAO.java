package com.sw.cms.dao;

import java.util.List;

import com.sw.cms.dao.base.JdbcBaseDAO;

/**
 * 采购订单汇总包括按客户汇和按货品汇总两种
 * @author zhj
 *
 */

public class CgddhzDAO extends JdbcBaseDAO {

	
	/**
	 * 取货品采购订单汇总
	 * @param product_id
	 * @param start_date
	 * @param end_date
	 * @param product_xh
	 * @param product_kind
	 * @return
	 */
	public List getHpcgList(String product_kind,String start_date,String end_date,String product_name,String product_xh){
		
         //进货单未入库
		String jhdw_sql = "select '未入库' as state,a.product_id,c.product_name,c.product_xh,sum(a.nums) as nums,sum(b.total) as je "+
				"from jhd_product a join jhd b on b.id=a.jhd_id left join product c on c.product_id=a.product_id "+
				"where b.state!='已入库' and  c.prop='库存商品' and b.th_flag='0' ";
		//处理商品类别
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
		
		
        //进货单已入库
		String jhd_sql = "select '已入库' as state,a.product_id,c.product_name,c.product_xh,sum(a.nums) as nums,sum(b.total) as je "+
				"from jhd_product a join jhd b on b.id=a.jhd_id left join product c on c.product_id=a.product_id "+
				"where b.state='已入库' and  c.prop='库存商品' and b.th_flag='0' ";
		//处理商品类别
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
	 * 取货品采购汇总单据明细（进货单）
	 * @param proudct_id
	 * @param start_date
	 * @param end_date
	 * @param client_name
	 * @return
	 */
	public List getDjmxList(String proudct_id,String start_date,String end_date){
		//进货单
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
	 * 取客户采购汇总列表信息（进货单）
	 * @param start_date
	 * @param end_date
	 * @param dj_id
	 * @param client_name
	 * @return
	 */
	public List getClientCgList(String start_date,String end_date,String dj_id,String client_name){
		
		//进货单未入库
		String jhdw_sql = "select m.gysbh as client_id,n.name as client_name,sum(m.total) as je,'未入库' as state,n.lxr,n.lxdh  "+
		                 "from jhd m left join clients n on n.id=m.gysbh where m.state!='已入库'  and m.th_flag='0' ";
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
		
		
       //进货单已入库
		String jhd_sql = "select m.gysbh as client_id,n.name as client_name,sum(m.total) as je,'已入库' as state,n.lxr,n.lxdh  "+
		                 "from jhd m left join clients n on n.id=m.gysbh where m.state='已入库' and m.th_flag='0' ";
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
	 * 客记采购汇总单据列表(采购单、采购退货单)
	 * @param start_date
	 * @param end_date
	 * @param dj_id
	 * @param client_name
	 * @return
	 */
	public List getClientMxList(String start_date,String end_date,String dj_id,String client_name){
		
		//进货单
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
	 * 取单据商品明细
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
