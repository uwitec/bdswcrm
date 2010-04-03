package com.sw.cms.action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.model.LoginInfo;
import com.sw.cms.model.Page;
import com.sw.cms.model.SysMsg;
import com.sw.cms.service.SysMsgService;
import com.sw.cms.service.UserService;
import com.sw.cms.util.Constant;

/**
 * ϵͳ��Ϣ����
 * @author liyt
 *
 */
public class SysMsgAction extends BaseAction {
	
	private SysMsgService sysMsgService;
	private UserService userService;
	
	private Page msgPage;
	private List msgList = new ArrayList();
	private List userList = new ArrayList();
	private SysMsg sysMsg;
	private String msg_id;
	
	private String[] msg_reciever;
	private String reciever_id = "";
	private String msg_body = "";
	
	private int curPage = 1;
	
	/**
	 * ȡ��ǰ�û�δ����Ϣ�б�
	 * @return
	 */
	public String listNotReadMsg(){
		LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		String user_id = info.getUser_id();
		
		msgList = sysMsgService.getNotReadMsg(user_id);
		
		sysMsgService.updateNotReadMsg(user_id);
		return "success";
	}
	
	
	/**
	 * ȡ��ǰ�û��Ѷ���Ϣ�б�
	 * @return
	 */
	public String listReadedMsg(){
		LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		String user_id = info.getUser_id();
		
		int rowsPerPage = Constant.PAGE_SIZE;
		
		msgPage = sysMsgService.getReadedMsg(user_id, curPage, rowsPerPage);
		return "success";
	}
	
	
	/**
	 * �򿪷�����Ϣҳ��
	 * @return
	 */
	public String sendMsg(){
		//�����û�ID�б�
		List<String> onlineUserId = new ArrayList<String>();
		List onlineList = (List)getApplication().getAttribute("onlineUserList");
		Iterator it = onlineList.iterator();
		while(it.hasNext()){
			LoginInfo loginInfo = (LoginInfo)it.next();
			onlineUserId.add(loginInfo.getUser_id());
		}
		
		userList = sysMsgService.getSysUsers(onlineUserId);
		return "success";
	}
	
	
	/**
	 * �������ͱ�����Ϣ
	 * @return
	 */
	public String saveMsg(){
		LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		String user_id = info.getUser_id();
		
		sysMsgService.saveMsgs(user_id,msg_reciever, msg_body);
		return "success";
	}
	
	
	/**
	 * ɾ���Ѷ���Ϣ
	 * @return
	 */
	public String delReadedMsg(){
		sysMsgService.delRecievedMsg(msg_id);
		return "success";
	}
	
	/**
	 * ɾ���ѷ���Ϣ
	 * @return
	 */
	public String delSendedMsg(){
		sysMsgService.delSendedMsg(msg_id);
		return "success";
	}
	
	public Page getMsgPage() {
		return msgPage;
	}
	public void setMsgPage(Page msgPage) {
		this.msgPage = msgPage;
	}
	public SysMsg getSysMsg() {
		return sysMsg;
	}
	public void setSysMsg(SysMsg sysMsg) {
		this.sysMsg = sysMsg;
	}
	public SysMsgService getSysMsgService() {
		return sysMsgService;
	}
	public void setSysMsgService(SysMsgService sysMsgService) {
		this.sysMsgService = sysMsgService;
	}

	public int getCurPage() {
		return curPage;
	}

	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}


	public List getUserList() {
		return userList;
	}


	public void setUserList(List userList) {
		this.userList = userList;
	}


	public List getMsgList() {
		return msgList;
	}


	public void setMsgList(List msgList) {
		this.msgList = msgList;
	}


	public String getMsg_id() {
		return msg_id;
	}


	public void setMsg_id(String msg_id) {
		this.msg_id = msg_id;
	}


	public String getMsg_body() {
		return msg_body;
	}


	public void setMsg_body(String msg_body) {
		this.msg_body = msg_body;
	}


	public String[] getMsg_reciever() {
		return msg_reciever;
	}


	public void setMsg_reciever(String[] msg_reciever) {
		this.msg_reciever = msg_reciever;
	}


	public String getReciever_id() {
		return reciever_id;
	}


	public void setReciever_id(String reciever_id) {
		this.reciever_id = reciever_id;
	}

}
