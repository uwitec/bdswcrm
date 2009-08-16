package com.sw.cms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

import com.sw.cms.dao.base.JdbcBaseDAO;
import com.sw.cms.dao.base.SqlUtil;
import com.sw.cms.model.Page;
import com.sw.cms.model.Product;

public class ProductDAO extends JdbcBaseDAO {

	/**
	 * 根据产品类别ID取产品 采用分页模型
	 * 
	 * @param curId
	 *            类别ID
	 * @return
	 */
	public Page getProductListByID(String curId, int curPage, int rowsPerPage) {
		String sql = "select a.product_id,a.product_xh,a.product_name,b.name as product_kind,a.gysmc,a.price,a.lsxj,a.fxxj,a.prop,a.state  from product a left join product_kind b on b.id=a.product_kind where 1=1";

		if (!curId.equals("")) {
			sql = sql + " and product_kind='" + curId + "'";
		}

		return this.getResultByPage(sql, curPage, rowsPerPage);
	}
	
	
	/**
	 * 根据查询条件取产品列表
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getProductList(String con, int curPage, int rowsPerPage) {
		String sql = "select a.product_id,a.product_xh,a.product_name,b.name as product_kind,a.gysmc,a.price,a.lsxj,a.fxxj,a.prop,a.state  from product a left join product_kind b on b.id=a.product_kind where 1=1";

		if (!con.equals("")) {
			sql = sql + con;
		}

		return this.getResultByPage(sql, curPage, rowsPerPage);
	}
	
	/**
	 * 取根据查询条取产品列表
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getProducts(String con,int curPage,int rowsPerPage){
		String sql = "select * from product where 1=1";
		if(!con.equals("")){
			sql += con;
		}
		return this.getResultByPage(sql, curPage,rowsPerPage, new ProductRowMapper());
	}

	/**
	 * 第二种方式取分页对象 返回结果是包装的是Product对象
	 * 
	 * @param curId
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getProductsByID(String curId, int curPage, int rowsPerPage) {
		String sql = "select * from product where 1=1";

		if (!curId.equals("")) {
			sql = sql + " and product_kind='" + curId + "'";
		}

		return this.getResultByPage(sql, curPage, rowsPerPage,
				new ProductRowMapper());
	}

	/**
	 * 保存产品信息
	 * 
	 * @param product
	 */
	public void saveProductInfo(Product product) {
		String sql = "insert into product(product_id,product_xh,product_name,product_kind,gysbh,gysmc,price,img,ms,prop,dw,fxxj,lsxj,gf,dss,kcxx,kcsx,state,lsbj,qz_serial_num,fxbj,khcbj,sp_txm) "
				+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		Object[] params = new Object[23];
		
		String productKind = product.getProductKind();

		params[0] = getProductID(productKind);
		params[1] = product.getProductXh();
		params[2] = product.getProductName();
		params[3] = productKind;
		params[4] = product.getGysbh();
		params[5] = product.getGysmc();
		params[6] = new Double(product.getPrice());
		params[7] = product.getImg();
		params[8] = product.getMs();
		params[9] = product.getProp();
		params[10] = product.getDw();
		params[11] = new Double(product.getFxxj());
		params[12] = new Double(product.getLsxj());
		params[13] = new Double(product.getGf());
		params[14] = new Double(product.getDss());
		params[15] = new Integer(product.getKcxx());
		params[16] = new Integer(product.getKcsx());
		params[17] = product.getState();
		params[18] = product.getLsbj();
		params[19] = product.getQz_serial_num();
		params[20] = product.getFxbj();
		params[21] = product.getKhcbj();
		params[22] = product.getSp_txm();

		this.getJdbcTemplate().update(sql, params);
	}

