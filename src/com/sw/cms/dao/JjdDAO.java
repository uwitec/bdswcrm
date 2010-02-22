package com.sw.cms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

import com.sw.cms.dao.base.JdbcBaseDAO;
import com.sw.cms.dao.base.SqlUtil;
import com.sw.cms.model.Jjd;
import com.sw.cms.model.JjdProduct;
import com.sw.cms.model.Page;
import com.sw.cms.util.DateComFunc;

public class JjdDAO extends JdbcBaseDAO {
	/**
	 * 返回接修单列表（带分页）
	 * 
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getJjdList(String con, int curPage, int rowsPerPage) 
	{
		String sql = "select j.id,j.client_name,j.linkman,j.mobile,j.jj_date,j.state,j.jjr,j.cjr from jjd j left join sys_user s on j.jjr=s.user_id  where 1=1 "; 
		if(!con.equals(""))
		{
			sql=sql+con;
		}
		return this.getResultByPage(sql, curPage, rowsPerPage);
	}
	
	/**
	 * 获取接件单ID
	 * @return
	 */
	public String getJjdId()
	{
		String sql="select jjdid from cms_all_seq";
		String jjdid=this.getJdbcTemplate().queryForInt(sql)+"";
		sql="update cms_all_seq set jjdid=jjdid+1";
		this.getJdbcTemplate().update(sql);
		String curdate= DateComFunc.getCurDay();
		
		for(int i=jjdid.length();i<3;i++)
		{
			jjdid="0"+jjdid;
		}
		return "JJ"+curdate+"-"+jjdid;
	}
	
	/**
	 * 保存接件单及商品
	 * @param jjd
	 * @param jjdProduct
	 */
	public void saveJjd(Jjd jjd,List jjdProduct)
	{
		String sql="insert into jjd(id,sfd_id,client_name,mail,mobile,linkman,jjr,cjr,jj_date,cj_date,state,ms)values(?,?,?,?,?,?,?,?,?,?,?,?)";
		Object param[]=new Object[12]; 
		param[0]=jjd.getId();
		param[1]=jjd.getSfd_id();
		param[2]=jjd.getClient_name();
		param[3]=jjd.getMail();
		param[4]=jjd.getMobile();
		param[5]=jjd.getLinkman();
		param[6]=jjd.getJjr();
		param[7]=jjd.getCjr();
		param[8]=jjd.getJj_date();	
		param[9]=DateComFunc.getToday();
		param[10]=jjd.getState();
		param[11]=jjd.getMs();
		
		this.getJdbcTemplate().update(sql,param);
		this.addJjdProducts(jjdProduct,jjd.getId());
		
	}
	/**
	 * 保存接件单商品信息
	 * @param jjdProducts
	 * @param jjdid
	 */
	public void addJjdProducts(List jjdProducts,String jjdid)
	{
		String sql="";
		Object []param=new Object[11];
		
		String sqlStore=""; 
		sqlStore= "select id from storehouse where name='坏件库'";		
		Map map=getResultMap(sqlStore);		
		String storeId= (String)map.get("id") ;
		
		if(jjdProducts!=null&&jjdProducts.size()>0)
		{
			for(int i=0;i<jjdProducts.size();i++)
			{
				JjdProduct jjdProduct=(JjdProduct)jjdProducts.get(i);
				 if(jjdProduct!=null)					
				 {
				   if(!jjdProduct.getProduct_name().equals("")&&!jjdProduct.getQz_serial_num().equals(""))
				   {
					 sql="insert into jjd_product(jjd_id,product_id,product_name,product_xh,qz_serial_num,remark,nums,cpfj,store_id,storestate,fxts)values(?,?,?,?,?,?,?,?,?,?,?)";
				     
					 param[0]=jjdid;
				     param[1]=jjdProduct.getProduct_id();
				     param[2]=jjdProduct.getProduct_name();
				     param[3]=jjdProduct.getProduct_xh();
				     param[4]=jjdProduct.getQz_serial_num();
				     param[5]=jjdProduct.getRemark();
				     param[6]=jjdProduct.getNums();
				     param[7]=jjdProduct.getCpfj();
				     param[8]=storeId;
				     param[9]="1";
				     param[10]=jjdProduct.getFxts();
					 this.getJdbcTemplate().update(sql,param);				   
				   }
				 }
				   
			}
		}
	}
	/**
	 * 删除接件单及相关产品
	 * @param id
	 */
	public void delJjd(String id)
	{
		String sql="delete from jjd where id='"+id+"'";
		this.getJdbcTemplate().update(sql);
		sql="delete from jjd_product where jjd_id='"+id+"'";
		this.getJdbcTemplate().update(sql);
		
	}
	
	/**
	 * 删除接件单商品
	 * @param id
	 */
	public void delJjdProduct(String id)
	{
		String sql="delete from jjd_Product where jjd_id='"+id+"'";
		this.getJdbcTemplate().update(sql);
	}
	/**
	 * 根据接件单ID获取接件单产品
	 * @param id
	 * @return
	 */
	public List getJjdProducts(String id)
	{
		 String sql="select * from jjd_product where jjd_id='"+id+"'";
		 
		return  this.getJdbcTemplate().query(sql, new JjdProductRowMapper());
	}
	
