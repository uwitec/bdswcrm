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
	public void saveClient(Clients client,ClientsLinkman linkman, List clientsPayInfos) 
	{
		 String clients_id=(String)clientsDao.saveClient(client,clientsPayInfos);
		 if(null!=clientsDao.getClient(clients_id)){
			 linkman.setClients_id(clients_id);
			 clientsLinkmanDao.insertLinkman(linkman);
		 }
	}

	/**
	 * ���¿ͻ���Ϣ
	 * 
	 * @param clients
	 */
	public void updateClient(Clients client, List clientsPayInfos) {

		clientsDao.updateClient(client,clientsPayInfos);

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
	 * ���ݿͻ����ȡ
	 * @param client_id
	 * @return
	 */
	public List getClientsPayInfos(String client_id){
		return clientsDao.getClientsPayInfos(client_id);
	}

	/**
	 * ɾ���ͻ���Ϣ
	 * 
	 * @param id
	 */
	public void delClient(String id) {
		clientsDao.delClient(id);
		if(null==clientsDao.getClient(id))
		{
			clientsLinkmanDao.deleteLinkmanByClentsId(id);
			
		}
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
	
	/**
	 * ȡ�ͻ��������׼�¼(ǰ5��)
	 * @param client_id
	 * @return
	 */
	public Page getClientWlyw(String client_id){
		return clientsDao.getClientWlyw(client_id);
	}
	
	/**
	 * �ж�������λ�Ƿ����
	 * @param name
	 * @return
	 */
	public int getClientsIsExist(String name)
	{
		return clientsDao.getClientsIsExist(name);
	}
	
	
	/**
	 * ���ݿͻ���ʾ���������ʾ��Ϣ��ѯ��ؿͻ���Ϣ
	 * @param clientsName
	 * @return
	 */
	public List getClientListByAjaxParam(String clientsName){
		return clientsDao.getClientListByAjaxParam(clientsName);
	}
	
	
	/**
	 * �жϿͻ���Ϣ�Ƿ����ɾ��<BR>
	 * ��������ҵ���ϵ�Ŀͻ���Ϣ����ɾ��<BR>
	 * ����ҵ���ϵ���������ۡ������˻����ɹ����ɹ��˻�����������
	 * @param client_id
	 * @return boolean true:���ԣ�false:������
	 */
	public boolean isCanDel(String client_id){
		return clientsDao.isCanDel(client_id);
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