	/**
	 * 修改产品信息
	 * 
	 * @param product
	 */
	public void updateProductInfo(Product product) {
		String sql = "update product set product_xh=?,product_name=?,product_kind=?,gysbh=?,gysmc=?,price=?,img=?,ms=?,prop=?,dw=?,fxxj=?,lsxj=?,gf=?,dss=?,kcxx=?,kcsx=?,state=?,lsbj=?,qz_serial_num=?,fxbj=?,khcbj=?,sp_txm=? where product_id=?";

		Object[] params = new Object[23];

		params[0] = product.getProductXh();
		params[1] = product.getProductName();
		params[2] = product.getProductKind();
		params[3] = product.getGysbh();
		params[4] = product.getGysmc();
		params[5] = new Double(product.getPrice());
		params[6] = product.getImg();
		params[7] = product.getMs();
		params[8] = product.getProp();
		params[9] = product.getDw();
		params[10] = new Double(product.getFxxj());
		params[11] = new Double(product.getLsxj());
		params[12] = new Double(product.getGf());
		params[13] = new Double(product.getDss());
		params[14] = new Integer(product.getKcxx());	
		params[15] = new Integer(product.getKcsx());	
		params[16] = product.getState();
		params[17] = product.getLsbj();
		params[18] = product.getQz_serial_num();
		params[19] = product.getFxbj();
		params[20] = product.getKhcbj();
		params[21] = product.getSp_txm();
		params[22] = product.getProductId();

		this.getJdbcTemplate().update(sql, params);
	}

	/**
	 * 根据产品系统编号取产品详细信息
	 * 
	 * @param productId
	 * @return Map
	 */
	public Map getProductInfoById(String productId) {
		String sql = "select * from product where product_id='" + productId
				+ "'";
		return this.getJdbcTemplate().queryForMap(sql);
	}

	/**
	 * 根据产品系统编号取产品信息
	 * 
	 * @param productId
	 * @return Product对象
	 */
	public Object getProductById(String productId) {
		String sql = "select * from product where product_id='" + productId
				+ "'";
		return this.queryForObject(sql, new ProductRowMapper());
	}
	
	
	/**
	 * 删除产品信息
	 * @param productId
	 */
	public void delProductById(String productId){
		String sql = "delete from product where product_id='" + productId + "'";
		
		this.getJdbcTemplate().update(sql);
	}

