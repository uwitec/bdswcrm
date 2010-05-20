package com.sw.cms.service;

import com.sw.cms.dao.PgdDAO;
import com.sw.cms.dao.SfdDAO;
import com.sw.cms.model.Page;
import com.sw.cms.model.Pgd;
import com.sw.cms.model.Sfd;

public class SfdService 
{
   private SfdDAO sfdDao;
   private PgdDAO pgdDao;
   
   public Page getSfdList(String con,int curPage,int rowsPerPage)
   {
	  return sfdDao.getSfdList(con,curPage,rowsPerPage);
   }
   
   public String updateSfdId()
   {
	   return sfdDao.updateSfdId();
   }
   
   public void  saveSfd(Sfd sfd)
   { 
       //	 如果售后服务单已提交，不做处理
		if(sfdDao.isSfdSubmit(sfd.getId())){
			return;
		}
	   sfdDao.saveSfd(sfd);
    
	   if(sfd.getState().equals("已提交")&&sfd.getWx_state().equals("已处理"))
	   {
		sfdDao.updateSfdJddate(sfd);
	   }
	   
   }
   
 
   
   public void deleteSfd(String id)
   {
	   sfdDao.deleteSfd(id);
   }
   
   public Object getSfdById(String id)
   {
	   return sfdDao.getSfdById(id);
   }
   
   public void updateSfd(Sfd sfd)
   {
	   sfdDao.updateSfd(sfd);
	   if(sfd.getState().equals("已提交")&&sfd.getWx_state().equals("已处理"))
	   {
		sfdDao.updateSfdJddate(sfd);
	   }
   }

public SfdDAO getSfdDao() {
	return sfdDao;
}

public void setSfdDao(SfdDAO sfdDao) {
	this.sfdDao = sfdDao;
}

public PgdDAO getPgdDao() {
	return pgdDao;
}

public void setPgdDao(PgdDAO pgdDao) {
	this.pgdDao = pgdDao;
}
}
