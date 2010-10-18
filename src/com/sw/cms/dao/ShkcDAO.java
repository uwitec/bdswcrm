package com.sw.cms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

import com.sw.cms.dao.XsdDAO.XsdRowMapper;
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
import com.sw.cms.util.StringUtils;

/**
 * ά�޿��DAO
 * @author Administrator
 *
 */
public class ShkcDAO extends JdbcBaseDAO
{
	
	/**
	 * �ۺ����б�����ҳ��
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
	 * ����ID��ѯ�ۺ���
	 * @param id
	 * @return
	 */
	public Object getShkcById(String id)
	{
		String sql="select a.*,b.client_name,b.linkman,b.lxdh,b.mobile from shkc a left join jjd_product c on a.qz_serial_num=c.qz_serial_num left join jjd b on b.id=c.jjd_id  where a.id="+id+"";
	    return  this.getJdbcTemplate().queryForObject(sql, new ShkcRowMapper());
		 
	}
	/**
	 * �������б�(����ҳ)
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	//�ڻ�������
	public Page getShkuIsBadProduct(String con,int curPage,int rowsPerPage)
	{
		String sqlStore=""; 
		String storeId="";
		sqlStore= "select id from storehouse where name='������'";
		 
		Map map=getResultMap(sqlStore);
	
		storeId= (String)map.get("id") ; 
		
		String sql="select a.product_id, a.product_name,a.product_xh,a.qz_serial_num,sum(a.nums) as hj_nums,b.qz_serial_num as qz_flag from shkc a left join product b on a.product_id=b.product_id where a.state='1' and a.store_id='"+storeId+"'";
		
		if(!con.equals(""))
		{
			sql=sql+con;
		}
		sql=sql+" group by a.product_id, a.product_name,a.product_xh,a.qz_serial_num,qz_flag";
		return this.getResultByPage(sql, curPage, rowsPerPage);
	}
	/**
	 * �ü����б�(����ҳ)
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	//�ü���
	public Page getShkcIsHaoProduct(String con,int curPage,int rowsPerPage)
	{
		String sqlStore=""; 
		String storeId="";
		sqlStore= "select id from storehouse where name='�ü���'";		
		Map map=getResultMap(sqlStore);
		
		storeId= (String)map.get("id") ; 
	    
		String sql="select a.product_id, a.product_name,a.product_xh,a.qz_serial_num,sum(a.nums) as hj_nums,b.qz_serial_num as qz_flag from shkc a left join product b on a.product_id=b.product_id where  a.store_id='"+storeId+"'";
		if(!con.equals(""))
		{
			sql=sql+con;
		}
		sql=sql+" group by a.product_id, a.product_name,a.product_xh,a.qz_serial_num,qz_flag";
		return this.getResultByPage(sql, curPage, rowsPerPage);
	}
	//�����
	public Page getShkcIsWaiProduct(String con,int curPage,int rowsPerPage)
	{
		String sqlStore=""; 
		String storeId="";
		sqlStore= "select id from storehouse where name='������'";		
        Map map=getResultMap(sqlStore);		
		storeId= (String)map.get("id"); 
	    
		String sql="select a.product_id, a.product_name,a.product_xh,a.qz_serial_num,sum(a.nums) as hj_nums,b.qz_serial_num as qz_flag from shkc a left join product b on a.product_id=b.product_id where a.state='2' and a.store_id='"+storeId+"'";
		if(!con.equals(""))
		{
			sql=sql+con;
		}
		sql=sql+" group by a.product_id, a.product_name,a.product_xh,a.qz_serial_num,qz_flag";
		return this.getResultByPage(sql, curPage, rowsPerPage);
	}
	/**
	 * �������кŲ�ѯά�޿��
	 * @param serialNum
	 * @return
	 */
   public int getShkcBySerialNum(String serialNum)
   {
	   String sql="select count(*) from shkc where qz_serial_num='"+serialNum+"'";
	  return this.getJdbcTemplate().queryForInt(sql);
   }
   
   /**
    * �������кŲ�ѯ�������Ƿ��и���Ʒ
    * @param serialNum
    * @return
    */
   public int getBadShkcBySerialNum(String serialNum)
   {
	   String sqlStore=""; 
	   String storeId="";
	   sqlStore= "select id from storehouse where name='������'";		
	   Map map=getResultMap(sqlStore);		
	   storeId= (String)map.get("id"); 
	   
	   String sql="select count(*) from shkc where state='1' and qz_serial_num='"+serialNum+"' and store_id='"+storeId+"'";
	   return this.getJdbcTemplate().queryForInt(sql);
   }
   
