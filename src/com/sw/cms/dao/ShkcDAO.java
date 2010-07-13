package com.sw.cms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

import com.sw.cms.dao.base.JdbcBaseDAO;
import com.sw.cms.dao.base.SqlUtil;
import com.sw.cms.model.BxdProduct;
import com.sw.cms.model.BxfhdProduct;
import com.sw.cms.model.FhkhdProduct;
import com.sw.cms.model.JjdProduct;
import com.sw.cms.model.BfdProduct;
import com.sw.cms.model.HjdProduct;
import com.sw.cms.model.Product;
import com.sw.cms.model.WxrkdProduct;
import com.sw.cms.model.Page;
import com.sw.cms.model.Shkc;
import com.sw.cms.util.DateComFunc;

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
		String sql="select a.*,b.client_name,b.linkman,b.lxdh,b.mobile from shkc a left join jjd_product c on a.qz_serial_num=c.qz_serial_num left join jjd b on b.id=c.jjd_id  where 1=1 and (a.store_id<>'' and a.state<>'4') ";
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
		String sql="select a.*,b.client_name,b.linkman,b.lxdh,b.mobile from shkc a left join jjd_product c on a.qz_serial_num=c.qz_serial_num left join jjd b on b.id=c.jjd_id  where a.id="+id+"";
	    return  this.getJdbcTemplate().queryForObject(sql, new ShkcRowMapper());
		 
	}
	/**
	 * 坏件库列表(带分页)
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	//在坏件库中
	public Page getShkuIsBadProduct(String con,int curPage,int rowsPerPage)
	{
		String sqlStore=""; 
		String storeId="";
		sqlStore= "select id from storehouse where name='坏件库'";
		 
		Map map=getResultMap(sqlStore);
	
		storeId= (String)map.get("id") ; 
		
		String sql="select product_id, product_name,product_xh,qz_serial_num,sum(nums) as hj_nums from shkc  where state='1' and store_id='"+storeId+"'";
		
		if(!con.equals(""))
		{
			sql=sql+con;
		}
		sql=sql+" group by product_id, product_name,product_xh,qz_serial_num";
		return this.getResultByPage(sql, curPage, rowsPerPage);
	}
	/**
	 * 好件库列表(带分页)
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	//好件库
	public Page getShkcIsHaoProduct(String con,int curPage,int rowsPerPage)
	{
		String sqlStore=""; 
		String storeId="";
		sqlStore= "select id from storehouse where name='好件库'";		
		Map map=getResultMap(sqlStore);
		
		storeId= (String)map.get("id") ; 
	    
		String sql="select product_id, product_name,product_xh,qz_serial_num,sum(nums) as hj_nums from shkc  where  store_id='"+storeId+"'";
		if(!con.equals(""))
		{
			sql=sql+con;
		}
		sql=sql+" group by product_id, product_name,product_xh,qz_serial_num";
		return this.getResultByPage(sql, curPage, rowsPerPage);
	}
	//在外库
	public Page getShkcIsWaiProduct(String con,int curPage,int rowsPerPage)
	{
		String sqlStore=""; 
		String storeId="";
		sqlStore= "select id from storehouse where name='坏件库'";		
        Map map=getResultMap(sqlStore);		
		storeId= (String)map.get("id"); 
	    
		String sql="select product_id, product_name,product_xh,qz_serial_num,sum(nums) as hj_nums from shkc  where state='2' and store_id='"+storeId+"'";
		if(!con.equals(""))
		{
			sql=sql+con;
		}
		sql=sql+" group by product_id, product_name,product_xh,qz_serial_num";
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
	   String sqlStore=""; 
	   String storeId="";
	   sqlStore= "select id from storehouse where name='坏件库'";		
	   Map map=getResultMap(sqlStore);		
	   storeId= (String)map.get("id"); 
	   
	   String sql="select count(*) from shkc where state='1' and qz_serial_num='"+serialNum+"' and store_id='"+storeId+"'";
	   return this.getJdbcTemplate().queryForInt(sql);
   }
   
   /**
    * 根据序列号查询好件库是否有该商品
    * @param serialNum
    * @return
    */
   public int getHaoShkcBySerialNum(String serialNum)
   {
	   String sqlStore=""; 
	   String storeId="";
	   sqlStore= "select id from storehouse where name='好件库'";		
	   Map map=getResultMap(sqlStore);		
	   storeId= (String)map.get("id"); 
	   
	   String sql="select count(*) from shkc where qz_serial_num='"+serialNum+"'and store_id='"+storeId+"'";
	   return this.getJdbcTemplate().queryForInt(sql);
   }
   
   /**
    * 根据序列号查询在外库是否有该商品
    * @param serialNum
    * @return
    */
   public int getWeiShkcBySerialNum(String serialNum)
   {
	   String sqlStore="";
	   String storeId="";
	   sqlStore= "select id from storehouse where name='坏件库'";		
	   Map map=getResultMap(sqlStore);		
	   storeId= (String)map.get("id"); 
	   
	   String sql="select count(*) from shkc where state='2' and qz_serial_num='"+serialNum+"' and store_id='"+storeId+"'";
	   return this.getJdbcTemplate().queryForInt(sql);
   }
   
   /**
    * 根据序列号查询报废中是否有该商品
    * @param serialNum
    * @return
    */
   public int getBfShkcBySerialNum(String serialNum)
   {
	   String sqlStore=""; 
	   String storeId="";
	   sqlStore= "select id from storehouse where name='坏件库'";		
	   Map map=getResultMap(sqlStore);		
	   storeId= (String)map.get("id"); 
	   
	   String sql="select count(*) from shkc where state='4' and qz_serial_num='"+serialNum+"' and store_id='"+storeId+"'";
	   return this.getJdbcTemplate().queryForInt(sql);
   }
   
   /**
    * 添加售后库存
    * @param jjdProducts
    */
   public void saveShkc(Shkc  shkc)
   {
	   String sql="insert into shkc(product_id,product_xh,product_name,qz_serial_num,state,store_id)values(?,?,?,?,?,?)";
	   Object[]param=new Object[6];
	   param[0]=shkc.getProduct_id();
	   param[1]=shkc.getProduct_xh();
	   param[2]=shkc.getProduct_name();
	   param[3]=shkc.getQz_serial_num();
	   param[4]=shkc.getState(); 
	   param[5]=shkc.getStore_id();

	   this.getJdbcTemplate().update(sql,param);	   
   }
   
   /**
    * 根据序列号删除库存
    * @param serial_num
    */
   public void deleteShkcById(String product_id,String serial_num)
   {
	   String sqlStore="";
	   String storeId="";
	   sqlStore= "select id from storehouse where name='好件库'";		
	   Map map=getResultMap(sqlStore);		
	   storeId= (String)map.get("id"); 
	   String sql="update shkc set state='',store_id=''  where  qz_serial_num='"+serial_num+"' and store_id='"+storeId+"' and product_id='"+product_id+"'";
	   this.getJdbcTemplate().update(sql);
   }
   
   public void deleteShkcWaiById(String product_id,String serial_num)
   {
	   String sqlStore="";
	   String storeId="";
	   sqlStore= "select id from storehouse where name='坏件库'";		
	   Map map=getResultMap(sqlStore);		
	   storeId= (String)map.get("id"); 
	   String sql="delete from shkc where state='2' and qz_serial_num='"+serial_num+"' and store_id='"+storeId+"' and product_id='"+product_id+"'";
	   this.getJdbcTemplate().update(sql);
   }
   
   
   public void deleteShkcHaoById(String product_id,String serial_num)
   {
	   String sqlStore="";
	   String storeId="";
	   sqlStore= "select id from storehouse where name='好件库'";		
	   Map map=getResultMap(sqlStore);		
	   storeId= (String)map.get("id"); 
	   String sql="delete from shkc where state='3' and qz_serial_num='"+serial_num+"' and store_id='"+storeId+"' and product_id='"+product_id+"'";
	   this.getJdbcTemplate().update(sql);
   }
   /**
    * 修改接件售后库存（1：坏件库 2：在外库 3：好件库）
    *
    */
   public void insertJjShkcState(List jjdProducts,String state)
   {
	   String sqlStore="";
	   String state1="1";
	   String state2="2";
	   String  sql="";
	   if (state.equals(state1))
	   {
		   sqlStore= "select id from storehouse where name='坏件库'";		
	   }
	   else if (state.equals(state2))
	   {
		   sqlStore= "select id from storehouse where name='坏件库'";	   
	   }
	   else
	   {
		   sqlStore= "select id from storehouse where name='好件库'";	  
	   }
	   Map map=getResultMap(sqlStore);		
	   String storeId= (String)map.get("id") ; 
	   if(jjdProducts != null && jjdProducts.size()>0)
		{
			for(int i=0;i<jjdProducts.size();i++)
			{
				JjdProduct jjdProduct = (JjdProduct)jjdProducts.get(i);
				if(jjdProduct != null)
				{
					String[] arryNums = (jjdProduct.getQz_serial_num()).split(",");
					
					for(int k=0;k<arryNums.length;k++)
				   {	
                      sql="insert into shkc(product_id,product_xh,product_name,qz_serial_num,state,store_id) values(?,?,?,?,?,?)";
                      Object[]param=new Object[6];
                      param[0]=jjdProduct.getProduct_id();
                      param[1]=jjdProduct.getProduct_xh();
                      param[2]=jjdProduct.getProduct_name();
                      param[3]=arryNums[k];
                      param[4]=state; 
                      param[5]=storeId;

                      this.getJdbcTemplate().update(sql,param);
				   }
				}
			}
		}
	                  
}

   /**
    * 维修入库单修改售后库存（0：坏件库1：在外库2：好件库）
    *
    */
   public void updateWxShkcState(String serialNum,String state)
   {
	   String sqlStore="";
	  
	   sqlStore= "select id from storehouse where name='好件库'";	  
	   
	   Map map=getResultMap(sqlStore);		
	   String storeId= (String)map.get("id") ; 
	   String sql="update shkc set state='"+state+"',store_id='"+storeId+"' where qz_serial_num='"+serialNum+"'";
	   this.getJdbcTemplate().update(sql);
   }
   
   /**
    * 修改售后库存（1：坏件库 2：在外库 3：好件库）
    *
    */
   public void updateShkcState(String arryNums,String state)
   {
	   String sqlStore="";
	   String state1="1";
	   String state2="2";
	   String  sql="";
	   if (state.equals(state1))
	   {
		   sqlStore= "select id from storehouse where name='坏件库'";		
	   }
	   else if (state.equals(state2))
	   {
		   sqlStore= "select id from storehouse where name='坏件库'";	   
	   }
	   else
	   {
		   sqlStore= "select id from storehouse where name='好件库'";	  
	   }
	   Map map=getResultMap(sqlStore);		
	   String storeId= (String)map.get("id") ; 
       sql="update shkc set state='"+state+"',store_id='"+storeId+"' where qz_serial_num='"+arryNums+"'";
       this.getJdbcTemplate().update(sql);
   }
   
   /**
    * 修改报修返还售后库存（1：坏件库 2：在外库  3:好件库）
    *
    */
   public void updateBxfhShkcState(List bxfhdProducts,String state)
   {
	   String sqlStore="";
	   String state1="1";
	   String state2="2";
	   String  sql="";
	   if (state.equals(state1))
	   {
		   sqlStore= "select id from storehouse where name='坏件库'";		
	   }
	   else if (state.equals(state2))
	   {
		   sqlStore= "select id from storehouse where name='坏件库'";	   
	   }
	   else
	   {
		   sqlStore= "select id from storehouse where name='好件库'";	  
	   }
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
					{ if(!state.equals(""))
					 {
						String[] arryNums = (bxfhdProduct.getQz_serial_num()).split(",");
						
						for(int k=0;k<arryNums.length;k++)
					   {
	                    sql="update shkc set state='"+state+"',store_id='"+storeId+"' where qz_serial_num='"+arryNums[k]+"'";
	                    this.getJdbcTemplate().update(sql);
					   }
					   
					 }					
	                  
					}
				}
			}
		}
   }
   
   /**
    * 修改报废售后库存（1：坏件库 2：在外库 3：好件库）
    *
    */
   public void updateBfShkcState(BfdProduct bfdProduct,String state)
   {
	   String sqlStore="";	   
	   String  sql="";
	   if (state.equals("3"))
	   {
		   sqlStore= "select id from storehouse where name='好件库'";		
	   }
	   else 
	   {
		   sqlStore= "select id from storehouse where name='坏件库'";	   
	   }
	   
	   Map map=getResultMap(sqlStore);		
	   String storeId= (String)map.get("id") ; 
       sql="update shkc set state='"+state+"',store_id='"+storeId+"' where qz_serial_num='"+bfdProduct.getQz_serial_num()+"'";
       this.getJdbcTemplate().update(sql);
   }
   
   /**
    * 修改换件售后库存（1：坏件库 2：在外库 3：好件库）
    *
    */
   public void updateHjShkcState(HjdProduct hjdProduct)
   {
	   String sqlStore="";
	   String  sql="";
	   sqlStore= "select id from storehouse where name='好件库'";		
	   
	   Map map=getResultMap(sqlStore);		
	   String storeId= (String)map.get("id") ; 
       sql="update shkc set qz_serial_num='"+hjdProduct.getNqz_serial_num()+"' where qz_serial_num='"+hjdProduct.getOqz_serial_num()+"' and store_id='"+storeId+"'";
       this.getJdbcTemplate().update(sql);
   }
   
   /**
	 * 返回售后库ID
	 * 
	 * @return
	 */
	public String getShfwId(String name) {
		String sql = "select id from storehouse where name='"+name+"'";

        //取ID号
		Map map=getResultMap(sql);		
		String storeId= (String)map.get("id") ; 
		return  storeId;
	}
	
	/**
	 * <p>判断序列号是否存在</p>
	 * @param serial_num 序列号
	 * @return 存在返回true;不存在返回false
	 */
	public String SerialIsExist(String serial_num,String product_id){
		String is = "false";		
		String sqlStore=""; 
		String storeId="";
		sqlStore= "select id from storehouse where name='好件库'";
		Map map=getResultMap(sqlStore);
	
		storeId= (String)map.get("id") ; 
		String sql = "select a.*,b.qz_serial_num as qz_flag from shkc a left join product b on b.product_id=a.product_id "+
		             " where a.state='3' and a.qz_serial_num='" + serial_num + "' and a.store_id='"+storeId+"' and a.product_id='"+product_id+"'";
		
		List list = this.getResultList(sql);
		if(list != null && list.size()>0){
			is = "true";
		}
		return is;
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
		   if(SqlUtil.columnIsExist(rs,"state"))shkc.setState(rs.getString("state"));
		   if(SqlUtil.columnIsExist(rs,"store_id"))shkc.setStore_id(rs.getString("store_id"));
		   if(SqlUtil.columnIsExist(rs,"day_num"))shkc.setDay_num(rs.getInt("day_num")+"");
	   
		   if(SqlUtil.columnIsExist(rs,"client_name"))shkc.setClient_name(rs.getString("client_name"));
		   if(SqlUtil.columnIsExist(rs,"lxdh"))shkc.setLxdh(rs.getString("lxdh"));
		   if(SqlUtil.columnIsExist(rs,"mobile"))shkc.setMobile(rs.getString("mobile"));
		   if(SqlUtil.columnIsExist(rs,"linkman"))shkc.setLinkman(rs.getString("linkman"));
		   
		   return shkc;
	   }
   }
}
