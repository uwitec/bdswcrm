package com.sw.cms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

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

public class WxrkdDAO extends JdbcBaseDAO
{
  
	
	/**
	 *  返回维修入库单列表(带分页)
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getWxrkdList(String con,int curPage, int rowsPerPage)
	{
		String sql = "select w.* from wxrkd w left join wxrkd_product p on w.id=p.wxrkd_id left join sys_user s on w.wxr=s.user_id  where 1=1  ";
		if(!con.equals(""))
		{
			sql=sql+con;
		}
		return this.getResultByPage(sql, curPage, rowsPerPage);
	}
	
	/**
	 * 根据维修单ID返回维修单
	 * 
	 * @param bxd_id
	 * @return
	 */
	public Object getWxrkd(String wxrkd_id) {
		String sql = "select *from wxrkd where id='" + wxrkd_id + "'";
		return this.getJdbcTemplate().queryForObject(sql, new WxrkdRowMapper());
	}
	
	/**
	 * 根据报修单ID返回保修单所修产品
	 * 
	 * @param bxd_id
	 * @return
	 */
	public Object getWxrkdProduct(String wxrkd_id) {
		String sql = "select *from wxrkd_product where wxrkd_id='" + wxrkd_id + "'";
		return this.getJdbcTemplate().queryForObject(sql,
				new WxrkdProductRowMapper());
	}
	
	
	/**
	 * 获取维修入库单可用ID
	 * @return
	 */
	public String updateWxrkdId()
	{
		String sql = "select wxrkdid from cms_all_seq";

		// 取当前序列号
		String curId = this.getJdbcTemplate().queryForInt(sql) + "";

		String day = DateComFunc.getCurDay();

		// 将序列号加1
		sql = "update cms_all_seq set wxrkdid=wxrkdid+1";
		this.getJdbcTemplate().update(sql);

		for (int i = curId.length(); i < 3; i++) {
			curId = "0" + curId;
		}

		return "WX" + day + "-" + curId;
	}
	
	public void saveWxrkd(Wxrkd wxrkd,WxrkdProduct wxrkdProduct)
	{
		String sql = "insert into wxrkd(id,wx_date,cj_date,wxr,cjr,state,client_name,lxr,mobile,address,ms)values(?,?,?,?,?,?,?,?,?,?,?)";
		Object[] param = new Object[11];
		param[0] = wxrkd.getId();
		param[1] = wxrkd.getWx_date();
		param[2] = DateComFunc.getToday();
		param[3] = wxrkd.getWxr();
		param[4] = wxrkd.getCjr();
		param[5] = wxrkd.getState();
		param[6] = wxrkd.getClient_name();
		param[7] = wxrkd.getLxr();
		param[8] = wxrkd.getMobile();
		param[9] = wxrkd.getAddress();
		param[10] = wxrkd.getMs();	 
		getJdbcTemplate().update(sql, param);
		saveWxrkdProduct(wxrkd.getId(), wxrkdProduct);
	}
	
