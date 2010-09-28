package com.sw.cms.service;

import java.util.List;

import com.sw.cms.dao.ClientsLinkmanDAO;
import com.sw.cms.dao.HykdaDAO;
import com.sw.cms.dao.HykzzDAO;
import com.sw.cms.model.Hykda;
import com.sw.cms.model.Hykzz;
import com.sw.cms.model.Page;

public class HykdaService {

	private HykzzDAO hykzzDao;
	private HykdaDAO hykdaDao;
	private ClientsLinkmanDAO clientsLinkmanDao;

	/**
	 * ȡ��Ա�������б�
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getHykdaList(int curPage, int rowsPerPage, String con) {
		return hykdaDao.getHykdaList(curPage, rowsPerPage, con);
	}

	/**
	 * ���»�Ա��������Ϣ
	 * @param info
	 */
	public void updateHykda(Hykda info) {
		hykdaDao.updateHykda(info);
	}

	/**
	 * ִ�з�������
	 * @param info
	 */
	public String updateHykfk(Hykda info) {
		String msg = "";
		Hykzz hykzz = hykzzDao.getHykzzda(info.getHykh());
		
		if("��ʹ��".equals(hykzz.getState())){
			msg = "�û�Ա���Ѿ�ʹ��,��ѡ��������Ա��!";
			return msg;
		}
		
		//�޸��ƿ���Ϣ״̬Ϊ��ʹ��
		hykzz.setState("��ʹ��");
		hykzzDao.updateHykzz(hykzz);
		
		//�����Ա����
		hykdaDao.saveHykfk(info);
		
		return msg;
	}

	/**
	 * ����IDȡ��Ա��������Ϣ
	 * 
	 * @param hykh
	 * @return
	 */
	public Hykda getHykda(String hykh) {
		return hykdaDao.getHykda(hykh);
	}
	
	/**
	 * ����IDȡ��Ա��������Ϣ
	 * 
	 * @param id
	 * @return
	 */
	public Hykda getHykdaById(String id) {
		return hykdaDao.getHykdaById(id);
	}

	/**
	 * ȡ���л�Ա�������б�
	 * 
	 * @return
	 */
	public List getAllHykflList() {
		return hykzzDao.getAllHykflList();
	}

	public HykdaDAO getHykdaDao() {
		return hykdaDao;
	}

	public void setHykdaDao(HykdaDAO hykdaDao) {
		this.hykdaDao = hykdaDao;
	}

	public ClientsLinkmanDAO getClientsLinkmanDao() {
		return clientsLinkmanDao;
	}

	public void setClientsLinkmanDao(ClientsLinkmanDAO clientsLinkmanDao) {
		this.clientsLinkmanDao = clientsLinkmanDao;
	}

	public HykzzDAO getHykzzDao() {
		return hykzzDao;
	}

	public void setHykzzDao(HykzzDAO hykzzDao) {
		this.hykzzDao = hykzzDao;
	}
}
