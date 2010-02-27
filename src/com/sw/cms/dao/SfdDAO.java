package com.sw.cms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.sw.cms.dao.base.JdbcBaseDAO;
import com.sw.cms.dao.base.SqlUtil;
import com.sw.cms.model.Bxd;
import com.sw.cms.model.BxdProduct;
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
	   String sql="select *from sfd where id='"+id+"'";
	   return this.queryForObject(sql, new SfdRowMapper()); 
	   
   }
   
   /**
    * �����ۺ����
    * @param sfd
    */
	public void saveSfd(Sfd sfd) 
	{
		String sql = "insert into sfd(id,client_name,address,mobile,linkman,jxr,cjr,jd_date,jx_date,cj_date,qzfs,state,wx_state,ms)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] param = new Object[14];
		param[0] = sfd.getId();
		param[1] = sfd.getClient_name();
		param[2] = sfd.getAddress();
		param[3] = sfd.getMobile();
		param[4] = sfd.getLinkman();
		param[5] = sfd.getJxr();
		param[6] = sfd.getCjr();		 
		param[7] = sfd.getJd_date();
		param[8] = sfd.getJx_date();
		param[9]= DateComFunc.getToday();
		param[10]=sfd.getQzfs();
		param[11]= sfd.getState();
		param[12]= sfd.getWx_state(); 
		param[13]= sfd.getMs();		
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
		String sql = "update  sfd set client_name=?,address=?,mobile=?,linkman=?,jxr=?,cjr=?,jd_date=?,jx_date=?,cj_date=?,qzfs=?,state=?,wx_state=?,ms=? where id=?";
		Object[] param = new Object[14];		
		param[0] = sfd.getClient_name();
		param[1] = sfd.getAddress();
		param[2] = sfd.getMobile();
		param[3] = sfd.getLinkman();
		param[4] = sfd.getJxr();		 
		param[5] = sfd.getCjr();
		param[6] = sfd.getJd_date();
		param[7] = sfd.getJx_date();
		param[8]= DateComFunc.getToday();
		param[9]= sfd.getQzfs();
		param[10]= sfd.getState();
		param[11]= sfd.getWx_state();
		param[12]= sfd.getMs();		
		param[13] = sfd.getId();
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
				
			return sfd;
		}
	}


}