	/**
	 * 取下一个可用产品编号
	 * 
	 * @return
	 */
	private String getProductID(String productKind) {
		String rtId = "";
		
		String sql = "select max(right(product_id,4)) as id from product where product_id like '%" + productKind + "%' or product_kind='" + productKind + "'";
		log.debug(sql);
		List list = this.getResultList(sql);
		
		if(list!=null && list.size()>0){
			Map map = (Map)list.get(0);
			
			String curMaxId = (String)map.get("id");
			if(curMaxId == null || curMaxId.equals("")){
				rtId = productKind + "0001";
				return rtId;
			}
			
			curMaxId = ((new Integer(curMaxId).intValue())+1) + "";
			
			for (int i = curMaxId.length(); i < 4; i++) {
				curMaxId = "0" + curMaxId;
			}
			rtId = productKind + curMaxId;
		}else{
			rtId = productKind + "0001";
		}
		
		return rtId;
	}
	
	
	/**
	 * 调整成本价
	 * @param product_id
	 * @param price
	 */
	public void updateProductPrice(String product_id,double price){
		String sql = "update product set price=" + price + " where product_id='" + product_id + "'";
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * 根据序列号查询产品对象
	 * @param serial_num
	 * @return
	 */
	public Product getProductBySerialNum(String serial_num){
		Product product = new Product();
		String sql = "select b.* from serial_num_mng a left join product b on b.product_id=a.product_id where a.state='在库' and a.serial_num='" + serial_num + "'";
		
		Object obj = this.queryForObject(sql, new ProductRowMapper());
		if(obj != null){
			product = (Product)obj;
		}
		return product;
	}
	
	
	/**
	 * 查看序列号是否在库
	 * @param serial_num
	 * @param product_id
	 * @return
	 */
	public String productIsExist(String serial_num){
		String flag = "false";
		String sql = "select * from serial_num_mng where serial_num='" + serial_num + "' and state='在库'";
		List list = this.getResultList(sql);
		if(list != null && list.size() > 0){
			flag = "true";
		}
		return flag;
	}
	
	/**
	 * 根据产品编号及序列号查询产品是否存在
	 * @param serial_num
	 * @param product_id
	 * @return
	 */
	public String productIsExist(String serial_num,String product_id){
		String flag = "false";
		String sql = "select * from serial_num_mng where product_id='" + product_id + "' and serial_num='" + serial_num + "' and state='在库'";
		List list = this.getResultList(sql);
		if(list != null && list.size() > 0){
			flag = "true";
		}
		return flag;
	}	
	
	
	/**
	 * 根据产品编号取分销限价
	 * @param product_id
	 * @return
	 */
	public double getProductFxxj(String product_id){
		double fxxj = 0;
		String sql = "select * from product where product_id='" + product_id + "'";
		List list = this.getResultList(sql);
		
		if(list != null && list.size() >0){
			Map map = (Map)list.get(0);
			fxxj = map.get("fxxj")==null?0:((Double)map.get("fxxj")).doubleValue();
		}
		
		return fxxj;
	}
	
	
	/**
	 * 根据产品编号取零售限价
	 * @param product_id
	 * @return
	 */
	public double getProductLsxj(String product_id){
		double lsxj = 0;
		
		String sql = "select * from product where product_id='" + product_id + "'";
		List list = this.getResultList(sql);
		
		if(list != null && list.size() >0){
			Map map = (Map)list.get(0);
			lsxj = map.get("lsxj")==null?0:((Double)map.get("lsxj")).doubleValue();
		}
		return lsxj;
	}
	
	
	/**
	 * 根据条件查询所有产品列表
	 * @param product_kind  产品类别
	 * @param product_name  产品名称
	 * @param product_xh    产品规格
	 * @return
	 */
	public List getProductByCon(String product_kind,String product_name,String product_xh,String state){
		String sql = "select * from product where 1=1";
		
		if(!product_kind.equals("")){
			sql += " and product_kind like '" + product_kind + "%'";
		}
		if(!product_name.equals("")){
			sql += " and product_name like '%" + product_name + "%'";
		}
		if(!product_xh.equals("")){
			sql += " and product_xh like '%" + product_xh + "%'";
		}
		if(!state.equals("")){
			sql += " and state='" + state + "'";
		}
		
		return this.getResultList(sql, new ProductRowMapper());
	}
	
	
	/**
	 * 判断商品是否可以删除<BR>
	 * 发生业务数据的商品不能删除<BR>
	 * 业务数据包括：零售、销售、退货、采购、采购退货、调拨申请、调拨、调价<BR>
	 * 因为出库入库，不能添加，只能有相应单据生成，所以不在考虑范围内
	 * @param product_id  商品编号
	 * @return boolean true:可以；false:不可以
	 */
	public boolean isCanDel(String product_id){
		
		//判断是否发生零售，如果发生零售则返回false
		String sql = "select count(*) as counts from lsd_product where product_id='" + product_id + "'";		
		if(this.getJdbcTemplate().queryForInt(sql) > 0){
			return false;
		}
		
		//判断是否发生销售，如果发生销售则返回false
		sql = "select count(*) as counts from xsd_product where product_id='" + product_id + "'";
		if(this.getJdbcTemplate().queryForInt(sql) > 0){
			return false;
		}
		
		//判断是否发生销售退货，如果发生销售退货则返回false
		sql = "select count(*) as counts from thd_product where product_id='" + product_id + "'";
		if(this.getJdbcTemplate().queryForInt(sql) > 0){
			return false;
		}
		
		//判断是否发生采购，如果发生采购则返回false
		sql = "select count(*) as counts from jhd_product where product_id='" + product_id + "'";
		if(this.getJdbcTemplate().queryForInt(sql) > 0){
			return false;
		}
		
		//判断是否发生采购退货，如果发生采购退货则返回false
		sql = "select count(*) as counts from cgthd_product where product_id='" + product_id + "'";
		if(this.getJdbcTemplate().queryForInt(sql) > 0){
			return false;
		}	
		
		//判断是否发生调拨申请，如果发生调拨申请则返回false
		sql = "select count(*) as counts from dbsq_product where product_id='" + product_id + "'";
		if(this.getJdbcTemplate().queryForInt(sql) > 0){
			return false;
		}
		
		//判断是否发生调拨，如果发生调拨则返回false
		sql = "select count(*) as counts from kfdb_product where product_id='" + product_id + "'";
		if(this.getJdbcTemplate().queryForInt(sql) > 0){
			return false;
		}	
		
		//判断是否发生存货调价，如果发生存货调价则返回false
		sql = "select count(*) as counts from chtj_desc where product_id='" + product_id + "'";
		if(this.getJdbcTemplate().queryForInt(sql) > 0){
			return false;
		}			
		
		return true;
	}
	

	/**
	 * 包装对象
	 * 
	 * @author liyt
	 * 
	 */
	class ProductRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			Product product = new Product();

			if(SqlUtil.columnIsExist(rs,"product_id")) product.setProductId(rs.getString("product_id"));
			if(SqlUtil.columnIsExist(rs,"product_xh")) product.setProductXh(rs.getString("product_xh"));
			if(SqlUtil.columnIsExist(rs,"product_name")) product.setProductName(rs.getString("product_name"));
			if(SqlUtil.columnIsExist(rs,"product_kind")) product.setProductKind(rs.getString("product_kind"));

			if(SqlUtil.columnIsExist(rs,"gysbh")) product.setGysbh(rs.getString("gysbh"));
			if(SqlUtil.columnIsExist(rs,"gysmc")) product.setGysmc(rs.getString("gysmc"));

			if(SqlUtil.columnIsExist(rs,"price")) product.setPrice(rs.getDouble("price"));

			if(SqlUtil.columnIsExist(rs,"img")) product.setImg(rs.getString("img"));
			if(SqlUtil.columnIsExist(rs,"ms")) product.setMs(rs.getString("ms"));

			if(SqlUtil.columnIsExist(rs,"prop")) product.setProp(rs.getString("prop"));
			if(SqlUtil.columnIsExist(rs,"dw")) product.setDw(rs.getString("dw"));
			
			if(SqlUtil.columnIsExist(rs,"fxxj")) product.setFxxj(rs.getDouble("fxxj"));
			if(SqlUtil.columnIsExist(rs,"lsxj")) product.setLsxj(rs.getDouble("lsxj"));
			if(SqlUtil.columnIsExist(rs,"gf")) product.setGf(rs.getDouble("gf"));
			if(SqlUtil.columnIsExist(rs,"dss")) product.setDss(rs.getDouble("dss"));
			if(SqlUtil.columnIsExist(rs,"lsbj")) product.setLsbj(rs.getDouble("lsbj"));
			
			if(SqlUtil.columnIsExist(rs,"kcxx")) product.setKcxx(rs.getInt("kcxx"));
			if(SqlUtil.columnIsExist(rs,"kcsx")) product.setKcsx(rs.getInt("kcsx"));
			if(SqlUtil.columnIsExist(rs,"state")) product.setState(rs.getString("state"));
			if(SqlUtil.columnIsExist(rs,"qz_serial_num")) product.setQz_serial_num(rs.getString("qz_serial_num"));
			
			if(SqlUtil.columnIsExist(rs,"sp_txm")) product.setSp_txm(rs.getString("sp_txm"));
			if(SqlUtil.columnIsExist(rs,"fxbj")) product.setFxbj(rs.getDouble("fxbj"));
			if(SqlUtil.columnIsExist(rs,"khcbj")) product.setKhcbj(rs.getDouble("khcbj"));
			
			return product;
		}
	}

}
