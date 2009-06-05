package com.sw.cms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.sw.cms.dao.base.JdbcBaseDAO;
import com.sw.cms.dao.base.SqlUtil;
import com.sw.cms.model.Page;
import com.sw.cms.model.SysMsg;
import com.sw.cms.util.StaticParamDo;

/**
 * 系统消息处理
 * @author liyt
 *
 */
public class SysMsgDAO extends JdbcBaseDAO {
	
	/**
	 * 保存消息
	 * @param sysMsg
	 */
	public int saveMsg(SysMsg sysMsg){
		String sql = "insert into sys_msg(msg_body,sender_id,send_time,reciever_id,mobile_num) " +
				"values(?,?,now(),?,?)";
		
		Object[] param = new Object[4];
		
		param[0] = sysMsg.getMsg_body();
		param[1] = sysMsg.getSender_id();
		param[2] = sysMsg.getReciever_id();
		param[3] = sysMsg.getMobile_num();
		
		return this.getJdbcTemplate().update(sql,param);
	}
	
	
	/**
	 * 判断用户是否存在未读消息
	 * @param user_id
	 * @return flag 0:不存在；>=1:存在
	 */
	public int isHasMsgByUser(String user_id){
		String sql = "select count(*) as nums from sys_msg where reciever_id='" + user_id + "' and msg_read_flag='0'";
		return this.getJdbcTemplate().queryForInt(sql);
	}
	
	
	/**
	 * 读取用户消息列表同时将用户未读消息置为已读
	 * @param user_id
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public List getNotReadMsg(String user_id){
		//返回用户消息列表
		String sql = "select * from sys_msg where reciever_id='" + user_id + "' and msg_read_flag='0' and read_del_flag='0' order by send_time desc";	
		List list = this.getResultList(sql, new SysMsgMapper());
		
		//将用户未读消息置为已读
		sql = "update sys_msg set msg_read_flag='1',read_time=now() where msg_read_flag='0' and reciever_id='" + user_id + "'";
		this.getJdbcTemplate().update(sql);
		
		return list;
	}
	
	
	/**
	 * 返回已读消息列表
	 * @param user_id
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getReadedMsg(String user_id,int curPage, int rowsPerPage){
		String sql = "select * from sys_msg where reciever_id='"+ user_id +"' and msg_read_flag='1' and read_del_flag='0' order by send_time desc";
		return this.getResultByPage(sql, curPage, rowsPerPage, new SysMsgMapper());
	}
	
	
	/**
	 * 删除已发送的消息
	 * @param id
	 */
	public void delSendedMsg(String id){
		String sql = "update sys_msg set send_del_flag='1' where msg_id='" + id + "'";
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * 删除接收到的消息
	 * @param id
	 */
	public void delRecievedMsg(String id){
		String sql = "update sys_msg set read_del_flag='1' where msg_id='" + id + "'";
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * 包装对象(系统消息)
	 * 
	 * @author liyt
	 * 
	 */
	class SysMsgMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			SysMsg msg = new SysMsg();

			if(SqlUtil.columnIsExist(rs,"msg_id")) msg.setMsg_id(rs.getInt("msg_id"));
			if(SqlUtil.columnIsExist(rs,"msg_body")) msg.setMsg_body(rs.getString("msg_body"));
			if(SqlUtil.columnIsExist(rs,"msg_read_flag")) msg.setMsg_read_flag(rs.getString("msg_read_flag"));
			if(SqlUtil.columnIsExist(rs,"sender_id")){
				msg.setSender_id(rs.getString("sender_id"));
				msg.setSender_name(StaticParamDo.getRealNameById(rs.getString("sender_id")));
			}
			if(SqlUtil.columnIsExist(rs,"send_del_flag")) msg.setSend_del_flag(rs.getString("send_del_flag"));
			if(SqlUtil.columnIsExist(rs,"send_time") && rs.getTimestamp("send_time") != null){ 
				msg.setSend_time(rs.getTimestamp("send_time").toString());
			}
			if(SqlUtil.columnIsExist(rs,"reciever_id")){
				msg.setReciever_id(rs.getString("reciever_id"));
				msg.setReciever_name(StaticParamDo.getRealNameById(rs.getString("reciever_id")));
			}
			if(SqlUtil.columnIsExist(rs,"read_del_flag")) msg.setRead_del_flag(rs.getString("read_del_flag"));
			if(SqlUtil.columnIsExist(rs,"read_time") && rs.getTimestamp("read_time") != null){ 
				msg.setRead_time(rs.getTimestamp("read_time").toString());
			}
			if(SqlUtil.columnIsExist(rs,"mobile_num")) msg.setMobile_num(rs.getString("mobile_num"));
			if(SqlUtil.columnIsExist(rs,"mobile_send_flag")) msg.setMobile_send_flag(rs.getString("mobile_send_flag"));
			
			
			return msg;
		}
	}

}
