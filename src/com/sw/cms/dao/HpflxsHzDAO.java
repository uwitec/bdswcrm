package com.sw.cms.dao;

import java.util.List;

import com.sw.cms.dao.base.JdbcBaseDAO;

/**
 * 货品分类销售汇总
 * @author liyt
 *
 */
public class HpflxsHzDAO extends JdbcBaseDAO {

	
	/**
	 * 按货品类别等级统计销售类别销售
	 * @param start_date
	 * @param end_date
	 * @param dj
	 * @return
	 */
	public List getHzResults(String start_date,String end_date,String xsry,String client_name,int dj){
		
		String con = "";
		if(!start_date.equals("")){
			con += " and b.cz_date>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			con += " and b.cz_date<='" + end_date + "'"; 
		}
		if(!xsry.equals("")){
			con += " and b.xsry='" + xsry + "'";
		}
		if(!client_name.equals("")){
			con += " and b.client_name='" + client_name + "'"; 
		}
		
		String sql = "select a.id,a.name," +
				"(select sum(nums) from view_hpxshz_tj b where b.product_id like concat(a.id,'%') " + con + ") as nums," +
				"(select sum(hjje) from view_hpxshz_tj b where b.product_id like concat(a.id,'%') " + con + ") as hjje" +
				" from product_kind a where LENGTH(id)<=" + (dj*2);
		
		List list = this.getResultList(sql);
		
		return list;
	}
	
	
	
	/**
	 * 根据货品销售分类汇总明细信息
	 * @param product_kind   商品类别
	 * @param start_date     开始时间
	 * @param end_date       结束时间
	 * @param client_name    客户名称
	 * @param xsry_id        销售人员
	 * @return
	 */
	public List getMxResults(String product_kind,String start_date,String end_date,String client_name,String xsry){
		
		String sql = "select product_id,product_name,product_xh,sum(nums) as nums,sum(hjje) as hjje from view_hpxshz_tj where 1=1";
		
		//处理商品类别
		if(!product_kind.equals("")){
			sql += " and product_id like '" + product_kind + "%'";
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
		if(!xsry.equals("")){
			sql += " and xsry='" + xsry + "'";
		}
		
		sql += " group by product_id,product_name,product_xh";
		
		return this.getResultList(sql);
	}
	
}
