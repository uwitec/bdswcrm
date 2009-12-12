package com.sw.cms.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sw.cms.dao.base.JdbcBaseDAO;

public class XstjXsryDAO extends JdbcBaseDAO {
	
	/**
	 * 取销售单列表
	 * @param start_date
	 * @param end_date
	 * @param xsry_id
	 * @return
	 */
	public List getXsdList(String start_date,String end_date,String xsry_id,String client_name,String dj_id){
		String sql = "select DATE_FORMAT(a.cz_date,'%Y-%m-%d') as creatdate,a.id,b.name as client_name,a.sjcjje from xsd a left join clients b on b.id=a.client_name where state='已出库'";
		if(!dj_id.equals("")){
			sql = sql + " and a.id='" + dj_id + "'";
		}	
		if(!start_date.equals("")){
			sql = sql + " and DATE_FORMAT(a.cz_date,'%Y-%m-%d')>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			sql = sql + " and DATE_FORMAT(a.cz_date,'%Y-%m-%d')<='" + (end_date + " 23:59:59") + "'";
		}
		if(!xsry_id.equals("")){
			sql = sql + " and a.fzr ='" + xsry_id + "'";
		}
		if(!client_name.equals("")){
			sql = sql + " and b.name like '%" + client_name + "%'";
		}
		
		return this.getResultList(sql);
	}
	
	
	/**
	 * 根据销售单编号取销售明细信息
	 * @param xsd_id
	 * @return
	 */
	public List getXsdMxList(String xsd_id){
		String sql = "select a.*,b.prop from xsd_product a left join product b on b.product_id=a.product_id where a.xsd_id='" + xsd_id + "'";
		
		return this.getResultList(sql);
	}
	
	
	/**
	 * 根据查询条件取零售单列表
	 * @param start_date 开始时间
	 * @param end_date  截止时间
	 * @param client_id  客户名称
	 * @return
	 */
	public List getLsdList(String start_date,String end_date,String xsry_id,String client_name,String dj_id){
		String sql = "select DATE_FORMAT(cz_date,'%Y-%m-%d') as creatdate,id,client_name,lsdje from lsd where state='已提交'";
		if(!dj_id.equals("")){
			sql = sql + " and id='" + dj_id + "'";
		}
		if(!start_date.equals("")){
			sql = sql + " and DATE_FORMAT(cz_date,'%Y-%m-%d')>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			sql = sql + " and DATE_FORMAT(cz_date,'%Y-%m-%d')<='" + (end_date + " 23:59:59") + "'";
		}
		if(!xsry_id.equals("")){
			sql = sql + " and xsry = '" + xsry_id + "'";
		}
		if(!client_name.equals("")){
			sql = sql + " and client_name like '%" + client_name + "%'";
		}		
		return this.getResultList(sql);
	}
	
	
	/**
	 * 根据零售单编号取零售单元销售明细
	 * @param lsd_id
	 * @return
	 */
	public List getLsdMxList(String lsd_id){
		String sql = "select a.*,b.prop from lsd_product a left join product b on b.product_id=a.product_id where a.lsd_id='" + lsd_id + "'";
		return this.getResultList(sql);
	}
	
	
	/**
	 * 取退货单列表
	 * @param start_date
	 * @param end_date
	 * @param client_name
	 * @return
	 */
	public List getThdList(String start_date,String end_date,String xsry_id,String client_name,String dj_id){
		String sql = "select DATE_FORMAT(a.cz_date,'%Y-%m-%d') as th_date,a.thd_id,b.name as client_name,a.thdje from thd a left join clients b on b.id=a.client_name where state='已入库'";
		if(!dj_id.equals("")){
			sql = sql + " and a.thd_id='" + dj_id + "'";
		}
		if(!start_date.equals("")){
			sql = sql + " and DATE_FORMAT(a.cz_date,'%Y-%m-%d')>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			sql = sql + " and DATE_FORMAT(a.cz_date,'%Y-%m-%d')<='" + (end_date + " 23:59:59") + "'";
		}
		if(!xsry_id.equals("")){
			sql = sql + " and a.th_fzr = '" + xsry_id + "'";
		}
		if(!client_name.equals("")){
			sql = sql + " and b.name like '%" + client_name + "%'";
		}		
		return this.getResultList(sql);
	}
	
	
	/**
	 * 根据退货单编号取退货单明细
	 * @param thd_id
	 * @return
	 */
	public List getThdMxList(String thd_id){
		String sql = "select a.*,b.prop from thd_product a left join product b on b.product_id=a.product_id where a.thd_id='" + thd_id + "'";
		return this.getResultList(sql);
	}
	
	
	