   /**
    * �������кŲ�ѯ�ü����Ƿ��и���Ʒ
    * @param serialNum
    * @return
    */
   public int getHaoShkcBySerialNum(String serialNum)
   {
	   String sqlStore=""; 
	   String storeId="";
	   sqlStore= "select id from storehouse where name='�ü���'";		
	   Map map=getResultMap(sqlStore);		
	   storeId= (String)map.get("id"); 
	   
	   String sql="select count(*) from shkc where qz_serial_num='"+serialNum+"'and store_id='"+storeId+"'";
	   return this.getJdbcTemplate().queryForInt(sql);
   }
   
   /**
    * �������кŲ�ѯ������Ƿ��и���Ʒ
    * @param serialNum
    * @return
    */
   public int getWeiShkcBySerialNum(String serialNum)
   {
	   String sqlStore="";
	   String storeId="";
	   sqlStore= "select id from storehouse where name='������'";		
	   Map map=getResultMap(sqlStore);		
	   storeId= (String)map.get("id"); 
	   
	   String sql="select count(*) from shkc where state='2' and qz_serial_num='"+serialNum+"' and store_id='"+storeId+"'";
	   return this.getJdbcTemplate().queryForInt(sql);
   }
   
   /**
    * �������кŲ�ѯ�������Ƿ��и���Ʒ
    * @param serialNum
    * @return
    */
   public int getBfShkcBySerialNum(String serialNum)
   {
	   String sqlStore=""; 
	   String storeId="";
	   sqlStore= "select id from storehouse where name='������'";		
	   Map map=getResultMap(sqlStore);		
	   storeId= (String)map.get("id"); 
	   
	   String sql="select count(*) from shkc where state='4' and qz_serial_num='"+serialNum+"' and store_id='"+storeId+"'";
	   return this.getJdbcTemplate().queryForInt(sql);
   }
   
   /**
    * ����ۺ���
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
    * �������к�ɾ�����
    * @param serial_num
    */
   public void deleteShkcById(String product_id,String serial_num)
   {
	   String sqlStore="";
	   String storeId="";
	   sqlStore= "select id from storehouse where name='�ü���'";		
	   Map map=getResultMap(sqlStore);		
	   storeId= (String)map.get("id"); 
	   String sql="update shkc set state='',store_id=''  where  qz_serial_num='"+serial_num+"' and store_id='"+storeId+"' and product_id='"+product_id+"'";
	   this.getJdbcTemplate().update(sql);
   }
   
