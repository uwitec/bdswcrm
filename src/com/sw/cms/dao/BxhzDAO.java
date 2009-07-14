package com.sw.cms.dao;

import java.util.List;

import com.sw.cms.dao.base.JdbcBaseDAO;

public class BxhzDAO extends JdbcBaseDAO 
{
	 public List getHpbxList(String start_date, String end_date,String client_name,String lxr,String product_name,String gcs) 
     {
		 String sql="select b.id,b.lxr,p.product_name,b.jxdate,b.jddate,b.gcs,b.jxr,b.client_name,p.product_xh from bxd b left join bxd_product p on b.id=p.bxd_id where 1=1";
		 
		 if(!start_date.equals(""))
		 {
			 sql+=" and b.jxdate>='"+start_date+"'";
		 }
		 if(!end_date.equals(""))
		 {
			 sql+=" and b.jxdate<='"+end_date+"'";
		 }
		 if(!client_name.equals(""))
		 {
			 sql+=" and b.client_name='"+client_name+"'";
		 }
		 if(!lxr.equals(""))
		 {
			 sql+=" and b.lxr like '%"+lxr+"%'";
		 }
		 if(!product_name.equals(""))
		 {
			 sql+=" and p.product_name like '%"+product_name+"%'";
		 }
		
		 if(!gcs.equals(""))
		 {
			 sql+=" and b.gcs='"+gcs+"'";
		 }
		 sql+=" order by b.jxdate";
		
    	return this.getResultList(sql);
     }
}
