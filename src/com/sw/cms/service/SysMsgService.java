package com.sw.cms.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sw.cms.dao.SysMsgDAO;
import com.sw.cms.dao.UserDAO;
import com.sw.cms.model.Page;
import com.sw.cms.model.SysMsg;
import com.sw.cms.model.SysUser;

public class SysMsgService {
	
	private SysMsgDAO sysMsgDao;
	private UserDAO userDao;
	
	/**
	 * 保存消息
	 * @param sysMsg
	 */
	public void saveMsg(SysMsg sysMsg){
		sysMsgDao.saveMsg(sysMsg);
	}
	
	
	/**
	 * 取系统所有用户列表，并添加是否在线标识
	 * @param onLineUser
	 * @return
	 */
	public List getSysUsers(List onLineUser){
		List<SysUser> sysUsers = new ArrayList<SysUser>();
		
		List list = userDao.getAllSysUserListObj();
		if(list != null && list.size() > 0){
			for(int i=0;i<list.size();i++){
				SysUser user = (SysUser)list.get(i);
				if(onLineUser.contains(user.getUser_id())){
					user.setIsOnline("1");
				}
				
				sysUsers.add(user);
			}
		}
		return sysUsers;
	}
	
	
	/**
	 * 批量保存消息
	 * @param reciever_id
	 * @param msg_body
	 */
	public void saveMsgs(String sender_id,String[] reciever_id,String msg_body){
		if(reciever_id != null && reciever_id.length > 0){
			for(int i=0;i<reciever_id.length;i++){
				SysMsg msg = new SysMsg();
				msg.setSender_id(sender_id);
				msg.setReciever_id(reciever_id[i]);
				msg.setMsg_body(msg_body);
				
				sysMsgDao.saveMsg(msg);
			}
		}
	}
	
	
	/**
	 * 保存回复消息
	 * @param sender_id
	 * @param reciever_id
	 * @param msg_body
	 */
	public int saveFwMsg(String sender_id,String reciever_id,String msg_body){
		SysMsg msg = new SysMsg();
		msg.setSender_id(sender_id);
		msg.setReciever_id(reciever_id);
		msg.setMsg_body(msg_body);
		
		return sysMsgDao.saveMsg(msg);
	}
	
	
	/**
	 * 判断用户是否存在未读消息
	 * @param user_id
	 * @return flag 0:不存在；1:存在
	 */
	public int isHasMsgByUser(String user_id){
		return sysMsgDao.isHasMsgByUser(user_id);
	}
	
	
	/**
	 * 读取用户未读消息列表同时将未读消息置为已读
	 * @param user_id
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public List getNotReadMsg(String user_id){
		return sysMsgDao.getNotReadMsg(user_id);
	}
	
	/**
	 * 将未读消息置为已读
	 * @param user_id
	 */
	public void updateNotReadMsg(String user_id){
		sysMsgDao.updateNotReadMsg(user_id);
	}
	
	/**
	 * 返回已读消息列表
	 * @param user_id
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getReadedMsg(String user_id,int curPage, int rowsPerPage){
		return sysMsgDao.getReadedMsg(user_id, curPage, rowsPerPage);
	}
	
	/**
	 * 删除已发送的消息
	 * @param id
	 */
	public void delSendedMsg(String id){
		sysMsgDao.delSendedMsg(id);
	}
	
	
	/**
	 * 删除接收到的消息
	 * @param id
	 */
	public void delRecievedMsg(String id){
		sysMsgDao.delRecievedMsg(id);
	}
	
	
	/**
	 * 取所有用户的未读消息情况
	 */
	public Map getUserNoReadMsg(){
		return sysMsgDao.getUserNoReadMsg();
	}
	

	public SysMsgDAO getSysMsgDao() {
		return sysMsgDao;
	}

	public void setSysMsgDao(SysMsgDAO sysMsgDao) {
		this.sysMsgDao = sysMsgDao;
	}


	public UserDAO getUserDao() {
		return userDao;
	}


	public void setUserDao(UserDAO userDao) {
		this.userDao = userDao;
	}

}
