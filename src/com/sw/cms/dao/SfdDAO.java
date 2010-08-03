package com.sw.cms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.sw.cms.dao.base.BeanRowMapper;
import com.sw.cms.dao.base.JdbcBaseDAO;
import com.sw.cms.dao.base.SqlUtil;
import com.sw.cms.model.Bxd;
import com.sw.cms.model.BxdProduct;
import com.sw.cms.model.Jhd;
import com.sw.cms.model.Page;
import com.sw.cms.model.Sfd;
import com.sw.cms.util.DateComFunc;

public class SfdDAO extends JdbcBaseDAO
{
	/**
	 * �ۺ�����б�����ҳ��
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
   public Page getSfdList(String con,int curPage,int rowsPerPage)
   {
	   String sql="select f.* from sfd f left join  sys_user s on f.jxr=s.user_id  where 1=1 ";  
	   if(!con.equals(""))
	   {
		   sql=sql+con;
	   }
	   return this.getResultByPage(sql, curPage, rowsPerPage);
   }
   
   /**
    * ɾ���ۺ����
    * @param id
    */
   public void deleteSfd(String id)
   {
	   String sql="delete from sfd where id='"+id+"'";
	   this.getJdbcTemplate().update(sql);
   }
   
   /**
    * �����۷���ID�����۷���ʵ��
    * @param id
    * @return
    */
   public Object getSfdById(String id)
   {
	   String sql="select * from sfd where id='"+id+"'";
	   return this.queryForObject(sql, new SfdRowMapper()); 
	   
   }
   
   /**
    * �����ۺ����
    * @param sfd
    */
	public void saveSfd(Sfd sfd) 
	{
		String sql = "insert into sfd(id,client_name,address,mobile,linkman,jxr,cjr,jd_date,jx_date,cj_date,qzfs,state,wx_state,ms,bxyy,bxyy_ms,qz_serial_num,khlx)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] param = new Object[18];
		param[0] = sfd.getId();
		param[1] = sfd.getClient_name();
		param[2] = sfd.getAddress();
		param[3] = sfd.getMobile();
		if(sfd.getKhlx().equals("���ۿͻ�"))
		{
		  param[4] = sfd.getLinkmanLs();
		}
		else
		{
		  param[4] = sfd.getLinkman();
		}		
		param[5] = sfd.getJxr();
		param[6] = sfd.getCjr();		 
		param[7] = sfd.getJd_date();
		param[8] = sfd.getJx_date();
		param[9]= DateComFunc.getToday();
		param[10]=sfd.getQzfs();
		param[11]= sfd.getState();
		param[12]= sfd.getWx_state(); 
		param[13]= sfd.getMs();	
		String bxyy=sfd.getBxyy();
		param[14]= bxyy; 
		if((bxyy.equals("����")) || (bxyy.equals("����ԭ��")))
		{
		   param[15]= sfd.getBxyy_ms();	
		}
		else
		{
			param[15]="";
		}
		param[16]= sfd.getQz_serial_num();
		
		param[17]= sfd.getKhlx();
		getJdbcTemplate().update(sql, param);		 
	}
	 
	/**
	    * �����۷���ID���½ᵥ����
	    * @param id
	    * @return
	    */
	   public void updateSfdJddate(Sfd sfd)
	   {
		   String sql="update sfd set jd_date=? where id=?";
		   Object[] param=new Object[2];
		   param[0]=DateComFunc.getToday();
		   param[1]=sfd.getId();
		   getJdbcTemplate().update(sql, param); 
		   
	   }
	
	
	/**
	 * �޸��۷���
	 * @param sfd
	 */
	public void updateSfd(Sfd sfd)
	{		 
		String sql = "update  sfd set client_name=?,address=?,mobile=?,linkman=?,jxr=?,cjr=?,jd_date=?,jx_date=?,cj_date=?,qzfs=?,state=?,wx_state=?,ms=?,bxyy=?,bxyy_ms=?,qz_serial_num=? where id=?";
		Object[] param = new Object[17];		
		param[0] = sfd.getClient_name();
		param[1] = sfd.getAddress();
		param[2] = sfd.getMobile();
		if(sfd.getKhlx().equals("���ۿͻ�"))
		{
		  param[3] = sfd.getLinkmanLs();
		}
		else
		{
		  param[3] = sfd.getLinkman();
		}
		param[4] = sfd.getJxr();		 
		param[5] = sfd.getCjr();
		param[6] = sfd.getJd_date();
		param[7] = sfd.getJx_date();
		param[8]= DateComFunc.getToday();
		param[9]= sfd.getQzfs();
		param[10]= sfd.getState();
		param[11]= sfd.getWx_state();
		param[12]= sfd.getMs();	
		String bxyy=sfd.getBxyy();
		param[13]= bxyy; 
		if((bxyy.equals("����")) || (bxyy.equals("����ԭ��")))
		{
		   param[14]= sfd.getBxyy_ms();	
		}
		else
		{
			param[14]="";
		}
		param[15]= sfd.getQz_serial_num();
		param[16] = sfd.getId();
		getJdbcTemplate().update(sql, param);	
	}
	
