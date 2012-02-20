package com.sw.cms.dao;

import java.util.Map;

import com.sw.cms.dao.base.JdbcBaseDAO;
import com.sw.cms.model.ProductSaleFlow;
import com.sw.cms.util.StringUtils;

/**
 * 商品销售流水处理
 * @author jinyanni
 *
 */
public class ProductSaleFlowDAO extends JdbcBaseDAO {
	
	public void insertProductSaleFlow(ProductSaleFlow info){
		String sql = "insert into product_sale_flow(id,yw_type,product_id,client_name,xsry,cz_date,nums,price,hjje,dwcb,cb,dwkhcb,khcb,dwygcb,ygcb,sd,bhsje,gf,ds,basic_ratio,out_ratio,lsxj,sfcytc,jy_time,xsry_dept,store_id) " +
				"values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,now(),?,?)";
		
		Object[] param = new Object[25];
		
		param[0] = info.getId();
		param[1] = info.getYw_type();
		param[2] = info.getProduct_id();
		param[3] = info.getClient_name();
		param[4] = info.getXsry();
		param[5] = info.getCz_date();
		param[6] = info.getNums();
		param[7] = info.getPrice();
		param[8] = info.getHjje();
		param[9] = info.getDwcb();
		param[10] = info.getCb();
		param[11] = info.getDwkhcb();
		param[12] = info.getKhcb();
		param[13] = info.getDwygcb();
		param[14] = info.getYgcb();
		param[15] = info.getSd();
		param[16] = info.getBhsje();
		param[17] = info.getGf();
		param[18] = info.getDs();
		param[19] = info.getBasic_ratio();
		param[20] = info.getOut_ratio();
		param[21] = info.getLsxj();
		param[22] = info.getSfcytc();
		param[23] = this.getXsryDept(info.getXsry());
		param[24] = this.getStoreId(info.getId(),info.getYw_type());
		
		this.getJdbcTemplate().update(sql,param);
	}
	
	
	/**
	 * 根据销售人员编号取所在部门编号
	 * @param xsry 销售人员编号
	 * @return  String 部门编号
	 */
	private String getXsryDept(String xsry){
		String dept = "";
		String sql = "select dept from sys_user where user_id='" + xsry + "'";
		Map map = this.getJdbcTemplate().queryForMap(sql);
		if(map != null){
			dept = StringUtils.nullToStr(map.get("dept"));
		}
		return dept;
	}

	/**
	 * 根据单据编号取出库库房编号
	 * @param id 单据编号
	 * @param yw_type 业务类型
	 * @return  String 仓库编号
	 */
	private String getStoreId(String id,String yw_type){
		String store = "";
		String sql="";
		if(yw_type.equals("退货单"))
		{
			sql = "select store_id from rkd where jhd_id='" + id + "'";
		}
		else
		{
		   sql = "select store_id from ckd where xsd_id='" + id + "'";
		}
		Map map = this.getJdbcTemplate().queryForMap(sql);
		if(map != null){
			store = StringUtils.nullToStr(map.get("store_id"));
		}
		return store;
	}
}
