package com.sw.cms.dao;

import java.util.Map;

import com.sw.cms.dao.base.JdbcBaseDAO;
import com.sw.cms.model.Page;
import com.sw.cms.model.Pgd;
import com.sw.cms.model.Wxcld;
import com.sw.cms.model.WxcldProduct;
import com.sw.cms.util.DateComFunc;

public class WxcldDAO extends JdbcBaseDAO
{
	
	/**
	 * 维修处理单列表（带分页）
	 * @param con
	 * @param cruPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getWxcldList(String con,int cruPage,int rowsPerPage)
	{
		   String sql="select * from wxcld w left join wxcld_product p on w.w_id=p.wxcld_id  where 1=1 ";
		   if(!con.equals(""))
		   {
			   sql=sql+con;
		   }
		   return this.getResultByPage(sql, cruPage, rowsPerPage);
	 }
	
	/**
	 * 根据派工单ID查询派工单，维修处理单，售服单信息
	 * @param pgd_id
	 * @return
	 */
	public Object getWxcldBySfdPgdId(String pgd_id)
	{
		String sql="select *from wxcld w left join pgd p on w.w_pgd_id=p.p_id left join sfd s on p.p_sfd_id=s.id where w.w_pgd_id='"+pgd_id+"'";
		return this.getResultMap(sql);
	}
	
	/**
	 * 根据维修处理单ID返回维修产品
	 * @param wxcld_id
	 * @return
	 */
	public Object getWxcldProduct(String wxcld_id)
	{
		String sql="select * from wxcld_product p left join wxcld w on w.w_id=p.wxcld_id where p.wxcld_id='"+wxcld_id+"'";
		return this.getResultMap(sql);
		 
	}
	
	/**
	 * 添加维修处理单
	 * @param wxcld
	 */
	public void saveWxcld(Wxcld wxcld) 
	{
			String sql = "insert into wxcld(w_id,w_pgd_id,w_state,w_wx_state,w_jd_date,w_cj_date,w_jx_date,w_cjr,w_wxr,w_pgr,w_isfy,w_skje,w_skzh,w_client_name,w_linkman,w_mobile,w_address,w_ms)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			Object[] param = new Object[18];
			param[0] = wxcld.getW_id();
			param[1] = wxcld.getW_pgd_id();
			param[2] = wxcld.getW_state();
			param[3] = wxcld.getW_wx_state();
			param[4] = wxcld.getW_jd_date();
			param[5] = DateComFunc.getToday();
			param[6] = wxcld.getW_jx_date();
			param[7] = wxcld.getW_cjr();
			param[8] = wxcld.getW_wxr();
			param[9] = wxcld.getW_pgr();
			param[10] =wxcld.getW_isfy();
			param[11]= wxcld.getW_skje();
			param[12]= wxcld.getW_skzh();
			param[13]= wxcld.getW_client_name();
			param[14]= wxcld.getW_linkman();
			param[15]= wxcld.getW_mobile();
			param[16]= wxcld.getW_address();
			param[17]= wxcld.getW_ms();
			getJdbcTemplate().update(sql,param);		 
	 }
	