	/**
	 * �޸��۷���״̬
	 * @param sfd
	 */
	public void updateSfdState(Sfd sfd)
	{
		String sql="update sfd set wx_state=?,jd_date=? where id=?";
		Object[] param = new Object[3];
		param[0]=sfd.getWx_state();
		param[1]=sfd.getJd_date();
		param[2]=sfd.getId();
		getJdbcTemplate().update(sql, param);
	}
	
	/**
	 * �޸��۷�������
	 * @param sfd
	 */
	public void updateSfdFlow(String id,String flow,String wx_state)
	{
		String sql="update sfd set flow=?,state='���ύ',wx_state=? where id=?";
		Object[] param = new Object[3];
		param[0]=flow;
		param[1]=wx_state;
		param[2]=id;
		
		getJdbcTemplate().update(sql, param);
	}
	  
	
	/**
	 * �����ۺ���񵥿���ID
	 * 
	 * @return
	 */
	public String updateSfdId() {
		String sql = "select sfdid from cms_all_seq";

		// ȡ��ǰ���к�
		String curId = this.getJdbcTemplate().queryForInt(sql) + "";

		String day = DateComFunc.getCurDay();

		// �����кż�1
		sql = "update cms_all_seq set sfdid=sfdid+1";
		this.getJdbcTemplate().update(sql);

		for (int i = curId.length(); i < 3; i++) {
			curId = "0" + curId;
		}

		return "SF" + day + "-" + curId;

	}
	
	/**
	 * �����ۺ����IDȡ�ۺ������ϸ��Ϣ
	 * @param id
	 * @return
	 */
	public Object getSfd(String id){
		String sql = "select * from sfd where id='" + id + "'";
		
		return this.queryForObject(sql,new BeanRowMapper(Sfd.class));
	}
	
	  
	/**
	 * �鿴�ۺ�����Ƿ��Ѿ��ύ
	 * @param ckd_id
	 * @return
	 */
	public boolean isSfdSubmit(String sfd_id){
		boolean is = false;
		String sql = "select count(*) from sfd where id='" + sfd_id + "' and state='���ύ'";
		int counts = this.getJdbcTemplate().queryForInt(sql);
		if(counts > 0){
			is = true;
		}
		return is;
	}
	
	/**
	 * �ۺ���񵥷�װ����
	 * 
	 * @author Administrator
	 * 
	 */
	class SfdRowMapper implements RowMapper 
	{
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			Sfd sfd = new Sfd();
			if (SqlUtil.columnIsExist(rs, "id"))sfd.setId(rs.getString("id"));
				
			if (SqlUtil.columnIsExist(rs, "client_name"))sfd.setClient_name(rs.getString("client_name"));
				
			if (SqlUtil.columnIsExist(rs, "address"))sfd.setAddress(rs.getString("address"));
				
			if (SqlUtil.columnIsExist(rs, "mobile"))sfd.setMobile(rs.getString("mobile"));
				
			if (SqlUtil.columnIsExist(rs, "linkman"))sfd.setLinkman(rs.getString("linkman"));
				
			if (SqlUtil.columnIsExist(rs, "jxr"))sfd.setJxr(rs.getString("jxr"));	
							 
			if (SqlUtil.columnIsExist(rs, "cjr"))sfd.setCjr(rs.getString("cjr"));
				
			if (SqlUtil.columnIsExist(rs, "jd_date"))sfd.setJd_date(rs.getString("jd_date"));
				
			if (SqlUtil.columnIsExist(rs, "jx_date"))sfd.setJx_date(rs.getString("jx_date"));
				
			if (SqlUtil.columnIsExist(rs, "cj_date"))sfd.setCj_date(rs.getString("cj_date"));
				
			if (SqlUtil.columnIsExist(rs, "qzfs"))sfd.setQzfs(rs.getString("qzfs"));
				
			if (SqlUtil.columnIsExist(rs, "state"))sfd.setState(rs.getString("state"));
							 
			if (SqlUtil.columnIsExist(rs, "ms"))sfd.setMs(rs.getString("ms"));
				
			if (SqlUtil.columnIsExist(rs, "wx_state"))sfd.setWx_state(rs.getString("wx_state"));
			//2010-3-23����һ������������ת���ֶ�
			if (SqlUtil.columnIsExist(rs, "flow"))sfd.setFlow(rs.getString("flow"));
			
			if (SqlUtil.columnIsExist(rs, "bxyy"))sfd.setBxyy(rs.getString("bxyy"));
			
			if (SqlUtil.columnIsExist(rs, "bxyy_ms"))sfd.setBxyy_ms(rs.getString("bxyy_ms"));
			
			if (SqlUtil.columnIsExist(rs, "qz_serial_num"))sfd.setQz_serial_num(rs.getString("qz_serial_num"));
			
			if (SqlUtil.columnIsExist(rs, "khlx"))sfd.setKhlx(rs.getString("khlx"));		
			
			if (SqlUtil.columnIsExist(rs, "linkmanLs"))sfd.setLinkmanLs(rs.getString("linkmanLs"));
			
			return sfd;
		}
	}


}