   public void deleteShkcWaiById(String product_id,String serial_num)
   {
	   String sqlStore="";
	   String storeId="";
	   sqlStore= "select id from storehouse where name='������'";		
	   Map map=getResultMap(sqlStore);		
	   storeId= (String)map.get("id"); 
	   String sql="delete from shkc where state='2' and qz_serial_num='"+serial_num+"' and store_id='"+storeId+"' and product_id='"+product_id+"'";
	   this.getJdbcTemplate().update(sql);
   }
   
   
   public void deleteShkcHaoById(String product_id,String serial_num)
   {
	   String sqlStore="";
	   String storeId="";
	   sqlStore= "select id from storehouse where name='�ü���'";		
	   Map map=getResultMap(sqlStore);		
	   storeId= (String)map.get("id"); 
	   String sql="delete from shkc where state='3' and qz_serial_num='"+serial_num+"' and store_id='"+storeId+"' and product_id='"+product_id+"'";
	   this.getJdbcTemplate().update(sql);
   }
   /**
    * �޸ĽӼ��ۺ��棨1�������� 2������� 3���ü��⣩
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
		   sqlStore= "select id from storehouse where name='������'";		
	   }
	   else if (state.equals(state2))
	   {
		   sqlStore= "select id from storehouse where name='������'";	   
	   }
	   else
	   {
		   sqlStore= "select id from storehouse where name='�ü���'";	  
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
				  if(!jjdProduct.getQz_serial_num().equals(""))
				  {
					String[] arryNums = (jjdProduct.getQz_serial_num()).split(",");
					
					for(int k=0;k<arryNums.length;k++)
				    {	
                      if((!arryNums[k].equals("")) && (!jjdProduct.getProduct_id().equals("")))
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
				  else
					{
					    boolean flag=isExist(jjdProduct.getProduct_id(),state);
						if(flag==true)
						{
                            //��ǰ��Ʒ�ܿ����
							int nums = 0;
							sql = "select nums from shkc where product_id='" + jjdProduct.getProduct_id() + "' and state='" +state + "'";	
							Map mapShkc = this.getResultMap(sql);
							String strNums = StringUtils.nullToStr(mapShkc.get("nums"));
							if(!strNums.equals("")){
								nums = (new Integer(strNums)).intValue();
							}
						  	sql="update shkc set nums=? where product_id=? and store_id=? and state=?";
						  	Object[] param=new Object[4];
						  	param[0]=jjdProduct.getNums()+nums;
						  	param[1]=jjdProduct.getProduct_id();
						  	param[2]=storeId;
						  	param[3]=state; 
						  	this.getJdbcTemplate().update(sql,param);						  	
						}
						else
						{
						  if(!jjdProduct.getProduct_id().equals(""))
						  {
						     sql="insert into shkc(product_id,product_xh,product_name,qz_serial_num,state,store_id,nums) values(?,?,?,?,?,?,?)";
	                         Object[]param=new Object[7];
	                         param[0]=jjdProduct.getProduct_id();
	                         param[1]=jjdProduct.getProduct_xh();
	                         param[2]=jjdProduct.getProduct_name();
	                         param[3]=jjdProduct.getQz_serial_num();
	                         param[4]=state; 
	                         param[5]=storeId;
	                         param[6]=jjdProduct.getNums();

	                         this.getJdbcTemplate().update(sql,param);
						  }
						}
					}
				  
				}
				
			}
		}
	                  
}
  
   private boolean isExist(String product_id,String state)
   {
	   boolean flag=false;
	   int count=productCount(product_id,state);	   
	    
	   if(count!=0)
	   {
			flag=true;
	   }
		
		return flag;
   }
  
   private int productCount(String product_id,String state)
   {
	   String sql="select count(*) from shkc where product_id='" + product_id + "' and state='"+ state + "'";
	   return this.getJdbcTemplate().queryForInt(sql);
   }
   
   public Object getShkc(String product_id,String state)
   {
	   String sql = "select * from shkc where product_id='" + product_id + "' and state='"+ state + "'";
		
		return this.queryForObject(sql, new ShkcRowMapper());
   }
   
   /**
    * ά����ⵥ�޸��ۺ��棨0��������1�������2���ü��⣩
    *
    */
   public void updateWxShkcState(String serialNum,String state)
   {
	   String sqlStore="";
	  
	   sqlStore= "select id from storehouse where name='�ü���'";	  
	   
	   Map map=getResultMap(sqlStore);		
	   String storeId= (String)map.get("id") ; 
	   String sql="update shkc set state='"+state+"',store_id='"+storeId+"' where qz_serial_num='"+serialNum+"'";
	   this.getJdbcTemplate().update(sql);
   }
   