	/**
	 * 业务员毛利汇总(销售收入、成本、考核成本)
	 * 如果考核成本为0自动取最近的一次进货价格
	 * @param start_date
	 * @param end_date
	 * @return
	 */
	public HashMap<String,Double> getMlHz(String xsry_id,String start_date,String end_date){
		HashMap<String,Double> map = new HashMap<String,Double>();
		
		double xssr = 0;  //销售收入
		double cb = 0;    //成本
		double khcb = 0;  //考核成本
		
		//销售单
		String xsd_sql = "select (sum(a.sjcj_xj)) as xssr,(sum(a.cbj * a.sjcj_nums)) as cb,(sum(a.kh_cbj * a.sjcj_nums)) as khcb from xsd_product a left join xsd b on b.id=a.xsd_id where b.state='已出库'";
		if(!xsry_id.equals("")){
			xsd_sql = xsd_sql + " and b.fzr='" + xsry_id + "'";
		}
		if(!start_date.equals("")){
			xsd_sql = xsd_sql + " and DATE_FORMAT(b.cz_date,'%Y-%m-%d')>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			xsd_sql = xsd_sql + " and DATE_FORMAT(b.cz_date,'%Y-%m-%d')<='" + end_date + "'";
		}
		Map xsdMap = this.getResultMap(xsd_sql);
		if(xsdMap != null){
			xssr += xsdMap.get("xssr")==null?0:((Double)xsdMap.get("xssr")).doubleValue();
			cb += xsdMap.get("cb")==null?0:((Double)xsdMap.get("cb")).doubleValue();
			khcb += xsdMap.get("khcb")==null?0:((Double)xsdMap.get("khcb")).doubleValue();
		}
		
		//零售单
		String lsd_sql = "select (sum(a.xj)) as xssr,(sum(a.cbj * a.nums)) as cb,(sum(a.kh_cbj * a.nums)) as khcb from lsd_product a left join lsd b on b.id=a.lsd_id where b.state='已提交'";
		if(!xsry_id.equals("")){
			lsd_sql = lsd_sql + " and b.xsry='" + xsry_id + "'";
		}
		if(!start_date.equals("")){
			lsd_sql = lsd_sql + " and DATE_FORMAT(b.cz_date,'%Y-%m-%d')>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			lsd_sql = lsd_sql + " and DATE_FORMAT(b.cz_date,'%Y-%m-%d')<='" + end_date + "'";
		}
		Map lsdMap = this.getResultMap(lsd_sql);
		if(lsdMap != null){
			xssr += lsdMap.get("xssr")==null?0:((Double)lsdMap.get("xssr")).doubleValue();
			cb += lsdMap.get("cb")==null?0:((Double)lsdMap.get("cb")).doubleValue();
			khcb += lsdMap.get("khcb")==null?0:((Double)lsdMap.get("khcb")).doubleValue();
		}
		
		//退货单
		String thd_sql = "select (sum(a.xj)) as xssr,(sum(a.cbj * a.nums)) as cb,(sum(a.kh_cbj * a.nums)) as khcb from thd_product a left join thd b on b.thd_id=a.thd_id where b.state='已入库'";
		if(!xsry_id.equals("")){
			thd_sql = thd_sql + " and b.th_fzr='" + xsry_id + "'";
		}
		if(!start_date.equals("")){
			thd_sql = thd_sql + " and DATE_FORMAT(b.cz_date,'%Y-%m-%d')>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			thd_sql = thd_sql + " and DATE_FORMAT(b.cz_date,'%Y-%m-%d')<='" + end_date + "'";
		}
		Map thdMap = this.getResultMap(thd_sql);
		if(thdMap != null){
			xssr -= thdMap.get("xssr")==null?0:((Double)thdMap.get("xssr")).doubleValue();
			cb -= thdMap.get("cb")==null?0:((Double)thdMap.get("cb")).doubleValue();
			khcb -= thdMap.get("khcb")==null?0:((Double)thdMap.get("khcb")).doubleValue();
		}
		
		map.put("xssr", xssr);
		map.put("cb", cb);
		map.put("khcb", khcb);
		
		return map;
	}
	
	
	/**
	 * 取最近的一次进货价格做为考核成本
	 * @param product_id
	 * @return
	 */
	private double getLaterKhcb(String product_id){
		double khcb = 0;
		String sql = "select a.price from jhd_product a join jhd b on a.jhd_id=b.id where product_id='" + product_id + "' order by cg_date desc";
		Map map = this.getResultMap(sql);
		if(map != null){
			khcb = map.get("price")==null?0:((Double)map.get("price")).doubleValue();
		}
		return khcb;
	}
	
	
	
