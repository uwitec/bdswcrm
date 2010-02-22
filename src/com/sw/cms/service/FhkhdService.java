package com.sw.cms.service;

import java.util.List;

import com.sw.cms.dao.FhkhdDAO;
import com.sw.cms.dao.QtsrDAO;
import com.sw.cms.dao.ShSerialNumFlowDAO;
import com.sw.cms.dao.ShkcDAO;
import com.sw.cms.model.Fhkhd;
import com.sw.cms.model.FhkhdProduct;
import com.sw.cms.model.Page;
import com.sw.cms.model.Qtsr;
import com.sw.cms.model.ShSerialNumFlow;
import com.sw.cms.util.DateComFunc;

public class FhkhdService 
{
   private FhkhdDAO fhkhdDao;
   private ShkcDAO shkcDao;
   private QtsrDAO qtsrDao;
   private ShSerialNumFlowDAO shSerialNumFlowDao;
   private QtsrService qtsrService;
 
public QtsrService getQtsrService() {
	return qtsrService;
}

public void setQtsrService(QtsrService qtsrService) {
	this.qtsrService = qtsrService;
}

public Page getFhkhdList(String con,int curPage,int rowsPerPage)
   {
	  return fhkhdDao.getFhkhdList(con,curPage,rowsPerPage);
   }
   
public String updatefhkhdId()
{
	  return fhkhdDao.getfhkhdId();
}

   public String updateFhkhdId()
   {
	   return fhkhdDao.updateFhkhdId();
   }
   
   public String isHaoShkcExist(List fhkhdProducts)
   {
	   String message="";
	   if(fhkhdProducts!=null&&fhkhdProducts.size()>0)
	   {
		   for(int i=0;i<fhkhdProducts.size();i++)
		   {
		       FhkhdProduct fhkhdProduct=(FhkhdProduct)fhkhdProducts.get(i);
		       String qz_serial_Num=fhkhdProduct.getQz_serial_num();
		       if(!qz_serial_Num.equals(""))
		       {
		    	   int count=shkcDao.getHaoShkcBySerialNum(qz_serial_Num);
		    	   if(count==0)
		    	   {
		    		   message="�ü����������к�:"+qz_serial_Num+" ����Ʒ,���飡";
		    		   break;
		    	   }
		       }
	       }
	   }
		   
	   return message;
   }
   
   public void savefhkhd(Fhkhd fhkhd,List fhkhdProducts)
   {	   
	   fhkhdDao.saveFhkhd(fhkhd, fhkhdProducts);
	   if(fhkhd.getState().equals("���ύ"))
	   {
		   updateShkc(fhkhd,fhkhdProducts);//ɾ���ۺ���
		   saveSerialNumFlow(fhkhd,fhkhdProducts); //������к���ת��¼
		   //if(fhkhd.getIsfy().equals("��"))//�����������
		   //{
			//   saveQtsr(fhkhd);//����ʽ�����
		  // }
		 
	   }
   }
   public void updatefhkhd(Fhkhd fhkhd,List fhkhdProducts)
   {
	   fhkhdDao.updateFhkhd(fhkhd, fhkhdProducts);
	   if(fhkhd.getState().equals("���ύ"))
	   {
		   updateShkc(fhkhd,fhkhdProducts);//�����ۺ���
		   saveSerialNumFlow(fhkhd,fhkhdProducts); //������к���ת��¼
		 //  if(fhkhd.getIsfy().equals("��"))//�����������
		//   {
		//	   saveQtsr(fhkhd);//����ʽ�����
		//   }
		 
	   }
   }
   
