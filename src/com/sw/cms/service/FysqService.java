package com.sw.cms.service;

import java.util.List;
import java.util.Map;

import com.sw.cms.dao.FysqDAO;
import com.sw.cms.dao.QtzcDAO;
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
	
	/**
	 * ���ݲ�ѯ����ȡ�������뵥�б�
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getFysqList(String con,int curPage, int rowsPerPage){
		return fysqDao.getFysqList(con, curPage, rowsPerPage);
	}
	
	
	/**
	 * ȡ�������������뵥�б�
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getDspFysqList(String con,int curPage, int rowsPerPage){
		return fysqDao.getDspFysqList(con, curPage, rowsPerPage);
	}
	
	
	/**
	 * ���·���������Ϣ
	 * @param fysq
	 */
	public void updateFysq(Fysq fysq){
		fysqDao.updateFysq(fysq);
		
		if(fysq.getState().equals("�ύ")){
			//���һ��������������
			
			//���з�������Ȩ�޵���Ա�б�
			List list = userDao.getFysqUsers();
			if(list != null && list.size() >0){
				for(int i=0;i<list.size();i++){
					Map map = (Map)list.get(i);
					
					SysMsg msg = new SysMsg();
					msg.setReciever_id(StringUtils.nullToStr(map.get("user_id")));
					msg.setSender_id(fysq.getCzr());
					msg.setMsg_body("��һ�����������ã��뼰ʱ�������������뵥���Ϊ��" + fysq.getId());
					msg.setMobile_num(StringUtils.nullToStr(map.get("mobile")));
					
					sysMsgDao.saveMsg(msg);
				}
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

}
