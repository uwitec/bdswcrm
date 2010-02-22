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
		    		   message="好件库已无序列号:"+qz_serial_Num+" 的商品,请检查！";
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
	   if(fhkhd.getState().equals("已提交"))
	   {
		   updateShkc(fhkhd,fhkhdProducts);//删除售后库存
		   saveSerialNumFlow(fhkhd,fhkhdProducts); //添加序列号流转记录
		   //if(fhkhd.getIsfy().equals("是"))//如果产生费用
		   //{
			//   saveQtsr(fhkhd);//添加资金流向
		  // }
		 
	   }
   }
   public void updatefhkhd(Fhkhd fhkhd,List fhkhdProducts)
   {
	   fhkhdDao.updateFhkhd(fhkhd, fhkhdProducts);
	   if(fhkhd.getState().equals("已提交"))
	   {
		   updateShkc(fhkhd,fhkhdProducts);//更新售后库存
		   saveSerialNumFlow(fhkhd,fhkhdProducts); //添加序列号流转记录
		 //  if(fhkhd.getIsfy().equals("是"))//如果产生费用
		//   {
		//	   saveQtsr(fhkhd);//添加资金流向
		//   }
		 
	   }
   }
   
   /**
    * 删除库存中的仓库信息
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
				   shkcDao.deleteShkcById(fhkhdProduct.getQz_serial_num());//删除库存中的仓库信息
			   }
		   }
	   }	  
   }
   /**
    * 添加序列号流转记录
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
					shSerialNumFlow.setYwtype("返还客户");
					shSerialNumFlowDao.saveShSerialNumFlow(shSerialNumFlow);
			   }			  		   
		   }
	   }
   }
   
   /**
    * 添加资金流向
    * @param fhkhd
    */
   public void saveQtsr(Fhkhd fhkhd)
   {
	   Qtsr qtsr=new Qtsr();
	   qtsr.setCzr(fhkhd.getCjr());
	   qtsr.setId(qtsrDao.getQtsrID());
	   qtsr.setJsr(fhkhd.getJsr());
	   qtsr.setRemark("返还客户单["+fhkhd.getId()+"] 收取费用:"+fhkhd.getSkje());
	   qtsr.setSkje(fhkhd.getSkje());
	   qtsr.setSkzh(fhkhd.getSkzh());
	   qtsr.setSr_date(DateComFunc.getToday());
	   qtsr.setState("已提交");
	   qtsr.setType("其他");
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
