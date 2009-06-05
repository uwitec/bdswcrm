package com.sw.cms.service;

import java.util.List;

import com.sw.cms.dao.ClientsDAO;
import com.sw.cms.dao.ClientsFollowDAO;
import com.sw.cms.dao.ClientsLinkmanDAO;
import com.sw.cms.dao.InitParamDAO;
import com.sw.cms.model.Clients;
import com.sw.cms.model.ClientsFollow;
import com.sw.cms.model.ClientsLinkman;
import com.sw.cms.model.Page;

public class ClientsService {

	private ClientsDAO clientsDao;

	private InitParamDAO initParamDao;

	private ClientsLinkmanDAO clientsLinkmanDao;

	private ClientsFollowDAO clientsFollowDao;

	public void setClientsDao(ClientsDAO clientsDao) {
		this.clientsDao = clientsDao;
	}

	/**
	 * ȡ�ͻ��б�����ҳ��
	 * 
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getClients(String con, int curPage, int rowsPerPage) {
		return clientsDao.getClients(con, curPage, rowsPerPage);
	}

	/**
	 * ���ݿͻ�����ģ����ѯ
	 * 
	 * @param client_name
	 * @return
	 */
	public List getClientList(String client_name) {
		return clientsDao.getClientList(client_name);
	}

	/**
	 * ���ؿͻ��б����Ӧ������Ϣ(����ҳ)
	 * 
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getClientIncludYsk(String con, int curPage, int rowsPerPage) {
		return clientsDao.getClientIncludYsk(con, curPage, rowsPerPage);
	}

	/**
	 * ����ͻ���Ϣ
	 * 
	 * @param clients
	 */
	public void saveClient(Clients client) {
		clientsDao.saveClient(client);

	}

	/**
	 * ���¿ͻ���Ϣ
	 * 
	 * @param clients
	 */
	public void updateClient(Clients client) {

		clientsDao.updateClient(client);

	}

	/**
	 * ����IDȡ�ͻ���Ϣ
	 * 
	 * @param id
	 * @return
	 */
	public Object getClient(String id) {
		return clientsDao.getClient(id);
	}

	/**
	 * ɾ���ͻ���Ϣ
	 * 
	 * @param id
	 */
	public void delClient(String id) {
		clientsDao.delClient(id);
	}

	/**
	 * ȡ�ͻ�Ӧ�յ�ǰӦ��
	 * 
	 * @param client_name
	 *            �ͻ����
	 * @return
	 */
	public double getClientYinshou(String client_name) {
		return initParamDao.getClientYinshou(client_name);
	}

	/**
	 * ȡ�ͻ���ǰӦ��
	 * 
	 * @param client_name
	 *            �ͻ����
	 * @return
	 */
	public double getClientYinfu(String client_name) {
		return initParamDao.getClientYinfu(client_name);
	}

	/**
	 * desc ��ϵ��
	 * 
	 * @return
	 */
	public List getClientsLinkman(String id) {
		return clientsLinkmanDao.getClientsLinkman(id);
	}

	/**
	 * ���ݿͻ�ID ��ȡ������¼
	 * 
	 * @param id
	 * @return
	 */
	public List getClientsFollow(String id) {
		return clientsFollowDao.getClientsFollow(id);
	}

	/**
	 * ������ϵ��
	 * 
	 * @param linkman
	 */
	public void insertLinkman(ClientsLinkman linkman) {
		clientsLinkmanDao.insertLinkman(linkman);
	}

	/**
	 * ������ϵ��ID ��ȡ��ϵ��
	 * 
	 * @param id
	 * @return
	 */
	public Object getLinkmanById(String id) {
		return clientsLinkmanDao.getLinkmanById(id);
	}

	/**
	 * �����޸�
	 * 
	 * @param linkman
	 */
	public void updateLinkman(ClientsLinkman linkman) {
		clientsLinkmanDao.updateLinkman(linkman);
	}

	/**
	 * ɾ����ϵ��
	 * 
	 * @param id
	 */
	public String deleteLinkman(String id) {
		return clientsLinkmanDao.deleteLinkman(id);
	}

	/**
	 * ������Ӹ�����¼
	 * 
	 * @param follow
	 */
	public void insertFollow(ClientsFollow follow) {
		clientsFollowDao.insertFollow(follow);
	}

	/**
	 * ���ݸ���ID ��ȡ������¼
	 * 
	 * @param id
	 * @return
	 */
	public Object getClientsFollowById(String id) {
		return clientsFollowDao.getClientsFollowById(id);
	}

	/**
	 * �����޸ĸ�����¼
	 * 
	 * @param clientsDao
	 */
	public void updateFollow(ClientsFollow follow) {
		clientsFollowDao.updateFollow(follow);
	}

	public InitParamDAO getInitParamDao() {
		return initParamDao;
	}

	public void setInitParamDao(InitParamDAO initParamDao) {
		this.initParamDao = initParamDao;
	}

	public ClientsDAO getClientsDao() {
		return clientsDao;
	}

	public ClientsFollowDAO getClientsFollowDao() {
		return clientsFollowDao;
	}

	public void setClientsFollowDao(ClientsFollowDAO clientsFollowDao) {
		this.clientsFollowDao = clientsFollowDao;
	}

	public ClientsLinkmanDAO getClientsLinkmanDao() {
		return clientsLinkmanDao;
	}

	public void setClientsLinkmanDao(ClientsLinkmanDAO clientsLinkmanDao) {
		this.clientsLinkmanDao = clientsLinkmanDao;
	}

}
