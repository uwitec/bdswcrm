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
	 * 取客户列表（带分页）
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
	 * 根据客户名称模糊查询
	 * 
	 * @param client_name
	 * @return
	 */
	public List getClientList(String client_name) {
		return clientsDao.getClientList(client_name);
	}

	/**
	 * 返回客户列表包括应付款信息(带分页)
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
	 * 保存客户信息
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
	 * 更新客户信息
	 * 
	 * @param clients
	 */
	public void updateClient(Clients client, List clientsPayInfos) {

		clientsDao.updateClient(client,clientsPayInfos);

	}

	/**
	 * 根据ID取客户信息
	 * 
	 * @param id
	 * @return
	 */
	public Object getClient(String id) {
		return clientsDao.getClient(id);
	}
	
	/**
	 * 根据客户编号取
	 * @param client_id
	 * @return
	 */
	public List getClientsPayInfos(String client_id){
		return clientsDao.getClientsPayInfos(client_id);
	}

	/**
	 * 删除客户信息
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
	 * 取客户应收当前应收
	 * 
	 * @param client_name
	 *            客户编号
	 * @return
	 */
	public double getClientYinshou(String client_name) {
		return initParamDao.getClientYinshou(client_name);
	}

	/**
	 * 取客户当前应付
	 * 
	 * @param client_name
	 *            客户编号
	 * @return
	 */
	public double getClientYinfu(String client_name) {
		return initParamDao.getClientYinfu(client_name);
	}

	/**
	 * desc 联系人
	 * 
	 * @return
	 */
	public List getClientsLinkman(String id) {
		return clientsLinkmanDao.getClientsLinkman(id);
	}

	/**
	 * 根据客户ID 获取跟进记录
	 * 
	 * @param id
	 * @return
	 */
	public List getClientsFollow(String id) {
		return clientsFollowDao.getClientsFollow(id);
	}

	/**
	 * 保存联系人
	 * 
	 * @param linkman
	 */
	public void insertLinkman(ClientsLinkman linkman) {
		clientsLinkmanDao.insertLinkman(linkman);
	}

	/**
	 * 根据联系人ID 获取联系人
	 * 
	 * @param id
	 * @return
	 */
	public Object getLinkmanById(String id) {
		return clientsLinkmanDao.getLinkmanById(id);
	}

	/**
	 * 保存修改
	 * 
	 * @param linkman
	 */
	public void updateLinkman(ClientsLinkman linkman) {
		clientsLinkmanDao.updateLinkman(linkman);
	}

	/**
	 * 删除联系人
	 * 
	 * @param id
	 */
	public String deleteLinkman(String id) {
		return clientsLinkmanDao.deleteLinkman(id);
	}

	/**
	 * 保存添加跟进记录
	 * 
	 * @param follow
	 */
	public void insertFollow(ClientsFollow follow) {
		clientsFollowDao.insertFollow(follow);
	}

	/**
	 * 根据跟进ID 获取跟进记录
	 * 
	 * @param id
	 * @return
	 */
	public Object getClientsFollowById(String id) {
		return clientsFollowDao.getClientsFollowById(id);
	}
	

	/**
	 * 保存修改跟进记录
	 * 
	 * @param clientsDao
	 */
	public void updateFollow(ClientsFollow follow) {
		clientsFollowDao.updateFollow(follow);
	}
	
	/**
	 * 取客户往来交易记录(前5条)
	 * @param client_id
	 * @return
	 */
	public Page getClientWlyw(String client_id){
		return clientsDao.getClientWlyw(client_id);
	}
	
	/**
	 * 判断往来单位是否可用
	 * @param name
	 * @return
	 */
	public int getClientsIsExist(String name)
	{
		return clientsDao.getClientsIsExist(name);
	}
	
	
	/**
	 * 根据客户提示框输入的提示信息查询相关客户信息
	 * @param clientsName
	 * @return
	 */
	public List getClientListByAjaxParam(String clientsName){
		return clientsDao.getClientListByAjaxParam(clientsName);
	}
	
	
	/**
	 * 判断客户信息是否可以删除<BR>
	 * 发生往来业务关系的客户信息不能删除<BR>
	 * 往来业务关系包括：销售、销售退货、采购、采购退货、往来调账
	 * @param client_id
	 * @return boolean true:可以；false:不可以
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
