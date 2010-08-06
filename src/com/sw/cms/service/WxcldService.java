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
import com.sw.cms.dao.ShkcDAO;

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
   private ShkcDAO  shkcDao;
   
   
   public Page getWxcldList(String con,int cruPage,int rowsPerPage)
   {
	   return wxcldDao.getWxcldList(con, cruPage, rowsPerPage);
   }
   
   public Object getWxcldBySfdPgdId(String w_id)
   {
		return wxcldDao.getWxcldBySfdPgdId(w_id);
   }
     
   public List getWxcldProduct(String wxcld_id)
   {
	      
	   return wxcldDao.getWxcldProduct(wxcld_id);
	      
   }
   
   public void updateWxcld(Wxcld wxcld,List wxcldProducts) 
   {
	   String isProduct="false";
	   if(wxcldProducts != null && wxcldProducts.size()>0){
			for(int i=0;i<wxcldProducts.size();i++){
				
				WxcldProduct wxcldProduct = (WxcldProduct)wxcldProducts.get(i);
				if(wxcldProduct != null){
					if(wxcldProduct.getProduct_name() != null && !wxcldProduct.getProduct_name().equals("")){
	                     wxcldDao.updateWxcld(wxcld,wxcldProduct);
	                     isProduct="true";
	                     if(wxcld.getW_state().equals("���ύ"))
	                     {
		                    if(wxcldProduct.getProduct_clfs().equals("ά��"))
		                    {	    		 
	    	                   if(wxcld.getW_isfy().equals("��"))//�����������
	   		                  {
	   			                saveQtsr(wxcld);//����ʽ�����
	   		                   }
	    		 
		                    }
		                   if(wxcldProduct.getProduct_clfs().equals("����"))
		                   {
			                 Jjd jjd=new Jjd();
			                 jjd.setId(jjdDao.getJjdId());
			                 jjd.setJj_date(DateComFunc.getToday());
			                 jjd.setCj_date(DateComFunc.getToday());
			                 jjd.setCjr(wxcld.getW_cjr());
			                 jjd.setJjr(wxcld.getW_wxr());
			                 jjd.setState("�ѱ���");
			   
			                 jjd.setClient_name(wxcld.getW_client_name());
			                 jjd.setLinkman(wxcld.getW_linkman());
			                 jjd.setMobile(wxcld.getW_mobile());
			                 jjd.setAddress(wxcld.getW_address());
			                 
			                 String sql="select a.khlx from sfd a join pgd b on a.id=b.p_sfd_id join wxcld c on b.p_id=c.w_pgd_id where c.w_id='"+wxcld.getW_id()+"'";
			                 Map jjdMap=(Map)wxcldDao.getResultMap(sql);
			                 jjd.setKhlx(jjdMap.get("khlx").toString());
			                 
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
			                 
			                 shkcDao.deleteShkcById(wxcldProduct.getProduct_id(),wxcldProduct.getN_product_serial_num());//ɾ������еĲֿ���Ϣ
		                   }
		   
		                   Pgd pgd=new Pgd();
		                   pgd.setP_id(wxcld.getW_pgd_id());
		                   pgd.setP_wx_state("�Ѵ���");
		                   pgd.setP_jd_date(DateComFunc.getToday());
  		                   pgdDao.updatePgdState(pgd);//�޸��ɹ���״̬
  		                   Map pgdMap=(Map)pgdDao.getSfdByPgdId(wxcld.getW_pgd_id());
  		                   if(null==pgdMap)
  		                   {
  			                 pgdMap=new HashMap();
  		                   }
		                   Sfd sfd=new Sfd();
  		                   sfd.setId(StringUtils.nullToStr(pgdMap.get("p_sfd_id")));
  		                   sfd.setWx_state("�Ѵ���");
  		                   sfd.setJd_date(DateComFunc.getToday());
  		                   sfdDao.updateSfdState(sfd);//�޸��ۺ����״̬ 
	                     }
					  }
				}
			}
	   }
	   if(isProduct=="false")
	   {
		   wxcldDao.updateWxcldMain(wxcld); 
		   if(wxcld.getW_state().equals("���ύ"))
           {
                 if(wxcld.getW_isfy().equals("��"))//�����������
	             {
		           saveQtsr(wxcld);//����ʽ�����
	             }
                 Pgd pgd=new Pgd();
                 pgd.setP_id(wxcld.getW_pgd_id());
                 pgd.setP_wx_state("�Ѵ���");
                 pgd.setP_jd_date(DateComFunc.getToday());
                 pgdDao.updatePgdState(pgd);//�޸��ɹ���״̬
                 Map pgdMap=(Map)pgdDao.getSfdByPgdId(wxcld.getW_pgd_id());
                 if(null==pgdMap)
                 {
	               pgdMap=new HashMap();
                 }
                 Sfd sfd=new Sfd();
                 sfd.setId(StringUtils.nullToStr(pgdMap.get("p_sfd_id")));
                 sfd.setWx_state("�Ѵ���");
                 sfd.setJd_date(DateComFunc.getToday());
                 sfdDao.updateSfdState(sfd);//�޸��ۺ����״̬ 
           }	 
	   }
	   
   }
   
 
   
   public void saveQtsr(Wxcld wxcld)
   {
	   Qtsr qtsr=new Qtsr();
	   qtsr.setCzr(wxcld.getW_cjr());
	   qtsr.setId(qtsrDao.getQtsrID());
	   qtsr.setJsr(wxcld.getW_wxr());
	   qtsr.setRemark("ά�޴���["+wxcld.getW_id()+"] ��ȡ����:"+wxcld.getW_skje());
	   qtsr.setSkje(wxcld.getW_skje());
	   qtsr.setSkzh(wxcld.getW_skzh());
	   qtsr.setSr_date(DateComFunc.getToday());
	   qtsr.setState("���ύ");
	   qtsr.setType("����");
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

public ShkcDAO getShkcDao() {
	return shkcDao;
}

public void setShkcDao(ShkcDAO shkcDao) {
	this.shkcDao = shkcDao;
}
}
