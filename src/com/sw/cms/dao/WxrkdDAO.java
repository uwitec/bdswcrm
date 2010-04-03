package com.sw.cms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

import com.sw.cms.dao.BxdDAO.BxdProductRowMapper;
import com.sw.cms.dao.BxdDAO.BxdRowMapper;
import com.sw.cms.dao.base.JdbcBaseDAO;
import com.sw.cms.dao.base.SqlUtil;
import com.sw.cms.model.Bxd;
import com.sw.cms.model.BxdProduct;
import com.sw.cms.model.Page;
import com.sw.cms.model.Wxrkd;
import com.sw.cms.model.WxrkdProduct;
import com.sw.cms.util.DateComFunc;

import java.util.List;


public class WxrkdDAO extends JdbcBaseDAO
{
  
	
	/**
	 *  ����ά����ⵥ�б�(����ҳ)
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getWxrkdList(String con,int curPage, int rowsPerPage)
	{
		String sql = "select w.* from wxrkd w where 1=1  ";
		if(!con.equals(""))
		{
			sql=sql+con;
		}
		return this.getResultByPage(sql, curPage, rowsPerPage);
	}
	
	/**
	 * ����ά����ⵥID����ά�޵�
	 * 
	 * @param bxd_id
	 * @return
	 */
	public Object getWxrkd(String wxrkd_id) {
		String sql = "select *from wxrkd where id='" + wxrkd_id + "'";
		return this.getJdbcTemplate().queryForObject(sql, new WxrkdRowMapper());
	}
	
	/**
	 * ����ά����ⵥID���ر��޵�������Ʒ
	 * 
	 * @param bxd_id
	 * @return
	 */
	public List getWxrkdProducts(String wxrkd_id) {
		String sql = "select * from wxrkd_product where wxrkd_id='" + wxrkd_id + "'";
		return this.getJdbcTemplate().query(sql, new WxrkdProductRowMapper());
	}
	
	
	/**
	 * ��ȡά����ⵥ����ID
	 * @return
	 */
	public String updateWxrkdId()
	{
		String sql = "select wxrkdid from cms_all_seq";

		// ȡ��ǰ���к�
		String curId = this.getJdbcTemplate().queryForInt(sql) + "";

		String day = DateComFunc.getCurDay();

		// �����кż�1
		sql = "update cms_all_seq set wxrkdid=wxrkdid+1";
		this.getJdbcTemplate().update(sql);

		for (int i = curId.length(); i < 3; i++) {
			curId = "0" + curId;
		}

		return "WXRK" + day + "-" + curId;
	}
	
	public void saveWxrkd(Wxrkd wxrkd,List wxrkdProducts)
	{
		String sql = "insert into wxrkd(id,wxrk_date,cj_date,jsr,cjr,state,remark)values(?,?,?,?,?,?,?)";
		Object[] param = new Object[7];
		param[0] = wxrkd.getId();
		param[1] = wxrkd.getWxrk_date();
		param[2] = DateComFunc.getToday();
		param[3] = wxrkd.getJsr();
		param[4] = wxrkd.getCjr();
		param[5] = wxrkd.getState();
		param[6] = wxrkd.getRemark();	 
		getJdbcTemplate().update(sql, param);
		
		saveWxrkdProduct(wxrkd.getId(), wxrkdProducts);
	}
	
	public void saveWxrkdProduct(String id,List wxrkdProducts)
	{
		Object param[] = new Object[9];
		String sqlStore=""; 
		String storeId="";
		sqlStore= "select id from storehouse where name='�ü���'";
		Map map=getResultMap(sqlStore);
		storeId= (String)map.get("id") ;
	
		if(wxrkdProducts != null && wxrkdProducts.size()>0)
		{
			for(int i=0;i<wxrkdProducts.size();i++)
			{
				WxrkdProduct wxrkdProduct = (WxrkdProduct)wxrkdProducts.get(i);
				if(wxrkdProduct != null){
				  if(!wxrkdProduct.getProduct_id().equals("") && !wxrkdProduct.getProduct_name().equals(""))
					{
		               String sql = "insert into wxrkd_product(wxrkd_id,product_id,product_name,product_xh,qz_serial_num,remark,nums,store_id,storestate)values(?,?,?,?,?,?,?,?,?)";
		
		               param[0] = id;
		               param[1] = wxrkdProduct.getProduct_id();
		               param[2] = wxrkdProduct.getProduct_name();
		               param[3] = wxrkdProduct.getProduct_xh();
		               param[4] = wxrkdProduct.getQz_serial_num();
		               param[5] = wxrkdProduct.getRemark();
		               param[6] = wxrkdProduct.getNums();
		
		               param[7] = storeId;
		               param[8] = "3";		
		               getJdbcTemplate().update(sql, param);
					}
				}
			}
		}
	}
	/**
	 * ����ά����ⵥ
	 * @param bxd
	 * @param bxdProduct
	 */
	public void updateWxrkd(Wxrkd wxrkd, List wxrkdProducts) {
		String sql = "update wxrkd set wxrk_date=?,cj_date=?,jsr=?,cjr=?,state=?,remark=? where id=? ";
		Object param[] = new Object[7];
		param[0] = wxrkd.getWxrk_date();
		param[1] = DateComFunc.getToday();
		param[2] = wxrkd.getJsr();
		param[3] = wxrkd.getCjr();
		param[4] = wxrkd.getState();
		param[5] = wxrkd.getRemark();
		param[6] = wxrkd.getId();
		this.getJdbcTemplate().update(sql, param);
		delWxrkdProductByWxrkdId(wxrkd.getId());
		saveWxrkdProduct(wxrkd.getId(), wxrkdProducts);
	}
	/**
	 * ɾ��ά�޵���Ʒ
	 * @param bxdProductId
	 */
	public void delWxrkdProductByWxrkdId(String wxrkd_id)
	{
		String sql="delete from wxrkd_product where wxrkd_id='"+wxrkd_id+"'";
		this.getJdbcTemplate().update(sql);
	}
		