	/**
	 * 添加维修处理单商品
	 * @param wxcldProduct
	 */
	public void saveWxcldProduct(WxcldProduct wxcldProduct) 
	{
			String sql = "insert into wxcld_product(wxcld_id,product_id,product_name,product_xh,product_serial_num,product_remark,product_gmts,product_wxlx,product_clfs) values(?,?,?,?,?,?,?,?,?)";
			Object[] param = new Object[9];
			param[0] =wxcldProduct.getWxcld_id();
			param[1] =wxcldProduct.getProduct_id();
			param[2] =wxcldProduct.getProduct_name();
			param[3] =wxcldProduct.getProduct_xh();
			param[4] =wxcldProduct.getProduct_serial_num();
			param[5] =wxcldProduct.getProduct_remark();
			param[6] =wxcldProduct.getProduct_gmts();
			param[7] =wxcldProduct.getProduct_wxlx();
			param[8] =wxcldProduct.getProduct_clfs();		 
			getJdbcTemplate().update(sql,param);		 
	 }
	/**
	 * 修改维修处理单
	 * @param wxcldProduct
	 */
	public void updateWxcld(Wxcld wxcld,WxcldProduct wxcldProduct) 
	{
			String sql = "update wxcld set w_pgd_id=?,w_state=?,w_wx_state=?,w_jd_date=?,w_cj_date=?,w_jx_date=?,w_cjr=?,w_wxr=?,w_pgr=?,w_isfy=?,w_skje=?,w_skzh=?,w_client_name=?,w_linkman=?,w_mobile=?,w_address=?,w_ms=? where w_id=?";
			Object[] param = new Object[18];
			
			param[0] = wxcld.getW_pgd_id();
			param[1] = wxcld.getW_state();
			param[2] = wxcld.getW_wx_state();
			param[3] = wxcld.getW_jd_date();
			param[4] = DateComFunc.getToday();
			param[5] = wxcld.getW_jx_date();
			param[6] = wxcld.getW_cjr();
			param[7] = wxcld.getW_wxr();
			param[8] = wxcld.getW_pgr();
			param[9] =wxcld.getW_isfy();
			param[10]= wxcld.getW_skje();
			param[11]= wxcld.getW_skzh();
			param[12]= wxcld.getW_client_name();
			param[13]= wxcld.getW_linkman();
			param[14]= wxcld.getW_mobile();
			param[15]= wxcld.getW_address();
			param[16]= wxcld.getW_ms();
			param[17] = wxcld.getW_id();
			getJdbcTemplate().update(sql,param);	
			deleteWxcldProduct(wxcld.getW_id());
			wxcldProduct.setWxcld_id(wxcld.getW_id());
			saveWxcldProduct(wxcldProduct);
	 }
	/**
	 * 修改维修处理单商品
	 * @param wxcldProduct
	 */
	public void updateWxcldProduct(WxcldProduct wxcldProduct) 
	{
			String sql = "update wxcld_product set product_id=?,product_name=?,product_xh=?,product_serial_num=?,product_remark=?,product_gmts=?,product_wxlx=?,product_clfs=? where wxcld_id=?";
			Object[] param = new Object[9];			
			param[0] =wxcldProduct.getProduct_id();
			param[1] =wxcldProduct.getProduct_name();
			param[2] =wxcldProduct.getProduct_xh();
			param[3] =wxcldProduct.getProduct_serial_num();
			param[4] =wxcldProduct.getProduct_remark();
			param[5] =wxcldProduct.getProduct_gmts();
			param[6] =wxcldProduct.getProduct_wxlx();
			param[7] =wxcldProduct.getProduct_clfs();
			param[8] =wxcldProduct.getWxcld_id();
			getJdbcTemplate().update(sql,param);		 
	 }
	
	 
	
	
	
	
	/**
	 * 删除维修处理单商品
	 * @param wxcld_id
	 */
	public void deleteWxcldProduct(String wxcld_id)
	{
		String sql="delete from  wxcld_product where wxcld_id='"+wxcld_id+"' ";
		this.getJdbcTemplate().update(sql);
	}
	
	/**
	 * 维修处理单可用ID
	 * 
	 * @return
	 */
	public String updateWxcldId() {
		String sql = "select wxcldid from cms_all_seq";

		// 取当前序列号
		String curId = this.getJdbcTemplate().queryForInt(sql) + "";

		String day = DateComFunc.getCurDay();

		// 将序列号加1
		sql = "update cms_all_seq set wxcldid=wxcldid+1";
		this.getJdbcTemplate().update(sql);

		for (int i = curId.length(); i < 3; i++) {
			curId = "0" + curId;
		}

		return "WL" + day + "-" + curId;

	}
	   
}
