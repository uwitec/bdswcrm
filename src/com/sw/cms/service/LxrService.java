package com.sw.cms.service;

import com.sw.cms.dao.ClientsLinkmanDAO;
import com.sw.cms.model.ClientsLinkman;
import com.sw.cms.model.Page;

public class LxrService {
	private ClientsLinkmanDAO clientsLinkmanDao;

	public Page getClinetsLinkman(String con, int curPage, int rowsPerPage) {
		return clientsLinkmanDao.getClinetsLinkman(con, curPage, rowsPerPage);
	}

	public void insertLinkman(ClientsLinkman linkman) {
		clientsLinkmanDao.insertLinkman(linkman);
	}

	public Object getLinkmanById(String id) {
		return clientsLinkmanDao.getLinkmanById(id);
	}

	public void updateLinkman(ClientsLinkman linkman) {
		clientsLinkmanDao.updateLinkman(linkman);
	}

	public String deleteLinkman(String id) {

		return clientsLinkmanDao.deleteLinkman(id);
	}

	public ClientsLinkmanDAO getClientsLinkmanDao() {
		return clientsLinkmanDao;
	}

	public void setClientsLinkmanDao(ClientsLinkmanDAO clientsLinkmanDao) {
		this.clientsLinkmanDao = clientsLinkmanDao;
	}
}
