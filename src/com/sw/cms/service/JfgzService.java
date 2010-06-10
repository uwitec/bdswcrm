package com.sw.cms.service;

import java.util.List;

import com.sw.cms.dao.JfgzDAO;
import com.sw.cms.model.Page;
import com.sw.cms.model.Jfgz;

public class JfgzService {
	
	private JfgzDAO jfgzDao;
	
	/**
	 * ȡ���ֹ����б�
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getJfgzList(int curPage, int rowsPerPage,String con){
		return jfgzDao.getJfgzList(curPage, rowsPerPage,con);
	}
	
	
	public String updateJfgzId() {
		return jfgzDao.getJfgzId();
	}
	
	/**
	 * ������ֹ�����Ϣ
	 * @param info
	 */
	public void saveJfgz(Jfgz info){
//		 ������ֹ������ύ����������
		if(jfgzDao.isJfgzSubmit(info.getId())){
			return;
		}
		jfgzDao.saveJfgz(info);
	}
	
	
	/**
	 * ���»��ֹ�����Ϣ
	 * @param info
	 */
	public void updateJfgz(Jfgz info){
		jfgzDao.updateJfgz(info);
	}
	
	
	/**
	 * ����IDȡ���ֹ�����Ϣ
	 * @param id
	 * @return
	 */
	public Jfgz getJfgz(String id){
		return jfgzDao.getJfgz(id);
	}
	
	
	/**
	 * ɾ�����ֹ�����Ϣ
	 * @param id
	 */
	public void delJfgz(String id){
		jfgzDao.delJfgz(id);
	}
	
	 /**
	 * ȡ���л��ֹ����б�
	 * @return
	 */
	public List getAllJfgzList(){
		return jfgzDao.getAllJfgzList();
	}
	
	public JfgzDAO getJfgzDao() {
		return jfgzDao;
	}

	public void setJfgzDao(JfgzDAO jfgzDao) {
		this.jfgzDao = jfgzDao;
	}
}
