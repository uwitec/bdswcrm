package com.sw.cms.service;

import com.sw.cms.dao.ClientsDAO;
import com.sw.cms.dao.ClientsLinkmanDAO;
import com.sw.cms.model.ClientsLinkman;
import com.sw.cms.model.Page;

public class LxrService 
{
	private ClientsLinkmanDAO clientsLinkmanDao;
	private ClientsDAO clientsDao;
	
	
	
	public Page getClinetsLinkman(String con,int curPage,int rowsPerPage)
	{
		return clientsLinkmanDao.getClinetsLinkman(con, curPage, rowsPerPage);
	}
	
	
	public void insertLinkman(ClientsLinkman linkman)
	{
		clientsLinkmanDao.insertLinkman(linkman);
	}
	
	public Object getLinkmanById(String id)
	{
		return clientsLinkmanDao.getLinkmanById(id);
	}

	
	
	
	
	
	public ClientsDAO getClientsDao() {
		return clientsDao;
	}

	public void setClientsDao(ClientsDAO clientsDao) {
		this.clientsDao = clientsDao;
	}

	public ClientsLinkmanDAO getClientsLinkmanDao() {
		return clientsLinkmanDao;
	}

	public void setClientsLinkmanDao(ClientsLinkmanDAO clientsLinkmanDao) {
		this.clientsLinkmanDao = clientsLinkmanDao;
	}
}
