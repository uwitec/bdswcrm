package com.sw.cms.dao;

import java.util.List;

import com.sw.cms.dao.base.JdbcBaseDAO;

/**
 * 工分统计
 * @author liyt
 *
 */
public class GfReportDAO extends JdbcBaseDAO {
	
	/**
	 * 根据统计条件统计员工工分
	 * @param start_date
	 * @param end_date
	 * @param dept_id
	 * @param xsry_id
	 * @return
	 */
	public List getGfReportByCon(String start_date,String end_date,String dept_id,String xsry_id,String flag){
		String sql = "select b.xsry as xsry_id,c.real_name as xsry_name,sum(a.nums * a.gf) as gfhj from lsd_product a left join lsd b on b.id=a.lsd_id left join sys_user c on c.user_id=b.xsry where b.state='已提交'";
		
		if(!start_date.equals("")){
			sql += " and DATE_FORMAT(b.cz_date,'%Y-%m-%d')>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			sql += " and DATE_FORMAT(b.cz_date,'%Y-%m-%d')<='" + (end_date + " 23:59:59") + "'";
		}
		if(!dept_id.equals("")){
			sql += " and c.dept like '" + dept_id + "%'";
		}
		if(!xsry_id.equals("")){
			sql += " and b.xsry='" + xsry_id + "'";
		}
		
		sql += " group by b.xsry,c.real_name";
		
		if(flag.equals("否")){
			sql += " having gfhj>0";
		}
		
		return this.getResultList(sql);
	}
	
	
	/**
	 * 获取工分统计明细
	 * @param start_date
	 * @param end_date
	 * @param xsry_id
	 * @return
	 */
	public List getMxList(String start_date,String end_date,String xsry_id){
		String sql = "select b.id,b.client_name,creatdate,sum(a.nums * a.gf) as gfhj  from lsd_product a left join lsd b on b.id=a.lsd_id where b.state='已提交'";
		
		if(!start_date.equals("")){
			sql += " and DATE_FORMAT(b.cz_date,'%Y-%m-%d')>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			sql += " and DATE_FORMAT(b.cz_date,'%Y-%m-%d')<='" + (end_date + " 23:59:59") + "'";
		}
		if(!xsry_id.equals("")){
			sql += " and b.xsry='" + xsry_id + "'";
		}
		sql += " group by b.id,b.client_name,creatdate";
		
		return this.getResultList(sql);
	}

}
