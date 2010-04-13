package com.sw.cms.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sw.cms.dao.CgfpDAO;
import com.sw.cms.dao.RoleDAO;
import com.sw.cms.model.Cgfpd;
import com.sw.cms.model.Page;
import com.sw.cms.util.StringUtils;

public class CgfpService {

	private CgfpDAO cgfpDao;
	private RoleDAO roleDao;

	/**
	 * ȡ�ɹ���Ʊ�б�
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getCgfps(String con,int curPage, int rowsPerPage){
		return cgfpDao.getCgfps(con, curPage, rowsPerPage);
	}
	/**
	 * ���²ɹ���Ʊ�����Ϣ
	 * 
	 * @param cgfpDescs
	 */
	public void updateCgfp(Cgfpd cgfpd,List cgfpDescs){
			
		//����ɹ���Ʊ״̬��Ϣ
		cgfpDao.updateCgfp(cgfpd,cgfpDescs);
	}

	/**
	 * ���ݹ�Ӧ�̱��ȡ�����Ϣ
	 * @param gysbh
	 * @return
	 */
	public Object getCgfp(String gysbh){
		return cgfpDao.getCgfp(gysbh);
	}
	
	
	/**
	 * ���ݹ�Ӧ�̱��ȡ��ϸ��Ϣ
	 * @param gysbh
	 * @return
	 */
	public List getCgfpDesc(String gysbh){
		return cgfpDao.getCgfpDesc(gysbh);
	}

	public CgfpDAO getCgfpDao() {
		return cgfpDao;
	}

	

	public void setCgfpDao(CgfpDAO cgfpDao) {
		this.cgfpDao = cgfpDao;
	}


	
	public RoleDAO getRoleDao() {
		return roleDao;
	}


	public void setRoleDao(RoleDAO roleDao) {
		this.roleDao = roleDao;
	}



}
