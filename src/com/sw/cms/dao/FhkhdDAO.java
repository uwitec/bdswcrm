package com.sw.cms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.sw.cms.dao.base.JdbcBaseDAO;
import com.sw.cms.dao.base.SqlUtil;
import com.sw.cms.model.Fhkhd;
import com.sw.cms.model.FhkhdProduct;
import com.sw.cms.model.Jjd;
import com.sw.cms.model.Page;
import com.sw.cms.util.DateComFunc;

public class FhkhdDAO extends JdbcBaseDAO {
	/**
	 * 返还客户单列表（带分页）
	 * 
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getFhkhdList(String con, int curPage, int rowsPerPage) {
		String sql = "select f.id,f.client_name,f.lxr,f.lxdh,f.fh_date,f.state,f.fhr,f.cjr from fhkhd f left join sys_user s on f.fhr=s.user_id  where 1=1 ";
		if (!con.equals("")) {
			sql = sql + con;
		}
		return this.getResultByPage(sql, curPage, rowsPerPage);
	}

	/**
	 * 获取返还客户单ID
	 * 
	 * @return
	 */
	public String updateFhkhdId() {
		String sql = "select fhkhdid from cms_all_seq";
		String fhkhdid = this.getJdbcTemplate().queryForInt(sql) + "";
		sql = "update cms_all_seq set fhkhdid=fhkhdid+1";
		this.getJdbcTemplate().update(sql);
		String cur = DateComFunc.getCurDay();
		for (int i = fhkhdid.length(); i < 3; i++) {
			fhkhdid = "0" + fhkhdid;
		}
		return "FK" + cur + "-" + fhkhdid;
	}

	public void saveFhkhd(Fhkhd fhkhd, List fhkhdProducts) {
		String sql = "insert into fhkhd(id,fh_date,cj_date,fhr,cjr,state,client_name,lxr,lxdh,address,fkfs,pos_id,sfdje,skje,skzh,isfy,ms)  values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object param[] = new Object[17];
		param[0] = fhkhd.getId();
		param[1] = fhkhd.getFh_date();
		param[2] = DateComFunc.getToday();
		param[3] = fhkhd.getFhr();
		param[4] = fhkhd.getCjr();
		param[5] = fhkhd.getState();
		param[6] = fhkhd.getClient_name();
		param[7] = fhkhd.getLxr();
		param[8] = fhkhd.getLxdh();
		param[9] = fhkhd.getAddress();
		param[10] = fhkhd.getFkfs();
		param[11] = fhkhd.getPos_id();
		param[12] = fhkhd.getSfdje();
		param[13] = fhkhd.getSkje();
		param[14] = fhkhd.getSkzh();
		param[15] = fhkhd.getIsfy();
		param[16] = fhkhd.getMs();
		this.getJdbcTemplate().update(sql, param);
		saveFhkhdProduct(fhkhd.getId(), fhkhdProducts);
	}

	public void updateFhkhd(Fhkhd fhkhd, List fhkhdProducts) {

		String sql = "update fhkhd set fh_date=?,cj_date=?,fhr=?,cjr=?,state=?,client_name=?,lxr=?,lxdh=?,address=?,fkfs=?,pos_id=?,sfdje=?,skje=?,skzh=?,isfy=?,ms=?  where id=? ";
		Object param[] = new Object[17];
		
		param[0] = fhkhd.getFh_date();
		param[1] = DateComFunc.getToday();
		param[2] = fhkhd.getFhr();
		param[3] = fhkhd.getCjr();
		param[4] = fhkhd.getState();
		param[5] = fhkhd.getClient_name();
		param[6] = fhkhd.getLxr();
		param[7] = fhkhd.getLxdh();
		param[8] = fhkhd.getAddress();
		param[9] = fhkhd.getFkfs();
		param[10] = fhkhd.getPos_id();
		param[11] = fhkhd.getSfdje();
		param[12] = fhkhd.getSkje();
		param[13] = fhkhd.getSkzh();
		param[14] = fhkhd.getIsfy();
		param[15] = fhkhd.getMs();
		param[16] = fhkhd.getId();
		this.getJdbcTemplate().update(sql, param);
		delFhkhdProduct(fhkhd.getId());
		saveFhkhdProduct(fhkhd.getId(),fhkhdProducts);
	}
	
	public void delFhkhdProduct(String fhkhdId)
	{
		String sql="delete from fhkhd_product where fhkhd_id='"+fhkhdId+"'";
		this.getJdbcTemplate().update(sql);		
	}

	public void saveFhkhdProduct(String fhkhd_id, List fhkhdProducts) {
		String sql = "";
		Object[] param = new Object[6];
		if (fhkhdProducts != null && fhkhdProducts.size() > 0) {
			for (int i = 0; i < fhkhdProducts.size(); i++) {
				FhkhdProduct fhkhdProduct = (FhkhdProduct) fhkhdProducts.get(i);
				if ((!fhkhdProduct.getProduct_name().equals(""))
						&& (!fhkhdProduct.getQz_serial_num().equals(""))) {
					sql = "insert into fhkhd_product(fhkhd_id,product_id,product_name,product_xh,qz_serial_num,remark) values(?,?,?,?,?,?)";
					param[0] = fhkhd_id;
					param[1] = fhkhdProduct.getProduct_id();
					param[2] = fhkhdProduct.getProduct_name();
					param[3] = fhkhdProduct.getProduct_xh();
					param[4] = fhkhdProduct.getQz_serial_num();
					param[5] = fhkhdProduct.getRemark();
					this.getJdbcTemplate().update(sql, param);
				}
			}
		}
	}

