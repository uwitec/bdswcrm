package com.sw.cms.dao;

import java.util.List;

import com.sw.cms.dao.base.JdbcBaseDAO;

public class HpxsMlHzDAO extends JdbcBaseDAO {
	
	/**
	 * 商品列表信息
	 * @param productKind 商品分类编号
	 * @return   商品列表
	 */
	public List getProductList(String productKind){
		String sql = "select * from product where product_kind like'" + productKind + "%' or product_id='"+ productKind +"'";
		return this.getResultList(sql);
	}
	
	
	/**
	 * 根据统计条件统计货品毛利销售汇总表
	 * @param product_kind   商品类别
	 * @param product_name   商品名称
	 * @param product_xh     商品规格
	 * @param start_date     开始时间
	 * @param end_date       结束时间
	 * @param client_name    客户名称
	 * @param xsry_id        销售人员
	 * @return
	 */
	public List getHpxsmlhzTjResult(String product_kind,String product_name,String product_xh,
			String start_date,String end_date,String client_name,String xsry_id){
		
		String sql = "select product_id,product_name,product_xh,prop,sum(nums) as nums,sum(hjje) as hjje,sum(cb) as hjcb from view_hpxshz_tj where 1=1";
		
		//处理商品类别
		if(!product_kind.equals("")){
			String[] arryItems = product_kind.split(",");			
			if(arryItems != null && arryItems.length >0){
				sql += " and (";
				for(int i=0;i<arryItems.length;i++){
					if(i == 0){
						sql += " product_id like '" + arryItems[i] + "%'";
					}else{
						sql += " or product_id like '" + arryItems[i] + "%'";
					}
				}
				sql += ")";
			}
		}
		
		//商品名称
		if(!product_name.equals("")){
			sql += " and product_name like '%" + product_name + "%'";
		}
		
		//商品规格
		if(!product_name.equals("")){
			sql += " and product_xh like '%" + product_xh + "%'";
		}
		
		//开始时间
		if(!start_date.equals("")){
			sql += " and cz_date>='" + start_date + "'";
		}
		
		//结束时间
		if(!end_date.equals("")){
			sql += " and cz_date<='" + end_date + "'";
		}
		
		//客户编号
		if(!client_name.equals("")){
			sql += " and client_name='" + client_name + "'";
		}
		
		//销售人员
		if(!xsry_id.equals("")){
			sql += " and xsry='" + xsry_id + "'";
		}
		
		sql += " group by product_id,product_name,product_xh,prop";
		
		return this.getResultList(sql);
	}
	
	
	/**
	 * 货品销售毛利汇总列表
	 * 包括销售单、零售单、退货单
	 * @param product_id
	 * @param start_date
	 * @param end_date
	 * @param client_name
	 * @param xsry_id
	 * @return
	 */
	public List getHpxsList(String product_id,String start_date,String end_date,String client_name,String xsry_id){
		String sql = "";
		
		//销售单
		String xsd_sql = "select DATE_FORMAT(b.cz_date,'%Y-%m-%d') as creatdate,a.xsd_id as dj_id,'销售' as xwtype,b.client_name,a.product_id,a.product_name,a.product_xh,a.sjcj_nums as nums,(a.price+a.jgtz) as price,a.cbj as cb,b.cz_date,'viewXsd.html?id=' as url from xsd_product a left join xsd b on b.id=a.xsd_id where b.state='已出库'";
		if(!product_id.equals("")){
			xsd_sql = xsd_sql + " and a.product_id='" + product_id + "'";
		}
		if(!start_date.equals("")){
			xsd_sql = xsd_sql + " and DATE_FORMAT(b.cz_date,'%Y-%m-%d')>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			xsd_sql = xsd_sql + " and DATE_FORMAT(b.cz_date,'%Y-%m-%d')<='" + (end_date + " 23:59:59") + "'";
		}
		if(!client_name.equals("")){
			xsd_sql = xsd_sql + " and b.client_name='" + client_name + "'";
		}
		if(!xsry_id.equals("")){
			xsd_sql = xsd_sql + " and b.fzr='" + xsry_id + "'";
		}	
		
		//零售单
		String lsd_sql = "select DATE_FORMAT(b.cz_date,'%Y-%m-%d') as creatdate,b.id as dj_id,'零售' as xwtype,b.client_name,a.product_id,a.product_name,a.product_xh,a.nums,a.price,a.cbj as cb,b.cz_date,'viewLsd.html?id=' as url from lsd_product a left join lsd b on b.id=a.lsd_id where b.state='已提交'";
		if(!product_id.equals("")){
			lsd_sql = lsd_sql + " and a.product_id='" + product_id + "'";
		}
		if(!start_date.equals("")){
			lsd_sql = lsd_sql + " and DATE_FORMAT(b.cz_date,'%Y-%m-%d')>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			lsd_sql = lsd_sql + " and DATE_FORMAT(b.cz_date,'%Y-%m-%d')<='" + (end_date + " 23:59:59") + "'";
		}
		if(!xsry_id.equals("")){
			lsd_sql = lsd_sql + " and b.xsry='" + xsry_id + "'";
		}
		
		
		//退货单
		String thd_sql = "select DATE_FORMAT(b.cz_date,'%Y-%m-%d') as creatdate,b.thd_id as dj_id,'退货' as xwtype,b.client_name,a.product_id,a.product_name,a.product_xh,(0-a.nums) as nums,(0-a.th_price) as price,(0-a.cbj) as cb,b.cz_date,'viewThd.html?thd_id=' as url from thd_product a left join thd b on b.thd_id=a.thd_id where b.state<>'已保存'";
		if(!product_id.equals("")){
			thd_sql = thd_sql + " and a.product_id='" + product_id + "'";
		}
		if(!start_date.equals("")){
			thd_sql = thd_sql + " and DATE_FORMAT(b.cz_date,'%Y-%m-%d')>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			thd_sql = thd_sql + " and DATE_FORMAT(b.cz_date,'%Y-%m-%d')<='" + (end_date + " 23:59:59") + "'";
		}
		if(!client_name.equals("")){
			thd_sql = thd_sql + " and b.client_name='" + client_name + "'";
		}
		if(!xsry_id.equals("")){
			thd_sql = thd_sql + " and b.th_fzr='" + xsry_id + "'";
		}
		
		if(client_name.equals("")){ //不选择客户时统计包括零售单
			sql = "select * from ((" + xsd_sql + ") union (" + lsd_sql + ") union (" + thd_sql + ")) x order by cz_date asc";
		}else{ //不包括零售单
			sql = "select * from ((" + xsd_sql + ") union (" + thd_sql + ")) x order by cz_date asc";
		}
		
		return this.getResultList(sql);
	}

}
