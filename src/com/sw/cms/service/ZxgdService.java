package com.sw.cms.service;

import java.util.Map;
import com.sw.cms.dao.ZxgdDAO;
import com.sw.cms.dao.SfdDAO;
import com.sw.cms.model.Page;
import com.sw.cms.model.Pgd;
import com.sw.cms.model.Zxgd;
import com.sw.cms.model.Sfd;
import com.sw.cms.util.DateComFunc;
import com.sw.cms.util.StringUtils;

public class ZxgdService 
{
    private ZxgdDAO zxgdDao;
    private SfdDAO sfdDao;
        
    public Page getZxgdList(String con,int cruPage,int rowsPerPage)
    {
    	return zxgdDao.getZxgdList(con, cruPage, rowsPerPage);
    }
    
    public Object getZxgdById(String id)
    {
    	return zxgdDao.getZxgdById(id);
    }
    
    public boolean isNotExist(String id)
    {
    	boolean existNotInZxgd=true;
    	
    	int count=zxgdDao.getZxgdNum(id);
		if(count!=0)
		{
			existNotInZxgd=false;
		}
		return existNotInZxgd;
    }
    
    public void saveZxgd(Zxgd zxgd,String id)
    {
    	Sfd sfd = (Sfd)sfdDao.getSfd(id);
    	
    	   sfdDao.updateSfdFlow(id,"咨询");
 		   zxgd.setId(zxgdDao.updateZxgdId());
 		   zxgd.setCzr(sfd.getCjr());
 		   zxgd.setSfd_id(sfd.getId());
 		   zxgd.setHfr(sfd.getJxr());
 		   zxgd.setState("处理中");
 		   zxgdDao.saveZxgd(zxgd);
    	  
 	   
    }
    
    public void updateZxgd(Zxgd zxgd)
    {
    	 zxgdDao.updateZxgd(zxgd);  
    	 if(zxgd.getState().equals("已完成"))
    	 {
    		 Sfd sfd=new Sfd();
    		 sfd.setId(zxgd.getSfd_id());
    		 sfd.setWx_state("已处理");
    		 sfd.setJd_date(DateComFunc.getToday());
    		 sfdDao.updateSfdState(sfd);
    	 }
    }

	public ZxgdDAO getZxgdDao() {
		return zxgdDao;
	}

	public void setZxgdDao(ZxgdDAO zxgdDao) {
		this.zxgdDao = zxgdDao;
	}

	public SfdDAO getSfdDao() {
		return sfdDao;
	}

	public void setSfdDao(SfdDAO sfdDao) {
		this.sfdDao = sfdDao;
	}

	
}
