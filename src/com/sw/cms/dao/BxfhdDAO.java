package com.sw.cms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.sw.cms.dao.base.JdbcBaseDAO;
import com.sw.cms.dao.base.SqlUtil;
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
	  String sql="select b.* from bxfhd  b left join bxfhd_product p on b.id=p.bxfhd_id left join sys_user s on b.jxr=s.user_id  where 1=1 ";
	  if(!con.equals(""))
	  {
		  sql=sql+con;
	  }
	 return this.getResultByPage(sql, curPage, rowsPerPage);
  }
  
  /**
   * 根据ID获取报修返还单
   * @param bxfhd_id
   * @return
   */
  public Object getBxfhdById(String bxfhd_id)
  {
	String sql="select *from bxfhd where id='"+bxfhd_id+"'";
	return this.queryForObject(sql, new BxfhdRowMapper());
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
  public void saveBxfhd(Bxfhd bxfhd,BxfhdProduct bxfhdProduct)
  {
	  String sql="insert into bxfhd(id,fh_date,cj_date,jxr,cjr,gcs,state,client_name,lxr,lxdh,address,ms,bxd_id,bxr,fkzh,ssje,isfy)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	  Object[]param=new Object[17];
	  param[0]=bxfhd.getId();
	  param[1]=bxfhd.getFh_date();
	  param[2]=bxfhd.getCj_date();
	  param[3]=bxfhd.getJxr();
	  param[4]=bxfhd.getCjr();
	  param[5]=bxfhd.getGcs();
	  param[6]=bxfhd.getState();
	  param[7]=bxfhd.getClient_name();
	  param[8]=bxfhd.getLxr();
	  param[9]=bxfhd.getLxdh();
	  param[10]=bxfhd.getAddress();
	  param[11]=bxfhd.getMs();
	  param[12]=bxfhd.getBxd_id();
	  param[13]=bxfhd.getBxr();
	  param[14]=bxfhd.getFkzh();
	  param[15]=bxfhd.getSsje();
	  param[16]=bxfhd.getIsfy();
	  this.getJdbcTemplate().update(sql,param);
	  saveBxfhdProduct(bxfhd.getId(),bxfhdProduct);
  }
  /**
   * 保存报修返还单商品
   * @param bxfhd_id
   * @param bxfhdProduct
   */
  public void saveBxfhdProduct(String bxfhd_id,BxfhdProduct bxfhdProduct)
  {
	  String sql="insert into bxfhd_product(bxfhd_id,product_id,product_name,product_xh,qz_serial_num,remark,bxaddress,bxstate,has_fj,fj,qtfj,bj,gzfx,pcgc,wxf)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	  Object []param=new Object[15];
	  param[0]=bxfhdProduct.getBxfhd_id();
	  param[1]=bxfhdProduct.getProduct_id();
	  param[2]=bxfhdProduct.getProduct_name();
	  param[3]=bxfhdProduct.getProduct_xh();
	  param[4]=bxfhdProduct.getQz_serial_num();
	  param[5]=bxfhdProduct.getRemark();
	  param[6]=bxfhdProduct.getBxaddress();
	  param[7]=bxfhdProduct.getBxstate();
	  param[8]=bxfhdProduct.getHas_fj();
	  param[9]=bxfhdProduct.getFj();
	  param[10]=bxfhdProduct.getQtfj();
	  param[11]=bxfhdProduct.getBj();
	  param[12]=bxfhdProduct.getGzfx();
	  param[13]=bxfhdProduct.getPcgc();
	  param[14]=bxfhdProduct.getWxf();
	  this.getJdbcTemplate().update(sql,param);
  }
  
  /**
   * 修改报修返还单
   * @param bxfhd
   * @param bxfhdProduct
   */
  public void updateBxfhd(Bxfhd bxfhd,BxfhdProduct bxfhdProduct)
  {
	  String sql="update bxfhd set fh_date=?,cj_date=?,jxr=?,cjr=?,gcs=?,state=?,client_name=?,lxr=?,lxdh=?,address=?,ms=?,bxd_id=?,bxr=?,fkzh=?,ssje=?,isfy=?  where id=?";
	  Object[]param=new Object[17];	  
	  param[0]=bxfhd.getFh_date();
	  param[1]=bxfhd.getCj_date();
	  param[2]=bxfhd.getJxr();
	  param[3]=bxfhd.getCjr();
	  param[4]=bxfhd.getGcs();
	  param[5]=bxfhd.getState();
	  param[6]=bxfhd.getClient_name();
	  param[7]=bxfhd.getLxr();
	  param[8]=bxfhd.getLxdh();
	  param[9]=bxfhd.getAddress();
	  param[10]=bxfhd.getMs();
	  param[11]=bxfhd.getBxd_id();
	  param[12]=bxfhd.getBxr();
	  param[13]=bxfhd.getFkzh();
	  param[14]=bxfhd.getSsje();
	  param[15]=bxfhd.getIsfy();
	  param[16]=bxfhd.getId();
	  this.getJdbcTemplate().update(sql,param);
	  this.deleteBxfhdProduct(bxfhd.getId());
	  this.updateBxfhdProduct(bxfhd.getId(), bxfhdProduct);
	  
  }
  
  /**
   * 修改报修返还单商品
   * @param bxfhd
   * @param bxfhdProduct
   */
  private void updateBxfhdProduct(String bxfhd_id,BxfhdProduct bxfhdProduct)
  {
	  String sql="insert into bxfhd_product(bxfhd_id,product_id,product_name,product_xh,qz_serial_num,remark,bxaddress,bxstate,has_fj,fj,qtfj,bj,gzfx,pcgc,wxf)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	  Object []param=new Object[15];
	  param[0]=bxfhd_id;	   
	  param[1]=bxfhdProduct.getProduct_id();
	  param[2]=bxfhdProduct.getProduct_name();
	  param[3]=bxfhdProduct.getProduct_xh();
	  param[4]=bxfhdProduct.getQz_serial_num();
	  param[5]=bxfhdProduct.getRemark();
	  param[6]=bxfhdProduct.getBxaddress();
	  param[7]=bxfhdProduct.getBxstate();
	  param[8]=bxfhdProduct.getHas_fj();
	  param[9]=bxfhdProduct.getFj();
	  param[10]=bxfhdProduct.getQtfj();
	  param[11]=bxfhdProduct.getBj();
	  param[12]=bxfhdProduct.getGzfx();
	  param[13]=bxfhdProduct.getPcgc();
	  param[14]=bxfhdProduct.getWxf();
	  this.getJdbcTemplate().update(sql,param);
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
	      if (SqlUtil.columnIsExist(rs, "jxr"))bxfhd.setJxr(rs.getString("jxr"));
	      if (SqlUtil.columnIsExist(rs, "cjr"))bxfhd.setCjr(rs.getString("cjr"));
	      if (SqlUtil.columnIsExist(rs, "gcs"))bxfhd.setGcs(rs.getString("gcs"));
	      if (SqlUtil.columnIsExist(rs, "state"))bxfhd.setState(rs.getString("state"));
	      if (SqlUtil.columnIsExist(rs, "client_name"))bxfhd.setClient_name(rs.getString("client_name"));
	      if (SqlUtil.columnIsExist(rs, "lxr"))bxfhd.setLxr(rs.getString("lxr"));
	      if (SqlUtil.columnIsExist(rs, "lxdh"))bxfhd.setLxdh(rs.getString("lxdh"));
	      if (SqlUtil.columnIsExist(rs, "address"))bxfhd.setAddress(rs.getString("address"));
	      if (SqlUtil.columnIsExist(rs, "ms"))bxfhd.setMs(rs.getString("ms"));
	      if (SqlUtil.columnIsExist(rs, "bxd_id"))bxfhd.setBxd_id(rs.getString("bxd_id"));
	      if (SqlUtil.columnIsExist(rs, "bxr"))bxfhd.setBxr(rs.getString("bxr"));
	      if (SqlUtil.columnIsExist(rs, "fkzh"))bxfhd.setFkzh(rs.getString("fkzh"));
	      if (SqlUtil.columnIsExist(rs, "ssje"))bxfhd.setSsje(rs.getDouble("ssje"));
	      if (SqlUtil.columnIsExist(rs, "isfy"))bxfhd.setIsfy(rs.getString("isfy"));
	     				
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
		  if(SqlUtil.columnIsExist(rs, "bxaddress"))bxfhdProduct.setBxaddress(rs.getString("bxaddress"));
		  if(SqlUtil.columnIsExist(rs, "bxstate"))bxfhdProduct.setBxstate(rs.getString("bxstate"));
		  if(SqlUtil.columnIsExist(rs, "has_fj"))bxfhdProduct.setHas_fj(rs.getString("has_fj"));
		  if(SqlUtil.columnIsExist(rs, "fj"))bxfhdProduct.setFj(rs.getString("fj"));
		  if(SqlUtil.columnIsExist(rs, "qtfj"))bxfhdProduct.setQtfj(rs.getString("qtfj"));
		  if(SqlUtil.columnIsExist(rs, "bj"))bxfhdProduct.setBj(rs.getString("bj"));
		  if(SqlUtil.columnIsExist(rs, "gzfx"))bxfhdProduct.setGzfx(rs.getString("gzfx"));
		  if(SqlUtil.columnIsExist(rs, "pcgc"))bxfhdProduct.setPcgc(rs.getString("pcgc"));
		  if(SqlUtil.columnIsExist(rs, "wxf"))bxfhdProduct.setWxf(rs.getDouble("wxf"));
		  return bxfhdProduct;
	  }
  }
}
