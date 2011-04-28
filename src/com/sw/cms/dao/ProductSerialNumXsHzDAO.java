package com.sw.cms.dao;

import java.util.List;

import com.sw.cms.dao.base.JdbcBaseDAO;

/**
 * 商品序列号销售汇总
 * @author liyt
 *
 */
public class ProductSerialNumXsHzDAO extends JdbcBaseDAO {
	
	/**
	 * 根据查询条件汇总商吕销售序列号
	 * @param start_date  开始时间
	 * @param end_date    结束时间
	 * @param product_kind 商品类别
	 * @param product_name 商品名称
	 * @param dept_id      部门编号
	 * @param xsry_id      销售人员编号
	 * @param clientId 客户名称
	 * @return
	 */
	public List getSerialNumXsList(String start_date,String end_date,String product_kind,
								   String product_name,String dept_id,String xsry_name,String clientId){
	
		String sql = "SELECT a.fs_date,a.serial_num,b.product_name,b.product_xh,a.client_name,a.tel,a.jsr,a.xsdj " +
					 "FROM  serial_num_flow a inner join serial_num_mng b on b.serial_num=a.serial_num " +
					 "left join product c on c.product_id=b.product_id where (a.ywtype='零售' or a.ywtype='销售')";
		
		if(!start_date.equals("")){
			sql += " and a.fs_date>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			sql += " and a.fs_date<='" + (end_date + " 23:59:59") + "'";
		}
		if(!product_kind.equals("")){
			sql += " and c.product_kind like '" + product_kind + "%'";
		}
		if(!product_name.equals("")){
			sql += " and b.product_name like '%" + product_name + "%'";
		}
		if(!xsry_name.equals("")){
			sql += " and a.jsr='" + xsry_name + "'";
		}
		if(!clientId.equals("")){
			sql += " and a.client_name='" + clientId + "'";
		}
		
		sql += " order by a.fs_date";
		
		return this.getResultList(sql);
	}

}
