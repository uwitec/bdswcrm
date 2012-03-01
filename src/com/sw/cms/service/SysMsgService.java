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
	 * ������Ϣ
	 * @param sysMsg
	 */
	public void saveMsg(SysMsg sysMsg){
		sysMsgDao.saveMsg(sysMsg);
	}
	
	
	/**
	 * ȡϵͳ�����û��б�������Ƿ����߱�ʶ
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
	 * ����������Ϣ
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
	 * ����ظ���Ϣ
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
	 * �ж��û��Ƿ����δ����Ϣ
	 * @param user_id
	 * @return flag 0:�����ڣ�1:����
	 */
	public int isHasMsgByUser(String user_id){
		return sysMsgDao.isHasMsgByUser(user_id);
	}
	
	
	/**
	 * ��ȡ�û�δ����Ϣ�б�ͬʱ��δ����Ϣ��Ϊ�Ѷ�
	 * @param user_id
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public List getNotReadMsg(String user_id){
		return sysMsgDao.getNotReadMsg(user_id);
	}
	
	/**
	 * ��δ����Ϣ��Ϊ�Ѷ�
	 * @param user_id
	 */
	public void updateNotReadMsg(String user_id){
		sysMsgDao.updateNotReadMsg(user_id);
	}
	
	/**
	 * �����Ѷ���Ϣ�б�
	 * @param user_id
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getReadedMsg(String user_id,int curPage, int rowsPerPage){
		return sysMsgDao.getReadedMsg(user_id, curPage, rowsPerPage);
	}
	
	/**
	 * ɾ���ѷ��͵���Ϣ
	 * @param id
	 */
	public void delSendedMsg(String id){
		sysMsgDao.delSendedMsg(id);
	}
	
	
	/**
	 * ɾ�����յ�����Ϣ
	 * @param id
	 */
	public void delRecievedMsg(String id){
		sysMsgDao.delRecievedMsg(id);
	}
	
	
	/**
	 * ȡ�����û���δ����Ϣ���
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
