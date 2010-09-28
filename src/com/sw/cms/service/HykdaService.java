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
	 * 取会员卡档案列表
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getHykdaList(int curPage, int rowsPerPage, String con) {
		return hykdaDao.getHykdaList(curPage, rowsPerPage, con);
	}

	/**
	 * 更新会员卡档案信息
	 * @param info
	 */
	public void updateHykda(Hykda info) {
		hykdaDao.updateHykda(info);
	}

	/**
	 * 执行发卡操作
	 * @param info
	 */
	public String updateHykfk(Hykda info) {
		String msg = "";
		Hykzz hykzz = hykzzDao.getHykzzda(info.getHykh());
		
		if("已使用".equals(hykzz.getState())){
			msg = "该会员卡已经使用,请选择其它会员卡!";
			return msg;
		}
		
		//修改制卡信息状态为已使用
		hykzz.setState("已使用");
		hykzzDao.updateHykzz(hykzz);
		
		//插入会员档案
		hykdaDao.saveHykfk(info);
		
		return msg;
	}

	/**
	 * 根据ID取会员卡档案信息
	 * 
	 * @param hykh
	 * @return
	 */
	public Hykda getHykda(String hykh) {
		return hykdaDao.getHykda(hykh);
	}
	
	/**
	 * 根据ID取会员卡档案信息
	 * 
	 * @param id
	 * @return
	 */
	public Hykda getHykdaById(String id) {
		return hykdaDao.getHykdaById(id);
	}

	/**
	 * 取所有会员卡分类列表
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
