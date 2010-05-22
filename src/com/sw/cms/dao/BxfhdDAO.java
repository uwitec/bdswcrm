package com.sw.cms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

import com.sw.cms.dao.BxdDAO.BxdProductRowMapper;
import com.sw.cms.dao.BxdDAO.BxdRowMapper;
import com.sw.cms.dao.base.JdbcBaseDAO;
import com.sw.cms.dao.base.SqlUtil;
import com.sw.cms.model.Bxd;
import com.sw.cms.model.BxdProduct;
import com.sw.cms.model.Bxfhd;
import com.sw.cms.model.BxfhdProduct;
import com.sw.cms.model.Page;
import com.sw.cms.util.DateComFunc;

public class BxfhdDAO extends JdbcBaseDAO
{
  /**
   * 报修返还单列表（带分页）
   * @param con
   * @param curPage
   * @param rowsPerPage
   * @return
   */
  public Page getBxfhdList(String con,int curPage,int rowsPerPage)
  {
	  String sql="select b.*,c.name as bxcs_name,(select sum(p.nums) as totalNums  from bxfhd_product p where b.id=p.bxfhd_id ) as productNums from bxfhd b left join clients c  on c.id=b.bxcs   where 1=1  ";
	  if(!con.equals(""))
	  {
		  sql=sql+con;
	  }
	 return this.getResultByPage(sql, curPage, rowsPerPage);
  }
  
  /**
	 * 根据报修返还单ID返回保修返还单所还商品
	 * 
	 * @param bxfhd_id
	 * @return
	 */
	public List getBxfhdProducts(String bxfhd_id) {
		String sql = "select * from bxfhd_product where bxfhd_id='" + bxfhd_id + "'";
		return this.getJdbcTemplate().query(sql, new BxfhdProductRowMapper());
	}
  
	/**
	 * 根据报修返还单ID返回报修返还单
	 * 
	 * @param bxfhd_id
	 * @return
	 */
	public Object getBxfhd(String bxfhd_id) {
		String sql = "select *  from bxfhd where id='" + bxfhd_id + "'";
		return this.getJdbcTemplate().queryForObject(sql, new BxfhdRowMapper());
	}
	
  /**
	 * 返回报修返还单可用ID
	 * 
	 * @return
	 */
	public String getBxfhdId() 
	{
		String sql = "select bxfhdid from cms_all_seq";

		// 取当前序列号
		String curId = this.getJdbcTemplate().queryForInt(sql) + "";

		String day = DateComFunc.getCurDay();

		// 将序列号加1
		sql = "update cms_all_seq set bxfhdid=bxfhdid+1";
		this.getJdbcTemplate().update(sql);

		for (int i = curId.length(); i < 3; i++) {
			curId = "0" + curId;
		}

		return "BXFH" + day + "-" + curId;

	}
   
  /**
   * 根据ID获取报修返还单商品
   * @param bxfhd_id
   * @return
   */
  public Object getBxfhdProductById(String bxfhd_id)
  {
	 String sql="select *from bxfhd_product where bxfhd_id='"+bxfhd_id+"'";
	 return this.queryForObject(sql, new BxfhdProductRowMapper());
  }
	/**
	 * 保存报修返还单
	 * @param bxfhd
	 * @param bxfhdProduct
	 */
  public void saveBxfhd(Bxfhd bxfhd,List bxfhdProducts)
  {
	  Object[]param=new Object[11];
	  String sql="insert into bxfhd(id,fh_date,cj_date,jsr,cjr,state,Bxcs,remark,fkzh,ssje,hjje)values(?,?,?,?,?,?,?,?,?,?,?)";
	  
	  param[0]=bxfhd.getId();
	  param[1]=bxfhd.getFh_date();
	  param[2]=DateComFunc.getToday();
	  param[3]=bxfhd.getJsr();
	  param[4]=bxfhd.getCjr();
	  param[5]=bxfhd.getState();
	  param[6]=bxfhd.getBxcs();
 	  param[7]=bxfhd.getRemark();
	  param[8]=bxfhd.getFkzh();
	  param[9]=bxfhd.getSsje();
	  param[10]=bxfhd.getHjje();
	 
	  this.getJdbcTemplate().update(sql,param);
      //插入对应的商品返回单的商品信息 
      this.addBxfhdProduct(bxfhd, bxfhdProducts);
  }
   
