package com.sw.cms.dao;

import com.sw.cms.dao.base.BeanRowMapper;
import com.sw.cms.dao.base.JdbcBaseDAO;
import com.sw.cms.model.ProductSaleFlow;
import com.sw.cms.model.SysUser;

/**
 * ��Ʒ������ˮ����
 * @author jinyanni
 *
 */
public class ProductSaleFlowDAO extends JdbcBaseDAO {
	
	public void insertProductSaleFlow(ProductSaleFlow info){
		String sql = "insert into product_sale_flow(id,yw_type,product_id,client_name,xsry,cz_date,nums,price,hjje,dwcb,cb,dwkhcb,khcb,dwygcb,ygcb,sd,bhsje,gf,ds,basic_ratio,out_ratio,lsxj,sfcytc,jy_time,xsry_dept) " +
				"values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,now(),?)";
		
		Object[] param = new Object[24];
		
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
		
		
		this.getJdbcTemplate().update(sql,param);
	}
	
	
	/**
	 * ����������Ա���ȡ���ڲ��ű��
	 * @param xsry ������Ա���
	 * @return  String ���ű��
	 */
	private String getXsryDept(String xsry){
		String dept = "";
		String sql = "select * from sys_user where user_id='" + xsry + "'";
		
		SysUser user = (SysUser)this.queryForObject(sql, new BeanRowMapper(SysUser.class));
		
		if(user != null){
			dept = user.getDept();
		}
		
		return dept;
	}

}
