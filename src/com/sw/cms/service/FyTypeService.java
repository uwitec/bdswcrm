package com.sw.cms.service;

import java.util.List;

import com.sw.cms.dao.FyTypeDAO;
import com.sw.cms.model.FyType;

public class FyTypeService {
	
	private FyTypeDAO fyTypeDao;
	
	
	/**
	 * ȡ���������б�
	 * @return
	 */
	public List getFyTypeList(){
		return fyTypeDao.getFyTypeList();
	}
	
	
	/**
	 * ����IDȡ�����������
	 * @param id
	 * @return
	 */
	public FyType getFyType(String id){
		return fyTypeDao.getFyType(id);
	}
	
	
	/**
	 * ���·������ͣ����ڸ��£������ڲ���
	 * @param fyType
	 */
	public void updateFyType(FyType fyType){
		fyTypeDao.updateFyType(fyType);
	}
	
	
	
	/**
	 * ɾ����������
	 * @param id
	 */
	public void delFyType(String id){
		fyTypeDao.delFyType(id);
	}
	
	
	/**
	 * �ж�һ�����������Ƿ�������
	 * @param id
	 * @return
	 */
	public boolean isChildren(String id){
		return fyTypeDao.isChildren(id);
	}
	

	public FyTypeDAO getFyTypeDao() {
		return fyTypeDao;
	}

	public void setFyTypeDao(FyTypeDAO fyTypeDao) {
		this.fyTypeDao = fyTypeDao;
	}

}