  /**
   * 修改报修返还单
   * @param bxfhd
   * @param bxfhdProduct
   */
  public void updateBxfhd(Bxfhd bxfhd,List bxfhdProducts)
  {//更新报修返回单信息
		Object param[] = new Object[10];
	    String sql="update bxfhd set fh_date=?,cj_date=?,jsr=?,cjr=?,state=?,bxcs=?,remark=?,fkzh=?,ssje=?  where id=?";
	    param[0]=bxfhd.getFh_date();
	    param[1]=DateComFunc.getToday();
	    param[2]=bxfhd.getJsr();
	    param[3]=bxfhd.getCjr();	  
	    param[4]=bxfhd.getState();
	    param[5]=bxfhd.getBxcs();	  
	    param[6]=bxfhd.getRemark();	  
	    param[7]=bxfhd.getFkzh();
	    param[8]=bxfhd.getSsje();	 
	    param[9]=bxfhd.getId();
	    this.getJdbcTemplate().update(sql,param);
	    //删除对应的报修返还单的商品信息
	    this.deleteBxfhdProduct(bxfhd.getId());
	    //更新对应的商品返回单的商品信息 
	    this.addBxfhdProduct(bxfhd, bxfhdProducts);  
  }
  
  /**
   * 修改报修返还单商品
   * @param bxfhd
   * @param bxfhdProduct
   */
  private void addBxfhdProduct(Bxfhd bxfhd,List bxfhdProducts)
  {
	  Object []param=new Object[12];
	  String bxfhd_id = bxfhd.getId();
	  
	  String sqlStore=""; 
	  sqlStore= "select id from storehouse where name='好件库'";		
	  Map map=getResultMap(sqlStore);		
	  String storeId= (String)map.get("id") ; 
		
	  if(bxfhdProducts != null && bxfhdProducts.size()>0)
	    {
	       for(int i=0;i<bxfhdProducts.size();i++)
	       {
	          BxfhdProduct bxfhdProduct = (BxfhdProduct)bxfhdProducts.get(i);
	          if(bxfhdProduct != null)
	          {
	        	if(!bxfhdProduct.getProduct_id().equals("") && !bxfhdProduct.getProduct_name().equals(""))
	        	{
	               String sql="insert into bxfhd_product(bxfhd_id,product_id,product_name,product_xh,qz_serial_num,remark,store_id,storestate,nums,cpfj,price,totalmoney)values(?,?,?,?,?,?,?,?,?,?,?,?)";
	  
	               param[0]=bxfhd_id;	   
	               param[1]=bxfhdProduct.getProduct_id();
	               param[2]=bxfhdProduct.getProduct_name();
	               param[3]=bxfhdProduct.getProduct_xh();
	               param[4]=bxfhdProduct.getQz_serial_num();
	               param[5]=bxfhdProduct.getRemark();
	               param[6]=storeId;
	               param[7]="";
	               param[8]=new Integer(bxfhdProduct.getNums());
	               param[9]=bxfhdProduct.getCpfj();
	               param[10]=new Double(bxfhdProduct.getPrice());	               
	               param[11]=new Double(bxfhdProduct.getTotalmoney());	               
	               this.getJdbcTemplate().update(sql,param);
	        	}
	          }
	       }
	    }
  }
  
  /**
   * 删除报修返还单商品
   * @param bxfhd_id
   */
  public void deleteBxfhdProduct(String bxfhd_id)
  {
	  String sql="delete from bxfhd_product where bxfhd_id='"+bxfhd_id+"'";
	  this.getJdbcTemplate().update(sql);
  }
  
  /**
	 * 删除报修返还单
	 * 
	 * @param bxfhd_id
	 */
  public void delBxfhd(String bxfhd_id) 
  {
	String sql = "delete from bxfhd where id='" + bxfhd_id + "'";
	this.getJdbcTemplate().update(sql);
	sql = "delete from bxfhd_product where bxfhd_id='" + bxfhd_id + "'";
	this.getJdbcTemplate().update(sql);
  }
  
  /**
   * 获取报修返还单ID
   *
   */
  public String updateBxfhId()
  {
	  String sql="select bxfhdid from cms_all_seq";
	  String bxfhdid=this.getJdbcTemplate().queryForInt(sql)+"";
	  sql="update cms_all_seq set bxfhdid=bxfhdid+1";
	  this.getJdbcTemplate().update(sql);
	  for(int i=bxfhdid.length();i<3;i++)
	  {
		  bxfhdid="0"+bxfhdid;
	  }
	  String day = DateComFunc.getCurDay();

		return "BF" + day + "-" + bxfhdid;
  }
  
