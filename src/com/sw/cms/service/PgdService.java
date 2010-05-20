package com.sw.cms.service;

import java.util.Map;

 

import com.sw.cms.dao.PgdDAO;
import com.sw.cms.dao.SfdDAO;
import com.sw.cms.dao.WxcldDAO;
import com.sw.cms.model.Jhd;
import com.sw.cms.model.Page;
import com.sw.cms.model.Pgd;
import com.sw.cms.model.Sfd;
import com.sw.cms.model.Wxcld;
import com.sw.cms.util.DateComFunc;
import com.sw.cms.util.StringUtils;

public class PgdService 
{
    private PgdDAO pgdDao;
    private SfdDAO sfdDao;
    private WxcldDAO wxcldDao;
    
    public Page getPgdList(String con,int cruPage,int rowsPerPage)
    {
    	return pgdDao.getPgdList(con, cruPage, rowsPerPage);
    }
    
    public Object getPgdBySfdId(String sfd_id)
    {
    	return pgdDao.getPgdBySfdId(sfd_id);
    }
    
    public void savePgd(Pgd pgd,String id)
    {
    	
		Sfd sfd = (Sfd)sfdDao.getSfd(id);
        //如果派工单已提交，不做处理
		if(pgdDao.isPgdSubmit(sfd.getId())){
			return;
		}
    	   sfdDao.updateSfdFlow(id,"派工");
    	   pgd.setP_id(pgdDao.updatePgdId());
 		   pgd.setP_ms("售后服务单号["+sfd.getId()+"]");
 		   pgd.setP_sfd_id(sfd.getId());
 		   pgd.setP_cjr(sfd.getCjr());
 		   pgd.setP_state("已保存");
 		   pgd.setP_wx_state("待处理");
 		   pgdDao.savePgd(pgd);
 	   
    }
    
    public void updatePgd(Pgd pgd)
    {
    	 pgdDao.updatePgd(pgd);    	 
    	 if(pgd.getP_state().equals("已提交")&&pgd.getP_wx_state().equals("待处理"))
    	 {
    		 Sfd sfd=(Sfd)sfdDao.getSfdById(pgd.getP_sfd_id());
    		 Wxcld wxcld=new Wxcld();
             // 如果派工单已提交，不做处理
    		if(wxcldDao.isWxcldSubmit(pgd.getP_id())){
    				return;
    			}    		 
    		 wxcld.setW_id(wxcldDao.updateWxcldId());
    		 wxcld.setW_cjr(pgd.getP_cjr());
    		 wxcld.setW_ms("派工单["+pgd.getP_id()+"]");
    		 wxcld.setW_pgd_id(pgd.getP_id());
    		 wxcld.setW_pgr(pgd.getP_pgr());
    		 wxcld.setW_wxr(pgd.getP_wxr());
    		 wxcld.setW_state("已保存");
    		 wxcld.setW_wx_state("待处理");
    		 wxcld.setW_client_name(StringUtils.nullToStr(sfd.getClient_name()));
    		 wxcld.setW_linkman(StringUtils.nullToStr(sfd.getLinkman()));
    		 wxcld.setW_mobile(StringUtils.nullToStr(sfd.getMobile()));
    		 wxcld.setW_address(StringUtils.nullToStr(sfd.getAddress()));
    		 wxcldDao.saveWxcld(wxcld);
    		 
    	 }
    	 else if(pgd.getP_state().equals("已提交")&&pgd.getP_wx_state().equals("已处理"))
    	 {
    		 Sfd sfd=new Sfd();
    		 sfd.setId(pgd.getP_sfd_id());
    		 sfd.setWx_state("已处理");
    		 sfd.setJd_date(DateComFunc.getToday());
    		 sfdDao.updateSfdState(sfd);
    	 }
    }

	public PgdDAO getPgdDao() {
		return pgdDao;
	}

	public void setPgdDao(PgdDAO pgdDao) {
		this.pgdDao = pgdDao;
	}

	public SfdDAO getSfdDao() {
		return sfdDao;
	}

	public void setSfdDao(SfdDAO sfdDao) {
		this.sfdDao = sfdDao;
	}

	public WxcldDAO getWxcldDao() {
		return wxcldDao;
	}

	public void setWxcldDao(WxcldDAO wxcldDao) {
		this.wxcldDao = wxcldDao;
	}
}
