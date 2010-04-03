package com.sw.cms.dao;

import java.util.List;

import com.sw.cms.dao.base.BeanRowMapper;
import com.sw.cms.dao.base.JdbcBaseDAO;
import com.sw.cms.model.Page;
import com.sw.cms.model.SysMsg;

/**
 * ϵͳ��Ϣ����
 * @author liyt
 *
 */
public class SysMsgDAO extends JdbcBaseDAO {
	
	/**
	 * ������Ϣ
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
	 * �ж��û��Ƿ����δ����Ϣ
	 * @param user_id
	 * @return flag 0:�����ڣ�>=1:����
	 */
	public int isHasMsgByUser(String user_id){
		String sql = "select count(*) as nums from sys_msg where reciever_id='" + user_id + "' and msg_read_flag='0'";
		return this.getJdbcTemplate().queryForInt(sql);
	}
	
	
	/**
	 * ��ȡ�û���Ϣ�б�ͬʱ���û�δ����Ϣ��Ϊ�Ѷ�
	 * @param user_id
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public List getNotReadMsg(String user_id){
		//�����û���Ϣ�б�
		String sql = "select * from sys_msg where reciever_id='" + user_id + "' and msg_read_flag='0' and read_del_flag='0' order by send_time desc";	
		List list = this.getResultList(sql,  new BeanRowMapper(SysMsg.class));
		
		return list;
	}
	
	
	/**
	 * ��δ����Ϣ��Ϊ�Ѷ�
	 * @param user_id
	 */
	public void updateNotReadMsg(String user_id){
		//���û�δ����Ϣ��Ϊ�Ѷ�
		String sql = "update sys_msg set msg_read_flag='1',read_time=now() where msg_read_flag='0' and reciever_id='" + user_id + "'";
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * �����Ѷ���Ϣ�б�
	 * @param user_id
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getReadedMsg(String user_id,int curPage, int rowsPerPage){
		String sql = "select * from sys_msg where reciever_id='"+ user_id +"' and msg_read_flag='1' and read_del_flag='0' order by send_time desc";
		return this.getResultByPage(sql, curPage, rowsPerPage, new BeanRowMapper(SysMsg.class));
	}
	
	
	/**
	 * ɾ���ѷ��͵���Ϣ
	 * @param id
	 */
	public void delSendedMsg(String id){
		String sql = "update sys_msg set send_del_flag='1' where msg_id='" + id + "'";
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * ɾ�����յ�����Ϣ
	 * @param id
	 */
	public void delRecievedMsg(String id){
		String sql = "update sys_msg set read_del_flag='1' where msg_id='" + id + "'";
		this.getJdbcTemplate().update(sql);
	}

}
