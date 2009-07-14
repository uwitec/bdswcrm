package com.sw.cms.service;

import com.sw.cms.dao.BxdDAO;
import com.sw.cms.model.Bxd;
import com.sw.cms.model.BxdProduct;
import com.sw.cms.model.Page;

public class BxdService {
	private BxdDAO bxdDao;

	public Page getBxdList(String con, int curPage, int rowsPerPage) {
		return bxdDao.getBxdList(con, curPage, rowsPerPage);
	}

	public String updateBxdId() {
		return bxdDao.getBxdId();
	}
	
	
	public void insertBxd(Bxd bxd,BxdProduct bxdProduct)
	{
		bxdDao.insertBxd(bxd, bxdProduct);
	}
	
	public Object getBxd(String bxd_id)
	{
		return bxdDao.getBxd(bxd_id);
	}
	
	public Object getBxdProduct(String bxd_id)
	{
		return bxdDao.getBxdProduct(bxd_id);
	}
	
	public void updateBxd(Bxd bxd,BxdProduct bxdProduct)
	{
		bxdDao.updateBxd(bxd, bxdProduct);
	}
	
	public void delBxd(String bxd_id)
	{
		bxdDao.delBxd(bxd_id);
	}
	
	 
	
	
	
	
	
	
	
	
	
	
	
	

	public BxdDAO getBxdDao() {
		return bxdDao;
	}

	public void setBxdDao(BxdDAO bxdDao) {
		this.bxdDao = bxdDao;
	}
}
