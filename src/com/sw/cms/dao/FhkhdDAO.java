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
		String sql = "select b.*,c.name as client_name,(select sum(p.nums) as totalNums  from fhkhd_product p where b.id=p.fhkhd_id ) as productNums from fhkhd b left join clients c  on c.id=b.client_id   where 1=1  ";
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
		String sql = "insert into fhkhd(id,fh_date,cj_date,jsr,cjr,state,client_id,lxr,skje,skzh,remark,khlx,lxdh,mobile,address,mail)  values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object param[] = new Object[16];
		param[0] = fhkhd.getId();
		param[1] = fhkhd.getFh_date();
		param[2] = DateComFunc.getToday();
		param[3] = fhkhd.getJsr();
		param[4] = fhkhd.getCjr();
		param[5] = fhkhd.getState();
		param[6] = fhkhd.getClient_id();
		if(fhkhd.getKhlx().equals("零售客户"))
		{
		  param[7] = fhkhd.getLinkmanLs();
		}
		else
		{
		  param[7] = fhkhd.getLxr();
		}	
		param[8] = fhkhd.getSkje();
		param[9] = fhkhd.getSkzh();
		param[10] = fhkhd.getRemark();
		param[11] = fhkhd.getKhlx();
		param[12] = fhkhd.getLxdh();
		param[13] = fhkhd.getMobile();
		param[14] = fhkhd.getAddress();
		param[15] = fhkhd.getMail();
		
		this.getJdbcTemplate().update(sql, param);
		saveFhkhdProduct(fhkhd.getId(), fhkhdProducts);
	}

	public void updateFhkhd(Fhkhd fhkhd, List fhkhdProducts) {

		String sql = "update fhkhd set fh_date=?,cj_date=?,jsr=?,cjr=?,state=?,client_id=?,lxr=?,skje=?,skzh=?,remark=?,khlx=?,lxdh=?,mobile=?,address=?,mail=?  where id=? ";
		Object param[] = new Object[16];
		
		param[0] = fhkhd.getFh_date();
		param[1] = DateComFunc.getToday();
		param[2] = fhkhd.getJsr();
		param[3] = fhkhd.getCjr();
		param[4] = fhkhd.getState();
		param[5] = fhkhd.getClient_id();
		if(fhkhd.getKhlx().equals("零售客户"))
		{
		  param[6] = fhkhd.getLinkmanLs();
		}
		else
		{
		  param[6] = fhkhd.getLxr();
		}			
		param[7] = fhkhd.getSkje();
		param[8] = fhkhd.getSkzh();		
		param[9] = fhkhd.getRemark();
		
		param[10] = fhkhd.getKhlx();
		param[11] = fhkhd.getLxdh();		
		param[12] = fhkhd.getMobile();
		param[13] = fhkhd.getAddress();		
		param[14] = fhkhd.getMail();
		
		param[15] = fhkhd.getId();
		this.getJdbcTemplate().update(sql, param);
		delFhkhdProduct(fhkhd.getId());
		saveFhkhdProduct(fhkhd.getId(),fhkhdProducts);
	}
	
	public void delFhkhdProduct(String fhkhdId)
	{
		String sql="delete from fhkhd_product where fhkhd_id='"+fhkhdId+"'";
		this.getJdbcTemplate().update(sql);		
	}

	public void saveFhkhdProduct(String fhkhd_id, List fhkhdProducts) 
	{
		String sql = "";
		Object[] param = new Object[12];
		if (fhkhdProducts != null && fhkhdProducts.size() > 0) 
		{
			for (int i = 0; i < fhkhdProducts.size(); i++) 
			{
				FhkhdProduct fhkhdProduct = (FhkhdProduct) fhkhdProducts.get(i);
				if(fhkhdProduct != null)
		          {
		        	if(!fhkhdProduct.getProduct_id().equals("") && !fhkhdProduct.getProduct_name().equals(""))
		        	{
					   sql = "insert into fhkhd_product(fhkhd_id,product_id,product_name,product_xh,qz_serial_num,remark,price,totalmoney,store_id,storestate,nums,cpfj) values(?,?,?,?,?,?,?,?,?,?,?,?)";
					   param[0] = fhkhd_id;
					   param[1] = fhkhdProduct.getProduct_id();
					   param[2] = fhkhdProduct.getProduct_name();
					   param[3] = fhkhdProduct.getProduct_xh();
					   param[4] = fhkhdProduct.getQz_serial_num();
					   param[5] = fhkhdProduct.getRemark();
					   param[6] = new Double(fhkhdProduct.getPrice());
					   param[7] = new Double(fhkhdProduct.getTotalmoney());
					   param[8] = fhkhdProduct.getStore_id();
					   param[9] = fhkhdProduct.getStorestate();
					   param[10] = new Integer(fhkhdProduct.getNums());
					   param[11] = fhkhdProduct.getCpfj();
					   this.getJdbcTemplate().update(sql, param);
		        	}
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
	 * 返回返还客户单可用ID
	 * 
	 * @return
	 */
	public String getfhkhdId() 
	{
		String sql = "select fhkhdid from cms_all_seq";

		// 取当前序列号
		String curId = this.getJdbcTemplate().queryForInt(sql) + "";

		String day = DateComFunc.getCurDay();

		// 将序列号加1
		sql = "update cms_all_seq set fhkhdid=fhkhdid+1";
		this.getJdbcTemplate().update(sql);

		for (int i = curId.length(); i < 3; i++) {
			curId = "0" + curId;
		}

		return "FHKH" + day + "-" + curId;

	}
   
	/**
	 * 查看返还客户单是否已经提交
	 * @param ckd_id
	 * @return
	 */
	public boolean isFhkhdSubmit(String fhkhd_id){
		boolean is = false;
		String sql = "select count(*) from fhkhd where id='" + fhkhd_id + "' and state='已提交'";
		int counts = this.getJdbcTemplate().queryForInt(sql);
		if(counts > 0){
			is = true;
		}
		return is;
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
			if (SqlUtil.columnIsExist(rs, "id"))fhkhd.setId(rs.getString("id"));
			if (SqlUtil.columnIsExist(rs, "fh_date"))fhkhd.setFh_date(rs.getString("fh_date"));
			if (SqlUtil.columnIsExist(rs, "cj_date"))fhkhd.setCj_date(rs.getString("cj_date"));
			if (SqlUtil.columnIsExist(rs, "jsr"))fhkhd.setJsr(rs.getString("jsr"));
			if (SqlUtil.columnIsExist(rs, "cjr"))fhkhd.setCjr(rs.getString("cjr"));
			if (SqlUtil.columnIsExist(rs, "state"))fhkhd.setState(rs.getString("state"));
			if (SqlUtil.columnIsExist(rs, "client_id"))fhkhd.setClient_id(rs.getString("client_id"));
			if (SqlUtil.columnIsExist(rs, "lxr"))fhkhd.setLxr(rs.getString("lxr"));			
			if (SqlUtil.columnIsExist(rs, "skje"))fhkhd.setSkje(rs.getDouble("skje"));
			if (SqlUtil.columnIsExist(rs, "skzh"))fhkhd.setSkzh(rs.getString("skzh"));			
			if (SqlUtil.columnIsExist(rs, "remark"))fhkhd.setRemark(rs.getString("remark"));
			if (SqlUtil.columnIsExist(rs, "khlx"))fhkhd.setKhlx(rs.getString("khlx"));
			if (SqlUtil.columnIsExist(rs, "lxdh"))fhkhd.setLxdh(rs.getString("lxdh"));			
			if (SqlUtil.columnIsExist(rs, "mobile"))fhkhd.setMobile(rs.getString("mobile"));
			if (SqlUtil.columnIsExist(rs, "address"))fhkhd.setAddress(rs.getString("address"));			
			if (SqlUtil.columnIsExist(rs, "mail"))fhkhd.setMail(rs.getString("mail"));
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
			
			if (SqlUtil.columnIsExist(rs, "price"))
				fhkhdProduct.setPrice(rs.getDouble("price"));
			if (SqlUtil.columnIsExist(rs, "totalmoney"))
				fhkhdProduct.setTotalmoney(rs.getDouble("totalmoney"));
			if (SqlUtil.columnIsExist(rs, "store_id"))
				fhkhdProduct.setStore_id(rs.getString("store_id"));
			if (SqlUtil.columnIsExist(rs, "storestate"))
				fhkhdProduct.setStorestate(rs.getString("storestate"));
			if (SqlUtil.columnIsExist(rs, "nums"))
				fhkhdProduct.setNums(rs.getInt("nums"));
			if (SqlUtil.columnIsExist(rs, "cpfj"))
				fhkhdProduct.setCpfj(rs.getString("cpfj"));
			
			return fhkhdProduct;
		}
	}

}
