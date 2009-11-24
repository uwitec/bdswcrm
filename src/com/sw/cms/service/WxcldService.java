package com.sw.cms.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sw.cms.dao.JjdDAO;
import com.sw.cms.dao.PgdDAO;
import com.sw.cms.dao.QtsrDAO;
import com.sw.cms.dao.SfdDAO;
import com.sw.cms.dao.WxcldDAO;
import com.sw.cms.model.Fhkhd;
import com.sw.cms.model.Jjd;
import com.sw.cms.model.JjdProduct;
import com.sw.cms.model.Page;
import com.sw.cms.model.Pgd;
import com.sw.cms.model.Qtsr;
import com.sw.cms.model.Sfd;
import com.sw.cms.model.Wxcld;
import com.sw.cms.model.WxcldProduct;
import com.sw.cms.util.DateComFunc;
import com.sw.cms.util.StringUtils;

public class WxcldService 
{
   private WxcldDAO wxcldDao;
   private PgdDAO pgdDao;
   private SfdDAO sfdDao;
   private QtsrDAO qtsrDao;
   private QtsrService qtsrService;
   private JjdService jjdService;
   private JjdDAO  jjdDao;

   public Page getWxcldList(String con,int cruPage,int rowsPerPage)
   {
	   return wxcldDao.getWxcldList(con, cruPage, rowsPerPage);
   }
   
   public Object getWxcldBySfdPgdId(String pgd_id)
   {
		return wxcldDao.getWxcldBySfdPgdId(pgd_id);
   }
   
   public Object getWxcldProduct(String wxcld_id)
   {
	      
	   return wxcldDao.getWxcldProduct(wxcld_id);
	      
   }
   
   public void updateWxcld(Wxcld wxcld,WxcldProduct wxcldProduct) 
   {
	   wxcldDao.updateWxcld(wxcld,wxcldProduct);
	   if(wxcld.getW_state().equals("已提交"))
	   {
		   if(wxcldProduct.getProduct_clfs().equals("维修"))
		   {	    		 
	    	    if(wxcld.getW_isfy().equals("是"))//如果产生费用
	   		    {
	   			   saveQtsr(wxcld);//添加资金流向
	   		    }
	    		 
		   }
		   if(wxcldProduct.getProduct_clfs().equals("报修"))
		   {
			   Jjd jjd=new Jjd();
			   jjd.setId(jjdDao.getJjdId());
			   jjd.setJj_date(DateComFunc.getToday());
			   jjd.setCj_date(DateComFunc.getToday());
			   jjd.setCjr(wxcld.getW_cjr());
			   jjd.setJjr(wxcld.getW_wxr());
			   jjd.setState("已保存");
			   jjd.setAddress(wxcld.getW_address());
			   jjd.setClient_name(wxcld.getW_client_name());
			   jjd.setLinkman(wxcld.getW_linkman());
			   jjd.setMobile(wxcld.getW_mobile());
			   
			   JjdProduct jjdProduct =new JjdProduct();
			   jjdProduct.setJjd_id(jjd.getId());
			   jjdProduct.setNums(1);
			   jjdProduct.setProduct_id(wxcldProduct.getProduct_id());
			   jjdProduct.setProduct_name(wxcldProduct.getProduct_name());
			   jjdProduct.setProduct_xh(wxcldProduct.getProduct_xh());
			   jjdProduct.setQz_serial_num(wxcldProduct.getProduct_serial_num());
			   jjdProduct.setRemark(wxcldProduct.getProduct_remark());
			   List jjdProducts=new ArrayList();
			   jjdProducts.add(jjdProduct);
			   jjdService.saveJjd(jjd, jjdProducts);
		   }
		   
		     Pgd pgd=new Pgd();
		     pgd.setP_id(wxcld.getW_pgd_id());
		     pgd.setP_wx_state("已处理");
		     pgd.setP_jd_date(DateComFunc.getToday());
  		     pgdDao.updatePgdState(pgd);//修改派工单状态
  		     Map pgdMap=(Map)pgdDao.getSfdByPgdId(wxcld.getW_pgd_id());
  		     if(null==pgdMap)
  		     {
  			   pgdMap=new HashMap();
  		     }
		     Sfd sfd=new Sfd();
  		    sfd.setId(StringUtils.nullToStr(pgdMap.get("p_sfd_id")));
  		    sfd.setWx_state("已处理");
  		    sfd.setJd_date(DateComFunc.getToday());
  		    sfdDao.updateSfdState(sfd);//修改售后服务单状态 
	   }
   }
   
   public void saveQtsr(Wxcld wxcld)
   {
	   Qtsr qtsr=new Qtsr();
	   qtsr.setCzr(wxcld.getW_cjr());
	   qtsr.setId(qtsrDao.getQtsrID());
	   qtsr.setJsr(wxcld.getW_wxr());
	   qtsr.setRemark("维修处理单["+wxcld.getW_id()+"] 收取费用:"+wxcld.getW_skje());
	   qtsr.setSkje(wxcld.getW_skje());
	   qtsr.setSkzh(wxcld.getW_skzh());
	   qtsr.setSr_date(DateComFunc.getToday());
	   qtsr.setState("已提交");
	   qtsr.setType("其他");
	   qtsrService.saveQtsr(qtsr);	   
   }

   
public WxcldDAO getWxcldDao() {
	return wxcldDao;
}

public void setWxcldDao(WxcldDAO wxcldDao) {
	this.wxcldDao = wxcldDao;
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

public QtsrDAO getQtsrDao() {
	return qtsrDao;
}

public void setQtsrDao(QtsrDAO qtsrDao) {
	this.qtsrDao = qtsrDao;
}

public QtsrService getQtsrService() {
	return qtsrService;
}

public void setQtsrService(QtsrService qtsrService) {
	this.qtsrService = qtsrService;
}

public JjdDAO getJjdDao() {
	return jjdDao;
}

public void setJjdDao(JjdDAO jjdDao) {
	this.jjdDao = jjdDao;
}

public JjdService getJjdService() {
	return jjdService;
}

public void setJjdService(JjdService jjdService) {
	this.jjdService = jjdService;
}
}
