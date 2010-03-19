package com.sw.cms.dao;

import java.util.List;

import com.sw.cms.dao.base.JdbcBaseDAO;

/**
 * 费用统计报表
 * @author liyt
 *
 */
public class FytjReportDAO extends JdbcBaseDAO {
	
	/**
	 * 费用统计分类汇总
	 * @param start_date  开始时间
	 * @param end_date    结束时间
	 * @param dept        部门
	 * @param dj          费用类型等级
	 * @return
	 */
	public List getFytjResult(String start_date,String end_date,String dept,int dj){
		
		String con = " and b.state='已提交'";
		if(!start_date.equals("")){
			con += " and b.zc_date>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			con += " and b.zc_date<='" + (end_date + " 23:59:59") + "'";
		}
		if(!dept.equals("")){
			con += " and b.ywy_dept like '" + dept + "%'";
		}
		
		String sql = "select a.id,a.name," +
		"(select sum(zcje) from qtzc b where b.type like concat(a.id,'%') " + con + ") as hjje" +
		" from fy_type a where LENGTH(id)<=" + (dj*2);
		
		return this.getResultList(sql);
	}
	
	
	/**
	 * 部门费用汇总
	 * @param start_date  开始时间
	 * @param end_date    结束时间
	 * @param fy_type     费用类型
	 * @param dj          部门等级
	 * @return
	 */
	public List getDeptFytjResult(String start_date,String end_date,String fy_type,int dj){
		
		String con = " and b.state='已提交'";
		if(!start_date.equals("")){
			con += " and b.zc_date>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			con += " and b.zc_date<='" + (end_date + " 23:59:59") + "'";
		}
		if(!fy_type.equals("")){
			con += " and b.type like '" + fy_type + "%'";
		}
		
		String sql = "select a.dept_id,a.dept_name," +
		"(select sum(zcje) from qtzc b where b.ywy_dept like concat(a.dept_id,'%') " + con + ") as hjje" +
		" from dept a where LENGTH(a.dept_id)<=" + (dj*2);
		
		return this.getResultList(sql);
	}
	
	
	/**
	 * 费用汇总明细信息
	 * @param start_date  开始时间
	 * @param end_date    结束时间
	 * @param dept        部门编号
	 * @param fy_type     费用类型
	 * @return
	 */
	public List getFytjList(String start_date,String end_date,String dept,String fy_type){
		String sql = "select a.id,a.zc_date,a.type,a.zcje,a.zczh,a.sqr,a.ywy_dept from qtzc a left join sys_user b on b.user_id=a.ywy where a.state='已提交'";
		
		if(!start_date.equals("")){
			sql += " and a.zc_date>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			sql += " and a.zc_date<='" + (end_date + " 23:59:59") + "'";
		}
		if(!dept.equals("")){
			sql += " and a.ywy_dept like '" + dept + "%'";
		}
		if(!fy_type.equals("")){
			sql += " and a.type like'" + fy_type + "%'";
		}
		return this.getResultList(sql);
	}

}
