package com.sw.cms.dao;

/**
 * 销售明细统计
 */

import java.util.List;

import com.sw.cms.dao.base.JdbcBaseDAO;

public class XsmxReportDAO extends JdbcBaseDAO {
	
	/**
	 * 取销售单列表
	 * @param start_date 开始时间
	 * @param end_date结束时间
	 * @param client_name客户名称
	 * @param xsry_id销售人员
	 * @param dj_id单据编号
	 * @return
	 */
	public List getXsdList(String start_date,String end_date,String client_name,String xsry_id,String dj_id,String dept_id){
		String sql = "select DATE_FORMAT(a.cz_date,'%Y-%m-%d') as creatdate,a.id,b.name as client_name,a.fzr,a.sjcjje as xsdje " +
				     "from xsd a left join clients b on b.id=a.client_name left join sys_user c on c.user_id=a.fzr where a.state='已出库'";
		if(!dj_id.equals("")){
			sql = sql + " and a.id='" + dj_id + "'";
		}		
		if(!start_date.equals("")){
			sql = sql + " and DATE_FORMAT(a.cz_date,'%Y-%m-%d')>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			sql = sql + " and DATE_FORMAT(a.cz_date,'%Y-%m-%d')<='" + (end_date + " 23:59:59") + "'";
		}
		if(!client_name.equals("")){
			sql = sql + " and b.name like'%" + client_name + "%'";
		}
		if(!xsry_id.equals("")){
			sql = sql + " and a.fzr='" + xsry_id + "'";
		}
		if(!dept_id.equals("")){
			sql = sql + " and c.dept like '" + dept_id + "%'";
		}

		return this.getResultList(sql);
	}
	
	
	
	/**
	 * 根据销售单编号取销售明细信息
	 * @param xsd_id
	 * @return
	 */
	public List getXsdMxList(String xsd_id){
		String sql = "select * from xsd_product where xsd_id='" + xsd_id + "'";
		
		return this.getResultList(sql);
	}
	
	
	/**
	 * 根据查询条件取零售单列表
	 * @param start_date 开始时间
	 * @param end_date  截止时间
	 * @param client_id  客户名称
	 * @return
	 */
	public List getLsdList(String start_date,String end_date,String client_name,String xsry_id,String dj_id,String dept_id){
		String sql = "select DATE_FORMAT(a.cz_date,'%Y-%m-%d') as creatdate,a.id,a.client_name,a.xsry,a.lsdje " +
				     "from lsd a left join sys_user b on user_id=a.xsry where a.state='已提交'";
		
		if(!dj_id.equals("")){
			sql = sql + " and a.id='" + dj_id + "'";
		}	
		if(!start_date.equals("")){
			sql = sql + " and DATE_FORMAT(a.cz_date,'%Y-%m-%d')>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			sql = sql + " and DATE_FORMAT(a.cz_date,'%Y-%m-%d')<='" + (end_date + " 23:59:59") + "'";
		}
		if(!client_name.equals("")){
			sql = sql + " and a.client_name like'%" + client_name + "%'";
		}
		if(!xsry_id.equals("")){
			sql = sql + " and a.xsry='" + xsry_id + "'";
		}
		if(!dept_id.equals("")){
			sql = sql + " and b.dept like '" + dept_id + "%'";
		}
		return this.getResultList(sql);
	}
	
	
	/**
	 * 根据零售单编号取零售单销售明细
	 * @param lsd_id
	 * @return
	 */
	public List getLsdMxList(String lsd_id){
		String sql = "select * from lsd_product where lsd_id='" + lsd_id + "'";
		return this.getResultList(sql);
	}
	
	
	/**
	 * 取退货单列表
	 * @param start_date
	 * @param end_date
	 * @param client_name
	 * @return
	 */
	public List getThdList(String start_date,String end_date,String client_name,String xsry_id,String dj_id,String dept_id){
		String sql = "select DATE_FORMAT(a.cz_date,'%Y-%m-%d') as th_date,a.thd_id,b.name as client_name,a.th_fzr,thdje " +
				     "from thd a left join clients b on b.id=a.client_name left join sys_user c on c.user_id=a.th_fzr where a.state='已入库'";
		if(!dj_id.equals("")){
			sql = sql + " and a.thd_id='" + dj_id + "'";
		}
		if(!start_date.equals("")){
			sql = sql + " and DATE_FORMAT(a.cz_date,'%Y-%m-%d')>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			sql = sql + " and DATE_FORMAT(a.cz_date,'%Y-%m-%d')<='" + (end_date + " 23:59:59") + "'";
		}
		if(!client_name.equals("")){
			sql = sql + " and b.name like'%" + client_name + "%'";
		}
		if(!xsry_id.equals("")){
			sql = sql + " and a.th_fzr='" + xsry_id + "'";
		}
		if(!dept_id.equals("")){
			sql = sql + " and c.dept like'" + dept_id + "%'";
		}
		return this.getResultList(sql);
	}
	
	
	/**
	 * 根据退货单编号取退货单明细
	 * @param thd_id
	 * @return
	 */
	public List getThdMxList(String thd_id){
		String sql = "select * from thd_product where thd_id='" + thd_id + "'";
		return this.getResultList(sql);
	}
	
	
	/**
	 * 取未收单据列表
	 * @param start_date
	 * @param end_date
	 * @param dept_id
	 * @param xsry_id
	 * @return
	 */
	public List getWsdjList(String start_date,String end_date,String dept_id,String xsry_id,String client_name){
		String sql = "select a.creatdate,a.ysrq,a.id,a.client_name,a.kh_lxr,a.kh_lxdh,a.fzr,a.xsdje,(a.xsdje-a.skje) as je from xsd a left join sys_user b on b.user_id=a.fzr where a.state='已出库' and a.skxs<>'已收'";
		if(!start_date.equals("")){
			sql += " and DATE_FORMAT(a.cz_date,'%Y-%m-%d')>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			sql += " and DATE_FORMAT(a.cz_date,'%Y-%m-%d')<='" + (end_date + " 23:59:59") + "'"; 
		}
		if(!dept_id.equals("")){
			sql += " and b.dept like '" + dept_id + "%'";
		}
		if(!xsry_id.equals("")){
			sql += " and a.fzr='" + xsry_id + "'";
		}
		if(!client_name.equals("")){
			sql += " and a.client_name='"+ client_name +"'";
		}
		
		return this.getResultList(sql);
	}

}
