package com.sw.cms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.sw.cms.dao.base.JdbcBaseDAO;
import com.sw.cms.dao.base.SqlUtil;
import com.sw.cms.model.MailSet;
import com.sw.cms.model.Page;

/**
 * 邮件发送设置
 * @author jinyanni
 *
 */
public class MailDAO extends JdbcBaseDAO {
	
	
	/**
	 * 取联系人列表(Mail)
	 * @param type
	 * @return
	 */
	public Page getLxrList(String client_type, int curPage, int rowsPerPage){
		String sql = "select * from view_client_mail where 1=1";
		if(!client_type.equals("")){
			sql += " and client_type='" + client_type + "'";
		}
		return this.getResultByPage(sql, curPage, rowsPerPage);
	}
	
	/**
	 * 获取邮件接收人（不分页）
	 * @param client_type
	 * @return
	 */
	public List getLxrAll(String client_type)
	{
		String sql = "select * from view_client_mail where 1=1";
		if(!client_type.equals("")){
			sql += " and client_type='" + client_type + "'";
		}
		return this.getResultList(sql);
	}
	
	
	/**
	 * 取当前用户邮箱设置
	 * @param user_id
	 * @return
	 */
	public MailSet getMailSet(String user_id){
		String sql = "select * from mail_set where user_id='" + user_id + "'";
		Object obj = this.queryForObject(sql, new MailSetRowMapper());
		
		if(obj != null){
			return (MailSet)obj;
		}else{
			return null;
		}
	}
	
	
	/**
	 * 更新用户邮箱设置
	 * @param mailSet
	 */
	public void updateMailSet(MailSet mailSet){
		String sql = "select count(*) as counts from mail_set where user_id='" + mailSet.getUser_id() + "'";
		
		int counts = this.getJdbcTemplate().queryForInt(sql);
		
		if(counts > 0){
			sql = "update mail_set set smtp=?,user_name=?,password=?,from_user=?,is_ssl=?,port_num=?,remark=? where user_id=?";
		}else{
			sql = "insert into mail_set(smtp,user_name,password,from_user,is_ssl,port_num,remark,user_id) values(?,?,?,?,?,?,?,?)";
		}
		
		Object[] params = new Object[8];
		
		params[0] = mailSet.getSmtp();
		params[1] = mailSet.getUser_name();
		params[2] = mailSet.getPassword();
		params[3] = mailSet.getFrom_user();
		params[4] = mailSet.getIs_ssl();
		params[5] = mailSet.getPort_num();
		params[6] = mailSet.getRemark();
		params[7] = mailSet.getUser_id();
		
		
		this.getJdbcTemplate().update(sql,params);
	}
	
	
	/**
	 * 包装对象(邮件设置)
	 * @author jinyanni
	 *
	 */
	class MailSetRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			MailSet mailSet = new MailSet();

			if (SqlUtil.columnIsExist(rs, "user_id")) mailSet.setUser_id(rs.getString("user_id"));
			if (SqlUtil.columnIsExist(rs, "smtp")) mailSet.setSmtp(rs.getString("smtp"));
			if (SqlUtil.columnIsExist(rs, "user_name")) mailSet.setUser_name(rs.getString("user_name"));
			if (SqlUtil.columnIsExist(rs, "password")) mailSet.setPassword(rs.getString("password"));
			if (SqlUtil.columnIsExist(rs, "from_user")) mailSet.setFrom_user(rs.getString("from_user"));
			if (SqlUtil.columnIsExist(rs, "is_ssl")) mailSet.setIs_ssl(rs.getString("is_ssl"));
			if (SqlUtil.columnIsExist(rs, "port_num")) mailSet.setPort_num(rs.getString("port_num"));
			if (SqlUtil.columnIsExist(rs, "remark")) mailSet.setRemark(rs.getString("remark"));

			return mailSet;
		}
	}

}