	//2009-10-10修改业务员考核毛利
	
	
	/**
	 * 业务员毛利汇总
	 * @param start_date   开始时间
	 * @param end_date     结束时间
	 * @param dept_id      部门编号
	 * @param user_id      销售人员
	 * @return
	 */
	public List getYwymlHz(String start_date,String end_date,String dept_id,String user_id){
		String sql = "select dept,xsry,real_name,sum(hjje) as xsje,sum(bhsje) as bhsje,sum(cb) as cb,sum(khcb) as khcb from view_hpxshz_tj where 1=1";
		
		if(!start_date.equals("")){
			sql += " and cz_date>='" + start_date + "'";
		}
		if(!start_date.equals("")){
			sql += " and cz_date<='" + end_date + "'";
		}
		if(!dept_id.equals("")){
			sql += " and dept like '" + dept_id + "%'";
		}
		if(!user_id.equals("")){
			sql += " and xsry='" + user_id + "'";
		}
		
		sql += " group by dept,xsry,real_name";
		return this.getResultList(sql);
	}
	
	
	/**
	 * 业务员毛利汇总明细
	 * @param start_date
	 * @param end_date
	 * @param user_id
	 * @return
	 */
	public List getYwymlMx(String start_date,String end_date,String user_id){
		String sql = "select cz_date,id,yw_type,client_name,product_name,product_id,product_xh,nums,price,hjje,bhsje,dwcb,cb,dwkhcb,khcb from view_hpxshz_tj where 1=1";
		
		if(!start_date.equals("")){
			sql += " and cz_date>='" + start_date + "'";
		}
		if(!start_date.equals("")){
			sql += " and cz_date<='" + end_date + "'";
		}
		if(!user_id.equals("")){
			sql += " and xsry='" + user_id + "'";
		}
		
		sql += " order by id";
		
		return this.getResultList(sql);
	}
	
	
	/**
	 * 业务员提成统计--汇总
	 * @param start_date
	 * @param end_date
	 * @param dept_id
	 * @param user_id
	 * @return
	 */
	public List getYwytcHz(String start_date,String end_date,String dept_id,String user_id){
		String sql = "select dept,xsry,real_name,sum((bhsje-khcb)*basic_ratio/100) as jbtc," +
				"sum((bhsje-khcb)*gf/100) as blds, sum(ds) as jeds," +
				"sum((bhsje-khcb)*out_ratio/100) as cxjl from view_hpxshz_tj where real_name<>''";
		
		if(!start_date.equals("")){
			sql += " and cz_date>='" + start_date + "'";
		}
		if(!start_date.equals("")){
			sql += " and cz_date<='" + end_date + "'";
		}
		if(!dept_id.equals("")){
			sql += " and dept like '" + dept_id + "%'";
		}
		if(!user_id.equals("")){
			sql += " and xsry='" + user_id + "'";
		}
		
		sql += " group by dept,xsry,real_name";
		return this.getResultList(sql);
	}
	
	
	/**
	 * 业务员提成统计--明细
	 * @param start_date
	 * @param end_date
	 * @param dept_id
	 * @param user_id
	 * @return
	 */
	public List getYwytcMx(String start_date,String end_date,String user_id){
		String sql = "select cz_date,id,yw_type,client_name,product_name,product_id,product_xh,nums," +
				"(bhsje-khcb)*basic_ratio/100 as jbtc,(bhsje-khcb)*gf/100 as blds,ds as jeds,(bhsje-khcb)*out_ratio/100 as cxjl from view_hpxshz_tj where 1=1";
		
		if(!start_date.equals("")){
			sql += " and cz_date>='" + start_date + "'";
		}
		if(!start_date.equals("")){
			sql += " and cz_date<='" + end_date + "'";
		}
		if(!user_id.equals("")){
			sql += " and xsry='" + user_id + "'";
		}
		
		sql += " order by id";
		
		return this.getResultList(sql);
	}	

}
