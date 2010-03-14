package com.sw.cms.service;

import java.util.List;
import java.util.Map;

import com.sw.cms.dao.FysqDAO;
import com.sw.cms.dao.QtzcDAO;
import com.sw.cms.dao.RoleDAO;
import com.sw.cms.dao.SysMsgDAO;
import com.sw.cms.dao.UserDAO;
import com.sw.cms.model.Fysq;
import com.sw.cms.model.Page;
import com.sw.cms.model.Qtzc;
import com.sw.cms.model.SysMsg;
import com.sw.cms.model.SysUser;
import com.sw.cms.util.StringUtils;

public class FysqService {
	
	private FysqDAO fysqDao;
	private QtzcDAO qtzcDao;
	private SysMsgDAO sysMsgDao;
	private UserDAO userDao;
	private RoleDAO roleDao;
	
	/**
	 * ���ݲ�ѯ����ȡ�������뵥�б�
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getFysqList(String con,int curPage, int rowsPerPage){
		return fysqDao.getFysqList(con,curPage, rowsPerPage);
	}
	
	/**
	 * ���ݲ�ѯ����ȡ�������뵥�б�
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public List getFysqList(String con){
		return fysqDao.getFysqList(con);
	}
	
	
	/**
	 * ���·���������Ϣ
	 * @param fysq
	 */
	public void updateFysq(Fysq fysq){
		
		Map fysqMap = roleDao.getSpRight("��������");
		String sp_flag = "";
		String role_id = "";
		if(fysqMap != null){
			sp_flag = StringUtils.nullToStr(fysqMap.get("sp_flag"));
			role_id = StringUtils.nullToStr(fysqMap.get("role_id"));
		}
		
		if(fysq.getState().equals("�ύ")){
			
			if(sp_flag.equals("00") || sp_flag.equals("") || role_id.equals("")){
				//����������벻��Ҫ����,����һ����������Ӧ��¼
				
				Qtzc qtzc = new Qtzc();
				
				qtzc.setId(qtzcDao.getQtzcID());       //���
				qtzc.setYwy(fysq.getYwy_id());      //ҵ��Ա
				qtzc.setState("�ѱ���");  //״̬
				qtzc.setType(fysq.getFy_type());   //��������
				qtzc.setZcje(fysq.getJe());   //���
				qtzc.setFklx(fysq.getFklx());  //��������(��ʽ)
				qtzc.setZcxm(fysq.getXgkh());   //��ؿͻ�
				qtzc.setZczh(fysq.getZfzh());   //֧���˻�
				qtzc.setSpr(fysq.getSpr());     //������
				qtzc.setRemark(fysq.getRemark()); //��������
				qtzc.setFysq_id(fysq.getId());   //��Ӧ�������뵥���
				qtzc.setSqr(fysq.getSqr());      //������
				qtzc.setYwy_dept(fysq.getYwy_dept());  //ҵ��Ա����
				
				qtzcDao.saveQtzc(qtzc);
				
				fysq.setState("��֧��");
				
			}else{
				//���������Ҫ����������Ӵ�����������
				
				String[] fysqRoles = role_id.split(",");
				List userList = userDao.getUserByRoles(fysqRoles);
				if(userList != null && userList.size() >0){
					for(int i=0;i<userList.size();i++){	
						Map tempMap =(Map)userList.get(i);
						SysMsg msg = new SysMsg();
						msg.setReciever_id(StringUtils.nullToStr(tempMap.get("user_id")));
						msg.setSender_id(fysq.getCzr());
						msg.setMsg_body("��һ�����������ã��뼰ʱ�������������뵥���Ϊ��" + fysq.getId());						
						sysMsgDao.saveMsg(msg);
					}
				}
				
				fysq.setState("������");
			}
			
		}else if(fysq.getState().equals("����ͨ��")){
			//���������ͨ��������һ����������Ӧ��¼
			
			Qtzc qtzc = new Qtzc();
			
			qtzc.setId(qtzcDao.getQtzcID());       //���
			qtzc.setYwy(fysq.getYwy_id());      //ҵ��Ա
			qtzc.setState("�ѱ���");  //״̬
			qtzc.setType(fysq.getFy_type());   //��������
			qtzc.setZcje(fysq.getJe());   //���
			qtzc.setFklx(fysq.getFklx());  //��������(��ʽ)
			qtzc.setZcxm(fysq.getXgkh());   //��ؿͻ�
			qtzc.setZczh(fysq.getZfzh());   //֧���˻�
			qtzc.setSpr(fysq.getSpr());     //������
			qtzc.setRemark(fysq.getRemark()); //��������
			qtzc.setFysq_id(fysq.getId());   //��Ӧ�������뵥���
			qtzc.setSqr(fysq.getSqr());      //������
			qtzc.setYwy_dept(fysq.getYwy_dept());  //ҵ��Ա����
			
			qtzcDao.saveQtzc(qtzc);
			
			SysMsg msg = new SysMsg();
			msg.setReciever_id(fysq.getCzr());
			msg.setSender_id(fysq.getSpr());
			if(userDao.getUser(fysq.getCzr()) != null){
				msg.setMobile_num(((SysUser)userDao.getUser(fysq.getCzr())).getMobile());
			}			
			msg.setMsg_body("���ύ�ķ������뵥��" + fysq.getId() + "��������ͨ������ȷ�ϣ�");
			
			sysMsgDao.saveMsg(msg);
			
			fysq.setState("��֧��");
			
		}else if(fysq.getState().equals("������ͨ��")){
			SysMsg msg = new SysMsg();
			msg.setReciever_id(fysq.getCzr());
			msg.setSender_id(fysq.getSpr());
			if(userDao.getUser(fysq.getCzr()) != null){
				msg.setMobile_num(((SysUser)userDao.getUser(fysq.getCzr())).getMobile());
			}			
			msg.setMsg_body("���ύ�ķ������뵥��" + fysq.getId() + "��û������ͨ������ȷ�ϣ�");
			
			sysMsgDao.saveMsg(msg);
		}
		
		fysqDao.updateFysq(fysq);
	}
	
	
	/**
	 * ȡ�������������Ϣ
	 * @param id
	 * @return
	 */
	public Fysq getFysq(String id){
		return fysqDao.getFysq(id);
	}
	
	
	/**
	 * ɾ������������Ϣ
	 * @param id
	 */
	public void delFysq(String id){
		fysqDao.delFysq(id);
	}
	
	
	/**
	 * ȡ��ǰ���õ�ID��
	 * @return
	 */
	public String updateFysqId(){
		return fysqDao.getFysqID();
	}
	
	
	/**
	 * ���������Ƿ��������
	 * @param id
	 * @return
	 */
	public boolean isFinishSp(String id){
		return fysqDao.isFinishSp(id);
	}
	

	public FysqDAO getFysqDao() {
		return fysqDao;
	}

	public void setFysqDao(FysqDAO fysqDao) {
		this.fysqDao = fysqDao;
	}


	public QtzcDAO getQtzcDao() {
		return qtzcDao;
	}


	public void setQtzcDao(QtzcDAO qtzcDao) {
		this.qtzcDao = qtzcDao;
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

	public RoleDAO getRoleDao() {
		return roleDao;
	}

	public void setRoleDao(RoleDAO roleDao) {
		this.roleDao = roleDao;
	}

}
