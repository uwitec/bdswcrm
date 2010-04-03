package com.sw.cms.dao;

import java.util.List;
import java.util.Map;

import com.sw.cms.dao.base.JdbcBaseDAO;

/**
 * 客户往来对账单
 * 列表字段包括：(日期、业务类型、单据号、商品名称、商品规格、数量、应收款、销售收款、期末应收、应付款、采购付款、期末应付)
 * @author liyt
 * add ad 2008-09-15
 */
public class ClientWlDzdDAO extends JdbcBaseDAO {
	
	
	/**
	 * 根据查询条件客户往来对账单列表
	 * 包括销售、退货、采购、采购退货、销售收款、采购付款
	 * 列表字段包括：(日期、业务类型、单据号、金额(根据业务类型区分金额所放置位置))
	 * @param start_date
	 * @param end_date
	 * @param client_name
	 * @return
	 */
	public List getClientWlDzdList(String start_date,String end_date,String client_name){
		String sql = "";
		
		//销售单列表
		String xsd_sql = "select DATE_FORMAT(cz_date,'%Y-%m-%d') as creatdate,'销售' as xwtype,id as dj_id,sjcjje as je,cz_date from xsd where state='已出库'";
		if(!start_date.equals("")){
			xsd_sql = xsd_sql + " and DATE_FORMAT(cz_date,'%Y-%m-%d')>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			xsd_sql = xsd_sql + " and DATE_FORMAT(cz_date,'%Y-%m-%d')<='" + (end_date + " 23:59:59") + "'";
		}
		if(!client_name.equals("")){
			xsd_sql = xsd_sql + " and client_name = '" + client_name + "'";
		}
		
		//销售退货
		String thd_sql = "select DATE_FORMAT(cz_date,'%Y-%m-%d') as creatdate,'销售退货' as xwtype,thd_id as dj_id,(0-thdje) as je,cz_date from thd where state='已入库'";
		if(!start_date.equals("")){
			thd_sql = thd_sql + " and DATE_FORMAT(cz_date,'%Y-%m-%d')>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			thd_sql = thd_sql + " and DATE_FORMAT(cz_date,'%Y-%m-%d')<='" + (end_date + " 23:59:59") + "'";
		}
		if(!client_name.equals("")){
			thd_sql = thd_sql + " and client_name = '" + client_name + "'";
		}
		
		//销售收款
		String xssk_sql = "select DATE_FORMAT(cz_date,'%Y-%m-%d') as creatdate,'收款' as xwtype,id as dj_id,skje as je,cz_date from xssk where state='已提交'";
		if(!start_date.equals("")){
			xssk_sql = xssk_sql + " and DATE_FORMAT(cz_date,'%Y-%m-%d')>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			xssk_sql = xssk_sql + " and DATE_FORMAT(cz_date,'%Y-%m-%d')<='" + (end_date + " 23:59:59") + "'";
		}
		if(!client_name.equals("")){
			xssk_sql = xssk_sql + " and client_name = '" + client_name + "'";
		}
		
		//采购进货单
		String jhd_sql = "select DATE_FORMAT(cz_date,'%Y-%m-%d') as creatdate,'采购' as xwtype,id as dj_id,total as je,cz_date from jhd where state='已入库'";
		if(!start_date.equals("")){
			jhd_sql = jhd_sql + " and DATE_FORMAT(cz_date,'%Y-%m-%d')>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			jhd_sql = jhd_sql + " and DATE_FORMAT(cz_date,'%Y-%m-%d')<='" + (end_date + " 23:59:59") + "'";
		}
		if(!client_name.equals("")){
			jhd_sql = jhd_sql + " and gysbh = '" + client_name + "'";
		}
		
		//采购退货单
		String cgthd_sql = "select DATE_FORMAT(cz_date,'%Y-%m-%d') as creatdate,'采购退货' as xwtype,id as dj_id,(0-tkzje) as je,cz_date from cgthd where state='已出库'";
		if(!start_date.equals("")){
			cgthd_sql = cgthd_sql + " and DATE_FORMAT(cz_date,'%Y-%m-%d')>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			cgthd_sql = cgthd_sql + " and DATE_FORMAT(cz_date,'%Y-%m-%d')<='" + (end_date + " 23:59:59") + "'";
		}
		if(!client_name.equals("")){
			cgthd_sql = cgthd_sql + " and provider_name = '" + client_name + "'";
		}
		
		//采购付款
		String cgfk_sql = "select DATE_FORMAT(cz_date,'%Y-%m-%d') as creatdate,'付款' as xwtype,id as dj_id,fkje as je,cz_date from cgfk where (state='已提交' or state='已支付')";
		if(!start_date.equals("")){
			cgfk_sql = cgfk_sql + " and DATE_FORMAT(cz_date,'%Y-%m-%d')>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			cgfk_sql = cgfk_sql + " and DATE_FORMAT(cz_date,'%Y-%m-%d')<='" + (end_date + " 23:59:59") + "'";
		}
		if(!client_name.equals("")){
			cgfk_sql = cgfk_sql + " and gysbh = '" + client_name + "'";
		}
		
		
		//往来调账(应收)
		String wltz_ys_sql = "select DATE_FORMAT(cz_date,'%Y-%m-%d') as creatdate,'往来调账(应收)' as xwtype,id as dj_id,pzje as je,cz_date from pz where state='已提交' and type='应收'";
		if(!start_date.equals("")){
			wltz_ys_sql = wltz_ys_sql + " and DATE_FORMAT(cz_date,'%Y-%m-%d')>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			wltz_ys_sql = wltz_ys_sql + " and DATE_FORMAT(cz_date,'%Y-%m-%d')<='" + (end_date + " 23:59:59") + "'";
		}
		if(!client_name.equals("")){
			wltz_ys_sql = wltz_ys_sql + " and client_name = '" + client_name + "'";
		}
		
		
		//往来调账（应付）
		String wltz_yf_sql = "select DATE_FORMAT(cz_date,'%Y-%m-%d') as creatdate,'往来调账(应付)' as xwtype,id as dj_id,pzje as je,cz_date from pz where state='已提交' and type='应付'";
		if(!start_date.equals("")){
			wltz_yf_sql = wltz_yf_sql + " and DATE_FORMAT(cz_date,'%Y-%m-%d')>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			wltz_yf_sql = wltz_yf_sql + " and DATE_FORMAT(cz_date,'%Y-%m-%d')<='" + (end_date + " 23:59:59") + "'";
		}
		if(!client_name.equals("")){
			wltz_yf_sql = wltz_yf_sql + " and client_name = '" + client_name + "'";
		}
		
		
		
		sql = "select * from ((" + xsd_sql + ") union (" + thd_sql + ") union (" + xssk_sql + ") union(" + jhd_sql + ") union(" + cgthd_sql + ") union(" + cgfk_sql + ") union (" + wltz_ys_sql + ") union(" + wltz_yf_sql + ")) m order by cz_date asc";
		
		return this.getResultList(sql);
	}
	
	
	/**
	 * 获取单据明细信息
	 * @param dj_id 单据编号
	 * @param cdid  从单编号
	 * @param xwtype 业务类型
	 * @return
	 */
	public List getDjMxList(String dj_id,String xwtype){
		String sql = "";
		
		if(xwtype.equals("销售")){
			sql = "select product_id,product_name,product_xh,sjcj_nums,((price+jgtz) * sjcj_nums) as xj from xsd_product where xsd_id='" + dj_id + "'";
		}else if(xwtype.equals("销售退货")){
			sql = "select product_id,product_name,product_xh,nums,(0-th_price * nums) as xj from thd_product where thd_id='" + dj_id + "'";
		}else if(xwtype.equals("采购")){
			sql = "select  product_id,product_name,product_xh,nums,(price*nums) as xj from jhd_product where jhd_id='" + dj_id + "'";
		}else if(xwtype.equals("采购退货")){
			sql = "select product_id,product_name,product_xh,nums,(0-th_price*nums) as xj from cgthd_product where cgthd_id='" + dj_id + "'";
		}
		
		return this.getResultList(sql);
	}
	
	
	/**
	 * 取客户期初信息
	 * @param client_name
	 * @param qc_date
	 * @return
	 */
	public Map getClientQcInfo(String client_name,String cdate){
		Map map = null;
		String sql = "select * from client_qc where client_name='" + client_name + "' and cdate='" + cdate + "'";		
		List list = this.getResultList(sql);
		if(list != null && list.size()>0){
			map = (Map)list.get(0);
		}
		return map;
	}
	

}
