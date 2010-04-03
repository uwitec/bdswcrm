package com.sw.cms.dao;

import java.util.List;

import com.sw.cms.dao.base.JdbcBaseDAO;

public class KlHzReportDAO extends JdbcBaseDAO {
	
	
	
	
	/**
	 * 查询库龄汇总
	 * @param product_kind 商品种类	           
	 * @param product_name商品名称	             
	 * @param store_id商品库房	          
	 * @param kl_day库龄（天）	            
	 * @return
	 */
	public List getKlResults(String product_kind, String product_name,
			String store_id, String kl_day)
	{		
		String sql="select  mng.serial_num,mng.product_id,mng.product_name,mng.product_xh,datediff(curdate(),flow.fs_date) as klday,p.khcbj,p.lsbj,p.lsxj,p.fxxj,p.fxbj,p.dss,p.gf from serial_num_flow as flow right join serial_num_mng as mng on mng.serial_num=flow.serial_num left join product as p on mng.product_id=p.product_id left join product_kind kind on kind.id=p.product_kind where mng.state='在库' and flow.ywtype='采购'";
	
		if (!store_id.equals("")) {
			sql += " and mng.store_id='" + store_id + "'";
		}
		
		if (!product_kind.equals("")) {
			String[] arryItems = product_kind.split(",");

			if (arryItems != null && arryItems.length > 0) {
				 
				for (int i = 0; i < arryItems.length; i++) {
					System.out.println(arryItems[i]);
					if (i == 0) {
						sql += " and p.product_kind like '" + arryItems[i] + "%'";
					} else {
						sql += " or p.product_kind like '" + arryItems[i]+ "%'";
								
					}
				}
				 
			}

		}
		if (!product_name.equals("")) {
			sql += " and p.product_name like '%" + product_name + "%'";
		}
		if (!kl_day.equals("")) {
			sql += " and datediff(curdate(),flow.fs_date)>="+kl_day+"";
		}
		return this.getResultList(sql);
	}
}