   /**
    * ɾ������еĲֿ���Ϣ
    * @param fhkhd
    * @param fhkhdProducts
    */
   public void updateShkc(Fhkhd fhkhd,List fhkhdProducts)
   {
	   if(fhkhdProducts!=null&&fhkhdProducts.size()>0)
	   {
		   for(int i=0;i<fhkhdProducts.size();i++)
		   {
			   FhkhdProduct fhkhdProduct=(FhkhdProduct)fhkhdProducts.get(i);
			   if(!(fhkhdProduct.getProduct_name().equals(""))&&!(fhkhdProduct.getQz_serial_num().equals("")))
			   {
				   shkcDao.deleteShkcById(fhkhdProduct.getQz_serial_num());//ɾ������еĲֿ���Ϣ
			   }
		   }
	   }	  
   }
   /**
    * ������к���ת��¼
    * @param fhkhd
    * @param fhkhdProducts
    */
   public void saveSerialNumFlow(Fhkhd fhkhd,List fhkhdProducts)
   {
	   ShSerialNumFlow  shSerialNumFlow=null;
	   if(fhkhdProducts!=null&&fhkhdProducts.size()>0)
	   {
		   for(int i=0;i<fhkhdProducts.size();i++)
		   {
			   FhkhdProduct fhkhdProduct=(FhkhdProduct)fhkhdProducts.get(i);
			   if(!(fhkhdProduct.getProduct_name().equals(""))&&!(fhkhdProduct.getQz_serial_num().equals("")))
			   {
				    shSerialNumFlow=new ShSerialNumFlow(); 
				    shSerialNumFlow.setCj_date(DateComFunc.getToday());
					shSerialNumFlow.setClient_name(fhkhd.getClient_id());
					shSerialNumFlow.setFs_date(fhkhd.getFh_date());
					shSerialNumFlow.setJsr(fhkhd.getJsr());					 
					shSerialNumFlow.setLinkman(fhkhd.getLxr());
					shSerialNumFlow.setQz_serial_num(fhkhdProduct.getQz_serial_num());					 
					shSerialNumFlow.setYw_dj_id(fhkhd.getId());
					shSerialNumFlow.setYw_url("viewFhkhd.html?id=");
					shSerialNumFlow.setYwtype("�����ͻ�");
					shSerialNumFlowDao.saveShSerialNumFlow(shSerialNumFlow);
			   }			  		   
		   }
	   }
   }
   
   /**
    * ����ʽ�����
    * @param fhkhd
    */
   public void saveQtsr(Fhkhd fhkhd)
   {
	   Qtsr qtsr=new Qtsr();
	   qtsr.setCzr(fhkhd.getCjr());
	   qtsr.setId(qtsrDao.getQtsrID());
	   qtsr.setJsr(fhkhd.getJsr());
	   qtsr.setRemark("�����ͻ���["+fhkhd.getId()+"] ��ȡ����:"+fhkhd.getSkje());
	   qtsr.setSkje(fhkhd.getSkje());
	   qtsr.setSkzh(fhkhd.getSkzh());
	   qtsr.setSr_date(DateComFunc.getToday());
	   qtsr.setState("���ύ");
	   qtsr.setType("����");
	   qtsrService.saveQtsr(qtsr);	   
   }

   public List getFhkhdProductById(String id)
   {
	  return  fhkhdDao.getFhkhdProductById(id);
   }
   
   public void delFhkhd(String id)
   {
	   fhkhdDao.delFhkhd(id);
   }
   
   public Object getFhkhd(String id)
   {
	   return fhkhdDao.getFhkhd(id);
   }
   
   
   
public FhkhdDAO getFhkhdDao() {
	return fhkhdDao;
}

public void setFhkhdDao(FhkhdDAO fhkhdDao) {
	this.fhkhdDao = fhkhdDao;
}

public ShkcDAO getShkcDao() {
	return shkcDao;
}

public void setShkcDao(ShkcDAO shkcDao) {
	this.shkcDao = shkcDao;
}

public QtsrDAO getQtsrDao() {
	return qtsrDao;
}

public void setQtsrDao(QtsrDAO qtsrDao) {
	this.qtsrDao = qtsrDao;
}

public ShSerialNumFlowDAO getShSerialNumFlowDao() {
	return shSerialNumFlowDao;
}

public void setShSerialNumFlowDao(ShSerialNumFlowDAO shSerialNumFlowDao) {
	this.shSerialNumFlowDao = shSerialNumFlowDao;
}
}
