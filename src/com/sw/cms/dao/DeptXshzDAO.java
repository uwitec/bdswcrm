package com.sw.cms.dao;

import java.util.List;

import com.sw.cms.dao.base.JdbcBaseDAO;

/**
 * 部门销售汇总
 * @author liyt
 *
 */
public class DeptXshzDAO extends JdbcBaseDAO {
	
	/**
	 * 部门销售汇总列表(部门汇总表)
	 * @param start_date    开始时间
	 * @param end_date      结束时间
	 * @param client_name   客户编号（前台选择，行业客户、分销商等）
	 * @param dj  统计部门等级
	 * @return
	 * @throws Exception
	 */
	public List getResults(String start_date,String end_date,String client_name,int dj) throws Exception{
	
		String con = "";
		if(!start_date.equals("")){
			con += " and b.cz_date>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			con += " and b.cz_date<='" + end_date + "'"; 
		}
		if(!client_name.equals("")){
			con += " and b.client_name='" + client_name + "'"; 
		}
		
		String sql = "select a.dept_id,a.dept_name," +
				"(select sum(b.nums) from view_hpxshz_tj b where b.dept like concat(a.dept_id,'%') " + con + ") as nums," +
				"(select sum(b.hjje) from view_hpxshz_tj b where b.dept like concat(a.dept_id,'%') " + con + ") as hjje " +
				"from dept a where LENGTH(a.dept_id)<=" + (dj*2);
		
		return this.getResultList(sql);
	}
	
	
	
	/**
	 * 部门销售汇总--销售人员汇总表
	 * @param dept        部门编号
	 * @param start_date  起始时间
	 * @param end_date    结束时间
	 * @param client_name 客户编号
	 * @return
	 * @throws Exception
	 */
	public List getMxResults(String dept,String start_date,String end_date,String client_name) throws Exception{
		String sql = "select xsry,real_name,sum(nums) as nums,sum(hjje) as hjje FROM view_hpxshz_tj where 1=1";
		
		if(!dept.equals("")){
			sql += " and dept like '" + dept + "%'";
		}
		if(!start_date.equals("")){
			sql += " and cz_date>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			sql += " and cz_date<='" + end_date + "'";
		}
		if(!client_name.equals("")){
			sql += " and client_name='" + client_name + "'";
		}
		
		sql += " group by xsry,real_name";
		
		return this.getResultList(sql);
	}
	
	
	/**
	 * 部门销售汇总--销售人员销售明细
	 * @param xsry
	 * @param start_date
	 * @param end_date
	 * @param client_name
	 * @return
	 * @throws Exception
	 */
	public List getProductMxResults(String xsry,String start_date,String end_date,String client_name) throws Exception{
		String sql = "select product_id,product_name,product_xh,sum(nums) as nums,sum(hjje) as hjje from view_hpxshz_tj where 1=1";
		
		if(!xsry.equals("")){
			sql += " and xsry='" + xsry + "'";
		}
		if(!start_date.equals("")){
			sql += " and cz_date>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			sql += " and cz_date<='" + end_date + "'";
		}
		if(!client_name.equals("")){
			sql += " client_name='" + client_name + "'";
		}
		
		sql += " group by product_id,product_name,product_xh";
		
		return this.getResultList(sql);
	}

}