	/**
	 * 根据接件单ID获取接件单
	 * @param id
	 * @return
	 */
	public Object getJjd(String id)
	{
		String sql="select * from jjd where id='"+id+"'";
		return this.getJdbcTemplate().queryForObject(sql, new JjdRowMapper());
	}
	
	/**
	 * 修改接件单信息
	 * @param jjd
	 * @param jjdProduct
	 */
	public void updateJjd(Jjd jjd,List jjdProduct)
	{
		String sql="update jjd set client_name=?,lxdh=?,mobile=?,linkman=?,jjr=?,jj_date=?,state=?,ms=? where id=?";
		Object param[]=new Object[9];
		param[0]=jjd.getClient_name();
		param[1]=jjd.getLxdh();
		param[2]=jjd.getMobile();
		param[3]=jjd.getLinkman();
		param[4]=jjd.getJjr();
		param[5]=jjd.getJj_date();
		param[6]=jjd.getState();
		param[7]=jjd.getMs();
		param[8]=jjd.getId();
		this.getJdbcTemplate().update(sql,param);
		delJjdProduct(jjd.getId());
		addJjdProducts(jjdProduct,jjd.getId());
	}
	
	
	/**
	 * 接件单封装对象
	 * @author Administrator
	 *
	 */
	class JjdRowMapper implements RowMapper
	{
		public Object mapRow(ResultSet rs, int index) throws SQLException 
		{
			Jjd jjd=new Jjd();
			if (SqlUtil.columnIsExist(rs, "id"))jjd.setId(rs.getString("id"));
			if (SqlUtil.columnIsExist(rs, "sfd_id"))jjd.setSfd_id(rs.getString("sfd_id"));
			if (SqlUtil.columnIsExist(rs, "client_name"))jjd.setClient_name(rs.getString("client_name"));
			//if (SqlUtil.columnIsExist(rs, "address"))jjd.setAddress(rs.getString("address"));
			if (SqlUtil.columnIsExist(rs, "mobile"))jjd.setMobile(rs.getString("mobile"));
			if (SqlUtil.columnIsExist(rs, "linkman"))jjd.setLinkman(rs.getString("linkman"));
			if (SqlUtil.columnIsExist(rs, "jjr"))jjd.setJjr(rs.getString("jjr"));
			if (SqlUtil.columnIsExist(rs, "cjr"))jjd.setCjr(rs.getString("cjr"));
			if (SqlUtil.columnIsExist(rs, "jj_date"))jjd.setJj_date(rs.getString("jj_date"));
			if (SqlUtil.columnIsExist(rs, "cj_date"))jjd.setCj_date(rs.getString("cj_date"));
			if (SqlUtil.columnIsExist(rs, "state"))jjd.setState(rs.getString("state"));
			if (SqlUtil.columnIsExist(rs, "ms"))jjd.setMs(rs.getString("ms"));
			if (SqlUtil.columnIsExist(rs, "lxdh"))jjd.setLxdh(rs.getString("lxdh"));
			if (SqlUtil.columnIsExist(rs, "mail"))jjd.setMail(rs.getString("mail"));
			return jjd;
		}
	}
	/**
	 * 接件单商品封装对象
	 * @author Administrator
	 *
	 */
	class JjdProductRowMapper implements RowMapper
	{ 
	    public Object mapRow(ResultSet rs,int index)throws SQLException
	    {
	    	JjdProduct jjdProduct=new JjdProduct();
	    	if (SqlUtil.columnIsExist(rs, "id"))jjdProduct.setId(rs.getInt("id"));
			if (SqlUtil.columnIsExist(rs, "jjd_id"))jjdProduct.setJjd_id(rs.getString("jjd_id"));
			if (SqlUtil.columnIsExist(rs, "product_id"))jjdProduct.setProduct_id(rs.getString("product_id"));
			if (SqlUtil.columnIsExist(rs, "product_name"))jjdProduct.setProduct_name(rs.getString("product_name"));
			if (SqlUtil.columnIsExist(rs, "product_xh"))jjdProduct.setProduct_xh(rs.getString("product_xh"));
			if (SqlUtil.columnIsExist(rs, "qz_serial_num"))jjdProduct.setQz_serial_num(rs.getString("qz_serial_num"));
			if (SqlUtil.columnIsExist(rs, "remark"))jjdProduct.setRemark(rs.getString("remark"));
			if(SqlUtil.columnIsExist(rs, "nums"))jjdProduct.setNums(rs.getInt("nums"));
			if (SqlUtil.columnIsExist(rs, "store_id"))jjdProduct.setStord_id(rs.getString("store_id"));
			if (SqlUtil.columnIsExist(rs, "storestate"))jjdProduct.setStorestate(rs.getString("storestate"));
			if (SqlUtil.columnIsExist(rs, "cpfj"))jjdProduct.setCpfj(rs.getString("cpfj"));
			if(SqlUtil.columnIsExist(rs, "fxts"))jjdProduct.setFxts(rs.getInt("fxts"));
	    	return jjdProduct;
	    }  
	}
	 
}