   /**
    * �޸��ۺ��棨1�������� 2������� 3���ü��⣩
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
		   sqlStore= "select id from storehouse where name='������'";		
	   }
	   else if (state.equals(state2))
	   {
		   sqlStore= "select id from storehouse where name='������'";	   
	   }
	   else
	   {
		   sqlStore= "select id from storehouse where name='�ü���'";	  
	   }
	   Map map=getResultMap(sqlStore);		
	   String storeId= (String)map.get("id") ; 
	   
       sql="update shkc set state='"+state+"',store_id='"+storeId+"' where qz_serial_num='"+arryNums+"'";
       this.getJdbcTemplate().update(sql);
   }
   
   /**
    * �޸��ۺ�����Ʒ����ǿ�����кŵģ�1�������� 2������� 3���ü��⣩
    *
    */
   public void updateShkcStateNums(String product_id,int nums,String state,String oldState)
   {
	   String sqlStore="";
	   String state1="1";
	   String state2="2";
	   String  sql="";
	   if (state.equals(state1))
	   {
		   sqlStore= "select id from storehouse where name='������'";		
	   }
	   else if (state.equals(state2))
	   {
		   sqlStore= "select id from storehouse where name='������'";	   
	   }
	   else
	   {
		   sqlStore= "select id from storehouse where name='�ü���'";	  
	   }
	   Map map=getResultMap(sqlStore);		
	   String storeId= (String)map.get("id") ; 
	   if(state.equals(""))
	   {
		  storeId=""; 
	   }
	   boolean flag=isExist(product_id,state);
		if(flag==true)
		{
           //��ǰ��Ʒ�ܿ����
			int numsTemp = 0;	
			sql = "select * from shkc where product_id='" + product_id + "' and state='" + state + "'";	
			Map mapShkc = this.getResultMap(sql);
			String strNums = StringUtils.nullToStr(mapShkc.get("nums"));
			if(!strNums.equals("")){
				numsTemp = (new Integer(strNums)).intValue();
			}
		  	sql="update shkc set nums=? where product_id=? and store_id=? and state=?";
		  	Object[] param=new Object[4];
		  	param[0]=numsTemp+nums;
		  	param[1]=product_id;
		  	param[2]=storeId;
		  	param[3]=state; 
		  	this.getJdbcTemplate().update(sql,param);						  	
		}
		else
		{	
		  sql = "select * from shkc where product_id='" + product_id + "' and state='" + oldState + "'";	
		  Map mapShkc = this.getResultMap(sql);
	      sql="insert into shkc(product_id,product_xh,product_name,state,store_id,nums) values(?,?,?,?,?,?);";
          Object[]param=new Object[6];
          param[0]=product_id;
          param[1]=StringUtils.nullToStr(mapShkc.get("product_xh"));
          param[2]=StringUtils.nullToStr(mapShkc.get("product_name"));       
          param[3]=state; 
          param[4]=storeId;
          param[5]=nums;

          this.getJdbcTemplate().update(sql,param);   
		}
       
   }
   
   /**
    * �޸��ۺ�����Ʒ����ǿ�����кŵģ�1�������� 2������� 3���ü��⣩
    *
    */
   public void updateShkcNums(String product_id,int nums,String state,String oldState)
   {
	   String  sql="";
	   
	   int numsTemp = 0;
		sql = "select * from shkc where product_id='" + product_id + "' and state='"+oldState+"'";	
		Map mapShkc = this.getResultMap(sql);
		String strNums = StringUtils.nullToStr(mapShkc.get("nums"));
		if(!strNums.equals("")){
			numsTemp = (new Integer(strNums)).intValue();
		}
	  
	   sql="update shkc set nums="+(numsTemp-nums)+" where product_id='"+product_id+"' and state='"+oldState+"'";
	   
	   this.getJdbcTemplate().update(sql);
   }
   
   /**
    * �޸��ۺ�����Ʒ����ǿ�����кŵģ�1�������� 2������� 3���ü��⣩
    *
    */
   public void updateShkcStateAll(String product_id,int nums,String state,String oldState)
   {
	   String sqlStore="";	  
	   String  sql="";
	   if (state.equals("3"))
	   {
		   sqlStore= "select id from storehouse where name='�ü���'";		
	   }
	   else 
	   {
		   sqlStore= "select id from storehouse where name='������'";	   
	   }
	   Map map=getResultMap(sqlStore);		
	   String storeId= (String)map.get("id") ; 
	   
	   if(state.equals(""))
	   {
		   storeId="";
	   }
	   boolean flag=isExist(product_id,state);
		if(flag==true)
		{
          //��ǰ��Ʒ�ܿ����
			int numsTemp = 0;	
			sql = "select * from shkc where product_id='" + product_id + "' and state='" + state + "'";	
			Map mapShkc = this.getResultMap(sql);
			String strNums = StringUtils.nullToStr(mapShkc.get("nums"));
			if(!strNums.equals("")){
				numsTemp = (new Integer(strNums)).intValue();
			}
		  	sql="update shkc set nums=? where product_id=? and store_id=? and state=?";
		  	Object[] param=new Object[4];
		  	param[0]=numsTemp+nums;
		  	param[1]=product_id;
		  	param[2]=storeId;
		  	param[3]=state; 
		  	this.getJdbcTemplate().update(sql,param);						  	
		}
		else
		{	
	       sql="update shkc set state='"+state+"',store_id='"+storeId+"' where product_id='"+product_id+"' and state='"+oldState+"'";
	   
	       this.getJdbcTemplate().update(sql);
		}
   }
   
