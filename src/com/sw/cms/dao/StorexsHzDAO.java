package com.sw.cms.dao;

import java.util.List;

import com.sw.cms.dao.base.JdbcBaseDAO;

public class StorexsHzDAO extends JdbcBaseDAO {
	
	
	/**
	 * 根据统计条件统计仓库销售汇总表
	 * @param product_kind   商品类别
	 * @param product_name   商品名称
	 * @param product_xh     商品规格
	 * @param start_date     开始时间
	 * @param end_date       结束时间
	 * @param store_id    仓库名称
	 * @return
	 */
	public List getStorexshzTjResult(String product_kind,String product_name,String product_xh,
			String start_date,String end_date,String store_id){
		
		String sql = "select a.store_id,sum(a.nums) as nums,sum(a.hjje) as hjje from product_sale_flow a left join product b on b.product_id=a.product_id where 1=1";
		
		//处理商品类别
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
		
		//商品名称
		if(!product_name.equals("")){
			sql += " and b.product_name like '%" + product_name + "%'";
		}
		
		//商品规格
		if(!product_name.equals("")){
			sql += " and b.product_xh like '%" + product_xh + "%'";
		}
		
		//开始时间
		if(!start_date.equals("")){
			sql += " and a.cz_date>='" + start_date + "'";
		}
		
		//结束时间
		if(!end_date.equals("")){
			sql += " and a.cz_date<='" + end_date + "'";
		}
		
		
		//仓库编号
		if(!store_id.equals("")){
			sql += " and a.store_id='" + store_id + "'";
		}
		
		sql += " group by a.store_id";
		
		return this.getResultList(sql);
	}
	
		
	/**
	 * 仓库销售汇总--明细表
	 * @param dept        部门编号
	 * @param start_date  起始时间
	 * @param end_date    结束时间
	 * @param store_id 仓库编号
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
		
		//处理商品类别
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
		
		//商品名称
		if(!product_name.equals("")){
			sql += " and c.product_name like '%" + product_name + "%'";
		}
		
		sql += " group by a.xsry,b.real_name";
		
		return this.getResultList(sql);
	}
	
	/**
	 * 仓库销售汇总--销售人员销售明细
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
		
		//处理商品类别
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
		
		//商品名称
		if(!product_name.equals("")){
			sql += " and c.product_name like '%" + product_name + "%'";
		}
		
		sql += " group by a.product_id,c.product_name,c.product_xh";
		
		return this.getResultList(sql);
	}
}