	public void saveWxrkdProduct(String id,WxrkdProduct wxrkdProduct)
	{
		String sql = "insert into wxrkd_product(wxrkd_id,product_id,product_name,product_xh,qz_serial_num,remark,bxaddress,bxstate,has_fj,fj,qtfj,bj,gzfx,pcgc)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object param[] = new Object[14];
		param[0] = id;
		param[1] = wxrkdProduct.getProduct_id();
		param[2] = wxrkdProduct.getProduct_name();
		param[3] = wxrkdProduct.getProduct_xh();
		param[4] = wxrkdProduct.getQz_serial_num();
		param[5] = wxrkdProduct.getRemark();
		param[6] = wxrkdProduct.getBxaddress();
		param[7] = wxrkdProduct.getBxstate();
		param[8] = wxrkdProduct.getHas_fj();
		param[9] = wxrkdProduct.getFj();
		param[10] = wxrkdProduct.getQtfj();
		param[11] = wxrkdProduct.getBj();
		param[12] = wxrkdProduct.getGzfx();
		param[13] = wxrkdProduct.getPcgc();
		getJdbcTemplate().update(sql, param);
	}
	/**
	 * 更改维修入库单
	 * @param bxd
	 * @param bxdProduct
	 */
	public void updateWxrkd(Wxrkd wxrkd, WxrkdProduct wxrkdProduct) {
		String sql = "update wxrkd set wx_date=?,cj_date=?,wxr=?,cjr=?,state=?,client_name=?,lxr=?,mobile=?,address=?,ms=? where id=? ";
		Object param[] = new Object[11];
		param[0] = wxrkd.getWx_date();
		param[1] = wxrkd.getCj_date();
		param[2] = wxrkd.getWxr();
		param[3] = wxrkd.getCjr();
		param[4] = wxrkd.getState();
		param[5] = wxrkd.getClient_name();
		param[6] = wxrkd.getLxr();
		param[7] = wxrkd.getMobile();
		param[8] = wxrkd.getAddress();
		param[9] = wxrkd.getMs();
		param[10] = wxrkd.getId();
		this.getJdbcTemplate().update(sql, param);
		delWxrkdProductByWxrkdId(wxrkd.getId());
		updateWxrkdProduct(wxrkd.getId(), wxrkdProduct);
	}
	/**
	 * 删除维修单商品
	 * @param bxdProductId
	 */
	public void delWxrkdProductByWxrkdId(String wxrkd_id)
	{
		String sql="delete from wxrkd_product where wxrkd_id='"+wxrkd_id+"'";
		this.getJdbcTemplate().update(sql);
	}
	/**
	 * 更改维修单商品
	 * @param id
	 * @param bxdProduct
	 */
	public void updateWxrkdProduct(String id, WxrkdProduct wxrkdProduct)
	{
		String sql = "update wxrkd_product set product_id=?,product_name=?,product_xh=?,qz_serial_num=?,remark=?,bxaddress=?,bxstate=?,has_fj=?,fj=?,qtfj=?,bj=?,gzfx=?,pcgc=? where wxrkd_id=?";
		Object param[] = new Object[14];
		param[0] = wxrkdProduct.getProduct_id();
		param[1] = wxrkdProduct.getProduct_name();
		param[2] = wxrkdProduct.getProduct_xh();
		param[3] = wxrkdProduct.getQz_serial_num();
		param[4] = wxrkdProduct.getRemark();
		param[5] = wxrkdProduct.getBxaddress();
		param[6] = wxrkdProduct.getBxstate();
		param[7] = wxrkdProduct.getHas_fj();
		param[8] = wxrkdProduct.getFj();
		param[9] = wxrkdProduct.getQtfj();
		param[10] = wxrkdProduct.getBj();
		param[11] = wxrkdProduct.getGzfx();
		param[12] = wxrkdProduct.getPcgc();
		param[13]=id;
		this.getJdbcTemplate().update(sql, param);
	}
	
	/**
	 * 删除维修入库单及商品
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
	 * 维修入库单包装对象
	 * @author Administrator
	 *
	 */
	class WxrkdRowMapper implements RowMapper 
	{
		public Object mapRow(ResultSet rs, int index) throws SQLException 
		{
			Wxrkd  wxrkd=new Wxrkd();
			if (SqlUtil.columnIsExist(rs, "id"))wxrkd.setId(rs.getString("id"));
			if (SqlUtil.columnIsExist(rs, "wx_date"))wxrkd.setWx_date(rs.getString("wx_date"));
			if (SqlUtil.columnIsExist(rs, "cj_date"))wxrkd.setCj_date(rs.getString("cj_date"));
			if (SqlUtil.columnIsExist(rs, "wxr"))wxrkd.setWxr(rs.getString("wxr"));
			if (SqlUtil.columnIsExist(rs, "cjr"))wxrkd.setCjr(rs.getString("cjr"));
			if (SqlUtil.columnIsExist(rs, "state"))wxrkd.setState(rs.getString("state"));
			if (SqlUtil.columnIsExist(rs, "client_name"))wxrkd.setClient_name(rs.getString("client_name"));
			if (SqlUtil.columnIsExist(rs, "lxr"))wxrkd.setLxr(rs.getString("lxr"));
			if (SqlUtil.columnIsExist(rs, "mobile"))wxrkd.setMobile(rs.getString("mobile"));
			if (SqlUtil.columnIsExist(rs, "address"))wxrkd.setAddress(rs.getString("address"));
			if (SqlUtil.columnIsExist(rs, "ms"))wxrkd.setMs(rs.getString("ms"));
			return wxrkd;	
		}
	}
	/**
	 * 维修入库单商品包装对象
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
			if (SqlUtil.columnIsExist(rs, "bxaddress"))wxrkdProduct.setBxaddress(rs.getString("bxaddress"));
			if (SqlUtil.columnIsExist(rs, "bxstate"))wxrkdProduct.setBxstate(rs.getString("bxstate"));
			if (SqlUtil.columnIsExist(rs, "has_fj"))wxrkdProduct.setHas_fj(rs.getString("has_fj"));
			if (SqlUtil.columnIsExist(rs, "fj"))wxrkdProduct.setFj(rs.getString("fj"));
			if (SqlUtil.columnIsExist(rs, "qtfj"))wxrkdProduct.setQtfj(rs.getString("qtfj"));
			if (SqlUtil.columnIsExist(rs, "bj"))wxrkdProduct.setBj(rs.getString("bj"));
			if (SqlUtil.columnIsExist(rs, "gzfx"))wxrkdProduct.setGzfx(rs.getString("gzfx"));
			if (SqlUtil.columnIsExist(rs, "pcgc"))wxrkdProduct.setPcgc(rs.getString("pcgc"));
			
			return 	wxrkdProduct;
		}
	}
}