   /**
    * �޸ı��޷����ۺ��棨1�������� 2�������  3:�ü��⣩
    *
    */
   public void updateBxfhShkcState(List bxfhdProducts,String state)
   {
	   String sqlStore="";
	   
	   String  sql="";
	   if (state.equals("3"))
	   {
		   sqlStore= "select id from storehouse where name='�ü���'";		
	   }
	   else 
	   {
		   sqlStore= "select id from storehouse where name='������'";	   
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
						if((bxfhdProduct.getQz_serial_num() != null) && (!bxfhdProduct.getQz_serial_num().equals("")))
						{
						   String[] arryNums = (bxfhdProduct.getQz_serial_num()).split(",");
						
						   for(int k=0;k<arryNums.length;k++)
					       {
	                         sql="update shkc set state='"+state+"',store_id='"+storeId+"' where qz_serial_num='"+arryNums[k]+"'";
	                         this.getJdbcTemplate().update(sql);
					       }
						}
						else
						{
							Shkc shkc = (Shkc)getShkc(bxfhdProduct.getProduct_id(),"2");
							if((shkc.getNums()-bxfhdProduct.getNums())==0)
							{
							    updateShkcStateAll(bxfhdProduct.getProduct_id(),bxfhdProduct.getNums(),"3","2");
							}
							else
							{
								updateShkcNums(bxfhdProduct.getProduct_id(),bxfhdProduct.getNums(),"3","2");
								updateShkcStateNums(bxfhdProduct.getProduct_id(),bxfhdProduct.getNums(),"3","2");
							}
						}
					   
					  }					
	                  
					}
				}
			}
		}
   }
   
   /**
    * �޸ı����ۺ��棨1�������� 2������� 3���ü��⣩
    *
    */
   public void updateBfShkcState(String serial,String state)
   {
	   String sqlStore="";	   
	   String  sql="";
	   if (state.equals("3"))
	   {
		   sqlStore= "select id from storehouse where name='�ü���'";		
	   }
	   else 
	   {
		   sqlStore= "select id from storehouse where name='������'";	   
	   }
	   
	   Map map=getResultMap(sqlStore);		
	   String storeId= (String)map.get("id") ; 
       sql="update shkc set state='"+state+"',store_id='"+storeId+"' where qz_serial_num='"+serial+"'";
       this.getJdbcTemplate().update(sql);
   }
   
   /**
    * �޸Ļ����ۺ��棨1�������� 2������� 3���ü��⣩
    *
    */
   public void updateHjShkcState(HjdProduct hjdProduct)
   {
	   String sqlStore="";
	   String  sql="";
	   sqlStore= "select id from storehouse where name='�ü���'";		
	   
	   Map map=getResultMap(sqlStore);		
	   String storeId= (String)map.get("id") ; 
       sql="update shkc set qz_serial_num='"+hjdProduct.getNqz_serial_num()+"' where qz_serial_num='"+hjdProduct.getOqz_serial_num()+"' and store_id='"+storeId+"'";
       this.getJdbcTemplate().update(sql);
   }
   
   /**
	 * �����ۺ��ID
	 * 
	 * @return
	 */
	public String getShfwId(String name) {
		String sql = "select id from storehouse where name='"+name+"'";

        //ȡID��
		Map map=getResultMap(sql);		
		String storeId= (String)map.get("id") ; 
		return  storeId;
	}
	
	/**
	 * <p>�ж����к��Ƿ����</p>
	 * @param serial_num ���к�
	 * @return ���ڷ���true;�����ڷ���false
	 */
	public String SerialIsExist(String serial_num,String product_id){
		String is = "false";		
		String sqlStore=""; 
		String storeId="";
		sqlStore= "select id from storehouse where name='�ü���'";
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
    * �ۺ����װ��
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
		   if(SqlUtil.columnIsExist(rs,"nums"))shkc.setNums(rs.getInt("nums"));
		   if(SqlUtil.columnIsExist(rs,"client_name"))shkc.setClient_name(rs.getString("client_name"));
		   if(SqlUtil.columnIsExist(rs,"lxdh"))shkc.setLxdh(rs.getString("lxdh"));
		   if(SqlUtil.columnIsExist(rs,"mobile"))shkc.setMobile(rs.getString("mobile"));
		   if(SqlUtil.columnIsExist(rs,"linkman"))shkc.setLinkman(rs.getString("linkman"));
		   
		   return shkc;
	   }
   }
}
