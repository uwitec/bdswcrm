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
	 * 取采购发票列表
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getCgfps(String con,int curPage, int rowsPerPage,String orderName,String orderType){
		return cgfpDao.getCgfps(con, curPage, rowsPerPage,orderName,orderType);
	}
	/**
	 * 更新采购发票相关信息
	 * 
	 * @param cgfpDescs
	 */
	public void updateCgfp(Cgfpd cgfpd,List cgfpDescs){
			
		//保存采购发票状态信息
		cgfpDao.updateCgfp(cgfpd,cgfpDescs);
	}

	/**
	 * 根据供应商编号取相关信息
	 * @param gysbh
	 * @return
	 */
	public Object getCgfp(String gysbh){
		return cgfpDao.getCgfp(gysbh);
	}
	
	
	/**
	 * 根据供应商编号取未入库的明细信息
	 * @param gysbh
	 * @return
	 */
	public List getCgfpDesc(String gysbh){
		return cgfpDao.getCgfpDesc(gysbh);
	}

	/**
	 * 根据供应商编号取采购发票的明细信息
	 * @param gysbh
	 * @return
	 */
	public List getCgfpViewDesc(String gysbh){
		return cgfpDao.getCgfpViewDesc(gysbh);
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
