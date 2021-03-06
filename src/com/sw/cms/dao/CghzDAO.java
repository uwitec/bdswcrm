package com.sw.cms.dao;

import java.util.List;

import com.sw.cms.dao.base.JdbcBaseDAO;

/**
 * 采购汇总包括按客户汇和按货品汇总两种
 * @author liyt
 *
 */

public class CghzDAO extends JdbcBaseDAO {

	
	/**
	 * 取货品采购汇总
	 * @param product_kind
	 * @param start_date
	 * @param end_date
	 * @param client_name
	 * @param product_name
	 * @param product_xh
	 * @param cgry_id
	 * @param product_prop
	 * @return
	 */
	public List getHpcgList(String product_kind,String start_date,String end_date,String client_name,String product_name,String product_xh,String cgry_id,String product_prop){
		
		//进货单
		String jhd_sql = "select a.product_id,c.sp_txm,c.product_name,c.product_xh,sum(nums) as nums,sum(hsje) as je,sum(sje) as sje,sum(bhsje) as bhsje from jhd_product a join jhd b on b.id=a.jhd_id left join product c on c.product_id=a.product_id where b.state='已入库' ";
		if(product_prop.equals("1"))
		{
			jhd_sql += " and c.prop='库存商品'";
		}
		else if(product_prop.equals("2"))
		{
			jhd_sql += " and c.prop='服务/劳务'";
		}
		   
		   
		//处理商品类别
		if(!product_kind.equals("")){
			String[] arryItems = product_kind.split(",");
			
			if(arryItems != null && arryItems.length >0){
				jhd_sql += " and (";
				for(int i=0;i<arryItems.length;i++){
					if(i == 0){
						jhd_sql += " c.product_kind like '" + arryItems[i] + "%'";
					}else{
						jhd_sql += " or c.product_kind like '" + arryItems[i] + "%'";
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
		if(!cgry_id.equals("")){
			jhd_sql += " and b.fzr ='" + cgry_id + "'";
		}
		
		jhd_sql += " group by a.product_id,c.product_name,c.product_xh";
		
		//采购退货单
		String cgthd_sql = "select a.product_id,c.sp_txm,c.product_name,c.product_xh,(0-sum(nums)) as nums,(0-sum(th_price*nums)) as je,0 as sje,(0-sum(th_price*nums)) as bhsje from cgthd_product a join cgthd b on b.id=a.cgthd_id left join product c on c.product_id=a.product_id where b.state='已出库' ";
		if(product_prop.equals("1"))
		{
			cgthd_sql += " and c.prop='库存商品'";
		}
		else if(product_prop.equals("2"))
		{
			cgthd_sql += " and c.prop='服务/劳务'";
		}
		
		//处理商品类别
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
		if(!cgry_id.equals("")){
			cgthd_sql += " and b.jsr ='" + cgry_id + "'";
		}
		
		cgthd_sql += " group by a.product_id,c.product_name,c.product_xh";
		
		String sql = "select product_id,sp_txm,product_name,product_xh,sum(nums) as nums,sum(je) as je,sum(sje) as sje,sum(bhsje) as bhsje from ((" + jhd_sql + ") union all (" + cgthd_sql + ")) x group by product_id,sp_txm,product_name,product_xh";
		
		return this.getResultList(sql);
	}
	
	
	/**
	 * 取货品采购汇总单据明细（进货单、采购退货单）
	 * @param proudct_id
	 * @param start_date
	 * @param end_date
	 * @param client_name
	 * @param cgry_id
	 * @param product_prop
	 * @return
	 */
	public List getDjmxList(String proudct_id,String start_date,String end_date,String client_name,String cgry_id,String product_prop){
		//进货单
		String jhd_sql = "select b.id as dj_id,'采购' as xwtype,b.gysbh as client_name,b.fzr as jsr,DATE_FORMAT(b.cz_date,'%Y-%m-%d') as creatdate,'viewJhd.html?id=' as url,b.cz_date from jhd_product a left join jhd b on b.id=a.jhd_id left join product c on c.product_id=a.product_id where b.state='已入库'";
		if(product_prop.equals("1"))
		{
			jhd_sql += " and c.prop='库存商品'";
		}
		else if(product_prop.equals("2"))
		{
			jhd_sql += " and c.prop='服务/劳务'";
		}
		
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
		if(!cgry_id.equals("")){
			jhd_sql += " and b.fzr ='" + cgry_id + "'";
		}
		//采购退货单
		String cgthd_sql = "select b.id as dj_id,'退货' as xwtype,b.provider_name as client_name,b.jsr,DATE_FORMAT(b.cz_date,'%Y-%m-%d') as creatdate,'viewCgthd.html?id=' as url,b.cz_date from cgthd_product a left join cgthd b on b.id=a.cgthd_id left join product c on c.product_id=a.product_id where b.state='已出库'";
		if(product_prop.equals("1"))
		{
			cgthd_sql += " and c.prop='库存商品'";
		}
		else if(product_prop.equals("2"))
		{
			cgthd_sql += " and c.prop='服务/劳务'";
		}
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
		if(!cgry_id.equals("")){
			cgthd_sql += " and b.jsr ='" + cgry_id + "'";
		}
		String sql = "select * from ((" + jhd_sql + ") union (" + cgthd_sql + ")) x order by cz_date asc";
		
		return this.getResultList(sql);
	}
	
	
	/**
	 * 取客户采购汇总列表信息（进货单、采购退货单）
	 * @param start_date
	 * @param end_date
	 * @param dj_id
	 * @param client_name
	 * @return
	 */
	public List getClientCgList(String start_date,String end_date,String dj_id,String client_name){
		
		//进货单
		String jhd_sql = "select gysbh as client_id,sum(total) as je,sum(hjsje) as hjsje,sum(hjbhsje) as hjbhsje  from jhd where state='已入库'";
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

		
		//采购退货单
		String cgthd_sql = "select provider_name as client_id,(0-sum(tkzje)) as je,0 as hjsje,(0-sum(tkzje)) as hjbhsje from cgthd where state='已出库'";
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

		
		String sql = "select client_id,sum(je) as je,sum(hjsje) as hjsje,sum(hjbhsje) as hjbhsje from ((" + jhd_sql + ") union (" + cgthd_sql + ")) x group by x.client_id";
		
		sql = "select m.client_id,n.name as client_name,n.lxr,n.lxdh,m.je,m.hjsje,m.hjbhsje from (" + sql + ") m left join clients n on n.id=m.client_id where 1=1";
		if(!client_name.equals("")){
			sql = sql + " and n.name like '%" + client_name + "%'";
		}
		
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
		String jhd_sql = "select id as dj_id,'采购' as xwtype,gysbh as client_name,fzr as jsr,DATE_FORMAT(cz_date,'%Y-%m-%d') as creatdate,total as je,hjsje,hjbhsje,'viewJhd.html?id=' as url,cz_date from jhd where state='已入库'";
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

		
		//采购退货单
		String cgthd_sql = "select id as dj_id,'采购退货' as xwtype,provider_name as client_name,jsr,DATE_FORMAT(cz_date,'%Y-%m-%d') as creatdate,(0-tkzje) as je,0 as hjsje,(0-tkzje) as hjbhsje, 'viewCgthd.html?id=' as url,cz_date from cgthd where state='已出库'";
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
	 * 取单据商品明细
	 * @param dj_id
	 * @param xwtype
	 * @return
	 */
	public List getDjmxList(String dj_id,String xwtype){
		String sql ="";
		
		if(xwtype.equals("采购")){
			sql = "select product_id,product_name,product_xh,price,nums,hsje,sje,bhsje,sd from jhd_product where jhd_id='" + dj_id + "'";
		}else{
			sql = "select product_id,product_name,product_xh,(0-nums) as nums,(0-xj) as hsje,th_price as price,0 as sd,0 as sje,(0-xj) as bhsje from cgthd_product where cgthd_id='" + dj_id + "'";
		}
		
		return this.getResultList(sql);
	}
	
}
