package com.sw.cms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

import com.sw.cms.dao.base.JdbcBaseDAO;
import com.sw.cms.dao.base.SqlUtil;
import com.sw.cms.model.JjdProduct;
import com.sw.cms.model.Page;
import com.sw.cms.model.Shkc;

/**
 * 维修库存DAO
 * @author Administrator
 *
 */
public class ShkcDAO extends JdbcBaseDAO
{
	
	/**
	 * 售后库存列表（带分页）
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getShkcProduct(String con,int curPage,int rowsPerPage)
	{
		String sql="select * from shkc where 1=1 ";
		if(!con.equals(""))
		{
			sql=sql+con;
		}
		return this.getResultByPage(sql, curPage, rowsPerPage);
	}
	/**
	 * 根据ID查询售后库存
	 * @param id
	 * @return
	 */
	public Object getShkcById(String id)
	{
		String sql="select * from shkc where id="+id+"";
	    return  this.getJdbcTemplate().queryForObject(sql, new ShkcRowMapper());
		 
	}
	/**
	 * 坏件库列表(带分页)
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getShkuIsBadProduct(String con,int curPage,int rowsPerPage)
	{
		String sql="select * from shkc where state='0'";
		if(!con.equals(""))
		{
			sql=sql+con;
		}
		return this.getResultByPage(sql, curPage, rowsPerPage);
	}
	/**
	 * 好件库列表(带分页)
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getShkcIsHaoProduct(String con,int curPage,int rowsPerPage)
	{
		String sql="select * from shkc where state='2'";
		if(!con.equals(""))
		{
			sql=sql+con;
		}
		return this.getResultByPage(sql, curPage, rowsPerPage);
	}
	/**
	 * 根据序列号查询维修库存
	 * @param serialNum
	 * @return
	 */
   public int getShkcBySerialNum(String serialNum)
   {
	   String sql="select count(*)from shkc where qz_serial_num='"+serialNum+"'";
	  return this.getJdbcTemplate().queryForInt(sql);
   }
   
   /**
    * 根据序列号查询坏件库是否有该商品
    * @param serialNum
    * @return
    */
   public int getBadShkcBySerialNum(String serialNum)
   {
	   String sql="select count(*) from shkc where state='0' and qz_serial_num='"+serialNum+"'";
	   return this.getJdbcTemplate().queryForInt(sql);
   }
   
   /**
    * 根据序列号查询好件库是否有该商品
    * @param serialNum
    * @return
    */
   public int getHaoShkcBySerialNum(String serialNum)
   {
	   String sql="select count(*) from shkc where state='2' and qz_serial_num='"+serialNum+"'";
	   return this.getJdbcTemplate().queryForInt(sql);
   }
   
   /**
    * 添加售后库存
    * @param jjdProducts
    */
   public void saveShkc(Shkc  shkc)
   {
	   String sql="insert into shkc(product_id,product_xh,product_name,qz_serial_num,state,remark,client_name,linkman,mobile,address,wxf)values(?,?,?,?,?,?,?,?,?,?,?)";
	   Object[]param=new Object[11];
	   param[0]=shkc.getProduct_id();
	   param[1]=shkc.getProduct_xh();
	   param[2]=shkc.getProduct_name();
	   param[3]=shkc.getQz_serial_num();
	   param[4]=shkc.getState();
	   param[5]=shkc.getRemark();
	   param[6]=shkc.getClient_name();
	   param[7]=shkc.getLinkman();
	   param[8]=shkc.getMobile();
	   param[9]=shkc.getAddress();
	   param[10]=shkc.getWxf();
	   this.getJdbcTemplate().update(sql,param);	   
   }
   
   /**
    * 根据序列号删除库存
    * @param serial_num
    */
   public void deleteShkcById(String serial_num)
   {
	   String sql="delete from shkc where state='2' and qz_serial_num='"+serial_num+"'";
	   this.getJdbcTemplate().update(sql);
   }
   /**
    * 修改售后库存（0：坏件库1：在外库2：好件库）
    *
    */
   public void updateShkcState(String serialNum,String state)
   {
	   String sql="update shkc set state='"+state+"' where qz_serial_num='"+serialNum+"'";
	   this.getJdbcTemplate().update(sql);
   }
   /**
    * 售后库存封装类
    * @author Administrator
    *
    */
   class ShkcRowMapper  implements RowMapper
   {
	   public Object mapRow(ResultSet rs,int index)throws SQLException
	   {
		   Shkc shkc=new Shkc();
		   if(SqlUtil.columnIsExist(rs,"id"))shkc.setId(rs.getInt("id"));
		   if(SqlUtil.columnIsExist(rs,"product_id"))shkc.setProduct_id(rs.getString("product_id"));
		   if(SqlUtil.columnIsExist(rs,"product_xh"))shkc.setProduct_xh(rs.getString("product_xh"));
		   if(SqlUtil.columnIsExist(rs,"product_name"))shkc.setProduct_name(rs.getString("product_name"));
		   if(SqlUtil.columnIsExist(rs,"qz_serial_num"))shkc.setQz_serial_num(rs.getString("qz_serial_num"));
		   if(SqlUtil.columnIsExist(rs,"remark"))shkc.setRemark(rs.getString("remark"));
		   if(SqlUtil.columnIsExist(rs,"state"))shkc.setState(rs.getString("state"));
		   if(SqlUtil.columnIsExist(rs,"client_name"))shkc.setClient_name(rs.getString("client_name"));
		   if(SqlUtil.columnIsExist(rs,"linkman"))shkc.setLinkman(rs.getString("linkman"));
		   if(SqlUtil.columnIsExist(rs,"mobile"))shkc.setMobile(rs.getString("mobile"));
		   if(SqlUtil.columnIsExist(rs,"address"))shkc.setAddress(rs.getString("address"));
		   if(SqlUtil.columnIsExist(rs,"day_num"))shkc.setDay_num(rs.getInt("day_num")+"");
		   if(SqlUtil.columnIsExist(rs, "wxf"))shkc.setWxf(rs.getDouble("wxf"));
		   
		   return shkc;
	   }
   }
}
