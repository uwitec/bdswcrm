package com.sw.cms.service;

import java.util.List;
import java.util.Map;

import com.sw.cms.dao.ShSerialNumFlowDAO;
import com.sw.cms.dao.ShkcDAO;
import com.sw.cms.model.Page;
import com.sw.cms.model.Shkc;

public class ShkcService 
{
	private ShkcDAO shkcDao;
	private ShSerialNumFlowDAO shSerialNumFlowDao;
 
	/**
	 * 坏件库列表（带分页）
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
    public Page getShkuIsBadProduct(String con,int curPage,int rowsPerPage)
    {
    	return shkcDao.getShkuIsBadProduct(con,curPage,rowsPerPage);
    }
    
    public Object getShkcById(String id)
    {
    	 return shkcDao.getShkcById(id);
    	 
    }
    
    public Page getShkcIsHaoProduct(String con,int curPage,int rowsPerPage)
    {
    	return shkcDao.getShkcIsHaoProduct(con, curPage, rowsPerPage);
    }
    
    public Page getShkcIsWaiProduct(String con,int curPage,int rowsPerPage)
    {
    	return shkcDao.getShkcIsWaiProduct(con, curPage, rowsPerPage);
    }
    
    public Page getShkcProduct(String con,int curPage,int rowsPerPage)
    {
    	return shkcDao.getShkcProduct(con, curPage, rowsPerPage);
    }
    
    public List getSerialFlow(String serial_Num)
    {
        return shSerialNumFlowDao.getSerialFlow(serial_Num);
    }
    
    
    public ShkcDAO getShkcDao() {
		return shkcDao;
	}
	public void setShkcDao(ShkcDAO shkcDao) {
		this.shkcDao = shkcDao;
	}

	public ShSerialNumFlowDAO getShSerialNumFlowDao() {
		return shSerialNumFlowDao;
	}

	public void setShSerialNumFlowDao(ShSerialNumFlowDAO shSerialNumFlowDao) {
		this.shSerialNumFlowDao = shSerialNumFlowDao;
	}
}
