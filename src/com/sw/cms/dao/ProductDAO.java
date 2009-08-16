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
	 * ���ݲ�Ʒ���IDȡ��Ʒ ���÷�ҳģ��
	 * 
	 * @param curId
	 *            ���ID
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
	 * ���ݲ�ѯ����ȡ��Ʒ�б�
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
	 * ȡ���ݲ�ѯ��ȡ��Ʒ�б�
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
	 * �ڶ��ַ�ʽȡ��ҳ���� ���ؽ���ǰ�װ����Product����
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
	 * �����Ʒ��Ϣ
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
	 * �޸Ĳ�Ʒ��Ϣ
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
	 * ���ݲ�Ʒϵͳ���ȡ��Ʒ��ϸ��Ϣ
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
	 * ���ݲ�Ʒϵͳ���ȡ��Ʒ��Ϣ
	 * 
	 * @param productId
	 * @return Product����
	 */
	public Object getProductById(String productId) {
		String sql = "select * from product where product_id='" + productId
				+ "'";
		return this.queryForObject(sql, new ProductRowMapper());
	}
	
	
	/**
	 * ɾ����Ʒ��Ϣ
	 * @param productId
	 */
	public void delProductById(String productId){
		String sql = "delete from product where product_id='" + productId + "'";
		
		this.getJdbcTemplate().update(sql);
	}

	/**
	 * ȡ��һ�����ò�Ʒ���
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
	 * �����ɱ���
	 * @param product_id
	 * @param price
	 */
	public void updateProductPrice(String product_id,double price){
		String sql = "update product set price=" + price + " where product_id='" + product_id + "'";
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * �������кŲ�ѯ��Ʒ����
	 * @param serial_num
	 * @return
	 */
	public Product getProductBySerialNum(String serial_num){
		Product product = new Product();
		String sql = "select b.* from serial_num_mng a left join product b on b.product_id=a.product_id where a.state='�ڿ�' and a.serial_num='" + serial_num + "'";
		
		Object obj = this.queryForObject(sql, new ProductRowMapper());
		if(obj != null){
			product = (Product)obj;
		}
		return product;
	}
	
	
	/**
	 * �鿴���к��Ƿ��ڿ�
	 * @param serial_num
	 * @param product_id
	 * @return
	 */
	public String productIsExist(String serial_num){
		String flag = "false";
		String sql = "select * from serial_num_mng where serial_num='" + serial_num + "' and state='�ڿ�'";
		List list = this.getResultList(sql);
		if(list != null && list.size() > 0){
			flag = "true";
		}
		return flag;
	}
	
	/**
	 * ���ݲ�Ʒ��ż����кŲ�ѯ��Ʒ�Ƿ����
	 * @param serial_num
	 * @param product_id
	 * @return
	 */
	public String productIsExist(String serial_num,String product_id){
		String flag = "false";
		String sql = "select * from serial_num_mng where product_id='" + product_id + "' and serial_num='" + serial_num + "' and state='�ڿ�'";
		List list = this.getResultList(sql);
		if(list != null && list.size() > 0){
			flag = "true";
		}
		return flag;
	}	
	
	
	/**
	 * ���ݲ�Ʒ���ȡ�����޼�
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
	 * ���ݲ�Ʒ���ȡ�����޼�
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
	 * ����������ѯ���в�Ʒ�б�
	 * @param product_kind  ��Ʒ���
	 * @param product_name  ��Ʒ����
	 * @param product_xh    ��Ʒ���
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
	 * �ж���Ʒ�Ƿ����ɾ��<BR>
	 * ����ҵ�����ݵ���Ʒ����ɾ��<BR>
	 * ҵ�����ݰ��������ۡ����ۡ��˻����ɹ����ɹ��˻����������롢����������<BR>
	 * ��Ϊ������⣬������ӣ�ֻ������Ӧ�������ɣ����Բ��ڿ��Ƿ�Χ��
	 * @param product_id  ��Ʒ���
	 * @return boolean true:���ԣ�false:������
	 */
	public boolean isCanDel(String product_id){
		
		//�ж��Ƿ������ۣ�������������򷵻�false
		String sql = "select count(*) as counts from lsd_product where product_id='" + product_id + "'";		
		if(this.getJdbcTemplate().queryForInt(sql) > 0){
			return false;
		}
		
		//�ж��Ƿ������ۣ�������������򷵻�false
		sql = "select count(*) as counts from xsd_product where product_id='" + product_id + "'";
		if(this.getJdbcTemplate().queryForInt(sql) > 0){
			return false;
		}
		
		//�ж��Ƿ��������˻���������������˻��򷵻�false
		sql = "select count(*) as counts from thd_product where product_id='" + product_id + "'";
		if(this.getJdbcTemplate().queryForInt(sql) > 0){
			return false;
		}
		
		//�ж��Ƿ����ɹ�����������ɹ��򷵻�false
		sql = "select count(*) as counts from jhd_product where product_id='" + product_id + "'";
		if(this.getJdbcTemplate().queryForInt(sql) > 0){
			return false;
		}
		
		//�ж��Ƿ����ɹ��˻�����������ɹ��˻��򷵻�false
		sql = "select count(*) as counts from cgthd_product where product_id='" + product_id + "'";
		if(this.getJdbcTemplate().queryForInt(sql) > 0){
			return false;
		}	
		
		//�ж��Ƿ����������룬����������������򷵻�false
		sql = "select count(*) as counts from dbsq_product where product_id='" + product_id + "'";
		if(this.getJdbcTemplate().queryForInt(sql) > 0){
			return false;
		}
		
		//�ж��Ƿ���������������������򷵻�false
		sql = "select count(*) as counts from kfdb_product where product_id='" + product_id + "'";
		if(this.getJdbcTemplate().queryForInt(sql) > 0){
			return false;
		}	
		
		//�ж��Ƿ���������ۣ����������������򷵻�false
		sql = "select count(*) as counts from chtj_desc where product_id='" + product_id + "'";
		if(this.getJdbcTemplate().queryForInt(sql) > 0){
			return false;
		}			
		
		return true;
	}
	

	/**
	 * ��װ����
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