	/**
	 * 删除返还客户单
	 * 
	 * @param id
	 */
	public void delFhkhd(String id) {
		String sql = "delete from fhkhd where id='" + id + "'";
		this.getJdbcTemplate().update(sql);
		sql = "delete from fhkhd_product where fhkhd_id='" + id + "'";
		this.getJdbcTemplate().update(sql);
	}

	/**
	 * 返回返还客户单商品
	 * 
	 * @param id
	 * @return
	 */
	public List getFhkhdProductById(String id) {
		String sql = "select * from fhkhd_product where fhkhd_id='" + id + "'";
		return this.getJdbcTemplate().query(sql, new FhkhdProductRowMapper());
	}

	public Object getFhkhd(String id) {
		String sql = "select *from fhkhd where id='" + id + "'";
		return this.getJdbcTemplate().queryForObject(sql, new FhkhdRowMapper());
	}

	/**
	 * 返还客户单包装类
	 * 
	 * @author Administrator
	 * 
	 */
	class FhkhdRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			Fhkhd fhkhd = new Fhkhd();
			if (SqlUtil.columnIsExist(rs, "id"))
				fhkhd.setId(rs.getString("id"));
			if (SqlUtil.columnIsExist(rs, "fh_date"))
				fhkhd.setFh_date(rs.getString("fh_date"));
			if (SqlUtil.columnIsExist(rs, "cj_date"))
				fhkhd.setCj_date(rs.getString("cj_date"));
			if (SqlUtil.columnIsExist(rs, "fhr"))
				fhkhd.setFhr(rs.getString("fhr"));
			if (SqlUtil.columnIsExist(rs, "cjr"))
				fhkhd.setCjr(rs.getString("cjr"));
			if (SqlUtil.columnIsExist(rs, "state"))
				fhkhd.setState(rs.getString("state"));
			if (SqlUtil.columnIsExist(rs, "client_name"))
				fhkhd.setClient_name(rs.getString("client_name"));
			if (SqlUtil.columnIsExist(rs, "lxr"))
				fhkhd.setLxr(rs.getString("lxr"));
			if (SqlUtil.columnIsExist(rs, "lxdh"))
				fhkhd.setLxdh(rs.getString("lxdh"));
			if (SqlUtil.columnIsExist(rs, "address"))
				fhkhd.setAddress(rs.getString("address"));
			if (SqlUtil.columnIsExist(rs, "fkfs"))
				fhkhd.setFkfs(rs.getString("fkfs"));
			if (SqlUtil.columnIsExist(rs, "pos_id"))
				fhkhd.setPos_id(rs.getString("pos_id"));
			if (SqlUtil.columnIsExist(rs, "sfdje"))
				fhkhd.setSfdje(rs.getDouble("sfdje"));
			if (SqlUtil.columnIsExist(rs, "skje"))
				fhkhd.setSkje(rs.getDouble("skje"));
			if (SqlUtil.columnIsExist(rs, "skzh"))
				fhkhd.setSkzh(rs.getString("skzh"));
			if (SqlUtil.columnIsExist(rs, "isfy"))
				fhkhd.setIsfy(rs.getString("isfy"));
			if (SqlUtil.columnIsExist(rs, "ms"))
				fhkhd.setMs(rs.getString("ms"));

			return fhkhd;
		}
	}

	/**
	 * 返还客户单商品包装类
	 * 
	 * @author Administrator
	 * 
	 */
	class FhkhdProductRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			FhkhdProduct fhkhdProduct = new FhkhdProduct();
			if (SqlUtil.columnIsExist(rs, "id"))
				fhkhdProduct.setId(rs.getInt("id"));
			if (SqlUtil.columnIsExist(rs, "fhkhd_id"))
				fhkhdProduct.setFhkhd_id(rs.getString("fhkhd_id"));
			if (SqlUtil.columnIsExist(rs, "product_id"))
				fhkhdProduct.setProduct_id(rs.getString("product_id"));
			if (SqlUtil.columnIsExist(rs, "product_name"))
				fhkhdProduct.setProduct_name(rs.getString("product_name"));
			if (SqlUtil.columnIsExist(rs, "product_xh"))
				fhkhdProduct.setProduct_xh(rs.getString("product_xh"));
			if (SqlUtil.columnIsExist(rs, "qz_serial_num"))
				fhkhdProduct.setQz_serial_num(rs.getString("qz_serial_num"));
			if (SqlUtil.columnIsExist(rs, "remark"))
				fhkhdProduct.setRemark(rs.getString("remark"));
			return fhkhdProduct;
		}
	}

}