	/**
	 * ɾ��ά����ⵥ����Ʒ
	 * @param id
	 */
	public void delWxrkd(String id)
	{
		String sql = "delete from wxrkd where id='" + id + "'";

		this.getJdbcTemplate().update(sql);

		sql = "delete from wxrkd_product where wxrkd_id='" + id + "'";
		this.getJdbcTemplate().update(sql);
	}
	
	/**
	 * ά����ⵥ��װ����
	 * @author Administrator
	 *
	 */
	class WxrkdRowMapper implements RowMapper 
	{
		public Object mapRow(ResultSet rs, int index) throws SQLException 
		{
			Wxrkd  wxrkd=new Wxrkd();
			if (SqlUtil.columnIsExist(rs, "id"))wxrkd.setId(rs.getString("id"));
			if (SqlUtil.columnIsExist(rs, "wxrk_date"))wxrkd.setWxrk_date(rs.getString("wxrk_date"));
			if (SqlUtil.columnIsExist(rs, "cj_date"))wxrkd.setCj_date(rs.getString("cj_date"));
			if (SqlUtil.columnIsExist(rs, "jsr"))wxrkd.setJsr(rs.getString("jsr"));
			if (SqlUtil.columnIsExist(rs, "cjr"))wxrkd.setCjr(rs.getString("cjr"));
			if (SqlUtil.columnIsExist(rs, "state"))wxrkd.setState(rs.getString("state"));
			if (SqlUtil.columnIsExist(rs, "remark"))wxrkd.setRemark(rs.getString("remark"));
			return wxrkd;	
		}
	}
	/**
	 * ά����ⵥ��Ʒ��װ����
	 * @author Administrator
	 *
	 */
	class WxrkdProductRowMapper implements RowMapper 
	{
		public Object mapRow(ResultSet rs, int index) throws SQLException 
		{
			WxrkdProduct  wxrkdProduct=new WxrkdProduct();
			if (SqlUtil.columnIsExist(rs, "id"))wxrkdProduct.setId(rs.getInt("id"));
			if (SqlUtil.columnIsExist(rs, "wxrkd_id"))wxrkdProduct.setWxrkd_id(rs.getString("wxrkd_id"));
			if (SqlUtil.columnIsExist(rs, "product_id"))wxrkdProduct.setProduct_id(rs.getString("product_id"));
			if (SqlUtil.columnIsExist(rs, "product_name"))wxrkdProduct.setProduct_name(rs.getString("product_name"));
			if (SqlUtil.columnIsExist(rs, "product_xh"))wxrkdProduct.setProduct_xh(rs.getString("product_xh"));
			if (SqlUtil.columnIsExist(rs, "qz_serial_num"))wxrkdProduct.setQz_serial_num(rs.getString("qz_serial_num"));
			if (SqlUtil.columnIsExist(rs, "remark"))wxrkdProduct.setRemark(rs.getString("remark"));
			
			if (SqlUtil.columnIsExist(rs, "nums"))wxrkdProduct.setNums(rs.getInt("nums"));
			if (SqlUtil.columnIsExist(rs, "store_id"))wxrkdProduct.setStore_id(rs.getString("store_id"));
			if (SqlUtil.columnIsExist(rs, "storestate"))wxrkdProduct.setStorestate(rs.getString("storestate"));
			
			return 	wxrkdProduct;
		}
	}
}
