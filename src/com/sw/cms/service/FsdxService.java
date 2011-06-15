package com.sw.cms.service;

import java.util.List;
import java.util.Map;

import com.sw.cms.dao.FsdxDAO;
import com.sw.cms.model.Flinkman;
import com.sw.cms.model.Flx;
import com.sw.cms.model.Page;

public class FsdxService {
	private FsdxDAO fsdxDao;


	public List getLxList() {
		return fsdxDao.getLxList();
	}

	public void saveLx(Flx flx) {
		fsdxDao.saveLx(flx);
	}

	public Map getLx(String id) {
		return fsdxDao.getLx(id);
	}

	public void updateLx(Flx flx) {
		fsdxDao.updateLx(flx);
	}

	public void deleteLx(String id) {
		fsdxDao.deleteLx(id);
	}

	public Page getLinkmanList(String con, int curPage, int rowsPerPage) {
		return fsdxDao.getLinkmanList(con, curPage, rowsPerPage);
	}

	public void saveLinkman(Flinkman linkman) {
		fsdxDao.saveLinkman(linkman);
	}

	public Map getLinkman(String id) {
		return fsdxDao.getLinkman(id);
	}

	public void updateLinkman(Flinkman linkman) {
		fsdxDao.updateLinkman(linkman);
	}

	public void deleteLinkman(String id) {
		fsdxDao.deleteLinkman(id);

	}

	/**
	 * 发送消息
	 * @param receptMobiles  手机号组
	 * @param msg   消息体
	 */
	public List sendSMS(String sendLinkman, String msg,String user_id) {
		return null;

	}

	public FsdxDAO getFsdxDao() {
		return fsdxDao;
	}

	public void setFsdxDao(FsdxDAO fsdxDao) {
		this.fsdxDao = fsdxDao;
	}

}
