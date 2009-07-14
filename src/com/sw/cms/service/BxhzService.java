package com.sw.cms.service;

import java.util.List;

import com.sw.cms.dao.BxhzDAO;

public class BxhzService 
{
     private BxhzDAO bxhzDao;
     
     public List getHpbxList(String start_date, String end_date,String client_name,String lxr,String product_name,String gcs) 
     {
    	return  bxhzDao.getHpbxList(start_date, end_date, client_name, lxr, product_name, gcs);
     }

	public BxhzDAO getBxhzDao() {
		return bxhzDao;
	}

	public void setBxhzDao(BxhzDAO bxhzDao) {
		this.bxhzDao = bxhzDao;
	}
}