  /**
	 * 查看报修返还单是否已经提交
	 * @param ckd_id
	 * @return
	 */
	public boolean isBxfhdSubmit(String bxfhd_id){
		boolean is = false;
		String sql = "select count(*) from bxfhd where id='" + bxfhd_id + "' and state='已提交'";
		int counts = this.getJdbcTemplate().queryForInt(sql);
		if(counts > 0){
			is = true;
		}
		return is;
	}
	
  /**
   * 报修返还单包装类
   * @author Administrator
   *
   */
  class BxfhdRowMapper  implements RowMapper
  {
	  public Object mapRow(ResultSet rs,int index)throws SQLException
	  {
		  Bxfhd bxfhd=new Bxfhd();
	      if (SqlUtil.columnIsExist(rs, "id"))bxfhd.setId(rs.getString("id"));
	      if (SqlUtil.columnIsExist(rs, "fh_date"))bxfhd.setFh_date(rs.getString("fh_date"));
	      if (SqlUtil.columnIsExist(rs, "cj_date"))bxfhd.setCj_date(rs.getString("cj_date"));
	      if (SqlUtil.columnIsExist(rs, "jsr"))bxfhd.setJsr(rs.getString("jsr"));
	      if (SqlUtil.columnIsExist(rs, "cjr"))bxfhd.setCjr(rs.getString("cjr"));	      
	      if (SqlUtil.columnIsExist(rs, "state"))bxfhd.setState(rs.getString("state"));
	      if (SqlUtil.columnIsExist(rs, "bxcs"))bxfhd.setBxcs(rs.getString("bxcs"));	      
	      if (SqlUtil.columnIsExist(rs, "remark"))bxfhd.setRemark(rs.getString("remark"));	     
	      if (SqlUtil.columnIsExist(rs, "fkzh"))bxfhd.setFkzh(rs.getString("fkzh"));
	      if (SqlUtil.columnIsExist(rs, "ssje"))bxfhd.setSsje(rs.getDouble("ssje"));
	      if (SqlUtil.columnIsExist(rs, "hjje"))bxfhd.setHjje(rs.getDouble("hjje"));	     				
		  return bxfhd;
	  }
  }
  /**
   * 报修返还单商品包装类
   * @author Administrator
   *
   */
  class BxfhdProductRowMapper implements RowMapper
  {
	  public Object mapRow(ResultSet rs,int index)throws SQLException
	  {
		  BxfhdProduct bxfhdProduct=new BxfhdProduct();
		  if(SqlUtil.columnIsExist(rs, "id"))bxfhdProduct.setId(rs.getInt("id"));
		  if(SqlUtil.columnIsExist(rs, "bxfhd_id"))bxfhdProduct.setBxfhd_id(rs.getString("bxfhd_id"));
		  if(SqlUtil.columnIsExist(rs, "product_id"))bxfhdProduct.setProduct_id(rs.getString("product_id"));
		  if(SqlUtil.columnIsExist(rs, "product_name"))bxfhdProduct.setProduct_name(rs.getString("product_name"));
		  if(SqlUtil.columnIsExist(rs, "product_xh"))bxfhdProduct.setProduct_xh(rs.getString("product_xh"));
		  if(SqlUtil.columnIsExist(rs, "qz_serial_num"))bxfhdProduct.setQz_serial_num(rs.getString("qz_serial_num"));
		  if(SqlUtil.columnIsExist(rs, "remark"))bxfhdProduct.setRemark(rs.getString("remark"));
		  if(SqlUtil.columnIsExist(rs, "store_id"))bxfhdProduct.setStord_id(rs.getString("store_id"));
		  if(SqlUtil.columnIsExist(rs, "storestate"))bxfhdProduct.setStorestate(rs.getString("storestate"));
		  if(SqlUtil.columnIsExist(rs, "nums"))bxfhdProduct.setNums(rs.getInt("nums"));
		  if(SqlUtil.columnIsExist(rs, "price"))bxfhdProduct.setPrice(rs.getDouble("price"));
		  if(SqlUtil.columnIsExist(rs, "cpfj"))bxfhdProduct.setCpfj(rs.getString("cpfj"));
		  if(SqlUtil.columnIsExist(rs, "totalmoney"))bxfhdProduct.setTotalmoney(rs.getDouble("totalmoney"));
		  return bxfhdProduct;
	  }
  }
}
